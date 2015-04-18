package nc.ui.jyglgt.j40068c;

import java.util.ArrayList;
import java.util.List;

import nc.ui.ic.pub.bill.GeneralBillHelper;
import nc.ui.ic.pub.bill.query.QueryConditionDlgForBillNew;
import nc.ui.ic.pub.query.ICSqlProcessor;
import nc.ui.ic.pub.query.QueryDlgUtil;
import nc.ui.ic.pub.query.SCMDefaultFilter;
import nc.ui.ic.pub.query.SCMTemplateInfo;
import nc.ui.ic.pub.tools.GenMethod;
import nc.ui.querytemplate.meta.FilterMeta;
import nc.vo.ic.pub.BillTypeConst;
import nc.vo.ic.pub.ICConst;
import nc.vo.ic.pub.bill.GeneralBillVO;
import nc.vo.ic.pub.bill.QryConditionVO;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.query.ConditionVO;
import nc.vo.querytemplate.TemplateInfo;
import nc.vo.scm.pub.BD_TYPE;
import nc.vo.scm.pub.query.DataPowerCtl;

public class QueryDlgHelp extends ICSqlProcessor{
  
  protected IssuleToBalanceDialog clientUI;
  protected QueryConditionDlgForBillNew queryDlg;
  
  protected String m_sBnoutnumnull ;
  
//是否曾经查询过的标识
  protected boolean m_bEverQry = false;
  protected boolean isinit = false;
  
//最近一次的查询条件，可用于刷新。现用于列表形式下的打印。
  protected QryConditionVO m_voLastQryCond ;
  
  protected DataPowerCtl datapowerctl ;
  
  protected ConditionVO[] linkQryConds = null;
  
