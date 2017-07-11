package team.even.jobcrawler.model.htmlresolver;

import java.util.Map;

/**
 * 页面内容解析器接口
 * @author 曾裕文
 *
 */
public interface IContentResolver
{
	/**
	 * 获取招聘页面核心信息
	 * @param fileName 被解析html文件
	 * @return 获取的招聘信息
	 */
	Map<String, String> getContent(String fileName);
	
	/**
	 * 字符串过滤器，过滤掉不需要的字符串
	 * @param str 需要被过滤的字符串
	 * @return 过滤后得到的字符串
	 */
	String strFilter(String str);
}
