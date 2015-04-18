package nc.ui.to.pubtransfer;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import nc.pub.jyglgt.proxy.Proxy;
import nc.ui.ml.NCLangRes;
import nc.ui.pub.ButtonObject;
import nc.ui.pub.ClientEnvironment;
import nc.ui.pub.beans.MessageDialog;
import nc.ui.pub.beans.UIMenuItem;
import nc.ui.pub.bill.BillCardPanel;
import nc.ui.pub.bill.BillModel;
import nc.ui.pub.bill.BillScrollPane;
import nc.ui.pub.bill.IBillItem;
import nc.ui.pub.change.PfChangeBO_Client;
import nc.ui.pub.msg.PfLinkData;
import nc.ui.pub.pf.PfUtilClient;
import nc.ui.scm.extend.IFuncExtend;
import nc.ui.scm.print.BillPrintTool;
import nc.ui.scm.print.PrintLogClient;
import nc.ui.scm.print.SplitParams;
import nc.ui.scm.print.SplitPrintParamDlg;
import nc.ui.scm.pub.CollectSettingDlg;
import nc.ui.scm.pub.bill.ButtonTree;
import nc.ui.scm.pub.ctrl.GenEditConst;
import nc.ui.scm.pub.panel.RelationsCal;
import nc.ui.scm.pub.panel.SetColor;
import nc.ui.scm.pub.sourceref.SourceRefDlg;
import nc.ui.to.outer.ClientOuterHelper;
import nc.ui.to.outer.SettlePathDlgForIC;
import nc.ui.to.pub.ConstHintMsg;
import nc.ui.to.pub.ExceptionUITools;
import nc.ui.to.pub.RemoteCall;
import nc.ui.to.pub.TOBillBusinessTool;
import nc.ui.to.pub.TOBillTool;
import nc.ui.uap.sf.SFClientUtil;
import nc.vo.jyglgt.pub.Toolkits.Toolkits;
import nc.vo.logging.Debug;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.VOStatus;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.lang.UFDateTime;
import nc.vo.pub.lang.UFDouble;
import nc.vo.scm.ic.ATPVO;
import nc.vo.scm.ic.bill.InvVO;
import nc.vo.scm.ic.exp.ATPNotEnoughException;
import nc.vo.scm.pub.SCMEnv;
import nc.vo.scm.pub.ctrl.GenMsgCtrl;
import nc.vo.scm.pub.ctrl.GenTimer;
import nc.vo.so.so001.SaleorderBVO;
import nc.vo.so.so001.SaleorderHVO;
import nc.vo.so.soreceive.SaleReceiveVO;
import nc.vo.to.pub.ATPWhenDeleteException;
import nc.vo.to.pub.BillHeaderVO;
import nc.vo.to.pub.BillItemVO;
import nc.vo.to.pub.BillVO;
import nc.vo.to.pub.ConstVO;
import nc.vo.to.pub.Log;
import nc.vo.to.pub.tools.ExceptionUtils;
import nc.vo.to.to002.SettlePathVO;
import nc.vo.to.to201.ConstVO201;
import nc.vo.trade.checkrule.VOChecker;

public class ButtonEventHandler {
  private TransferClientUI toftpanel = null;

  // added by songhy, 2008-08-29, start
  ArrayList<Integer> errrows = null; // 保存“可用量不足”的行号，用在方法saveBill中

  // added by songhy, 2008-08-29, end

  private CardPanelCtrl getCardPanelCtrl() {
    return toftpanel.m_CardCtrl;
  }

  private ListPanelCtrl getListPanelCtrl() {
    return toftpanel.m_ListCtrl;
  }

  private Model getModel() {
    return toftpanel.m_Model;
  }

  private ButtonCtrl getButtonCtrl() {
    return toftpanel.m_ButtonCtrl;
  }

  /**
   * ****************************************** 功能：<|> 创建日期：(2004-3-18 8:47:35)
   * 
   * @return： ******************************************
   */
  private void onCopy() throws BusinessException {
    try {
      startEdit();
      //把当前选中业务流程设置到复制后的单据上
      String cbiztypeid = getButtonCtrl().m_boBusiType.getSelectedChildButton()[0]
          .getTag();
      getCardPanelCtrl().copyBill(getModel().getCurRow(), cbiztypeid);
      // 设置表头业务类型
      /*getCardPanelCtrl().setBusiTypeId(
       getButtonCtrl().m_boBusiType.getSelectedChildButton()[0].getTag());*/

      toftpanel.setButtonStatusEdit();
      rightButtonRightControl();
    }
    catch (Exception ex) {
      // 取消新增
      onCancel();
      ExceptionUtils.wrappException(ex);
    }
  }

  /**
   * 进入修改，复制，退回操作这些编辑状态时的一些共同操作 不包括新增(因为新增比较特殊) 创建日期：(2005-11-22 10:18:12)
   */
  private void startEdit() throws BusinessException {
    toftpanel.getDefaultCalbodyID();
    // 当前是列表形式时，首先切换到表单形式
    if (getCurPanel() == GenEditConst.LIST) {
      onSwitch();
    }
    getCardPanelCtrl().startEdit();
    
  }

  private int getCurPanel() {
    return toftpanel.getCurPanel();
  }

  /**
   * 创建日期：(2004-2-10 17:09:40) 作者：王乃军 参数： 返回： 说明：复制行处理。
   */
  private void onCopyLine() {
    getCardPanelCtrl().getCardPanel().getBillCardPanel().stopEditing();
    getCardPanelCtrl().copyLine();
  }

