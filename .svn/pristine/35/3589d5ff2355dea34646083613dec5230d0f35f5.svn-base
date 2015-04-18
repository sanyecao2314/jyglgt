/*==============================================================*/
/* Table: jyglgt_posanction                                     */
/*==============================================================*/
create table jyglgt_posanction 
(
   pk_posanction        CHAR(20)             not null,
   cdptid               CHAR(20),
   sanctiontype         VARCHAR2(10),
   concode              VARCHAR2(30),
   pk_order             VARCHAR2(20),
   pk_invbasdoc         VARCHAR2(20),
   pocode               VARCHAR2(30),
   invname              VARCHAR2(200),
   bucklenum            NUMBER(20,8),
   price                NUMBER(20,8),
   mny                  NUMBER(20,8),
   sanctionprice        NUMBER(20,8),
   sanctionmny          NUMBER(20,8),
   reason               VARCHAR2(512),
   pk_busitype          CHAR(20),
   vbillcode            VARCHAR2(50),
   cbillname            VARCHAR2(200),
   dbilldate            CHAR(10),
   vbillstatus          SMALLINT,
   billtype             CHAR(4),
   pk_operator          CHAR(20),
   dmakedate            CHAR(10),
   tmaketime            CHAR(19),
   modifyid             CHAR(20),
   modifydate           CHAR(10),
   tmodifytime          CHAR(19),
   vapproveid           CHAR(20),
   dapprovedate         CHAR(10),
   vapprovetime         CHAR(19),
   vapprovenote         VARCHAR2(500),
   vunapproveid         CHAR(20),
   vunapprovedate       CHAR(10),
   vunapprovetime       CHAR(19),
   vunapprovednote      VARCHAR2(200),
   vmemo                VARCHAR2(300),
   ts                   CHAR(19),
   dr                   SMALLINT,
   pk_corp              CHAR(20),
   vdef1                NUMBER(20,8),
   vdef2                NUMBER(20,8),
   vdef3                NUMBER(20,8),
   vdef4                NUMBER(20,8),
   vdef5                NUMBER(20,8),
   vdef6                VARCHAR2(300),
   vdef7                VARCHAR2(300),
   vdef8                VARCHAR2(300),
   vdef9                VARCHAR2(300),
   vdef10               VARCHAR2(300),
   vdef11               VARCHAR2(300),
   vdef12               VARCHAR2(300),
   vdef13               VARCHAR2(300),
   vdef14               VARCHAR2(300),
   vdef15               VARCHAR2(300),
   vdef16               VARCHAR2(300),
   vdef17               VARCHAR2(300),
   vdef18               VARCHAR2(300),
   vdef19               VARCHAR2(300),
   vdef20               VARCHAR2(300),
   constraint PK_JYGLGT_POSANCTION primary key (pk_posanction)
);

insert into pub_busiclass (ACTIONTYPE, CLASSNAME, DR, ISBEFORE, PK_BILLTYPE, PK_BUSICLASS, PK_BUSINESSTYPE, PK_CORP, TS)
values ('SAVE', 'N_JY01_SAVE', null, 'N', 'JY01', '0001AA100000000IU1QK', '', '', '2014-09-24 18:10:19');

insert into pub_busiclass (ACTIONTYPE, CLASSNAME, DR, ISBEFORE, PK_BILLTYPE, PK_BUSICLASS, PK_BUSINESSTYPE, PK_CORP, TS)
values ('APPROVE', 'N_JY01_APPROVE', null, 'N', 'JY01', '0001AA100000000IU1QL', '', '', '2014-09-24 18:12:13');

insert into pub_busiclass (ACTIONTYPE, CLASSNAME, DR, ISBEFORE, PK_BILLTYPE, PK_BUSICLASS, PK_BUSINESSTYPE, PK_CORP, TS)
values ('UNAPPROVE', 'N_JY01_UNAPPROVE', 0, 'N', 'JY01', 'SYSTEMJY01W000000004', '', '', '2014-10-10 15:51:12');

insert into pub_votable (APPROVEID, BILLID, BILLNO, BILLVO, BUSITYPE, DEF1, DEF2, DEF3, DR, HEADBODYFLAG, HEADITEMVO, ITEMCODE, OPERATOR, PK_BILLTYPE, PK_CORP, PK_VOTABLE, PKFIELD, TS, VOTABLE)
values ('vapproveid', 'pk_posanction', 'vbillcode', 'nc.vo.trade.pub.HYBillVO', '', '', '', '', null, 'Y', 'nc.vo.jyglgt.j400420.PosanctionVO', '', 'pk_operator', 'JY01', '', '0001AA100000000IU1QC', 'pk_posanction', '2014-09-24 16:47:15', 'jyglgt_posanction');
