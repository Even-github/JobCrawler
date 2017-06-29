package team.even.jobcrawler.model.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import team.even.jobcrawler.model.db.dao.IJobDataDAO;
import team.even.jobcrawler.model.db.mybatis.mapper.JobDataMapper;
import team.even.jobcrawler.model.db.mybatis.sqlsessionfactory.SqlSessionFactoryUtil;
import team.even.jobcrawler.model.db.vo.JobData;

/**
 * �ӿ�IJobDataDAO����ʵʵ����
 * @author ��ԣ��
 *
 */
public class JobDataDAOimpl implements IJobDataDAO
{
	@Override
	public boolean doCreate(JobData jobData) throws Exception
	{
		boolean flag = false; //�жϲ�����Ϣ�Ƿ�ɹ�
		SqlSession sqlSession = SqlSessionFactoryUtil.newSqlSession(true);
		try
		{
			JobDataMapper mapper = sqlSession.getMapper(JobDataMapper.class); 
			if(mapper.insertJobData(jobData) > 0)
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
	public boolean doDelete(String column, String value) throws Exception
	{
		boolean flag = false; //�ж�ɾ�������Ƿ�ɹ�
		SqlSession sqlSession = SqlSessionFactoryUtil.newSqlSession(true);
		try
		{
			JobDataMapper mapper = sqlSession.getMapper(JobDataMapper.class);
			if(mapper.deleteJobData(column, value) > 0)
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
	public List<JobData> findAll() throws Exception
	{
		List<JobData> dataList = null;
		SqlSession sqlSession = SqlSessionFactoryUtil.newSqlSession(true);
		try
		{
			JobDataMapper mapper = sqlSession.getMapper(JobDataMapper.class);
			dataList = mapper.selectAll();
		}
		finally
		{
			sqlSession.close();
		}
		return dataList;
	}

	@Override
	public List<JobData> find(String column, String value) throws Exception
	{
		List<JobData> dataList = null;
		SqlSession sqlSession = SqlSessionFactoryUtil.newSqlSession(true);
		try
		{
			JobDataMapper mapper = sqlSession.getMapper(JobDataMapper.class);
			dataList = mapper.selectJobData(column, value);
		}
		finally
		{
			sqlSession.close();
		}
		return dataList;
	}

	@Override
	public List<JobData> findByKindandWorkPlace(String kind, String workPlace) throws Exception
	{
		List<JobData> dataList = null;
		SqlSession sqlSession = SqlSessionFactoryUtil.newSqlSession(true);
		try
		{
			JobDataMapper mapper = sqlSession.getMapper(JobDataMapper.class);
			dataList = mapper.selectByKindandWorkPlace(kind, workPlace);
		}
		finally
		{
			sqlSession.close();
		}
		return dataList;
	}

	@Override
	public boolean doDeleteByKindandWorkPlace(String kind, String workPlace) throws Exception
	{
		boolean flag = false; //�ж�ɾ�������Ƿ�ɹ�
		SqlSession sqlSession = SqlSessionFactoryUtil.newSqlSession(true);
		try
		{
			JobDataMapper mapper = sqlSession.getMapper(JobDataMapper.class);
			if(mapper.deleteByKindandWorkPlace(kind, workPlace) > 0)
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
}
