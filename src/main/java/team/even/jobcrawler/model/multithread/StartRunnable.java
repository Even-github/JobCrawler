package team.even.jobcrawler.model.multithread;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

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
import team.even.jobcrawler.model.service.Service;

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
	private boolean useProxy; //��ʶ�Ƿ�ʹ�ô��������
	private static Logger logger = Logger.getLogger(StartRunnable.class);
	
	/**
	 * 
	 * @param distinct
	 * @param kind
	 * @param ctrl ע����������״̬������
	 * @param useProxy �Ƿ�ʹ�ô��������
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
		logger.info("�߳̿�ʼ��");
		int page = ctrl.getNextPage(); //��ȡ��һҳ��json����
		logger.info("��ȡ��" + page + "ҳjson���ݡ�");
		String jsonPath = null;
		int jsonFailureTimes = 0; //����jsonҳ������ʧ�ܵĴ���������һ��������ֹͣ��������
		while(ctrl.getIsContinue() == true && jsonFailureTimes < 3) //��������ʧ��3����ǰ�߳̽���
		{
			jsonPath = jsonLoader.downLoad(distinct, kind, page, useProxy);
			if(jsonPath == null) //jsonҳ������ʧ��
			{
				ctrl.setStatus("���������쳣��������������...");
				jsonFailureTimes++; //����ʧ�ܴ�����1
				logger.info("��"+ page +"ҳjson��������ʧ�ܣ�ʧ�ܴ�����" + jsonFailureTimes + "��������������...");
			}
			else //jsonҳ�����سɹ�
			{
				jsonFailureTimes = 0; //����ʧ�ܴ�������
				ctrl.setStatus("��"+ page +"ҳjson���ݰ����سɹ������ݰ�·����" + jsonPath);
				List<String> urlList = jsonResolver.getUrl(jsonPath);
				logger.info("��"+ page +"ҳjson���ݽ����ɹ���");
				for(String url: urlList)
				{
					try
					{	//���ͷ�����վ��Ƶ�ʣ���ֹ����վʶ��Ϊ������Ϊ������ip
						Thread.sleep((int)(Math.random() * 2500));
					} catch (InterruptedException e)
					{
						e.printStackTrace();
					}
					ctrl.setStatus("��������html�ļ�...");
					logger.info("��������html�ļ���" + url);
					String htmlPath = htmlLoader.downLoad(url, useProxy);
					if(htmlPath == null) //html����ʧ��
					{
						ctrl.setStatus("���������쳣��������������...");
						int htmlFailureTimes = 1; //html��������ʧ�ܴ���
						while(htmlFailureTimes < 3)
						{
							logger.info("html�ļ�����ʧ�ܣ�ʧ�ܴ�����" + htmlFailureTimes + "��������������...");
							htmlPath = htmlLoader.downLoad(url, useProxy); //��������
							if(htmlPath == null)
							{
								htmlFailureTimes++;
							}
							else
							{
								break;
							}
						}
						if(htmlFailureTimes >= 3) //������������ʧ�ܣ�������ǰhtmlҳ�棬��ʼ��ȡ��һ��ҳ��
						{
							logger.info("html�ļ�����ʧ�ܴ�������3�Σ�������html��");
							continue;
						}
					}
					if(htmlPath != null)
					{
						ctrl.setStatus("���ڽ���html�ļ����ļ�·����" + htmlPath);
						logger.info("���ڽ���html�ļ����ļ�·����" + htmlPath);
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
				page = ctrl.getNextPage();  //��һҳjson����
			}
		}
		ctrl.stop(); //������ǰ�߳�����
		logger.info("�߳̽�����");
		if(ctrl.isFinish() == true) //��������̵߳����񶼽�����
		{
			logger.info("�����߳������Ѿ����������ڱ�����ȡ������...");
			ctrl.saveJobTypes(kind, distinct);
			FileClear.clearFile(FilePath.DOWNLOADPATH); //����������ص�json�ļ���html�ļ�
			ctrl.setStatus("���������ֹͣ���С�");
			Service.getInstance().setCrawlerIsRuning(false);//���������״̬����Ϊδ����״̬
			logger.info("��������Ѿ���ȫֹͣ��");
		}
//		ctrl.setStatus("���������ֹͣ���С�");
	}
}