  protected DataPowerCtl getDataPowerCtl() {
    if(datapowerctl==null){
      datapowerctl = new DataPowerCtl(getClientUI().getUserID(),
          getClientUI().getCorpID());
   // addied by liuzy 2010-10-29 下午04:49:58 V5.7查询模板增加了是否拼isnull的注册的配置，
      //而cinventoryid在查询模板上没有
      List<String> noNullPowerFields = new ArrayList<String>();
      noNullPowerFields.add("body.cinventoryid");
      datapowerctl.setNoNullPowerFields(noNullPowerFields);
      String billtype = getClientUI().getBillType();
      String[] cotherwhere = null;  //把OR添加到对应的权限SQL里去。
      if (BillTypeConst.m_saleOut.equals(billtype)
          || BillTypeConst.m_allocationIn.equals(billtype)
          || BillTypeConst.m_allocationOut.equals(billtype)){
        cotherwhere = new String[]{
            null,
            null,
            null,
            null,
            " or exists (select bd_deptdoc.pk_deptdoc from  bd_deptdoc where bd_deptdoc.pk_corp<>'"+getClientUI().getCorpID()+"' and head.cdptid=bd_deptdoc.pk_deptdoc ) ",
            " or exists (select bd_psndoc.pk_psndoc from  bd_psndoc where bd_psndoc.pk_corp<>'"+getClientUI().getCorpID()+"' and head.cbizid=bd_psndoc.pk_psndoc ) ",
            null,
            null,
            null,
            null,
            
            null,
            null,
            null,
            null,
            null,
            null
        };
      }
      //修改人：刘家清 修改时间：2009-3-5 上午09:44:52 修改原因：库存采购入库单的采购部门、采购员不进行部门、人员权限控制；
      if (BillTypeConst.m_purchaseIn.equals(billtype)){
    	  
	      QueryDlgUtil.setDataPowerCtlFields(datapowerctl,new String[]{
		          "head.cbiztype",
		          "head.pk_calbody",
		          "head.cwarehouseid",
		          "head.cdispatcherid",

		          "head.cdilivertypeid",
		          "head.cwastewarehouseid",
		          "head.ccustomerid",
		          "head.cproviderid",
		          
		          "body.cinventoryid",
		          "body.cinventoryid",
		          "body.cprojectid",
		          "body.cvendorid",
		          "body.creceiveareaid",
		          
		          "head.cdptid",
		          "head.cwhsmanagerid",
		          "head.cbizid"
		      }, 
		      new BD_TYPE[]{
		          BD_TYPE.BusiType,
		          BD_TYPE.Calbody,
		          BD_TYPE.Warehouse,
		          BD_TYPE.Dispatcher,

		          BD_TYPE.Dilivertype,
		          BD_TYPE.Warehouse,
		          BD_TYPE.Cust,
		          BD_TYPE.Cust,
		          
		          BD_TYPE.InvCl,
		          BD_TYPE.Inv,
		          BD_TYPE.Proj,
		          BD_TYPE.Cust,
		          BD_TYPE.Area,
		          
		          BD_TYPE.Dept,
		          BD_TYPE.Psn,
		          BD_TYPE.Psn
		          
		      },cotherwhere
		      );
    	  
      }else if(BillTypeConst.m_allocationOut.equals(billtype) || BillTypeConst.m_allocationIn.equals(billtype)){
        QueryDlgUtil.setDataPowerCtlFields(datapowerctl,new String[]{
            "head.cbiztype",
            "head.pk_calbody",
            "head.cwarehouseid",
            "head.cdispatcherid",
            "head.cdptid",
            "head.cbizid",
            "head.cdilivertypeid",
            "head.cwastewarehouseid",
            "head.ccustomerid",
            "head.cproviderid",
            
            "body.cinventoryid",
            "body.cinventoryid",
            "body.cprojectid",
            "body.cvendorid",
            //"body.creceiveareaid",
            
            "head.cwhsmanagerid"
        }, 
        new BD_TYPE[]{
            BD_TYPE.BusiType,
            BD_TYPE.Calbody,
            BD_TYPE.Warehouse,
            BD_TYPE.Dispatcher,
            BD_TYPE.Dept,
            BD_TYPE.Psn,
            BD_TYPE.Dilivertype,
            BD_TYPE.Warehouse,
            BD_TYPE.Cust,
            BD_TYPE.Cust,
            
            BD_TYPE.InvCl,
            BD_TYPE.Inv,
            BD_TYPE.Proj,
            BD_TYPE.Cust,
            //BD_TYPE.Area,
            
            BD_TYPE.Psn
        },cotherwhere
        );
      }
      
      else{
	      QueryDlgUtil.setDataPowerCtlFields(datapowerctl,new String[]{
	          "head.cbiztype",
	          "head.pk_calbody",
	          "head.cwarehouseid",
	          "head.cdispatcherid",
	          "head.cdptid",
	          "head.cbizid",
	          "head.cdilivertypeid",
	          "head.cwastewarehouseid",
	          "head.ccustomerid",
	          "head.cproviderid",
	          
	          "body.cinventoryid",
	          "body.cinventoryid",
	          "body.cprojectid",
	          "body.cvendorid",
	          "body.creceiveareaid",
	          
	          "head.cwhsmanagerid"
	      }, 
	      new BD_TYPE[]{
	          BD_TYPE.BusiType,
	          BD_TYPE.Calbody,
	          BD_TYPE.Warehouse,
	          BD_TYPE.Dispatcher,
	          BD_TYPE.Dept,
	          BD_TYPE.Psn,
	          BD_TYPE.Dilivertype,
	          BD_TYPE.Warehouse,
	          BD_TYPE.Cust,
	          BD_TYPE.Cust,
	          
	          BD_TYPE.InvCl,
	          BD_TYPE.Inv,
	          BD_TYPE.Proj,
	          BD_TYPE.Cust,
	          BD_TYPE.Area,
	          
	          BD_TYPE.Psn
	      },cotherwhere
	      );
      }
    }
    return datapowerctl;
  }

  public QueryDlgHelp(IssuleToBalanceDialog ui) {
    // TODO 自动生成构造函数存根
    clientUI = ui;
    //init();
  }


  public IssuleToBalanceDialog getClientUI() {
    return clientUI;
  }


  public void setClientUI(IssuleToBalanceDialog clentUI) {
    this.clientUI = clentUI;
  }


