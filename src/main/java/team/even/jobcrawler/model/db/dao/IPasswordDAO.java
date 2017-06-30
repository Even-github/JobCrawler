package team.even.jobcrawler.model.db.dao;

public interface IPasswordDAO
{
	/**
	 * 获取密码
	 * @return 返回获取的密码
	 * @throws Exception 
	 */
	public String getPassword() throws Exception;
	
	/**
	 * 设置密码
	 * @throws Exception 
	 */
	public boolean updatePassword(String password) throws Exception;
}
