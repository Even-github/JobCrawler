package team.even.jobcrawler.model.db.vo;

public class JobTypes
{
	private String kind;
	private String workPlace;
	private int amount;
	
	public String getKind()
	{
		return kind;
	}
	public void setKind(String kind)
	{
		this.kind = kind;
	}
	public String getWorkPlace()
	{
		return workPlace;
	}
	public void setWorkPlace(String workPlace)
	{
		this.workPlace = workPlace;
	}
	public int getAmount()
	{
		return amount;
	}
	public void setAmount(int amount)
	{
		this.amount = amount;
	}
}
