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
	 */
	boolean doCreate(JobTypes jobTypes);
	
	/**
	 * ɾ�����ݿ��jobtypes�е�ָ����¼
	 * @param kind ��ɾ����¼��kind����
	 * @param workPlace ��ɾ����¼��workPlace
	 * @return ɾ���Ƿ�ɹ�
	 */
	boolean doDelete(String kind, String workPlace);
	
	/**
	 * �������ݿ��jobtypes�е����м�¼
	 * @return ���м�¼
	 */
	List<JobTypes> findAll();
	
	/**
	 * ͨ������kind��workPlace���Ҽ�¼
	 * @return ���ҵ��ļ�¼
	 */
	List<JobTypes> findByKindandWorkPlace(String kind, String workPlace);
}
