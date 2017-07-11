package team.even.jobcrawler.model.multithread;

import team.even.jobcrawler.model.filecounter.FileCounter;
import team.even.jobcrawler.model.filepath.FilePath;

/**
 * 文件名分配器，将文件名分配给多线程中的ContentResolver作为文件参数进行进一步解析
 * @author 曾裕文
 *
 */
public class FileNameDistributor
{
	private volatile int contentPageNum;
	
	public FileNameDistributor()
	{
		//获取内容页面文件夹的数量
		contentPageNum = new FileCounter().getFileNum(FilePath.CONTENTHTMLPATH);
	}
	
	/**
	 * 获取文件名，线程安全
	 * @return 文件名，分配完毕后，返回null
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
