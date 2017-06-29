package team.even.jobcrawler.model.db.dao;

import java.util.List;

import team.even.jobcrawler.model.db.vo.JobTypes;

/**
 * ���ӿڶ������ݿ��jobtypes�Ĳ����������������롢ɾ������ѯ
 * @author ��ԣ��
 *
 */
public interface IJobTypesDAO
{
	/**
	 * �����ݿ��jobtypes����һ������
	 * @param jobTypes �����������
	 * @return �����Ƿ�ɹ�
	 * @throws Exception 
	 */
	boolean doCreate(JobTypes jobTypes) throws Exception;
	
	/**
	 * ɾ�����ݿ��jobtypes�е�ָ����¼
	 * @param kind ��ɾ����¼��kind����
	 * @param workPlace ��ɾ����¼��workPlace
	 * @return ɾ���Ƿ�ɹ�
	 * @throws Exception 
	 */
	boolean doDelete(String kind, String workPlace) throws Exception;
	
	/**
	 * �������ݿ��jobtypes�е����м�¼
	 * @return ���м�¼
	 * @throws Exception 
	 */
	List<JobTypes> findAll() throws Exception;
	
	/**
	 * ͨ������kind��workPlace���Ҽ�¼
	 * @return ���ҵ��ļ�¼
	 * @throws Exception 
	 */
	List<JobTypes> findByKindandWorkPlace(String kind, String workPlace) throws Exception;
}
