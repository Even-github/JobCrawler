package team.even.jobcrawler.model.db.dao;

import java.util.List;

import team.even.jobcrawler.model.db.vo.JobData;

/**
 * 本接口定义数据库表jobdata的操作方法，包括插入、删除、查找
 * @author 曾裕文
 *
 */
public interface IJobDataDAO
{
	/**
	 * 向jobdata表插入一条记录
	 * @param jobData 被插入的数据
	 * @return 插入是否成功
	 * @throws Exception 
	 */
	boolean doCreate(JobData jobData) throws Exception;

	/**
	 * 删除条件指定的记录
	 * @param column 条件中的列名
	 * @param value 列的值
	 * @return 删除是否成功
	 * @throws Exception 
	 */
	boolean doDelete(String column, String value) throws Exception;

	/**
	 * 通过表属性kind和workPlace指定删除条件，进行删除记录
	 * @param kind 被删除记录kind属性的值
	 * @param workPlace 被删除记录workPlace属性的值
	 * @return 删除是否成功
	 * @throws Exception 
	 */
	boolean doDeleteByKindandWorkPlace(String kind, String workPlace) throws Exception;
	
	/**
	 * 查找jobdata表中的所有数据
	 * @return 表中的所有数据
	 * @throws Exception 
	 */
	List<JobData> findAll() throws Exception;
	
	/**
	 * 查找条件指定的值
	 * @param column 条件中的列名
	 * @param value 列的值
	 * @return 查询的结果
	 * @throws Exception 
	 */
	List<JobData> find(String column, String value) throws Exception;
	
	/**
	 * 通过属性kind和workPlace查找记录
	 * @param kind 指定查询条件中kind的值
	 * @param workPlace 指定查询条件中workPlace的值 
	 * @return 查询的结果
	 * @throws Exception 
	 */
	List<JobData> findByKindandWorkPlace(String kind, String workPlace) throws Exception;
}
