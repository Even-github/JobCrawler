package team.even.jobcrawler.model.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import team.even.jobcrawler.model.db.dao.IJobTypesDAO;
import team.even.jobcrawler.model.db.mybatis.mapper.JobTypesMapper;
import team.even.jobcrawler.model.db.mybatis.sqlsessionfactory.SqlSessionFactoryUtil;
import team.even.jobcrawler.model.db.vo.JobTypes;

public class JobTypesDAOimpl implements IJobTypesDAO
{
	@Override
	public boolean doCreate(JobTypes jobTypes) throws Exception
	{
		boolean flag = false;
		SqlSession sqlSession = SqlSessionFactoryUtil.newSqlSession(true);
		try
		{
			JobTypesMapper mapper = sqlSession.getMapper(JobTypesMapper.class);
			if(mapper.insertJobTypes(jobTypes) > 0)
			{
				flag = true;
			}
		}
		finally
		{
			sqlSession.close();
		}
		return flag;
	}

	@Override
	public boolean doDelete(String kind, String workPlace) throws Exception
	{
		boolean flag = false;
		SqlSession sqlSession = SqlSessionFactoryUtil.newSqlSession(true);
		try
		{
			JobTypesMapper mapper = sqlSession.getMapper(JobTypesMapper.class);
			if(mapper.deleteJobTypes(kind, workPlace) > 0)
			{
				flag = true;
			}
		}
		finally
		{
			sqlSession.close();
		}
		return flag;
	}

	@Override
	public List<JobTypes> findAll() throws Exception
	{
		List<JobTypes> list = null;
		SqlSession sqlSession = SqlSessionFactoryUtil.newSqlSession(true);
		try
		{
			JobTypesMapper mapper = sqlSession.getMapper(JobTypesMapper.class);
			list = mapper.selectAll();
		}
		finally
		{
			sqlSession.close();
		}
		
		return list;
	}

	@Override
	public List<JobTypes> findByKindandWorkPlace(String kind, String workPlace) throws Exception
	{
		List<JobTypes> list = null;
		SqlSession sqlSession = SqlSessionFactoryUtil.newSqlSession(true);
		try
		{
			JobTypesMapper mapper = sqlSession.getMapper(JobTypesMapper.class);
			list = mapper.selectByKindandPlace(kind, workPlace);
		}
		finally
		{
			sqlSession.close();
		}
		return list;
	}
}
