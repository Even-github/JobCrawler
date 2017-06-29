package team.even.jobcrawler.model.db.mybatis.mapper;

import java.util.List;

import team.even.jobcrawler.model.db.vo.District;

public interface DistrictMapper
{
	int insertDistrict(District district);
	List<District> selectAll();
}