  public QueryConditionDlgForBillNew getQueryDlg() {
    if(queryDlg==null){
      TemplateInfo info = QueryDlgUtil.getTemplateInfo(
          getClientUI().getCorpID(),
          getClientUI().getFunctionNode(), 
          getClientUI().getUserID());
      queryDlg = new QueryConditionDlgForBillNew(getClientUI(),info);
      init();
    }
    return queryDlg;
  }


  @Override
  public String processWhere(SCMDefaultFilter filter, String basicWhere) {
    // TODO 自动生成方法存根
    super.processWhere(filter, basicWhere);
    FilterMeta meta = (FilterMeta)filter.getFilterMeta();
    String fieldcode = meta.getFieldCode();
    String swhere = basicWhere;
    if("head.dbilldate.from".equals(fieldcode) || "head.dbilldate.end".equals(fieldcode)){
      meta.setFieldCode("head.dbilldate");
      swhere = QueryDlgUtil.getSqlString(filter);
      meta.setFieldCode(fieldcode);
    }else if( "dbilldate.from".equals(fieldcode) || "dbilldate.end".equals(fieldcode)){
      meta.setFieldCode("dbilldate");
      swhere = QueryDlgUtil.getSqlString(filter);
      meta.setFieldCode(fieldcode);
    }
    return swhere;
  }
  
