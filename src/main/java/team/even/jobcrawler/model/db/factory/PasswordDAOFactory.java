package team.even.jobcrawler.model.db.factory;

import team.even.jobcrawler.model.db.dao.IPasswordDAO;
import team.even.jobcrawler.model.db.dao.impl.PasswordDAOimpl;

public class PasswordDAOFactory
{
	public static IPasswordDAO getPasswordDAOInstance()
	{
		return new PasswordDAOimpl();
	}
}
