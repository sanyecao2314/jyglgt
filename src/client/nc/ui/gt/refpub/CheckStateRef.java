package nc.ui.gt.refpub;

import nc.ui.bd.ref.AbstractRefModel;

/**
 * @功能：质量等级档案参照
 * @作者：施坤
 * @时间：2011-8-6
 */
public class CheckStateRef extends AbstractRefModel {
	/**
	* 构造函数
	 * @return 
	*/
	public  CheckStateRef(){
		super();		
	}
	@Override
	public String[] getFieldCode() {
		return new String[]{"ccheckstatecode","ccheckstatename","bqualified","ccheckstate_bid"};
	}
	@Override
	public String[] getFieldName() {
		return new String[]{"质量等级编码","质量等级名称","是否合格","主键"};
	}
	public String getRefTitle() {
	    return "质量等级档案参照";
	}
	@Override
	public java.lang.String getTableName() {
		StringBuffer sb=new StringBuffer();
		sb.append("qc_checkstate_b");
		return sb.toString();
	}
	public String getPkFieldCode() {
	    return "ccheckstate_bid";
	}
	@Override
	public String getWherePart() {
		String swhere = super.getWherePart();
		if(swhere==null ||swhere.trim().equals("")){
			return " 1=1 and nvl(dr,0)=0 and nvl(bqualified,'N')='Y' and pk_corp='"+getPk_corp()+"'";
		}else{
			return swhere+" and nvl(dr,0)=0 and nvl(bqualified,'N')='Y' and pk_corp='"+getPk_corp()+"'";
		}
	}
}
