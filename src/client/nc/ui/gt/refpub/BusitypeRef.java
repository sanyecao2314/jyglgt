package nc.ui.gt.refpub;

import nc.ui.bd.ref.AbstractRefModel;

/**
 * @���ܣ�ҵ�����Ͳ���
 * @���ߣ�ʩ��
 * @ʱ�䣺2011-6-24
 */
public class BusitypeRef extends AbstractRefModel {
	/**
	* ���캯��
	 * @return 
	*/
	public  BusitypeRef(){
		super();		
	}
	@Override
	public String[] getFieldCode() {
		return new String[]{"pk_busitype","businame"};
	}
	@Override
	public String[] getFieldName() {
		return new String[]{"ҵ����������","ҵ������"};
	}
	public String getRefTitle() {
	    return "ҵ�����Ͳ���";
	}
	@Override
	public java.lang.String getTableName() {
		StringBuffer sb=new StringBuffer();
		sb.append("bd_busitype");
		return sb.toString();
	}
	public String getPkFieldCode() {
	    return "pk_busitype";
	}
	@Override
	public String getWherePart() {
		String swhere = super.getWherePart();
		if(swhere==null ||swhere.trim().equals("")){
			return " 1=1 and nvl(dr,0)=0 and busiprop in(1,2)";
		}else{
			return swhere+" and nvl(dr,0)=0 and busiprop in(1,2) ";
		}
	}
}
