package team.even.jobcrawler.model.htmlresolver.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import team.even.jobcrawler.model.htmlresolver.IKindResolver;

public class KindResolver implements IKindResolver
{
	@Override
	public List<String> getKind()
	{
		List<String> jobList = new ArrayList<String>();
		try
		{
			Document doc = Jsoup.connect("https://www.lagou.com/").get();
			Elements jobLinks = doc.select("a[data-lg-tj-id~=(4[A-Z]00)]");
			for(Element e: jobLinks)
			{
				jobList.add(e.text());
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return jobList;
	}
}
