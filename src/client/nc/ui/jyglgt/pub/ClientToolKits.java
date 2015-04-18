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
 * 此类放在Client,不可放到Public端,防止Public端被private端调用,最终
 * 变成Client被private访问而出错
 */
public class ClientToolKits {

	/**
     * 管理型单据通用
	 * 单据导出
	 * 调用方法时： ClientToolKits.onBoExport((BillManageUI)getBillUI(),String title);
	 * @param title2 
	 * @throws Exception
	 */
	public static void onBoExport(BillManageUI ui, String title2) throws Exception {
		final BillManageUI fui = ui;
		// 调用父类方法，父类方法为空
		// super.onBoExport();
		// 定义文件选择对话框类
		UIFileChooser chooser = getFileChooser(fui);
		// 调用显示界面方法
		int ret = chooser.showDialog(fui, "打开文件");
		// 如果取消，则返回
		if (ret == 1)
			return;
		/************ 此处三个final变量因为在线程内部类中调用，所以声明为final类型 **************/
		// 定义File对象，
		final File file = chooser.getSelectedFile();
		// 导出Excel中sheet页签的名称
		final String title = title2;
		// 将导出的整个界面
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
		/***************** 由于导入导出查询等操作都较耗时，所以用如下线程弹出进度对话框，避免出现“假死”现象 ************************/
		// 线程类
		Runnable checkRun = new Runnable() {
			public void run() {
				// 线程对话框：系统运行提示框
				BannerDialog dialog = new BannerDialog(fui);
				dialog.start();
				try {
					/**
					 * 将导入导出查询等方法写在其中
					 */
					nc.ui.iuforeport.businessquery.ExcelUtil.export2Excel(file,
							dbtable, title);
				} catch (Exception e) {
					e.printStackTrace();
					fui.showErrorMessage(e.getMessage());
				} finally {
					// 销毁系统运行提示框
					dialog.end();
				}
			}
		};
		// 启用线程
		new Thread(checkRun).start();
	}

	// 选择文件对话框
	private static UIFileChooser getFileChooser(BillManageUI fui) {
		UIFileChooser bomFileChooser_imp = null;
		if (bomFileChooser_imp == null) {
			bomFileChooser_imp = new UIFileChooser();
			// 表示可以选取目录
			bomFileChooser_imp
					.setFileSelectionMode(UIFileChooser.FILES_AND_DIRECTORIES);
			// 设置默认文件名
			bomFileChooser_imp.setSelectedFile(new File(""+fui.getTitle()+fui._getDate().toString()+".xls"));
			// 设置对话框标题
			bomFileChooser_imp.setDialogTitle("选择文件对话框");
			bomFileChooser_imp.setBounds(325, 500, 25, 25);
			bomFileChooser_imp.setAcceptAllFileFilterUsed(false);
			bomFileChooser_imp.setFileFilter(new FileFilter() {
				public boolean accept(File f) { // 设定可用的文件的后缀名
					if (f.getName().toLowerCase().endsWith(".xls")
							|| f.isDirectory()) {
						return true;
					}
					return false;
				}

				public String getDescription() {
					return "EXCEL文档(*.xls)";
				}
			});
		}
		return bomFileChooser_imp;
	}
	
}
