package nc.ui.gt.gs.gs24;

import java.util.ArrayList;

import nc.pub.jyglgt.proxy.Proxy;
import nc.ui.jyglgt.billmanage.AbstractClientUI;
import nc.ui.pub.bill.BillEditEvent;
import nc.ui.pub.bill.BillItem;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.manage.ManageEventHandler;
import nc.vo.pub.BusinessException;

/**
 * @���ܣ�ģ������ֶ���ʾ����ui��
 */
public class ClientUI extends AbstractClientUI{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected AbstractManageController createController() {
	return new ClientCtrl();
    }
    
    @Override
    public ManageEventHandler createEventHandler() {
	return new ClientEventHandler(this,this.getUIControl());
    }

    /**��ͷ��ʱ�䴦��*/
    @Override
    public void setDefaultData() throws Exception {
    	super.setDefaultData();
        BillItem oper = getBillCardPanel().getTailItem("lastoperator");
        if (oper != null){
            oper.setValue(_getOperator());
        }
        BillItem date = getBillCardPanel().getTailItem("lastdate");
        if (date != null){
            date.setValue(_getDate());
        }
        getBillCardPanel().execHeadLoadFormulas();
    }
	@Override
	public void afterEdit(BillEditEvent e) {
		super.afterEdit(e);
		ctrproduct(e);//��Ʒ�������ظ���Ψһ��
	}
	/**
	 * ��Ʒ�������ظ���Ψһ��
	 * */
	@SuppressWarnings("unchecked")
	private void ctrproduct(BillEditEvent e) {
		if(e.getKey().equals("pk_productline")){
			Object pk= getHeadValueObject("pk_productline");
			String sql=" select * from  gt_columnshow where nvl(dr,0)=0 and pk_productline='"+pk.toString()+"' and pk_corp='"+_getCorp().getPk_corp()+"' ";
			ArrayList al;
			try {
				al = Proxy.getItf().queryArrayBySql(sql.toString());
                if(al!=null&&al.size()>0){
                	showErrorMessage("�ò�Ʒ�����Ѵ��ڣ�");
                	setHeadValue("pk_productline", null);
                	return;
                }
			} catch (BusinessException e1) {
				e1.printStackTrace();
			}
		}
	}
}