package team.even.jobcrawler.model.db.dao;

import java.util.List;

import team.even.jobcrawler.model.db.vo.District;

/**
 * 此接口定义district表的基本操作，包括查询、插入
 * @author 曾裕文
 *
 */
public interface IDistrictDAO
{
	/**
	 * 向district表插入一条数据
	 * @param district 被插入的数据
	 * @return 是否插入成功
	 * @throws Exception 
	 */
	boolean doCreate(District district) throws Exception;
	
	/**
	 * 查询district中的所有数据
	 * @return 被插入的数据
	 * @throws Exception 
	 */
	List<District> findAll() throws Exception;
}
