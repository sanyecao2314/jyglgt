package nc.ui.gt.refpub;

import nc.ui.bd.ref.AbstractRefModel;

/**
 * @���ܣ�������ͷ����
 * @���ߣ�ʩ��
 * @ʱ�䣺2011-6-19
 */
public class SaleHeadCODERef extends AbstractRefModel {
	/**
	* ���캯��
	* @return 
	*/
	public  SaleHeadCODERef(){
		super();		
	}
	@Override
	public int getDefaultFieldCount() {
		return 6;
	}
	@Override
	public String[] getFieldCode() {
		return new String[]{"so_sale.dbilldate","so_sale.vreceiptcode","so_sale.vdef16","bd_cubasdoc.custname","so_sale.csaleid"};
	}
	@Override
	public String[] getFieldName() {
		return new String[]{"��������","������","��������","�ͻ�","��������"};
	}
	public String getRefTitle() {
	    return "������ͷ����";
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
			return " 1=1 and so_sale.fstatus in ('2','6') and so_sale.vdef16 in ('���Ų�����','��������','����������','����Ʒ����','�ᵥ������','�ᵥ����') and nvl(so_sale.dr,0)=0  and so_sale.pk_corp='"+getPk_corp()+"'";
		}else{
			return swhere+" and so_sale.fstatus in ('2','6') and so_sale.vdef16 in ('���Ų�����','��������','����������','����Ʒ����','�ᵥ������','�ᵥ����') and nvl(so_sale.dr,0)=0  and so_sale.pk_corp='"+getPk_corp()+"'";
		}
	}
}
