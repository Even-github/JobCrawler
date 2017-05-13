package team.even.jobcrawler.model.pageloader;

/*
 * ҳ���������ӿ�
 */
public interface IPageLoader
{
	/**
	 * ����downLoad����
	 * ����ָ��ҳ�棬������Ϊ�����ļ���һ������htmlҳ������
	 * @param url ָ�����ص�url
	 * @return �ļ����غ󱣴���ļ���
	 */
	String downLoad(String url);
	
	/**
	 * ����downLoad����
	 * ����ָ��ҳ�棬������Ϊ�����ļ���һ������json������
	 * @param distinct ��Ƹ����
	 * @param kind ��Ƹְҵ���ͣ��磺Java��C++��
	 * @param page ���󷵻صڼ�ҳ����
	 * @return
	 */
	String downLoad(String distinct, String kind, int page);
}
