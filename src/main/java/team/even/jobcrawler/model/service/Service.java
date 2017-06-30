package team.even.jobcrawler.model.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import team.even.jobcrawler.model.db.factory.DistrictDAOFactory;
import team.even.jobcrawler.model.db.factory.JobDataDAOFactory;
import team.even.jobcrawler.model.db.factory.JobKindDAOFactory;
import team.even.jobcrawler.model.db.factory.PasswordDAOFactory;
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
import team.even.jobcrawler.model.passwordmanager.PasswordManager;
import team.even.jobcrawler.model.proxy.ProxyGetter;
import team.even.jobcrawler.model.proxy.ProxyManager;

public class Service
{
	private IPageLoader htmlLoader = null;
	private IContentResolver contentResolver = null;
	private JsonLoader jsonLoader = null;
	private JsonResolver jsonResolver = null;
	private RunStatusCtrl runStatusctrl = null; //��������״̬������
	private IKindResolver kindResolver = null; 
	private IDistrictResolver districtResolver = null;
	private volatile boolean crawlerIsRuning; //��������Ƿ��������еı�ʶ��true�������У�false��ʾδ����
	private static Service service = null;
	
	private Service()
	{
		htmlLoader = new HtmlLoader();
		contentResolver = new ContentResolver();
		jsonLoader = new JsonLoader();
		jsonResolver = new JsonResolver();
		runStatusctrl = RunStatusCtrl.getInstance();
		crawlerIsRuning = false; 
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
	 * ��ȡ��������״̬�����Ϊfalse����������Ϊtrue��������false;���Ϊtrue���򷵻�true
	 * @return
	 */
	public synchronized boolean getCrawlerIsRuningStatus()
	{
		if(crawlerIsRuning == false) //δ����
		{
			crawlerIsRuning = true; //��ʶΪ������
			return false;
		}
		else //������
		{
			return true;
		}
	}

	public synchronized void setCrawlerIsRuning(boolean crawlerIsRuning)
	{
		this.crawlerIsRuning = crawlerIsRuning;
	}

	/**
	 * ������������
	 * @param distinct ����
	 * @param kind ְҵ����
	 * @throws IOException 
	 * @return true��ʾ�����ɹ���false��ʾ��������Ѿ������У���������
	 */
	public boolean start(String distinct, String kind) throws IOException
	{
		boolean startFlag = false; //��ʶ���������ɹ����
		if(this.getCrawlerIsRuningStatus() == false) //����δ���У���������
		{
			startFlag = true;
			runStatusctrl.init(); //��ʼ����������״̬������
			runStatusctrl.open(); //���濪ʼ����
			int poolSize = 5; //�̳߳�����
			ProxyGetter.saveProxy(); //��ȡ���������
			if(ProxyManager.getProxyList() != null) //��ȡ����������ɹ�
			{
				ExecutorService pool = Executors.newFixedThreadPool(poolSize); //��������ΪpoolSize���̳߳�
				for(int i = 0; i < poolSize - 1; i++ )
				{
					//�ύʹ�ô�������������������̳߳�
					pool.submit(new StartRunnable(distinct, kind, runStatusctrl, true));
				}
				//�ύ��ʹ�ô����������������̳߳�
				pool.submit(new StartRunnable(distinct, kind, runStatusctrl, false));
				runStatusctrl.setThreadAmount(poolSize);
				pool.shutdown();			
			}
			else
			{
				runStatusctrl.setStatus("�����������ȡʧ�ܣ�������������");
				runStatusctrl.close();
			}
			this.setCrawlerIsRuning(false); //��������������
		}
		return startFlag;
	}
	
	/**
	 * ����������б�־����Ϊfalse��ֹͣ��������
	 */
	public void close()
	{
		//�����������б�־Ϊfalse
		runStatusctrl.close();
	}
	
	/**
	 * ������������״̬������
	 * @return ����״̬������
	 */
	public RunStatusCtrl getRunStatusCtrl()
	{
		return runStatusctrl;
	}
	
	public boolean getKinds() throws Exception
	{
		boolean flag = false;
		kindResolver = new KindResolver();
		List<String> kindList = kindResolver.getKind();
		if(!kindList.isEmpty())
		{
			for(String data: kindList)
			{
				JobKind jobKind = new JobKind();
				jobKind.setKind(data);
				JobKindDAOFactory.getJobKindDAOInstance().doCreate(jobKind);
			}
			flag = true;
		}
		return flag;
	}
	
	public boolean getDistricts() throws Exception
	{
		boolean flag = false;
		districtResolver = new DistrictResolver();
		List<String> districtList = districtResolver.getDistrict();
		if(!districtList.isEmpty())
		{
			for(String data: districtList)
			{
				District district = new District();
				district.setDistrict(data);
				DistrictDAOFactory.getDistrictDAOInstance().doCreate(district);
			}
			flag = true;
		}
		return flag;
	}
	
	/**
	 * �������
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public boolean checkPassword(String password) throws Exception
	{
		return PasswordManager.checkPassword(password);
	}
	
	/**
	 * �޸�����
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public boolean updatePassword(String password) throws Exception
	{
		return PasswordManager.updatePassword(password);
	}
}
