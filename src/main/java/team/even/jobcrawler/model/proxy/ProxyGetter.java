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
 * 代理服务获取器， 从网上获取代理服务器的ip和port，并存储到ProxyManager中
 * 代理服务器来源：http://www.kuaidaili.com/free/intr/
 * @author 曾裕文
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
		for(int i = 1; i < trElements.size(); i++) //从第二个tr元素开始，遍历所有tr元素
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
		runStatusCtrl.setStatus("正在获取代理服务器...");
		ProxyManager.setProxyMap(getProxy());
	}
}
