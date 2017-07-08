package junitTestpkg;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import team.even.jobcrawler.model.proxy.ProxyGetter;

public class ProxyGetterTest
{
	private ProxyGetter getter;
	
	public ProxyGetterTest()
	{
		getter = new ProxyGetter();
	} 

	@Test
	public void test() throws IOException
	{
		List<Map<String, String>> list = getter.getProxy();
		for(Map map : list)
		{
			System.out.println(map.get("ip") + ":" + map.get("port"));
		}
	}

}
