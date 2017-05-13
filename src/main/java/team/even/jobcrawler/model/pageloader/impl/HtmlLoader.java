package team.even.jobcrawler.model.pageloader.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import team.even.jobcrawler.model.pageloader.IPageLoader;
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
	public String downLoad(String url)
	{
		String fileName = null; //ҳ�����غ󱣴���ļ���
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try
		{
			HttpPost post = new HttpPost(url);
			RequestConfig reqConfig = RequestConfig.custom()
					.setConnectTimeout(5000)   //���ó�ʱʱ��5��
					.setSocketTimeout(10000)	//���û�ȡ���ݳ�ʱʱ��10��
					.build();
			post.setConfig(reqConfig);
			//����л�http����ͷ�еġ�User-Agent��
			post.setHeader("User-Agent", (new UserAgentLib()).getRandomUserAgent());
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
						int num = (new FileCounter()).getFileNum(saveUrl);
						fileName = saveUrl + "/contentPage" + (num + 1) + ".html";
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
	public String downLoad(String distinct, String kind, int page)
	{
		return null;
	}
}
