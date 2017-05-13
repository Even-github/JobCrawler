package team.even.jobcrawler.model.filepath;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * FilePath���û���������ҳ���ļ�·��������ҳ���ļ�·��
 * @author ��ԣ��
 *
 */
public class FilePath
{
	public static String DOWNLOADPATH = getAbsoluteRootPath() + "Download_resources";
	//json�ļ�·��
	public static String JSONPATH = getAbsoluteRootPath() + "Download_resources/json";
	//����ҳ���ļ�·��
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
