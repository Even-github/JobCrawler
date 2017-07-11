package team.even.jobcrawler.model.proxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 代理服务管理器，负责存储代理服务器的ip和port以及随机分发代理服务器
 * @author 曾裕文
 *
 */
public class ProxyManager
{
	private static List<Map<String, String>> proxyList;

	static
	{
		proxyList = new ArrayList<Map<String, String>>();
	}
	
	public static List<Map<String, String>> getProxyList()
	{
		return proxyList;
	}
	
	/**
	 * 获取随机的代理服务器的ip和port
	 * @return
	 */
	public static Map<String, String> getRandomProxyMap()
	{
		int size = proxyList.size();
		int randomNum = (int)(Math.random() * size);
		return proxyList.get(randomNum);
	}

	public static void setProxyMap(List<Map<String, String>> proxyList)
	{
		ProxyManager.proxyList = proxyList;
	}
}