  protected void init() {
	if(isinit)
		return;
	isinit = true;
    
    getQueryDlg().setLogFields(new String[]{
        "qbillstatus",
        "boutnumnull",
        "freplenishflag",
        "head.dbilldate.from",
        "head.dbilldate.end",
        "head.dbilldate",
        "dbilldate",//added by lirr 2009-10-15下午01:56:46报废单 使用
        "dbilldate.from",
        "dbilldate.end",
        "head.pk_calbody",
        "pk_calbody",
        "head.cwarehouseid",
        "cwarehouseid"
    });
    getQueryDlg().getSCMTempInfo().setSqlProcessor(this);
    getQueryDlg().setVBatchCodeItem("body.vbatchcode", "body.cinventoryid");
    
    getQueryDlg().setPowerCtl(getDataPowerCtl());
//    ivjQueryConditionDlg.setTempletID(getEnvironment().getCorpID(),
//        getFunctionNode(), getEnvironment().getUserID(), null);

    // 以下为对公司参照的初始化
    ArrayList alCorpIDs = new ArrayList();
    alCorpIDs.add(getClientUI().getCorpID());
    getQueryDlg().initCorpRef("head.pk_corp", getClientUI()
        .getCorpID(), alCorpIDs);
    // 以下为对参照的初始化
    getQueryDlg().initQueryDlgRef();

    // 隐藏常用条件
    getQueryDlg().hideNormal();
    // 条码是否关闭查询条件body.bbarcodeclose
    getQueryDlg().setCombox("body.bbarcodeclose",
        new String[][] {
         // modified by lirr 2009-11-25上午09:08:35 必须为空而非空格 否则sql=body.bbarcodeclose = ' ',前一个为sqlstring，后一个为showstring 
            // { " ", " " },
             { "", "" },
            {
                "N",
                nc.ui.ml.NCLangRes.getInstance()
                    .getStrByID("SCMCOMMON",
                        "UPPSCMCommon-000108") /*
                                     * @res
                                     * "否"
                                     */},
            {
                "Y",
                nc.ui.ml.NCLangRes.getInstance()
                    .getStrByID("SCMCOMMON",
                        "UPPSCMCommon-000244") /*
                                     * @res
                                     * "是"
                                     */} });
    //是否在途
    getQueryDlg().setCombox("body.bonroadflag",
            new String[][] {
            // modified by lirr 2009-11-25上午09:08:35 必须为空而非空格 否则sql=body.bonroadflag = ' ',前一个为sqlstring，后一个为showstring 
               // { " ", " " },
                { "", "" },
                {
                    "N",
                    nc.ui.ml.NCLangRes.getInstance()
                        .getStrByID("SCMCOMMON",
                            "UPPSCMCommon-000108") /*
                                         * @res
                                         * "否"
                                         */},
                {
                    "Y",
                    nc.ui.ml.NCLangRes.getInstance()
                        .getStrByID("SCMCOMMON",
                            "UPPSCMCommon-000244") /*
                                         * @res
                                         * "是"
                                         */} });
    // 设置下拉框显示
    getQueryDlg().setCombox("qbillstatus", new String[][] {
        {
            "2",
            nc.ui.ml.NCLangRes.getInstance().getStrByID(
                "4008bill", "UPP4008bill-000313") /*
                                   * @res
                                   * "制单"
                                   */},
        {
            "3",
            nc.ui.ml.NCLangRes.getInstance().getStrByID(
                "40080402", "UPT40080402-000013") /*
                                   * @res
                                   * "签字"
                                   */},
        {
            "A",
            nc.ui.ml.NCLangRes.getInstance().getStrByID(
                "SCMCOMMON", "UPPSCMCommon-000217") /*
                                   * @res
                                   * "全部"
                                   */} });
    // set default logon date into query condiotn dlg
    // modified by liuzy 2008-03-28 5.03需求，单据查询增加起止日期
    getQueryDlg().setDefaultValue("qbillstatus","2");
    getQueryDlg().setInitDate("head.dbilldate.from",
        getClientUI().getLogDate());
    getQueryDlg().setInitDate("head.dbilldate.end",
        getClientUI().getLogDate());
    // added by lirr 2009-9-25下午04:25:01 设置默认日期
    getQueryDlg().setDefaultValue("head.dbilldate", new String[]{getClientUI().getLogDate(),getClientUI().getLogDate()});
    // added by lirr 2009-10-15下午01:56:46 报废单
    getQueryDlg().setDefaultValue("dbilldate", new String[]{getClientUI().getLogDate(),getClientUI().getLogDate()});
    
    getQueryDlg().setInitDate("dbilldate.from", getClientUI()
        .getLogDate());
    getQueryDlg().setInitDate("dbilldate.end", getClientUI()
        .getLogDate());
    getQueryDlg().setInOutFlag(getClientUI().getInOutFlag());

    // 查询对话框显示打印次数页签。
    getQueryDlg().setShowPrintStatusPanel(true);

    // 修改自定义项目 add by hanwei 2003-12-09
    getQueryDlg().updateQueryConditionClientUserDef(
        getClientUI().getCorpID(), ICConst.BILLTYPE_IC,
        "head.vuserdef", "body.vuserdef");
    
    /* yb query
    //getClientUI().getConDlginitself(getQueryDlg());
     */

    // 过滤库存组织，仓库,废品库,客户,供应商的数据权限，部门，业务员
    // zhy2005-06-10 客户和供应商不需要在普通单上过滤，（客户在销售出库单上过滤，供应商在采购入库单上过滤）
    // zhy2007-02-12 V51新需求:3、
    // 客商、地区分类、库存组织、项目受数据权限控制，部门、仓库、存货分类、存货受已定义的库管员匹配纪录的控制；
    /**
     * 库管员:head.cwhsmanagerid 客商:head.cproviderid 库存组织:head.pk_calbody
     * 仓库:head.cwarehouseid,head.cwastewarehouseid 项目:body.cprojectid
     * 部门:head.cdptid 存货分类:invcl.invclasscode 存货:inv.invcode
     */
    // ivjQueryConditionDlg.setCorpRefs("head.pk_corp", new String[] {
    // "head.cproviderid","head.pk_calbody", "head.cwarehouseid",
    // "head.cwastewarehouseid","body.cprojectid"
    // //, "head.cdptid", "head.cbizid"
    // });
    // ivjQueryConditionDlg.setDataPower(true,
    // getEnvironment().getCorpID());
    
    /* yb query
    if (BillTypeConst.m_allocationIn.equals(getBillTypeCode())
        || BillTypeConst.m_allocationOut.equals(getBillTypeCode()))
      ivjQueryConditionDlg.setCorpRefs("head.pk_corp",
          nc.ui.ic.pub.tools.GenMethod
              .getDataPowerFieldFromDlg(ivjQueryConditionDlg,
                  false, new String[] {
                      "head.cothercorpid",
                      "head.coutcorpid",
                      "body.creceieveid",
                      "head.cothercalbodyid",
                      "head.cotherwhid",
                      "head.coutcalbodyid" }));
    else
      ivjQueryConditionDlg
          .setCorpRefs(
              "head.pk_corp",
              nc.ui.ic.pub.tools.GenMethod
                  .getDataPowerFieldFromDlgNotByProp(ivjQueryConditionDlg));
                  
    */              
    

    // zhy205-05-19 加可还回数量条件
    // 借出单
/*    getQueryDlg()
        .setCombox(
            "coalesce(body.noutnum,0)-coalesce(body.nretnum,0)-coalesce(body.ntranoutnum,0)",
            new Integer[][] { { new Integer(0), new Integer(0) } });
    // 借入单
    getQueryDlg()
        .setCombox(
            "coalesce(body.ninnum,0)-coalesce(body.nretnum,0)-coalesce(body.ntranoutnum,0)",
            new Integer[][] { { new Integer(0), new Integer(0) } });*/
  }
  
