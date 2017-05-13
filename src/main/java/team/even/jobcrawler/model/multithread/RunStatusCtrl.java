package team.even.jobcrawler.model.multithread;

/**
 * �������������״̬��isContinue=trueʱ������������У�isContinue=falseʱ������ֹͣ����
 * @author ��ԣ��
 *
 */
public class RunStatusCtrl
{
	private boolean isContinue;
	private int count; //��¼��ӵļ�¼����
	private String status; //�ַ���������������״̬
	private static RunStatusCtrl ctrl = null;
	
	private RunStatusCtrl()
	{
		isContinue = true;
		count = 0;
		status = "�������ڳ�ʼ��...";
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
