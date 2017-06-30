package team.even.jobcrawler.model.db.dao;

public interface IPasswordDAO
{
	/**
	 * ��ȡ����
	 * @return ���ػ�ȡ������
	 * @throws Exception 
	 */
	public String getPassword() throws Exception;
	
	/**
	 * ��������
	 * @throws Exception 
	 */
	public boolean updatePassword(String password) throws Exception;
}
