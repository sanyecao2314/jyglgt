package nc.ui.gt.refpub;

import nc.ui.bd.ref.AbstractRefModel;

/**
 * @功能：收款方式基本档案参照
 * @作者：施坤
 * @时间：2011-6-11
 */
public class BalanceWayRef extends AbstractRefModel {
	/**
	* 构造函数
	 * @return 
	*/
	public  BalanceWayRef(){
		super();		
	}
	@Override
	public String[] getFieldCode() {
		return new String[]{"balancecode","balancename","balancestatus","pk_balanceway"};
	}
	@Override
	public String[] getFieldName() {
		return new String[]{"收款方式编码","收款方式名称","是否封存","主键"};
	}
	public String getRefTitle() {
	    return "收款方式基本档案参照";
	}
	@Override
	public java.lang.String getTableName() {
		StringBuffer sb=new StringBuffer();
		sb.append("gt_balanceway");
		return sb.toString();
	}
	public String getPkFieldCode() {
	    return "pk_balanceway";
	}
	@Override
	public String getWherePart() {
		String swhere = super.getWherePart();
		if(swhere==null ||swhere.trim().equals("")){
			return " 1=1 and nvl(dr,0)=0 and nvl(balancestatus,'N')='N' and pk_corp='"+getPk_corp()+"'";
		}else{
			return swhere+" and nvl(dr,0)=0 and nvl(balancestatus,'N')='N' and pk_corp='"+getPk_corp()+"'";
		}
	}
}
