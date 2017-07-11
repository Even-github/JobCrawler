package team.even.jobcrawler.model.dataAnalyzer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import team.even.jobcrawler.model.db.factory.JobDataDAOFactory;
import team.even.jobcrawler.model.db.vo.JobData;

/**
 * 数据分析器，对爬虫程序获取的信息进行分析，为生成报告提供数据支持
 * @author 曾裕文
 *
 */
public class DataAnalyzer
{
	/**
	 * 分析jobdata表中符合条件的月薪，统计各范围内的数量
	 * @param kind 职业种类条件
	 * @param district 地区条件
	 * @return 以范围/月薪的形式输出在各个范围内的数量统计
	 * @throws Exception 
	 */
	public Map<String, String> analyzeSalary(String kind, String district) throws Exception
	{
		Map<String, String> dataMap = new HashMap<String, String>();
		String[] scope = new String[10];
		int[] countScope = new int[10];
		scope[0] = "2k以下";
		scope[1] = "2k-4k";
		scope[2] = "4k-6k";
		scope[3] = "6k-8k";
		scope[4] = "8k-10k";
		scope[5] = "10k-15k";
		scope[6] = "15k-20k";
		scope[7] = "20k-30k";
		scope[8] = "30k-40k";
		scope[9] = "40k以上";

		List<JobData> dataList = JobDataDAOFactory
				.getJobDataDAOInstance()
				.findByKindandWorkPlace(kind, district);
		for(JobData data: dataList)
		{
			//通过正则表达式获取月薪的额度
			String salaryStr = data.getSalary();
			salaryStr = salaryStr.toLowerCase();
			Pattern pattern = Pattern.compile("[0-9]+k");
			Matcher matcher = pattern.matcher(salaryStr);
			String targetStr = null;
			if(matcher.find())
			{
				targetStr = salaryStr
						.substring(matcher.start(), matcher.end() - 1);
				int basicSalary = Integer.parseInt(targetStr);
				if(basicSalary < 2)
				{
					countScope[0]++;
				}
				else if(basicSalary < 4)
				{
					countScope[1]++;
				}
				else if(basicSalary < 6)
				{
					countScope[2]++;
				}
				else if(basicSalary < 8)
				{
					countScope[3]++;
				}
				else if(basicSalary < 10)
				{
					countScope[4]++;
				}
				else if(basicSalary < 15)
				{
					countScope[5]++;
				}
				else if(basicSalary < 20)
				{
					countScope[6]++;
				}
				else if(basicSalary < 30)
				{
					countScope[7]++;
				}
				else if(basicSalary < 40)
				{
					countScope[8]++;
				}
				else
				{
					countScope[9]++;
				}
			}
		}
		for(int i = 0; i < 10; i++)
		{
			dataMap.put(scope[i], String.valueOf(countScope[i]));
		}
		
		return dataMap;
	}
	
	/**
	 * 分析jobdata表中符合条件的经验要求，统计各范围内的数量
	 * @param kind 职业种类条件
	 * @param district 地区条件
	 * @return 以要求种类/数量的形式输出在各个范围内的数量统计
	 * @throws Exception 
	 */
	public Map<String, String> analyzeExp(String kind, String district) throws Exception
	{
		Map<String, String> dataMap = new HashMap<String, String>();
		String[] type = new String[5];
		int[] countType = new int[5];
		type[0] = "经验应届毕业生";
		type[1] = "经验1-3年";
		type[2] = "经验3-5年";
		type[3] = "经验5-10年";
		type[4] = "经验不限";
		List<JobData> dataList = JobDataDAOFactory
				.getJobDataDAOInstance()
				.findByKindandWorkPlace(kind, district);
		for(JobData data: dataList)
		{
			String expStr = data.getExperience();
			for(int i = 0; i < 5; i++)
			{
				if(expStr.equals(type[i]))
				{
					countType[i]++;
					break;
				}
			}
		}
		for(int i = 0; i < 5; i++)
		{
			dataMap.put(type[i], String.valueOf(countType[i]));
		}
		
		return dataMap;
	}
	
	/**
	 * 分析jobdata表中符合条件的学历要求，统计各范围内的数量
	 * @param kind 职业种类条件
	 * @param district 地区条件
	 * @return 以学历/数量的形式输出在各个范围内的数量统计
	 * @throws Exception 
	 */
	public Map<String, String> analyzeAcade(String kind, String district) throws Exception
	{
		Map<String, String> dataMap = new HashMap<String, String>();
		String[] type = new String[5];
		int[] countType = new int[5];
		type[0] = "大专及以上";
		type[1] = "本科及以上";
		type[2] = "硕士及以上";
		type[3] = "博士及以上";
		type[4] = "学历不限";
		List<JobData> dataList = JobDataDAOFactory
				.getJobDataDAOInstance()
				.findByKindandWorkPlace(kind, district);
		for(JobData data: dataList)
		{
			String acaStr = data.getAcademic();
			for(int i = 0; i < 5; i++)
			{
				if(acaStr.equals(type[i]))
				{
					countType[i]++;
					break;
				}
			}
		}
		for(int i = 0; i < 5; i++)
		{
			dataMap.put(type[i], String.valueOf(countType[i]));
		}
		
		return dataMap;
	}
}
