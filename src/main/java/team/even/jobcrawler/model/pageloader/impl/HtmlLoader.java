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

import team.even.jobcrawler.model.pageloader.IPageLoader;
import team.even.jobcrawler.model.proxy.ProxyManager;
import team.even.jobcrawler.model.filecounter.FileCounter;
import team.even.jobcrawler.model.filepath.FilePath;

/**
 * htmlҳ��������������������ҳ��html���룬������Ϊ���ص�html�ļ�
 * @author ��ԣ��
 *
 */
public class HtmlLoader implements IPageLoader
{
	@Override
	public String downLoad(String url, boolean useProxy)
	{
		String fileName = null; //ҳ�����غ󱣴���ļ���
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try
		{
			HttpPost post = new HttpPost(url);
			RequestConfig reqConfig = null;
			if(useProxy == true) //ʹ�ô��������
			{
				Map<String, String> proxyMap = ProxyManager.getRandomProxyMap();
				HttpHost proxy = new HttpHost(proxyMap.get("ip"),   //���������ip��ַ
						Integer.parseInt(proxyMap.get("port")), //���������port
						"http"); //���������Э��
				reqConfig = RequestConfig.custom()
						.setProxy(proxy)
						.setConnectTimeout(10000) //�������ӳ�ʱ
						.setSocketTimeout(10000) //�������ݻ�ȡ��ʱ
						.setConnectionRequestTimeout(10000)
						.build();
			}
			else //��ʹ�ô��������
			{
				reqConfig = RequestConfig.custom()
						.setConnectTimeout(10000) //�������ӳ�ʱ
						.setSocketTimeout(10000) //�������ݻ�ȡ��ʱ
						.setConnectionRequestTimeout(10000)
						.build();
			}
			post.setConfig(reqConfig);
			//����л�http����ͷ�еġ�User-Agent��
			post.setHeader("User-Agent", (new UserAgentLib()).getRandomUserAgent());
			post.setHeader("Referer", "https://www.lagou.com/jobs/");
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
			try
			{
				CloseableHttpResponse response = httpClient.execute(post);
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
						Timestamp ts = new Timestamp(System.currentTimeMillis()); //ʱ���
						DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
						String fileNumber = df.format(ts) + (int)(Math.random() * 900 + 100); //ͨ��ʱ��������ļ�
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
