package nc.ui.gt.refpub;

import nc.ui.bd.ref.AbstractRefModel;

/**
 * @功能：存货档案参照
 * @作者：施坤
 * @时间：2011-6-11
 */
public class INVRef extends AbstractRefModel {
	/**
	* 构造函数
	 * @return 
	*/
	public  INVRef(){
		super();		
	}
	@Override
	public String[] getFieldCode() {
		return new String[]{"bd_invbasdoc.invcode","bd_invbasdoc.invname","bd_invbasdoc.pk_invbasdoc","bd_invmandoc.pk_invmandoc"};
	}
	@Override
	public String[] getFieldName() {
		return new String[]{"存货编码","存货名称","存货基本主键","存货管理主键"};
	}
	public String getRefTitle() {
	    return "存货档案参照";
	}
	@Override
	public java.lang.String getTableName() {
		StringBuffer sb=new StringBuffer();
		sb.append(" bd_invmandoc bd_invmandoc")
		.append(" left join bd_invbasdoc bd_invbasdoc on bd_invbasdoc.pk_invbasdoc=bd_invmandoc.pk_invbasdoc ");
		return sb.toString();
	}
	public String getPkFieldCode() {
	    return "bd_invmandoc.pk_invmandoc";
	}
	@Override
	public String getWherePart() {
		String swhere = super.getWherePart();
		if(swhere==null ||swhere.trim().equals("")){
			return " 1=1 and nvl(bd_invmandoc.dr,0)=0 and bd_invmandoc.pk_corp='"+getPk_corp()+"'";
		}else{
			return swhere+" and nvl(bd_invmandoc.dr,0)=0 and bd_invmandoc.pk_corp='"+getPk_corp()+"'";
		}
	}
}
