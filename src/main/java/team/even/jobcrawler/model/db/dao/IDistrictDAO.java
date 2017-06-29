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
	 * @throws Exception 
	 */
	boolean doCreate(District district) throws Exception;
	
	/**
	 * ��ѯdistrict�е���������
	 * @return �����������
	 * @throws Exception 
	 */
	List<District> findAll() throws Exception;
}
