package team.even.jobcrawler.model.htmlresolver;

import java.util.List;

/**
 * 地区解析器，获取页面中所有的地区名称
 * @author 曾裕文
 *
 */
public interface IDistrictResolver
{
	/**
	 * 获取拉勾网提供所有招聘地区
	 * @return
	 */
	List<String> getDistrict();
}
