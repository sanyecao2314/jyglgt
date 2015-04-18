package nc.ui.to.pubtransfer;

/**
 * 创建日期：(2004-2-10 9:13:55) 作者：王乃军 说明： 2、按钮定义。 3、按钮状态控制。
 * 4、动作相关的按钮做成public供tp直接访问。会不会有问题？ 这样做可以防止大量的get方法。
 */

import nc.ui.pub.ButtonObject;
import nc.ui.pub.ToftPanel;
import nc.ui.scm.extend.IFuncExtend;
import nc.ui.scm.pub.bill.ButtonTree;
import nc.ui.scm.pub.bill.IBillExtendFun;
import nc.ui.scm.pub.ctrl.GenEditConst;
import nc.ui.to.pub.btn.ITOButtonConst;
import nc.ui.to.pub.ctrl.GenPageBtnCtrl;
import nc.ui.to.pub.plugin.ToPluginButtonMenu;
import nc.vo.logging.Debug;
import nc.vo.pub.BusinessException;
import nc.vo.to.pub.ConstVO;

public class ButtonCtrl {

  public ToftPanel m_tp;

  /**
   * 以下是按钮，交给initButton()初始化。
   */
  protected ButtonObject m_boBusiType;// 业务类型

  protected ButtonObject m_boNew;// 增加
   
  //add by shipeng 2014-10-11   取价
  protected ButtonObject boCalculate;	
  //end by shipeng

  protected ButtonObject m_boSave;// 保存
  // 维护：包含修改，取消，删除,复制

  protected ButtonObject m_boMaintain;

  protected ButtonObject m_boUpdate;// 修改

  protected ButtonObject m_boCancel;// 取消

  protected ButtonObject m_boDelete;// 删除

  protected ButtonObject m_boCopy;// 复制
  // 行操作：增行，删行，复制行，粘贴行，插入行

  protected ButtonObject m_boBillRowMng;

  protected ButtonObject m_boAddLine;// 增行

  protected ButtonObject m_boDeleteLine;// 删行

  protected ButtonObject m_boInsertLine;// 插入行

  protected ButtonObject m_boCopyLine;// 复制行

  protected ButtonObject m_boPasteLine;// 粘贴行

  protected ButtonObject m_boNewRowNo; // 重排行号

  protected ButtonObject m_boPasteLineToTail; // 粘贴行到表尾

  protected ButtonObject m_boCardEdit; // 卡片编辑

  protected ButtonObject m_boRefAddLine; // 参照增行

  protected ButtonObject m_boDRSQ;// 调入申请

  protected ButtonObject m_boAudit;// 审批
  // 执行：送审，弃审，关闭，打开，冻结，解冻

  protected ButtonObject m_boExecute;

  public ButtonObject m_boSendAudit;// 送审

  protected ButtonObject m_boCancelAudit;// 弃审

  protected ButtonObject m_boClose;// 关闭

  protected ButtonObject m_boOpen;// 打开

  protected ButtonObject m_boFreeze;// 冻结

  protected ButtonObject m_boFresh;// 解冻

  protected ButtonObject m_boQuery;// 查询
  
  protected ButtonObject m_boRefresh;//刷新

  // 浏览:定位，翻页
  protected ButtonObject m_boBrowse;

  protected ButtonObject m_boLocate;// 定位

  public GenPageBtnCtrl m_PageBtnCtrl;// 翻页

  // 卡片显示/列表显示
  protected ButtonObject m_boSwitch;

  // 打印：预览，合并显示，打印
  protected ButtonObject m_boPrintMng;

  protected ButtonObject m_boPreview;// 预览

  protected ButtonObject m_boBillCombin;// 合并显示

  protected ButtonObject m_boPrint;// 打印
  // 辅助功能：文档管理，退回，库存硬锁定。

  protected ButtonObject m_boAssistMng;

  protected ButtonObject m_boDocument;// 文档管理

  protected ButtonObject m_boReturn;// 退回

  protected ButtonObject m_boStockLock;// 库存硬锁定

  protected ButtonObject m_boSelSettlePath;// 指定结算路径

  protected ButtonObject m_boDelSettlePath;// 取消结算路径

  protected ButtonObject m_boSendPlan;// 发货安排

  protected ButtonObject m_boFillPlan;// 补货安排

  // 辅助查询：联查，ATP,现存量，审批状态查询，存量显示、隐藏
  protected ButtonObject m_boAssistQuery;

  protected ButtonObject m_boJointCheck;// 联查

  protected ButtonObject m_boAllATP;

  protected ButtonObject m_boAllSP;

  protected ButtonObject m_boAuditStatus;// 审批状态查询

  public ButtonObject m_boOnHandShowHidden; // 现存量显示/隐藏

  // 放弃转单
  protected ButtonObject m_boCancelRef;

  protected ButtonObject m_boAskPrice; // 询价按钮

  public ButtonTree m_boTree = null;

  protected IFuncExtend m_funcExtend = null;
  
  protected ButtonObject m_splitPrint;//分单打印

  /**
   * ButtonCtrl 构造子注解。
   */
  public ButtonCtrl(
      ButtonTree bt, IFuncExtend funcExtend) {
    super();
    m_boTree = bt;
    m_funcExtend = funcExtend;
    initButtons();
  }

