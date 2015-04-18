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
  
//�Ƿ�������ѯ���ı�ʶ
  protected boolean m_bEverQry = false;
  protected boolean isinit = false;
  
//���һ�εĲ�ѯ������������ˢ�¡��������б���ʽ�µĴ�ӡ��
  protected QryConditionVO m_voLastQryCond ;
  
  protected DataPowerCtl datapowerctl ;
  
  protected ConditionVO[] linkQryConds = null;
  
  protected DataPowerCtl getDataPowerCtl() {
    if(datapowerctl==null){
      datapowerctl = new DataPowerCtl(getClientUI().getUserID(),
          getClientUI().getCorpID());
   // addied by liuzy 2010-10-29 ����04:49:58 V5.7��ѯģ���������Ƿ�ƴisnull��ע������ã�
      //��cinventoryid�ڲ�ѯģ����û��
      List<String> noNullPowerFields = new ArrayList<String>();
      noNullPowerFields.add("body.cinventoryid");
      datapowerctl.setNoNullPowerFields(noNullPowerFields);
      String billtype = getClientUI().getBillType();
      String[] cotherwhere = null;  //��OR��ӵ���Ӧ��Ȩ��SQL��ȥ��
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
      //�޸��ˣ������� �޸�ʱ�䣺2009-3-5 ����09:44:52 �޸�ԭ�򣺿��ɹ���ⵥ�Ĳɹ����š��ɹ�Ա�����в��š���ԱȨ�޿��ƣ�
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
    // TODO �Զ����ɹ��캯�����
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
    // TODO �Զ����ɷ������
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
        "dbilldate",//added by lirr 2009-10-15����01:56:46���ϵ� ʹ��
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

    // ����Ϊ�Թ�˾���յĳ�ʼ��
    ArrayList alCorpIDs = new ArrayList();
    alCorpIDs.add(getClientUI().getCorpID());
    getQueryDlg().initCorpRef("head.pk_corp", getClientUI()
        .getCorpID(), alCorpIDs);
    // ����Ϊ�Բ��յĳ�ʼ��
    getQueryDlg().initQueryDlgRef();

    // ���س�������
    getQueryDlg().hideNormal();
    // �����Ƿ�رղ�ѯ����body.bbarcodeclose
    getQueryDlg().setCombox("body.bbarcodeclose",
        new String[][] {
         // modified by lirr 2009-11-25����09:08:35 ����Ϊ�ն��ǿո� ����sql=body.bbarcodeclose = ' ',ǰһ��Ϊsqlstring����һ��Ϊshowstring 
            // { " ", " " },
             { "", "" },
            {
                "N",
                nc.ui.ml.NCLangRes.getInstance()
                    .getStrByID("SCMCOMMON",
                        "UPPSCMCommon-000108") /*
                                     * @res
                                     * "��"
                                     */},
            {
                "Y",
                nc.ui.ml.NCLangRes.getInstance()
                    .getStrByID("SCMCOMMON",
                        "UPPSCMCommon-000244") /*
                                     * @res
                                     * "��"
                                     */} });
    //�Ƿ���;
    getQueryDlg().setCombox("body.bonroadflag",
            new String[][] {
            // modified by lirr 2009-11-25����09:08:35 ����Ϊ�ն��ǿո� ����sql=body.bonroadflag = ' ',ǰһ��Ϊsqlstring����һ��Ϊshowstring 
               // { " ", " " },
                { "", "" },
                {
                    "N",
                    nc.ui.ml.NCLangRes.getInstance()
                        .getStrByID("SCMCOMMON",
                            "UPPSCMCommon-000108") /*
                                         * @res
                                         * "��"
                                         */},
                {
                    "Y",
                    nc.ui.ml.NCLangRes.getInstance()
                        .getStrByID("SCMCOMMON",
                            "UPPSCMCommon-000244") /*
                                         * @res
                                         * "��"
                                         */} });
    // ������������ʾ
    getQueryDlg().setCombox("qbillstatus", new String[][] {
        {
            "2",
            nc.ui.ml.NCLangRes.getInstance().getStrByID(
                "4008bill", "UPP4008bill-000313") /*
                                   * @res
                                   * "�Ƶ�"
                                   */},
        {
            "3",
            nc.ui.ml.NCLangRes.getInstance().getStrByID(
                "40080402", "UPT40080402-000013") /*
                                   * @res
                                   * "ǩ��"
                                   */},
        {
            "A",
            nc.ui.ml.NCLangRes.getInstance().getStrByID(
                "SCMCOMMON", "UPPSCMCommon-000217") /*
                                   * @res
                                   * "ȫ��"
                                   */} });
    // set default logon date into query condiotn dlg
    // modified by liuzy 2008-03-28 5.03���󣬵��ݲ�ѯ������ֹ����
    getQueryDlg().setDefaultValue("qbillstatus","2");
    getQueryDlg().setInitDate("head.dbilldate.from",
        getClientUI().getLogDate());
    getQueryDlg().setInitDate("head.dbilldate.end",
        getClientUI().getLogDate());
    // added by lirr 2009-9-25����04:25:01 ����Ĭ������
    getQueryDlg().setDefaultValue("head.dbilldate", new String[]{getClientUI().getLogDate(),getClientUI().getLogDate()});
    // added by lirr 2009-10-15����01:56:46 ���ϵ�
    getQueryDlg().setDefaultValue("dbilldate", new String[]{getClientUI().getLogDate(),getClientUI().getLogDate()});
    
    getQueryDlg().setInitDate("dbilldate.from", getClientUI()
        .getLogDate());
    getQueryDlg().setInitDate("dbilldate.end", getClientUI()
        .getLogDate());
    getQueryDlg().setInOutFlag(getClientUI().getInOutFlag());

    // ��ѯ�Ի�����ʾ��ӡ����ҳǩ��
    getQueryDlg().setShowPrintStatusPanel(true);

    // �޸��Զ�����Ŀ add by hanwei 2003-12-09
    getQueryDlg().updateQueryConditionClientUserDef(
        getClientUI().getCorpID(), ICConst.BILLTYPE_IC,
        "head.vuserdef", "body.vuserdef");
    
    /* yb query
    //getClientUI().getConDlginitself(getQueryDlg());
     */

    // ���˿����֯���ֿ�,��Ʒ��,�ͻ�,��Ӧ�̵�����Ȩ�ޣ����ţ�ҵ��Ա
    // zhy2005-06-10 �ͻ��͹�Ӧ�̲���Ҫ����ͨ���Ϲ��ˣ����ͻ������۳��ⵥ�Ϲ��ˣ���Ӧ���ڲɹ���ⵥ�Ϲ��ˣ�
    // zhy2007-02-12 V51������:3��
    // ���̡��������ࡢ�����֯����Ŀ������Ȩ�޿��ƣ����š��ֿ⡢������ࡢ������Ѷ���Ŀ��Աƥ���¼�Ŀ��ƣ�
    /**
     * ���Ա:head.cwhsmanagerid ����:head.cproviderid �����֯:head.pk_calbody
     * �ֿ�:head.cwarehouseid,head.cwastewarehouseid ��Ŀ:body.cprojectid
     * ����:head.cdptid �������:invcl.invclasscode ���:inv.invcode
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
    

    // zhy205-05-19 �ӿɻ�����������
    // �����
/*    getQueryDlg()
        .setCombox(
            "coalesce(body.noutnum,0)-coalesce(body.nretnum,0)-coalesce(body.ntranoutnum,0)",
            new Integer[][] { { new Integer(0), new Integer(0) } });
    // ���뵥
    getQueryDlg()
        .setCombox(
            "coalesce(body.ninnum,0)-coalesce(body.nretnum,0)-coalesce(body.ntranoutnum,0)",
            new Integer[][] { { new Integer(0), new Integer(0) } });*/
  }
  
  /**
   * �˴����뷽��˵���� ��������: ���ݲ�ѯ��������; ���롢������ݿ������¹���÷���,����������onquery���� �������: ����ֵ:
   * �쳣����: ����:
   * 
   * @return nc.vo.ic.pub.bill.QryConditionVO
   */
   protected QryConditionVO getQryConditionVO() {
    // ��Ӳ�ѯ
    /* yb query
    nc.vo.pub.query.ConditionVO[] voaCond = getConditionDlg()
        .getConditionVO();
    
    // ����繫˾����ҵ��Ա����
    voaCond = nc.ui.ic.pub.tools.GenMethod.procMultCorpDeptBizDP(voaCond,
        getBillTypeCode(), getCorpPrimaryKey());
    // ����nullΪ is null �� is not null add by hanwei 2004-03-31.01
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
    // addied by liuzy 2008-03-28 V5.03���󣺵��ݲ�ѯ������ֹ����
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
   * �����ߣ����˾� ���ܣ��õ��û�����Ķ����ѯ���� ������//��ѯ�������� ���أ� ���⣺ ���ڣ�(2001-5-9 9:23:32)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   * 
   * 
   * 
   * 
   */
  protected String getExtendQryCond(nc.vo.pub.query.ConditionVO[] voaCond) {
    // ����״̬����,ȱʡ��
    String sBillStatusSql = " (1=1) ";
    try {
      // -------- ��ѯ�����ֶ� itemkey ---------
      String sFieldCode = null;
      // �������в��������С����
      // ����״̬
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
      // ȱʡ��A
      if ("2".equals(sBillStatus)) // ����
        sBillStatusSql = " fbillflag="
            + nc.vo.ic.pub.bill.BillStatus.FREE;
      else if ("3".equals(sBillStatus)) // ǩ�ֵ�
        sBillStatusSql = " ( fbillflag="
            + nc.vo.ic.pub.bill.BillStatus.SIGNED
            + " OR fbillflag="
            + nc.vo.ic.pub.bill.BillStatus.AUDITED + ") ";

      // �˿��ѯ add by hanwei 2003-10-10
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

      // ȥ��freplenishflag �Ƿ��˿�
      String saItemKey[] = new String[] { "qbillstatus", "freplenishflag" };
      filterCondVO2(voaCond, saItemKey);
      // ��������
      String sOtherCond = voaCond[0].getWhereSQL(voaCond);
      if (sOtherCond != null)
        sBillStatusSql += " AND ( " + sOtherCond + " )";
    } catch (Exception e) {
      GenMethod.handleException(null, null, e);
    }

    return sBillStatusSql;
  }
  
  /**
   * �����ߣ����˾� ���ܣ��˵�����Ҫ�������ֶΣ�Ȼ�󷵻�֮ ������ ���أ� ���⣺ ���ڣ�(2001-8-17 13:13:51)
   * �޸����ڣ��޸��ˣ��޸�ԭ��ע�ͱ�־��
   */
  public void filterCondVO2(nc.vo.pub.query.ConditionVO[] voaCond,
      String[] saItemKey) {
    if (voaCond == null || saItemKey == null)
      return;
    // �ݴ���
    int j = 0;
    // ���鳤
    int len = saItemKey.length;
    for (int i = 0; i < voaCond.length; i++)
      // ��
      if (voaCond[i] != null)
        for (j = 0; j < len; j++) {
          if (saItemKey[j] != null
              && voaCond[i].getFieldCode() != null
              && saItemKey[j].trim().equals(
                  voaCond[i].getFieldCode().trim())) {
            // ���϶������ı���
            voaCond[i].setFieldCode("1");
            voaCond[i].setOperaCode("=");
            voaCond[i].setDataType(1);
            voaCond[i].setValue("1");
          }
        }
  }
  
  /*
   * 
   * ���أ�Object[] 1--UFBoolean �Ƿ��ѯ 2--ArrayList ��ѯ���vo List
   */
  public Object[] queryData(boolean isFrash) throws BusinessException{
    // DUYONG ���Ӳ�ѯ��ˢ�µĴ���
    try {
      Object[] ret = new Object[2];
      nc.vo.ic.pub.bill.Timer timer = new nc.vo.ic.pub.bill.Timer();
      timer.start("@@��ѯ��ʼ��");
      m_sBnoutnumnull = null;
      QryConditionVO voCond = null;

      // ��ԭ�������ͣ���Ƭ/�б���¼����������ڿ�Ƭ����ִ����ˢ�£��������л����б����
      //int cardOrList = getM_iCurPanel();
      // �����[(1)���в�ѯ(2)����û�н��в�ѯ�����ǵ����ˢ�°�ť]������ʾ��ѯģ����в�ѯ
      // ���������ѯ�������ҵ����ˢ�°�ť���������˶δ���
      if (!isFrash) {
        getQueryDlg().showModal();
        timer.showExecuteTime("@@getConditionDlg().showModal()��");

        if (getQueryDlg().getResult() != nc.ui.pub.beans.UIDialog.ID_OK){
          // ȡ������
          ret[0] = UFBoolean.FALSE;
          return ret;
        }

        // ���qrycontionVO�Ĺ���
        voCond = getQryConditionVO();

        // ��¼��ѯ��������
        m_voLastQryCond = voCond;

        // ����ǽ��в�ѯ���򽫡�������ѯ�����ı�ʶ���ó�true�����������ܹ����С�ˢ�¡��Ĳ�����
        m_bEverQry = true;
        // ˢ�°�ť
        //setButtonStatus(true);
      } else
        voCond = getVoLastQryCond();
      
      //addied by liuzy 2008-03-31 �����������ڡ��ӡ����������ֶ���
      
      /* yb query
       String str_where = voCond.getQryCond();
      if(null != str_where && !"".equals(str_where)){
        if(str_where.indexOf("dbilldate.from") > -1)
          str_where = str_where.replace("dbilldate.from", "dbilldate");
        if(str_where.indexOf("dbilldate.end") > -1)
          str_where = str_where.replace("dbilldate.end", "dbilldate");
        voCond.setQryCond(str_where);
       
      }*/

      // DUYONG �˴�Ӧ����ˢ�°�ť���ܵĿ�ʼִ�д�

      // ���ʹ�ù�ʽ����봫voaCond ����̨. �޸� zhangxing 2003-03-05
      //nc.vo.pub.query.ConditionVO[] voaCond = getClientUI().getConditionDlg()
      //    .getConditionVO();
      
      //addied by liuzy 2008-03-28 V5.03���󣺵��ݲ�ѯ������ֹ����
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
        // �Ƿ����ʵ������
        voCond.setParam(33, m_sBnoutnumnull);
      }

      voCond.setParam(QryConditionVO.QRY_LOGCORPID, getClientUI()
          .getCorpID());
      voCond.setParam(QryConditionVO.QRY_LOGUSERID, getClientUI()
          .getUserID());

      timer.showExecuteTime("Before ��ѯ����");

      ArrayList alListData = (ArrayList) GeneralBillHelper.queryBills(
          getClientUI().getBillType(), voCond);

      timer.showExecuteTime("��ѯʱ�䣺");
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
          
          // modified by liuzy 2010-9-7 ����03:23:28 ԭ����ѯ�ǿ��ǵ�Ȩ�ޣ�����û�п��ǵ�������
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
