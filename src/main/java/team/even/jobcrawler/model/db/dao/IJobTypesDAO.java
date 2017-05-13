package team.even.jobcrawler.model.db.dao;

import java.util.List;

import team.even.jobcrawler.model.db.vo.JobTypes;

/**
 * 本接口定义数据库表jobtypes的操作方法，包括插入、删除、查询
 * @author 曾裕文
 *
 */
public interface IJobTypesDAO
{
	/**
	 * 向数据库表jobtypes插入一条数据
	 * @param jobTypes 被插入的数据
	 * @return 插入是否成功
	 */
	boolean doCreate(JobTypes jobTypes);
	
	/**
	 * 删除数据库表jobtypes中的指定记录
	 * @param kind 被删除记录的kind属性
	 * @param workPlace 被删除记录的workPlace
	 * @return 删除是否成功
	 */
	boolean doDelete(String kind, String workPlace);
	
	/**
	 * 查找数据库表jobtypes中的所有记录
	 * @return 所有记录
	 */
	List<JobTypes> findAll();
	
	/**
	 * 通过属性kind和workPlace查找记录
	 * @return 查找到的记录
	 */
	List<JobTypes> findByKindandWorkPlace(String kind, String workPlace);
}
