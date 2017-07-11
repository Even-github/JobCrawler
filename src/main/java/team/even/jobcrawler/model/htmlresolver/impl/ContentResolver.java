package team.even.jobcrawler.model.htmlresolver.impl;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import team.even.jobcrawler.model.htmlresolver.IContentResolver;

public class ContentResolver implements IContentResolver
{

	@Override
	public Map<String, String> getContent(String fileName)
	{
		if(fileName != null)
		{
			String recruitingUnit = "";
			String job = "";
			String salary = "";
			String workPlace = "";
			String experience = "";
			String academic = ""; 
			String workType = "";
			Map<String, String> infoMap = new HashMap<String, String>();
			
			File htmlFile = new File(fileName);
			Document doc = null;
			try
			{
				doc = Jsoup.parse(htmlFile, "UTF-8");
			} catch (IOException e)
			{
				e.printStackTrace();
			}	
			//获取class="job-name"的标签元素
			Element jobName = doc.getElementsByClass("job-name").first();
			if(jobName != null)
			{
				recruitingUnit = jobName.child(0).text(); //获取招聘单位名称
				job = jobName.child(1).text(); //获取招聘的职业名称				
			}
			//获取class="job_request"的标签元素
			Element jobRequest = doc.getElementsByClass("job_request").first();
			if(jobRequest != null)
			{
				Element p = jobRequest.child(0);
				if(p != null)
				{
					salary = p.child(0).text(); //获取薪资
					workPlace = p.child(1).text(); //获取工作地点
					experience = p.child(2).text(); //获取工作经验要求
					academic = p.child(3).text(); //获取学历要求
					workType = p.child(4).text(); //获取工作种类（全职/实习）
				}
			}
			//过滤信息
			recruitingUnit = strFilter(recruitingUnit);
			job = strFilter(job);
			salary = strFilter(salary);
			workPlace = strFilter(workPlace);
			experience = strFilter(experience);
			academic = strFilter(academic);
			workType = strFilter(workType);
			//将过滤后的信息添加到infoMap中
			infoMap.put("recruitingUnit", recruitingUnit);
			infoMap.put("job", job);
			infoMap.put("salary", salary);
			infoMap.put("workPlace", workPlace);
			infoMap.put("experience", experience);
			infoMap.put("academic", academic);
			infoMap.put("workType", workType);
			
			return infoMap;
		}
		else //文件名为空，无法找到文件
		{
			return null;
		}
	}

	@Override
	public String strFilter(String str)
	{
		//过滤掉字符串中所有的反斜杠"/"
		Pattern pattern1 = Pattern.compile("/");
		Matcher matcher1 = pattern1.matcher(str);
		String rs1 = matcher1.replaceAll("");
		//过滤掉字符串中所有的空格
		Pattern pattern2 = Pattern.compile(" ");
		Matcher matcher2 = pattern2.matcher(rs1);
		String rs2 = matcher2.replaceAll("");
		
		return rs2;
	}
}