  // 其他组
  /**
   * ActionCtrl 构造子注解。
   */
  // public ButtonCtrl(nc.ui.pub.ToftPanel tp,IFuncExtend funcExtend) {
  // super();
  // //设置toftpanel引用，须先于initButtons调用。
  // m_tp=tp;
  // m_funcExtend = funcExtend;
  // initButtons(tp,m_funcExtend);
  // }
  // /**
  // * 创建日期：(2004-2-10 11:39:49)
  // * 作者：王乃军
  // * 参数：
  // * 返回：
  // * 说明：初始化按钮
  // * @deprecated
  // */
  // protected void initButtons(nc.ui.pub.ToftPanel tp,IFuncExtend funcExtend)
  // {
  //
  // m_boSave = new
  // ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000001")/*@res
  // "保存"*/,
  // nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000001")/*@res
  // "保存"*/, 2,"保存"); /*-=notranslate=-*/
  // m_boSendAudit = new
  // ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010","UPT40093010-000024")/*@res
  // "送审"*/,
  // nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010","UPT40093010-000024")/*@res
  // "送审"*/, 2,"送审"); /*-=notranslate=-*/
  // m_boCancel = new
  // ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000008")/*@res
  // "取消"*/,
  // nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000008")/*@res
  // "取消"*/, 2,"取消"); /*-=notranslate=-*/
  // m_boQuery = new
  // ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000006")/*@res
  // "查询"*/,
  // nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000006")/*@res
  // "查询"*/, 2,"查询"); /*-=notranslate=-*/
  // m_boAudit = new
  // ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000027")/*@res
  // "审批"*/,
  // nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON","UPPSCMCommon-000238")/*@res
  // "审批单据"*/, 2,"审批"); /*-=notranslate=-*/
  // m_boCancelAudit = new
  // ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000028")/*@res
  // "弃审"*/,
  // nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON","UPPSCMCommon-000210")/*@res
  // "取消审批"*/, 2,"弃审"); /*-=notranslate=-*/
  // m_boAuditStatus = new
  // ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010","UPT40093010-000023")/*@res
  // "审批流状态"*/,
  // nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010","UPT40093010-000023")/*@res
  // "审批流状态"*/, 2,"审批流状态"); /*-=notranslate=-*/
  // m_boReturn = new
  // ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010","UPT40093010-000025")/*@res
  // "退回"*/,
  // nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010","UPT40093010-000025")/*@res
  // "退回"*/, 2,"退回"); /*-=notranslate=-*/
  // /*/--------------------------------------------------------------------
  // m_pageBtn = new PageCtrlBtn(this);
  // m_pageBtn.getFirst().setName("I<");
  // m_pageBtn.getPre().setName("<");
  // m_pageBtn.getNext().setName(">");
  // m_pageBtn.getLast().setName(">I");
  // *///--------------------------------------------------------------------
  // //翻页控件
  // m_PageBtnCtrl = new GenPageBtnCtrl(tp);
  //
  // m_boBillMng = new
  // ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010","UPT40093010-000026")/*@res
  // "单据操作"*/,
  // nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON","UPPSCMCommon-000074")/*@res
  // "单据维护操作"*/, 2,"单据操作"); /*-=notranslate=-*/
  // m_boNew = new
  // ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000002")/*@res
  // "增加"*/,
  // nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010","UPP40093010-000000")/*@res
  // "新增一张单据"*/, 2,"增加"); /*-=notranslate=-*/
  // m_boCopy = new
  // ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000043")/*@res
  // "复制"*/,
  // nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON","UPPSCMCommon-000110")/*@res
  // "复制当前单据"*/, 2,"复制"); /*-=notranslate=-*/
  // m_boUpdate = new
  // ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000045")/*@res
  // "修改"*/,
  // nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000045")/*@res
  // "修改"*/, 2,"修改"); /*-=notranslate=-*/
  // m_boDelete = new
  // ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000039")/*@res
  // "删除"*/,
  // nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000039")/*@res
  // "删除"*/, 2,"删除"); /*-=notranslate=-*/
  // //--------------------------------------------------------------------
  // m_boStatusMng = new
  // ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010","UPT40093010-000027")/*@res
  // "状态维护"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010","UPT40093010-000027")/*@res
  // "状态维护"*/,2,"状态维护"); /*-=notranslate=-*/
  // m_boClose = new
  // ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010","UPT40093010-000028")/*@res
  // "关闭"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON","UPPSCMCommon-000120")/*@res
  // "关闭单据"*/,2,"关闭"); /*-=notranslate=-*/
  // m_boOpen = new
  // ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010","UPT40093010-000029")/*@res
  // "打开"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010","UPP40093010-000001")/*@res
  // "打开单据"*/,2,"打开"); /*-=notranslate=-*/
  // m_boFreeze = new
  // ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000030")/*@res
  // "冻结"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010","UPP40093010-000002")/*@res
  // "冻结单据"*/,2,"冻结"); /*-=notranslate=-*/
  // m_boFresh = new
  // ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000031")/*@res
  // "解冻"*/,nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010","UPP40093010-000003")/*@res
  // "将冻结的单据解冻"*/,2,"解冻"); /*-=notranslate=-*/
  // //---------------------------------------------------------------------
  // m_boPrint = new
  // ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000007")/*@res
  // "打印"*/,
  // nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000007")/*@res
  // "打印"*/, 2,"打印管理"); /*-=notranslate=-*/
  // m_boPrintMng = new
  // ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010","UPT40093010-000030")/*@res
  // "打印"*/,
  // nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010","UPT40093010-000030")/*@res
  // "打印"*/, 2,"打印"); /*-=notranslate=-*/
  // m_boPreview = new
  // ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010","UPT40093010-000032")/*@res
  // "预览"*/,
  // nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010","UPT40093010-000032")/*@res
  // "预览"*/, 2,"预览"); /*-=notranslate=-*/
  // //---------------------------------------------------------------------
  // m_boAssistMng = new
  // ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000036")/*@res
  // "辅助"*/,
  // nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON","UPPSCMCommon-000066")/*@res
  // "单据的各种辅助功能"*/, 2,"辅助"); /*-=notranslate=-*/
  // m_boLocate = new
  // ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010","UPT40093010-000033")/*@res
  // "定位"*/,
  // nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON","UPPSCMCommon-000092")/*@res
  // "定位到指定单据"*/, 2,"定位"); /*-=notranslate=-*/
  // m_boSwitch = new
  // ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010","UPT40093010-000034")/*@res
  // "切换"*/,
  // nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010","UPT40093010-000034")/*@res
  // "切换"*/, 2,"切换"); /*-=notranslate=-*/
  // // m_boAtp = new
  // ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010","UPT40093010-000035")/*@res
  // "存量查询"*/,
  // nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010","UPT40093010-000035")/*@res
  // "存量查询"*/, 2,"存量查询"); /*-=notranslate=-*/
  // m_boOnHandShowHidden = new
  // ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("topub","UPP4009-000027")/*@res
  // "切换"*/,
  // nc.ui.ml.NCLangRes.getInstance().getStrByID("topub","UPP4009-000027")/*@res
  // "现存量显示/隐藏"*/, 2,"存量显示/隐藏");/*-=notranslate=-*/
  // m_boAllATP = new ButtonObject("ATP",
  // nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON","UPPSCMCommon-000283")/*@res
  // "显示当前存货在所有库存组织的ATP"*/, 2,"ATP"); /*-=notranslate=-*/
  // m_boAllSP = new
  // ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC000-0002918")/*@res
  // "现存量"*/,
  // nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON","UPPSCMCommon-000282")/*@res
  // "显示当前存货在所有仓库的现存量"*/, 2,"现存量"); /*-=notranslate=-*/
  // m_boDocument = new
  // ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010","UPT40093010-000036")/*@res
  // "文档管理"*/,
  // nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010","UPT40093010-000036")/*@res
  // "文档管理"*/, 2,"文档管理"); /*-=notranslate=-*/
  // m_boJointCheck = new
  // ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010","UPT40093010-000037")/*@res
  // "联查"*/,
  // nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010","UPT40093010-000037")/*@res
  // "联查"*/, 2,"联查"); /*-=notranslate=-*/
  // m_boStockLock = new
  // ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC000-0001824")/*@res
  // "库存硬锁定"*/,
  // nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC000-0001824")/*@res
  // "库存硬锁定"*/, 2,"库存硬锁定"); /*-=notranslate=-*/
  // m_boBillCombin=new
  // ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("topub","UPT4009-000007"),nc.ui.ml.NCLangRes.getInstance().getStrByID("topub","UPT4009-000007"),2,"合并显示");
  // //----------------------------------------------------------------------
  // m_boBillRowMng = new
  // ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010","UPT40093010-000038")/*@res
  // "行操作"*/,
  // nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010","UPP40093010-000004")/*@res
  // "行操作"*/, 2,"行操作"); /*-=notranslate=-*/
  // m_boAddLine = new
  // ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000012")/*@res
  // "增行"*/,
  // nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000012")/*@res
  // "增行"*/, 2,"增行"); /*-=notranslate=-*/
  // m_boDeleteLine = new
  // ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000013")/*@res
  // "删行"*/,
  // nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000013")/*@res
  // "删行"*/, 2,"删行"); /*-=notranslate=-*/
  // m_boCopyLine = new
  // ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000014")/*@res
  // "复制行"*/,
  // nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000014")/*@res
  // "复制行"*/, 2,"复制行"); /*-=notranslate=-*/
  // m_boPasteLine = new
  // ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000015")/*@res
  // "粘贴行"*/,
  // nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000015")/*@res
  // "粘贴行"*/, 2,"粘贴行"); /*-=notranslate=-*/
  // m_boInsertLine = new
  // ButtonObject(nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000016")/*@res
  // "插入行"*/,
  // nc.ui.ml.NCLangRes.getInstance().getStrByID("common","UC001-0000016")/*@res
  // "插入行"*/, 2,"插入行"); /*-=notranslate=-*/
  // //将上层按钮组合：
  // m_boBillMng.addChildButton(m_boNew);
  // m_boBillMng.addChildButton(m_boCopy);
  // m_boBillMng.addChildButton(m_boUpdate);
  // m_boBillMng.addChildButton(m_boDelete);
  //
  // m_boStatusMng.addChildButton(m_boClose);
  // m_boStatusMng.addChildButton(m_boOpen);
  // m_boStatusMng.addChildButton(m_boFreeze);
  // m_boStatusMng.addChildButton(m_boFresh);
  //
  // m_boPrintMng.addChildButton(m_boPrint);
  // m_boPrintMng.addChildButton(m_boPreview);
  //
  // m_boAssistMng.addChildButton(m_boLocate);
  // m_boAssistMng.addChildButton(m_boSwitch);
  // // m_boAssistMng.addChildButton(m_boAtp);
  // m_boAssistMng.addChildButton(m_boOnHandShowHidden);
  // m_boAssistMng.addChildButton(m_boAllATP);
  // m_boAssistMng.addChildButton(m_boAllSP);
  // m_boAssistMng.addChildButton(m_boStockLock);
  // m_boAssistMng.addChildButton(m_boDocument);
  // m_boAssistMng.addChildButton(m_boJointCheck);
  // m_boAssistMng.addChildButton(m_boBillCombin);
  //
  // m_boBillRowMng.addChildButton(m_boAddLine);
  // m_boBillRowMng.addChildButton(m_boDeleteLine);
  // m_boBillRowMng.addChildButton(m_boCopyLine);
  // m_boBillRowMng.addChildButton(m_boPasteLine);
  // m_boBillRowMng.addChildButton(m_boInsertLine);
  //
  // //缺省组
  // ButtonObject[] boThis =
  // new ButtonObject[] {
  // m_boSave,
  // m_boSendAudit,
  // m_boCancel,
  // m_boQuery,
  // m_boAudit,
  // m_boCancelAudit,
  // m_boAuditStatus,
  // m_boReturn,
  // m_PageBtnCtrl.getFirst(),
  // m_PageBtnCtrl.getPre(),
  // m_PageBtnCtrl.getNext(),
  // m_PageBtnCtrl.getLast(),
  // m_boBillMng,
  // m_boStatusMng,
  // m_boPrintMng,
  // m_boAssistMng,
  // m_boBillRowMng
  // };
  //
  // // 初始化扩展类----支持扩展类和按钮。
  // ButtonObject[] boExtend = null;
  // if(funcExtend != null)
  // {
  // boExtend = funcExtend.getExtendButton();
  // }
  //
  // if (boExtend != null && boExtend.length != 0)
  // {
  // //arraycopy(Object src, int srcPos, Object dest, int destPos,int length);
  // m_boaDefault = new ButtonObject[boThis.length + boExtend.length];
  // System.arraycopy(boThis,0,m_boaDefault,0,boThis.length);
  // System.arraycopy(boExtend,0,m_boaDefault,boThis.length,boExtend.length);
  // }
  // else
  // {
  // //arraycopy(Object src, int srcPos, Object dest, int destPos,int length);
  // m_boaDefault = new ButtonObject[boThis.length];
  // System.arraycopy(boThis,0,m_boaDefault,0,boThis.length);
  // }
  // //支持扩展类和按钮结束
  // }
  protected void initButtons() {
    m_boBusiType = m_boTree.getButton(ITOButtonConst.BTN_BUSINESS_TYPE);// 业务类型
    m_boNew = m_boTree.getButton(ITOButtonConst.BTN_ADD);// 增加
    //取价 add by shipeng at 2014-10-28
    boCalculate = m_boTree.getButton("取价");
    //end by shipeng at 2014-10-28
    m_boSave = m_boTree.getButton(ITOButtonConst.BTN_SAVE);// 保存
    m_boMaintain = m_boTree.getButton(ITOButtonConst.BTN_BILL);// 维护
    m_boUpdate = m_boTree.getButton(ITOButtonConst.BTN_BILL_EDIT);// 修改
    m_boCancel = m_boTree.getButton(ITOButtonConst.BTN_BILL_CANCEL);// 取消
    m_boDelete = m_boTree.getButton(ITOButtonConst.BTN_BILL_DELETE);// 删除
    m_boCopy = m_boTree.getButton(ITOButtonConst.BTN_BILL_COPY);// 复制
    m_boBillRowMng = m_boTree.getButton(ITOButtonConst.BTN_LINE);// 行操作
    m_boAddLine = m_boTree.getButton(ITOButtonConst.BTN_LINE_ADD);// 增行
    m_boDeleteLine = m_boTree.getButton(ITOButtonConst.BTN_LINE_DELETE);// 删行
    m_boInsertLine = m_boTree.getButton(ITOButtonConst.BTN_LINE_INSERT);// 插入行

    m_boCopyLine = m_boTree.getButton(ITOButtonConst.BTN_LINE_COPY);// 复制行
    m_boPasteLine = m_boTree.getButton(ITOButtonConst.BTN_LINE_PASTE);// 粘贴行
    m_boPasteLineToTail = m_boTree
        .getButton(ITOButtonConst.BTN_LINE_PASTE_TAIL); // 粘贴行到表尾
    m_boCardEdit = m_boTree.getButton(ITOButtonConst.BTN_CARD_EDIT); // 卡片编辑
    m_boNewRowNo = m_boTree.getButton(ITOButtonConst.BTN_ADD_NEWROWNO); // 重排行号
    m_boAskPrice = m_boTree.getButton(ITOButtonConst.BTN_ASKPRICE); // 询价
    m_boRefresh = m_boTree.getButton(ITOButtonConst.BTN_REFRESH);//刷新
    m_boRefAddLine = m_boTree.getButton(ITOButtonConst.BTN_REF_ADDLINE);// 参照增行
    m_boDRSQ = m_boTree.getButton(ITOButtonConst.BTN_DRSQ);// 调入申请

    m_boAudit = m_boTree.getButton(ITOButtonConst.BTN_AUDIT);// 审批
    m_boExecute = m_boTree.getButton(ITOButtonConst.BTN_EXECUTE);// 执行
    m_boSendAudit = m_boTree.getButton(ITOButtonConst.BTN_EXECUTE_AUDIT);// 送审
    m_boCancelAudit = m_boTree
        .getButton(ITOButtonConst.BTN_EXECUTE_AUDIT_CANCEL);// 弃审
    m_boClose = m_boTree.getButton(ITOButtonConst.BTN_EXECUTE_CLOSE);// 关闭
    m_boOpen = m_boTree.getButton(ITOButtonConst.BTN_EXECUTE_OPEN);// 打开
    m_boFreeze = m_boTree.getButton(ITOButtonConst.BTN_EXECUTE_FREEZE);// 冻结
    m_boFresh = m_boTree.getButton(ITOButtonConst.BTN_EXECUTE_FREEZE_CANCEL);// 解冻
    m_boQuery = m_boTree.getButton(ITOButtonConst.BTN_QUERY);// 查询
    m_boBrowse = m_boTree.getButton(ITOButtonConst.BTN_BROWSE);// 浏览
    m_boLocate = m_boTree.getButton(ITOButtonConst.BTN_BROWSE_LOCATE);// 定位
    m_boSwitch = m_boTree.getButton(ITOButtonConst.BTN_SWITCH);// 切换
    m_boPrintMng = m_boTree.getButton(ITOButtonConst.BTN_PRINT);// 打印管理
    m_boPreview = m_boTree.getButton(ITOButtonConst.BTN_PRINT_PREVIEW);// 预览
    m_boBillCombin = m_boTree.getButton(ITOButtonConst.BTN_COMBIN_SHOW);// 合并显示
    m_boPrint = m_boTree.getButton(ITOButtonConst.BTN_PRINT_PRINT);// 打印
    m_boAssistMng = m_boTree.getButton(ITOButtonConst.BTN_ASSIST_FUNC);// 辅助功能
    m_boDocument = m_boTree.getButton(ITOButtonConst.BTN_ASSIST_FUNC_DOCUMENT);// 文档管理
    m_boReturn = m_boTree.getButton(ITOButtonConst.BTN_RETURN);// 退回
    m_boStockLock = m_boTree.getButton(ITOButtonConst.BTN_STOCK_LOCK);// 库存硬锁定
    m_boSelSettlePath = m_boTree.getButton(ITOButtonConst.BTN_M_SELSETTLEPATH);// 指定结算路径
    m_boDelSettlePath = m_boTree.getButton(ITOButtonConst.BTN_M_DELSETTLEPATH);// 取消结算路径
    m_boSendPlan = m_boTree.getButton(ITOButtonConst.BTN_SEND_PLAN);// 发货安排
    m_boFillPlan = m_boTree.getButton(ITOButtonConst.BTN_FILL_PLAN);// 补货安排
    m_boAssistQuery = m_boTree.getButton(ITOButtonConst.BTN_ASSIST_QUERY);// 辅助查询
    m_boJointCheck = m_boTree
        .getButton(ITOButtonConst.BTN_ASSIST_QUERY_RELATED);// 联查
    m_splitPrint = m_boTree.getButton(ITOButtonConst.BTN_SPLITPRINT_PREVIEW);//分单打印预览
    m_boAllATP = m_boTree.getButton(ITOButtonConst.BTN_ATP);// 可用量
    m_boAllSP = m_boTree.getButton(ITOButtonConst.BTN_AllSP);// 现存量
    m_boAuditStatus = m_boTree
        .getButton(ITOButtonConst.BTN_ASSIST_QUERY_WORKFLOW);// 审批流状态
    m_boOnHandShowHidden = m_boTree
        .getButton(ITOButtonConst.BTN_ASSIST_FUNC_ONHAND);// 现存量显示/隐藏
    m_boCancelRef = m_boTree.getButton(ITOButtonConst.BTN_REF_CANCEL);// 放弃转单
    m_boCancelRef.setVisible(false);// 设置按钮不可见
    // 翻页控件
    m_PageBtnCtrl = new GenPageBtnCtrl(m_boTree);

    // 初始化扩展类----支持扩展类和按钮。
    ButtonObject[] boExtend = null;
    if (m_funcExtend != null) {
      boExtend = m_funcExtend.getExtendButton();
    }

    try {
      if (boExtend != null) {
        ButtonObject boExtTop = m_boTree.getExtTopButton();
        m_boTree.addMenu(boExtTop);
        for (int i = 0; i < boExtend.length; i++) {
          if (boExtend != null)
            m_boTree.addChildMenu(boExtTop, boExtend[i]);
        }
      }
      m_boTree = ToPluginButtonMenu.getInstance().addUIPluginMenu(m_boTree,ConstVO.m_sBillTOORDER,"40093009");
    }
    catch (BusinessException be) {
      Debug.error(be.getMessage());
    }
    // 支持扩展类和按钮结束
  }

