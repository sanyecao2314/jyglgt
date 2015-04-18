package nc.vo.jyglgt.pub.Toolkits;

import nc.vo.trade.pub.IBillStatus;
/**
 * 说明:定义单据状态
 * @author 公共开发者 2012-1-5 12:50:00
 */
public interface IJyglgtBillStatus extends IBillStatus{
	
	
    /*单据状态名称*/
    public static String[]  strStateRemark = new String[] { "审批不通过", "审批通过", "审批进行中", "提交态", "作废态",
            "冲销态", "终止(结算)态", "冻结态", "自由态","关闭态","归还态"};
    /*关闭态*/
    public static int CLOSE=9; 
    /*关闭态*/
    public static int BRETURN=10; 
    /*确定态*/
    public static int CHANGESTATUS=11; 
        
}
