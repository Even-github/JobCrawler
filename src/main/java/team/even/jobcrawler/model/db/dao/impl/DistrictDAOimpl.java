package team.even.jobcrawler.model.db.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import team.even.jobcrawler.model.db.dao.IDistrictDAO;
import team.even.jobcrawler.model.db.mybatis.mapper.DistrictMapper;
import team.even.jobcrawler.model.db.mybatis.sqlsessionfactory.SqlSessionFactoryUtil;
import team.even.jobcrawler.model.db.vo.District;
import team.even.jobcrawler.model.db.vo.JobKind;

public class DistrictDAOimpl implements IDistrictDAO
{	
	@Override
	public boolean doCreate(District district) throws Exception
	{
		boolean flag = false;
		SqlSession sqlSession = SqlSessionFactoryUtil.newSqlSession(true);
		try
		{
			DistrictMapper mapper = sqlSession.getMapper(DistrictMapper.class);
			if(mapper.insertDistrict(district) > 0);
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
	public List<District> findAll() throws Exception
	{
		List<District> dataList = new ArrayList<District>();
		SqlSession sqlSession = SqlSessionFactoryUtil.newSqlSession(true);
		try
		{
			DistrictMapper mapper = sqlSession.getMapper(DistrictMapper.class);
			dataList = mapper.selectAll();
		}
		finally
		{
			sqlSession.close();
		}
		return dataList;
	}
	
}
