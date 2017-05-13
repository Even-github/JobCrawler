package team.even.jobcrawler.model.filecounter;

import java.io.File;

/**
 * 文件计数器，计算指定文件夹下的文件数量
 * @author 曾裕文
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
