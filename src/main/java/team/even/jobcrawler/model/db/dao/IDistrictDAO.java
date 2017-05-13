package team.even.jobcrawler.model.db.dao;

import java.util.List;

import team.even.jobcrawler.model.db.vo.District;

/**
 * �˽ӿڶ���district��Ļ���������������ѯ������
 * @author ��ԣ��
 *
 */
public interface IDistrictDAO
{
	/**
	 * ��district�����һ������
	 * @param district �����������
	 * @return �Ƿ����ɹ�
	 */
	boolean doCreate(District district);
	
	/**
	 * ��ѯdistrict�е���������
	 * @return �����������
	 */
	List<District> findAll();
}
