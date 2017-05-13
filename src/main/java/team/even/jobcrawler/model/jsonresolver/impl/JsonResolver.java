package team.even.jobcrawler.model.jsonresolver.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import team.even.jobcrawler.model.jsonresolver.IJsonResolver;

public class JsonResolver implements IJsonResolver
{
	@Override
	public List<String> getUrl(String fileName)
	{
		List<String> urlList = new ArrayList<String>();
		if(fileName != null)
		{	
			BufferedReader reader = null;
			try
			{
				reader = new BufferedReader(
									new InputStreamReader(
								new FileInputStream(fileName), "UTF-8"));
			} catch (UnsupportedEncodingException e)
			{
				e.printStackTrace();
			} catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			StringBuilder content = new StringBuilder();
			String line = "";
			try
			{
				while((line = reader.readLine()) != null)
				{
					content.append(line);
				}
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			//˫��ƥ���ȡ��Ƹ��Ϣ�ı�ţ��������Ƕ��urlģ����������
			Pattern p1 = Pattern.compile("\"positionId\":\\d*");
			Matcher m1 = p1.matcher(content.toString());
			Pattern p2 = Pattern.compile("\\d+");
			while(m1.find())
			{
				Matcher m2 = p2.matcher(m1.group());
				if(m2.find())
				{
					String result = m2.group();
					String url = "https://www.lagou.com/jobs/"
							+ result  //��ƥ�������Ƕ�뵽urlģ����
							+ ".html";
					urlList.add(url);
				}
			}
		}
		return urlList;
	}
}
