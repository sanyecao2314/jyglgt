package nc.ui.gt.gs.refpub;

import nc.ui.bd.ref.AbstractRefModel;
import nc.vo.jyglgt.pub.Toolkits.IJyglgtBillStatus;

/**
 * @功能：结算价目表参照
 * @作者：施鹏
 * @时间：2011-8-18
 */
public class BalancePriceRef extends AbstractRefModel {
	/**
	* 构造函数
	 * @return 
	*/
	public  BalancePriceRef(){
		super();		
	}
	@Override
	public String[] getFieldCode() {
		return new String[]{"a.pricename","a.vbillcode","d.prodlinename","a.sdate","a.edate","a.pk_pricepolicy"};
	}
	@Override
	public String[] getFieldName() {
		return new String[]{"结算价目表名称","单据号","产品线","开始时间","截止时间","表头主键"};
	}
	public String getRefTitle() {
	    return "结算价目表参照";
	}
	@Override
	public java.lang.String getTableName() {
		StringBuffer sb=new StringBuffer();
		sb.append(" gt_jspricepolicy a ");
		sb.append(" left join bd_prodline d on d.pk_prodline=a.product ");
		return sb.toString();
	}
	public String getPkFieldCode() {
	    return "a.pk_pricepolicy";
	}
	@Override
	public String getWherePart() {
		String swhere = super.getWherePart();
		if(swhere==null ||swhere.trim().equals("")){
			return " 1=1 and nvl(a.dr,0)=0 and a.vbillstatus='"+IJyglgtBillStatus.CHECKPASS+"' and a.pk_corp='"+getPk_corp()+"'";
		}else{
			return swhere+" and nvl(a.dr,0)=0 and a.vbillstatus='"+IJyglgtBillStatus.CHECKPASS+"' and a.pk_corp='"+getPk_corp()+"'";
		}
	}
}
