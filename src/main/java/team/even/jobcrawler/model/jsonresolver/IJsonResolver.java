package team.even.jobcrawler.model.jsonresolver;

import java.util.List;

public interface IJsonResolver
{
	/**
	 * ��ָ����json�ļ��н�����url
	 * @return ��������url
	 */
	List<String> getUrl(String fileName);
}
