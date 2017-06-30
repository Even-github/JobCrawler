package team.even.jobcrawler.model.multithread;

import java.io.File;
import java.util.List;
import java.util.Map;

import team.even.jobcrawler.model.db.factory.JobDataDAOFactory;
import team.even.jobcrawler.model.db.factory.JobTypesDAOFactory;
import team.even.jobcrawler.model.db.vo.JobData;
import team.even.jobcrawler.model.db.vo.JobTypes;
import team.even.jobcrawler.model.fileclear.FileClear;
import team.even.jobcrawler.model.filepath.FilePath;
import team.even.jobcrawler.model.htmlresolver.IContentResolver;
import team.even.jobcrawler.model.htmlresolver.impl.ContentResolver;
import team.even.jobcrawler.model.jsonresolver.impl.JsonResolver;
import team.even.jobcrawler.model.pageloader.IPageLoader;
import team.even.jobcrawler.model.pageloader.impl.HtmlLoader;
import team.even.jobcrawler.model.pageloader.impl.JsonLoader;

/**
 * 实现Runnable接口，用于为爬虫核心功能创建一条独立线程，以实现通过RunStatusCtrl对象中途停止爬虫的运行。
 * @author 曾裕文
 *
 */
public class StartRunnable implements Runnable
{
	private String distinct;
	private String kind;
	private JsonLoader jsonLoader = null;
	private JsonResolver jsonResolver = null; 
	private IPageLoader htmlLoader = null;
	private IContentResolver contentResolver = null;
	private RunStatusCtrl ctrl = null;
	private boolean useProxy; //标识是否使用代理服务器
	
	/**
	 * 
	 * @param distinct
	 * @param kind
	 * @param ctrl 注入爬虫运行状态控制器
	 * @param useProxy 是否使用代理服务器
	 */
	public StartRunnable(String distinct, String kind, RunStatusCtrl ctrl, boolean useProxy)
	{
		this.distinct = distinct;
		this.kind = kind;
		this.ctrl = ctrl;
		this.useProxy = useProxy;
		jsonLoader = new JsonLoader();
		jsonResolver = new JsonResolver();
		htmlLoader = new HtmlLoader();
		contentResolver = new ContentResolver();
	}
	
	@Override
	public void run()
	{
		int page = ctrl.getNextPage(); //爬取下一页的json数据
		String jsonPath = null;
		int jsonFailureTimes = 0; //连接json页面连续失败的次数，超过一定次数将停止爬虫运行
		while(ctrl.getIsContinue() == true && jsonFailureTimes < 3) //连接连续失败3次则当前线程结束
		{
			jsonPath = jsonLoader.downLoad(distinct, kind, page, useProxy);
			if(jsonPath == null) //json页面下载失败
			{
				ctrl.setStatus("网络连接异常，正在重新连接...");
				jsonFailureTimes++; //连接失败次数加1
			}
			else //json页面下载成功
			{
				jsonFailureTimes = 0; //连续失败次数清零
				ctrl.setStatus("正在下载并解析json数据包，数据包路径：" + jsonPath);
				List<String> urlList = jsonResolver.getUrl(jsonPath);
				for(String url: urlList)
				{
					try
					{	//降低访问网站的频率，防止被网站识别为爬虫行为而封锁ip
						Thread.sleep((int)(Math.random() * 1800));
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					ctrl.setStatus("正在下载html文件...");
					String htmlPath = htmlLoader.downLoad(url, useProxy);
					if(htmlPath == null) //html下载失败
					{
						ctrl.setStatus("网络连接异常，正在重新连接...");
						int htmlFailureTimes = 1; //html下载连续失败次数
						while(htmlFailureTimes < 3)
						{
							htmlPath = htmlLoader.downLoad(url, useProxy); //重新下载
							if(htmlPath == null)
							{
								htmlFailureTimes++;
							}
							else
							{
								break;
							}
						}
						if(htmlFailureTimes >= 3) //连续三次连接失败，放弃当前html页面，开始爬取下一个页面
						{
							continue;
						}
					}
					if(htmlPath != null)
					{
						ctrl.setStatus("正在解析html文件，文件路径：" + htmlPath);
						Map<String, String> dataMap = contentResolver.getContent(htmlPath);
						JobData jobData = new JobData();
						jobData.setKind(kind);
						jobData.setJob(dataMap.get("job"));
						jobData.setRecruitingUnit(dataMap.get("recruitingUnit"));
						jobData.setSalary(dataMap.get("salary"));
						jobData.setWorkPlace(dataMap.get("workPlace"));
						jobData.setExperience(dataMap.get("experience"));
						jobData.setAcademic(dataMap.get("academic"));
						jobData.setWorkType(dataMap.get("workType"));
						jobData.setUrl(url);
						try
						{
							JobDataDAOFactory.getJobDataDAOInstance().doCreate(jobData);
						} catch (Exception e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						ctrl.countAdd();
					}
					if(ctrl.getIsContinue() == false)
					{
						break;
					}
				}
				page = ctrl.getNextPage();  //下一页json数据
			}
		}
		ctrl.stop(); //结束当前线程任务
		if(ctrl.isFinish() == true) //如果所有线程的任务都结束了
		{
			ctrl.saveJobTypes(kind, distinct);
			FileClear.clearFile(FilePath.DOWNLOADPATH); //清空爬虫下载的json文件和html文件
			ctrl.setStatus("爬虫程序已停止运行。");
			ctrl.close();
		}
//		ctrl.setStatus("爬虫程序已停止运行。");
	}
}
