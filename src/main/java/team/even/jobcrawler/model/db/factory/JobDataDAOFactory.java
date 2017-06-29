package team.even.jobcrawler.model.db.factory;

import java.sql.Connection;

import team.even.jobcrawler.model.db.dao.IJobDataDAO;
import team.even.jobcrawler.model.db.dao.impl.JobDataDAOimpl;
import team.even.jobcrawler.model.db.dbc.DatabaseConnection;

/**
 * JobDataDAOimpl类的工厂方法
 * @author 曾裕文
 *
 */
public class JobDataDAOFactory
{
	public static IJobDataDAO getJobDataDAOInstance()
	{
		return new JobDataDAOimpl();
	}
}
