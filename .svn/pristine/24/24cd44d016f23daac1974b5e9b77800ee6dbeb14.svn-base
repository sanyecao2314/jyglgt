/*==============================================================*/
/* alter Table: gt_credit                                  */
/*==============================================================*/
alter table gt_credit
add  ( payunit        VARCHAR2(100),
  PAYBANKACC     VARCHAR2(100),
  PAYBANK        VARCHAR2(100),
  RECEIVEUNIT    VARCHAR2(100),
  RECEIVEBANKACC VARCHAR2(100),
  RECEIVEBANK    VARCHAR2(100),
  dapprovedate char(10),
  vapprovenote VARCHAR2(200)
  );
  
insert into pub_busiclass (ACTIONTYPE, CLASSNAME, DR, ISBEFORE, PK_BILLTYPE, PK_BUSICLASS, PK_BUSINESSTYPE, PK_CORP, TS)
values ('SAVE', 'N_JY04_SAVE', 0, 'N', 'JY04', 'FBM_JY04_SAVE100101 ', '', '', '2007-08-31 15:40:43');

insert into pub_busiclass (ACTIONTYPE, CLASSNAME, DR, ISBEFORE, PK_BILLTYPE, PK_BUSICLASS, PK_BUSINESSTYPE, PK_CORP, TS)
values ('APPROVE', 'N_JY04_APPROVE', 0, 'N', 'JY04', 'SYSTEMJY04W000000003', '', '', '2008-02-16 15:51:12');

insert into pub_busiclass (ACTIONTYPE, CLASSNAME, DR, ISBEFORE, PK_BILLTYPE, PK_BUSICLASS, PK_BUSINESSTYPE, PK_CORP, TS)
values ('UNAPPROVE', 'N_JY04_UNAPPROVE', 0, 'N', 'JY04', 'SYSTEMJY04W000000004', '', '', '2014-10-10 15:51:12');

insert into pub_votable (APPROVEID, BILLID, BILLNO, BILLVO, BUSITYPE, DEF1, DEF2, DEF3, DR, HEADBODYFLAG, HEADITEMVO, ITEMCODE, OPERATOR, PK_BILLTYPE, PK_CORP, PK_VOTABLE, PKFIELD, TS, VOTABLE)
values ('vapproveid', 'pk_credit', 'vbillcode', 'nc.vo.trade.pub.HYBillVO', 'pk_busitype', 'JY04', '', '', null, 'Y', 'nc.vo.jyglgt.j400680.CreditVO', '', 'pk_operator', 'JY04', '', '1007AA10JY04000024EB', 'pk_credit', '2014-10-05 19:33:55', 'gt_credit');


