package team.even.jobcrawler.model.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import team.even.jobcrawler.model.db.factory.DistrictDAOFactory;
import team.even.jobcrawler.model.db.factory.JobDataDAOFactory;
import team.even.jobcrawler.model.db.factory.JobKindDAOFactory;
import team.even.jobcrawler.model.db.vo.District;
import team.even.jobcrawler.model.db.vo.JobData;
import team.even.jobcrawler.model.db.vo.JobKind;
import team.even.jobcrawler.model.filecounter.FileCounter;
import team.even.jobcrawler.model.htmlresolver.IContentResolver;
import team.even.jobcrawler.model.htmlresolver.IDistrictResolver;
import team.even.jobcrawler.model.htmlresolver.IKindResolver;
import team.even.jobcrawler.model.htmlresolver.impl.ContentResolver;
import team.even.jobcrawler.model.htmlresolver.impl.DistrictResolver;
import team.even.jobcrawler.model.htmlresolver.impl.KindResolver;
import team.even.jobcrawler.model.jsonresolver.impl.JsonResolver;
import team.even.jobcrawler.model.multithread.FileNameDistributor;
import team.even.jobcrawler.model.multithread.ResolveRunnable;
import team.even.jobcrawler.model.multithread.RunStatusCtrl;
import team.even.jobcrawler.model.multithread.StartRunnable;
import team.even.jobcrawler.model.pageloader.IPageLoader;
import team.even.jobcrawler.model.pageloader.impl.HtmlLoader;
import team.even.jobcrawler.model.pageloader.impl.JsonLoader;

public class Service
{
	private IPageLoader htmlLoader = null;
	private IContentResolver contentResolver = null;
	private JobDataDAOFactory factory = null;
	private JsonLoader jsonLoader = null;
	private JsonResolver jsonResolver = null;
	private RunStatusCtrl ctrl = null;
	private IKindResolver kindResolver = null; 
	private IDistrictResolver districtResolver = null;

	private static Service service = null;
	
	private Service()
	{
		htmlLoader = new HtmlLoader();
		contentResolver = new ContentResolver();
		factory = new JobDataDAOFactory();
		jsonLoader = new JsonLoader();
		jsonResolver = new JsonResolver();
		ctrl = RunStatusCtrl.getInstance();
	}
	
	//���õ���ģʽʹ������������һ��Service����
	public static Service getInstance()
	{
		if(service == null)
		{
			service = new Service();
		}
		return service;
	}
	
	/**
	 * ��������̶߳�contentHtml�ļ����µ��ļ�������
	 */
	public void resolverAllContent()
	{
		FileNameDistributor distributor = new FileNameDistributor();
		ResolveRunnable resolve = new ResolveRunnable(distributor);
		//�����̳߳�
		ExecutorService pool = Executors.newFixedThreadPool(5);
		pool.submit(resolve);
		pool.shutdown();
	}
	
	/**
	 * ������������
	 * @param distinct ����
	 * @param kind ְҵ����
	 */
	public void start(String distinct, String kind)
	{
		ctrl.goOn(); //�����������б�־Ϊtrue
		ctrl.setCount(0);
		Thread thread = new Thread(
				new StartRunnable(distinct, kind, ctrl));
		thread.start();
	}
	
	/**
	 * ����������б�־����Ϊfalse��ֹͣ��������
	 */
	public void stop()
	{
		//�����������б�־Ϊfalse
		ctrl.stop();
	}
	
	/**
	 * ������������״̬������
	 * @return ����״̬������
	 */
	public RunStatusCtrl getRunStatusCtrl()
	{
		return ctrl;
	}
	
	public boolean getKinds()
	{
		boolean flag = false;
		kindResolver = new KindResolver();
		List<String> kindList = kindResolver.getKind();
		JobKindDAOFactory factory = new JobKindDAOFactory();
		if(!kindList.isEmpty())
		{
			for(String data: kindList)
			{
				JobKind jobKind = new JobKind();
				jobKind.setKind(data);
				factory.getJobKindDAOInstance().doCreate(jobKind);
			}
			flag = true;
		}
		return flag;
	}
	
	public boolean getDistricts()
	{
		boolean flag = false;
		districtResolver = new DistrictResolver();
		List<String> districtList = districtResolver.getDistrict();
		DistrictDAOFactory factory = new DistrictDAOFactory();
		if(!districtList.isEmpty())
		{
			for(String data: districtList)
			{
				District district = new District();
				district.setDistrict(data);
				factory.getDistrictDAOInstance().doCreate(district);
			}
			flag = true;
		}
		return flag;
	}
}