  /**
   * 创建日期：(2004-2-10 11:28:46) 作者：王乃军 参数： int iMode,当前编辑状态。区分新增、修改、快速新增、修订等。
   * boolean bCanAddLine ，是否可增行的标志 返回： 说明：设置编辑时的按钮状态
   *
   * @param mode
   *          int
   */
  public void setEditStatus(int iMode, boolean bCanAddLine) {
    if (m_tp != null && m_tp instanceof IBillExtendFun) {
      ((IBillExtendFun) m_tp).setExtendBtnsStat(iMode);
    }
    switch (iMode) {
      case GenEditConst.NEW:
        setButtonStatusOfNew();
        break;
      case GenEditConst.UPDATE:
        setButtonStatusOfUpdate(bCanAddLine);
        break;
    }
  }

  /**
   * 创建日期：(2004-2-10 11:28:46) 作者：lisb 参数： int iCurBillStatus
   * ,根据当前业务单状态来控制改、删、批等。 返回： 说明：设置按钮状态
   *
   * @param mode
   *          int
   */
  private void setBusinessStatus(int iCurBillStatus, String auditorId) {
    switch (iCurBillStatus) {
      // 自由状态下的单据，弃审，冻结，打开,解冻不可用
      case ConstVO.IBillStatus_FREE:
        m_boCopy.setEnabled(true);
        m_boUpdate.setEnabled(true);
        m_boDelete.setEnabled(true);
        m_boAudit.setEnabled(false);
        m_boCancelAudit.setEnabled(false);
        m_boSendAudit.setEnabled(true);
        m_boFreeze.setEnabled(false);
        m_boOpen.setEnabled(false);
        m_boClose.setEnabled(false);
        m_boFresh.setEnabled(false);
        m_boExecute.setEnabled(true);
        m_boReturn.setEnabled(false);
        m_boStockLock.setEnabled(false);
        break;

      // 审批状态下的单据，弃审，冻结,关闭可用，打开,解冻不可用
      case ConstVO.IBillStatus_PASSCHECK:
        m_boCopy.setEnabled(true);
        m_boUpdate.setEnabled(false);
        m_boDelete.setEnabled(false);
        m_boReturn.setEnabled(true);
        m_boAudit.setEnabled(false);
        m_boCancelAudit.setEnabled(true);
        m_boOpen.setEnabled(false);
        m_boClose.setEnabled(true);
        m_boSendAudit.setEnabled(false);
        m_boFreeze.setEnabled(true);
        m_boFresh.setEnabled(false);
        m_boExecute.setEnabled(true);
        m_boStockLock.setEnabled(true);
       
        break;

      case ConstVO.IBillStatus_UNCHECKED: // 审批未通过的单据只能进行修改，作废
        m_boCopy.setEnabled(true);
        m_boUpdate.setEnabled(true);
        m_boDelete.setEnabled(true);
        m_boSendAudit.setEnabled(false);
        m_boAudit.setEnabled(false);
        m_boCancelAudit.setEnabled(false);
        m_boFreeze.setEnabled(false);
        m_boOpen.setEnabled(false);
        m_boClose.setEnabled(false);
        m_boFresh.setEnabled(false);
        m_boExecute.setEnabled(true);
        m_boReturn.setEnabled(false);
        m_boStockLock.setEnabled(false);

        break;

      case ConstVO.IBillStatus_CHECKING: // 审批中
        m_boCopy.setEnabled(true);

        // modified by songhy, 2008-08-07, start
        if (auditorId == null) {
          // 当前单据是“审批中”状态，且“审批人”为空，可修改、删除，即“修改”和“删除”按钮设置可用
          m_boUpdate.setEnabled(true);
          m_boDelete.setEnabled(true);
        }
        else {
          // 当前单据是“审批中”状态，且“审批人”非空，不可改、删除，即“修改”和“删除”按钮设置不可用
          m_boUpdate.setEnabled(false);
          m_boDelete.setEnabled(false);
        }
        // modified by songhy, 2008-08-07, end

        m_boSendAudit.setEnabled(false);
        m_boAudit.setEnabled(true);
        m_boCancelAudit.setEnabled(true);
        m_boReturn.setEnabled(false);

        m_boFreeze.setEnabled(false);
        m_boOpen.setEnabled(false);
        m_boClose.setEnabled(false);
        m_boFresh.setEnabled(false);
        m_boExecute.setEnabled(true);
        m_boStockLock.setEnabled(false);
        break;

      // 关闭状态下的单据，打开可用，弃审，冻结，解冻不可用。
      case ConstVO.IBillStatus_CLOSED:
        m_boCopy.setEnabled(false);
        m_boUpdate.setEnabled(false);
        m_boDelete.setEnabled(false);
        m_boSendAudit.setEnabled(false);
        m_boAudit.setEnabled(false);
        m_boCancelAudit.setEnabled(false);
        // m_boAuditStatus.setEnabled(true);
        m_boOpen.setEnabled(true);
        m_boClose.setEnabled(false);
        m_boFreeze.setEnabled(false);
        m_boFresh.setEnabled(false);
        m_boExecute.setEnabled(true);
        m_boReturn.setEnabled(true);
        m_boStockLock.setEnabled(false);
        break;

      // 冻结状态下的单据，解冻可用，弃审，冻结, 打开不可用
      case ConstVO.IBillStatus_FREEZED:
        m_boCopy.setEnabled(false);
        m_boUpdate.setEnabled(false);
        m_boDelete.setEnabled(false);
        m_boSendAudit.setEnabled(false);
        m_boAudit.setEnabled(false);
        m_boCancelAudit.setEnabled(false);
        m_boOpen.setEnabled(false);
        m_boClose.setEnabled(false);
        m_boFreeze.setEnabled(false);
        m_boFresh.setEnabled(true);
        m_boReturn.setEnabled(false);
        m_boExecute.setEnabled(true);
        m_boStockLock.setEnabled(false);

        break;

    }
  }

