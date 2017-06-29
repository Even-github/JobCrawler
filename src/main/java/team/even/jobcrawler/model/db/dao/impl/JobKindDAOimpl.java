package team.even.jobcrawler.model.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import team.even.jobcrawler.model.db.dao.IJobKindDAO;
import team.even.jobcrawler.model.db.mybatis.mapper.JobKindMapper;
import team.even.jobcrawler.model.db.mybatis.sqlsessionfactory.SqlSessionFactoryUtil;
import team.even.jobcrawler.model.db.vo.JobKind;

public class JobKindDAOimpl implements IJobKindDAO
{
	@Override
	public boolean doCreate(JobKind jobKind) throws Exception
	{
		boolean flag = false;
		SqlSession sqlSession = SqlSessionFactoryUtil.newSqlSession(true);
		try
		{
			JobKindMapper mapper = sqlSession.getMapper(JobKindMapper.class);
			if(mapper.insertJobKind(jobKind) > 0)
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
	public List<JobKind> findAll() throws Exception
	{
		List<JobKind> dataList = null;
		SqlSession sqlSession = SqlSessionFactoryUtil.newSqlSession(true);
		try
		{
			JobKindMapper mapper = sqlSession.getMapper(JobKindMapper.class);
			dataList = mapper.selectAll();
		}
		finally
		{
			sqlSession.close();
		}
		return dataList;
	}
}
