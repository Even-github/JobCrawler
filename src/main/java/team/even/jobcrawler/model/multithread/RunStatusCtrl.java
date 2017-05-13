package team.even.jobcrawler.model.multithread;

/**
 * 控制爬虫的运行状态，isContinue=true时，爬虫持续运行，isContinue=false时，爬虫停止运行
 * @author 曾裕文
 *
 */
public class RunStatusCtrl
{
	private boolean isContinue;
	private int count; //记录添加的记录数量
	private String status; //字符串描述爬虫运行状态
	private static RunStatusCtrl ctrl = null;
	
	private RunStatusCtrl()
	{
		isContinue = true;
		count = 0;
		status = "爬虫正在初始化...";
	}

	public static RunStatusCtrl getInstance()
	{
		if(ctrl == null)
		{
			ctrl = new RunStatusCtrl();
		}
		return ctrl;
	}
	
	public void goOn()
	{
		isContinue = true;
	}
	
	public void stop()
	{
		isContinue = false;
	}
	
	public boolean getIsContinue()
	{
		return isContinue;
	}

	public int getCount()
	{
		return count;
	}

	public void countAdd()
	{
		count++;
	}
	
	public void setCount(int count)
	{
		this.count = count;
	}
	
	public String getStatus()
	{
		return status;
	}
	
	public void setStatus(String status)
	{
		this.status = status;
	}
}
