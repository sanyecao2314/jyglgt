package nc.ui.jyglgt.pub;

import java.io.File;

import javax.swing.filechooser.FileFilter;

import nc.ui.pub.beans.UIFileChooser;
import nc.ui.pub.beans.UITable;
import nc.ui.pub.tools.BannerDialog;
import nc.ui.trade.bill.AbstractManageController;
import nc.ui.trade.bill.ISingleController;
import nc.ui.trade.manage.BillManageUI;

/**
 * @author Administrator
 * �������Client,���ɷŵ�Public��,��ֹPublic�˱�private�˵���,����
 * ���Client��private���ʶ�����
 */
public class ClientToolKits {

	/**
     * �����͵���ͨ��
	 * ���ݵ���
	 * ���÷���ʱ�� ClientToolKits.onBoExport((BillManageUI)getBillUI(),String title);
	 * @param title2 
	 * @throws Exception
	 */
	public static void onBoExport(BillManageUI ui, String title2) throws Exception {
		final BillManageUI fui = ui;
		// ���ø��෽�������෽��Ϊ��
		// super.onBoExport();
		// �����ļ�ѡ��Ի�����
		UIFileChooser chooser = getFileChooser(fui);
		// ������ʾ���淽��
		int ret = chooser.showDialog(fui, "���ļ�");
		// ���ȡ�����򷵻�
		if (ret == 1)
			return;
		/************ �˴�����final������Ϊ���߳��ڲ����е��ã���������Ϊfinal���� **************/
		// ����File����
		final File file = chooser.getSelectedFile();
		// ����Excel��sheetҳǩ������
		final String title = title2;
		// ����������������
		UITable et = null;
		AbstractManageController ctrl = ui.getUIControl();
		if(ctrl instanceof ISingleController){
			ISingleController ic = (ISingleController) ctrl;
			if(ic.isSingleDetail()){
				et = fui.getBillCardPanel().getBillTable();
			}else{
				et = fui.getBillListPanel().getHeadTable();
			}
		}else{
			et = fui.getBillCardPanel().getBillTable();
		}
		final UITable dbtable = et;
		if (file == null) {
			return;
		}
		/***************** ���ڵ��뵼����ѯ�Ȳ������Ϻ�ʱ�������������̵߳������ȶԻ��򣬱�����֡����������� ************************/
		// �߳���
		Runnable checkRun = new Runnable() {
			public void run() {
				// �̶߳Ի���ϵͳ������ʾ��
				BannerDialog dialog = new BannerDialog(fui);
				dialog.start();
				try {
					/**
					 * �����뵼����ѯ�ȷ���д������
					 */
					nc.ui.iuforeport.businessquery.ExcelUtil.export2Excel(file,
							dbtable, title);
				} catch (Exception e) {
					e.printStackTrace();
					fui.showErrorMessage(e.getMessage());
				} finally {
					// ����ϵͳ������ʾ��
					dialog.end();
				}
			}
		};
		// �����߳�
		new Thread(checkRun).start();
	}

	// ѡ���ļ��Ի���
	private static UIFileChooser getFileChooser(BillManageUI fui) {
		UIFileChooser bomFileChooser_imp = null;
		if (bomFileChooser_imp == null) {
			bomFileChooser_imp = new UIFileChooser();
			// ��ʾ����ѡȡĿ¼
			bomFileChooser_imp
					.setFileSelectionMode(UIFileChooser.FILES_AND_DIRECTORIES);
			// ����Ĭ���ļ���
			bomFileChooser_imp.setSelectedFile(new File(""+fui.getTitle()+fui._getDate().toString()+".xls"));
			// ���öԻ������
			bomFileChooser_imp.setDialogTitle("ѡ���ļ��Ի���");
			bomFileChooser_imp.setBounds(325, 500, 25, 25);
			bomFileChooser_imp.setAcceptAllFileFilterUsed(false);
			bomFileChooser_imp.setFileFilter(new FileFilter() {
				public boolean accept(File f) { // �趨���õ��ļ��ĺ�׺��
					if (f.getName().toLowerCase().endsWith(".xls")
							|| f.isDirectory()) {
						return true;
					}
					return false;
				}

				public String getDescription() {
					return "EXCEL�ĵ�(*.xls)";
				}
			});
		}
		return bomFileChooser_imp;
	}
	
}
