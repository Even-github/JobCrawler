package team.even.jobcrawler.model.proxy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ������������������洢�����������ip��port�Լ�����ַ����������
 * @author ��ԣ��
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
	 * ��ȡ����Ĵ����������ip��port
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
