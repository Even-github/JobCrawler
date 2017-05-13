package team.even.jobcrawler.model.filepath;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FilePath类用户保存索引页面文件路径和内容页面文件路径
 * @author 曾裕文
 *
 */
public class FilePath
{
	public static String DOWNLOADPATH = getAbsoluteRootPath() + "Download_resources";
	//json文件路径
	public static String JSONPATH = getAbsoluteRootPath() + "Download_resources/json";
	//内容页面文件路径
	public static String CONTENTHTMLPATH = getAbsoluteRootPath() + "Download_resources/contentHtml";
	
	public static String getAbsoluteRootPath()
	{
		String path1 = FilePath.class.getResource("/").getPath();
		Pattern pattern1 = Pattern.compile("WEB-INF/classes/");
		Matcher matcher1 = pattern1.matcher(path1);
		String path2 = matcher1.replaceAll("");
		Pattern pattern2 = Pattern.compile("%20");
		Matcher matcher2 = pattern2.matcher(path2);
		String path3 =  matcher2.replaceAll(" ");
		Pattern pattern3 = Pattern.compile("^/");
		Matcher matcher3 = pattern3.matcher(path3);
		String absoluteRootPath = matcher3.replaceAll("");
		
		return absoluteRootPath;
	}
}
