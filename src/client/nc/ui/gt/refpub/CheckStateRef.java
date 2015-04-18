package nc.ui.gt.refpub;

import nc.ui.bd.ref.AbstractRefModel;

/**
 * @���ܣ������ȼ���������
 * @���ߣ�ʩ��
 * @ʱ�䣺2011-8-6
 */
public class CheckStateRef extends AbstractRefModel {
	/**
	* ���캯��
	 * @return 
	*/
	public  CheckStateRef(){
		super();		
	}
	@Override
	public String[] getFieldCode() {
		return new String[]{"ccheckstatecode","ccheckstatename","bqualified","ccheckstate_bid"};
	}
	@Override
	public String[] getFieldName() {
		return new String[]{"�����ȼ�����","�����ȼ�����","�Ƿ�ϸ�","����"};
	}
	public String getRefTitle() {
	    return "�����ȼ���������";
	}
	@Override
	public java.lang.String getTableName() {
		StringBuffer sb=new StringBuffer();
		sb.append("qc_checkstate_b");
		return sb.toString();
	}
	public String getPkFieldCode() {
	    return "ccheckstate_bid";
	}
	@Override
	public String getWherePart() {
		String swhere = super.getWherePart();
		if(swhere==null ||swhere.trim().equals("")){
			return " 1=1 and nvl(dr,0)=0 and nvl(bqualified,'N')='Y' and pk_corp='"+getPk_corp()+"'";
		}else{
			return swhere+" and nvl(dr,0)=0 and nvl(bqualified,'N')='Y' and pk_corp='"+getPk_corp()+"'";
		}
	}
}
