package team.even.jobcrawler.model.db.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import team.even.jobcrawler.model.db.vo.JobData;

public interface JobDataMapper
{
	int insertJobData(JobData jobData);
	int deleteJobData(@Param("column")String column, @Param("value")String value);
	List<JobData> selectAll();
	List<JobData> selectJobData(@Param("column")String column, @Param("value")String value);
	List<JobData> selectByKindandWorkPlace(@Param("kind")String kind, @Param("workPlace")String workPlace);
	int deleteByKindandWorkPlace(@Param("kind")String kind, @Param("workPlace")String workPlace);
}