  /**
   * 创建日期：(2004-2-10 17:09:40) 作者：王乃军 参数： 返回： 说明：删除处理。
   */
  private void onDelete() {
    GenTimer timer = new GenTimer();
    timer.start("删除开始");/*-=notranslate=-*/

    ArrayList alVOs = new ArrayList();
    nc.vo.to.pub.BillVO[] voaDelete = null;
    int[] iaSelCount = null;
    String sWarningMsg = null;
    
    try {
    
      // 删除前的数据检查
      try {
        checkBeforeUpdate(Const.ACTION_DELETE, alVOs);
      }
      catch (BusinessException be) {
        sWarningMsg = be.getMessage();
      }
      finally {
        voaDelete = (nc.vo.to.pub.BillVO[]) alVOs.get(0);
        iaSelCount = (int[]) alVOs.get(1);
        if (voaDelete == null || voaDelete.length <= 0) {
          if (sWarningMsg != null && sWarningMsg.length() > 0) {
            showWarningMessage(sWarningMsg);
          }
          return;
        }
      }
      //二次开发扩展
      toftpanel.getPluginProxy().beforeAction(nc.vo.scm.plugin.Action.DELETE, voaDelete);
      switch (nc.ui.pub.beans.MessageDialog
          .showOkCancelDlg(toftpanel, nc.ui.ml.NCLangRes.getInstance()
              .getStrByID("SCMCOMMON", "UPPSCMCommon-000219")/* @res "确认" */,
              nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
                  "UPPSCMCommon-000178")/* @res "你确认删除当前单据吗？" */)) {

        case nc.ui.pub.beans.MessageDialog.ID_OK:
          break;
        case nc.ui.pub.beans.MessageDialog.ID_CANCEL:
          return;
      }
      showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
          "UPPSCMCommon-000318")/* @res "正在删除，请稍候..." */);
    
      // 删除单据
      deleteBills(voaDelete);
      timer.showExecTime("删除");/*-=notranslate=-*/
      //二次开发扩展
      toftpanel.getPluginProxy().afterAction(nc.vo.scm.plugin.Action.DELETE, voaDelete);
      // 删除后的提示
      if (sWarningMsg != null && sWarningMsg.length() > 0) {
        showWarningMessage(sWarningMsg);
      }
      // 从model中删除
      getModel().removeData(iaSelCount);

      // 删除后界面处理。
      if (getModel().getCount() <= 0) {
        // 删除后没有单据了
        getModel().setCurRow(-1);
      }
      else {
        // 如果剩余的单据数量已经小于刚才删除的起始位置，则显示第一张
        if (getModel().getCount() - 1 < iaSelCount[0]) {
          getModel().setCurRow(0);
        }
        else// 显示下一张(刚才删除的位置的那一张)
        {
          getModel().setCurRow(iaSelCount[0]);
        }
      }
      getBillCtrl().displayBill(getModel().getCurRow());

      // 重设按钮状态
      setButtonStatusBrowse(getModel().getCurRow());

      showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010",
          "UPP40093010-000066")/* @res "删除成功！" */);
    }
    catch (Exception e) {
      ExceptionUITools.showMessage(e, toftpanel);
    }
  }

  private void deleteBills(BillVO[] voaDelete) throws Exception {
    for (int i = 0; i < voaDelete.length; i++) {
      voaDelete[i].setOperator(getNodeInfo().getUserID());
      // 放入客户端IP地址,写业务日志用
      voaDelete[i].setIPAdress(java.net.InetAddress.getLocalHost()
          .getHostAddress());
    }
    final String[] saType = new String[] {
      ConstVO.m_sBillDBDD
    };
    ArrayList<BillVO>[] list = new ArrayList[saType.length];
    for (int i = 0; i < saType.length; i++) {
      list[i] = new ArrayList<BillVO>();
    }
    for (int i = 0; i < voaDelete.length; i++) {
      String sTypeCode = voaDelete[i].getBillTypeCode();
      for (int j = 0; j < saType.length; j++) {
        if (sTypeCode.equals(saType[j]))
          list[j].add(voaDelete[i]);
      }
    }

    Object[] objAry = new Object[voaDelete.length];
    objAry[0] = toftpanel.m_clientLink;
    // 调用动作执行脚本执行删除
    for (int i = 0; i < saType.length; i++) {
      if (list[i].size() > 0) {
        BillVO[] voaTemp = new BillVO[list[i].size()];
        voaTemp = (BillVO[]) list[i].toArray(voaTemp);
        try {
          nc.ui.pub.pf.PfUtilClient.processBatch("DELETE", saType[i],
              getNodeInfo().getLogDate(), voaTemp, objAry);
        }
        catch (ATPWhenDeleteException atde) {
          if (atde.getHint() == null) {
            String msg = atde.getMessage();
            throw new BusinessException(msg);
          }
          else {

            int iFlag = toftpanel.showYesNoMessage(atde.getMessage()
                + " \r\n"
                + nc.ui.ml.NCLangRes.getInstance().getStrByID("40093009",
                    "UPP40093009-000203")/*
                 * @res "是否继续删除调拨订单？"
                 */);
            if (iFlag == nc.ui.pub.beans.MessageDialog.ID_YES) {
              // 是否进行可用量检查,设为“否”
              voaDelete[atde.getIndex()].getHeaderVO().setBatpcheck(
                  new UFBoolean(false));
              nc.ui.pub.pf.PfUtilClient.processBatch("DELETE", saType[i],
                  getNodeInfo().getLogDate(), voaTemp, objAry);
            }
            else// 取消单据删除
            {
              throw new BusinessException("");
            }
          }
        }
      }
    }
  }

  private void showWarningMessage(String smsg) {
    toftpanel.showWarningMessage(smsg);
  }

  /*
   * 功能： 修改删除单据前检查是否允许他人修改，是否保存原始制单人等 返回：ArrayList:
   * 1、BillVO[]:检查通过的VO数组；2、通过的VO索引；3、String:不通过的VO错误信息
   */
  private void checkBeforeUpdate(String sAction, ArrayList alData)
      throws BusinessException {
    BillVO[] checkVOs = null;

    int[] iaSelCount;
    if (getCurPanel() == GenEditConst.LIST) {
      iaSelCount = getBillCtrl().getCurSelectIndex();
      // 待检查的VO
      ArrayList alRet = getSelectedBills(false);
      checkVOs = new BillVO[iaSelCount.length];
      checkVOs = (BillVO[]) alRet.toArray(checkVOs);
    }
    else {
      iaSelCount = new int[] {
        getModel().getCurRow()
      };
      checkVOs = new BillVO[] {
        (BillVO) getModel().getCurVO()
      };
    }
    if (iaSelCount == null || iaSelCount.length <= 0) {
      showHintMessage(ConstHintMsg.S_WARNING_CHOSE/* @res "请选择要处理的数据！" */);
      return;
    }
    // 取出参数
    String pk_corp = getNodeInfo().getCorpID();
    // 当前操作员
    String sOperator = getNodeInfo().getUserID().trim();

    // 检查通过的VOs
    BillVO[] passVOs = null;
    int[] ipassCount = null;
    UFBoolean isPermit;

    if (getNodeInfo().getBPerModify() == null) {
      // 是否允许他人修改单据
      String sPermitUpdate = ConstVO.m_sPk_Para[ConstVO.m_iPara_SFYXSGFBR];

      String[] sPara = new String[] {
        sPermitUpdate
      };
      Hashtable htParas = nc.ui.to.pub.ClientCommonDataHelper.getParaValues(
          pk_corp, sPara);
      isPermit = new UFBoolean(htParas.get(sPermitUpdate).toString().trim());
      // 保存到NodeInfo中
      getNodeInfo().setBPerModify(isPermit);
    }
    else {
      isPermit = getNodeInfo().getBPerModify();
    }
    StringBuffer sErrorMsg = new StringBuffer();
    StringBuffer sErrorMsg2 = new StringBuffer();
    // 检查当前VO 是否符合要求
    // 保存符合条件的VO
    ArrayList alVOs = new ArrayList();
    // 符合条件VO的索引
    ArrayList alIndex = new ArrayList();
    for (int i = 0; i < checkVOs.length; i++) {
      // 原始制单人
      String sOriBillMaker = checkVOs[i].getHeaderVO().getCoperatorid().trim();
      // 审批人
      String sAuditorid = checkVOs[i].getHeaderVO().getCauditorid();
      Integer iFstatus = checkVOs[i].getHeaderVO().getFstatusflag();
      if (!sOriBillMaker.equals(sOperator) && !isPermit.booleanValue()) {
        sErrorMsg.append(checkVOs[i].getHeaderVO().getVcode()).append("\n");
      }
      else if (!(iFstatus.intValue()==ConstVO.IBillStatus_FREE
          ||iFstatus.intValue()==ConstVO.IBillStatus_UNCHECKED
          || (VOChecker.isEmpty(sAuditorid)&&iFstatus.intValue()==ConstVO.IBillStatus_CHECKING))) {
        // 只有自由和审批未通过的单据才能修改，删除
        sErrorMsg2.append(checkVOs[i].getHeaderVO().getVcode()).append("\n");

      }
      else {
        alVOs.add(checkVOs[i]);
        alIndex.add(new Integer(iaSelCount[i]));
      }
    }

    // 完全没通过
    if (alVOs.size() == 0) {
      passVOs = null;
      ipassCount = null;
    }
    else if (checkVOs.length == alVOs.size()) {
      passVOs = checkVOs;
      ipassCount = iaSelCount;
    }
    else {
      passVOs = new BillVO[alVOs.size()];
      ipassCount = new int[alIndex.size()];
      passVOs = (BillVO[]) alVOs.toArray(passVOs);
      for (int i = 0; i < alIndex.size(); i++) {
        ipassCount[i] = ((Integer) alIndex.get(i)).intValue();
      }
    }
    if (alData != null) {
      alData.add(passVOs);
      alData.add(ipassCount);
    }

    if (sErrorMsg.toString().trim().length() > 0) {
      String[] value = new String[] {
        String.valueOf(sErrorMsg)
      };
      if (Const.ACTION_DELETE.equals(sAction)) {
        throw new BusinessException(nc.ui.ml.NCLangRes.getInstance()
            .getStrByID("40093010", "UPP40093010-000120", null, value)/* "不能删除他人单据单据号：" */);
      }
      else if (Const.ACTION_UPDATE.equals(sAction)) {
        throw new BusinessException(nc.ui.ml.NCLangRes.getInstance()
            .getStrByID("40093010", "UPP40093010-000123", null, value)/* "此单据不能被他人修改\n单据号: */);
      }
    }
    if (sErrorMsg2.toString().trim().length() > 0) {
      String[] value = new String[] {
        String.valueOf(sErrorMsg2)
      };
      if (Const.ACTION_DELETE.equals(sAction)) {
        throw new BusinessException(nc.ui.ml.NCLangRes.getInstance()
            .getStrByID("topub", "UPPtopub-000114", null, value)/* "由于不能删除非自由或非审批未通过状态的单据，单据号:{0} */);
      }
      else {
        throw new BusinessException(nc.ui.ml.NCLangRes.getInstance()
            .getStrByID("topub", "UPPtopub-000144", null, value)/* "由于不能修改非自由或非审批未通过状态的单据，单据号:{0} */);
      }
    }

  }

  private void afterSaveBill(Object[] objRet, BillVO voBill, String sActionName)
      throws BusinessException {
    BillHeaderVO voHeader = voBill.getHeaderVO();
    BillItemVO[] voaItem = voBill.getItemVOs();
    int iOldEditMode = getCardPanelCtrl().getEditMode();
    getCardPanelCtrl().endUpdate();

    ArrayList alRetData = (ArrayList) objRet[objRet.length - 1];
    // =========================================================
    // 以下把返回的数据放入model中
    if (alRetData != null) {
      // 主表PK
      String sBillid = (String) alRetData.get(0);
      // 单据号
      String sBillcode = (String) alRetData.get(1);
      if (iOldEditMode == GenEditConst.NEW) {
        voHeader.setCbillid(sBillid);
        voHeader.setVcode(sBillcode);
      }
      else if (iOldEditMode == GenEditConst.UPDATE) {
        voHeader.setStatus(VOStatus.UNCHANGED);
        voHeader.setVcode(sBillcode);
        ArrayList alTemp = new ArrayList(voaItem.length);
        for (int item = 0; item < voaItem.length; item++) {
          if (voaItem[item].getStatus() == VOStatus.DELETED) {
            continue;
          }
          voaItem[item].setStatus(VOStatus.UNCHANGED);
          alTemp.add(voaItem[item]);
        }
        voaItem = (BillItemVO[]) alTemp.toArray(new BillItemVO[alTemp.size()]);
      }
      // 返回的其他信息
      HashMap hmRetData = (HashMap) alRetData.get(alRetData.size() - 2);
      // 表头ts
      voHeader.setTs((UFDateTime) hmRetData.get(sBillid
          + ConstVO201.sPostfix_CTS));
      // 把atpcheck的标志置为空
      voHeader.setBatpcheck(null);
      // 单据类型名称
      voHeader.setAttributeValue("billtypename", TOBillTool
          .getOrderNameByCode(voHeader.getCtypecode()));
      String sBillbid = "";
      int iCounter = 2; // start from 2!!!
      Integer headStatus = voHeader.getFstatusflag();
      for (int i = 0; i < voaItem.length; i++) {
        sBillbid = (String) alRetData.get(iCounter + i);
        if (voaItem[i].getCbill_bid() == null) {
          voaItem[i].setAttributeValue("vcode", sBillcode);
          voaItem[i].setAttributeValue("cbillid", sBillid);
          voaItem[i].setCbill_bid(sBillbid);
          voaItem[i].setFrowstatuflag(headStatus);
        }
        // 只更新变更的行
        if (hmRetData.get(sBillbid + ConstVO201.sPostfix_CBTS) != null) {
          voaItem[i].setTs((UFDateTime) hmRetData.get(sBillbid
              + ConstVO201.sPostfix_CBTS));
        }
      }
      voBill.setChildrenVO(voaItem);

      // 刷新model、card 、list
      if (iOldEditMode == GenEditConst.NEW) {
        if (toftpanel.getIsComeFromOther()) {
          // 缓存数据，全部保存完后赋值给m_model,展示到界面中
          int iCount = toftpanel.getModelFrom5A().getCount();
          toftpanel.getModelFrom5A().insertData(iCount, voBill);
          onCancel();
          return;
        }
        else {
          int iCount = getModel().getCount();
          getModel().insertData(iCount, voBill);
          getModel().setCurRow(iCount);
        }
      }
      else if (iOldEditMode == GenEditConst.UPDATE) {
        getModel().setData(getModel().getCurRow(), voBill);
      }

      if (sActionName.equals(ConstVO.sActionSENDAFTERSAVE)) // 送审成功
      {
        getModel().freshTsAndStatus(getModel().getCurRow());
        voBill.getHeaderVO().setCauditorid(null);
        voBill.getHeaderVO().setFstatusflag(ConstVO.IBillStatus_CHECKING);
        getModel().setData(getModel().getCurRow(), voBill);
        // 解析一下表头
        getListPanelCtrl().setListHeadData(
            new CircularlyAccessibleValueObject[] {
              voBill.getHeaderVO()
            });

        showHintMessage(ConstHintMsg.S_ON_SUCCESS_SENDAUDIT);
      }
      // ============================================
      // 只显示卡片界面,为提高效率,不同步列表界面的显示
      getBillCtrl().displayBill(getModel().getCurRow());

      // 同步列表界面界面的显示数据
      getListPanelCtrl().setListHeadData(new CircularlyAccessibleValueObject[] {
        voBill.getHeaderVO()
      });

      if (sActionName.equals(ConstVO.sActionCOMMIT)) // 保存成功
      {
        showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010",
            "UPP40093010-000076")/*
         * @res "保存成功"
         */);
      }

      setButtonStatusBrowse(getModel().getCurRow());

    }
    else {
      setButtonStatusBrowse(-1);
      throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "40093010", "UPP40093010-000077")/*
       * @res "保存单据出错!"
       */);
    }
  }

  private void setButtonStatusBrowse(int iNum) {
    toftpanel.setButtonStatusBrowse(iNum);
  }

  /**
   * 处理审批后传回的VO，取出审批过程中可能改变的信息， 用来刷新前台数据
   * 
   * @param voAudit
   */
  private void processDataAfterApprove(List<BillVO> lstOld, List<BillVO> lstNew) {
    final int len = lstOld.size();
    BillHeaderVO[] voOldHeads = new BillHeaderVO[len];
    BillHeaderVO[] voNewHeads = new BillHeaderVO[len];
    List<BillItemVO> lstOldBody = new ArrayList<BillItemVO>();
    List<BillItemVO> lstNewBody = new ArrayList<BillItemVO>();
    HashMap<String, BillItemVO> hmData = new HashMap<String, BillItemVO>();
    for (int i = 0; i < len; i++) {
      voOldHeads[i] = lstOld.get(i).getHeaderVO();
      voNewHeads[i] = lstNew.get(i).getHeaderVO();
      lstOldBody.addAll(Arrays.asList(lstOld.get(i).getItemVOs()));
      lstNewBody.addAll(Arrays.asList(lstNew.get(i).getItemVOs()));
      for (int j = 0, len2 = lstNew.get(i).getItemVOs().length; j < len2; j++) {
        hmData.put(lstNew.get(i).getItemVOs()[j].getCbill_bid(), lstNew.get(i)
            .getItemVOs()[j]);
      }
    }

    // 1.需要刷新的表头数据
    final String[] saHeadField = new String[] {
        "taudittime", "dauditdate", "ts", "fstatusflag","cauditorid"
    };

    for (int i = 0; i < len; i++) {
      for (int j = 0; j < saHeadField.length; j++) {
        voOldHeads[i].setAttributeValue(saHeadField[j], voNewHeads[i]
            .getAttributeValue(saHeadField[j]));
      }
      // 审批人姓名
      if(VOChecker.isEmpty(voOldHeads[i].getCauditorid())){
        voOldHeads[i].setAttributeValue("cauditorname", null);
      }
      else{
        voOldHeads[i].setAttributeValue("cauditorname", getNodeInfo().getUserName());
      }
    }
    // 解析单据状态名称
    TOBillTool.setNameValueByFlag(new String[] {
      "fstatusflag"
    }, voOldHeads);

    // 2.需要刷新的表体数据
    final String[] saBodyField = new String[] {
        "ts",
        "frowstatuflag",
        // 回写数量
        "noutsumnum", "ntranponum", "norderoutnum", "norderoutassnum",
        "nordershouldoutnum", "norderbacknum", "norderbackassnum",
        "norderinnum", "norderinassnum", "nordersendnum", "nordersendassnum",
        "nordersignnum", "nordersignassnum", "norderinoutnum",
        "norderinoutmny", "nordertakenum", "nordertakemny",
        "norderwaylossassnum", "norderwaylossnum", "ntranpoassnum",
        "nnotinhandastnum", "nnotinhandnum",
        // 标识位
        "bsendendflag", "boutendflag", "bsettleendflag", "bsettleendflag",
        "btakesettleendflag"
    };
    for (int i = 0; i < saBodyField.length; i++) {
      for (int j = 0, ilen = lstOldBody.size(); j < ilen; j++) {
        lstOldBody.get(j).setAttributeValue(
            saBodyField[i],
            hmData.get(lstOldBody.get(j).getCbill_bid()).getAttributeValue(
                saBodyField[i]));
      }
    }
    // 解析单据行状态名称
    BillItemVO[] items = new BillItemVO[lstOldBody.size()];
    items = (BillItemVO[]) lstOldBody.toArray(items);
    TOBillTool.setNameValueByFlag(new String[] {
      "frowstatuflag"
    }, items);
  }

  /**
   * “新增”的时候把主表的pk生成， 下载到客户端
   */
  private String tempHeaderID = null;

  /**
   * 方法功能描述：“新增”的时候把主表的pk生成， 下载到客户端。
   * <b>参数说明</b>
   * @param pk_corp
   * @throws Exception
   * @author sunwei
   * @time 2009-11-20 上午09:06:47
   */
  private void setOID( String pk_corp ) throws Exception {
    if ( tempHeaderID == null ) { 
      Class[] parameterType = new Class[] {String.class};
      Object[] parameterValues = new Object[]{pk_corp};
      try {
        tempHeaderID = (String)RemoteCall.callEJBService("nc.bs.to.pub.CommonDataDMO","getOID",parameterType,parameterValues);
      }
      catch (Exception e) {
        ExceptionUtils.marsh(e);
      }

    }
  }

  /**
   * 方法功能描述：获得生成的主表pk。
   * <b>参数说明</b>
   * @return
   * @author sunwei
   * @time 2009-11-20 上午09:07:28
   */
  private String getOID() { 
    return tempHeaderID;
  }

  /**
   * 方法功能描述：清除下载到客户端的主表pk。保存成功后、断线提示点取消后调用。
   * <b>参数说明</b>
   * @author sunwei
   * @time 2009-11-20 上午09:07:46
   */
  public void clearOID() {
    tempHeaderID = null;
  }

  /**
   * 方法功能描述：设置后台得到的新增单据pk到表头临时字段，后台保存时使用。
   * 解决单据保存后，网络中断，前台报错后可以再次点保存，如果网络中断后后台已经保存，这时再次保存还会生成一张单据内容相同的单据
   * <b>参数说明</b>
   * @param headerVO
   * @throws Exception
   * @author sunwei
   * @time 2009-11-19 下午08:10:18
   */
  private void setPrimeryKey(BillHeaderVO headerVO) throws Exception {
    setOID(headerVO.getCoutcorpid());

    String headpkname = headerVO.getVOMeta().getPkColName();
    headerVO.setAttributeValue(headpkname+"_temp", getOID());
  }

  /**
   * 创建者：王乃军 功能：保存修改的单据 有错误需要以异常抛出，影响主流程 参数： 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 修改日期，修改人，修改原因，注释标志：
   */
  protected void saveBill(String sActionName) throws Exception {
    GenTimer timer = new GenTimer();
    GenTimer timer2 = new GenTimer();
    timer.start();
    timer2.start();
    
    //执行验证公式
    if(!getCardPanelCtrl().getCardPanel().getBillCardPanel().getBillData().execValidateFormulas())
      return;

    BillVO voNew = getSaveBill(sActionName);
    
    //拉式生成的单据设置拉式生成标志，后台判断是否外系统推式生成时，处理为“否”
    if (toftpanel.getIsComeFromOther()) {
      BillHeaderVO header = voNew.getHeaderVO();
      header.setAttributeValue("bpullby5Aflag", true);
    }
    
    //二次开发扩展
    toftpanel.getPluginProxy().beforeAction(nc.vo.scm.plugin.Action.SAVE, new BillVO[]{voNew});
    ArrayList alParam = getSaveParam(voNew);

    Object[] objRet = null;

    BillScrollPane bsp = toftpanel.m_CardCtrl.getCardPanel().getBillCardPanel()
        .getBodyPanel();
    try {
      //设置得到的表头pk
      setPrimeryKey(voNew.getHeaderVO());
      
      objRet = nc.ui.pub.pf.PfUtilClient.processBatch(toftpanel, sActionName,
          voNew.getBillTypeCode(), getNodeInfo().getLogDate(), new BillVO[] {
            voNew
          }, new Object[] {
            alParam
          });
   
    }
    catch (ATPNotEnoughException ane) {
      // add by songhy, 2008-08-06, start
      // 实现功能：调拨订单保存单据时，错误信息所在行背景色变黄
      ArrayList<ATPVO> atpVOs = ane.getAtpVoList();
      if ((atpVOs != null) && (atpVOs.size() > 0)) {
        // 以 ArrayList 形式获得表体中因可用量不足而导致出错的所有行的行号
        errrows = getErrorLines(voNew, atpVOs);
        SetColor.setRowColor(bsp, errrows, Color.YELLOW);
      }
      // add by songhy, 2008-08-06, end

      if (ane.getHint() == null) {
        showErrorMessage(ane.getMessage());
        return;
      }
      else {

        int iFlag = toftpanel.showYesNoMessage(ane.getMessage()
            + " \r\n"
            + nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010",
                "UPP40093010-000130")/*
             * @res "是否继续保存调拨订单？"
             */);
        if (iFlag == nc.ui.pub.beans.MessageDialog.ID_YES) {
          // 是否进行可用量检查,设为“否”
          voNew.getHeaderVO().setBatpcheck(UFBoolean.FALSE);
          objRet = nc.ui.pub.pf.PfUtilClient.processBatch(null, sActionName,
              voNew.getBillTypeCode(), getNodeInfo().getLogDate(),
              new BillVO[] {
                voNew
              }, new Object[] {
                alParam
              });
        }
        else// 取消单据保存
        {
          return;
        }
      }
    }
    
    clearOID();
    
    timer.showExecTime("@@走平台保存：：");/*-=notranslate=-*/
    toftpanel.getPluginProxy().afterAction(nc.vo.scm.plugin.Action.SAVE, new BillVO[]{voNew});
    // ========================================================
    // [[提示信息][new_PK_body1,new_PK_body2,....]]
    // 保存当前单据的OID
    // 返回数据错误 ------------------- EXIT --------------------------
    if (objRet == null || objRet.length <= 0
        || objRet[objRet.length - 1] == null) {
      setButtonStatusBrowse(-1);
      throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "SCMCOMMON", "UPPSCMCommon-000300")/*
       * @res "已经保存，但返回值错误，请重新查询单据。"
       */);
    }
    // 保存后处理
    afterSaveBill(objRet, voNew, sActionName);
    timer2.showExecTime("@@调拨订单保存：：");/*-=notranslate=-*/
  }


  /**
   * @author songhy
   * @param billVO
   *          需要保存的单据 billVO
   * @param atpVOs
   *          可用量不足的VO
   * @return 需要进行提示的表体行行号 ArrayList
   */
  private ArrayList<Integer> getErrorLines(BillVO billVO,
      ArrayList<ATPVO> atpVOs) {
    ArrayList<Integer> errrows = new ArrayList<Integer>();

    // 将可用量不足的存货Id 放入Set中
    Set<String> atpVOIds = new java.util.HashSet<String>();
    for (int i = 0, len = atpVOs.size(); i < len; i++) {
      atpVOIds.add(atpVOs.get(i).getCinventoryid());
    }

    // 记录可用量不足的存货Id所在的表体行行号
    BillItemVO[] billItemVOs = billVO.getItemVOs();
    for (int j = 0, m = billItemVOs.length; j < m; j++) {
      if (atpVOIds.contains(billItemVOs[j].getCoutinvid())) {
        errrows.add(j);
      }
    }

    return errrows;
  }

  private AbstractBillCtrl getBillCtrl() {
    return toftpanel.getBillCtrl();
  }

  protected void onDeleteLine() throws BusinessException {
    // 将删除行的来源id从还从中清楚，以便参照增行时可以再次查询到
    int[] selRow = getCardPanelCtrl().getCardPanel().getBillCardPanel()
        .getBillTable().getSelectedRows();
    String csourcebid = null;
    for (int i = 0; i < selRow.length; i++) {
      csourcebid = (String) getCardPanelCtrl().getCardPanel()
          .getBillCardPanel().getBillModel()
          .getValueAt(selRow[i], "csourcebid");
      toftpanel.getRefBillIds().remove(csourcebid);
    }

    getCardPanelCtrl().deleteLine();
  }

  /**
   * 此处插入方法说明。 创建日期：(2004-2-26 11:05:58)
   */
  private void onDocument() {
    try {
      BillHeaderVO voHead = (BillHeaderVO) getModel().getHeaderVO(
          getModel().getCurRow());
      String sBillid = voHead.getPrimaryKey();
      String sBillType = voHead.getCbilltype();
      // String sVCode = voHead.getVcode();
      nc.ui.scm.file.DocumentManager.showDM(toftpanel, sBillType, sBillid);
    }
    catch (Exception e) {
      nc.vo.scm.pub.ctrl.GenMsgCtrl.printHint(e.getMessage());
    }

  }

  public void onBtnClick(nc.ui.pub.ButtonObject bo) throws Exception {
    GenTimer timer = null;
    // 增加
    if (bo.getParent() == getButtonCtrl().m_boBusiType) {
      onBusiType(bo);
    }
    else if (bo.getParent() == getButtonCtrl().m_boNew) {
      onNew(bo);
    }
    else if (bo == getButtonCtrl().m_boAddLine) {
      onAddLine();
      try {
        //二次开发扩展
        toftpanel.getPluginProxy().onAddLine();
      }
      catch (Exception ex) {
        ExceptionUITools.showMessage( ex,toftpanel);
      }
    }
    else if (bo == getButtonCtrl().m_boCopyLine) {
      onCopyLine();
    }
    else if (bo == getButtonCtrl().m_boPasteLine) {
      onPasteLine();    
      try {
        //二次开发扩展
        toftpanel.getPluginProxy().onPastLine();
      }
      catch (Exception ex) {
        ExceptionUITools.showMessage( ex,toftpanel);
      }
    }
    else if (bo == getButtonCtrl().m_boDeleteLine) {
      onDeleteLine();
    }
    else if (bo == getButtonCtrl().m_boInsertLine) {
      onInsertLine();
    }
    else if (bo == getButtonCtrl().m_boPasteLineToTail) { // 粘贴行到表尾
      onPasteLineToTail();
    }
    else if (bo == getButtonCtrl().m_boCardEdit) { // 卡片编辑
      onCardEdit();
    }
    else if (bo == getButtonCtrl().m_boNewRowNo) { // 重排行号
      onNewRowNo();
    }
    else if (bo == getButtonCtrl().m_boAskPrice) { // 询价
      this.toftpanel.m_CardCtrl.askPrice();
    }
    else if (bo == getButtonCtrl().m_boDRSQ) { // 参照调入申请增行
      onRefDRSQAddLine(bo);
    }
    else if (bo == getButtonCtrl().m_boSwitch) {
      onSwitch();
    }
    else if (bo == getButtonCtrl().m_boQuery) {
      onQuery();
    }
    else if(bo == getButtonCtrl().m_boRefresh){//刷新
      onRefresh();
    }
    else if (bo == getButtonCtrl().m_boUpdate) {
      onUpdate();
    }
    else if (bo == getButtonCtrl().m_boCancel) {
      onCancel();
    }
    else if (bo == getButtonCtrl().m_boSave) {
      timer = new GenTimer();
      timer.start("保存");/*-=notranslate=-*/
      onSave();
      timer.stopAndShow("保存");/*-=notranslate=-*/
    }
	else if (bo == getButtonCtrl().boCalculate){
		//取价 add by shipeng at 2014-10-29
		onBoCalculate();
		//end by shipeng at 2014-10-29
	}
    // 审批
    else if (bo == getButtonCtrl().m_boAudit) {
      timer = new GenTimer();
      timer.start("审批");/*-=notranslate=-*/
      onApprove();
      timer.stopAndShow("审批");/*-=notranslate=-*/
    }
    else if (bo == getButtonCtrl().m_boCancelAudit) {
      timer = new GenTimer();
      timer.start("弃审");/*-=notranslate=-*/
      onUnApprove();
      timer.stopAndShow("弃审");/*-=notranslate=-*/
    }
    else if (bo == getButtonCtrl().m_boDelete) {
      onDelete();
    }
    else if (bo == getButtonCtrl().m_boCopy) {
      onCopy();
    }
    // 退回
    else if (bo == getButtonCtrl().m_boReturn) {
      onReturn();
    }
    // 关闭
    else if (bo == getButtonCtrl().m_boClose) {
      setBillStatus(ConstVO.IBillStatus_CLOSED, nc.ui.ml.NCLangRes
          .getInstance().getStrByID("40093010", "UPT40093010-000028")/*
       * @res
       * "关闭"
       */);
    }
    // 打开后单据状态为审批
    else if (bo == getButtonCtrl().m_boOpen) {
      setBillStatus(ConstVO.IBillStatus_PASSCHECK, nc.ui.ml.NCLangRes
          .getInstance().getStrByID("40093010", "UPT40093010-000029")/*
       * @res
       * "打开"
       */);
    }
    // 解冻后单据状态为审批
    else if (bo == getButtonCtrl().m_boFresh) {
      setBillStatus(ConstVO.IBillStatus_PASSCHECK, nc.ui.ml.NCLangRes
          .getInstance().getStrByID("common", "UC001-0000031")/*
       * @res "解冻"
       */);
    }
    // 冻结
    else if (bo == getButtonCtrl().m_boFreeze) {
      setBillStatus(ConstVO.IBillStatus_FREEZED, nc.ui.ml.NCLangRes
          .getInstance().getStrByID("common", "UC001-0000030")/*
       * @res "冻结"
       */);
    }
    else if (bo == getButtonCtrl().m_boSendAudit) {
      onSendAudit();
    }
    else if (bo == getButtonCtrl().m_boJointCheck) {
      onJointCheck();
    }
    else if (bo == getButtonCtrl().m_boDocument) {
      onDocument();
    }
    else if (bo == getButtonCtrl().m_boLocate) {
      onLocate();
    }
    else if (bo == getButtonCtrl().m_boOnHandShowHidden) {
      onOnHandShowHidden();
    }
    else if (bo == getButtonCtrl().m_boAllATP) {
      onAllATP();
    }
    else if (bo == getButtonCtrl().m_boAllSP) {
      onAllSP();
    }
    else if (bo == getButtonCtrl().m_PageBtnCtrl.getFirst()) {
      onFirst();
    }
    else if (bo == getButtonCtrl().m_PageBtnCtrl.getPre()) {
      onPrevious();
    }
    else if (bo == getButtonCtrl().m_PageBtnCtrl.getNext()) {
      onNext();
    }
    else if (bo == getButtonCtrl().m_PageBtnCtrl.getLast()) {
      onLast();
    }
    else if (bo == getButtonCtrl().m_boPreview) {
      onPreview();
    }
    else if (bo == getButtonCtrl().m_boPrint) {
      onPrint();
    }
    else if (bo == getButtonCtrl().m_boAuditStatus) {
      onQueryAudit();
    }
    else if (bo == getButtonCtrl().m_boStockLock) {
      onStockLock();
    }
    // 发货安排
    else if (bo == getButtonCtrl().m_boSendPlan) {
      onSendPlan();
    }
    // 补货安排
    else if (bo == getButtonCtrl().m_boFillPlan) {
      onFillPlan();
    }
    // 指定结算路径
    else if (bo == getButtonCtrl().m_boSelSettlePath) {
      onSelSettlePath();
    }
    // 增加"分单预览"的方法
    else  if (bo == getButtonCtrl().m_splitPrint) {
        onSplitPreview();
      }
    // 取消结算路径
    else if (bo == getButtonCtrl().m_boDelSettlePath) {
      onDelSettlePath();
    }
    else if (bo == getButtonCtrl().m_boBillCombin) {
      onBillCombin();
    }
    else if (bo == getButtonCtrl().m_boCancelRef) {
      onCancelRef();
    } // GenEditConst.SOURCE代表补货直运界面
    else if (toftpanel.getCurPanel() == GenEditConst.SOURCE) {
      toftpanel.onBHZYBtnClick(bo);
    }
    else {
      // 扩展按钮
      toftpanel.onExtendBtnsClick(bo);
      // 支持功能扩展
      if (getCurPanel() == GenEditConst.CARD)
        ((IFuncExtend) toftpanel).doAction(bo, toftpanel, getCardPanelCtrl()
            .getCardPanel().getBillCardPanel(), getListPanelCtrl()
            .getListPanel().getBillListPanel(),
            nc.ui.scm.extend.IFuncExtend.CARD);
      else if (getCurPanel() == GenEditConst.LIST)
        ((IFuncExtend) toftpanel).doAction(bo, toftpanel, getCardPanelCtrl()
            .getCardPanel().getBillCardPanel(), getListPanelCtrl()
            .getListPanel().getBillListPanel(),
            nc.ui.scm.extend.IFuncExtend.LIST);
    }

    // 单据复制或新增或退回
    if (getCardPanelCtrl().getEditMode() == GenEditConst.NEW
        && !toftpanel.getIsComeFromOther()) {
      getCardPanelCtrl().setItemEdit(-1);
    }
    // 单据修改
    else if (getCardPanelCtrl().getEditMode() == GenEditConst.UPDATE) {
      getCardPanelCtrl().setItemEdit(getModel().getCurRow());
    }
  }

  private void onRefresh() {
    toftpanel.refresh();
}

