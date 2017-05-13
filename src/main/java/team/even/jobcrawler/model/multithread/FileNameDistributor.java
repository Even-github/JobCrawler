package team.even.jobcrawler.model.multithread;

import team.even.jobcrawler.model.filecounter.FileCounter;
import team.even.jobcrawler.model.filepath.FilePath;

/**
 * �ļ��������������ļ�����������߳��е�ContentResolver��Ϊ�ļ��������н�һ������
 * @author ��ԣ��
 *
 */
public class FileNameDistributor
{
	private volatile int contentPageNum;
	
	public FileNameDistributor()
	{
		//��ȡ����ҳ���ļ��е�����
		contentPageNum = new FileCounter().getFileNum(FilePath.CONTENTHTMLPATH);
	}
	
	/**
	 * ��ȡ�ļ������̰߳�ȫ
	 * @return �ļ�����������Ϻ󣬷���null
	 */
	public synchronized String getFileName()
	{
		String fileName = null;
		if(contentPageNum >= 1)
		{
			fileName = FilePath.CONTENTHTMLPATH + 
					"/contentPage" + contentPageNum + ".html";
			contentPageNum--;
		}
		return fileName;
	}
}
