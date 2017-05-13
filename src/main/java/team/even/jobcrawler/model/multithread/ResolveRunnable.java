package team.even.jobcrawler.model.multithread;

import java.util.Map;

import team.even.jobcrawler.model.db.factory.JobDataDAOFactory;
import team.even.jobcrawler.model.db.vo.JobData;
import team.even.jobcrawler.model.htmlresolver.IContentResolver;
import team.even.jobcrawler.model.htmlresolver.impl.ContentResolver;

/**
 * 实现Runnable接口，以提供多线程解析内容页面文件，将库存的所有html文件解析到数据库中保存
 * @author 曾裕文
 *
 */
public class ResolveRunnable implements Runnable
{
	private FileNameDistributor distributor = null;
	private IContentResolver resolver = null;
	private JobDataDAOFactory factory = null;
	
	public ResolveRunnable(FileNameDistributor distributor)
	{
		this.distributor = distributor;
		resolver = new ContentResolver();
		factory = new JobDataDAOFactory();
	}
	
	@Override
	public void run()
	{
		String fileName = null;
		while((fileName = distributor.getFileName()) != null)
		{
			Map<String, String> infoMap = resolver.getContent(fileName); 
			JobData jobData = new JobData();
			jobData.setJob(infoMap.get("job"));
			jobData.setRecruitingUnit(infoMap.get("recruitingUnit"));
			jobData.setSalary(infoMap.get("salary"));
			jobData.setWorkPlace(infoMap.get("workPlace"));
			jobData.setExperience(infoMap.get("experience"));
			jobData.setAcademic(infoMap.get("academic"));
			jobData.setWorkType(infoMap.get("workType"));
			factory.getJobDataDAOInstance().doCreate(jobData);
		}
	}
}