private void onRefDRSQAddLine(ButtonObject bo) throws Exception {
    // 但前单据的业务流程id
    String cbiztypeid = (String) getCardPanelCtrl().getCardPanel()
        .getBillCardPanel().getHeadItem("cbiztypeid").getValueObject();
    // 1 流程配置中，调拨订单可以由5A拉式生成。
    if (!TOBillBusinessTool.isHaveBizType(ConstVO.m_sBillDRSQ,
        ConstVO.m_sBillDBDD, cbiztypeid)) {
      throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "40093009", "UPP40093009-000239")/* @res "当前单据未配置上游单据为调入申请！" */);
    }
    bo.setTag("5A:" + cbiztypeid);
    
    SourceRefDlg.getCacheToMapAdapter().clear();//清转单界面缓存，从新构造转单界面
    SourceRefDlg.childButtonClicked(bo, getNodeInfo().getCorpID(), null,
        getNodeInfo().getUserID(), ConstVO.m_sBillDBDD, toftpanel);
    if (SourceRefDlg.isCloseOK()) {
      // 单据来源参照返回单据聚合VO或数组
      BillVO[] vos = (BillVO[]) SourceRefDlg.getRetsVos();
      if (vos != null && vos.length > 0) {
        // 设置参照到的单据到卡片界面
        getCardPanelCtrl().setRefBillToCard(vos,toftpanel);
      }
    }
  }

  private void onCancelRef() {
    // 清空缓存的已参照单据表体VO，以便下次参照可以查询到
    toftpanel.getRefBillIds().clear();

    // 设置非参照外部单据
    toftpanel.setIsComeFromOther(false);
    // 把保存的来源于5A的调拨订单复制到Model中
    getModel().setData(toftpanel.getModelFrom5A().getData());
    // 清空缓存
    toftpanel.getModelFrom5A().setData(null);
    // 删除后没有单据了
    if (getModel().getCount() > 0) {
      getModel().setCurRow(0);
    }
    else {
      getModel().setCurRow(-1);
    }

    // 取消修改，结束修改状态。
    getCardPanelCtrl().cancelUpdate();

    // 清空页面数据
    getBillCtrl().displayBill(getModel().getCurRow());

    // 重设按钮状态
    setButtonStatusBrowse(getModel().getCurRow());

    // 更新切换按钮状态
    toftpanel.showBtnSwitch();

    showHintMessage(ConstHintMsg.S_ON_SUCCESS_CANCEL);
  }

  // 补货安排。如果出货公司非当前登陆公司，或审批不通过时 按钮不可用
  private void onFillPlan() throws Exception {
    BillVO vo = (BillVO) getModel().getCurVO();

    BillHeaderVO header = vo.getHeaderVO();
    BillItemVO[] items = vo.getItemVOs();

    ArrayList list = new ArrayList();
    for (int i = 0; i < items.length; i++) {
      // 尚未补货-直运安排结束的调拨订单
      if ((items[i].getBarrangedflag() == null 
          || !items[i].getBarrangedflag().booleanValue())) {
        list.add(items[i]);
      }
    }

    if (list.size() == 0) {
      throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "40093009", "UPP40093009-000240")/*
       * @res
       * "可能表体行已全部补货安排结束，没有表体行可以做补货安排！"
       */);
    }

    BillItemVO[] newItems = new BillItemVO[list.size()];
    newItems = (BillItemVO[]) list.toArray(newItems);

    BillVO newVO = new BillVO();
    newVO.setParentVO(header);
    newVO.setChildrenVO(newItems);

    // 在toftpanel中加载补货直运界面
    toftpanel.addBHZYCardUI(newVO);
  }

  // 发货安排。如果出货公司非当前登陆公司，或审批不通过 按钮不可用
  private void onSendPlan() throws BusinessException {
    BillVO vo = (BillVO) getModel().getCurVO();
    BillHeaderVO header = vo.getHeaderVO();
    BillItemVO[] items = vo.getItemVOs();
    //1。检查时间戳，避免因没有刷新界面，到发货单保存时才报并发
    Class[] parameterType = new Class[] {BillVO.class};
    Object[] parameterValues = new Object[]{vo};
    try {
      RemoteCall.callEJBService("nc.bs.to.pub.CommonDataDMO","checkTimeStamp",parameterType,parameterValues);
    }
    catch (Exception e) {
      ExceptionUtils.marsh(e);
    }
    
    // 2 流程配置中，发货单来源调拨订单。
    if (!TOBillBusinessTool.isHaveBizType(ConstVO.m_sBillDBDD,
        ConstVO.SO_Receive, header.getCbiztypeid())) {
      throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "40093009", "UPP40093009-000241")/* @res "当前单据未配置下游单据为发货单！" */);
    }
    // 1 发货关闭，不可做发货单
    // 3 红字调拨订单不允许做发货
    ArrayList list = new ArrayList();
    for (int i = 0; i < items.length; i++) {
      boolean bsendendflag = items[i].getBsendendflag().booleanValue();
      boolean bretractflag = items[i].getNnum().compareTo(new UFDouble(0)) < 0 ? true
          : false;
      if (!bsendendflag && !bretractflag) {
        list.add(items[i]);
      }
    }
    if (list.size() == 0) {
      throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "40093009", "UPP40093009-000242")/*
       * @res
       * "可能表体行已发货关闭或为红字，没有表体行可以进行发货安排！"
       */);
    }

    BillItemVO[] newItems = new BillItemVO[list.size()];
    newItems = (BillItemVO[]) list.toArray(newItems);

    BillVO newVO = new BillVO();
    newVO.setParentVO(header);
    newVO.setChildrenVO(newItems);

    // VO对照
    SaleReceiveVO sendVO = (SaleReceiveVO) PfChangeBO_Client
        .pfChangeBillToBill(newVO, "5X", "4331");

    PfLinkData linkData = new PfLinkData();
    linkData.setUserObject(sendVO);

    SFClientUtil.openLinkedADDDialog("40060401", toftpanel, linkData);
  }

  private void onNew(ButtonObject bo) throws Exception {
    SourceRefDlg.getCacheToMapAdapter().clear();//清转单界面缓存，从新构造转单界面
    SourceRefDlg.childButtonClicked(bo, getNodeInfo().getCorpID(), null,
        getNodeInfo().getUserID(), ConstVO.m_sBillDBDD, toftpanel);
    if (nc.ui.pub.pf.PfUtilClient.makeFlag) {
      // 自制单据处理
      onNew(getButtonCtrl().m_boBusiType.getSelectedChildButton()[0].getTag());
    }
    else {
      if (SourceRefDlg.isCloseOK()) {
        // 单据来源参照返回单据聚合VO或数组
        BillVO[] vo = (BillVO[]) SourceRefDlg.getRetsVos();

        String tag = bo.getTag();
        int index = tag.indexOf(":");
        String bizType = tag.substring(index + 1, tag.length());

        // 设置业务类型
        setBizType(vo, bizType);

        // 为参照单据设置默认值
        setDefaultData(vo);

        // 缓存单据
        getModel().setData(new ArrayList(Arrays.asList(vo)));

        toftpanel.setReftoListPanel();

        // getCardPanelCtrl().getCardPanel().getBillCardPanel().setCardData(vo);
        // //填充界面
      }
    }

  }

  /**
   * 为参照单据设置默认值
   * 
   * @param vo
   */
  private void setDefaultData(BillVO[] vo) {
    if (vo != null && vo.length > 0) {
      UFDate date = new UFDate(getNodeInfo().getLogDate());
      for (int i = 0; i < vo.length; i++) {
        // 设置计划发货、计划到货日期
        for (int j = 0; j < vo[i].getItemVOs().length; j++) {
          if (vo[i].getItemVOs()[j].getDplanarrivedate() == null) {
            vo[i].getItemVOs()[j].setDplanarrivedate(date);
          }
          if (vo[i].getItemVOs()[j].getDplanoutdate() == null) {
            vo[i].getItemVOs()[j].setDplanoutdate(date);
          }
        }
      }
    }
  }

  /**
   * 为参照单据设置业务流程
   * 
   * @param vo
   * @param bizType
   */
  private void setBizType(BillVO[] vo, String bizType) {
    if (vo != null && vo.length > 0) {
      for (int i = 0; i < vo.length; i++) {
        vo[i].getHeaderVO().setCbiztypeid(bizType);
      }
    }
  }

  private void onBusiType(ButtonObject bo) {
    // 固化以下两行,系统自动刷新增加来源单据按钮
    PfUtilClient.retAddBtn(getButtonCtrl().m_boNew, getNodeInfo().getCorpID(),
        ConstVO.m_sBillDBDD, bo);
  }

  private void onDelSettlePath() throws Exception {
    BillVO bill = (BillVO) getModel().getCurVO();
    if (bill == null) {
      showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("scmcommon",
          "UPPSCMCommon-000167")/* @res "没有选择单据" */);
      return;
    }
    String oldpathid = bill.getHeaderVO().getCsettlepathid();
    if (oldpathid.length() == 0) {
      return;
    }

    bill.setOperator(toftpanel.m_clientLink.getUser());
    bill.getHeaderVO().setCsettlepathid(null);
    bill.getHeaderVO().setCsettlepathname(null);
    bill.getHeaderVO().setStatus(VOStatus.UPDATED);

    BillVO ret = ClientOuterHelper.modifySettlePath(toftpanel.m_clientLink,
        bill);
    getModel().setData(getModel().getCurRow(), ret);

    getBillCtrl().displayBill(getModel().getCurRow());

    if (getCurPanel() == GenEditConst.CARD) {
      getCardPanelCtrl().getCardPanel().getBillCardPanel()
          .execHeadLoadFormulas();
    }
    else {
      getListPanelCtrl().getListPanel().getBillListPanel().getHeadBillModel()
          .execLoadFormula();
    }
    setButtonStatusBrowse(getModel().getCurRow());
  }

  private void onSelSettlePath() throws Exception {
    BillVO bill = (BillVO) getModel().getCurVO();
    if (bill == null) {
      showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("scmcommon",
          "UPPSCMCommon-000167")/* @res "没有选择单据" */);
      return;
    }
    if (bill.getHeaderVO().getCtypecode().equals(ConstVO.m_sBillSFDBDD)) {
      showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("40093009",
          "UPP40093009-000265")/* @res "三方调拨订单不能指定结算路径。" */);
      return;
    }
    String cinCorp = bill.getHeaderVO().getCincorpid(); // 调入公司
    String coutCorp = bill.getHeaderVO().getCoutcorpid(); // 调出公司

    SettlePathDlgForIC dlgModifySettlePath = new SettlePathDlgForIC(cinCorp,
        coutCorp, toftpanel, nc.ui.ml.NCLangRes.getInstance().getStrByID("40093009",
        "UPT40093009-000512")/* @res "指定结算路径" */);

    if (dlgModifySettlePath == null)
      return;

    dlgModifySettlePath.show();
    if (dlgModifySettlePath.getResult() != nc.ui.pub.beans.UIDialog.ID_OK)
      return;

    SettlePathVO[] spvos = dlgModifySettlePath.getReturnVOs();
    String cpathid = dlgModifySettlePath.getSelectedSettlePathID();
    if (spvos == null || spvos.length == 0 || cpathid == null) {
      ExceptionUtils
          .wrappBusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID(
              "40093009", "UPP40093009-000243")/* @res "选择的结算路径为空!" */);
      return;
    }

    SettlePathVO spvo = null;
    for (int i = 0; i < spvos.length; i++) {
      if (spvos[i].getHeadVO().getPrimaryKey().equals(cpathid)) {
        spvo = spvos[i];
        break;
      }
    }
    String cpathname = spvo.getHeadVO().getCpathname();

    if (cpathid == null) {
      SCMEnv.out(nc.ui.ml.NCLangRes.getInstance().getStrByID("40093009",
          "UPP40093009-000244")/* @res "路径空！" */);
      return;
    }

    String oldpathid = bill.getHeaderVO().getCsettlepathid();
    if (cpathid.equalsIgnoreCase(oldpathid)) {
      int ok = showOkCancelMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "40094030", "UPP40094030-000244")/*
       * @res "结算路径没有发生改变！是否继续更新结算路径？"
       */);
      if (ok == 2)
        return;
    }

    bill.setOperator(toftpanel.m_clientLink.getUser());
    bill.getHeaderVO().setCsettlepathid(cpathid);
    bill.getHeaderVO().setCsettlepathname(cpathname);
    bill.getHeaderVO().setStatus(VOStatus.UPDATED);

    BillVO ret = ClientOuterHelper.modifySettlePath(toftpanel.m_clientLink,
        bill);
    getModel().setData(getModel().getCurRow(), ret);

    getBillCtrl().displayBill(getModel().getCurRow());

    setButtonStatusBrowse(getModel().getCurRow());
  }

  /**
   * 显示错误信息
   * 
   * @param err
   *          java.lang.String
   */
  public int showOkCancelMessage(String msg) {
    return MessageDialog.showOkCancelDlg(toftpanel, null, msg);
  }

  /**
   * 函数功能:设置调拨订单的状态 参数: int iChangeStatus ----- 要修改为的状态 String sOperationName
   * ----- 操作名称，如关闭、审批等 返回值: 异常:
   */
  private void setBillStatus(int iChangeStatus, String sOperationName) {
    BillVO voSel = (BillVO) getModel().getCurVO();
    if (voSel == null || voSel.getParentVO() == null) {
      String[] value = new String[] {
        sOperationName
      };
      showWarningMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "40093010", "UPP40093010-000125", null, value));// "当前选中的单据中没有可以进行"
      // +
      // sOperationName
      // +
      // "操作的，请调整");
      return;
    }
    BillHeaderVO voHeader = (BillHeaderVO) voSel.getParentVO();
    // 设置操作员,用于锁单据
    voSel.setOperator(getNodeInfo().getUserID());
    voSel.getHeaderVO().setBatpcheck(new UFBoolean(true));
    // 设置关闭、打开、冻结、解冻的动作
    for (int i = 0; i < voSel.getChildrenVO().length; i++) {
      int iStatu = -1;
      if (sOperationName.equals(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "40093010", "UPT40093010-000028")/* @res "关闭" */)) {
        iStatu = ConstVO.A_CLOSE;
      }
      else if (sOperationName.equals(nc.ui.ml.NCLangRes.getInstance()
          .getStrByID("40093010", "UPT40093010-000029")/* @res "打开" */)) {
        iStatu = ConstVO.A_OPEN;
      }
      else if (sOperationName.equals(nc.ui.ml.NCLangRes.getInstance()
          .getStrByID("common", "UC001-0000030")/* @res "冻结" */)) {
        iStatu = ConstVO.A_FREEZE;
      }
      else if (sOperationName.equals(nc.ui.ml.NCLangRes.getInstance()
          .getStrByID("common", "UC001-0000031")/* @res "解冻" */)) {
        iStatu = ConstVO.A_UNFREEZE;
      }
      ((BillItemVO) voSel.getChildrenVO()[i]).setAction(iStatu);
    }
    String[] sBillIDs = new String[] {
      voHeader.getCbillid()
    };
    try {
      // 调用接口修改状态
      try {
        nc.ui.to.outer.ClientOuterHelper.setBillStatusWithLock("to_bill",
            sBillIDs, iChangeStatus, new BillVO[] {
              voSel
            });
      }
      catch (ATPNotEnoughException ane) {
        if (ane.getHint() == null) {
          showErrorMessage(ane.getMessage());
          return;
        }
        else {

          int iFlag = toftpanel.showYesNoMessage(ane.getMessage()
              + " \r\n"
              + nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010",
                  "UPP40093010-000206", null, new String[] {
                    sOperationName
                  })/*
               * @res "是否继续关闭/打开/冻结/解冻调拨订单？"
               */);
          if (iFlag == nc.ui.pub.beans.MessageDialog.ID_YES) {
            // 是否进行可用量检查,设为“否”
            voSel.getHeaderVO().setBatpcheck(new UFBoolean(false));
            nc.ui.to.outer.ClientOuterHelper.setBillStatusWithLock("to_bill",
                sBillIDs, iChangeStatus, new BillVO[] {
                  voSel
                });
          }
          else// 取消单据删除
          {
            return;
          }
        }
      }

      voHeader.setFstatusflag(iChangeStatus);

      // 刷新mode
      int index = getModel().getCurRow();
      getModel().setData(index, voSel);
      getModel().freshTsAndStatus(index);
      // 界面刷新
      TOBillTool.setNameValueByFlag(new String[] {
        "fstatusflag"
      }, new CircularlyAccessibleValueObject[] {
        getModel().getHeaderVO(index)
      });
      getBillCtrl().displayBill(index);

      setButtonStatusBrowse(index);
      String[] value = new String[] {
        sOperationName
      };
      showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010",
          "UPP40093010-000126", null, value));// sOperationName
      // + "成功");
    }
    catch (Exception e) {
      Log.error(e);
      String[] value = new String[] {
          sOperationName, e.getMessage()
      };
      showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010",
          "UPP40093010-000127", null, value));// sOperationName
      // +
      // "单据状态时出错："
      // +
      // e.getMessage());
    }
  }

  /**
   * 创建日期：(2004-2-10 17:09:40) 作者：王乃军 参数： 返回： 说明：新增行处理。
   */
  protected void onAddLine() {
    getCardPanelCtrl().addLine();
    showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010",
        "UPP40093010-000064")/* @res "增加一行分录" */);
  }

  /**
   * 创建日期：(2004-2-10 17:09:40) 作者：王乃军 参数： 返回： 说明：插入行处理。
   */
  private void onInsertLine() throws Exception {
    getCardPanelCtrl().insertLine();
  }

  /**
   * 点击 “卡片编辑” 按钮的响应方法
   */
  protected void onCardEdit() {

    showHintMessage(ConstHintMsg.S_ON_CARD_EDIT_START); // 表体行卡片编辑开始
    getCardPanelCtrl().getCardPanel().getBillCardPanel().startRowCardEdit();
    showHintMessage(ConstHintMsg.S_ON_CARD_EDIT_END); // 表体行卡片编辑完成
  }

  /**
   * 点击 “重排行号” 按钮的响应方法
   */
  protected void onNewRowNo() {

    nc.ui.scm.pub.report.BillRowNo.addNewRowNo(getCardPanelCtrl()
        .getCardPanel().getBillCardPanel(), toftpanel.getNodeInfo()
        .getBillTypeCode(), "crowno");
    showHintMessage(ConstHintMsg.S_ON_NEW_ROW_NO); // 重排行号完成
  }

  /**
   * 创建日期：(2004-2-10 17:09:40) 作者：王乃军 参数： 返回： 说明：新增行处理。
   */
  protected void onPasteLine() {
    getCardPanelCtrl().pasteLine();   
  }

  /**
   * 按钮“粘贴行到表尾”的响应方法
   */
  protected void onPasteLineToTail() {

    getCardPanelCtrl().pasteLineToTail();
    showHintMessage(ConstHintMsg.S_ON_PASTE_LINE_TO_TAIL); // 提示粘贴行到表尾完成
  }

  /**
   * 此处插入方法说明。 功能描述:在卡片模式下转向第一张。 作者：程起伍 输入参数: 返回值: 异常处理: 日期:(2003-5-27 14:47:24)
   */
  private void onFirst() throws BusinessException {
    doPageChanged(0);
    getButtonCtrl().m_PageBtnCtrl.first(getModel().getCount());
  }

  private void doPageChanged(int cur) {
    int iAll = getModel().getCount();
    if (iAll > 0) {
      getModel().setCurRow(cur);
      getBillCtrl().displayBill(cur);
      setButtonStatusBrowse(cur);
    }
  }

  /**
   * 此处插入方法说明。 功能描述:在卡片模式下指向最后一张 作者：程起伍 输入参数: 返回值: 异常处理: 日期:(2003-5-27 14:48:54)
   */
  private void onLast() {
    int count = getModel().getCount();
    doPageChanged(count - 1);
    getButtonCtrl().m_PageBtnCtrl.last(count);
  }

  /**
   * 此处插入方法说明。 功能描述:在卡片模式下指向下一张 作者：程起伍 输入参数: 返回值: 异常处理: 日期:(2003-5-27 14:48:31)
   */
  private void onNext() {
    int cur = getModel().getCurRow();
    doPageChanged(cur + 1);
    getButtonCtrl().m_PageBtnCtrl.next(getModel().getCount(), cur + 1);
  }

  /**
   * 此处插入方法说明。 功能描述:打印预览 输入参数: 返回值: 异常处理: 日期:(2003-5-27 14:48:02)
   */
  protected void onPreview() {
    // 调出打印窗口
    // 依当前是列表还是表单而定打印内容
    if (getCardPanelCtrl().getEditMode() == GenEditConst.BROWSE) {
      if (getCurPanel() == GenEditConst.CARD) {
        showHintMessage(PrintLogClient.getBeforePrintMsg(true, false));
        // 准备数据
        BillVO voPrint = (BillVO) getModel().getCurVO();

        if (null == voPrint) {
          voPrint = new BillVO();
        }
        if (null == voPrint.getParentVO()) {
          voPrint.setParentVO(new BillHeaderVO());
        }
        if ((null == voPrint.getChildrenVO())
            || (voPrint.getChildrenVO().length == 0)
            || (voPrint.getChildrenVO()[0] == null)) {
          BillItemVO[] ivo = new BillItemVO[1];
          ivo[0] = new BillItemVO();
          voPrint.setChildrenVO(ivo);
        }

        ArrayList alPrintVO = new ArrayList(1);
        alPrintVO.add(voPrint);
        try {
          BillPrintTool printTool = new BillPrintTool(this.toftpanel,
              getNodeInfo().getNodeCode(), alPrintVO, getCardPanelCtrl()
                  .getCardPanel().getBillCardPanel().getBillData(), null,
              getNodeInfo().getCorpID(), getNodeInfo().getUserID(), "vcode",
              "cbillid");

          printTool.onCardPrintPreview(getCardPanelCtrl().getCardPanel()
              .getBillCardPanel(), getListPanelCtrl().getListPanel()
              .getBillListPanel(), getNodeInfo().getBillTypeCode());
          showHintMessage(printTool.getPrintMessage());
        }
        catch (Exception e) {
          GenMsgCtrl.printErr(e.getMessage());
        }

      }
      else { // 列表
        showHintMessage(PrintLogClient.getBeforePrintMsg(true, true));
        if (getModel().getCount() <= 0) {
          return;
        }

        ArrayList alPrintVO = getSelectedBills(true);
        if (alPrintVO == null)
          return;
        BillPrintTool printTool = null;
        try {
          printTool = new BillPrintTool(this.toftpanel, getNodeInfo()
              .getNodeCode(), alPrintVO, getCardPanelCtrl().getCardPanel()
              .getBillCardPanel().getBillData(), null, getNodeInfo()
              .getCorpID(), getNodeInfo().getUserID(), "vcode", "cbillid");


          printTool.onBatchPrintPreview(getListPanelCtrl().getListPanel()
              .getBillListPanel(), getNodeInfo().getBillTypeCode());
          showHintMessage(printTool.getPrintMessage());
        }
        catch (Exception e) {
          GenMsgCtrl.printErr(e.getMessage());
        }
      }
    }
  }

  /**
   * 方法说明：取得选中的单据 创建日期：(2003-03-13 12:59:54) 修改日期： 修改人： 修改原因： 算法说明：
   * 
   * @return java.util.ArrayList
   */
  protected ArrayList getSelectedBills(boolean bParse) {

    int[] iaSelListIndex = getBillCtrl().getCurSelectIndex();
    int iSelCount = iaSelListIndex.length;
    if (iSelCount <= 0) {
      showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010",
          "UPP40093010-000065")/* @res "请先选中单据！" */);
      return null;
    }

    BillVO[] voaBill = new BillVO[iSelCount];
    // 表头PK
    ArrayList<String> alHeadPK = new ArrayList<String>();
    ArrayList<Integer> alIndex = new ArrayList<Integer>();
    for (int i = 0; i < iSelCount; i++) {
      voaBill[i] = (BillVO) getModel().getData(iaSelListIndex[i]);
      // 如果没有表体，则需要查询
      if (voaBill[i].getItemVOs() == null) {
        alHeadPK.add(voaBill[i].getHeaderVO().getCbillid());
        alIndex.add(i);
      }
    }

    // 查询表体数据
    try {
      if(alHeadPK.size()>0){
        BillVO[] voaQuery = nc.ui.to.pub.ClientBillHelper.queryBillByIDs(alHeadPK);
        for (int i = 0, len = alIndex.size(); i < len; i++) {
          //只补充表体,此时补充的表体没有解析公式
          voaBill[alIndex.get(i)].setChildrenVO(voaQuery[i].getItemVOs());
        }
      }
      ArrayList alRet = new ArrayList(Arrays.asList(voaBill));
      if (bParse) {
        // 需要解析所有的表头数据
        getListPanelCtrl().setListHeadData(alRet);
        // 需要解析所有的表体数据
        getListPanelCtrl().setlistBodyData(voaBill.length, alRet);
      }

      return alRet;
    }
    catch (Exception e) {
      nc.vo.scm.pub.ctrl.GenMsgCtrl.printErr(e.getMessage());
    }
    return null;
  }

  private void processDataBeforeUnApprove(List<BillVO> lstUnAudit)
      throws BusinessException {
    StringBuffer sbErr = new StringBuffer("");
    for (int i = 0, len = lstUnAudit.size(); i < len; i++) {
      BillHeaderVO voHead = lstUnAudit.get(i).getHeaderVO();
      int iBillStatus = voHead.getFstatusflag().intValue();

      // V55需求：弃审模式为“逐级弃审”时，审批中状态的单据也可以弃审
      // 弃审模式为“一弃到底”时，只有审批完成状态的单据才能弃审
      // modifier: songhy
      if ((iBillStatus != ConstVO.IBillStatus_CHECKING)
          && (iBillStatus != ConstVO.IBillStatus_PASSCHECK)) {
        // 记录下不需要弃审的单据号
        sbErr.append(i+1).append(
            nc.ui.ml.NCLangRes.getInstance().getStrByID("40093009",
                "UPP40093009-000245")/* @res "、" */);
        continue;
      }
    }
    if (sbErr.toString().trim().length() > 0) {
      sbErr.delete(sbErr.length() - 1, sbErr.length());
      String[] value = new String[] {
        sbErr.toString()
      };
      String error = NCLangRes.getInstance().getStrByID("40093009",
          "UPP40093009-000272", null, value);
      throw new BusinessException(error);
    }
    BillVO[] vos = new BillVO[lstUnAudit.size()];
    vos = lstUnAudit.toArray(vos);
    // 检查审批人和弃审人是否相同
    ArrayList alMsg = TOBillTool.checkApprover(vos, getNodeInfo().getCorpID(),
        getNodeInfo().getUserID(), "cauditorid", "vcode");
    String sMsg = (String) alMsg.get(1);
    if (sMsg != null) {
      throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "topub", "UPPtopub-000126")/*
       * @res "审批人和弃审人必须相同"
       */);
    }
    for (int i = 0, len = lstUnAudit.size(); i < len; i++) {
      BillHeaderVO voHead = lstUnAudit.get(i).getHeaderVO();
      BillItemVO[] voaItem = lstUnAudit.get(i).getItemVOs();
      UFBoolean bTemp = null;

      for (int j = 0; j < voaItem.length; j++) {
        // 检查已调入/出主数量
        if ((voaItem[j].getNorderinnum() != null && voaItem[j].getNorderinnum()
            .doubleValue() != 0.0)
            || (voaItem[j].getNorderoutnum() != null && voaItem[j]
                .getNorderoutnum().doubleValue() != 0.0))
          throw new BusinessException(nc.ui.ml.NCLangRes.getInstance()
              .getStrByID("40093010", "UPP40093010-000091")/*
           * @res
           * "已调入或调出的数量不为空,不能进行弃审!"
           */);
        bTemp = voaItem[j].getBoutendflag();
        if (bTemp != null && bTemp.toString().equals("Y")) {
          throw new BusinessException(nc.ui.ml.NCLangRes.getInstance()
              .getStrByID("40093010", "UPP40093010-000092")/*
           * @res
           * "出库已结束,不能进行弃审!"
           */);
        }
        bTemp = voaItem[j].getBsettleendflag();
        if (bTemp != null && bTemp.toString().equals("Y")) {
          throw new BusinessException(nc.ui.ml.NCLangRes.getInstance()
              .getStrByID("40093010", "UPP40093010-000093")/*
           * @res
           * "结算已完成,不能进行弃审!"
           */);
        }

        voaItem[j].setStatus(VOStatus.UPDATED);
      }

      // 放入当前操作员
      lstUnAudit.get(i).setOperator(getNodeInfo().getUserID());
      // 放入客户端IP地址,写业务日志用
      try {
        lstUnAudit.get(i).setIPAdress(
            java.net.InetAddress.getLocalHost().getHostAddress());
      }
      catch (Exception ex) {
        ExceptionUITools.showMessage(ex, toftpanel);
      }
      // 审批流用:这个审批人是当前操作员，并不是单据的审批人。审批流会根据这个人与单据上的审核人比较，如果不相等，就会报出“没有弃审权限”
      voHead.setCauditorid(getNodeInfo().getUserID());

      // 放入当前日期
      voHead.setDauditdate(new UFDate(getNodeInfo().getLogDate(), false));
      // 把单据状态置为修改
      voHead.setStatus(VOStatus.UPDATED);

    }
  }

  private void showHintMessage(String hint) {
    toftpanel.showHintMessage(hint);
  }

  /**
   * 对当前单据进行合并显示，并可打印 功能： 参数： 返回： 例外： 日期：(2002-9-25 13:51:28) 修改日期:2008-07-22
   * 修改人:张埔铭 修改原因:修改合并显示构造方法 按 v5.5 需求，合并打印按照单据模板打印 注释标志:
   */
  private void onBillCombin() {
    // CollectSettingDlg dlg = new
    // CollectSettingDlg(toftpanel,nc.ui.ml.NCLangRes.getInstance().getStrByID("topub","UPT4009-000007")/*@res
    // "合并显示"*/);
    CollectSettingDlg dlg = new CollectSettingDlg(this.toftpanel, getNodeInfo()
        .getBillTypeCode(), getNodeInfo().getNodeCode(), getNodeInfo()
        .getCorpID(), getNodeInfo().getUserID(), BillVO.class.getName(),
        BillHeaderVO.class.getName(), BillItemVO.class.getName());
    // dlg.setBilltype(toftpanel.getNodeInfo().getBillTypeCode());
    BillCardPanel bcp = getCardPanelCtrl().getCardPanel().getBillCardPanel();
    dlg.initData(bcp, new String[] {
        "cinvcode", "cinvname", "cinvspec", "cinvtype"
    }, // 固定分组列
        null, // new String[]{"dplanarrvdate"},缺省分组列
        new String[] {
            "nnum", "nmny", "nnotaxmny"
        }, // 求和列
        null,// 求平均列
        new String[] {
            "nprice", "nnotaxprice"
        },// 加权平均
        "nnum",// 数量
        new String[] {
          "base"
        });
    dlg.showModal();
  }

  /**
   * 此处插入方法说明。 功能描述:根据表体行的单据ID和单据类型联查上下游单据。 作者：程起伍 输入参数: 返回值: 异常处理:
   * 日期:(2003-4-22 16:09:14)
   */
  private void onJointCheck() {
    String sBillPK = null;
    BillHeaderVO voHead = (BillHeaderVO) getModel().getHeaderVO(
        getModel().getCurRow());
    if (voHead == null) {
      showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010",
          "UPP40093010-000067")/* @res "没有选择单据！" */);
      return;
    }
    String sBillTypeCode = voHead.getCbilltype();
    String vBillCode = voHead.getVcode();
    sBillPK = (String) (getModel().getHeaderVO(getModel().getCurRow())
        .getAttributeValue("cbillid"));
    if (sBillPK == null || sBillTypeCode == null) {
      showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010",
          "UPP40093010-000068")/* @res "该行没有对应单据！" */);
      return;
    }
    nc.ui.scm.sourcebill.SourceBillFlowDlg soureDlg = new nc.ui.scm.sourcebill.SourceBillFlowDlg(
        toftpanel, sBillTypeCode, sBillPK, null, getNodeInfo().getUserID(),
        vBillCode);

    soureDlg.showModal();
  }

  /**
   * 此处插入方法说明。 创建日期：(2004-2-26 11:06:14)
   */
  private void onLocate() {
    getBillCtrl().onLocate();
  }

  /**
   * 创建日期：(2004-2-10 17:09:40) 作者：王乃军 参数： 返回： 说明：新增处理。
   */
  private void onNew(String cbusitype) throws Exception {
    try {
      if (getCurPanel() == GenEditConst.LIST)
        onSwitch();
    
      getCardPanelCtrl().newBlankBill(cbusitype);

      getCardPanelCtrl().setDefaultCorpID();
      toftpanel.setButtonStatusEdit();
      
      rightButtonRightControl();
      getCardPanelCtrl().getCardPanel().getBillCardPanel().transferFocusTo(
          IBillItem.HEAD);
    }
    catch (BusinessException be) {
      // 取消新增
      onCancel();
      throw be;
    }
  }

 private void rightButtonRightControl() {
    String[] saTableCodes = new String[] { "base", "relation", "exec2",
        "free", "produce" };
    for (int j = 0; j < saTableCodes.length; j++) {
    UIMenuItem[] bodyMenuItems = getBillCardPanel().getBodyMenuItems(saTableCodes[j]);

    for (int i = 0; i < bodyMenuItems.length; i++) {

      String sButtonName = bodyMenuItems[i].getText().toString();
      if (ButtonTree.BUTTON_NULL != toftpanel.m_boBtnTree.getButton(sButtonName))
        if(!toftpanel.m_boBtnTree.getButton(sButtonName).getParent().isPower()
            || (toftpanel.m_boBtnTree.getButton(sButtonName).getParent().isPower()
                && !(toftpanel.m_boBtnTree.getButton(sButtonName).isPower()))){
          bodyMenuItems[i].setEnabled(false);
      }
    }

    }
  }

  private BillCardPanel getBillCardPanel(){
    return toftpanel.m_CardCtrl.getCardPanel().getBillCardPanel();
  }

  /**
   * 函数功能:存量查询 参数: 返回值: 异常:
   */
  protected void onOnHandShowHidden() {
    // 存量查询界面只在卡片界面显示
    if (getCurPanel() == GenEditConst.LIST)
      onSwitch();
    getCardPanelCtrl().setOnhandShowHiddenChanged();
    toftpanel.updateUI();
  }

  /**
   * 此处插入方法说明。 功能描述:在卡片模式下指向前一张 作者：程起伍 输入参数: 返回值: 异常处理: 日期:(2003-5-27 14:48:02)
   */
  protected void onPrevious() {
    int iCur = getModel().getCurRow();
    doPageChanged(iCur - 1);
    getButtonCtrl().m_PageBtnCtrl.previous(getModel().getCount(), iCur);
  }

  /**
   * 函数功能:打印 参数: 返回值: 异常:
   */
  protected void onPrint() {
    // 调出打印窗口
    // 依当前是列表还是表单而定打印内容
    if (getCardPanelCtrl().getEditMode() == GenEditConst.BROWSE) {
      if (getCurPanel() == GenEditConst.CARD) {
        showHintMessage(PrintLogClient.getBeforePrintMsg(false, false));
        // 准备数据
        BillVO voPrint = (BillVO) getModel().getCurVO();

        if (null == voPrint) {
          return;
        }

        ArrayList alPrintVO = new ArrayList(1);
        alPrintVO.add(voPrint);
        try {
          BillPrintTool printTool = new BillPrintTool(this.toftpanel,
              getNodeInfo().getNodeCode(), alPrintVO, getCardPanelCtrl()
                  .getCardPanel().getBillCardPanel().getBillData(), null,
              getNodeInfo().getCorpID(), getNodeInfo().getUserID(), "vcode",
              "cbillid");

          printTool.onCardPrint(getCardPanelCtrl().getCardPanel()
              .getBillCardPanel(), getListPanelCtrl().getListPanel()
              .getBillListPanel(), getNodeInfo().getBillTypeCode());
          showHintMessage(printTool.getPrintMessage());
        }
        catch (Exception e) {
          GenMsgCtrl.printErr(e.getMessage());
        }

      }
      else { // 列表
        showHintMessage(PrintLogClient.getBeforePrintMsg(false, true));
        if (getModel().getCount() <= 0) {
          return;
        }

        ArrayList alPrintVO = getSelectedBills(true);
        if (alPrintVO == null)
          return;

        try {
          BillPrintTool printTool = new BillPrintTool(this.toftpanel,
              getNodeInfo().getNodeCode(), alPrintVO, getCardPanelCtrl()
                  .getCardPanel().getBillCardPanel().getBillData(), null,
              getNodeInfo().getCorpID(), getNodeInfo().getUserID(), "vcode",
              "cbillid");

          printTool.onBatchPrint(getListPanelCtrl().getListPanel()
              .getBillListPanel(), getNodeInfo().getBillTypeCode());
          showHintMessage(printTool.getPrintMessage());
        }
        catch (Exception e) {
          Debug.debug(e.getMessage(), e);
        }
      }
    }
  }

  /**
   * 创建日期：(2004-2-10 18:38:37) 作者：王乃军 参数： 返回： 说明：查询
   */
  protected void onQuery() {
    toftpanel.query();
  }

  /**
   * ****************************************** 功能：审批状态查询(调用审批流平台的查询对话框)
   * 创建日期：(2004-3-11 14:19:33)
   * 
   * @param：
   * @return： ******************************************
   */
  private void onQueryAudit() throws Exception {

    if (getModel().getCurRow() < 0) {
      showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010",
          "UPP40093010-000088")/* @res "请选择一张单据!" */);
      return;
    }
    BillVO vo = (BillVO) getModel().getCurVO();
    nc.ui.pub.workflownote.FlowStateDlg dlgAuditStatus = new nc.ui.pub.workflownote.FlowStateDlg(
        toftpanel, vo.getBillTypeCode(), vo.getParentVO().getPrimaryKey());
    dlgAuditStatus.showModal();

  }

  /**
   * 点“退回”按钮时触发 创建日期：(2005-11-22 9:28:41)
   */
  protected void onReturn() throws Exception {
    try {
      startEdit();
      //把当前选中业务流程设置到复制后的单据上
      String cbiztypeid = getButtonCtrl().m_boBusiType.getSelectedChildButton()[0]
          .getTag();
      getCardPanelCtrl().returnBill(getModel().getCurRow(), cbiztypeid);
      toftpanel.setButtonStatusEdit();
    }
    catch (Exception be) {
      // 取消退回
      onCancel();
      throw be;

    }
  }

  boolean b = false;

  /**
   * 创建日期：(2004-2-10 18:38:37) 作者：王乃军 参数： 返回：
   * 说明：保存，区分为新增保存和修改保存。也可以统一为EDIT状态。通过其他来标志出是新增还是修改。
   */
  protected void onSave() throws Exception {
    saveBill(ConstVO.sActionCOMMIT);
  }

	/**
	 * 功能：取价
	 * @author shipeng
	 * @param onBoCalculate
	 * @time 2014-10-11
	 * */
	private void onBoCalculate() {
		//产品线（存货档案自动带出）、日期时间的有效区间，地区（客户基本档案的自定义项一），存货、自由项、质量等级、是否含税
//		if (( getCardPanelCtrl().getCardPanel().getBillCardPanel().getHeadItem("pk_defdoc8") != null
//				&& (getCardPanelCtrl().getCardPanel().getBillCardPanel().getHeadItem("pk_defdoc8").getValueObject()== null||"".equals(getCardPanelCtrl().getCardPanel().getBillCardPanel().getHeadItem("pk_defdoc8").getValueObject())))||
//				(getCardPanelCtrl().getCardPanel().getBillCardPanel().getHeadItem("vdef8") != null
//				&&(getCardPanelCtrl().getCardPanel().getBillCardPanel().getHeadItem("vdef8").getValueObject()== null||"".equals(getCardPanelCtrl().getCardPanel().getBillCardPanel().getHeadItem("vdef8").getValueObject())))) {
//			showWarningMessage("价格地区不可为空");
//			getCardPanelCtrl().getCardPanel().getBillCardPanel().getHeadItem("pk_defdoc8").setValue("");
//			return;
//		}
//		Object pk_addprice=getCardPanelCtrl().getCardPanel().getBillCardPanel().getHeadItem("pk_defdoc8").getValueObject();
	    UFDate dbilldate = new UFDate(getCardPanelCtrl().getCardPanel().getBillCardPanel().getHeadItem("dbilldate").getValue());
	    String time=ClientEnvironment.getServerTime().getTime();
		Object vdef9=getCardPanelCtrl().getCardPanel().getBillCardPanel().getHeadItem("vdef9").getValueObject();
//		if(vdef9==null||"".equals(vdef9)){
//			showWarningMessage("请选择是否含税");
//			return;
//		}
		int rowcount = getCardPanelCtrl().getCardPanel().getBillCardPanel().getRowCount(); 
		for (int i = 0; i < rowcount; i++){
			Object cinvbasdocid=getCardPanelCtrl().getCardPanel().getBillCardPanel().getBodyValueAt(i, "cinvbasid");
			Object ic_cqualitylevelid=getCardPanelCtrl().getCardPanel().getBillCardPanel().getBodyValueAt(i, "ic_cqualitylevelid");
          if(cinvbasdocid==null||"".equals(cinvbasdocid)){
          	if(i==0){
          		showWarningMessage("表体没有存货信息");
          		return;
          	}
  			continue;
          }
			
			Object vfree1=getCardPanelCtrl().getCardPanel().getBillCardPanel().getBodyValueAt(i, "vfree1");
			Object vfree2=getCardPanelCtrl().getCardPanel().getBillCardPanel().getBodyValueAt(i, "vfree2");
			Object vfree3=getCardPanelCtrl().getCardPanel().getBillCardPanel().getBodyValueAt(i, "vfree3");
			Object vfree4=getCardPanelCtrl().getCardPanel().getBillCardPanel().getBodyValueAt(i, "vfree4");
          StringBuffer sql=new StringBuffer();
          sql.append(" select  a.pk_corp dec,b.hsprice,b.vdef1,a.vbillcode,a.pk_pricepolicy  ") 
          .append("   from gt_pricepolicy a ") 
          .append("  inner join gt_pricepolicy_b b on a.pk_pricepolicy = b.pk_pricepolicy ") 
          .append("  where billtype = 'JY03' ") 
          .append("    and nvl(a.dr, 0) = 0 ") 
          .append("    and nvl(b.dr, 0) = 0 ") 
          .append("    and a.vbillstatus = 1 ") 
          .append("    and (a.pk_corp = '"+getNodeInfo().getCorpID()+"' or a.pk_corp = '0001') ") 
          .append("    and b.vdef12 = '"+cinvbasdocid+"' ") 
//          .append("    and b.vdef11 = '"+pk_addprice+"' ") 
          .append("    and a.sdate <= '"+dbilldate+"' ") 
          .append("    and '"+dbilldate+"'<=a.edate ") 
          .append("    and a.stime<='"+time+"' ") 
          .append("    and '"+time+"'<=a.etime ") ;
  		if(!Toolkits.isEmpty(ic_cqualitylevelid)){
  			sql.append( " and b.pk_level ='"+ic_cqualitylevelid+"'");
  		}
  		else{
  			sql.append( " and b.pk_level is null ");
  		}
  		if(!Toolkits.isEmpty(vfree1)){
  			sql.append( " and ((decode(free1_2,0,free1_0)<="+vfree1)
  			.append( " and decode(free1_2,0,free1_1)>="+vfree1+" )or ")
  			.append( " (decode(free1_2,1,free1_0)<="+vfree1)
  			.append( " and decode(free1_2,1,free1_1)>"+vfree1+")or ")
  			.append( " (decode(free1_2,2,free1_0)<"+vfree1)
  			.append( " and decode(free1_2,2,free1_1)>="+vfree1+")or")
  			.append( " (decode(free1_2,3,free1_0)<"+vfree1)
  			.append( " or decode(free1_2,3,free1_1)>"+vfree1+"))");	
  		}
  		if(!Toolkits.isEmpty(vfree2)){
  			sql.append( " and ((decode(free2_2,0,free2_0)<="+vfree2)
  			.append( " and decode(free2_2,0,free2_1)>="+vfree2+" )or ")
  			.append( " (decode(free2_2,1,free2_0)<="+vfree2)
  			.append( " and decode(free2_2,1,free2_1)>"+vfree2+")or ")
  			.append( " (decode(free2_2,2,free2_0)<"+vfree2)
  			.append( " and decode(free2_2,2,free2_1)>="+vfree2+")or")
  			.append( " (decode(free2_2,3,free2_0)<"+vfree2)
  			.append( " or decode(free2_2,3,free2_1)>"+vfree2+"))");	
  		}
  		if(!Toolkits.isEmpty(vfree3)){
  			sql.append( " and ((decode(free3_2,0,free3_0)<="+vfree3)
  			.append( " and decode(free3_2,0,free3_1)>="+vfree3+" )or ")
  			.append( " (decode(free3_2,1,free3_0)<="+vfree3)
  			.append( " and decode(free3_2,1,free3_1)>"+vfree3+")or ")
  			.append( " (decode(free3_2,2,free3_0)<"+vfree3)
  			.append( " and decode(free3_2,2,free3_1)>="+vfree3+")or")
  			.append( " (decode(free3_2,3,free3_0)<"+vfree3)
  			.append( " or decode(free3_2,3,free3_1)>"+vfree3+"))");	
  		}
  		if(!Toolkits.isEmpty(vfree4)){
  			sql.append( " and ((decode(free4_2,0,free4_0)<="+vfree4)
  			.append( " and decode(free4_2,0,free4_1)>="+vfree4+" )or ")
  			.append( " (decode(free4_2,1,free4_0)<="+vfree4)
  			.append( " and decode(free4_2,1,free4_1)>"+vfree4+")or ")
  			.append( " (decode(free4_2,2,free4_0)<"+vfree4)
  			.append( " and decode(free4_2,2,free4_1)>="+vfree4+")or")
  			.append( " (decode(free4_2,3,free4_0)<"+vfree4)
  			.append( " or decode(free4_2,3,free4_1)>"+vfree4+"))");	
  		}
  		 ArrayList list;
			try {
				list = Proxy.getItf().queryArrayBySql(sql.toString());
	    		 if(list!=null&&list.size()>0){
	        		 String key="nprice"; 
	        		 Object price=null;
	        		 HashMap<String, String>map=(HashMap<String,String>)list.get(0);
//	        		 if(i==0){
//	        			 getBillCardPanel().getHeadItem("pk_defdoc13").setValue(map.get("pk_pricepolicy"));
//	        			 getBillCardPanel().getHeadItem("vdef13").setValue(map.get("vbillcode"));
//	        		 }
	        		 getCardPanelCtrl().getCardPanel().getBillCardPanel().setBodyValueAt(map.get("vbillcode"), i,"vdef13");
	        		 getCardPanelCtrl().getCardPanel().getBillCardPanel().setBodyValueAt(map.get("pk_pricepolicy"), i, "pk_defdoc13");
	        		 if(vdef9==null||"".equals(vdef9)||"N".equals(vdef9)||"否".equals(vdef9)){
	        			price=map.get("vdef1"); 
	        		 }else{
	        			price=map.get("hsprice");	        			
	        		 }
	        		 UFDouble 	nnum=Toolkits.getUFDouble(getCardPanelCtrl().getCardPanel().getBillCardPanel().getBodyValueAt(i, "nnum"));
	        		 UFDouble   mny=nnum.multiply(Toolkits.getUFDouble(price));
	        		 getCardPanelCtrl().getCardPanel().getBillCardPanel().setBodyValueAt(Toolkits.getUFDouble(price), i, key);
	        		 getCardPanelCtrl().getCardPanel().getBillCardPanel().setBodyValueAt(Toolkits.getUFDouble(price), i, "nnotaxprice");
	        		 getCardPanelCtrl().getCardPanel().getBillCardPanel().setBodyValueAt(Toolkits.getUFDouble(price), i, "nprice");
	        		 getCardPanelCtrl().getCardPanel().getBillCardPanel().setBodyValueAt(mny, i, "nnotaxmny");
	        		 getCardPanelCtrl().getCardPanel().getBillCardPanel().setBodyValueAt(mny, i, "nmny");
	        		 getCardPanelCtrl().getCardPanel().getBillCardPanel().setBodyValueAt(new UFDouble(1), i, "ntaxrate");
	    		 }else{
	            		showWarningMessage("没有取到价格，请确定是否维护好价格政策");
	            		return;
	    		 }
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
  /**
   * 函数功能:点击“送审”按钮时触发 参数: 返回值: 异常:
   */
  protected void onSendAudit() throws Exception {

    // 编辑状态下,调用保存并送审的动作
    if (getCardPanelCtrl().getEditMode() == GenEditConst.NEW
        || getCardPanelCtrl().getEditMode() == GenEditConst.UPDATE) {
      saveBill(ConstVO.sActionSENDAFTERSAVE);
    }
    // 浏览状态下,只调用单纯的送审动作
    else {
      BillVO voBill = (BillVO) getModel().getCurVO();
      if (voBill == null || voBill.getParentVO() == null) {
        showErrorMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
            "SCMCOMMON", "UPPSCMCommon-000199")/* @res "请选择单据" */);
        return;
      }
      if (!getNodeInfo().getUserID().equals(
          voBill.getHeaderVO().getCoperatorid())) {
        showErrorMessage(NCLangRes.getInstance().getStrByID("scmcommon",
            "UPPSCMCommon-000080")/* @res "您不是此单的制单人，不能送审" */);
        return;
      }

      voBill = toftpanel.sendAudit(voBill);

      showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010",
          "UPP40093010-000074")/* @res "送审成功" */);
      int iNewStatus = voBill.getHeaderVO().getFstatusflag().intValue();
      if (iNewStatus == ConstVO.IBillStatus_CHECKING) {
        getModel().setData(getModel().getCurRow(), voBill);
        // 解析一下表头
        getListPanelCtrl().setListHeadData(
            new CircularlyAccessibleValueObject[] {
              voBill.getHeaderVO()
            });
      }

      getBillCtrl().displayBill(getModel().getCurRow());
      setButtonStatusBrowse(getModel().getCurRow());
    }

  }

  private void showErrorMessage(String smsg) {
    toftpanel.showErrorMessage(smsg);
  }

  private void showUnknownErrorMessage(Exception e) {
    MessageDialog.showUnknownErrorDlg(toftpanel, e);
  }

  /**
   * 此处插入方法说明。 功能描述:在卡片模式下转向第一张。 作者：程起伍 输入参数: 返回值: 异常处理: 日期:(2003-5-27 14:47:24)
   */
  protected void onStockLock() throws Exception {
    try {
      // 取出要审核的单据
      // 库存硬锁定只能锁定一次。刘佳清语
      BillVO voBill = (BillVO) getModel().getCurVO();
      voBill.setOperator(getNodeInfo().getUserID());
      nc.ui.pub.pf.PfUtilClient.processActionNoSendMessage(toftpanel,
          "STOCKLOCK", voBill.getBillTypeCode(), getNodeInfo().getLogDate(),
          voBill, null, null, null);

      //刷新表体时间戳
      StringBuffer sql = new StringBuffer(" ");
      sql.append(" cbillid = '");
      sql.append(voBill.getHeaderVO().getPrimaryKey());
      sql.append("' and dr=0 ");
      BillItemVO[] itemVOs = nc.ui.to.pub.ClientBillHelper.queryBodyBySQL(
          new String[] {
              "cbill_bid", "ts"
          }, sql.toString());
      HashMap<String, UFDateTime> mapTS = new HashMap<String, UFDateTime>();
      for (BillItemVO item : itemVOs) {
        mapTS.put(item.getCbill_bid(), item.getTs());
      }

      BillItemVO[] oldItemVOs = voBill.getItemVOs();
      for (BillItemVO item : oldItemVOs) {
        item.setTs(mapTS.get(item.getCbill_bid()));
      }
      getModel().setData(getModel().getCurRow(), voBill);
      getBillCtrl().displayBill(getModel().getCurRow());
      // showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID(
      // "40093010", "UPP40093010-000082")/* @res "订单已经成功锁定！" */);
      //
    }
    catch (Exception e) {
      throw e;
    }

  }

  /**
   * 创建日期：(2004-2-10 17:09:40) 作者：王乃军 参数： 返回： 说明：Panel切换处理。
   */
  protected void onSwitch() {
    toftpanel.switchPanel();
  }

  /**
   * ****************************************** 功能：审核单据 创建日期：(2004-3-10
   * 13:10:37)
   * 
   * @param：
   * @return： ******************************************
   */
  protected void onUnApprove() {

    showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010",
        "UPP40093010-000089")/* @res "正在弃审" */);
    // 取出要弃审的单据，参数为false，不解析表头表体公式，减小流量。可以考虑切换表头时解析表体公式
    ArrayList lstUnAudit = getSelectedBills(false);
    List<BillVO> lstRet = null;
    try {
      processDataBeforeUnApprove(lstUnAudit);
      lstRet = toftpanel.unApproveBill(lstUnAudit);
    }
    catch (BusinessException ex) {
      ExceptionUITools.showMessage(ex, toftpanel);
    }

    // 刷新审批中改变的信息
    if (lstRet != null && lstRet.size() > 0 && lstUnAudit != null) {
      processDataAfterApprove(lstUnAudit, lstRet);

      int[] iaSelListIndex = getBillCtrl().getCurSelectIndex();
      for (int i = 0; i < iaSelListIndex.length; i++) {
        getModel().setData(iaSelListIndex[i], (BillVO) lstUnAudit.get(i));
      }
      getBillCtrl().displayBill(getModel().getCurRow());
      setButtonStatusBrowse(getModel().getCurRow());

      showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010",
          "UPP40093010-000096")/* @res "弃审成功" */);
    }
    else {
      showHintMessage("");
    }
  }

  /**
   * 创建日期：(2004-2-10 17:09:40) 作者：王乃军 参数： 返回： 说明：修改动作处理。
   */
  protected void onUpdate() throws Exception {
    showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
        "UPPSCMCommon-000315")/* @res "正在加载需要的数据，请稍候..." */);
   
    if (!toftpanel.getIsComeFromOther()) {
      // 1、BillVO[]:检查通过的VO数组；2、通过的VO索引；3、String:不通过的VO错误信息
      checkBeforeUpdate(Const.ACTION_UPDATE, null);
    
      startEdit();
      // ---
      getCardPanelCtrl().startUpdate();
      // 使设置生效,必须！
      toftpanel.setButtonStatusEdit();
      rightButtonRightControl();
      getCardPanelCtrl().getCardPanel().getBillCardPanel().transferFocusTo(
          IBillItem.HEAD);
    }
    else {
    
      toftpanel.getDefaultCalbodyID();
      // 当前是列表形式时，首先切换到表单形式
      if (getCurPanel() == GenEditConst.LIST) {
        // 设置选定的单据到界面上
        toftpanel.onSwitchForRef();
      }
      getCardPanelCtrl().startEdit();
      rightButtonRightControl();

      // 卡片通过参照数据新增单据
      getCardPanelCtrl().newBillByRef();
      // 使设置生效,必须！
      toftpanel.setButtonStatusEdit();
      getCardPanelCtrl().getCardPanel().getBillCardPanel().transferFocusTo(
          IBillItem.HEAD);
    }

    setModifiedRowsColor();

    showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("SCMCOMMON",
        "UPPSCMCommon-000133")/* @res "就绪" */);

  }

  /**
   * 函数功能:ATP查询 参数: 返回值: 异常:
   */
  private void onAllATP() {
    getBillCtrl().onAllATP();
  }

  /**
   * 函数功能:现存量查询 参数: 返回值: 异常:
   */
  private void onAllSP() {
    // 查询现存量：存货+公司+库存组织+仓库
    getBillCtrl().onAllSP();
  }

  /**
   * ****************************************** 功能：审核单据 创建日期：(2004-3-10
   * 13:10:37)
   * 
   * @param：
   * @return： ******************************************
   */
  private void onApprove() {
    showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("40093010",
        "UPP40093010-000081")/* @res "正在审批" */);

    // 取出要审核的单据，参数为false，不解析表头表体公式，减小流量。可以考虑切换表头时解析表体公式
    ArrayList<BillVO> lstAudit = getSelectedBills(false);
    List<BillVO> lstRet = null;
    try {
      for (int i = 0, len = lstAudit.size(); i < len; i++) {
        BillHeaderVO voHead = lstAudit.get(i).getHeaderVO();
        String coperatorId = voHead.getCoperatorid(); // 制单人
        String currUserId = toftpanel.m_NodeInfo.getUserID(); // 当前登陆人，即审核人
        String corpId = toftpanel.m_NodeInfo.getCorpID(); // 当前登陆公司  
        
        String isAllowSame = toftpanel.m_NodeInfo.getM_paravalue().get(ConstVO.m_sPk_Para[ConstVO.m_iPara_ZDSPSFXT]).toString();
        if (isAllowSame.equals("N")) { // 不允许制单人和审批人是同一人
          if (coperatorId.equals(currUserId)) { // 而此时制单人和审批人却相同，返回 false
            //不允许制单人和审批人是同一人，审核失败！
            showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("topub",
                "UCH4009-000011"));
            return;
          }
        }
      }

      processDataBeforeApprove(lstAudit);
      lstRet = toftpanel.approveBill(lstAudit);
    }
    catch (BusinessException be) {
      ExceptionUITools.showMessage(be, toftpanel);
    }

    // 刷新审批中改变的信息
    if (lstRet != null && lstAudit != null) {
      processDataAfterApprove(lstAudit, lstRet);

      int[] iaSelListIndex = getBillCtrl().getCurSelectIndex();
      for (int i = 0; i < iaSelListIndex.length; i++) {
        getModel().setData(iaSelListIndex[i], lstAudit.get(i));
      }
      getBillCtrl().displayBill(getModel().getCurRow());
      setButtonStatusBrowse(getModel().getCurRow());

      String hint = ConstHintMsg.S_ON_SUCCESS_AUDIT;
      if (lstRet != null && lstRet.size() > 0) {
        Integer fStatusFlag = lstAudit.get(0).getHeaderVO().getFstatusflag();
        if (fStatusFlag.equals(ConstVO.IBillStatus_PASSCHECK))
          hint += nc.ui.ml.NCLangRes.getInstance().getStrByID("40093009",
              "UPP40093009-000246")/* @res " 结果为:通过" */;
        else if (fStatusFlag.equals(ConstVO.IBillStatus_CHECKING))
          hint += nc.ui.ml.NCLangRes.getInstance().getStrByID("40093009",
              "UPP40093009-000247")/* @res " 结果为:审批进行中" */;
        else if (fStatusFlag.equals(ConstVO.IBillStatus_UNCHECKED))
          hint += nc.ui.ml.NCLangRes.getInstance().getStrByID("40093009",
              "UPP40093009-000248")/* @res " 结果为:不通过" */;
        else if (fStatusFlag.equals(ConstVO.IBillStatus_FREE))
          hint += nc.ui.ml.NCLangRes.getInstance().getStrByID("40093009",
              "UPP40093009-000249")/* @res " 结果为:驳回" */;
        else
          hint += nc.ui.ml.NCLangRes.getInstance().getStrByID("40093009",
              "UPP40093009-000250")/* @res " 结果为:未知" */;

        showHintMessage(hint);
      }
      else {
        showHintMessage(nc.ui.ml.NCLangRes.getInstance().getStrByID("40093009",
            "UPP40093009-000251")/* @res "审批失败" */);
      }
    }
    else {
      showHintMessage("");
    }
  }

  private void processDataBeforeApprove(List<BillVO> lstAudit)
      throws BusinessException {
    StringBuffer sbErr = new StringBuffer("");
    for (int i = 0, len = lstAudit.size(); i < len; i++) {
      BillVO voAudit = lstAudit.get(i);
      BillHeaderVO voHead = voAudit.getHeaderVO();
      BillItemVO[] voaItem = voAudit.getItemVOs();
      int iBillStatus = voHead.getFstatusflag();

      if (iBillStatus == ConstVO.IBillStatus_FREE
          || iBillStatus == ConstVO.IBillStatus_CHECKING) // 审批中
      {
        // 审批前的检查
        BusinessCheck.checkForSendAudit(voAudit);
        // 放入当前操作员
        voAudit.setOperator(getNodeInfo().getUserID());
        voHead.setCauditorid(getNodeInfo().getUserID());
        // 放入客户端IP地址,写业务日志用
        try {
          voAudit.setIPAdress(java.net.InetAddress.getLocalHost()
              .getHostAddress());
        }
        catch (Exception ex) {
          ExceptionUITools.showMessage(ex, toftpanel);
        }
        // 放入当前日期
        voHead.setDauditdate(new UFDate(getNodeInfo().getLogDate(), false));

        if (voHead.getDbilldate().after(voHead.getDauditdate())) {
          throw new BusinessException(NCLangRes.getInstance().getStrByID(
              "40093010", "UPP40093010-000005")/*
           * @res "制单日期不能大于审批日期"
           */);
        }
        // 把单据状态置为修改
        voHead.setStatus(VOStatus.UPDATED);
        for (int j = 0; j < voaItem.length; j++) {
          voaItem[j].setStatus(VOStatus.UPDATED);
        }
        // 设置各公司的本位币
        BusinessCheck.setCurrencyType(voAudit);
      }
      else {
        // 记录下不需要审批的单据号
        sbErr.append(voHead.getVcode()).append(
            nc.ui.ml.NCLangRes.getInstance().getStrByID("40093009",
                "UPP40093009-000245")/* @res "、" */);
      }
    }
    if (sbErr.toString().trim().length() > 0) {
      sbErr.delete(sbErr.length() - 1, sbErr.length());
      String[] value = new String[] {
        sbErr.toString()
      };
      String error = NCLangRes.getInstance().getStrByID("40093009",
          "UPP40093009-000209", null, value);
      throw new BusinessException(error);
    }
  }

  /**
   * 创建日期：(2004-2-10 18:38:37) 作者：王乃军 参数： 返回： 说明：取消 操作
   */
  private void onCancel() {

    clearOID();
    
    if (toftpanel.getIsComeFromOther()) {
      getModel().removeData(new int[] {
        getModel().getCurRow()
      });
      if (getModel().getCount() == 0) {
        onCancelRef();
      }
      else {
        //清除缓存的表体参照的上游单据id
        clearRefLineFromHashWhenCancel();
        
        toftpanel.setReftoListPanelForCancel();
      }
    }
    else {
      //清除缓存的表体参照的上游单据id
      clearRefLineFromHashWhenCancel();
      
      getCardPanelCtrl().cancelUpdate();
      getBillCtrl().displayBill(getModel().getCurRow());
      setButtonStatusBrowse(getModel().getCurRow());
      showHintMessage(ConstHintMsg.S_ON_SUCCESS_CANCEL);
    }
  }

  /**
   * 取消编辑时，将参照的上游单据id从缓存中清除，以便参照增行时可以再次查询到
   * <b>参数说明</b>
   * @author sunwei
   * @time 2009-1-9 下午02:59:19
   */
  private void clearRefLineFromHashWhenCancel() {
    int count = getCardPanelCtrl().getCardPanel().getBillCardPanel().getBillTable().getRowCount();
    for (int i = 0; i < count; i++) {
      Object csourcebid = null;
      Object rowId = null;
      rowId = getCardPanelCtrl().getCardPanel().getBillCardPanel().getBillModel().getValueAt(i, "cbill_bid");
      //id为空的为新增行，避免已保存过的表体行存在csourcebid，造成误清除
      if(rowId==null||rowId.toString().length()==0){
        csourcebid = getCardPanelCtrl().getCardPanel().getBillCardPanel().getBillModel().getValueAt(i, "csourcebid");
      }
      
      toftpanel.getRefBillIds().remove(csourcebid);
    }
  }

  private BillVO getSaveBill(String sActionName) throws Exception {
    BillVO voNew = (BillVO) getCardPanelCtrl().getVO();
    if (voNew == null || voNew.getParentVO() == null
        || voNew.getChildrenVO() == null) {
      throw new BusinessException(nc.ui.ml.NCLangRes.getInstance().getStrByID(
          "40093010", "UPP40093010-000073")/*
       * @res "单据为空！"
       */);
    }
    // 放入客户端IP地址,写业务日志用
    voNew.setIPAdress(java.net.InetAddress.getLocalHost().getHostAddress());
    // 放入当前操作员
    if (sActionName.equals(ConstVO.sActionSENDAFTERSAVE)) {
      // 补充审批人id(供审批流使用:当配置并满足审批条件时,审批流可能在送审后直接调用审批脚本)
      ((BillVO) voNew).getHeaderVO().setCauditorid(getNodeInfo().getUserID());

    }
    voNew.setOperator(getNodeInfo().getUserID());

    BillHeaderVO voHeader = voNew.getHeaderVO();

    voHeader.setBatpcheck(new UFBoolean(true));
    voHeader.setClastmodifierid(getNodeInfo().getUserID());
    voHeader.setClastmodiname(getNodeInfo().getUserName());
    voHeader.setTlastmodifytime(ClientEnvironment.getServerTime());
    if (voNew.getHeaderVO().getCbillid() == null
        || voNew.getHeaderVO().getCbillid().toString().trim().length() == 0) {
      // 制单时间
      voNew.getHeaderVO().setTmaketime(ClientEnvironment.getServerTime());
    }

    // 行号重复检查
    if (!nc.ui.scm.pub.report.BillRowNo.verifyRowNosCorrect(getCardPanelCtrl()
        .getCardPanel().getBillCardPanel(), "crowno")) {
      return null;
    }
    BusinessCheck.inputChk(voNew);
    BusinessCheck.checkCardPanel(getCardPanelCtrl().getCardPanel()
        .getBillCardPanel());
    if (sActionName.equals(ConstVO.sActionSENDAFTERSAVE)) {
      // 检查送审的单据
      BusinessCheck.checkForSendAudit(voNew);
    }
    return voNew;
  }

  private NodeInfo getNodeInfo() {
    return toftpanel.getNodeInfo();
  }

  /*
   * 得到保存时动作脚本需要的参数
   */
  private ArrayList getSaveParam(BillVO voBill) {
    BillHeaderVO voHeader = voBill.getHeaderVO();
    BillItemVO[] voaItem = voBill.getItemVOs();

    // 1.新增状态下，认为修改前的单据为null
    BillVO voOriginalBill = (BillVO) getModel().getCurVO();
    // 2.修改状态下，取修改前的单据
    // 判断是调拨订单退回还是手工新增退回调拨订单
    String sSourceid = voaItem[0].getCsourceid();
    boolean bRetBillFlag = false;
    if (voOriginalBill != null)
      bRetBillFlag = voaItem[0].getBretractflag() != null
          && voaItem[0].getBretractflag().booleanValue() && sSourceid != null
          && sSourceid.equals(voOriginalBill.getItemVOs()[0].getCbillid());

    ArrayList alParam = new ArrayList();
    if (getCardPanelCtrl().getEditMode() == GenEditConst.NEW) {
      // 检查退回的来源调拨订单是否被修改时使用
      alParam.add(bRetBillFlag ? new BillVO[] {
        voOriginalBill
      } : null);
      alParam.add(toftpanel.m_clientLink);
    }
    else if (getCardPanelCtrl().getEditMode() == GenEditConst.UPDATE) {
      alParam.add(new BillVO[] {
        (BillVO) voOriginalBill
      });
      alParam.add(toftpanel.m_clientLink);
    }
    return alParam;
  }

  public ButtonEventHandler(
      TransferClientUI toftpanel) {
    super();
    this.toftpanel = toftpanel;
  }

  private void setModifiedRowsColor() {
    BillCardPanel billCardPanel = getCardPanelCtrl().getCardPanel()
        .getBillCardPanel();
    BillModel billModel = billCardPanel.getBillModel();
    ArrayList<Integer> selectedRows = new ArrayList<Integer>();
    for (int i = 0, len = billModel.getRowCount(); i < len; i++) {
      UFDouble nprice = (UFDouble) billModel.getValueAt(i, "nprice");
      UFDouble naskprice = (UFDouble) billModel.getValueAt(i, "naskprice");
      if ((nprice != null) && (naskprice != null)
          && (!nprice.equals(naskprice))) {
        selectedRows.add(i);

      }
    }
    SetColor.setRowColor(billCardPanel.getBodyPanel(), selectedRows,
        Color.YELLOW);
  }
  
  /**
   * 方法功能描述：增加调拨订单分单打印预览功能。
   * <b>参数说明</b>
   * @author liyu
   * @time 2009-2-19 下午03:46:53
   */
  private void onSplitPreview(){
    if (getCardPanelCtrl().getEditMode() == GenEditConst.BROWSE) {
      // 这里组装分单参数vo,按出货仓库这个参数进行分单
      SplitParams[] paramvos = new SplitParams[1];
      paramvos[0] = new SplitParams("ctakeoutwhid",
          nc.ui.ml.NCLangRes.getInstance()
              .getStrByID("common", "UC000-0000525")/* @res "出货仓库" */,
          SplitParams.NoInput, null, true);// 出货仓库
      SplitPrintParamDlg splitDlg = new SplitPrintParamDlg(this.toftpanel, paramvos);
      splitDlg.showModal();
      // 获取新的参数vo
      SplitParams[] paramvonew = null;
      if (splitDlg.isCloseByBtnOK()) {// 获取确定按钮
        paramvonew = splitDlg.getSplitParams();
      }
      else{
        return;
      }
      if (getCurPanel() == GenEditConst.LIST) {
        // 列表
        showHintMessage(PrintLogClient.getBeforePrintMsg(true, true));
        if (getModel().getCount() <= 0) {
          return;
        }

        ArrayList alPrintVO = getSelectedBills(true);
        if (alPrintVO == null)
          return;
        BillPrintTool printTool = null;
        try {
          printTool = new BillPrintTool(this.toftpanel, getNodeInfo()
              .getNodeCode(), alPrintVO, getCardPanelCtrl().getCardPanel()
              .getBillCardPanel().getBillData(), null, getNodeInfo()
              .getCorpID(), getNodeInfo().getUserID(), "vcode", "cbillid");

          printTool.onBatchSplitPrintPreview(getListPanelCtrl().getListPanel()
              .getBillListPanel(), getNodeInfo().getBillTypeCode(),paramvonew);
          showHintMessage(printTool.getPrintMessage());
        }
        catch (Exception e) {
          GenMsgCtrl.printErr(e.getMessage());
        }
      
        
      }
      else if(getCurPanel() == GenEditConst.CARD){ 
        showHintMessage(PrintLogClient.getBeforePrintMsg(true, false));
      // 准备数据
      BillVO voPrint = (BillVO) getModel().getCurVO();

      if (null == voPrint) {
        voPrint = new BillVO();
      }
      if (null == voPrint.getParentVO()) {
        voPrint.setParentVO(new BillHeaderVO());
      }
      if ((null == voPrint.getChildrenVO())
          || (voPrint.getChildrenVO().length == 0)
          || (voPrint.getChildrenVO()[0] == null)) {
        BillItemVO[] ivo = new BillItemVO[1];
        ivo[0] = new BillItemVO();
        voPrint.setChildrenVO(ivo);
      }

      ArrayList alPrintVO = new ArrayList(1);
      alPrintVO.add(voPrint);
      try {
        BillPrintTool printTool = new BillPrintTool(this.toftpanel,
            getNodeInfo().getNodeCode(), alPrintVO, getCardPanelCtrl()
                .getCardPanel().getBillCardPanel().getBillData(), null,
            getNodeInfo().getCorpID(), getNodeInfo().getUserID(), "vcode",
            "cbillid");

        printTool.onSplitCardPrintPreview(getCardPanelCtrl().getCardPanel()
            .getBillCardPanel(), getListPanelCtrl().getListPanel()
            .getBillListPanel(), getNodeInfo().getBillTypeCode(),paramvonew);
        showHintMessage(printTool.getPrintMessage());
      }
      catch (Exception e) {
        GenMsgCtrl.printErr(e.getMessage());
      }}
      
      
    }
  }
}