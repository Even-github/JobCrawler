package team.even.jobcrawler.model.multithread;

import org.apache.log4j.Logger;

import team.even.jobcrawler.model.db.factory.JobTypesDAOFactory;
import team.even.jobcrawler.model.db.vo.JobTypes;

/**
 * 控制爬虫的运行状态，isContinue=true时，爬虫持续运行，isContinue=false时，爬虫停止运行
 * @author 曾裕文
 *
 */
public class RunStatusCtrl
{
	private boolean isContinue; //爬虫程序运行状态标识，true表示继续运行，false标识停止运行，所有线程都会循环检测此标识
	private volatile int threadAmount; //爬虫运行的线程总数
	private volatile int finishedthreadAmount; //已经结束的线程数量
	private volatile int count; //记录添加的记录数量
	private volatile int page; //记录已经爬取的json数量
	private volatile String status; //字符串描述爬虫运行状态
	private static RunStatusCtrl ctrl = null;
	private static Logger logger = Logger.getLogger(RunStatusCtrl.class);
	
	private RunStatusCtrl()
	{
		this.init();
	}

	public static RunStatusCtrl getInstance()
	{
		if(ctrl == null)
		{
			ctrl = new RunStatusCtrl();
		}
		return ctrl;
	}
	
	/**
	 * 初始化爬虫运行状态控制器
	 */
	public void init()
	{
		isContinue = false;
		threadAmount = 0;
		finishedthreadAmount = 0;
		count = 0;
		page = 0;
		status = "爬虫正在初始化...";
		logger.info("爬虫正在初始化...");
	}
	
	public synchronized int getThreadAmount()
	{
		return threadAmount;
	}

	public synchronized void setThreadAmount(int threadAmount)
	{
		this.threadAmount = threadAmount;
	}

	/**
	 * 将爬虫运行的标识设置为true，即启动爬虫
	 */
	public synchronized void open()
	{
		isContinue = true;
	}
	
	/**
	 * 将爬虫程序的运行标识设置为false，表示爬虫程序将被关闭
	 */
	public synchronized void close()
	{
		isContinue = false;
	}
	
	/**
	 * 线程调用此方法表示线程任务结束
	 */
	public synchronized void stop()
	{
		finishedthreadAmount++;
		status = "线程" + finishedthreadAmount + "结束...";
		logger.info(status);
	}
	
	/**
	 * 判断所有线程的任务都已经结束
	 * @param threadAmount 线程总数
	 * @return true表示所有线程的任务都已经结束，false表示还有线程正在运行
	 */
	public synchronized boolean isFinish()
	{
		if(finishedthreadAmount == threadAmount) //已经结束的线程数量等于线程总数
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public synchronized boolean getIsContinue()
	{
		return isContinue;
	}

	public synchronized int getCount()
	{
		return count;
	}

	public synchronized void setCount(int count)
	{
		this.count = count;
	}
	
	/**
	 * 爬取的信息条数加1
	 */
	public synchronized void countAdd()
	{
		count++;
	}
	
	public synchronized int getPage()
	{
		return page;
	}

	public synchronized void setPage(int page)
	{
		this.page = page;
	}
	
	/**
	 * 爬取的json数量加1
	 */
	public synchronized void pageAdd()
	{
		page++;
	}
	
	/**
	 * 获取下一页json的页码，同时将已爬取页码数加1
	 * @return 下一页json的页码
	 */
	public synchronized int getNextPage()
	{
		this.pageAdd();
		return this.getPage();
	}

	public synchronized String getStatus()
	{
		return status;
	}
	
	public synchronized void setStatus(String status)
	{
		this.status = status;
	}
	
	/**
	 * 所有爬虫任务结束后，将爬取的招聘信息条数存入数据库
	 * @param kind
	 * @param distinct
	 * @param amount 爬取的招聘信息条数
	 * @throws Exception
	 */
	public void doCreateJobTypes(String kind, String distinct, int amount) throws Exception
	{
		JobTypes jobTypes = new JobTypes();
		jobTypes.setKind(kind);
		jobTypes.setWorkPlace(distinct);
		jobTypes.setAmount(amount);
		JobTypesDAOFactory.getJobTypesDAOInstance().doCreate(jobTypes);
	}
	
	/**
	 * 如果爬取招聘信息数量大于0，则把相应的信息存入数据库
	 */
	public void saveJobTypes(String kind, String distinct)
	{
		if(this.getCount() > 0)
		{
			try
			{
				doCreateJobTypes(kind, distinct, this.getCount());
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
