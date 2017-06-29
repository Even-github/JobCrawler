package team.even.jobcrawler.model.db.mybatis.mapper;

import java.util.List;

import team.even.jobcrawler.model.db.vo.JobKind;

public interface JobKindMapper
{
	int insertJobKind(JobKind jobKind);
	List<JobKind> selectAll();
}
