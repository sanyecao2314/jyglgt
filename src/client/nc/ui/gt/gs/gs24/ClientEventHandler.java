package nc.ui.gt.gs.gs24;


import nc.ui.jyglgt.billmanage.AbstractEventHandler;
import nc.ui.trade.controller.IControllerBase;
import nc.ui.trade.manage.BillManageUI;

/**
 * @���ܣ�ģ������ֶ���ʾ���ð�ť��
 */
public class ClientEventHandler extends AbstractEventHandler {

	public ClientEventHandler(BillManageUI billUI, IControllerBase control) {
		super(billUI, control);
	}

	@Override
	protected void onBoEdit() throws Exception {
		super.onBoEdit();
		enabled(false);
	}
	/**
	 * �༭̬��ͷ�ֶοɷ�༭����
	 * */
	private void enabled(boolean falg) {
		getBillCardPanelWrapper().getBillCardPanel().getHeadItem("pk_productline").setEnabled(falg);
	}
	@Override
	protected void onBoCancel() throws Exception {
		enabled(true);
		super.onBoCancel();
	}
	@Override
	protected void onBoSave() throws Exception {
		enabled(true);
		for(int i=0;i<getRowCount();i++){
			String free=getBodyValue(i, "free")==null?"":getBodyValue(i, "free").toString();
			String pk_doclist=getBodyValue(i, "pk_doclist")==null?"":getBodyValue(i, "pk_doclist").toString();
			if((free.equals("free1_0")||free.equals("free1_1")||free.equals("free2_0")||free.equals("free2_1")
					||free.equals("free3_0")||free.equals("free3_1")||free.equals("free4_0")||free.equals("free4_1"))&&pk_doclist.equals("")){
				showErrorMessage("���ڵ�"+(i+1)+"�в��ճ��������б����ƣ�");
				return;
				}
			}
		super.onBoSave();
	}
	
}
