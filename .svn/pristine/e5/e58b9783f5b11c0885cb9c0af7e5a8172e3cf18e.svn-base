package nc.ui.jyglgt.refpub;

import nc.ui.bd.ref.AbstractRefModel;
import nc.ui.pub.ClientEnvironment;

/***
 * @说明：销售订单参照。
 * @author gdt
 * @time 2014-9-23
 */
public class saleOrderRefModel extends AbstractRefModel {

	public saleOrderRefModel(){
		super();
	}

	@Override
	public String getPkFieldCode() {
		
		return "so.csaleid";
	}

	@Override
	public String getRefTitle() {
		return "销售订单参照";
	}

	@Override
	public java.lang.String  getTableName() {
		StringBuffer sb = new StringBuffer();
		sb.append(" so_sale so left join bd_cumandoc cu on so.ccustomerid=cu.pk_cumandoc  ") 
		.append(" left join bd_cubasdoc cub on cu.pk_cubasdoc=cub.pk_cubasdoc ");
		return sb.toString();
	}

	@Override
	public String[] getFieldCode() {
		return new String[]{"so.vreceiptcode","cub.custname","so.csaleid"};
		
	}

	@Override
	public String[] getFieldName() {
		return new String[]{"订单号","客户","订单主键"};
	}

	@Override
	public String getWherePart() {
		String swhere = super.getWherePart();
		if(swhere==null ||swhere.trim().equals("")){
			return " 1=1 and nvl(so.dr,0)=0 ";
		}else{
			return swhere+" nvl(so.dr,0)=0 and so.fstatus=2 ";
		}
	}
	
	ClientEnvironment ce = ClientEnvironment.getInstance();
	
	@Override
	public int getDefaultFieldCount() {
		return getFieldCode().length-1;
	}
	
}
