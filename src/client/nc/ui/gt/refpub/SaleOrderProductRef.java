package nc.ui.gt.refpub;

import nc.ui.bd.ref.AbstractRefModel;


/**
 * @���ܣ�������Ʒ�û�����
 * @���ߣ�ʩ��
 * @ʱ�䣺2011-9-14
 */
public class SaleOrderProductRef extends AbstractRefModel {
	/**
	* ���캯��
	* @return 
	*/
	public  SaleOrderProductRef(){
		super();		
	}

	@Override
	public String[] getFieldCode() {
		return new String[]{"bd_cubasdoc.custcode","bd_cubasdoc.custname","so_sale.pk_defdoc15"};
	}
	@Override
	public String[] getFieldName() {
		return new String[]{"�ͻ�����","�ͻ�����","����"};
	}
	public String getRefTitle() {
	    return "������Ʒ�û�����";
	}
	@Override
	public java.lang.String getTableName() {
		StringBuffer sb=new StringBuffer();
		sb.append("so_sale ")
		.append(" inner join so_saleorder_b on so_saleorder_b.csaleid=so_sale.csaleid ")
		.append(" inner join gt_balancelist_temp on gt_balancelist_temp.pk_saleorder_b=so_saleorder_b.corder_bid and nvl(gt_balancelist_temp.dr,0)=0 ")
        .append(" inner join bd_cumandoc on bd_cumandoc.pk_cumandoc=so_sale.pk_defdoc15 ")
        .append(" inner join bd_cubasdoc on bd_cubasdoc.pk_cubasdoc=bd_cumandoc.pk_cubasdoc ");
		return sb.toString();
	}
	public String getPkFieldCode() {
	    return "so_sale.pk_defdoc15";
	}
	@Override
	public String getWherePart() {
		String swhere = super.getWherePart();
		if(swhere==null ||swhere.trim().equals("")){
			return " 1=1   and nvl(so_sale.dr,0)=0   and so_sale.pk_corp='"+getPk_corp()+"'";
		}else{
			return swhere+"  and nvl(so_sale.dr,0)=0  and so_sale.pk_corp='"+getPk_corp()+"'";
		}
	}

	@Override
	public String getGroupPart() {	
		return "bd_cubasdoc.custcode,bd_cubasdoc.custname,so_sale.pk_defdoc15";
	}
	
	
}