  /**
   * 创建日期：(2004-2-10 11:28:46) 作者：王乃军 参数： int iMode,当前编辑状态。 int
   * iCurPanel,当前显示的panel. int iBillTotal,当前的单据数量：控制page down 系列。 int
   * iCurNum,当前指向的单据：控制page down 系列。 int iCurBillStatus ,当前业务单状态。控制改、删、批等。
   * boolean bReject ，能否进行退回 boolean bLogCorp ,调出公司是否为登陆公司 boolean bCopy
   * ，能否进行单据复制 boolean bPlan,能否进行补货安排、发货安排（登陆公司是否等于发货公司） boolean bDelSettlePath
   * 取消结算路径是否可用
   *
   * @param auditorId
   *          审批人 返回： 说明：设置按钮状态
   * @param mode
   *          int
   */
  public void setButtonStatusOfBrowse(int iCurPanel, int iBillTotal,
      int iCurNum, int iCurBillStatus, boolean bReject, boolean bPlan3Z,boolean bPlan4331,
      boolean bDelSettlePath, String auditorId,boolean iqueryStatus) {
    if (m_tp != null && m_tp instanceof IBillExtendFun) {
      ((IBillExtendFun) m_tp).setExtendBtnsStat(GenEditConst.BROWSE);
    }

    m_boCancelRef.setVisible(false);
    m_boBusiType.setEnabled(true);
    m_boNew.setEnabled(true);
    m_boSave.setEnabled(false);
    //取价 add by shipeng at 2014-10-28
    boCalculate.setEnabled(false);
    //end by shipeng at 2014-10-28
    m_boCancel.setEnabled(false);
    m_boQuery.setEnabled(true);
    if(iqueryStatus)
    {m_boRefresh.setEnabled(true);}
    else{
    m_boRefresh.setEnabled(false);
    }
    m_boSwitch.setEnabled(true);
    m_boStockLock.setEnabled(false);
    m_boSelSettlePath.setEnabled(true);
    m_boDelSettlePath.setEnabled(true);

    m_boSendPlan.setEnabled(true);
    m_boFillPlan.setEnabled(true);

    m_boAddLine.setEnabled(false);
    m_boDeleteLine.setEnabled(false);
    m_boCopyLine.setEnabled(false);
    m_boPasteLine.setEnabled(false);
    m_boPasteLineToTail.setEnabled(false);
    m_boInsertLine.setEnabled(false);
    // 还应进一步判断当前的单据是否已签字
    // 同时判断修改、删除是否可用，所以应放在它们的后面。
    m_boBillRowMng.setEnabled(false);
    m_boRefAddLine.setEnabled(false);
    m_boExecute.setEnabled(true);
    // 外设输入支持
    m_boDocument.setEnabled(true);
    m_boJointCheck.setEnabled(true);
    // 控制翻页按钮的状态：
    if (iCurPanel == GenEditConst.LIST) {
      m_PageBtnCtrl.setPageBtnVisible(false);
      m_boBillCombin.setEnabled(false);
    }
    else {
      m_boBillCombin.setEnabled(true);
      m_PageBtnCtrl.setPageBtnVisible(true);
      m_PageBtnCtrl.setPageBtnStatus(iBillTotal, iCurNum);
    }
    // 根据业务单状态判断
    // 有单据
    if (iBillTotal > 0 && iCurNum >= 0) {
      m_boLocate.setEnabled(true);
      m_boPrint.setEnabled(true);
      m_boPreview.setEnabled(true);
      m_boAuditStatus.setEnabled(true);
      setBusinessStatus(iCurBillStatus, auditorId);
      m_boReturn.setEnabled(bReject);
      m_boCopy.setEnabled(true);

      m_boSendPlan.setEnabled(bPlan4331);// 发货安排
      m_boFillPlan.setEnabled(bPlan3Z);// 补货安排

      m_boDelSettlePath.setEnabled(bDelSettlePath);

      m_boBrowse.setEnabled(true);
      m_boPrintMng.setEnabled(true);
      m_boAssistMng.setEnabled(true);
      m_boAssistQuery.setEnabled(true);
      //分单打印按钮状态
      m_splitPrint.setEnabled(true);
    }
    else {
      m_boBillCombin.setEnabled(false);
      m_boLocate.setEnabled(false);
      m_boPrint.setEnabled(false);
      m_boPreview.setEnabled(false);

      m_boCopy.setEnabled(false);
      m_boUpdate.setEnabled(false);
      m_boDelete.setEnabled(false);

      m_boAudit.setEnabled(false);
      m_boCancelAudit.setEnabled(false);
      m_boSendAudit.setEnabled(false);
      m_boAuditStatus.setEnabled(false);

      m_boExecute.setEnabled(false);
      m_boReturn.setEnabled(false);

      m_boSendPlan.setEnabled(false);
      m_boFillPlan.setEnabled(false);
      m_boSelSettlePath.setEnabled(false);
      m_boDelSettlePath.setEnabled(false);
      //分单打印按钮状态
      m_splitPrint.setEnabled(false);
    }

  }

