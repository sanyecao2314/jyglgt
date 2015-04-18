package nc.ui.gt.refpub;

import nc.ui.bd.ref.AbstractRefModel;

/**
 * @功能：订单表头参照
 * @作者：施坤
 * @时间：2011-6-19
 */
public class SaleHeadRef extends AbstractRefModel {
	/**
	* 构造函数
	* @return 
	*/
	public  SaleHeadRef(){
		super();		
	}
	@Override
	public int getDefaultFieldCount() {
		return 6;
	}
	@Override
	public String[] getFieldCode() {
		return new String[]{"so_sale.vreceiptcode","so_sale.vdef13","so_sale.dbilldate","so_sale.vdef16","bd_cubasdoc.custname","so_sale.csaleid"};
	}
	@Override
	public String[] getFieldName() {
		return new String[]{"订单号","合同号","订单日期","销售类型","客户","主表主键"};
	}
	public String getRefTitle() {
	    return "订单表头参照";
	}
	@Override
	public java.lang.String getTableName() {
		StringBuffer sb=new StringBuffer();
        sb.append(" so_sale left join bd_cumandoc on bd_cumandoc.pk_cumandoc=so_sale.ccustomerid ")
        .append(" left join bd_cubasdoc on bd_cubasdoc.pk_cubasdoc=bd_cumandoc.pk_cubasdoc ");
		return sb.toString();
	}
	public String getPkFieldCode() {
	    return "so_sale.csaleid";
	}
	@Override
	public String getWherePart() {
		String swhere = super.getWherePart();
		if(swhere==null ||swhere.trim().equals("")){
			return " 1=1 and so_sale.fstatus='2'  and nvl(so_sale.dr,0)=0  and so_sale.pk_corp='"+getPk_corp()+"'";
		}else{
			return swhere+" and so_sale.fstatus='2' and nvl(so_sale.dr,0)=0  and so_sale.pk_corp='"+getPk_corp()+"'";
		}
	}
}
