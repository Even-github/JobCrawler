package team.even.jobcrawler.model.htmlresolver.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import team.even.jobcrawler.model.htmlresolver.IDistrictResolver;

public class DistrictResolver implements IDistrictResolver
{

	@Override
	public List<String> getDistrict()
	{
		List<String> dataList = new ArrayList<String>();
		String url = "https://www.lagou.com/jobs/allCity.html?keyword=C%2B%2B&px=default&city=%E5%85%A8%E5%9B%BD&positionNum=500+&companyNum=0&isCompanySelected=false&labelWords=";
		try
		{
			Document doc = Jsoup.connect(url).get();
			Elements districtLinks = doc.select("a[href~=((zhaopin/)$)]");
			for(Element e: districtLinks)
			{
				dataList.add(e.text());
			}
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		return dataList;
	}

}
