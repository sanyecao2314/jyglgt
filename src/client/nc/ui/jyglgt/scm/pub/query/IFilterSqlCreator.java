package nc.ui.jyglgt.scm.pub.query;

/**
 * �°��ѯģ����ע��ĳ����ѯ���� sql ���Ĵ�������
 * <p>�°��ѯģ���У����ĳ����ѯ������sql���Ƚϸ��ӣ��޷���Ĭ�ϵĹ��򴴽���
 * �����ע��ʹ�ô� sql �����ӿڣ�ʹ�÷������£�
 * <pre>
 * qryDlg.regsterCustomSqlCreator("bd_purorg.mobile", new IFilterSqlCreator(){
 *     public String getSql(String value) {
 *         return "realColumnname = bd_purorg.mobile and bd_purorg.mobile = " + value;
 *     }
 * });
 * </pre>
 * ��������ʵ�ָù��ܣ�fieldcodeΪbd_purorg.mobile�Ĳ�ѯ�������յõ���sql���Ϊ
 * <pre>realColumnname = bd_purorg.mobile and bd_purorg.mobile = 'value'��</pre>
 * 
 * @author ��ǿ��
 * @since 2008-11-5
 */
public interface IFilterSqlCreator {
	/**
	 * �����ֶε�ֵ���� sql Ƭ�Ρ�
	 * <p><b>ע�⣺ֻ��һ���ֶε� sql Ƭ�Σ�������Ƭ��֮ǰ���� "and" �� "or" �ȵ������ַ�</b>
	 * 
	 * @param value ��ѯ������ֵ
	 * @return ���ɵĵ�ǰ��ѯ�ֶε� sql Ƭ��
	 */
	String getSql(String value);
}
