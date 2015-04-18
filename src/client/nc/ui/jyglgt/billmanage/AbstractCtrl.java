package nc.ui.jyglgt.billmanage;

import nc.ui.trade.bill.AbstractManageController;

/**
 * 说明:基础事件处理类,一些常用方法已在此重写,管理型界面应继承此类
 * @author 公共开发者 2012-1-5 14:21s:47
 */
public abstract class AbstractCtrl extends AbstractManageController {

    /**
     * OperatorController 构造子注解。
     */
    public AbstractCtrl() {
        super();
    }

    /**
     * 获得单据类型。
     * 
     * @return java.lang.String
     */
    public abstract String getBillType();

    /**
     * 获得单据VO信息 0--billVo 1--billheadVo 2--billitemVo
     * 
     * @return java.lang.String[]
     */
    public abstract java.lang.String[] getBillVoName();

    /**
     * 获得表体自定义项关键字 创建日期：(2003-1-5 11:27:45)
     * 
     * @return java.lang.String
     */
    public String getBodyZYXKey() {
        return null;
    }

    /**
     * 获得BusinessAction种类(BD\PF)。 创建日期：(2004-1-15 13:47:52)
     * 
     * @return int
     */
    public int getBusinessActionType() {
        return nc.ui.trade.businessaction.IBusinessActionType.BD;
    }

    /**
     * 返回具体卡片的子表隐列 注意: 必须在该方法内进行实例化,该方法在构造中调用， 本类未实例化 创建日期：(2002-12-25 16:39:45)
     * 
     * @return java.lang.String[]
     */
    public java.lang.String[] getCardBodyHideCol() {
        return null;
    }

    /**
     * 返回录入的卡片的动作数组。 具体的参见IBillButton 创建日期：(2004-1-3 22:14:47)
     * 
     * @return java.lang.int[]
     */
    public int[] getCardButtonAry() {	   
	     return null;
    }
    /**
     * 返回该单据子表的主键 创建日期：(2002-12-27 16:57:01)
     * 
     * @return java.lang.String
     */
    public abstract String getChildPkField();

    /**
     * 获得表头自定义项关键字 创建日期：(2003-1-5 11:27:45)
     * 
     * @return java.lang.String
     */
    public String getHeadZYXKey() {
        return null;
    }

    /**
     * 返回具体列表子表的隐列 创建日期：(2002-12-25 16:39:45) 注意： 1.必须在该方法内进行实例化,该方法在构造中调用，
     * 本类未实例化
     * 
     * @return java.lang.String[]
     */
    public java.lang.String[] getListBodyHideCol() {
        return null;
    }

    /**
     * 返回录入的列表的动作数组。 具体的参见IBillButton 创建日期：(2004-1-3 22:14:47)
     * 
     * @return java.lang.int[]
     */	
    public int[] getListButtonAry() {
	     return null;
    }

    /**
     * 返回具体列表的表头隐列 注意： 1.必须在该方法内进行实例化,该方法在构造中调用， 本类未实例化 创建日期：(2002-12-25
     * 16:39:45)
     * 
     * @return java.lang.String[]
     */
    public java.lang.String[] getListHeadHideCol() {
        return null;
    }

    /**
     * 返回该单据在主表的主键 创建日期：(2002-12-27 16:57:01)
     * 
     * @return java.lang.String
     */
    public abstract String getPkField();

    /**
     * 审批进行中是否可修改。 系统默认不可修改，如果不能修改则重载该方法 创建日期：(2003-7-21 14:32:26)
     * 
     * @return nc.vo.pub.lang.UFBoolean
     * @exception java.lang.Exception 异常说明。
     */
    public Boolean isEditInGoing() throws Exception {
        return null;
    }

    /**
     * 是否存在单据状态。 创建日期：(2004-2-5 13:04:45)
     * 
     * @return boolean
     */
    public boolean isExistBillStatus() {
        return false;
    }

    /** 是否加载卡片公式，缺省false */
    public boolean isLoadCardFormula() {
        return true;
    }

    /**
     * 卡片是否显示行号 创建日期：(2003-1-5 15:29:05)
     * 
     * @return boolean
     */
    public boolean isShowCardRowNo() {
        return true;
    }

    /**
     * 卡片是否显示合计行 创建日期：(2003-1-5 15:29:05)
     * 
     * @return boolean
     */
    public boolean isShowCardTotal() {
        return true;
    }
    /**
     * 单据的方式是否手工选择
     */
    public boolean isCombinNeedDef(){
        return true;
    }
    /**
     * 列表是否显示行号 创建日期：(2003-1-5 15:29:05)
     * 
     * @return boolean
     */
    public boolean isShowListRowNo() {
        return true;
    }

    /**
     * 列表是否显示合计行 创建日期：(2003-1-5 15:29:05)
     * 
     * @return boolean
     */
    public boolean isShowListTotal() {
        return true;
    }

    public String getNotSelMsg() {
        return null;
    }

    public String getPageTitleFld() {
        return "";
    }
    
    public String getBodyCondition() {
        return null;
    }
}