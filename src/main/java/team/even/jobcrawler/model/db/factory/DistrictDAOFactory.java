package team.even.jobcrawler.model.db.factory;

import java.sql.Connection;

import team.even.jobcrawler.model.db.dao.IDistrictDAO;
import team.even.jobcrawler.model.db.dao.impl.DistrictDAOimpl;

public class DistrictDAOFactory
{
	public static IDistrictDAO getDistrictDAOInstance()
	{
		return new DistrictDAOimpl();
	}
}
