package team.even.jobcrawler.model.multithread;

import java.util.Map;

import team.even.jobcrawler.model.db.factory.JobDataDAOFactory;
import team.even.jobcrawler.model.db.vo.JobData;
import team.even.jobcrawler.model.htmlresolver.IContentResolver;
import team.even.jobcrawler.model.htmlresolver.impl.ContentResolver;

/**
 * ʵ��Runnable�ӿڣ����ṩ���߳̽�������ҳ���ļ�������������html�ļ����������ݿ��б���
 * @author ��ԣ��
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
