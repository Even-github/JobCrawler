package team.even.jobcrawler.model.multithread;

import java.util.List;
import java.util.Map;

import team.even.jobcrawler.model.db.factory.JobDataDAOFactory;
import team.even.jobcrawler.model.db.factory.JobTypesDAOFactory;
import team.even.jobcrawler.model.db.vo.JobData;
import team.even.jobcrawler.model.db.vo.JobTypes;
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
	
	public StartRunnable(String distinct, String kind, RunStatusCtrl ctrl)
	{
		this.distinct = distinct;
		this.kind = kind;
		this.ctrl = ctrl;
		jsonLoader = new JsonLoader();
		jsonResolver = new JsonResolver();
		htmlLoader = new HtmlLoader();
		contentResolver = new ContentResolver();
	}
	
	@Override
	public void run()
	{
		int page = 1;
		String jsonPath = null;
		while((jsonPath = jsonLoader.downLoad(distinct, kind, page)) != null &&
				ctrl.getIsContinue() == true)
		{
			ctrl.setStatus("正在下载并解析json数据包，数据包路径：" + jsonPath);
			List<String> urlList = jsonResolver.getUrl(jsonPath);
			for(String url: urlList)
			{
				try
				{	//降低访问网站的频率，防止被网站识别为爬虫行为而封锁ip
					Thread.sleep((int)(Math.random() * 2000));
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				ctrl.setStatus("正在下载html文件...");
				String htmlPath = htmlLoader.downLoad(url);
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
			page++;  //下一页json数据
		}
		ctrl.stop();
		ctrl.setStatus("爬虫程序已停止运行。");
		if(ctrl.getCount() > 0)
		{
			try
			{
				doCreateJobTypes(kind, distinct, ctrl.getCount());
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public void doCreateJobTypes(String kind, String distinct, int amount) throws Exception
	{
		JobTypes jobTypes = new JobTypes();
		jobTypes.setKind(kind);
		jobTypes.setWorkPlace(distinct);
		jobTypes.setAmount(amount);
		JobTypesDAOFactory.getJobTypesDAOInstance().doCreate(jobTypes);
	}
}
