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
	 */
	boolean doCreate(JobData jobData);

	/**
	 * ɾ������ָ���ļ�¼
	 * @param column �����е�����
	 * @param value �е�ֵ
	 * @return ɾ���Ƿ�ɹ�
	 */
	boolean doDelete(String column, String value);

	/**
	 * ͨ��������kind��workPlaceָ��ɾ������������ɾ����¼
	 * @param kind ��ɾ����¼kind���Ե�ֵ
	 * @param workPlace ��ɾ����¼workPlace���Ե�ֵ
	 * @return ɾ���Ƿ�ɹ�
	 */
	boolean doDeleteByKindandWorkPlace(String kind, String workPlace);
	
	/**
	 * ����jobdata���е���������
	 * @return ���е���������
	 */
	List<JobData> findAll();
	
	/**
	 * ��������ָ����ֵ
	 * @param column �����е�����
	 * @param value �е�ֵ
	 * @return ��ѯ�Ľ��
	 */
	List<JobData> find(String column, String value);
	
	/**
	 * ͨ������kind��workPlace���Ҽ�¼
	 * @param kind ָ����ѯ������kind��ֵ
	 * @param workPlace ָ����ѯ������workPlace��ֵ 
	 * @return ��ѯ�Ľ��
	 */
	List<JobData> findByKindandWorkPlace(String kind, String workPlace);
}
