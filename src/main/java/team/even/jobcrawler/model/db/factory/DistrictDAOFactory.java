package team.even.jobcrawler.model.db.factory;

import java.sql.Connection;

import team.even.jobcrawler.model.db.dao.IDistrictDAO;
import team.even.jobcrawler.model.db.dao.impl.DistrictDAOimpl;
import team.even.jobcrawler.model.db.dbc.DatabaseConnection;

public class DistrictDAOFactory
{
	public IDistrictDAO getDistrictDAOInstance()
	{
		Connection conn = null;
		try
		{
			conn = new DatabaseConnection().getConnection();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return new DistrictDAOimpl(conn);
	}
}
