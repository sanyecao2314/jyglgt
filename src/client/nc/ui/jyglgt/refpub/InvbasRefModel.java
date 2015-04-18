package nc.ui.jyglgt.refpub;

import nc.ui.bd.ref.AbstractRefModel;
import nc.ui.pub.ClientEnvironment;

/***
 * @˵������������������ա�
 * @author ʩ��
 * @time 2014-9-23
 */
public class InvbasRefModel extends AbstractRefModel {

	public InvbasRefModel(){
		super();
	}

	@Override
	public String getPkFieldCode() {
		
		return "pk_invbasdoc";
	}

	@Override
	public String getRefTitle() {
		return "���������������";
	}

	@Override
	public java.lang.String  getTableName() {
		return "bd_invbasdoc" ;
	}

	@Override
	public String[] getFieldCode() {
		return new String[]{"invcode","invname","invspec","invtype","pk_invbasdoc"};
		
	}

	@Override
	public String[] getFieldName() {
		return new String[]{"�������","�������","���","�ͺ�","PK"};
	}

	@Override
	public String getWherePart() {
		String swhere = super.getWherePart();
		if(swhere==null ||swhere.trim().equals("")){
			return " 1=1 and nvl(dr,0)=0 ";
		}else{
			return swhere+" nvl(dr,0)=0  ";
		}
	}
	
	ClientEnvironment ce = ClientEnvironment.getInstance();
	
	@Override
	public int getDefaultFieldCount() {
		return getFieldCode().length-1;
	}
	
}