  /**
   * 此处插入方法说明。 功能描述: 单据查询条件构造; 借入、借出单据可以重新构造该方法,而不必重载onquery方法 输入参数: 返回值:
   * 异常处理: 日期:
   * 
   * @return nc.vo.ic.pub.bill.QryConditionVO
   */
   protected QryConditionVO getQryConditionVO() {
    // 添加查询
    /* yb query
    nc.vo.pub.query.ConditionVO[] voaCond = getConditionDlg()
        .getConditionVO();
    
    // 处理跨公司部门业务员条件
    voaCond = nc.ui.ic.pub.tools.GenMethod.procMultCorpDeptBizDP(voaCond,
        getBillTypeCode(), getCorpPrimaryKey());
    // 过滤null为 is null 或 is not null add by hanwei 2004-03-31.01
    nc.ui.ic.pub.report.IcBaseReportComm.fixContionVONullsql(voaCond);
    */
    String swhere  = getQueryDlg().getWhereSQL();
    nc.vo.pub.query.ConditionVO[] logconvos = getQueryDlg().getLogicalConditionVOs();
    preProcLogConditionVO(logconvos);
    if(logconvos!=null && logconvos.length>0){
      swhere = nc.vo.ic.pub.GenMethod.andTowWhere(swhere,getExtendQryCond(logconvos));
    }
    QryConditionVO voCond = new QryConditionVO(
        nc.vo.ic.pub.GenMethod.andTowWhere(getBillFixWhere(),swhere)
    );
    
    voCond.setParam(QryConditionVO.QRY_CONDITIONVO,getAndConditionVO());

    /* yb query
    // addied by liuzy 2008-03-28 V5.03需求：单据查询增加起止日期
    for (int i = 0; i < voaCond.length; i++) {
      if (null != voaCond[i]
          && (voaCond[i].getFieldCode().equals("head.dbilldate.from") || voaCond[i]
              .getFieldCode().equals("head.dbilldate.end"))) {
        voaCond[i].setFieldCode("head.dbilldate");
      }
    }
    */

    return voCond;
  }
  
  protected void preProcLogConditionVO(nc.vo.pub.query.ConditionVO[] vos) {
    if(vos==null || vos.length<=0)
      return;
    String fieldcode = null;
    for(int i=0;i<vos.length;i++){
      fieldcode = vos[i].getFieldCode();
      if(fieldcode==null)
        continue;
      if("head.dbilldate.from".equals(fieldcode) || "head.dbilldate.end".equals(fieldcode)){
        vos[i].setFieldCode("head.dbilldate");
      }
      if( "dbilldate.from".equals(fieldcode) || "dbilldate.end".equals(fieldcode)){
        vos[i].setFieldCode("dbilldate");
      }
    }
    
  }
   
  protected String getBillFixWhere() {
     return " head.cbilltypecode='"+ getClientUI().getBillType() + "' ";
  }
  
