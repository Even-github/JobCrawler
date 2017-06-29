package team.even.jobcrawler.model.db.dao;

import java.util.List;

import team.even.jobcrawler.model.db.vo.JobKind;

/**
 * 本接口定义jobkind表的基本操作，包括插入和查询
 * @author 曾裕文
 *
 */
public interface IJobKindDAO
{
	/**
	 * 向jobkind表插入一条数据
	 * @param jobKind 被插入的数据
	 * @return 是否插入成功
	 * @throws Exception 
	 */
	boolean doCreate(JobKind jobKind) throws Exception;
	
	/**
	 * 查找jobkind表中的所有数据
	 * @return 返回所有数据
	 * @throws Exception 
	 */
	List<JobKind> findAll() throws Exception;
}