  private void setButtonStatusOfNew() {
    m_boBusiType.setEnabled(false);
    m_boNew.setEnabled(false);
    m_boBillCombin.setEnabled(false);
    m_boCopy.setEnabled(false);
    m_boUpdate.setEnabled(false);
    m_boSelSettlePath.setEnabled(false);
    m_boDelSettlePath.setEnabled(false);
    m_boSendPlan.setEnabled(false);
    m_boFillPlan.setEnabled(false);
    m_boDelete.setEnabled(false);
    m_boSave.setEnabled(true);
    //取价 add by shipeng at 2014-10-28
    boCalculate.setEnabled(true);
    //end by shipeng at 2014-10-28
    m_boSendAudit.setEnabled(false);
    m_boSendAudit.setEnabled(false);
    m_boAuditStatus.setEnabled(false);
    m_boCancel.setEnabled(true);
    m_boQuery.setEnabled(false);
    m_boRefresh.setEnabled(false);
    m_boLocate.setEnabled(false);
    m_boSwitch.setEnabled(false);
    m_boStockLock.setEnabled(false);

    m_boAddLine.setEnabled(true);
    // 退回时可删行
    m_boDeleteLine.setEnabled(true);
    m_boCopyLine.setEnabled(true);
    m_boPasteLine.setEnabled(true);
    m_boPasteLineToTail.setEnabled(true);
    m_boInsertLine.setEnabled(true);

    m_boAudit.setEnabled(false);
    m_boCancelAudit.setEnabled(false);

    m_boBillRowMng.setEnabled(true);
    m_boRefAddLine.setEnabled(true);

    m_boPrint.setEnabled(false);
    m_boPreview.setEnabled(false);
    m_boDocument.setEnabled(false);
    m_boReturn.setEnabled(false);

    // 控制翻页按钮的状态：
    m_PageBtnCtrl.setPageBtnVisible(false);
    m_boJointCheck.setEnabled(false);

    m_boFreeze.setEnabled(false);
    m_boOpen.setEnabled(false);
    m_boClose.setEnabled(false);
    m_boFresh.setEnabled(false);
    m_boExecute.setEnabled(false);
  }

