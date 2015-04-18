package nc.ui.gt.refpub;

import nc.ui.bd.ref.AbstractRefModel;

/**
 * @功能：自由项档案参照
 * @作者：施坤
 * @时间：2011-6-11
 */
public class DefdocRef extends AbstractRefModel {
	/**
	* 构造函数
	 * @return 
	*/
	public  DefdocRef(){
		super();		
	}
	@Override
	public String[] getFieldCode() {
		return new String[]{"a.doccode","a.docname","b.doclistname","b.doclistcode","b.pk_defdoclist","a.pk_defdoc"};
	}
	@Override
	public String[] getFieldName() {
		return new String[]{"自由项编码","自由项名称","档案列表名称","档案列表编码","档案列表主键","主键"};
	}
	public String getRefTitle() {
	    return "自由项档案参照";
	}
	@Override
	public java.lang.String getTableName() {
		StringBuffer sb=new StringBuffer();
		sb.append(" bd_defdoc a ")
		.append("   left join bd_defdoclist b on b.pk_defdoclist=a.pk_defdoclist ");
		return sb.toString();
	}
	public String getPkFieldCode() {
	    return "a.pk_defdoc";
	}
	@Override
	public String getWherePart() {
		String swhere = super.getWherePart();
		if(swhere==null ||swhere.trim().equals("")){
			return " 1=1 and nvl(a.dr,0)=0 and nvl(b.dr,0)=0 ";
		}else{
			return swhere+" and nvl(a.dr,0)=0 and nvl(b.dr,0)=0 ";
		}
	}
}
