package team.even.jobcrawler.model.db.factory;

import java.sql.Connection;

import team.even.jobcrawler.model.db.dao.IJobTypesDAO;
import team.even.jobcrawler.model.db.dao.impl.JobTypesDAOimpl;

public class JobTypesDAOFactory
{
	public static IJobTypesDAO getJobTypesDAOInstance()
	{
		return new JobTypesDAOimpl();
	}
}
