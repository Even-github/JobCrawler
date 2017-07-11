package team.even.jobcrawler.model.db.mybatis.mapper;

import org.apache.ibatis.annotations.Param;

public interface PasswordMapper
{
	String selectPassword();
	int updatePassword(@Param("password")String password);
}