  public ConditionVO[] getAndConditionVO() {
    ConditionVO[] vos1 = getConditionVO(getQueryDlg());
    ConditionVO[] vos2 = getQueryDlg().getLogicalConditionVOs();
    if(vos1==null || vos1.length<=0)
      return vos2;
    if(vos2==null || vos2.length<=0)
      return vos1;
    ConditionVO[] ret = new ConditionVO[vos1.length+vos2.length];
    System.arraycopy(vos1, 0, ret, 0, vos1.length);
    System.arraycopy(vos2, 0, ret, vos1.length, vos2.length);
    return ret;
  }
  
  /**
   * 创建者：王乃军 功能：得到用户输入的额外查询条件 参数：//查询条件数组 返回： 例外： 日期：(2001-5-9 9:23:32)
   * 修改日期，修改人，修改原因，注释标志：
   * 
   * 
   * 
   * 
   */
  protected String getExtendQryCond(nc.vo.pub.query.ConditionVO[] voaCond) {
    // 单据状态条件,缺省无
    String sBillStatusSql = " (1=1) ";
    try {
      // -------- 查询条件字段 itemkey ---------
      String sFieldCode = null;
      // 从条件中查找最大最小日期
      // 单据状态
      String sBillStatus = "A";
      String sFreplenishflag = null;
      if (voaCond != null) {
        for (int i = 0; i < voaCond.length; i++) {
          if (voaCond[i] != null && voaCond[i].getFieldCode() != null) {
            sFieldCode = voaCond[i].getFieldCode().trim();
            if ("qbillstatus".equals(voaCond[i].getFieldCode()
                .trim())) {
              if (voaCond[i].getValue() != null)
                sBillStatus = voaCond[i].getValue();
            } else if ("boutnumnull".equals(sFieldCode)) {

              voaCond[i].setFieldCode("body.noutnum");
              voaCond[i].setOperaCode(" is ");

              voaCond[i].setDataType(ConditionVO.INTEGER);

              if (voaCond[i].getValue() != null
                  && "Y".equals(voaCond[i].getValue())) {

                voaCond[i].setValue(" not null ");
                m_sBnoutnumnull = "Y";
              } else {

                voaCond[i].setValue("  null ");
                m_sBnoutnumnull = "N";

              }
            }

            if ("freplenishflag".equals(voaCond[i].getFieldCode()
                .trim())) {
              if (voaCond[i].getValue() != null)
                sFreplenishflag = voaCond[i].getValue();
            }

            if ("like".equals(voaCond[i].getOperaCode().trim())
                && voaCond[i].getFieldCode() != null) {
              // String sFeildCode = voaCond[i].getFieldCode()
              // .trim();
              if (sFieldCode.equals("invcl.invclasscode")
                  && voaCond[i].getValue() != null) {
                voaCond[i]
                    .setValue(voaCond[i].getValue() + "%");
              } else if (sFieldCode.equals("dept.deptcode")
                  && voaCond[i].getValue() != null) {
                voaCond[i]
                    .setValue(voaCond[i].getValue() + "%");
              } else if (voaCond[i].getValue() != null)
                voaCond[i].setValue("%" + voaCond[i].getValue()
                    + "%");
            }
          }
        }
      }
      // 缺省是A
      if ("2".equals(sBillStatus)) // 自由
        sBillStatusSql = " fbillflag="
            + nc.vo.ic.pub.bill.BillStatus.FREE;
      else if ("3".equals(sBillStatus)) // 签字的
        sBillStatusSql = " ( fbillflag="
            + nc.vo.ic.pub.bill.BillStatus.SIGNED
            + " OR fbillflag="
            + nc.vo.ic.pub.bill.BillStatus.AUDITED + ") ";

      // 退库查询 add by hanwei 2003-10-10
      if (nc.vo.ic.pub.BillTypeConst.BILLNORMAL
          .equalsIgnoreCase(sFreplenishflag)) {
        sBillStatusSql += " AND ( COALESCE(freplenishflag,'N') = 'N' and COALESCE(boutretflag,'N') = 'N' )";
      } else if (nc.vo.ic.pub.BillTypeConst.BILLSENDBACK
          .equalsIgnoreCase(sFreplenishflag)) {
        sBillStatusSql += " AND ( freplenishflag='Y' or boutretflag = 'Y' )";
      } else if (nc.vo.ic.pub.BillTypeConst.BILLALL
          .equalsIgnoreCase(sFreplenishflag)) {
        sBillStatusSql += "  ";
      }

      // 去掉freplenishflag 是否退库
      String saItemKey[] = new String[] { "qbillstatus", "freplenishflag" };
      filterCondVO2(voaCond, saItemKey);
      // 其他条件
      String sOtherCond = voaCond[0].getWhereSQL(voaCond);
      if (sOtherCond != null)
        sBillStatusSql += " AND ( " + sOtherCond + " )";
    } catch (Exception e) {
      GenMethod.handleException(null, null, e);
    }

    return sBillStatusSql;
  }
  
