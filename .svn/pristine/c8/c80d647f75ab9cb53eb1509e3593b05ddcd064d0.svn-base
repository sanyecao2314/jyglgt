package nc.ui.jyglgt.refpub;

import nc.ui.bd.ref.AbstractRefModel;
import nc.ui.pub.ClientEnvironment;

/***
 * @说明：采购订单参照。
 * @author 施鹏
 * @time 2014-9-23
 */
public class PoOrderRefModel extends AbstractRefModel {

	public PoOrderRefModel(){
		super();
	}

	@Override
	public String getPkFieldCode() {
		
		return "a.corderid";
	}

	@Override
	public String getRefTitle() {
		return "采购订单参照";
	}

	@Override
	public java.lang.String  getTableName() {
		return " po_order a  left join bd_cubasdoc c on a.cvendorbaseid =c.pk_cubasdoc " ;
	}

	@Override
	public String[] getFieldCode() {
		return new String[]{"a.corderid","a.vordercode","c.custcode","c.custname","a.dorderdate"};
		
	}

	@Override
	public String[] getFieldName() {
		return new String[]{"PK","订单号","客户编码","客户名称","单据日期"};
	}

	@Override
	public String getWherePart() {
		String swhere = super.getWherePart();
		if(swhere==null ||swhere.trim().equals("")){
			return " 1=1 and nvl(a.dr,0)=0 ";
		}else{
			return swhere+" nvl(a.dr,0)=0 and a.pk_corp='"+ce.getCorporation().getPk_corp()+"' ";
		}
	}
	
	ClientEnvironment ce = ClientEnvironment.getInstance();
	
	@Override
	public int getDefaultFieldCount() {
		return getFieldCode().length-1;
	}
	
}
