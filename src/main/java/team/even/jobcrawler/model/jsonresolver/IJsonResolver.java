package team.even.jobcrawler.model.jsonresolver;

import java.util.List;

public interface IJsonResolver
{
	/**
	 * 从指定的json文件中解析出url
	 * @return 解析出的url
	 */
	List<String> getUrl(String fileName);
}
