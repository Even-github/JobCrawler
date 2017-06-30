package team.even.jobcrawler.model.passwordmanager;

import team.even.jobcrawler.model.db.dao.IPasswordDAO;
import team.even.jobcrawler.model.db.factory.PasswordDAOFactory;

public class PasswordManager
{
	/**
	 * 检查密码是否正确
	 * @param password 输入的密码
	 * @return true表示正确，false表示错误
	 * @throws Exception
	 */
	public static boolean checkPassword(String password) throws Exception
	{
		IPasswordDAO passwordDAO = PasswordDAOFactory.getPasswordDAOInstance();
		if(passwordDAO.getPassword().equals(password))		
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * 更新密码
	 * @param password 新密码
	 * @return 更新是否成功
	 * @throws Exception
	 */ 
	public static boolean updatePassword(String password) throws Exception
	{
		IPasswordDAO passwordDAO = PasswordDAOFactory.getPasswordDAOInstance();
		if(passwordDAO.updatePassword(password) == true)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
}
