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
		String fileName = null; //ҳ�����غ󱣴���ļ���
		if(distinct != null && kind != null && page > 0) //������ȷ
		{
			String dist = null;
			String k = null;
			try
			{
				//����������distinct�Լ�ְҵ����kind��UTF-8����ת���URLcode���룬������Ϊurl�Ĳ���
				dist = URLEncoder.encode(distinct, "UTF-8");
				k = URLEncoder.encode(kind, "UTF-8");
			} catch (UnsupportedEncodingException e1)
			{
				e1.printStackTrace();
			}
			String reqUrl = "https://www.lagou.com/jobs/positionAjax.json?px=default&city="
					+ dist  //Ƕ��ת���ĵ�������
					+"&needAddtionalResult=false";
			//��page������int����ת����String���ͣ���Ƕ�����������
			String strPage = String.valueOf(page);
			CloseableHttpClient httpClient = HttpClients.createDefault();
			try
			{
				HttpPost post = new HttpPost(reqUrl);
				RequestConfig config = null;
				if(useProxy == true) //ʹ�ô��������
				{
					Map<String, String> proxyMap = ProxyManager.getRandomProxyMap();
					HttpHost proxy = new HttpHost(proxyMap.get("ip"),   //���������ip��ַ
							Integer.parseInt(proxyMap.get("port")), //���������port
							"http"); //���������Э��
					config = RequestConfig.custom()
							.setProxy(proxy)
							.setConnectTimeout(10000) //�������ӳ�ʱ
							.setSocketTimeout(10000) //�������ݻ�ȡ��ʱ
							.setConnectionRequestTimeout(10000)
							.build();
				}
				else //��ʹ�ô��������
				{
					config = RequestConfig.custom()
							.setConnectTimeout(10000) //�������ӳ�ʱ
							.setSocketTimeout(10000) //�������ݻ�ȡ��ʱ
							.setConnectionRequestTimeout(10000)
							.build();
				}
				post.setConfig(config);
				//��������ͷ��ģ����������ʷ�����
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
				
				//�����������
				List<NameValuePair> params = new ArrayList<NameValuePair>();  
				params.add(new BasicNameValuePair("first", "false"));  
				params.add(new BasicNameValuePair("pn", strPage)); //����ҳ��ҳ��
				params.add(new BasicNameValuePair("kd", kind));  //��ת����ְҵ���Ͳ�������Ϊ�������	
				
				//����post��������ע��һ��Ҫ��Consts.UTF_8���ڶ���������ת�룬��Ҫ��URLEncoderת�룬������ܳ������Ĳ������ͺ����������
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
						//ͨ������������е�ҳ������Ӧ��json���ݰ��е�ҳ����ƥ�䣬�жϴ�json���ݰ��Ƿ���Ч��
						//���ҳ����ƥ�䣬��json���ݰ�����Ч�ģ��������صģ�����Ӧ��������
						Pattern pattern = Pattern.compile("\"pageNo\":" + page);
						Matcher matcher = pattern.matcher(content.toString());
						if(matcher.find()) //ҳ����ƥ��
						{
							//��ȡjson�ļ����µ��ļ�����
							int num = new FileCounter().getFileNum(FilePath.JSONPATH);
							Timestamp ts = new Timestamp(System.currentTimeMillis()); //ʱ���
							DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
							String fileNumber = df.format(ts) + (int)(Math.random() * 900 + 100); //ͨ��ʱ��������ļ�
							fileName = FilePath.JSONPATH
									+ "/json" 
									+ fileNumber
									+ ".json";
							//������json�ļ���Ŀ¼�����ڣ��򴴽���Ŀ¼
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
