package team.even.jobcrawler.model.fileclear;

import java.io.File;

import org.apache.log4j.Logger;

/**
 * �ļ������࣬�����������ص�json�ļ���html�ļ�
 * @author ��ԣ��
 *
 */
public class FileClear
{
	private static Logger logger = Logger.getLogger(FileClear.class);
	
	public static void clearFile(String filePath)
	{
		logger.info("����ɾ�������ļ�...");
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
