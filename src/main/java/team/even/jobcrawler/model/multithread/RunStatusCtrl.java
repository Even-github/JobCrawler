package team.even.jobcrawler.model.multithread;

import org.apache.log4j.Logger;

import team.even.jobcrawler.model.db.factory.JobTypesDAOFactory;
import team.even.jobcrawler.model.db.vo.JobTypes;

/**
 * �������������״̬��isContinue=trueʱ������������У�isContinue=falseʱ������ֹͣ����
 * @author ��ԣ��
 *
 */
public class RunStatusCtrl
{
	private boolean isContinue; //�����������״̬��ʶ��true��ʾ�������У�false��ʶֹͣ���У������̶߳���ѭ�����˱�ʶ
	private volatile int threadAmount; //�������е��߳�����
	private volatile int finishedthreadAmount; //�Ѿ��������߳�����
	private volatile int count; //��¼��ӵļ�¼����
	private volatile int page; //��¼�Ѿ���ȡ��json����
	private volatile String status; //�ַ���������������״̬
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
	 * ��ʼ����������״̬������
	 */
	public void init()
	{
		isContinue = false;
		threadAmount = 0;
		finishedthreadAmount = 0;
		count = 0;
		page = 0;
		status = "�������ڳ�ʼ��...";
		logger.info("�������ڳ�ʼ��...");
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
	 * ���������еı�ʶ����Ϊtrue������������
	 */
	public synchronized void open()
	{
		isContinue = true;
	}
	
	/**
	 * �������������б�ʶ����Ϊfalse����ʾ������򽫱��ر�
	 */
	public synchronized void close()
	{
		isContinue = false;
	}
	
	/**
	 * �̵߳��ô˷�����ʾ�߳��������
	 */
	public synchronized void stop()
	{
		finishedthreadAmount++;
		status = "�߳�" + finishedthreadAmount + "����...";
		logger.info(status);
	}
	
	/**
	 * �ж������̵߳������Ѿ�����
	 * @param threadAmount �߳�����
	 * @return true��ʾ�����̵߳������Ѿ�������false��ʾ�����߳���������
	 */
	public synchronized boolean isFinish()
	{
		if(finishedthreadAmount == threadAmount) //�Ѿ��������߳����������߳�����
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
	 * ��ȡ����Ϣ������1
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
	 * ��ȡ��json������1
	 */
	public synchronized void pageAdd()
	{
		page++;
	}
	
	/**
	 * ��ȡ��һҳjson��ҳ�룬ͬʱ������ȡҳ������1
	 * @return ��һҳjson��ҳ��
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
	 * ����������������󣬽���ȡ����Ƹ��Ϣ�����������ݿ�
	 * @param kind
	 * @param distinct
	 * @param amount ��ȡ����Ƹ��Ϣ����
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
	 * �����ȡ��Ƹ��Ϣ��������0�������Ӧ����Ϣ�������ݿ�
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