  private void setButtonStatusOfUpdate(boolean bCanAddLine) {
    m_boBusiType.setEnabled(false);
    m_boNew.setEnabled(false);
    m_boBillCombin.setEnabled(false);
    m_boCopy.setEnabled(false);
    m_boUpdate.setEnabled(false);
    m_boSelSettlePath.setEnabled(false);
    m_boDelSettlePath.setEnabled(false);
    m_boSendPlan.setEnabled(false);
    m_boFillPlan.setEnabled(false);
    m_boDelete.setEnabled(false);
    m_boSave.setEnabled(true);
    //取价 add by shipeng at 2014-10-28
    boCalculate.setEnabled(true);
    //end by shipeng at 2014-10-28
    m_boSendAudit.setEnabled(false);
    m_boAuditStatus.setEnabled(false);
    m_boCancel.setEnabled(true);
    m_boQuery.setEnabled(false);
    m_boRefresh.setEnabled(false);
    m_boLocate.setEnabled(false);
    m_boSwitch.setEnabled(false);
    m_boStockLock.setEnabled(false);

    m_boAddLine.setEnabled(bCanAddLine);
    // 退回时可删行
    m_boDeleteLine.setEnabled(true);
    m_boCopyLine.setEnabled(bCanAddLine);
    m_boPasteLine.setEnabled(bCanAddLine);
    m_boPasteLineToTail.setEnabled(bCanAddLine);
    m_boInsertLine.setEnabled(bCanAddLine);

    m_boAudit.setEnabled(false);
    m_boCancelAudit.setEnabled(false);

    m_boBillRowMng.setEnabled(true);
    m_boRefAddLine.setEnabled(true);

    m_boPrint.setEnabled(false);
    m_boPreview.setEnabled(false);

    m_boDocument.setEnabled(false);
    // 控制翻页按钮的状态：
    m_PageBtnCtrl.setPageBtnVisible(false);
    m_boReturn.setEnabled(false);
    m_boJointCheck.setEnabled(false);

    m_boFreeze.setEnabled(false);
    m_boOpen.setEnabled(false);
    m_boClose.setEnabled(false);
    m_boFresh.setEnabled(false);
    m_boExecute.setEnabled(false);

  }

