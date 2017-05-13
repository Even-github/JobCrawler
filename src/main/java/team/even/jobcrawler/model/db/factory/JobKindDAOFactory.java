package team.even.jobcrawler.model.db.factory;

import java.sql.Connection;

import team.even.jobcrawler.model.db.dao.IJobKindDAO;
import team.even.jobcrawler.model.db.dao.impl.JobKindDAOimpl;
import team.even.jobcrawler.model.db.dbc.DatabaseConnection;

public class JobKindDAOFactory
{
	public IJobKindDAO getJobKindDAOInstance()
	{
		Connection conn = null;
		try
		{
			conn = new DatabaseConnection().getConnection();
		} catch (Exception e)
		{
			e.printStackTrace();
		}

		return new JobKindDAOimpl(conn);
	}
}
