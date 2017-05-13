package team.even.jobcrawler.model.db.vo;

public class JobData
{
	private int id;
	private String kind;
	private String job;
	private String recruitingUnit;
	private String salary;
	private String workPlace;
	private String experience;
	private String academic;
	private String workType;
	private String url;
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getKind()
	{
		return kind;
	}
	public void setKind(String kind)
	{
		this.kind = kind;
	}
	public String getJob()
	{
		return job;
	}
	public void setJob(String job)
	{
		this.job = job;
	}
	public String getRecruitingUnit()
	{
		return recruitingUnit;
	}
	public void setRecruitingUnit(String recruitingUnit)
	{
		this.recruitingUnit = recruitingUnit;
	}
	public String getSalary()
	{
		return salary;
	}
	public void setSalary(String salary)
	{
		this.salary = salary;
	}
	public String getWorkPlace()
	{
		return workPlace;
	}
	public void setWorkPlace(String workPlace)
	{
		this.workPlace = workPlace;
	}
	public String getExperience()
	{
		return experience;
	}
	public void setExperience(String experience)
	{
		this.experience = experience;
	}
	public String getAcademic()
	{
		return academic;
	}
	public void setAcademic(String academic)
	{
		this.academic = academic;
	}
	public String getWorkType()
	{
		return workType;
	}
	public void setWorkType(String workType)
	{
		this.workType = workType;
	}
	public String getUrl()
	{
		return url;
	}
	public void setUrl(String url)
	{
		this.url = url;
	}
}
