package team.even.jobcrawler.model.htmlresolver;

import java.util.Map;

/**
 * ҳ�����ݽ������ӿ�
 * @author ��ԣ��
 *
 */
public interface IContentResolver
{
	/**
	 * ��ȡ��Ƹҳ�������Ϣ
	 * @param fileName ������html�ļ�
	 * @return ��ȡ����Ƹ��Ϣ
	 */
	Map<String, String> getContent(String fileName);
	
	/**
	 * �ַ��������������˵�����Ҫ���ַ���
	 * @param str ��Ҫ�����˵��ַ���
	 * @return ���˺�õ����ַ���
	 */
	String strFilter(String str);
}
