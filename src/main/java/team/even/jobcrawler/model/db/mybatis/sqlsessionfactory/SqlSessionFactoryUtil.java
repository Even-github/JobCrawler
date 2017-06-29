package team.even.jobcrawler.model.db.mybatis.sqlsessionfactory;

import java.io.IOException;
import java.io.InputStream;

import javax.annotation.Resource;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class SqlSessionFactoryUtil
{
	private static SqlSessionFactory sqlSessionFactory;
	
	public static SqlSessionFactory getSqlSessionFactory() throws Exception
	{
		if(sqlSessionFactory == null)
		{
			String resource = "team/even/jobcrawler/model/db/mybatis/config/mybatis-configuration.xml";
			InputStream inputStream = Resources.getResourceAsStream(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		}
		
		return sqlSessionFactory;
	}
	
	public static SqlSession newSqlSession(boolean flag) throws Exception
	{
		return SqlSessionFactoryUtil.getSqlSessionFactory().openSession(flag);
	}
}
