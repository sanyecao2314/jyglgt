package nc.ui.gt.refpub;

import nc.ui.bd.ref.AbstractRefModel;

/**
 * @功能：订单参照
 * @作者：施坤
 * @时间：2011-6-19
 */
public class SaleOrderForICRef extends AbstractRefModel {
	/**
	* 构造函数
	* @return 
	*/
	public  SaleOrderForICRef(){
		super();		
	}
	@Override
	public int getDefaultFieldCount() {
		return 29;
	}
	@Override
	public String[] getFieldCode() {
		return new String[]{"so_sale.vreceiptcode","so_sale.vdef13","so_sale.vdef16","bd_cubasdoc.custname","bd_invbasdoc.invname","so_sale.dbilldate","so_saleexecute.vfree1","so_saleexecute.vdef12","so_saleexecute.vdef13",
				"so_saleexecute.vfree2","so_saleexecute.vdef14","so_saleexecute.vdef15","so_saleexecute.vfree3","so_saleexecute.vdef16",
				"so_saleexecute.vdef17","so_saleexecute.vdef6","so_saleexecute.vdef8","so_saleexecute.vdef9","so_saleexecute.vdef10","nvl(so_saleorder_b.nnumber,0)-nvl(so_saleexecute.ntotalinventorynumber,0) restnum",
				"so_sale.ccustomerid","so_saleorder_b.cinventoryid","so_sale.csaleid","so_saleexecute.csale_bid","so_sale.vnote","so_saleorder_b.frownote"};
	}
	@Override
	public String[] getFieldName() {
		return new String[]{"订单号","合同号","销售类型","客户","存货","订单日期","厚度下限","厚度上限","厚度条件","宽度上限","宽度下限","宽度条件","长度下限","长度上限","长度条件",
				"外形","货物状态","探伤级别","计量类型","可出库数量","客商管理档案主键","存货管理档案主键","主表主键","子表主键","表头备注","表体备注"};
	}
	public String getRefTitle() {
	    return "订单参照";
	}
	@Override
	public java.lang.String getTableName() {
		StringBuffer sb=new StringBuffer();
		sb.append("so_sale ")
		.append(" left join so_saleorder_b on so_saleorder_b.csaleid=so_sale.csaleid")
        .append(" left join bd_cumandoc on bd_cumandoc.pk_cumandoc=so_sale.ccustomerid ")
        .append(" left join bd_cubasdoc on bd_cubasdoc.pk_cubasdoc=bd_cumandoc.pk_cubasdoc ")
        .append(" left join so_saleexecute on so_saleexecute.csale_bid=so_saleorder_b.corder_bid")
        .append(" left join bd_invbasdoc on bd_invbasdoc.pk_invbasdoc=so_saleorder_b.cinvbasdocid");
		return sb.toString();
	}
	public String getPkFieldCode() {
	    return "so_saleexecute.csale_bid";
	}
	@Override
	public String getWherePart() {
		String swhere = super.getWherePart();
		if(swhere==null ||swhere.trim().equals("")){
			return " 1=1 and so_sale.vdef16 in ('订单扣信','订单不扣信') and so_sale.fstatus='2' and nvl(so_sale.dr,0)=0  and nvl(so_saleexecute.dr,0)=0  and so_sale.pk_corp='"+getPk_corp()+"' ";
		}else{
			return swhere+" and so_sale.vdef16 in ('订单扣信','订单不扣信') and so_sale.fstatus='2' and nvl(so_sale.dr,0)=0  and nvl(so_saleexecute.dr,0)=0  and so_sale.pk_corp='"+getPk_corp()+"' ";
		}
	}
}
