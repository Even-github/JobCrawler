package team.even.jobcrawler.model.db.dao.impl;

import org.apache.ibatis.session.SqlSession;

import team.even.jobcrawler.model.db.dao.IPasswordDAO;
import team.even.jobcrawler.model.db.mybatis.mapper.JobKindMapper;
import team.even.jobcrawler.model.db.mybatis.mapper.PasswordMapper;
import team.even.jobcrawler.model.db.mybatis.sqlsessionfactory.SqlSessionFactoryUtil;

public class PasswordDAOimpl implements IPasswordDAO
{

	@Override
	public String getPassword() throws Exception
	{
		String password = null;
		SqlSession sqlSession = SqlSessionFactoryUtil.newSqlSession(true);
		try
		{
			PasswordMapper mapper = sqlSession.getMapper(PasswordMapper.class);
			password = mapper.selectPassword();
		}
		finally
		{
			sqlSession.close();
		}
		return password;
	}

	@Override
	public boolean updatePassword(String password) throws Exception
	{
		boolean flag = false;
		SqlSession sqlSession = SqlSessionFactoryUtil.newSqlSession(true);
		try
		{
			PasswordMapper mapper = sqlSession.getMapper(PasswordMapper.class);
			if(mapper.updatePassword(password) > 0)
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
