package team.even.jobcrawler.model.db.dao;

import java.util.List;

import team.even.jobcrawler.model.db.vo.JobKind;

/**
 * ���ӿڶ���jobkind��Ļ�����������������Ͳ�ѯ
 * @author ��ԣ��
 *
 */
public interface IJobKindDAO
{
	/**
	 * ��jobkind�����һ������
	 * @param jobKind �����������
	 * @return �Ƿ����ɹ�
	 */
	boolean doCreate(JobKind jobKind);
	
	/**
	 * ����jobkind���е���������
	 * @return ������������
	 */
	List<JobKind> findAll();
}
