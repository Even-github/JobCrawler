package team.even.jobcrawler.model.db.factory;

import java.sql.Connection;

import team.even.jobcrawler.model.db.dao.IJobTypesDAO;
import team.even.jobcrawler.model.db.dao.impl.JobTypesDAOimpl;
import team.even.jobcrawler.model.db.dbc.DatabaseConnection;

public class JobTypesDAOFactory
{
	public IJobTypesDAO getJobTypesDAOInstance()
	{
		Connection conn = null;
		try
		{
			conn = new DatabaseConnection().getConnection();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return new JobTypesDAOimpl(conn);
	}
}
