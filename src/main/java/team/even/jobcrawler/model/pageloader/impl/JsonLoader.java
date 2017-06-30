package team.even.jobcrawler.model.pageloader.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

import team.even.jobcrawler.model.filecounter.FileCounter;
import team.even.jobcrawler.model.filepath.FilePath;
import team.even.jobcrawler.model.pageloader.IPageLoader;
import team.even.jobcrawler.model.proxy.ProxyManager;

public class JsonLoader implements IPageLoader
{

	@Override
	public String downLoad(String distinct, String kind, int page, boolean useProxy)
	{
		String fileName = null; //页面下载后保存的文件名
		if(distinct != null && kind != null && page > 0) //参数正确
		{
			String dist = null;
			String k = null;
			try
			{
				//将地区名称distinct以及职业类型kind由UTF-8编码转码成URLcode编码，用于作为url的参数
				dist = URLEncoder.encode(distinct, "UTF-8");
				k = URLEncoder.encode(kind, "UTF-8");
			} catch (UnsupportedEncodingException e1)
			{
				e1.printStackTrace();
			}
			String reqUrl = "https://www.lagou.com/jobs/positionAjax.json?px=default&city="
					+ dist  //嵌入转码后的地区参数
					+"&needAddtionalResult=false";
			//将page变量由int类型转换成String类型，以嵌入请求参数中
			String strPage = String.valueOf(page);
			CloseableHttpClient httpClient = HttpClients.createDefault();
			try
			{
				HttpPost post = new HttpPost(reqUrl);
				RequestConfig config = null;
				if(useProxy == true) //使用代理服务器
				{
					Map<String, String> proxyMap = ProxyManager.getRandomProxyMap();
					HttpHost proxy = new HttpHost(proxyMap.get("ip"),   //代理服务器ip地址
							Integer.parseInt(proxyMap.get("port")), //代理服务器port
							"http"); //代理服务器协议
					config = RequestConfig.custom()
							.setProxy(proxy)
							.setConnectTimeout(10000) //设置连接超时
							.setSocketTimeout(10000) //设置数据获取超时
							.setConnectionRequestTimeout(10000)
							.build();
				}
				else //不使用代理服务器
				{
					config = RequestConfig.custom()
							.setConnectTimeout(10000) //设置连接超时
							.setSocketTimeout(10000) //设置数据获取超时
							.setConnectionRequestTimeout(10000)
							.build();
				}
				post.setConfig(config);
				//设置请求报头，模拟浏览器访问服务器
				post.setHeader("User-agent", (new UserAgentLib()).getRandomUserAgent());
				post.setHeader("Referer", "https://www.lagou.com/jobs/list_" + k + "?city="+ dist +"&cl=false&fromSearch=true&labelWords=&suginput=");
				post.setHeader("Host", "www.lagou.com");
				post.setHeader("Connection", "keep-alive");
				post.setHeader("Origin", "https://www.lagou.com");
				post.setHeader("Content-Type", 
						"application/x-www-form-urlencoded; charset=UTF-8");
				post.setHeader("Cookie", 
						"user_trace_token=20170322164744-3bc0f3c99827474abf66f801560ab979; "
						+ "LGUID=20170322164744-37a1bb81-0edc-11e7-954d-5254005c3644; "
						+ "td_cookie=18446744071585481892; "
						+ "index_location_city=%E5%B9%BF%E5%B7%9E; "
						+ "isCloseNotice=0; "
						+ "JSESSIONID=5911D91387A82EDD19C2E2680A6D97E4; "
						+ "_gat=1; SEARCH_ID=742979fc5f274246a3356cb8229e4c9e; "
						+ "_ga=GA1.2.1413671222.1490172465; "
						+ "LGSID=20170326231432-ea59c030-1236-11e7-956e-5254005c3644; "
						+ "LGRID=20170327000307-b38a023e-123d-11e7-9570-5254005c3644; "
						+ "Hm_lvt_4233e74dff0ae5bd0a3d81c6ccf756e6="
						+ "1490529683,1490530280,1490532040,1490542460; "
						+ "Hm_lpvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1490544188; TG-TRACK-CODE=search_code");
				
				//设置请求参数
				List<NameValuePair> params = new ArrayList<NameValuePair>();  
				params.add(new BasicNameValuePair("first", "false"));  
				params.add(new BasicNameValuePair("pn", strPage)); //请求页面页码
				params.add(new BasicNameValuePair("kd", kind));  //将转码后的职业类型参数设置为请求参数	
				
				//插入post表单参数，注意一定要用Consts.UTF_8（第二个参数）转码，不要用URLEncoder转码，否则可能出现中文参数发送后变乱码的情况
				post.setEntity(new UrlEncodedFormEntity(params,Consts.UTF_8));

				CloseableHttpResponse response = null;
				try
				{
					response = httpClient.execute(post);
					if(response.getStatusLine().getStatusCode() == 200)
					{
						HttpEntity entity = response.getEntity();
						InputStream in = entity.getContent();
						BufferedReader reader = new BufferedReader(
											new InputStreamReader(in, "UTF-8"));
						StringBuilder content = new StringBuilder();
						String line = "";
						while((line = reader.readLine()) != null)
						{
							content.append(line);
						}
						in.close();
						//通过将请求参数中的页码与响应的json数据包中的页码相匹配，判断此json数据包是否有效。
						//如果页码相匹配，则json数据包是有效的，可以下载的，否则，应当丢弃。
						Pattern pattern = Pattern.compile("\"pageNo\":" + page);
						Matcher matcher = pattern.matcher(content.toString());
						if(matcher.find()) //页码相匹配
						{
							//获取json文件夹下的文件数量
							int num = new FileCounter().getFileNum(FilePath.JSONPATH);
							Timestamp ts = new Timestamp(System.currentTimeMillis()); //时间戳
							DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
							String fileNumber = df.format(ts) + (int)(Math.random() * 900 + 100); //通过时间戳命名文件
							fileName = FilePath.JSONPATH
									+ "/json" 
									+ fileNumber
									+ ".json";
							//如果存放json文件的目录不存在，则创建此目录
							File file = new File(FilePath.JSONPATH);
							if(!file.exists())
							{
								file.mkdirs();
							}
							OutputStreamWriter out = new OutputStreamWriter(
										new FileOutputStream(fileName),"UTF-8");
							out.write(content.toString());
							out.close();
						}
					}
				} catch (IOException e)
				{
					e.printStackTrace();
				}
				finally
				{
					if(response != null)
					{
						try
						{
							response.close();
						} catch (IOException e)
						{
							e.printStackTrace();
						}
					}
				}
			}
			finally
			{
				try
				{
					httpClient.close();
				} catch (IOException e)
				{
					e.printStackTrace();
				}
			}
			
		}
		return fileName;
	}
	
	@Override
	public String downLoad(String url, boolean useProxy)
	{
		return null;
	}
}
