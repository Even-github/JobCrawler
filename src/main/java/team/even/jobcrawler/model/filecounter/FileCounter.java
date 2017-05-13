package team.even.jobcrawler.model.filecounter;

import java.io.File;

/**
 * �ļ�������������ָ���ļ����µ��ļ�����
 * @author ��ԣ��
 *
 */
public class FileCounter
{
	public int getFileNum(String url)
	{
		File file = new File(url);
		if(file.exists())
		{
			int num = file.list().length;
			return num;			
		}
		else
		{
			return 0;
		}
	}
}
