package nc.ui.gt.refpub;

import nc.ui.bd.ref.AbstractRefModel;

/**
 * @功能：业务类型参照
 * @作者：施坤
 * @时间：2011-6-24
 */
public class BusitypeRef extends AbstractRefModel {
	/**
	* 构造函数
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
		return new String[]{"业务类型主键","业务名称"};
	}
	public String getRefTitle() {
	    return "业务类型参照";
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
