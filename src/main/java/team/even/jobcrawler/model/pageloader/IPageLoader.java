package team.even.jobcrawler.model.pageloader;

/*
 * 页面下载器接口
 */
public interface IPageLoader
{
	/**
	 * 重载downLoad方法
	 * 下载指定页面，并保存为本地文件，一般用于html页面下载
	 * @param url 指定下载的url
	 * @param useProxy 标识是否使用代理服务器
	 * @return 文件下载后保存的文件名
	 */
	String downLoad(String url, boolean useProxy);
	
	/**
	 * 重载downLoad方法
	 * 下载指定页面，并保存为本地文件，一般用于json包下载
	 * @param distinct 招聘地区
	 * @param kind 招聘职业类型（如：Java，C++）
	 * @param page 请求返回第几页数据
	 * @param useProxy 标识是否使用代理服务器
	 * @return
	 */
	String downLoad(String distinct, String kind, int page, boolean useProxy);
}