  /**
   * 创建者：王乃军 功能：滤掉不需要的条件字段，然后返回之 参数： 返回： 例外： 日期：(2001-8-17 13:13:51)
   * 修改日期，修改人，修改原因，注释标志：
   */
  public void filterCondVO2(nc.vo.pub.query.ConditionVO[] voaCond,
      String[] saItemKey) {
    if (voaCond == null || saItemKey == null)
      return;
    // 暂存器
    int j = 0;
    // 数组长
    int len = saItemKey.length;
    for (int i = 0; i < voaCond.length; i++)
      // 找
      if (voaCond[i] != null)
        for (j = 0; j < len; j++) {
          if (saItemKey[j] != null
              && voaCond[i].getFieldCode() != null
              && saItemKey[j].trim().equals(
                  voaCond[i].getFieldCode().trim())) {
            // 补上对括弧的保留
            voaCond[i].setFieldCode("1");
            voaCond[i].setOperaCode("=");
            voaCond[i].setDataType(1);
            voaCond[i].setValue("1");
          }
        }
  }
  
  /*
   * 
   * 返回：Object[] 1--UFBoolean 是否查询 2--ArrayList 查询后的vo List
   */
  public Object[] queryData(boolean isFrash) throws BusinessException{
    // DUYONG 增加查询和刷新的代码
    try {
      Object[] ret = new Object[2];
      nc.vo.ic.pub.bill.Timer timer = new nc.vo.ic.pub.bill.Timer();
      timer.start("@@查询开始：");
      m_sBnoutnumnull = null;
      QryConditionVO voCond = null;

      // 将原单据类型（卡片/列表）纪录下来，如果在卡片界面执行了刷新，则无须切换到列表界面
      //int cardOrList = getM_iCurPanel();
      // 如果是[(1)进行查询(2)从来没有进行查询过但是点击了刷新按钮]，则显示查询模板进行查询
      // 如果曾经查询过，并且点击了刷新按钮，则跳过此段代码
      if (!isFrash) {
        getQueryDlg().showModal();
        timer.showExecuteTime("@@getConditionDlg().showModal()：");

        if (getQueryDlg().getResult() != nc.ui.pub.beans.UIDialog.ID_OK){
          // 取消返回
          ret[0] = UFBoolean.FALSE;
          return ret;
        }

        // 获得qrycontionVO的构造
        voCond = getQryConditionVO();

        // 记录查询条件备用
        m_voLastQryCond = voCond;

        // 如果是进行查询，则将“曾经查询过”的标识设置成true（这样，才能够进行“刷新”的操作）
        m_bEverQry = true;
        // 刷新按钮
        //setButtonStatus(true);
      } else
        voCond = getVoLastQryCond();
      
      //addied by liuzy 2008-03-31 修正单据日期“从”、“到”字段名
      
      /* yb query
       String str_where = voCond.getQryCond();
      if(null != str_where && !"".equals(str_where)){
        if(str_where.indexOf("dbilldate.from") > -1)
          str_where = str_where.replace("dbilldate.from", "dbilldate");
        if(str_where.indexOf("dbilldate.end") > -1)
          str_where = str_where.replace("dbilldate.end", "dbilldate");
        voCond.setQryCond(str_where);
       
      }*/

      // DUYONG 此处应该是刷新按钮功能的开始执行处

      // 如果使用公式则必须传voaCond 到后台. 修改 zhangxing 2003-03-05
      //nc.vo.pub.query.ConditionVO[] voaCond = getClientUI().getConditionDlg()
      //    .getConditionVO();
      
      //addied by liuzy 2008-03-28 V5.03需求：单据查询增加起止日期
      /* yb query
      for (int i = 0; i < voaCond.length; i++) {
        if (null != voaCond[i] && null != voaCond[i].getFieldCode()
            && (voaCond[i].getFieldCode().equals("head.dbilldate.from") || voaCond[i]
                .getFieldCode().equals("head.dbilldate.end"))) {
          voaCond[i].setFieldCode("head.dbilldate");
        }
      }
      */
      
      /* yb query
      //voCond.setParam(QryConditionVO.QRY_CONDITIONVO, getClientUI().getAndConditionVO());
      */

      voCond.setIntParam(0, GeneralBillVO.QRY_HEAD_ONLY_PURE);

      if (m_sBnoutnumnull != null) {
        // 是否存在实发数量
        voCond.setParam(33, m_sBnoutnumnull);
      }

      voCond.setParam(QryConditionVO.QRY_LOGCORPID, getClientUI()
          .getCorpID());
      voCond.setParam(QryConditionVO.QRY_LOGUSERID, getClientUI()
          .getUserID());

      timer.showExecuteTime("Before 查询：：");

      ArrayList alListData = (ArrayList) GeneralBillHelper.queryBills(
          getClientUI().getBillType(), voCond);

      timer.showExecuteTime("查询时间：");
      ret[0] = UFBoolean.TRUE;
      ret[1] = alListData;
      
      return ret;
      
    } catch (Exception e) {
      throw GenMethod.handleException(null, null, e);
    }
  }
  
