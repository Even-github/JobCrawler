package team.even.jobcrawler.model.fileclear;

import java.io.File;

/**
 * �ļ������࣬�����������ص�json�ļ���html�ļ�
 * @author ��ԣ��
 *
 */
public class FileClear
{
	public static void clearFile(String filePath)
	{
		File file = new File(filePath);
		if(file.exists()) //�ļ�����
		{
			if(file.isDirectory()) //�ļ���
			{
				File[] fileList = file.listFiles();
				for(File childFile : fileList)
				{
					clearFile(childFile.getAbsolutePath());
				}
			}
			else //�ļ�
			{
				file.delete();
			}
		}
	}
}
