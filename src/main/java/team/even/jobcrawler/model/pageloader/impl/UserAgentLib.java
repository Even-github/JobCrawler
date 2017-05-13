package team.even.jobcrawler.model.pageloader.impl;

import java.util.ArrayList;
import java.util.List;

/**
 * User-Agent��Ϣ�⣬��������л�http����ͷ�е�"User-Agent"��Ϣ���Խ��ͱ���վ��⵽������Ϊ�ķ���
 * @author ��ԣ��
 *
 */
public class UserAgentLib
{
	private List<String> userAgentList = null;
	
	public UserAgentLib()
	{
		userAgentList = new ArrayList<String>();
		userAgentList.add("Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534");
		userAgentList.add("Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0");
		userAgentList.add("Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0)");
		userAgentList.add("Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; en) Presto/2.8.131 Version/11.11");
		userAgentList.add("Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Maxthon 2.0)");
	}
	
	public String getRandomUserAgent()
	{		
		int index = (int)(Math.random() * userAgentList.size());
		return userAgentList.get(index);
	}
}