  public ArrayList queryData(String where) throws BusinessException{
    try{
      
      QryConditionVO voCond = new QryConditionVO(where);
      voCond.setIntParam(0, GeneralBillVO.QRY_HEAD_ONLY_PURE);
      voCond.setParam(QryConditionVO.QRY_LOGCORPID, getClientUI()
              .getCorpID());
          voCond.setParam(QryConditionVO.QRY_LOGUSERID, getClientUI()
              .getUserID());
          
          // modified by liuzy 2010-9-7 下午03:23:28 原来查询是考虑的权限，但是没有考虑到表连接
      ConditionVO[] conds = null;

      if (null == getLinkQryConds() || getLinkQryConds().length == 0)
        conds = new ConditionVO[2];
      else
        conds = new ConditionVO[getLinkQryConds().length + 2];

      conds[0] = new ConditionVO();
      conds[0].setTableCode("head");
      conds[0].setFieldCode("head.cgeneralhid");
      conds[1] = new ConditionVO();
      conds[1].setTableCode("body");
      conds[1].setFieldCode("body.cgeneralbid");

      if (null != getLinkQryConds() && getLinkQryConds().length > 0)
        System.arraycopy(getLinkQryConds(), 0, conds, 2,
            getLinkQryConds().length);
          
          voCond.setParam(QryConditionVO.QRY_CONDITIONVO,conds);
      return (ArrayList) GeneralBillHelper.queryBills(
                  getClientUI().getBillType(), voCond);
    } catch (Exception e) {
        throw GenMethod.handleException(null, null, e);
    }
  }

  public String getBnoutnumnull() {
    return m_sBnoutnumnull;
  }


  public QryConditionVO getVoLastQryCond() {
    return m_voLastQryCond;
  }


  public boolean isBEverQry() {
    return m_bEverQry;
  }


  public void setVoLastQryCond(QryConditionVO lastQryCond) {
    m_voLastQryCond = lastQryCond;
  }

  public ConditionVO[] getLinkQryConds() {
    return linkQryConds;
  }

  public void setLinkQryConds(ConditionVO[] linkQryConds) {
    this.linkQryConds = linkQryConds;
  }

}
