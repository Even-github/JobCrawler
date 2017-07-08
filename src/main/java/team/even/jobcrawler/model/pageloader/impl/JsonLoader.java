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
import org.apache.log4j.Logger;

import team.even.jobcrawler.model.filecounter.FileCounter;
import team.even.jobcrawler.model.filepath.FilePath;
import team.even.jobcrawler.model.multithread.StartRunnable;
import team.even.jobcrawler.model.pageloader.IPageLoader;
import team.even.jobcrawler.model.proxy.ProxyManager;

public class JsonLoader implements IPageLoader
{
	private static Logger logger = Logger.getLogger(StartRunnable.class);

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
			logger.info("准备发送请求，目标json的url地址：" + reqUrl);
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
				post.setHeader("Cookie", "JSESSIONID=ABAAABAABEEAAJAA1F32254C581B2BDBC46BC2657D20F21; "
						+ "Hm_lvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1499048836; "
						+ "Hm_lpvt_4233e74dff0ae5bd0a3d81c6ccf756e6=1499048890; "
						+ "_ga=GA1.2.1366457569.1499048836; _gat=1; "
						+ "user_trace_token=20170703102717-225534cb-5f97-11e7-a18b-5254005c3644; "
						+ "LGSID=20170703102717-22553675-5f97-11e7-a18b-5254005c3644; "
						+ "PRE_UTM=; "
						+ "PRE_HOST=; "
						+ "PRE_SITE=; "
						+ "PRE_LAND=https%3A%2F%2Fwww.lagou.com%2F; "
						+ "LGRID=20170703102812-42d445e8-5f97-11e7-a18b-5254005c3644; "
						+ "LGUID=20170703102717-2255380e-5f97-11e7-a18b-5254005c3644; "
						+ "index_location_city=%E5%85%A8%E5%9B%BD; "
						+ "TG-TRACK-CODE=index_navigation; "
						+ "SEARCH_ID=575985ffe2174de0a1adb86d1646f49b");
				
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
					logger.info("向服务器发送请求，下载json数据...");
					response = httpClient.execute(post);
					logger.info("json页面响应状态码：" + response.getStatusLine().getStatusCode());
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
							logger.info("json数据有效，正在保存...");
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
							logger.info("json数据保存完毕，保存路径：" + fileName);
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
