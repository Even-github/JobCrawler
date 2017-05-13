package team.even.jobcrawler.model.dataAnalyzer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import team.even.jobcrawler.model.db.factory.JobDataDAOFactory;
import team.even.jobcrawler.model.db.vo.JobData;

/**
 * ���ݷ�����������������ȡ����Ϣ���з�����Ϊ���ɱ����ṩ����֧��
 * @author ��ԣ��
 *
 */
public class DataAnalyzer
{
	/**
	 * ����jobdata���з�����������н��ͳ�Ƹ���Χ�ڵ�����
	 * @param kind ְҵ��������
	 * @param district ��������
	 * @return �Է�Χ/��н����ʽ����ڸ�����Χ�ڵ�����ͳ��
	 */
	public Map<String, String> analyzeSalary(String kind, String district)
	{
		Map<String, String> dataMap = new HashMap<String, String>();
		String[] scope = new String[10];
		int[] countScope = new int[10];
		scope[0] = "2k����";
		scope[1] = "2k-4k";
		scope[2] = "4k-6k";
		scope[3] = "6k-8k";
		scope[4] = "8k-10k";
		scope[5] = "10k-15k";
		scope[6] = "15k-20k";
		scope[7] = "20k-30k";
		scope[8] = "30k-40k";
		scope[9] = "40k����";

		JobDataDAOFactory factory = new JobDataDAOFactory();
		List<JobData> dataList = factory
				.getJobDataDAOInstance()
				.findByKindandWorkPlace(kind, district);
		for(JobData data: dataList)
		{
			//ͨ��������ʽ��ȡ��н�Ķ��
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
	 * ����jobdata���з��������ľ���Ҫ��ͳ�Ƹ���Χ�ڵ�����
	 * @param kind ְҵ��������
	 * @param district ��������
	 * @return ��Ҫ������/��������ʽ����ڸ�����Χ�ڵ�����ͳ��
	 */
	public Map<String, String> analyzeExp(String kind, String district)
	{
		Map<String, String> dataMap = new HashMap<String, String>();
		String[] type = new String[5];
		int[] countType = new int[5];
		type[0] = "����Ӧ���ҵ��";
		type[1] = "����1-3��";
		type[2] = "����3-5��";
		type[3] = "����5-10��";
		type[4] = "���鲻��";
		JobDataDAOFactory factory = new JobDataDAOFactory();
		List<JobData> dataList = factory
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
	 * ����jobdata���з���������ѧ��Ҫ��ͳ�Ƹ���Χ�ڵ�����
	 * @param kind ְҵ��������
	 * @param district ��������
	 * @return ��ѧ��/��������ʽ����ڸ�����Χ�ڵ�����ͳ��
	 */
	public Map<String, String> analyzeAcade(String kind, String district)
	{
		Map<String, String> dataMap = new HashMap<String, String>();
		String[] type = new String[5];
		int[] countType = new int[5];
		type[0] = "��ר������";
		type[1] = "���Ƽ�����";
		type[2] = "˶ʿ������";
		type[3] = "��ʿ������";
		type[4] = "ѧ������";
		JobDataDAOFactory factory = new JobDataDAOFactory();
		List<JobData> dataList = factory
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
