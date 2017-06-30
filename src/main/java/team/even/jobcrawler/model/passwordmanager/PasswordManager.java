package team.even.jobcrawler.model.passwordmanager;

import team.even.jobcrawler.model.db.dao.IPasswordDAO;
import team.even.jobcrawler.model.db.factory.PasswordDAOFactory;

public class PasswordManager
{
	/**
	 * ��������Ƿ���ȷ
	 * @param password ���������
	 * @return true��ʾ��ȷ��false��ʾ����
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
	 * ��������
	 * @param password ������
	 * @return �����Ƿ�ɹ�
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
