package team.even.jobcrawler.model.db.factory;

import java.sql.Connection;

import team.even.jobcrawler.model.db.dao.IJobKindDAO;
import team.even.jobcrawler.model.db.dao.impl.JobKindDAOimpl;

public class JobKindDAOFactory
{
	public static IJobKindDAO getJobKindDAOInstance()
	{
		return new JobKindDAOimpl();
	}
}
