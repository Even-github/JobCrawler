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
			//��ȡclass="job-name"�ı�ǩԪ��
			Element jobName = doc.getElementsByClass("job-name").first();
			if(jobName != null)
			{
				recruitingUnit = jobName.child(0).text(); //��ȡ��Ƹ��λ����
				job = jobName.child(1).text(); //��ȡ��Ƹ��ְҵ����				
			}
			//��ȡclass="job_request"�ı�ǩԪ��
			Element jobRequest = doc.getElementsByClass("job_request").first();
			if(jobRequest != null)
			{
				Element p = jobRequest.child(0);
				if(p != null)
				{
					salary = p.child(0).text(); //��ȡн��
					workPlace = p.child(1).text(); //��ȡ�����ص�
					experience = p.child(2).text(); //��ȡ��������Ҫ��
					academic = p.child(3).text(); //��ȡѧ��Ҫ��
					workType = p.child(4).text(); //��ȡ�������ࣨȫְ/ʵϰ��
				}
			}
			//������Ϣ
			recruitingUnit = strFilter(recruitingUnit);
			job = strFilter(job);
			salary = strFilter(salary);
			workPlace = strFilter(workPlace);
			experience = strFilter(experience);
			academic = strFilter(academic);
			workType = strFilter(workType);
			//�����˺����Ϣ��ӵ�infoMap��
			infoMap.put("recruitingUnit", recruitingUnit);
			infoMap.put("job", job);
			infoMap.put("salary", salary);
			infoMap.put("workPlace", workPlace);
			infoMap.put("experience", experience);
			infoMap.put("academic", academic);
			infoMap.put("workType", workType);
			
			return infoMap;
		}
		else //�ļ���Ϊ�գ��޷��ҵ��ļ�
		{
			return null;
		}
	}

	@Override
	public String strFilter(String str)
	{
		//���˵��ַ��������еķ�б��"/"
		Pattern pattern1 = Pattern.compile("/");
		Matcher matcher1 = pattern1.matcher(str);
		String rs1 = matcher1.replaceAll("");
		//���˵��ַ��������еĿո�
		Pattern pattern2 = Pattern.compile(" ");
		Matcher matcher2 = pattern2.matcher(rs1);
		String rs2 = matcher2.replaceAll("");
		
		return rs2;
	}
}
