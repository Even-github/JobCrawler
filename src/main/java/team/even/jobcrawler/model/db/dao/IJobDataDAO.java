package team.even.jobcrawler.model.db.dao;

import java.util.List;

import team.even.jobcrawler.model.db.vo.JobData;

/**
 * ���ӿڶ������ݿ��jobdata�Ĳ����������������롢ɾ��������
 * @author ��ԣ��
 *
 */
public interface IJobDataDAO
{
	/**
	 * ��jobdata�����һ����¼
	 * @param jobData �����������
	 * @return �����Ƿ�ɹ�
	 * @throws Exception 
	 */
	boolean doCreate(JobData jobData) throws Exception;

	/**
	 * ɾ������ָ���ļ�¼
	 * @param column �����е�����
	 * @param value �е�ֵ
	 * @return ɾ���Ƿ�ɹ�
	 * @throws Exception 
	 */
	boolean doDelete(String column, String value) throws Exception;

	/**
	 * ͨ��������kind��workPlaceָ��ɾ������������ɾ����¼
	 * @param kind ��ɾ����¼kind���Ե�ֵ
	 * @param workPlace ��ɾ����¼workPlace���Ե�ֵ
	 * @return ɾ���Ƿ�ɹ�
	 * @throws Exception 
	 */
	boolean doDeleteByKindandWorkPlace(String kind, String workPlace) throws Exception;
	
	/**
	 * ����jobdata���е���������
	 * @return ���е���������
	 * @throws Exception 
	 */
	List<JobData> findAll() throws Exception;
	
	/**
	 * ��������ָ����ֵ
	 * @param column �����е�����
	 * @param value �е�ֵ
	 * @return ��ѯ�Ľ��
	 * @throws Exception 
	 */
	List<JobData> find(String column, String value) throws Exception;
	
	/**
	 * ͨ������kind��workPlace���Ҽ�¼
	 * @param kind ָ����ѯ������kind��ֵ
	 * @param workPlace ָ����ѯ������workPlace��ֵ 
	 * @return ��ѯ�Ľ��
	 * @throws Exception 
	 */
	List<JobData> findByKindandWorkPlace(String kind, String workPlace) throws Exception;
}
