package team.even.jobcrawler.model.proxy;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import team.even.jobcrawler.model.multithread.RunStatusCtrl;

/**
 * ��������ȡ���� �����ϻ�ȡ�����������ip��port�����洢��ProxyManager��
 * �����������Դ��http://www.kuaidaili.com/free/intr/
 * @author ��ԣ��
 *
 */
public class ProxyGetter
{
	public static List<Map<String, String>> getProxy() throws IOException
	{
		List<Map<String, String>> proxyList = new ArrayList<>(); 
		
		String url = "http://www.kuaidaili.com/free/inha/";
		Document doc = Jsoup.connect(url).get();
		Elements trElements = doc.getElementsByTag("tr");
		for(int i = 1; i < trElements.size(); i++) //�ӵڶ���trԪ�ؿ�ʼ����������trԪ��
		{
			Element e = trElements.get(i);
			Elements ipElements = e.getElementsByAttributeValue("data-title", "IP");
			Elements portElements = e.getElementsByAttributeValue("data-title", "PORT");
			String ip = ipElements.first().text();
			String port = portElements.first().text();
			Map<String, String> map = new HashMap<String, String>();
			map.put("ip", ip);
			map.put("port", port);
			proxyList.add(map);
		}
		return proxyList;
	}
	
	public static void saveProxy() throws IOException
	{
		RunStatusCtrl runStatusCtrl = RunStatusCtrl.getInstance();
		runStatusCtrl.setStatus("���ڻ�ȡ���������...");
		ProxyManager.setProxyMap(getProxy());
	}
}
