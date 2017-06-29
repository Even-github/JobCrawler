package team.even.jobcrawler.model.db.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import team.even.jobcrawler.model.db.vo.JobTypes;

public interface JobTypesMapper
{
	int insertJobTypes(JobTypes jobTypes);
	int deleteJobTypes(@Param("kind")String kind, @Param("workPlace")String workPlace);
	List<JobTypes> selectAll();
	List<JobTypes> selectByKindandPlace(@Param("kind")String kind, @Param("workPlace")String workPlace);
}
