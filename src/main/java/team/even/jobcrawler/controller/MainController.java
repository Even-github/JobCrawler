package team.even.jobcrawler.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import team.even.jobcrawler.model.dataAnalyzer.DataAnalyzer;
import team.even.jobcrawler.model.db.factory.DistrictDAOFactory;
import team.even.jobcrawler.model.db.factory.JobDataDAOFactory;
import team.even.jobcrawler.model.db.factory.JobKindDAOFactory;
import team.even.jobcrawler.model.db.factory.JobTypesDAOFactory;
import team.even.jobcrawler.model.db.vo.District;
import team.even.jobcrawler.model.db.vo.JobData;
import team.even.jobcrawler.model.db.vo.JobKind;
import team.even.jobcrawler.model.db.vo.JobTypes;
import team.even.jobcrawler.model.service.Service;

@Controller
@RequestMapping("/")
public class MainController
{
	@RequestMapping("/index")
	public String index()
	{
		return "index";
	}

	@RequestMapping("/savedContent")
	public @ResponseBody List<JobTypes> appearJobTypes() throws Exception
	{
		List<JobTypes> list = JobTypesDAOFactory
				.getJobTypesDAOInstance()
				.findAll();
		return list;
	}
	
	@RequestMapping("/getKind")
	public @ResponseBody List<JobKind> getKind() throws Exception
	{
		List<JobKind> kindList = JobKindDAOFactory
									.getJobKindDAOInstance()
									.findAll();
		return kindList;
	}
	
	@RequestMapping("/getDistrict")
	public @ResponseBody List<District> getDistrict() throws Exception
	{
		List<District>  districtList = DistrictDAOFactory
										.getDistrictDAOInstance()
										.findAll();
		return districtList;
	}

	@RequestMapping("/startCrawler")
	public @ResponseBody String startCrawler(@RequestParam(value = "kind")String kind,
			@RequestParam(value = "distinct")String distinct) throws Exception
	{
		List<JobTypes> list = JobTypesDAOFactory.getJobTypesDAOInstance()
				.findByKindandWorkPlace(kind, distinct);
		if(list.isEmpty())
		{
			Service service = Service.getInstance();
			service.start(distinct, kind);
			return "success";
		}
		else
		{
			return "failure";		
		}
	}
	
	@RequestMapping("/getCrawlerStatus")
	public @ResponseBody Map<String, String> getCrawlerStatus()
	{
		Map<String, String> map = new HashMap<String, String>();
		Service service =  Service.getInstance();
		String status = service.getRunStatusCtrl().getStatus();
		int count = service.getRunStatusCtrl().getCount();
		map.put("status", status);
		map.put("count", String.valueOf(count));
		return map;
	}
	
	@RequestMapping("/stopCrawler")
	public @ResponseBody String stopCrawler()
	{
		Service service = Service.getInstance();
		service.stop();
		String operation = "正在停止运行爬虫程序,请稍等...\n";
		return operation;
	}
	
	@RequestMapping("/displayData")
	public String displayData()
	{
		return "displayData";
	}
	
	@RequestMapping("/displayData/getData")
	public @ResponseBody List<JobData> display(@RequestParam("kind") String kind,
			@RequestParam("workPlace") String workPlace) throws Exception
	{
		List<JobData> list = JobDataDAOFactory.getJobDataDAOInstance()
				.findByKindandWorkPlace(kind, workPlace);
		return list;
	}
	
	@RequestMapping("/removeData")
	public @ResponseBody String removeData(@RequestParam(value="kind")String kind,
			@RequestParam(value="workPlace")String workPlace) throws Exception
	{
		boolean flag = true;
		boolean flag1 = JobDataDAOFactory
				.getJobDataDAOInstance()
				.doDeleteByKindandWorkPlace(kind, workPlace);
		boolean flag2 = JobTypesDAOFactory
				.getJobTypesDAOInstance()
				.doDelete(kind, workPlace);
		if(flag1 == true && flag2 == true)
		{
			return String.valueOf(true);
		}
		else
		{
			return String.valueOf(false);
		}
	}
	
	@RequestMapping("/report")
	public String generateReport()
	{
		return "report";
	}
	
	@RequestMapping("/report/getSalaryData")
	public @ResponseBody Map<String, String> getSalaryData(
			@RequestParam(value = "kind")String kind,
			@RequestParam(value="district")String district) throws Exception
	{
		Map<String, String> dataMap = new HashMap<String, String>();
		DataAnalyzer analyzer = new DataAnalyzer();
		dataMap = analyzer.analyzeSalary(kind, district);
		
		return dataMap;
	}
	
	@RequestMapping("/report/getExpData")
	public @ResponseBody Map<String, String> getExpData(
			@RequestParam(value = "kind")String kind,
			@RequestParam(value="district")String district) throws Exception
	{
		Map<String, String> dataMap = new HashMap<String, String>();
		DataAnalyzer analyzer = new DataAnalyzer();
		dataMap = analyzer.analyzeExp(kind, district);
		return dataMap;
	}
	
	@RequestMapping("/report/getAcadeData")
	public @ResponseBody Map<String, String> getAcadeData(
			@RequestParam(value = "kind")String kind,
			@RequestParam(value="district")String district) throws Exception
	{
		Map<String, String> dataMap = new HashMap<String, String>();
		DataAnalyzer analyzer = new DataAnalyzer();
		dataMap = analyzer.analyzeAcade(kind, district);
		return dataMap;
	}
}
