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
	private RunStatusCtrl runStatusctrl = null; //爬虫运行状态控制器
	private IKindResolver kindResolver = null; 
	private IDistrictResolver districtResolver = null;
	private volatile boolean crawlerIsRuning; //爬虫程序是否正在运行的标识，true正在运行，false表示未运行
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
	
	//运用单例模式使程序中最多存在一个Service对象
	public static Service getInstance()
	{
		if(service == null)
		{
			service = new Service();
		}
		return service;
	}
	
	/**
	 * 启动多个线程对contentHtml文件夹下的文件解析到
	 */
	public void resolverAllContent()
	{
		FileNameDistributor distributor = new FileNameDistributor();
		ResolveRunnable resolve = new ResolveRunnable(distributor);
		//创建线程池
		ExecutorService pool = Executors.newFixedThreadPool(5);
		pool.submit(resolve);
		pool.shutdown();
	}
	
	/**
	 * 获取爬虫运行状态，如果为false，则将其设置为true，并返回false;如果为true，则返回true
	 * @return
	 */
	public synchronized boolean getCrawlerIsRuningStatus()
	{
		if(crawlerIsRuning == false) //未启动
		{
			crawlerIsRuning = true; //标识为已启动
			return false;
		}
		else //已启动
		{
			return true;
		}
	}

	public synchronized void setCrawlerIsRuning(boolean crawlerIsRuning)
	{
		this.crawlerIsRuning = crawlerIsRuning;
	}

	/**
	 * 启动爬虫任务
	 * @param distinct 地区
	 * @param kind 职业种类
	 * @throws IOException 
	 * @return true表示启动成功，false表示爬虫程序已经在运行，不能启动
	 */
	public boolean start(String distinct, String kind) throws IOException
	{
		boolean startFlag = false; //标识爬虫启动成功与否
		if(this.getCrawlerIsRuningStatus() == false) //程序未运行，可以启动
		{
			startFlag = true;
			runStatusctrl.init(); //初始化爬虫运行状态控制器
			runStatusctrl.open(); //爬虫开始运行
			int poolSize = 5; //线程池容量
			ProxyGetter.saveProxy(); //获取代理服务器
			if(ProxyManager.getProxyList() != null) //获取代理服务器成功
			{
				ExecutorService pool = Executors.newFixedThreadPool(poolSize); //创建容量为poolSize的线程池
				for(int i = 0; i < poolSize - 1; i++ )
				{
					//提交使用代理服务器的爬虫任务到线程池
					pool.submit(new StartRunnable(distinct, kind, runStatusctrl, true));
				}
				//提交不使用代理服务的爬虫任务到线程池
				pool.submit(new StartRunnable(distinct, kind, runStatusctrl, false));
				runStatusctrl.setThreadAmount(poolSize);
				pool.shutdown();			
			}
			else
			{
				runStatusctrl.setStatus("代理服务器获取失败，爬虫程序结束！");
				runStatusctrl.close();
			}
			this.setCrawlerIsRuning(false); //爬虫程序结束运行
		}
		return startFlag;
	}
	
	/**
	 * 将爬虫的运行标志设置为false以停止运行爬虫
	 */
	public void close()
	{
		//设置爬虫运行标志为false
		runStatusctrl.close();
	}
	
	/**
	 * 返回爬虫运行状态控制器
	 * @return 爬虫状态控制器
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
	 * 检查密码
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public boolean checkPassword(String password) throws Exception
	{
		return PasswordManager.checkPassword(password);
	}
	
	/**
	 * 修改密码
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public boolean updatePassword(String password) throws Exception
	{
		return PasswordManager.updatePassword(password);
	}
}
