package team.even.jobcrawler.model.pageloader.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.log4j.Logger;

import team.even.jobcrawler.model.pageloader.IPageLoader;
import team.even.jobcrawler.model.proxy.ProxyManager;
import team.even.jobcrawler.model.filecounter.FileCounter;
import team.even.jobcrawler.model.filepath.FilePath;
import team.even.jobcrawler.model.multithread.StartRunnable;

/**
 * html页面下载器，用于下载网页的html代码，并保存为本地的html文件
 * @author 曾裕文
 *
 */
public class HtmlLoader implements IPageLoader
{
	private static Logger logger = Logger.getLogger(StartRunnable.class);
	
	@Override
	public String downLoad(String url, boolean useProxy)
	{
		logger.info("准备发送请求，目标html的url：" + url);
		String fileName = null; //页面下载后保存的文件名
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try
		{
			HttpPost post = new HttpPost(url);
			RequestConfig reqConfig = null;
			if(useProxy == true) //使用代理服务器
			{
				Map<String, String> proxyMap = ProxyManager.getRandomProxyMap();
				HttpHost proxy = new HttpHost(proxyMap.get("ip"),   //代理服务器ip地址
						Integer.parseInt(proxyMap.get("port")), //代理服务器port
						"http"); //代理服务器协议
				reqConfig = RequestConfig.custom()
						.setProxy(proxy)
						.setConnectTimeout(10000) //设置连接超时
						.setSocketTimeout(10000) //设置数据获取超时
						.setConnectionRequestTimeout(10000)
						.build();
			}
			else //不使用代理服务器
			{
				reqConfig = RequestConfig.custom()
						.setConnectTimeout(10000) //设置连接超时
						.setSocketTimeout(10000) //设置数据获取超时
						.setConnectionRequestTimeout(10000)
						.build();
			}
			post.setConfig(reqConfig);
			//随机切换http请求报头中的“User-Agent”
			post.setHeader("User-Agent", (new UserAgentLib()).getRandomUserAgent());
			post.setHeader("Referer", "https://www.lagou.com/jobs/");
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
			try
			{
				logger.info("向服务器发送请求，下载html数据...");
				CloseableHttpResponse response = httpClient.execute(post);
				logger.info("html页面响应状态码：" + response.getStatusLine().getStatusCode());
				try
				{
					if(response.getStatusLine().getStatusCode() == 200)
					{
						HttpEntity entity = response.getEntity();
						StringBuilder content = new StringBuilder();
						InputStream in = entity.getContent();
						try
						{
							String line;
							BufferedReader reader = new BufferedReader
									(new InputStreamReader(in, "UTF-8"));
							while((line = reader.readLine()) != null)
							{
								content.append(line + "\n");
							}							
						}
						finally
						{
							in.close();
						}
						String saveUrl = null;
						OutputStreamWriter output = null;
						saveUrl = FilePath.CONTENTHTMLPATH;
						Timestamp ts = new Timestamp(System.currentTimeMillis()); //时间戳
						DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
						String fileNumber = df.format(ts) + (int)(Math.random() * 900 + 100); //通过时间戳命名文件
						fileName = saveUrl + "/contentPage" + fileNumber + ".html";
						File file = new File(saveUrl);
						if(!file.exists())
						{
							file.mkdir();
						}
						output = new OutputStreamWriter(
							new FileOutputStream(fileName), "UTF-8");
						output.write(content.toString());
						output.close();
						logger.info("html文件保存完毕，保存地址：" + fileName);
					}
				}
				finally
				{
					response.close();
				}
			} catch (ClientProtocolException e)
			{
				e.printStackTrace();
			} catch (IOException e)
			{
				e.printStackTrace();
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
		
		return fileName;
	}

	@Override
	public String downLoad(String distinct, String kind, int page, boolean useProxy)
	{
		return null;
	}
}
