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
 * ʵ��Runnable�ӿڣ�����Ϊ������Ĺ��ܴ���һ�������̣߳���ʵ��ͨ��RunStatusCtrl������;ֹͣ��������С�
 * @author ��ԣ��
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
			ctrl.setStatus("�������ز�����json���ݰ������ݰ�·����" + jsonPath);
			List<String> urlList = jsonResolver.getUrl(jsonPath);
			for(String url: urlList)
			{
				try
				{	//���ͷ�����վ��Ƶ�ʣ���ֹ����վʶ��Ϊ������Ϊ������ip
					Thread.sleep((int)(Math.random() * 2000));
				} catch (InterruptedException e)
				{
					e.printStackTrace();
				}
				ctrl.setStatus("��������html�ļ�...");
				String htmlPath = htmlLoader.downLoad(url);
				if(htmlPath != null)
				{
					ctrl.setStatus("���ڽ���html�ļ����ļ�·����" + htmlPath);
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
			page++;  //��һҳjson����
		}
		ctrl.stop();
		ctrl.setStatus("���������ֹͣ���С�");
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
