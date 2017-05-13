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
	 * 启动爬虫任务
	 * @param distinct 地区
	 * @param kind 职业种类
	 */
	public void start(String distinct, String kind)
	{
		ctrl.goOn(); //设置爬虫运行标志为true
		ctrl.setCount(0);
		Thread thread = new Thread(
				new StartRunnable(distinct, kind, ctrl));
		thread.start();
	}
	
	/**
	 * 将爬虫的运行标志设置为false以停止运行爬虫
	 */
	public void stop()
	{
		//设置爬虫运行标志为false
		ctrl.stop();
	}
	
	/**
	 * 返回爬虫运行状态控制器
	 * @return 爬虫状态控制器
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
