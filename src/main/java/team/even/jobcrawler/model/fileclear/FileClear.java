package team.even.jobcrawler.model.fileclear;

import java.io.File;

/**
 * 文件清理类，清理爬虫下载的json文件和html文件
 * @author 曾裕文
 *
 */
public class FileClear
{
	public static void clearFile(String filePath)
	{
		File file = new File(filePath);
		if(file.exists()) //文件存在
		{
			if(file.isDirectory()) //文件夹
			{
				File[] fileList = file.listFiles();
				for(File childFile : fileList)
				{
					clearFile(childFile.getAbsolutePath());
				}
			}
			else //文件
			{
				file.delete();
			}
		}
	}
}