  public void setButtonStatusOfRef() {
    m_boBusiType.setEnabled(false);
    m_boNew.setEnabled(false);
    m_boBillCombin.setEnabled(false);
    m_boCopy.setEnabled(false);
    m_boUpdate.setEnabled(true);
    m_boSelSettlePath.setEnabled(false);
    m_boDelSettlePath.setEnabled(false);
    m_boSendPlan.setEnabled(false);
    m_boFillPlan.setEnabled(false);
    m_boDelete.setEnabled(false);
    m_boSave.setEnabled(false);
    //取价 add by shipeng at 2014-10-28
    boCalculate.setEnabled(false);
    //end by shipeng at 2014-10-28
    m_boSendAudit.setEnabled(false);
    m_boAuditStatus.setEnabled(false);
    m_boCancel.setEnabled(false);
    m_boQuery.setEnabled(false);
    m_boRefresh.setEnabled(false);
    m_boLocate.setEnabled(false);
    m_boSwitch.setEnabled(false);
    m_boStockLock.setEnabled(false);

    m_boAddLine.setEnabled(false);
    //退回时可删行
    m_boDeleteLine.setEnabled(false);
    m_boCopyLine.setEnabled(false);
    m_boPasteLine.setEnabled(false);
    m_boPasteLineToTail.setEnabled(false);
    m_boInsertLine.setEnabled(false);

    m_boAudit.setEnabled(false);
    m_boCancelAudit.setEnabled(false);

    m_boBillRowMng.setEnabled(false);
    m_boRefAddLine.setEnabled(false);

    m_boPrint.setEnabled(false);
    m_boPreview.setEnabled(false);

    m_boDocument.setEnabled(false);
    //控制翻页按钮的状态：
    m_PageBtnCtrl.setPageBtnVisible(false);
    m_boReturn.setEnabled(false);
    m_boJointCheck.setEnabled(false);

    m_boFreeze.setEnabled(false);
    m_boOpen.setEnabled(false);
    m_boClose.setEnabled(false);
    m_boFresh.setEnabled(false);
    m_boExecute.setEnabled(false);
    m_boBrowse.setEnabled(false);
    m_boPrintMng.setEnabled(false);
    m_boAssistMng.setEnabled(false);
    m_boAssistQuery.setEnabled(false);
    m_boCancelRef.setVisible(true);
  }
}