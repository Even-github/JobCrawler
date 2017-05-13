package team.even.jobcrawler.model.htmlresolver;

import java.util.List;

/**
 * 职业类型解析器，用于获取页面中的职业类型
 * @author 曾裕文
 *
 */
public interface IKindResolver
{
	/**
	 * 获取拉勾网首页的所有职位名称
	 * @return 包含所有职位名称的队列
	 */
	List<String> getKind();
}
