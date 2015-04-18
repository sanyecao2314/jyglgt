
create table BFZD
(	F_BH	char(20)	NOT Null,
	F_MC	char(80)	NOT Null,
        F_BZ	char(20)	 NOT Null
)
go
Create Unique index BFZD on BFZD(F_BZ,F_BH)
go

/*收付种类字典 20061125增加*/

create table BFSFZD
(	F_BH	char(4)	NOT Null,
	F_MC	char(60)	NOT Null,
        F_BZ	char(1)		NOT Null
)
go
Create Unique index BFSFZD on BFSFZD(F_BH)
go
/*帐期*/

create table BFZQ
(	F_BH	char(4)	NOT Null,
	F_MC	char(60)	NOT Null,
        F_SJ	INT	Null,
        F_BZ	char(1)		NOT Null
)
go
Create Unique index BFZQ on BFZQ(F_BH,F_BZ)
go


/*用户字典*/

create table BFUSER
(	F_ZGBH	Char(10)		NOT Null,
	F_ZGXM	Char(30)		NOT Null,
	F_PASS	Char(30)		 Null,
	F_GROUP	Char(255)	 Null,
	F_NOTE	VarChar(255)	 Null,
        F_CXSJ	INT  Default 0  null
)
go
create Unique index BFUSER ON BFUSER(F_ZGBH)
go
INSERT INTO BFUSER(F_ZGBH,F_ZGXM,F_PASS,F_NOTE,F_CXSJ) VALUES('0000','管理员','5@A0i>C01ABWJABW','',0)
INSERT INTO BFUSER(F_ZGBH,F_ZGXM,F_PASS,F_NOTE,F_CXSJ) VALUES('demo','管理员','n=7WN>7hh@@N','',0)
go
/*司磅地点字典*/

CREATE TABLE BFSGDZ
(       BFSGDZ_BH VarChar(2)  NOT Null,
        BFSGDZ_MC VarChar(40)     Null,
        BFSGDZ_GS VarChar(20)  NOT Null,
)
go
CREATE Unique index BFSGDZ on BFSGDZ(BFSGDZ_BH)
go

/*到货地点*/

CREATE TABLE BFFHDZ
(       BFFHDZ_DZBH VarChar(4)  NOT Null,
        BFFHDZ_FHDZ VarChar(40)  Null,
        BFFHDZ_GLS float,    /*公里数*/
        BFFHDZ_DJ float ,   /*吨公里单价*/
        BFFHDZ_YFDJ float,    /*运费单价*/
        BFFHDZ_HLSH float,    /*合理损耗*/
        BFFHDZ_C1     Char(60)        Null,      /*备用1*/
        BFFHDZ_C2     Char(60)        NulL,      /*备用2*/ 
)
go
CREATE Unique index BFFHDZ on BFFHDZ(BFFHDZ_DZBH)
go

/*地区简称*/

CREATE TABLE BFKZZD
(       BFKZZD_BH VarChar(4)  NOT Null,
        BFKZZD_MC VarChar(40)  Null        
)
go
CREATE Unique index BFKZZD on BFKZZD(BFKZZD_BH)
go

/*班次字典*/

CREATE TABLE BFJBYZD
(       BFJBYZD_JBYBH VarChar(4)  NOT Null,
        BFJBYZD_JBYMC VarChar(40)        Null
)
go
CREATE Unique index BFJBYZD on BFJBYZD(BFJBYZD_JBYBH)
go


/*IC卡管理*/

CREATE TABLE BFIC
(  BFIC_LSBH  Char(6)   NOT Null,        /*流水号*/
   BFIC_ICBH  CHAR(20)  NOT NULL,        /*卡号*/
   BFIC_BKRQ    Char(8)    Null,            /*发卡日期*/
   BFIC_GSRQ  Char(8)    Null,            /*挂失日期*/
   BFIC_SHBH  Char(20)       Null,        /*供应商编号*/
   BFIC_SHDW  CHAR(60)       Null,        /*供应商名称*/
   BFIC_WLBH  Char(30)   Null,            /*物料编号*/ 
   BFIC_FHDD  Char(30)   Null,            /*发货地点*/ 
   BFIC_ICBZ  Char(1)   Default '0',            /*IC卡状态： 0-正常 1-挂失*/ 
   BFIC_ICSY  FLOAT   Default 1,      /*IC使用数 */
   BFIC_ICGS  FLOAT   Default 0,      /*IC卡挂失数*/
   BFIC_DJBH  Char(20)         Null,      /*磅码单号*/
   BFIC_BHQZ  CHAR(2)          NULL,      /*过磅地点*/
   BFIC_ICXM  Char(10)         Null,      /*办卡人*/
   BFIC_CLBH     Char(20)        Null,      /*车辆编号*/
   BFIC_SJXM     Char(10)        NulL,      /*司机姓名*/  
   BFIC_FHBH     Char(20)        Null,      /*运输单位编号*/  
   BFIC_FHDW     Char(60)        Null,      /*运输单位*/  
   BFIC_CLLX     Char(20)        Null,      /*车辆类型*/  
   BFIC_C1     Char(60)        Null,      /*备用1*/
   BFIC_C2     Char(60)        NulL,      /*备用2*/  
   BFIC_C3     Char(60)        Null,      /*备用3*/  
   BFIC_C4     Char(60)        Null,      /*备用4*/  
   BFIC_C5    Char(60)        Null,      /*派车单号*/ 
   BFIC_PJPZ     float           null,      /*平均皮重*/
   BFIC_PZ     float           null,      /*皮重*/
   BFIC_U1     float           null,      /*备用数字1*/
   BFIC_U2     float           null,      /*备用数字2*/
   BFIC_U3     float           null       /*备用数字3*/
  )
go
CREATE Unique NonClustered index ID_BFIC ON BFIC(BFIC_LSBH)
CREATE Unique index BFIC_ICBH ON BFIC(BFIC_ICBH)
go

/*库存表*/

CREATE TABLE BFJCB
(  BFJCB_RQ    Char(8)   NOT Null,            /*日期*/
   BFJCB_WLBH  Char(30)  NOT Null,            /*货物编号*/ 
   BFJCB_CKBH  Char(30)  NOT Null,            /*仓库*/ 
   BFJCB_QCKC     float           null,      /*期初库存*/
   BFJCB_RRK     float           null,      /*采购入库*/
   BFJCB_YRK     float           null,      /*产品入库*/
   BFJCB_NRK     float           null,      /*调拨入库*/
   BFJCB_RCK     float           null,      /*销售出库*/
   BFJCB_YCK     float           null,      /*领料出库*/
   BFJCB_NCK     float           null,      /*调拨出库*/
   BFJCB_BRKC    float           null,      /*期末库存*/
   BFJCB_C1     Char(60)        Null,      /*备用1*/
   BFJCB_C2     Char(60)        NulL,      /*备用2*/  
   BFJCB_C3     Char(60)        Null,      /*备用3*/  
   BFJCB_C4     Char(60)        Null,      /**/  
   BFJCB_C5     Char(60)        Null,      /**/
   BFJCB_U1     float           null,      /*盘赢盘亏*/
   BFJCB_U2     float           null,      /*累计入库*/
   BFJCB_U3     float           null,      /*累计出库*/
   BFJCB_U4     float           null,      /**/
   BFJCB_U5     float           null     /**/ 
  )
go
CREATE Unique  index ID_BFJCB ON BFJCB(BFJCB_RQ,BFJCB_WLBH,BFJCB_CKBH)
go
/*车辆类型字典*/

CREATE TABLE BFCLLX
(       BFCLLX_LXBH VarChar(4)  NOT Null,
        BFCLLX_LXMC VarChar(20) Null,
        BFCLLX_MZ   float       Default 0
)
go
CREATE Unique index BFCLLX on BFCLLX(BFCLLX_LXBH)
go
/*车辆字典*/
CREATE TABLE BFCLZD
(       BFCLZD_CLBH     Char(15)                NOT Null,       /*车号*/
        BFCLZD_YSDW     Char(20)                  Null,  /*运输单位*/
        BFCLZD_LXMC     Char(20)                  Null,  /*车辆类型*/
        BFCLZD_CZXM     Char(8)                  Null,  /*车主姓名*/
        BFCLZD_PZ       float                    Default 0,  /*上次皮重*/
        BFCLZD_PJPZ     float                    Default 0,  /*平均皮重*/
        BFCLZD_KH       Char(30)                  Null,  /*卡号*/
        BFCLZD_FKRQ     Char(8)                  Null,  /*发卡日期*/
        BFCLZD_GSKH     Char(30)                  Null,  /*挂失卡号*/
        BFCLZD_GSRQ     Char(8)                  Null,  /*挂失日期*/
        BFCLZD_BKRQ     Char(8)                  Null,  /*补卡日期*/
        BFCLZD_JZ       float                    Default 0,  /*标准载重量*/
        BFCLZD_C1    Char(30)                  Null,  /*备用1*/
        BFCLZD_C2    Char(30)                  Null,  /*备用2*/
        BFCLZD_U1       float                    Default 0,  /*最小皮量*/
        BFCLZD_U2       float                    Default 0  /*最大皮重*/
)
go
CREATE Unique index BFCLZD on BFCLZD(BFCLZD_CLBH)
go

/*接口参数库*/

CREATE TABLE BFCOMSET
(  BFCOMSET_JKBH  Char(2)  NOT Null,            /*接口编号*/
   BFCOMSET_JKMC  Char(30) NOT Null,            /*接口名称*/
   BFCOMSET_COMID Char(4)  NOT Null,            /*串口编号，COM1*/
   BFCOMSET_COMCS Char(20) NOT Null,            /*串口参数*/
   BFCOMSET_TIMER float          Default 0.145,  /*时钟周期*/
   BFCOMSET_SUM   Integer  Default 0,           /*读数长度*/
   BFCOMSET_CS    Integer  Default 0,           /*读字符串起始位*/
   BFCOMSET_LONG  Integer       Default 0,              /*读字符串长度*/
   BFCOMSET_BZ    Integer       Default 0,              /*首末位字符标志*/
   BFCOMSET_SW          Char(1)                  Null,          /*首末位判断 0 首位*/
   BFCOMSET_ZF          Char(1)                  Null,          /*字符串是否反序  0 正序*/
   BFCOMSET_C1     Char(20)             Null,           /*asc/bcd/bin*/
   BFCOMSET_C2     Char(20)             Null,           /*预留字串2*/
   BFCOMSET_U1     float     Default 0,          /*预留数字1*/
   BFCOMSET_U2     float     Default 0           /*预留数字2*/
)
go
CREATE Unique NonClustered index ID_BFCOMSET on BFCOMSET(BFCOMSET_JKBH)
go
insert BFCOMSET VALUES('1','金钟衡器','COM1','2400,E,7,1',0.4,36,10,6,13,'0','0','asc','',0,0)
insert BFCOMSET VALUES('2','托利多8142pro','COM1','4800,E,7,1',0.15,26,5,6,41,'0','0','asc','',0,0)
insert BFCOMSET VALUES('3','上海耀华A系列','COM1','1200,n,8,1',0.4,20,2,6,2,'0','1','asc','',0,0)
insert BFCOMSET VALUES('4','上海耀华D系列','COM1','1200,n,8,1',0.4,20,2,6,61,'0','1','asc','',0,0)
insert BFCOMSET VALUES('5','深圳杰曼','COM1','4800,N,8,1',0.2,20,2,7,61,'0','1','bcd','',0,0)
insert BFCOMSET VALUES('6','长治维特','COM1','1200,n,7,1',0.4,20,2,7,61,'0','1','asc','',0,0)
insert BFCOMSET VALUES('7','山东鼎力3198','COM1','1200,e,7,1',0.6,20,3,6,42,'0','1','asc','',0,0)
insert BFCOMSET VALUES('8','山西太行仪表','COM1','1200,n,8,1',0.4,20,2,7,61,'0','1','asc','',0,0)
insert BFCOMSET VALUES('9','杭州衡天ht9800-C/V1/D','COM1','4800,n,8,2',0.4,20,2,6,255,'0','1','bcd','',0,0)
go

/*派车单/收货/调拨、通知单表*/

CREATE TABLE BFPCD
(  BFPCD_PCZH  Char(20)   NOT Null,        /*派车单号   通知单号*/
   BFPCD_CLBH  Char(15)   Null,            /*车辆编号*/
   BFPCD_CLLX  Char(20)   Null,            /*车辆类型  自卸 非自卸*/
   BFPCD_SJXM  CHAR(8)    NULL,            /*车主姓名*/
   BFPCD_RQ    Char(8)    Null,            /*单据日期  起始日期*/
   BFPCD_WLBH  Char(30)   Null,            /*物料编号  */ 
   BFPCD_JSSL  float      Default 0,       /*实收数 */
   BFPCD_YSSL  float      Default 0,       /*原发数 */
   BFPCD_HTBH  CHAR(20)         NULL,      /*合同编号  */ 
   BFPCD_SHBH  Char(20)       Null,        /*供应商编号 移出部门*/
   BFPCD_SHDW  CHAR(60)       Null,        /*供应商名称 移出部门名称*/
   BFPCD_FHBH  Char(20)        Null,       /*承运单位编号 */
   BFPCD_FHDW  CHAR(60)         Null,      /*单位名称  */
   BFPCD_SFBZ  Char(1)   Default '0',      /*收发标志 0 -收货  1-发货 2-收货通知单 3-调拨派车单,4-移库派车单 6-其他派车单 */
   BFPCD_HSBZ  Char(1)   Default '0',      /*单据标志 0-派出 1-返回 2-丢票 3-作废  0－审核  1 未审 */ 
   BFPCD_FHDD  Char(60)         Null,      /*收发地点*/
   BFPCD_PCR  Char(8)           Null,      /*派车人*/
   BFPCD_NOTE  Char(100)         Null,      /*备注*/
   BFPCD_C1     Char(60)        Null,      /*过磅日期 起始编号  铅封号  */
   BFPCD_C2     Char(60)        NulL,      /*过磅时间 终止编号  */  
   BFPCD_C3     Char(60)        Null,      /*过磅单号 */  
   BFPCD_C4     Char(60)        Null,      /*返回日期/派车部门    移入部门*/  
   BFPCD_C5     Char(60)        Null,      /*派车时间 终止日期*/  
   BFPCD_C6     Char(60)        Null,      /*返回日期   启用标志  移出仓库*/
   BFPCD_C7     Char(60)        NulL,      /*上传标志 0-未传 1-上传*/  
   BFPCD_C8     Char(60)        Null,       /*采样单号            移入仓库*/  
   BFPCD_C9     Char(60)        Null,       /*规格型号*/  
   BFPCD_C10    Char(60)        Null,       /*通知单号/司磅地点*/ 
   BFPCD_U1     float           null,       /*备用数量1*/
   BFPCD_U2     float           null,       /**/
   BFPCD_U3     float           null,       /*车数*/
   BFPCD_U4     float           null,       /*卸车费*/
   BFPCD_U5     float           null,       /*核定重量*/
   BFPCD_C11    Char(60)        Null,      /*联系电话*/
   BFPCD_C12    Char(60)        NulL,      /*过磅方式 0-直接过磅 1-等候指标 2-空水*/  
   BFPCD_C13    Char(60)        Null,      /*装货方式 结算方式  对方  我方 */  
   BFPCD_C14    Char(60)        Null,      /*化验项目*/  
   BFPCD_C15    Char(60)        Null,      /*混样标志*/  
   BFPCD_PDBH   char(60)        null,      /*排队编号*/
   BFPCD_DJBZ   Char(2)   Default '01' null,      /*单据标志 01-排队,02-要车 03-入场 04-采样 05-次过磅 06-开始卸货 07-卸货结束 08-二次过磅 09-出场*/ 
   BFPCD_PDSJ   datetime        null,      /*排队时间*/
   BFPCD_PDR    char(60)        null,      /*排队人*/
   BFPCD_YCSJ   datetime        null,      /*要车时间*/
   BFPCD_YCR    char(60)        null,      /*要车人*/
   BFPCD_YCBZ   Char(1)   Default '0' null,      /*要车标志 0-未要 1-已要*/ 
   BFPCD_YCBH    char(60)        null,      /*要车编号*/
   BFPCD_KSSJ    datetime        null,      /*扣水时间*/
   BFPCD_KSR     char(60)        null,      /*扣水人*/  
   BFPCD_KSL     float           null,       /*扣水数 */
   BFPCD_RCSJ    datetime        null,      /*入场时间*/
   BFPCD_RCR     char(60)        null,      /*入场人*/
   BFPCD_CYSJ    datetime        null,      /*采样时间*/
   BFPCD_CYR     char(60)        null,      /*采样人*/
   BFPCD_GBSJ1	 datetime        null,      /*一次过磅时间*/
   BFPCD_GBR1    char(60)        null,      /*一次过磅人*/
   BFPCD_QSSJ    datetime        null,      /*开始卸货时间*/
   BFPCD_QSR     char(60)        null,      /*卸货人人*/
   BFPCD_JSSJ    datetime        null,      /*卸完时间*/
   BFPCD_JSR     char(60)        null,      /*卸货人*/
   BFPCD_GBSJ2   datetime        null,      /*二次过磅时间*/
   BFPCD_GBR2    char(60)        null,      /*二次过磅人*/
   BFPCD_CCSJ    datetime        null,      /*出场时间*/
   BFPCD_CCR     char(60)        null,      /*出场人*/
   BFPCD_SJ1     float           null      /*排队 - 出场*/
  )
go
CREATE Unique NonClustered index ID_BFPCD ON BFPCD(BFPCD_PCZH DESC )
CREATE INDEX ID_BFPCD_3 ON BFPCD (BFPCD_RQ, BFPCD_SHBH, BFPCD_WLBH,BFPCD_C8)
CREATE INDEX ID_BFPCD_1 ON BFPCD (BFPCD_SFBZ, BFPCD_HSBZ, BFPCD_CLBH)
CREATE INDEX ID_BFPCD_2 ON BFPCD (BFPCD_SFBZ, BFPCD_HSBZ, BFPCD_C10)
go
/*皮带秤计量*/

CREATE TABLE BFPDC
(  BFPDC_DJBH  Char(12)   NOT Null,        /*单据编号*/
   BFPDC_SHDD  CHAR(2)    NOT NULL,        /*采集地点*/
   BFPDC_DJLX  Char(1)    DEFAULT '0' ,    /*单据类型  0-出库 1- 入库 2-调拨*/
   BFPDC_RQ    Char(8)    Null,            /*单据日期*/
   BFPDC_RCSJ  Char(8)    Null,            /*采集时间*/
   BFPDC_CKBH  Char(20)         Null,      /*仓库编号*/
   BFPDC_WLBH  Char(30)   Null,            /*物料编号*/ 
   BFPDC_JZ    float      Default 0,       /*数量  */
   BFPDC_SHBH  Char(20)       Null,        /*车间编号*/
   BFPDC_SHDW  CHAR(60)       Null,        /*车间名称*/
   BFPDC_RKBZ  Char(1)   Default '0',      /*入库标志：是否已入库 0- 未入库 1-出库 2-入库 */ 
   BFPDC_ZJDH  Char(20)         Null,      /*化验单号*/
   BFPDC_RKDH  Char(20)         Null,      /*入库单号*/
   BFPDC_BC    Char(8)         Null,       /*班次*/
   BFPDC_SBY   Char(8)           Null,     /*采集员*/
   BFPDC_NOTE  Char(100)         Null,     /*备注*/
   BFPDC_C1     Char(60)        Null,      /*备用1*/
   BFPDC_C2     Char(60)        NulL,      /*备用2*/  
   BFPDC_C3     Char(60)        Null,      /*备用3*/  
   BFPDC_C4     Char(60)        Null,      /*备用4*/  
   BFPDC_C5     Char(60)        Null,      /*备用5*/  
   BFPDC_U1     float           null,      /*起始读数*/
   BFPDC_U2     float           null,      /*终止读数*/
   BFPDC_U3     float           null,      /*扣除数*/
   BFPDC_U4     float           null,      /*备用数量4*/ 
   BFPDC_U5     float           null       /*备用数量5*/
  )
go
CREATE Unique NonClustered index ID_BFPDC ON BFPDC(BFPDC_DJBH,BFPDC_SHDD)
CREATE  NonClustered index ID_BFPDC_1 ON BFPDC(BFPDC_RKDH,BFPDC_ZJDH)
go

/*收货管理基础表*/

CREATE TABLE BFSFCL
(  BFSFCL_DJBH  Char(12)   NOT Null,        /*单据编号*/
   BFSFCL_SHDD  CHAR(2)    NOT NULL,        /*司磅地点*/
   BFSFCL_DJLX  Char(1)    DEFAULT '0' ,    /*单据类型  1-采购入库    2- 采购退货 3-生产入库 4-火车收货过磅单 9-分拆磅单*/
   BFSFCL_CLBH  Char(15)   Null,            /*车辆编号*/
   BFSFCL_CLLX  Char(20)   Null,            /*车辆类型*/
   BFSFCL_SJXM  CHAR(8)    NULL,            /*车主姓名*/
   BFSFCL_RQ    Char(8)    Null,            /*单据日期*/
   BFSFCL_JZRQ  Char(8)    Null,            /*入场日期*/
   BFSFCL_RCSJ  Char(8)    Null,            /*入厂时间*/
   BFSFCL_CCSJ  Char(8)    Null,            /*出厂时间*/
   BFSFCL_WLBH  Char(30)   Null,            /*物料编号*/ 
   BFSFCL_MZ    float      Default 0,       /*毛重*/
   BFSFCL_PZ    float      Default 0,       /*车辆皮重*/
   BFSFCL_BZW   float      Default 0,       /*包装物重*/
   BFSFCL_JZ    float      Default 0,       /*净重 bfsfcl_jz=bfsfcl_mz - bfsfcl_pz - bfsfcl_bzw */
   BFSFCL_WATER   float      Default 0,       /*扣水*/
   BFSFCL_ZAZHI   float      Default 0,       /*扣杂质*/
   BFSFCL_JKZ   float      Default 0,       /*加扣重量 bfsfcl_mz=bfsfcl_mz - bfsfcl_jkz*/
   BFSFCL_KZ    float      Default 0,       /*扣重*/  
   BFSFCL_JSSL  float      Default 0,       /*结算重量 bfsfcl_jssl=bfsfcl_jz - bfsfcl_zhazi - bfsfcl_water - bfsfcl_kz*/
   BFSFCL_YSSL  float      Default 0,       /*应收数量*/
   BFSFCL_LFS   float      Default 0,       /*大票数  */
   BFSFCL_CS    integer     Default 1,      /*车数*/
   BFSFCL_DJ    float      Default 0,       /*单价*/  
   BFSFCL_JE    float      Default 0,       /*金额*/
   BFSFCL_YFDJ  float      Default 0,       /*运费单价*/
   BFSFCL_YFJE  float      Default 0,       /*运费金额*/ 
   BFSFCL_HTBH  CHAR(20)         NULL,      /*质量合同*/ 
   BFSFCL_SHBH  Char(20)       Null,        /*供应商编号*/
   BFSFCL_SHDW  CHAR(60)       Null,        /*供应商名称*/
   BFSFCL_FHBH  Char(20)        Null,       /*承运单位编号*/
   BFSFCL_FHDW  CHAR(60)         Null,      /*单位名称*/
   BFSFCL_CJBZ  Char(1)   Default '0',      /*修改标志：是否被冲减  0-正常 1-补货 2-蓝单 3-错单 4-红单,5-补开错单 6-补单红单 7-补单冲减  8-冲减 9-重车出场 */
   BFSFCL_RKBZ  Char(1)   Default '0',      /*入库标志：是否已入库 0- 未入库 1-采购入库 2-生产入库  */ 
   BFSFCL_JSBZ  Char(1)   Default '0',      /*运费结算标志：是否生成结算单 0 -未计算  1 己计算,2-已结算*/ 
   BFSFCL_CLJS  CHAR(1)   DEFAULT  '0',     /*材料结算  0 - 未计算 1- 已计算 2-已结算*/
   BFSFCL_ZJDH  Char(20)         Null,      /*化验单号*/
   BFSFCL_YFDH  Char(20)         Null,      /*运费单号*/
   BFSFCL_RKDH  Char(20)         Null,      /*入库单号*/
   BFSFCL_JSDH  Char(20)         Null,      /*划价单号*/
   BFSFCL_CKBH  Char(20)         Null,      /*仓库编号*/
   BFSFCL_FHDD  Char(60)         Null,      /*发货地点*/
   BFSFCL_PCZH  Char(20)         Null,      /*派车单号*/
   BFSFCL_BC    Char(8)         Null,       /*班次*/
   BFSFCL_KZZD  Char(60)         Null,      /*收货通知单号  用友采购订单号*/
   BFSFCL_ZJXM  Char(10)         Null,      /*备份标志 0-正常 2-备份 3-补录 5-分拆*/
   BFSFCL_JBY   Char(8)           Null,     /*监磅员*/
   BFSFCL_SHY   Char(8)           Null,     /*收货员*/
   BFSFCL_SBY   Char(8)           Null,     /*司磅员*/
   BFSFCL_NOTE  Char(100)         Null,      /*备注*/
   BFSFCL_KZMX  Char(254)         Null,      /*扣重明细*/
   BFSFCL_C1     Char(60)        Null,      /*备用*/
   BFSFCL_C2     Char(60)        NulL,      /*备用*/  
   BFSFCL_C3     Char(60)        Null,      /*地区名称*/  
   BFSFCL_C4     Char(60)        Null,      /*发货日期/对方过磅日期*/  
   BFSFCL_C5     Char(60)        Null,      /*派车日期*/  
   BFSFCL_C6     Char(60)        Null,      /*订单id号*/
   BFSFCL_C7     Char(60)        NulL,      /*混样标志 0-单样 1-混样 入库id*/  
   BFSFCL_C8     Char(60)        Null,      /*采样流水号*/  
   BFSFCL_C9     Char(60)        Null,      /*规格型号*/  
   BFSFCL_C10    Char(60)        Null,      /*结算单号*/ 
   BFSFCL_U1     float           null,      /*备用数量*/
   BFSFCL_U2     float           null,      /*增值税率*/
   BFSFCL_U3     float           null,      /*运费税率*/
   BFSFCL_U4     float           null,      /*实收数量 bfsfcl_u4=bfsfcl_mz - bfsfcl_pz - bfsfcl_bzw - bfsfcl_zhazi - bfsfcl_water - bfsfcl_kz*/
   BFSFCL_U5     float           null,       /*装卸单价*/
   BFSFCL_U6     float     Default 0      null, /*件数*/
   BFSFCL_U7     float     Default 0      null, /*装卸费*/
   BFSFCL_U8     float     Default 0      null,/*扣水扣率*/
   BFSFCL_U9     float     Default 0      null,/*扣杂扣率 日报数 大票复磅数*/
   BFSFCL_U10     float     Default 0      null,/*出场打印次数*/
   BFSFCL_C11	 Char(60)  	Null,  /*联系电话*/
   BFSFCL_C12	 Char(60)  	Null,  /*收货部门*/
   BFSFCL_C13	 Char(60)  	Null,  /*审核标志*/
   BFSFCL_C14	 Char(60)  	Null,   /*审核人*/
   BFSFCL_C15	 Char(60)  	Null, /*运费合同*/
   ts            char(19)  null
  )
go
CREATE Unique NonClustered index ID_BFSFCL ON BFSFCL(BFSFCL_DJBH,BFSFCL_SHDD,BFSFCL_CJBZ)
go
CREATE Clustered index BFSFCL_CLBH ON BFSFCL(BFSFCL_RQ DESC ,BFSFCL_SHDD,BFSFCL_CLBH)
CREATE INDEX id_bfsfcl_1 ON BFSFCL (BFSFCL_JZ, BFSFCL_DJLX, BFSFCL_SHDD, BFSFCL_CJBZ,BFSFCL_CLBH)
CREATE INDEX id_bfsfcl_2 ON BFSFCL (BFSFCL_JZRQ, BFSFCL_SHBH, BFSFCL_WLBH, BFSFCL_C8)
CREATE INDEX id_bfsfcl_3 ON BFSFCL (BFSFCL_SHBH, BFSFCL_DJLX, BFSFCL_CLJS, BFSFCL_JSBZ, BFSFCL_RKBZ, BFSFCL_CJBZ, BFSFCL_JZ, BFSFCL_RQ, BFSFCL_WLBH)
CREATE INDEX id_bfsfcl_4 ON BFSFCL (BFSFCL_ZJDH, BFSFCL_JSDH, BFSFCL_RKDH, BFSFCL_YFDH)
CREATE INDEX id_bfsfcl_5 ON BFSFCL (BFSFCL_PCZH DESC, BFSFCL_JZ, BFSFCL_CJBZ, BFSFCL_DJLX)
go


/*发货基础表*/

CREATE TABLE BFFHCL
(  BFFHCL_DJBH  Char(12)   NOT Null,        /*单据编号*/
   BFFHCL_SHDD  CHAR(2)    NOT NULL,        /*司磅地点*/
   BFFHCL_DJLX  Char(1)    DEFAULT '0' ,    /*单据类型  1-销售出库  2-销售退货 3-移库单 4-火车发货过磅单  5-领料单  6-其它过磅单 7-*/
   BFFHCL_CLBH  Char(15)   Null,            /*车辆编号*/
   BFFHCL_SJXM  CHAR(8)    NULL,            /*车主姓名*/
   BFFHCL_CLLX  Char(20)   Null,            /*车辆类型*/
   BFFHCL_RQ    Char(8)    Null,            /*出场日期*/
   BFFHCL_JZRQ  Char(8)    Null,            /*   复磅日期*/
   BFFHCL_RCSJ  Char(8)    Null,            /*入厂时间*/
   BFFHCL_CCSJ  Char(8)    Null,            /*出厂时间*/
   BFFHCL_WLBH  Char(30)   Null,            /*物料编号*/ 
   BFFHCL_MZ    float      Default 0,       /*毛重 */
   BFFHCL_PZ    float      Default 0,       /*车辆皮重*/
   BFFHCL_BZW   float      Default 0,       /*包装物重*/
   BFFHCL_JZ    float      Default 0,       /*净重*/
   BFFHCL_DYSL  float      Default 0,       /*打印数量*/
   BFFHCL_LFS   float      Default 0,       /*立方数*/
   BFFHCL_CS    float      Default 0,       /*车数*/
   BFFHCL_DJ    float      Default 0,       /*单价*/  
   BFFHCL_JE    float      Default 0,       /*金额*/
   BFFHCL_YFDJ  float      Default 0,       /*运费单价*/
   BFFHCL_YFJE  float      Default 0,       /*运费金额*/
   BFFHCL_HTBH  CHAR(20)         NULL,      /*发货单号*/ 
   BFFHCL_TDLS  Char(20)         Null,      /*提单流水号*/
   BFFHCL_TDFL  Char(20)         Null,      /*提单分录*/
   BFFHCL_SHBH  Char(20)       Null,        /*客户编号 / 移出部门*/
   BFFHCL_SHDW  CHAR(60)       Null,        /*客户名称*/
   BFFHCL_FHBH  Char(20)        Null,       /*承运单位  */
   BFFHCL_FHDW  CHAR(60)         Null,      /*单位名称*/
   BFFHCL_CJBZ  Char(1)   Default '0',      /*修改标志：是否被冲减  0-正常 1-补货 2-蓝单 3-错单 4-红单,5-补开错单 6-补单红单 7-退票  8-冲减 9－空车出场 */
   BFFHCL_CKBZ  Char(1)   Default '0',      /*出库标志：是否已出库 1-销售出库  2-生产领料*/ 
   BFFHCL_JSBZ  Char(1)   Default '0',      /*运费结算：是否生成结算单 0 未结算 1-已结算*/ 
   BFFHCL_ZCBZ  CHAR(1)   DEFAULT  '0',     /*备份标志  0 正常 1-备份 3 手工录入*/
   BFFHCL_ZJDH  Char(20)         Null,      /*质检单号*/
   BFFHCL_CKDH  Char(20)         Null,      /*出库单号  调拨单号*/
   BFFHCL_YFDH  Char(20)         Null,      /*运费单号*/
   BFFHCL_CKBH  Char(20)         Null,      /*仓库编号  移出仓库*/
   BFFHCL_FHDD  Char(60)         Null,      /*收货地点*/
   BFFHCL_PCZH  Char(20)         Null,      /*派车证号 调拨通知单号*/
   BFFHCL_BC    Char(8)         Null,       /*班次*/
   BFFHCL_JBY   Char(8)           Null,     /*监磅员*/
   BFFHCL_SHY   Char(8)           Null,     /*发货员*/
   BFFHCL_SBY   Char(8)           Null,     /*司磅员*/
   BFFHCL_NOTE  Char(254)         Null,      /*备注*/
   BFFHCL_BKD1  Char(60)         Null,      /*补单打印一*/
   BFFHCL_BKD2  Char(60)         Null,      /*补单打印二*/
   BFFHCL_C1    Char(60)        Null,       /*铅封号*/
   BFFHCL_C2    Char(60)        NulL,       /*提单类型 */  
   BFFHCL_C3    Char(60)        Null,       /*ckid号*/  
   BFFHCL_C4    Char(60)        Null,       /*运费合同*/  
   BFFHCL_C5    Char(60)        Null,       /*销售合同号  调拨通知单号*/  
   BFFHCL_C6    Char(60)        Null,       /*移入部门/供货部门/出场磅单号*/
   BFFHCL_C7    Char(60)        NulL,       /*移入部门名称 结算单号*/  
   BFFHCL_C8    Char(60)        Null,       /*移入仓库 计算单号*/  
   BFFHCL_C9    Char(60)        Null,       /*规格型号*/  
   BFFHCL_C10   Char(60)        Null,       /*审核人*/
   BFFHCL_U1    float      Default 0 null,  /*备用数量1*/
   BFFHCL_U2    float      Default 0 null,  /*扣杂*/
   BFFHCL_U3    float      Default 0 null,  /**/
   BFFHCL_U4    float      Default 0 null,  /*审核标志*/
   BFFHCL_U5    float      Default 0 null,   /*复磅净重*/
   BFFHCL_U6     float     Default 0      null,   /*结算数*/
   BFFHCL_U7     float     Default 0      null,  /*增值税率*/
   BFFHCL_U8     float     Default 0      null,  /*结算标志 0-未计算 1-已计算 2-已结算*/
   BFFHCL_U9     float     Default 0      null,  /*  复磅毛重 件数*/
   BFFHCL_U10     float    Default 0      null, /*    复磅皮重*/
   ts            char(19)  null，
   BFFHCL_C11	 Char(60)  	Null,  /*联系电话*/
   BFFHCL_C12	 Char(60)  	Null,  /**/
   BFFHCL_C13	 Char(60)  	Null,  /**/
   BFFHCL_C14	 Char(60)  	Null,   /**/
   BFFHCL_C15	 Char(60)  	Null /**/
   )
go
CREATE Unique NonClustered index ID_BFFHCL ON BFFHCL(BFFHCL_DJBH,BFFHCL_SHDD,BFFHCL_TDLS,BFFHCL_TDFL,BFFHCL_CJBZ)
CREATE Clustered index BFFHCL_CLBH ON BFFHCL(BFFHCL_RQ DESC ,BFFHCL_SHDD,BFFHCL_CLBH)
CREATE INDEX id_bffhcl_1 ON BFFHCL (BFFHCL_JZ, BFFHCL_SHDD, BFFHCL_DJLX, BFFHCL_CJBZ, BFFHCL_U5,BFFHCL_CLBH)
CREATE INDEX id_bffhcl_2 ON BFFHCL (BFFHCL_SHBH, BFFHCL_DJLX, BFFHCL_JSBZ, BFFHCL_CKBZ, BFFHCL_CJBZ, BFFHCL_JZ, BFFHCL_RQ, BFFHCL_WLBH)
CREATE INDEX id_bffhcl_3 ON BFFHCL (BFFHCL_YFDH, BFFHCL_CKDH, BFFHCL_ZJDH)
CREATE INDEX id_bffhcl_4 ON BFFHCL (BFFHCL_PCZH DESC, BFFHCL_JZ, BFFHCL_CJBZ,BFFHCL_DJLX)
go


CREATE TABLE BFRBB
(  CZY  CHAR(10)    NULL,            
   RQ    Char(16)    Null,            
   SHBH  Char(20)       Null,       
   SHDW  CHAR(60)       Null,       
   WLBH  Char(30)   Null,           
   WLMC  CHAR(100)         Null,     
   BRCS  INT      Default 0,       
   BRFH  float      Default 0,       
   BYCS  INT      Default 0,      
   BYFH   float      Default 0
   )
go

/*化验单基础表*/

CREATE TABLE BFHYD
(  BFHYD_SHDD  Char(2)    NOT Null,             /*收货地点*/
   BFHYD_ZJDH  Char(12)   NOT Null,             /*化验单号*/
   BFHYD_SYBH  Char(20)   Null,  	   /*送样单位编号*/
   BFHYD_SYDW  CHAR(60)   Null,   	 /*送样单位名称*/
   BFHYD_SYRQ  Char(12)    Null,             /*送样日期*/
   BFHYD_HYRQ  Char(12)    Null,             /*化验日期*/  
   BFHYD_QSRQ  Char(8)    Null,             /*审核标志*/
   BFHYD_ZZRQ  Char(60)    Null,             /*审核人*/  
   BFHYD_CH    VARCHAR(2000)   Null,   	/*车号*/   
   BFHYD_ZJR   Char(20)   Null,   	/*混样操作员*/         
   BFHYD_FHR   Char(20)    Null,   	/*化验操作员*/         
   BFHYD_WLBH  Char(30)    Null,   	/*物料编号*/ 
   BFHYD_LRGS  float null  ,   	/*录入格式*/   
   BFHYD_JSBZ  Char(1)    NOT Null,   	/*化验标志 0,其他化验;1,原料采购化验;2,成品入库化验;3,产品销售化验;4,调拨化验;5,火车收货化验;6,火车发货化验*/  
   BFHYD_NOTE  Char(200)   Null,  	 /*备注*/  
   BFHYD_01   float      Default 0 null ,
   BFHYD_02   float      Default 0 null ,
   BFHYD_03   float      Default 0 null ,
   BFHYD_04   float      Default 0 null ,
   BFHYD_05   float      Default 0 null ,
   BFHYD_06   float      Default 0 null ,
   BFHYD_07   float      Default 0 null ,
   BFHYD_08   float      Default 0 null ,
   BFHYD_09   float      Default 0 null ,
   BFHYD_10   float      Default 0 null ,
   BFHYD_11   float      Default 0 null ,
   BFHYD_12   float      Default 0 null ,
   BFHYD_13   float      Default 0 null ,
   BFHYD_14   float      Default 0 null ,
   BFHYD_15   float      Default 0 null ,
   BFHYD_16   float      Default 0 null ,
   BFHYD_17   float      Default 0 null ,
   BFHYD_18   float      Default 0 null ,
   BFHYD_19   float      Default 0 null ,/*单据内码*/
   BFHYD_20   float      Default 0 null ,/*样品数量*/
   BFHYD_21   Char(60)   Null,/*收货部门*/
   BFHYD_22   Char(60)   Null,/*判定结果*/
   BFHYD_23   Char(60)   Null, /*采样依据*/
   BFHYD_24   Char(60)   Null, /*规格型号*/
   BFHYD_25   VARChar(2000)   Null,  /*采样单号*/
   BFHYD_26   Char(60)   Null,
   BFHYD_27   Char(60)   Null,
   BFHYD_28   Char(60)   Null, /**/
   BFHYD_29   Char(60)   Null, /**/
   BFHYD_30   Char(60)   Null,  /*二次编号标志*/
   BFHYD_31   Char(60)   Null,  /*一次编号*/
   BFHYD_32   Char(60)   Null, /*复检单对应化验单*/
   BFHYD_33   Char(60)   Null, /*采样员*/
   BFHYD_34   Char(60)   Null, /*制样员*/
   BFHYD_35   Char(60)   Null,  /*化验员*/   
   BFHYD_BC   CHAR(20)   NULL,
   BFHYD_CYDD CHAR(60)   NULL, /*采样地点*/
   BFHYD_36   Char(60)   Null,
   BFHYD_37   Char(60)   Null,
   BFHYD_38   Char(60)   Null, /**/
   BFHYD_39   Char(60)   Null, /**/
   BFHYD_40   Char(60)   Null,  /**/
   BFHYD_41   float      Default 0 null ,
   BFHYD_42   float      Default 0 null ,
   BFHYD_43   float      Default 0 null ,
   BFHYD_44   float      Default 0 null ,
   BFHYD_45   float      Default 0 null ,
   BFHYD_46   float      Default 0 null ,
   BFHYD_47   float      Default 0 null ,
   BFHYD_48   float      Default 0 null ,
   BFHYD_49   float      Default 0 null ,
   BFHYD_50   float      Default 0 null ,
   BFHYD_51   float      Default 0 null ,
   BFHYD_52   float      Default 0 null ,
   BFHYD_53   float      Default 0 null ,
   BFHYD_54   float      Default 0 null ,
   BFHYD_55   float      Default 0 null ,
   BFHYD_56   float      Default 0 null ,
   BFHYD_57   float      Default 0 null ,
   BFHYD_58   float      Default 0 null ,
   BFHYD_59   float      Default 0 null ,
   BFHYD_60   float      Default 0 null 
)
go
CREATE Unique NonClustered index ID_BFHYD ON BFHYD(BFHYD_ZJDH)
CREATE Clustered INDEX id_bfhyd_1 ON BFHYD (BFHYD_SYRQ DESC,BFHYD_WLBH,BFHYD_SYBH)
go


CREATE TABLE BFHYDPD
(	BFHYDPD_GSBH 	Char(20)		NOT Null,	/*流水编号 */
        BFHYDPD_LSBH 	Char(4)		NOT Null,	/*化验模板号 */
       	BFHYDPD_ZJXM 	Char(100)		not Null,	        /*质量指标 */
	BFHYDPD_XMMC 	Char(200)	Null,	        /*指标名称 */
	BFHYDPD_XX 	float		default 0,  /*标准下限 */
        BFHYDPD_SX   	float		Default 0,  /*标准上限 */ 
        BFHYDPD_PDZ	 Char(60)  	Null,	      /*判定值*/ 
	BFHYDPD_LJF	 Char(60)  	Null,	      /*逻辑符no 空  并且-and) 或者-or)*/   	      
 	BFHYDPD_LEFT	 Char(60)  	Null, /*左括号*/
 	BFHYDPD_RIGHT	 Char(60)  	Null, /*右括号*/	      
 	BFHYDPD_C1	 Char(60)  	Null, 
 	BFHYDPD_C2	 Char(60)  	Null,/*货物编号*/
        BFHYDPD_C3	 Char(60)  	Null,/*单位编号*/ 
 	BFHYDPD_C4	 Char(60)  	Null /*单位名称*/
)
go
CREATE Unique NonClustered index ID_BFHYDPD ON BFHYDPD(BFHYDPD_GSBH,BFHYDPD_LSBH)
go


/*销售发货单表*/

CREATE TABLE BFXSTD
(
BFXSTD_TDLS	VarChar(20)	NOT NULL,
BFXSTD_TDBH	Varchar(30)	NOT NULL,
BFXSTD_ZDRQ	Char(8)		NOT NULL,
BFXSTD_CSBZ	Char(1)		DEFAULT '0' NOT NULL,
BFXSTD_TDBZ	Char(1)		DEFAULT '0' NOT NULL,
BFXSTD_YLSH	VarChar(10)	NULL,
BFXSTD_DDBH	VarChar(20)	NULL,
BFXSTD_YWBH	VarChar(8)	 NULL,			
BFXSTD_ZLBH	Varchar(4)	NULL,
BFXSTD_SFLS       CHAR(1)	DEFAULT '0' NOT NULL,
BFXSTD_DWBH	Varchar(20)	NULL,
BFXSTD_BMBH	Varchar(20)	NULL,
BFXSTD_RYBH	Varchar(20)	NULL,
BFXSTD_WBBH	Varchar(10)	NULL,
BFXSTD_HL	float		DEFAULT 1 NOT NULL,
BFXSTD_ZKZC       CHAR(4)    NULL,
BFXSTD_HKRQ	Char(8)         DEFAULT '' NULL, 
BFXSTD_FSBH	Varchar(4)	NULL, /*发运方式 火运-是  汽运-否  */
BFXSTD_DHDD	Varchar(200)	NULL,
BFXSTD_BZ	Varchar(250)	NULL,
BFXSTD_ZDXM	Varchar(20)	NULL,
BFXSTD_JZBZ	Char(1)		DEFAULT '0' NOT NULL,
BFXSTD_JZRQ	Char(8)	        NULL,
BFXSTD_JZXM	Varchar(20)	NULL,
BFXSTD_JZLS	Varchar(10)	NULL,
BFXSTD_PZNM	Varchar(10)	NULL,
BFXSTD_PZRQ	Char(20)		NULL,  /*截止日期*/
BFXSTD_CKOK       CHAR(1)		 NULL,
BFXSTD_TWBZ       CHAR(1)		DEFAULT '0' NOT NULL,
BFXSTD_SJBZ	Char(1)		DEFAULT '0' NOT NULL,
BFXSTD_PJBZ	Char(1)		DEFAULT '0' NOT NULL,
BFXSTD_RYXM	Varchar(20)	NULL,
BFXSTD_DWMC	Varchar(120)	NULL,
BFXSTD_ADDR	Varchar(60)	NULL,
BFXSTD_TELE	Varchar(20)	NULL, /*ts*/
BFXSTD_C1		Varchar(100)	NULL, /*出库控制方式 ：不扣水正式出库、不扣水暂估出库、扣水暂估出库、扣水正式出库 */
BFXSTD_C2		Varchar(100)	NULL,
BFXSTD_C3		Varchar(100)	NULL,
BFXSTD_C4		Varchar(100)	NULL,
BFXSTD_C5		Varchar(100)	NULL,
BFXSTD_U1		float		DEFAULT 0  NULL,
BFXSTD_U2		float		DEFAULT 0  NULL,
BFXSTD_U3		float		DEFAULT 0  NULL
)
go
CREATE unique INDEX BFXSTD_1 on BFXSTD(BFXSTD_TDLS)
go
CREATE INDEX BFXSTD_2 on BFXSTD(BFXSTD_TDBH)
go
/*销售发货单子表*/

CREATE TABLE BFXSTDMX
(
BFXSTDMX_TDLS	VarChar(20)	NOT NULL,
BFXSTDMX_TDFL	VarChar(20)	NOT NULL,
BFXSTDMX_CKBH	Varchar(20)	DEFAULT '' NULL,
BFXSTDMX_WLBH	Varchar(30)	NOT NULL,
BFXSTDMX_DJBH CHAR(20)    NULL, /*子表id*/
BFXSTDMX_PCH      Varchar(20)     DEFAULT '' NULL, /*主表id*/
BFXSTDMX_SCRQ     char(8)         DEFAULT '' NULL,  /*pk_corp*/
BFXSTDMX_FSL1	float		DEFAULT 0 NOT NULL,
BFXSTDMX_FSL2	float		DEFAULT 0 NOT NULL,/*年度结转出库数量*/
BFXSTDMX_ZSL	float		DEFAULT 0 NOT NULL,
BFXSTDMX_ZHSJ	float		DEFAULT 0 NOT NULL,/*含税单价*/
BFXSTDMX_ZKBL float		DEFAULT 0 NOT NULL,/*付款数量*/
BFXSTDMX_ZKJE float		DEFAULT 0 NOT NULL,/*付款数量*/
BFXSTDMX_YHSE	float		DEFAULT 0 NOT NULL,/*含税金额*/
BFXSTDMX_BHSE	float		DEFAULT 0 NOT NULL,
BFXSTDMX_SL	float		DEFAULT 0 NOT NULL,
BFXSTDMX_ZXSJ	float		DEFAULT 0 NOT NULL,
BFXSTDMX_YXSE	float		DEFAULT 0 NOT NULL,
BFXSTDMX_BXSE	float		DEFAULT 0 NOT NULL,
BFXSTDMX_SE	float		DEFAULT 0 NOT NULL,
BFXSTDMX_FHSJ1	float		DEFAULT 0 NOT NULL,/*单位换算*/
BFXSTDMX_FHSJ2	float		DEFAULT 0 NOT NULL,/*控制*/
BFXSTDMX_FXSJ1	float		DEFAULT 0 NOT NULL,/*开票数量*/
BFXSTDMX_FXSJ2	float		DEFAULT 0 NOT NULL,/*开票金额*/
BFXSTDMX_KPSL	float		DEFAULT 0 NOT NULL,
BFXSTDMX_YKPE	float		DEFAULT 0 NOT NULL,/*出库金额*/
BFXSTDMX_BKPE	float		DEFAULT 0 NOT NULL, /*退货金额*/
BFXSTDMX_KPBZ	Char(1)		DEFAULT '0' NOT NULL,
BFXSTDMX_CKSL	float		DEFAULT 0 NOT NULL,
BFXSTDMX_CKBZ	Char(1)		DEFAULT '0' NOT NULL,
BFXSTDMX_THSL	float		DEFAULT 0 NOT NULL,
BFXSTDMX_TWBZ	Char(1)	DEFAULT '0' NOT NULL,
BFXSTDMX_C1	Varchar(100)	NULL,
BFXSTDMX_C2	Varchar(100)	NULL,
BFXSTDMX_C3	Varchar(100)	NULL,
BFXSTDMX_C4	Varchar(100)	NULL, /*pk_corp*/
BFXSTDMX_C5	Varchar(100)	NULL, /*付款单号*/
BFXSTDMX_U1	float		DEFAULT 0 NOT NULL,/*出库件数*/
BFXSTDMX_U2	float		DEFAULT 0 NOT NULL,/*退货件数*/
BFXSTDMX_U3	float		DEFAULT 0 NOT NULL
)
go
CREATE unique INDEX BFXSTDMX on BFXSTDMX(BFXSTDMX_TDLS,BFXSTDMX_TDFL)
go
/*补开单库*/

CREATE TABLE BFBKDJ
(	BFBKDJ_DJBH 	Char(20) NOT Null,
	BFBKDJ_TDBH 	Char(20) NOT Null,
	BFBKDJ_WLBH 	Char(30) 	 Null,
        BFBKDJ_TDLS  Char(20)                Null,           /*对应提单流水号*/
        BFBKDJ_TDFL  Char(20)                Null,           /*对应提单分录*/ 
        BFBKDJ_DWBH  Char(20)  		Null,		/*客户编号*/
        BFBKDJ_DWMC Char(60)  		Null,		/*客户名称*/
        BFBKDJ_BZW   float            Default 0,   
        BFBKDJ_DJ    float      Default 0,	/*销售单价*/  
        BFBKDJ_JE    float      Default 0,	/*销售金额*/
	BFBKDJ_ZL    float 		Default 0,
	BFBKDJ_SJZL  float	 	Default 0,
        BFBKDJ_BMBH  CHAR(20)    NULL,
        BFBKDJ_RYXM  CHAR(10)    NULL  
)
go
CREATE Unique NonClustered index ID_BFBKDJ on BFBKDJ(BFBKDJ_DJBH,BFBKDJ_TDBH,BFBKDJ_WLBH)
go

CREATE TABLE BFBKDJ1
(	BFBKDJ1_HTBH 	Char(20)	NOT Null,	/*合同编号 */
       	BFBKDJ1_SHBH 	Char(20)	    Null,	        /*客户 */
        BFBKDJ1_WLBH 	Char(30)	NOT Null,	        /*货物 */
        BFBKDJ1_JSYJ 	Char(1)	       default '0',	        /* */
	BFBKDJ1_DJ   	float		Default 0,  /*合同单价 */  
        BFBKDJ1_ZZSL 	float		default 0,  /*增值税率 */
        BFBKDJ1_YFDJ   	float		Default 0,  /*运费单价 */  
        BFBKDJ1_SHL 	float		Default 0,  /*合理损耗率 */
        BFBKDJ1_RKSL     float		Default 0,     /*剩余数量 */
        BFBKDJ1_BZ     float		Default 0,     /*补重 */
        BFBKDJ1_U1	 float          Default 0 NULL,  /*预留数1*/  
        BFBKDJ1_U2	 float          Default 0 NULL,   /*预留数1*/
        BFBKDJ1_C1 	Char(20)	    Null,	        /*  */
	BFBKDJ1_C2	Char(20)	    Null	        /*  */

)
go
CREATE Unique NonClustered index ID_BFBKDJ1 on BFBKDJ1(BFBKDJ1_HTBH)
go

/*运费结算*/
CREATE TABLE BFYFJS
(
BFYFJS_JSBH	Varchar(12)	NOT NULL,
BFYFJS_ZDRQ	Char(8)		NOT NULL,/*制单日期*/
BFYFJS_ZZRQ	Char(8)		NOT NULL,/*结算日期*/
BFYFJS_SHBH	VarChar(20)	NULL,/*供货单位*/
BFYFJS_FHBH	VarChar(20)	NULL,/*承运单位*/			
BFYFJS_CLBH	Varchar(15)	NULL,/*车号*/
BFYFJS_HTBH	VarChar(20)	NULL,/*运费合同*/	
BFYFJS_FHDD	VarChar(20)	NULL,/*发货地点*/	
BFYFJS_SFBZ	Varchar(1)	NULL,/*收发标志  0--收货单 1-发货单*/
BFYFJS_BZ		Varchar(100)	NULL,/*备注*/
BFYFJS_ZDXM	Varchar(20)	NULL,/*制单人*/
BFYFJS_SHBZ	Char(1)		DEFAULT '0' NOT NULL,
BFYFJS_SHXM	Varchar(20)	NULL,
BFYFJS_C1		Varchar(100)	NULL,/*结算单位名称*/
BFYFJS_C2		Varchar(100)	NULL,/*承运单位名称*/
BFYFJS_C3		Varchar(100)	NULL,/*货物名称*/
BFYFJS_C4		Varchar(100)	NULL,/**/
BFYFJS_C5		Varchar(100)	NULL,
BFYFJS_U1		float		DEFAULT 0  NULL,
BFYFJS_U2		float		DEFAULT 0  NULL,
BFYFJS_U3		float		DEFAULT 0  NULL
)
go
CREATE unique INDEX BFYFJS_1 on BFYFJS(BFYFJS_JSBH)
go


CREATE TABLE BFYFJSMX
(
BFYFJSMX_JSBH	VarChar(12)	NOT NULL,
BFYFJSMX_SHBH	VarChar(20)	NOT NULL,/*结算单位编号*/
BFYFJSMX_YSSL	float		DEFAULT 0  NULL,/*应收数*/
BFYFJSMX_SL	float		DEFAULT 0  NULL,/*实收数*/
BFYFJSMX_DJ	float		DEFAULT 0  NULL,/*运费单价*/
BFYFJSMX_JE	float		DEFAULT 0  NULL,/*运费金额*/
BFYFJSMX_SHL	float		DEFAULT 0  NULL,/*路耗率*/
BFYFJSMX_KFL	float		DEFAULT 0  NULL,/*亏吨*/
BFYFJSMX_KFDJ	float		DEFAULT 0  NULL,/*货物单价*/
BFYFJSMX_KFJE	float		DEFAULT 0  NULL,/*亏吨扣款*/
BFYFJSMX_QTKK	float		DEFAULT 0  NULL,/*其他扣款*/
BFYFJSMX_SJJE	float		DEFAULT 0  NULL,/*实际结款*/
BFYFJSMX_C1	Varchar(100)	NULL,   /*起始日期  出境单号*/
BFYFJSMX_C2	Varchar(100)	NULL,/*截止日期  发货地点*/
BFYFJSMX_C3	Varchar(100)	NULL,   /*结算单位名称*/
BFYFJSMX_C4	Varchar(100)	NULL,/*车号*/
BFYFJSMX_C5	Varchar(100)	NULL,   /*发货地点  司机姓名*/
BFYFJSMX_C6	Varchar(100)	NULL,/*磅码单号*/
BFYFJSMX_U1	float		DEFAULT 0  NULL,/*税率 / 出境单价*/
BFYFJSMX_U2	float		DEFAULT 0  NULL,/*车数*/
BFYFJSMX_U3	float		DEFAULT 0  NULL,/*不含税金额 /出境数量*/
BFYFJSMX_U4	float		DEFAULT 0  NULL,/* 过磅数 出境扣价 大票数  */
BFYFJSMX_U5	float		DEFAULT 0  NULL, /* 扣杂  增值税率 大票单价*/
BFYFJSMX_C7	Varchar(60)	NULL, /*物料编号*/
BFYFJSMX_C8	Varchar(60)	NULL, /*承运单位编号*/
BFYFJSMX_C9	Varchar(60)	NULL, /*司机姓名*/
BFYFJSMX_C10	Varchar(60)	NULL, /*运费结转单号*/
BFYFJSMX_U6	float	DEFAULT 0  NULL,/*运费结转标志*/
BFYFJSMX_U7	float	DEFAULT 0  NULL,/*挂牌单价*/
BFYFJSMX_U8	float	DEFAULT 0  NULL,/*代扣税率*/
BFYFJSMX_U9	float	DEFAULT 0  NULL,/*扣款税率*/
BFYFJSMX_U10	float	DEFAULT 0  NULL, /*划价依据*/
BFYFJSMX_U11	float	DEFAULT 0  NULL,/**/
BFYFJSMX_U12	float	DEFAULT 0  NULL,/**/
BFYFJSMX_U13	float	DEFAULT 0  NULL,/**/
BFYFJSMX_U14	float	DEFAULT 0  NULL,/*合同水*/
BFYFJSMX_U15	float	DEFAULT 0  NULL, /*结算数量*/
BFYFJSMX_C11	Varchar(100)	NULL,   /*pk_corp*/
BFYFJSMX_C12	Varchar(100)	NULL,/**/
BFYFJSMX_C13	Varchar(100)	NULL,   /**/
BFYFJSMX_C14	Varchar(100)	NULL,/*火车号*/
BFYFJSMX_C15	Varchar(100)	NULL   /*运费合同*/
)
go
CREATE unique INDEX BFYFJSMX on BFYFJSMX(BFYFJSMX_JSBH,BFYFJSMX_SHBH,BFYFJSMX_C6)
go


/*材料核算表*/

CREATE TABLE BFCLJS
(
BFCLJS_JSBH	Varchar(12)	NOT NULL,/*结算编号*/
BFCLJS_ZDRQ	Char(8)		NOT NULL,/*制单日期*/
BFCLJS_ZZRQ	Char(8)		NOT NULL,/*审核日期*/
BFCLJS_CGDH	VarChar(20)	NULL,/*采购合同号*/
BFCLJS_SHBH	VarChar(20)	NULL,/*结算单位*/
BFCLJS_BZ	Varchar(100)	NULL,/*备注*/
BFCLJS_ZDXM	Varchar(20)	NULL,/*制单人*/
BFCLJS_SHBZ	Char(1)		DEFAULT '0' NOT NULL, /*审核标志*/
BFCLJS_SHXM	Varchar(20)	NULL, /*审核人*/
BFCLJS_C1		Varchar(100)	NULL,/*单位名称*/
BFCLJS_C2		Varchar(100)	NULL,/*物料编号*/
BFCLJS_C3		Varchar(100)	NULL,
BFCLJS_C4		Varchar(100)	NULL,/*结算类型 0-材料结算 1-产品结算*/
BFCLJS_C5		Varchar(100)	NULL, /*结算类别 0-单车结算 1-汇总结算*/
BFCLJS_U1		float		DEFAULT 0  NULL,
BFCLJS_U2		float		DEFAULT 0  NULL,
BFCLJS_U3		float		DEFAULT 0  NULL
)
go
CREATE unique INDEX BFCLJS_1 on BFCLJS(BFCLJS_JSBH)
go


CREATE TABLE BFCLJSMX
(
BFCLJSMX_JSBH	VarChar(12)	NOT NULL,/*结算单号*/
BFCLJSMX_BMDH	VarChar(12)	 NULL,/*磅单号*/
BFCLJSMX_WLBH	Varchar(30)	NOT NULL,/*物料编号*/
BFCLJSMX_HTBH	Varchar(20)	 NULL,/*合同编号*/
BFCLJSMX_ZJDH	Varchar(12)	 NULL,/*质检单号*/
BFCLJSMX_YSSL	float		DEFAULT 0  NULL,/*应收数*/
BFCLJSMX_SSSL	float		DEFAULT 0  NULL,/*实收数*/
BFCLJSMX_KZ	float		DEFAULT 0  NULL,/*扣水*/
BFCLJSMX_JKZ	float		DEFAULT 0  NULL,/*扣杂*/
BFCLJSMX_JSSL	float		DEFAULT 0  NULL,/*结算数量*/
BFCLJSMX_DJ	float		DEFAULT 0  NULL,/*合同价*/
BFCLJSMX_JSDJ	float		DEFAULT 0  NULL,/*结算单价*/
BFCLJSMX_JE	float		DEFAULT 0  NULL,/*实结算金额*/
BFCLJSMX_KZMX	Varchar(250)	NULL,/*扣重明细*/
BFCLJSMX_C1	Varchar(60)	NULL,/*过磅日期*/
BFCLJSMX_C2	Varchar(60)	NULL,/*发货地点*/
BFCLJSMX_C3	Varchar(60)	NULL,/*pk_corp*/
BFCLJSMX_C4	Varchar(60)	NULL,/*规格型号*/
BFCLJSMX_C5	Varchar(60)	NULL,/*车号*/
BFCLJSMX_C6	Varchar(60)	NULL,/*提单流水   */
BFCLJSMX_U1	float		DEFAULT 0  NULL,/*扣重比率*/
BFCLJSMX_U2	float		DEFAULT 0  NULL,/*增值税率*/
BFCLJSMX_U3	float		DEFAULT 0  NULL,/*无税金额*/
BFCLJSMX_U4	float		DEFAULT 0  NULL,/*折扣率*/
BFCLJSMX_U5	float		DEFAULT 0  NULL,  /*大票数*/
BFCLJSMX_U6	float	DEFAULT 0  NULL, /*挂牌单价*/
BFCLJSMX_U7	float	DEFAULT 0  NULL, /*结算金额*/
BFCLJSMX_U8	float	DEFAULT 0  NULL, /*代扣运费*/
BFCLJSMX_U9	float	DEFAULT 0  NULL, /*车数*/
BFCLJSMX_U10	float	DEFAULT 0  NULL,/*大票复磅数*/
BFHYD_01   float      Default 0 null, 
BFHYD_02   float      Default 0 null, 
BFHYD_03   float      Default 0 null, 
BFHYD_04   float      Default 0 null, 
BFHYD_05   float      Default 0 null, 
BFHYD_06   float      Default 0 null, 
BFHYD_07   float      Default 0 null, 
BFHYD_08   float      Default 0 null, 
BFHYD_09   float      Default 0 null, 
BFHYD_10   float      Default 0 null, 
BFHYD_11   float      Default 0 null, 
BFHYD_12   float      Default 0 null, 
BFHYD_13   float      Default 0 null, 
BFHYD_14   float      Default 0 null, 
BFHYD_15   float      Default 0 null, 
BFHYD_16   float      Default 0 null, 
BFHYD_17   float      Default 0 null, /**/
BFHYD_18   float      Default 0 null,/*运费单价*/ 
BFHYD_19   float      Default 0 null,/*运费金额*/ 
BFHYD_20   float      Default 0 null, /*运费税率*/
BFHYD_011   float      Default 0 null, 
BFHYD_021   float      Default 0 null, 
BFHYD_031   float      Default 0 null, 
BFHYD_041   float      Default 0 null, 
BFHYD_051   float      Default 0 null, 
BFHYD_061   float      Default 0 null, 
BFHYD_071   float      Default 0 null, 
BFHYD_081   float      Default 0 null, 
BFHYD_091   float      Default 0 null, 
BFHYD_101   float      Default 0 null, 
BFHYD_111   float      Default 0 null, 
BFHYD_121   float      Default 0 null, 
BFHYD_131   float      Default 0 null, 
BFHYD_141   float      Default 0 null, 
BFHYD_151   float      Default 0 null, 
BFHYD_161   float      Default 0 null, 
BFHYD_171   float      Default 0 null, 
BFHYD_181   float      Default 0 null,
BFHYD_191   float      Default 0 null,
BFHYD_201  float      Default 0 null,
BFHYD_41   float      Default 0 null, 
BFHYD_42   float      Default 0 null, 
BFHYD_43   float      Default 0 null, 
BFHYD_44   float      Default 0 null, 
BFHYD_45   float      Default 0 null, 
BFHYD_46   float      Default 0 null, 
BFHYD_47   float      Default 0 null, 
BFHYD_48   float      Default 0 null, 
BFHYD_49   float      Default 0 null, 
BFHYD_50   float      Default 0 null, 
BFHYD_51   float      Default 0 null, 
BFHYD_52   float      Default 0 null, 
BFHYD_53   float      Default 0 null, 
BFHYD_54   float      Default 0 null, 
BFHYD_55   float      Default 0 null, 
BFHYD_56   float      Default 0 null, 
BFHYD_57   float      Default 0 null,  
BFHYD_58   float      Default 0 null, 
BFHYD_59   float      Default 0 null, 
BFHYD_60   float      Default 0 null, 
BFHYD_411   float      Default 0 null, 
BFHYD_421   float      Default 0 null, 
BFHYD_431   float      Default 0 null, 
BFHYD_441   float      Default 0 null, 
BFHYD_451   float      Default 0 null, 
BFHYD_461   float      Default 0 null, 
BFHYD_471   float      Default 0 null, 
BFHYD_481   float      Default 0 null, 
BFHYD_491   float      Default 0 null, 
BFHYD_501   float      Default 0 null, 
BFHYD_511   float      Default 0 null, 
BFHYD_521   float      Default 0 null, 
BFHYD_531   float      Default 0 null, 
BFHYD_541   float      Default 0 null, 
BFHYD_551   float      Default 0 null, 
BFHYD_561   float      Default 0 null, 
BFHYD_571   float      Default 0 null, 
BFHYD_581   float      Default 0 null,
BFHYD_591   float      Default 0 null,
BFHYD_601  float      Default 0 null  
)
go
CREATE unique INDEX BFCLJSMX_ID on BFCLJSMX(BFCLJSMX_JSBH,BFCLJSMX_BMDH,BFCLJSMX_HTBH,BFCLJSMX_C6)
go


/*结算单*/

CREATE TABLE BFCLJZD
(
BFCLJZD_JSBH	Varchar(12)	NOT NULL, /*采购结算单号  运费结算单号  销售结算单号*/
BFCLJZD_ZDRQ	Char(8)		NOT NULL, /*制单日期*/
BFCLJZD_QSRQ	Char(8)		 NULL, /*起始日期*/
BFCLJZD_ZZRQ	Char(8)		 NULL, /*截止日期*/
BFCLJZD_HTBH	VarChar(20)	NULL, /*采购合同编号 运费合同编号  发货单号*/
BFCLJZD_SHBH	VarChar(20)	NULL, /*供货单位编号  承运单位编号  购货单位编号*/
BFCLJZD_SHDW	VarChar(60)	NULL, /*单位名称*/
BFCLJZD_WLBH	Varchar(30)	NOT NULL, /*货物名称*/
BFCLJZD_YSSL	float		DEFAULT 0  NULL, /*原发数 运费单价  本方过磅数*/
BFCLJZD_SSSL	float		DEFAULT 0  NULL, /*过磅数  运费金额  对方收到数*/
BFCLJZD_SHL	float		DEFAULT 0  NULL, /*亏吨  亏吨扣款    路耗*/
BFCLJZD_KZ	float		DEFAULT 0  NULL, /*扣水  其他扣款    扣水*/
BFCLJZD_KS	float		DEFAULT 0  NULL, /*扣杂   亏吨       扣沫*/
BFCLJZD_JSSL	float		DEFAULT 0  NULL, /*实际结算数量   结算数量 结算数量*/  
BFCLJZD_HSDJ	float		DEFAULT 0  NULL, /*结算单价  结算单价  结算单价*/
BFCLJZD_HSJE	float		DEFAULT 0  NULL, /*结算金额   结算金额 结算金额 */
BFCLJZD_DJ	float		DEFAULT 0  NULL, /*无税单价*/
BFCLJZD_JE	float		DEFAULT 0  NULL, /*无税金额*/
BFCLJZD_SL	float		DEFAULT 0  NULL, /*税率*/
BFCLJZD_SE	float		DEFAULT 0  NULL, /*进项税*/
BFCLJZD_CS	float		DEFAULT 0  NULL, /*车数*/
BFCLJZD_BZ	Varchar(250)	NULL, /*备注*/
BFCLJZD_ZDXM	Varchar(20)	NULL, /*制单人*/
BFCLJZD_SHBZ	Char(1)		DEFAULT '0' NOT NULL, /*0-采购结算单  1-采购运费结算单 2-销售结算单 3-销售运费结算单*/
BFCLJZD_SHXM	Varchar(20)	NULL, /*审核人*/
BFCLJZD_C1		Varchar(100)	NULL, /*车号*/
BFCLJZD_C2		Varchar(100)	NULL, /*rkid*/
BFCLJZD_C3		Varchar(100)	NULL, /*审核标志*/
BFCLJZD_C4		Varchar(100)	NULL, /*单位编号 用于运费结算*/ 
BFCLJZD_C5		Varchar(100)	NULL, /*供货单位 用于运费结算*/
BFCLJZD_U1		float		DEFAULT 0  NULL, /*应结算数量  运费调整金额  应结算数量*/
BFCLJZD_U2		float		DEFAULT 0  NULL, /*应结算金额                应结算金额*/
BFCLJZD_U3		float		DEFAULT 0  NULL,  /*调整金额  货物单价  调整金额*/
BFCLJZD_U4		float		DEFAULT 0  NULL, /*已到票数量*/
BFCLJZD_U5		float		DEFAULT 0  NULL, /*已到票金额*/
BFCLJZD_U6		float		DEFAULT 0  NULL, /*已付款数量*/
BFCLJZD_U7		float		DEFAULT 0  NULL, /*已付款金额*/
BFCLJZD_U8		float		DEFAULT 0  NULL, /*未到票金额*/
BFCLJZD_U9		float		DEFAULT 0  NULL, /*未付款金额*/
BFCLJZD_U10		float		DEFAULT 0  NULL, /* 运费金额      合同水*/
BFCLJZD_U11		float		DEFAULT 0  NULL, /*               化验水*/
BFCLJZD_U12		float		DEFAULT 0  NULL, /*运费税率       对方水*/
BFCLJZD_U13		float		DEFAULT 0  NULL, /*调整数量      应结算单价*/
BFCLJZD_U14		float		DEFAULT 0  NULL, /*               运费单价*/
BFCLJZD_U15		float		DEFAULT 0  NULL, /*               运费金额*/
BFCLJZD_C6		Varchar(100)	NULL, /*入库单号*/
BFCLJZD_C7		Varchar(100)	NULL, /*仓库*/
BFCLJZD_C8		Varchar(100)	NULL, /*pk_corp*/
BFCLJZD_C9		Varchar(100)	NULL, /**/ 
BFCLJZD_C10		Varchar(100)	NULL, /*通知单号*/
BFCLJZD_C11		Varchar(100)	NULL,
BFCLJZD_C12		Varchar(100)	NULL,
BFCLJZD_C13		Varchar(100)	NULL,
BFCLJZD_C14		Varchar(100)	NULL,
BFCLJZD_C15		Varchar(100)	NULL
)
go
CREATE unique INDEX BFCLJZD_1 on BFCLJZD(BFCLJZD_JSBH)
go


/*采购订单/产品订单/运费合同主表*/


CREATE TABLE BFZLHT
(	BFZLHT_HTBH 	Char(20)	NOT Null,	/*合同编号 */
       	BFZLHT_ZDRQ 	Char(14)	not Null,        /*制单日期 */
	BFZLHT_SHBH 	Char(20)	    Null,	        /*客户 */
        BFZLHT_WLBH 	Char(30)	NOT Null,	        /*货物 */
	BFZLHT_SL 	float		default 0,  /*合同数量 */
        BFZLHT_DJ   	float		Default 0,  /*合同单价 */  
        BFZLHT_JE 	float		Default 0,  /*合同金额 */
        BFZLHT_ZZSL 	float		default 0,  /*增值税率 */
        BFZLHT_YFDJ   	float		Default 0,  /*运费单价 */  
        BFZLHT_SHL 	float		Default 0,  /*合理损耗率 */
        BFZLHT_SHBZ	Char(1)	        Null,	        /*审核标志 0,未审;1,一审;2,二审;3,三审;4,四审;5,五审 */
        BFZLHT_JSYJ	Char(1)	        Null,	        /*结算依据 0 以实收数结算，1 原发数结算，2-扣路耗结算 3-混合数结算 */
        BFZLHT_HTLX	Char(1)	        Null,	        /*合同类型 0,汽车运输合同;1,预付款合同;2,临时合同;3,补吨合同;4,普票合同;8,火车运输合同     1-收货运费合同  2-发货运费合同*/
        BFZLHT_YFBZ	Char(1)	        Null,	        /*合同类别 0 采购合同 1 运费合同,2-销售合同 */
        BFZLHT_KZBZ	Char(1)	        Null,	        /*运费结算 0 货款运费两票结算  1 货款运费一票结算 */
        BFZLHT_KZL     float		Default 0,     /*合同控制率 */
        BFZLHT_RKSL     float		Default 0,     /*已收数量 */
        BFZLHT_BMBH 	Char(20)	Null,	        /*部门 */
        BFZLHT_RYBH	Char(8)	        Null,	        /*签订人 */
        BFZLHT_ZDXM	Char(8)	        Null,	        /*制单人 */
	BFZLHT_NOTE1	 Char(254)  	Null,	      /*备注一*/ 
	BFZLHT_NOTE2     Char(254)  	Null,	      /*备注二*/ 
        BFZLHT_C1	 Char(60)  	Null,	      /*产地   发货地点*/
	BFZLHT_C2	 Char(60)  	Null,	      /*折价处理  0 不折价  1-折价  折价处理不再进行扣重扣价  到站*/  
        BFZLHT_C3	 Char(60)  	Null,	      /*审核人姓名*/ 
	BFZLHT_C4	 Char(60)  	Null,	      /*编号前缀*/  
        BFZLHT_C5	 Char(60)  	Null,	      /*单位名称*/ 
	BFZLHT_C6	 Char(60)  	Null,	      /*单据内码*/  
	BFZLHT_U1	 float          Default 0 NULL,	   /*运费费率*/  
	BFZLHT_U2	 float          Default 0 NULL,	      /*折扣率*/
        BFZLHT_U3	 float          Default 0 NULL,	   /*挂牌单价*/  
	BFZLHT_U4	 float          Default 0 NULL,	      /*合同执行率*/	
 	BFZLHT_C7	 Char(60)  	Null, /*承运单位编号  启用标志  0-启用 1－弃用*/
	BFZLHT_C8	 Char(60)  	Null, /*承运单位*/
 	BFZLHT_C9	 Char(60)  	Null, /*规格型号*/
 	BFZLHT_C10	 Char(60)  	Null,  /*终止日期*/	
        BFZLHT_U5	 float          Default 0 NULL,	  
	BFZLHT_U6	 float          Default 0 NULL,	      
        BFZLHT_U7	 float          Default 0 NULL,	   
	BFZLHT_U8	 float          Default 0 NULL,	    
        BFZLHT_U9	 float          Default 0 NULL,	   
	BFZLHT_U10	 float          Default 0 NULL, 	
        BFZLHT_C11	 Char(60)  	Null,	      
	BFZLHT_C12	 Char(60)  	Null,	       
        BFZLHT_C13	 Char(60)  	Null,	      
	BFZLHT_C14	 Char(60)  	Null,	      
        BFZLHT_C15	 Char(60)  	Null	     	      
)     
go    
CREATE Unique index BFZLHT on BFZLHT(BFZLHT_HTBH)
go

/*采购订单/产品订单/运费合同子表*/

CREATE TABLE BFZLHTMX
(	BFZLHTMX_HTBH 	Char(20)		NOT Null,	/*采购订单 */
        BFZLHTMX_LSBH 	Char(4)		NOT Null,	/*流水编号 */
       	BFZLHTMX_ZJXM 	Char(100)		not Null,	        /*质量指标 */
	BFZLHTMX_XMMC 	Char(200)	Null,	        /*指标名称 */
	BFZLHTMX_XX 	float		default 0,  /*标准下限 */
        BFZLHTMX_SX   	float		Default 0,  /*标准上限 */ 
        BFZLHTMX_KZ 	float		Default 0,  /*扣量*/ 
        BFZLHTMX_KL 	float		Default 0,  /*扣率*/  
	BFZLHTMX_GSBZ	 Char(1)  	NOT Null,  /* 扣罚种类 0-超下限扣重 1－低上限扣重 2-超下限扣价 3-低上限扣价 4-超下限比率扣重  5- 低上限比率扣重  6- 等级定价,7- 分组定义 8-超下限乘积扣价 9-低上限乘积扣价 a-等级扣价 b-等级扣重 */   
	BFZLHTMX_C1	 Char(60)  	Null,	      /*分组标志0-9*/ 
	BFZLHTMX_C2	 Char(60)  	Null,	      /*逻辑符no 空  并且-and) 或者-or)*/   
	BFZLHTMX_U1	 float          Default 0 NULL,	   /*数据精度*/  
	BFZLHTMX_U2	 float          Default 0 NULL,	      /*取数方式 0-四舍五入 1-截断法 2-进一法*/	   
 	BFZLHTMX_C3	 Char(60)  	Null,	      /*结算化验数据来源 0-单车取数  1-日权重超标平均取数 2-月权重超标平均取数 3-日权重平均 4-月权重平均 5-日超标平均 6-月超标平均 7-日平均 8-月平均 9累计平均 a-累计加权平均 b-累计超标平均 c-累计超标加权平均*/ 
 	BFZLHTMX_C4	 Char(60)  	Null, /*无化验数据处理方式*/	      
 	BFZLHTMX_C5	 Char(60)  	Null, /*左括号*/
 	BFZLHTMX_C6	 Char(60)  	Null, /*右括号*/	      
 	BFZLHTMX_C7	 Char(60)  	Null, 
 	BFZLHTMX_C8	 Char(60)  	Null, 
 	BFZLHTMX_C9	 Char(60)  	Null, 
 	BFZLHTMX_C10	 Char(60)  	Null, 
 	BFZLHTMX_U3 float          Default 0 NULL,
 	BFZLHTMX_U4 float          Default 0 NULL,   
 	BFZLHTMX_U5 float          Default 0 NULL,
 	BFZLHTMX_U6 float          Default 0 NULL,
 	BFZLHTMX_U7 float          Default 0 NULL,
 	BFZLHTMX_U8 float          Default 0 NULL,
 	BFZLHTMX_U9 float          Default 0 NULL,
 	BFZLHTMX_U10 float          Default 0 NULL
)     
go    
CREATE Unique index BFZLHTMX on BFZLHTMX(BFZLHTMX_HTBH,BFZLHTMX_LSBH)
go

/*采购发票、付款单主表*/

CREATE TABLE BFFKD
(  BFFKD_DJNM  Char(10)   NOT Null,        /*单据内码*/
   BFFKD_DJBH  Char(20)   NOT Null,        /*单据编号*/
   BFFKD_RQ    Char(8)    Null,            /*日期*/
   BFFKD_SHBH  Char(20)   Null,        /*供应商编号*/
   BFFKD_SHDW  CHAR(60)   Null,        /*供应商名称*/
   BFFKD_FHBH  Char(20)   Null,        /*承运编号*/
   BFFKD_FHDW  CHAR(60)   Null,        /*承运单位*/
   BFFKD_WLBH  Char(20)   Null,            /*物料编号*/ 
   BFFKD_GGXH  Char(60)   Null,            /*规格型号*/ 
   BFFKD_JSSL  float      Default 0,       /*结算重量*/
   BFFKD_HSDJ    float    Default 0,       /*含税单价*/  
   BFFKD_HSJE    float    Default 0,       /*含税金额*/
   BFFKD_ZZSL  float      Default 0,       /*增值税率  */
   BFFKD_DJ    float      Default 0,       /*单价      扣息*/  
   BFFKD_JE    float      Default 0,       /*金额      实际金额*/
   BFFKD_BMBH Char(20)    Null,        /*公司*/
   BFFKD_YWY  CHAR(20)     Null,        /*业务员*/
   BFFKD_ZDXM   Char(8)   Null,     /*操作员*/
   BFFKD_NOTE  Char(100)  Null,      /*备注*/
   BFFKD_DJLX  Char(1)    DEFAULT '1' ,    /*单据类型  1-采购发票 2-运费发票 3-采购付款单 4-运费付款,5-销售发票,6-销售运费发票, 7-销售收款单 8-销售运费付款 9-采购结算调整单 a-采购运费结算单调整 b-销售结算单调整 c-销售运费结算单调整 d-采购发票期初 e-采购运费发票期初 f-销售发票期初 g-销售运费发票期初 h－其他收款单 i－其他付款单 j-结存资金初始,k-结存资金调整,l-收款单余额结转*/
   BFFKD_QCBZ     Char(1) DEFAULT '1' ,     /*期初标志 0-未核销，1-已核销，2-期初数*/
   BFFKD_FKFS     Char(1) DEFAULT '0' ,     /*付款方式 0-汇票，1-承兑 2-现金 3-支票*/
   BFFKD_C1     Char(60)        Null,      /*票据类型*/
   BFFKD_C2     Char(60)        NulL,      /*票据号码*/  
   BFFKD_C3     Char(60)        Null,      /*审核标志*/  
   BFFKD_U1     float           null,      /*核销数量*/
   BFFKD_U2     float           null,      /*核销金额*/
   BFFKD_U3     float           null,      /*未核销金额*/
   BFFKD_U4     float           null,      
   BFFKD_U5     float           null,      
   BFFKD_U6     float           null,     
   BFFKD_U7     float           null,     
   BFFKD_U8     float           null,     
   BFFKD_U9     float           null,     
   BFFKD_U10     float          null,    
   BFFKD_C4     Char(60)        Null,  /*发票号*/    
   BFFKD_C5     Char(60)        NulL,  /*发票id*/     
   BFFKD_C6     Char(60)        Null,      
   BFFKD_C7     Char(60)        Null,     
   BFFKD_C8     Char(60)        NulL,       
   BFFKD_C9     Char(60)        Null,    
   BFFKD_C10    Char(60)        Null
  )
go
CREATE Unique NonClustered index ID_BFFKD ON BFFKD(BFFKD_DJNM)
CREATE Unique NonClustered index ID_BFFKD_1 ON BFFKD(BFFKD_DJBH,BFFKD_DJLX)
CREATE NonClustered index ID_BFFKD_2 ON BFFKD(BFFKD_DJLX, BFFKD_QCBZ,BFFKD_RQ,BFFKD_SHBH,BFFKD_WLBH)
go

/*采购发票、付款单副表*/

CREATE TABLE BFFKDMX
(  BFFKDMX_DJNM  Char(10)   NOT Null,      /*单据内码*/
   BFFKDMX_JSDH  Char(20)   NOT Null,      /*单据编号*/
   BFFKDMX_JSSL  float      Default 0,       /*结算重量*/
   BFFKDMX_JSJE    float    Default 0,       /*结算金额*/
   BFFKDMX_HSSL  float      Default 0,       /*核销数量*/
   BFFKDMX_HSJE    float      Default 0,     /*核销金额*/
   BFFKDMX_C1     Char(60)        Null,      /*备用1*/
   BFFKDMX_C2     Char(60)        NulL,      /*备用2*/  
   BFFKDMX_C3     Char(60)        Null,      /*备用3*/  
   BFFKDMX_U1     float           null,      /*备用数量1*/
   BFFKDMX_U2     float           null,      /*备用数量2*/
   BFFKDMX_U3     float           null     /*备用数量3*/
  )
go
CREATE Unique NonClustered index ID_BFFKDMX ON BFFKDMX(BFFKDMX_DJNM,BFFKDMX_JSDH)
go



/*用于自定义报表打印*/


CREATE TABLE report_columnname (
	tablename char(30) null,
        tablebz CHAR(20) NULL,
        id integer ,
        ssf char(1) Default 0,
	columnname_en char(20) null,
	columnname_cn char(30) null, 
	jsbz char(30) null,
	c1 char(30) null,
	c2 char(30) null,
        c3 char(30) null,
	c4 char(30) null,
	c5 char(30) null
	)
go 	
CREATE Unique NonClustered index report_columnname on report_columnname(tablename,columnname_en,tablebz)
go

                                                          
CREATE TABLE report_master (                                
	report_id float,                                    
	report_name char(50) null,                               
	app_id float,                                       
	app_name char(50) null,                                  
	subapp_id float,                                    
	subapp_name char(250) null,                               
	model_name char(250) null,                                
	locksql float,
	normal_syntax text  null,
	design_syntax text  null	
	)                                      
go                                                          
CREATE Unique NonClustered index report_master on report_master(report_id)
go


                                                            
                                                         
CREATE TABLE report_tablename (                             
	appname_en char(30) null,                                
	appname_cn char(30) null ,                                
	tablename_en char(30) null,                              
	tablename_cn char(30) null )                              
go                                                          
CREATE Unique NonClustered index report_tablename on report_tablename(appname_en,tablename_en)
INSERT INTO report_tablename VALUES ( '','','BFSFCL','收货磅码单')                 
INSERT INTO report_tablename VALUES ( '','','BFFHCL','发货磅码单') 
INSERT INTO report_tablename VALUES ( '','','BFHYD','化验单')  
INSERT INTO report_tablename VALUES ( '','','inventory','存货档案')                 
INSERT INTO report_tablename VALUES ( '','','DistrictClass','地区分类') 

go

                                                         
CREATE TABLE report_tablesrelation (                        
	id float,                                           
	tablename1 char(30) null,                                
	tablename2 char(30) null,                                
	columnname1 char(30) null,
	columnname2 char(30) null,
	operators char(30) null,
	joinoperators char(30) null,
	leftparenthises char(30) null,
	rightparenthises char(30) null )
go 
CREATE Unique NonClustered index report_tablesrelation on report_tablesrelation(id)
go
/*格式设置表*/
                                                          
CREATE TABLE BFDYSET                       
(	BFDYSET_DYLX 	Char(12) NOT Null,  /*打印类型  FH 发货单  SH 收货单  hydlr 化验单录入  hyddy 化验单打印  */
	BFDYSET_WLBH 	Char(30) not Null,/*产品编号*/
        BFDYSET_GSMC  Char(30)                Null,  /*打印格式名称*/        
        BFDYSET_GSBH   float      ,   /*打印格式编号*/
        BFDYSET_TOP    Char(10)                Null,/*上边距*/  
        BFDYSET_BOTTOM    Char(10)                Null, /*下边距*/
	BFDYSET_LEFT    Char(10)                Null, /*左边距*/
	BFDYSET_RIGHT  Char(10)                Null, /*右边距*/
	BFDYSET_HEIGHT    Char(10)                Null, /*纸张宽度*/
	BFDYSET_WIDTH    Char(10)                Null, /*纸张高度*/
	BFDYSET_SIZE  Char(10)                Null, /*纸张类型*/
	BFDYSET_C1  Char(20)                Null, /*打印行数*/
	BFDYSET_C2  Char(20)                Null, /*打印方向*/
	BFDYSET_C3  Char(20)                Null, /*备注*/
	BFDYSET_C4  Char(20)                Null, /*备注*/
	BFDYSET_C5  Char(20)                Null /*备注*/
	)
go
CREATE Unique NonClustered index ID_BFDYSET on BFDYSET(BFDYSET_DYLX,BFDYSET_WLBH,BFDYSET_GSBH)
go 

/*用户操作日志表*/
                                                          
CREATE TABLE bflog                       
(	bflog_id  float NOT Null,  /*id*/
	bflog_user  varChar(30) 	NOT Null,/*用户*/
        bflog_menu    varChar(20)                Null,/*主菜单*/  
        bflog_submenu    varChar(100)                Null, /*子菜单*/
	bflog_in  datetime               Null,  /*登陆时间*/        
        bflog_out   datetime  null      ,   /*退出时间*/
        bflog_Station    varChar(50)                Null, /*登陆地点*/
	bflog_Success  Char(1)                Null /*0－失败 1－正常*/
	)
go
CREATE Unique NonClustered index ID_bflog on bflog(bflog_id)
go 

/*采样单流水号设置表*/

create table LSCYLS
(	F_RQ	Char(8)		NOT Null,
	F_BH	Char(4)		NOT Null,
        F_QZ	Char(2)		NOT Null,
	F_BZ	VarChar(1)	 Null
)
go
create Unique index LSCYLS ON LSCYLS(F_RQ,F_BH,F_QZ)
go

/*采样单号前缀设置表*/

create table LSCYQZ
(	F_RQ	Char(8)		NOT Null,
	F_SHBH	Char(30)		NOT Null,
        F_WLBH	Char(30)		NOT Null,
        F_QZ	Char(2)		NOT Null,
        F_BH	Char(3)		NOT Null,
	F_BZ	VarChar(1)	 Null,
        F_FHDD	VarChar(60)	 Null
)
go
create Unique index LSCYQZ ON LSCYQZ(F_RQ,F_SHBH,F_WLBH,F_QZ,F_BH)
go

/*参数设置表*/

create table LSCONF
(	F_VKEY	Char(18)		NOT Null,
	F_VAL	Char(60)		  not Null,
	F_NOTE	VarChar(255)	 Null
)
go
create Unique index LSCONF ON LSCONF(F_VKEY)
go

/*功能字典表*/

create table LSGNZD
(	F_GNBH	char(8)			NOT Null,
	F_GNMC	char(20)			NOT Null,
	F_GNSM	varchar(100)		 Null,
        F_GNLS	varchar(4)		 Null,
        F_ID	varchar(60)		 Null,
        F_LB	varchar(4)		 Null,
        F_BZ	varchar(1)		 Null
)
go
create Unique Index LSGNZD ON LSGNZD(F_GNBH)
go

/*用户权限表*/

create table LSUSGN
(	F_ZGBH	char(10)	NOT Null,
	F_GNBH	char(20)		NOT Null,
        F_GNBZ	char(1)		 Null
)
go
Create Unique index LSUSGN on LSUSGN(F_ZGBH,F_GNBH,F_GNBZ)
go

/*表关联表*/

create  table  LSCOMN
  (F_XTBH  varchar(4)  not null,
   F_TABN  varchar(20) not null,
   F_COLN  varchar(100) not null,
   F_MATC  varchar(200) not null,
   F_LIS1  varchar(254) null,
   F_LIS2  varchar(254) null,
   F_EXC1  varchar(254) null,
   F_EXC2  varchar(254) null)
go
create  unique  index  LSCOMN on  LSCOMN(F_XTBH,F_TABN)
go

/*提示信息表*/

create table SYSMESSAGE
(S_MESSAGEID varchar(80)  not null,
 S_MESSAGE varchar(254) not null,
 S_MESSAGEJB char(1) not null,
 S_MESSAGEFS char(11) null,
 S_JRR char(8) null)
go
create unique  index SYSMESS_ID on SYSMESSAGE (S_MESSAGEID)
go

/*查询设置表*/

create table LSZBGS
(F_ID varchar(30) not null,
F_GSBH char(4) not null,
F_GSMC varchar(50) null,
F_NAME varchar(255) not null,
F_HEADH int default 300 not null,
F_BTGD int default 200 not null,
F_DETH int default 90 not null,
F_SUMH int default 0 not null,
F_FOOTH int default 0 not null,
F_THICK int default 10 not null,
F_THIN int default 6 not null,
F_YDGD int default 0 not null,
F_BMGD int default 180 not null,
F_SPACE int default 0 not null,
F_SORT varchar(80) null,
F_SELE varchar(255) null,
F_WHERE varchar(255) null,
F_TIJS char(1) null,
F_HJBZ int default 0 not null)
go
create unique  index LSZBGS on LSZBGS(F_ID,F_GSBH)
go 

/*查询项目表*/

create table LSTIGS
(F_ID varchar(30) not null,
F_GSBH char(4) not null,
F_TIBH char(12) not null,
F_JS char(1) null,
F_NAME varchar(40) null,
F_TEXT varchar(30) null,
F_TILX char(1) null,
F_XSGS varchar(254) null,
F_WIDTH char(4) null,
F_BEPL char(3) null,
F_ENPL char(3) null,
F_SUM int default 0 not null,
F_SORT int default 0 not null,
F_YHF char(1) not null,
F_RED int default 0 not null,
F_SIGN int default 0 not null,
F_QFBZ char(2) null,
F_DDDW varchar(30) null,
F_DISP varchar(254) null,
F_DATA varchar(254) null,
F_SXBZ char(2) null)
go 
create unique  index LSTIGS on LSTIGS(F_ID, F_GSBH,F_TIBH)
go

/*查询表头表尾设置表*/

create table LSOTGS
(F_ID varchar(30) not null,
 F_GSBH char(4) not null,
 F_OTBH char(2) not null,
 F_OTBZ char(1) not null,
 F_JS char(1) not null,
 F_TEXT varchar(255) not null,
 F_PLACE char(2) not null)
go
create unique  index LSOTGS on LSOTGS(F_ID,F_GSBH,F_OTBH)
go

/*页面设置表*/

create table LSYMSZ
(F_SZBH varchar(30) not null,
 F_GSBH varchar(4) not null,
 F_GSMC varchar(30)  not null,
 F_GDLS int not null,
 F_BDLS int not null,
 F_TTZT varchar(20) not null,
 F_TTZH char(2) not null,
 F_TTXT int default 0 not null,
 F_TTCT int default 0 not null,
 F_TTXH int default  0 not null,
 F_TOZT varchar(20) not null,
 F_TOZH char(2) not null,
 F_TOXT int default 0 not null,
 F_TOCT int default 0 not null,
 F_TOXH int default  0 not null,
 F_BTZT varchar(20) not null,
 F_BTZH char(2) not null,
 F_BTXT int default 0 not null,
 F_BTCT int default 0 not null,
 F_BTXH int default  0 not null,
 F_BIZT varchar(20)  not null,
 F_BIZH char(2) not null,
 F_BIXT int default 0 not null,
 F_BICT int default 0 not null,
 F_BIXH int default  0 not null,
 F_BWZT varchar(20) not null,
 F_BWZH char(2) not null,
 F_BWCT int default 0 not null,
 F_BWXT int default 0 not null,
 F_BWXH int default  0 not null,
 F_HXXS int default 100 not null,
 F_ZXXS int default 100 not null,
 F_TOP int not null,
 F_BOTT int not null,
 F_LEFT int not null,
 F_RIGH int not null,
 F_SCSX char(1) default  '1' not null,
 F_PAPER varchar(2) default  '0' not null,
 F_DYZL char(1) default  '0' not null,
 F_DYFX char(1) default  '1' not null,
 F_SYBZ char(1) default  '0' not null,
 F_MYHS int default 30,
 F_TDF  char(1) default  '0' not null,
 F_ZBGS varchar(4) null,
 F_BKHF  char(1) default  '0' null,
 F_MYHJ  char(1) default  '0' null,
 F_ZYFS  char(1) default  '0' null
)
go
create unique  index LSYMSZ on LSYMSZ(F_SZBH,F_GSBH)
go

/*打印格式设置表*/

create table LSDYZD
(F_SZBH char(30) not null,
 F_GSBH char(2) not null,
 F_GSMC varchar(30) not null,
 F_TTZT varchar(20) not null,
 F_TTZH char(2) not null,
 F_TTXT int default 0 not null,
 F_TTCT int default 0 not null,
 F_TTXH int default 0 not null,
 F_TOZT varchar(20) not null,
 F_TOZH char(2) not null,
 F_TOXT int default 0 not null,
 F_TOCT int default 0 not null,
 F_TOXH int default 0 not null,
 F_BTZT varchar(20) not null,
 F_BTZH char(2) not null,
 F_BTXT int default 0 not null,
 F_BTCT int default 0 not null,
 F_BTXH int default 0 not null,
 F_BIZT varchar(20) not null,
 F_BIZH char(2) not null,
 F_BIXT int default 0 not null,
 F_BICT int default 0 not null,
 F_BIXH int default 0 not null,
 F_BWZT varchar(20) not null,
 F_BWZH char(2) not null,
 F_BWCT int default 0 not null,
 F_DYZL char(1) not null,
 F_DYZX char(2) not null,
 F_DYFX char(1) not null,
 F_DYFS char(2) not null,        
 F_BWXT int default 0 not null,
 F_BWXH int default  0 not null,
 F_HXXS int default 100 not null,
 F_ZXXS int default 100 not null,
 F_TOP  int not null,
 F_BOTT int not null,
 F_LEFT int not null,
 F_RIGH int not null,
 F_SYBZ int not null)
go
create unique  index LSDYZD on LSDYZD(F_SZBH,F_GSBH)
go

/**/

create table LSBMJG
(F_STBH CHAR(12) NOT NULL,
 F_BMJB CHAR(1) NOT NULL,
 F_BJBMSM VARCHAR(20) NULL,
 F_BJWS int NOT NULL,
 F_BJYWS int NOT NULL,
 F_BJQSW int NOT NULL,
 F_DYLM VARCHAR(20) NULL)
go
create unique  index NDX_LSBMJG on LSBMJG(F_STBH,F_BMJB)
go

/*表结构字典*/

create table LSBJG
(F_BBH CHAR(20) NOT NULL,
 F_LBH CHAR(3) NOT NULL,
 F_LM  CHAR(12) NOT NULL,
 F_LSM VARCHAR(30) NULL,
 F_LLB CHAR(1) NOT NULL,
 F_SJLX VARCHAR(20) NULL,
 F_LKD int NULL,      
 F_SFZJ int DEFAULT 0 not null,
 F_SFWJ VARCHAR(20) NULL,
 F_KFWK int  DEFAULT 1 not null,  
 F_QSZ VARCHAR(40) NULL)
go
create unique  index NDX_LSBJG on LSBJG(F_BBH,F_LBH)
go

/*统计表列名字典*/

create table LSSTJG
(F_STBH VARCHAR(12) NOT NULL,
 F_BM VARCHAR(30) NULL,
 F_XMBH CHAR(3) NOT NULL,
 F_LM CHAR(30) NOT NULL, 
 F_XMSM VARCHAR(30) NULL, 
 F_SFTJBZ int DEFAULT 0 not null,
 F_BMSM VARCHAR(80) NULL,
 F_SJLX VARCHAR(10) NOT NULL,
 F_SYBZ int DEFAULT 1 not null,
 F_SM VARCHAR(80) NULL,
 F_DDDW varchar(30) NULL,
 F_DISP varchar(254) NULL,
 F_DATA varchar(254) NULL,
 F_SXBZ char(2) NULL)
go
create unique  index NDX_LSSTJG on LSSTJG(F_STBH,F_XMBH)
go

/*统计表字典*/

create table LSTJBB
(F_ZTBH VARCHAR(6) NULL,
 F_TJST VARCHAR(12)  NOT NULL,
 F_BBBH CHAR(3) NOT NULL,
 F_BBMC VARCHAR(60) NULL,
 F_TJTJ text NULL,
 F_TJSM text NULL,
 F_DYR VARCHAR(10) NULL,
 F_TJSJ VARCHAR(20) NULL,
 F_TJPD INT NULL,
 F_QXSZ	 Char(254)  Null,
 F_QXSZ1 Char(254)  Null
)
go
create unique  index NDX_LSTJBB on LSTJBB(F_TJST,F_BBBH)
go

/*统计条件表*/

create table LSTJNR
(F_ZTBH VARCHAR(6) NULL,
 F_TJST VARCHAR(12) NOT NULL,
 F_BBBH VARCHAR(12) NOT NULL,
 F_XMBH CHAR(3) NOT NULL,
 F_XMMC CHAR(20) NULL,
 F_XMSM VARCHAR(255) NULL,
 F_XMDY VARCHAR(255) NULL,
 F_FLXH CHAR(3) NULL,
 F_PXXH CHAR(3) NULL,
 F_SXNX int DEFAULT 0 not null,
 F_BMSM VARCHAR(80) NULL,
 F_SJLX VARCHAR(20) NULL,
 F_SFXJ int NULL,
 F_SJJD VARCHAR(8) NULL,
 F_DDDW varchar(30) NULL,
 F_DISP varchar(254) NULL,
 F_DATA varchar(254) NULL,
 F_SXBZ char(2) NULL)
go
create unique  index NDX_LSTJNR on LSTJNR(F_TJST,F_BBBH,F_XMBH)
go

/*图表格式设置表*/

create table  LSGRAP
(F_SZBH  char(20) not null,
 F_FXBH  char(3) not null,
 F_FXSM  char(20) not null,
 F_TITL  varchar(50) not null,
 F_BLOK  text null,
 F_TYPE  char(2) not null,
 F_BZWZ  char(1) not null,
 F_SERI  char(1) not null,
 F_DEPT  char(3) not null,
 F_ELEV  char(3) not null,
 F_ROTA  char(3) not null,
 F_PERS  char(3) not null,
 F_PAPE  char(2) not null,
 F_SCFS  char(1) not null,
 F_QUAL  char(1) not null,
 F_COPY  char(2) not null,
 F_SYBZ  int default 0 not null ,
 F_LEFT  char(4) not null,
 F_RIGH  char(4) not null,
 F_TOP   char(4) not null,
 F_BOTT  char(4) not null,
 F_HZ    int default 0 not null,
 F_AXIS  int default 0 not null)
go
create  unique   index  LSGRAP on  LSGRAP(F_SZBH,F_FXBH)
go

/*查询帮助字典*/

create table LSHELP
(F_HELP varchar(60) not null,
 F_HEBH  char(4) not null, 
 F_TITL  varchar(60) not null,
 F_JSZD  char(40) null, 
 F_NMZD  char(40) null,
 F_BHZD  char(60) null,
 F_MCZD  varchar(40) null,
 F_ZJM   char(40) null,
 F_FZXX  varchar(40) null,
 F_MXZD  char(40) null,
 F_BMJG  varchar(40) null,
 F_SUBS  int default 0 not null,
 F_WHER  varchar(250)  null,
 F_XZMX  int default 1 not null,
 F_ALLS  int default 0 not null)
go
create unique  index LSHELP on LSHELP (F_HELP,F_HEBH)
go

/*查询条件表*/

create table  LSSTRU
(F_TABN varchar(40) not null,
 F_COLN varchar(40) not null,
 F_COLA varchar(20) not null,
 F_DISP char(3) null,
 F_TYPE varchar(1) not null,
 F_CXBZ char( 1) null,
 F_HELP varchar(100) null,
 F_CODE varchar(250) null)
go
create unique  index LSSTRU on LSSTRU(F_TABN,F_COLN,F_DISP)
go

/*视频监控设置表*/

create table  LSBMP
(F_SHDD varchar(2) not null,
 F_LOGIN varchar(50)  null,
 F_PASS varchar(50)  null,
 F_ADDR char(200) null,
 F_DIR varchar(200)  null,
 F_SFSY varchar(1)  null,
 F_C1 varchar(60)  null,
 F_C2 varchar(60)  null
)
go
create unique  index LSBMP on LSBMP(F_SHDD)
go


/*图片表*/

CREATE TABLE BFBMP
(  SFBZ  Char(2)    Null,        
   RCBZ  Char(2)    Null,        
   DJBH    Char(12)    Null,       
   LSBH  Char(1)   Null,     
   RQ    CHAR(8)   Null,    
   BMP  IMAGE   Null,
   C1    CHAR(8)   Null,    
   C2    CHAR(20)   Null,    
   C3    CHAR(60)   Null,    
  )
go
CREATE Unique NonClustered index ID_BFBMP ON BFBMP(SFBZ,RCBZ,DJBH,LSBH)
go
/*信息发布表*/

create table  BFNOTE
(F_id int not null,
 F_rq datetime  NOT null,
 F_fbr varchar(8)  not null,
 F_zt varchar(250) null,
 F_nr varchar(2000)  null,
 F_note text  null,
 F_qx varchar(250)  null,
 F_lx varchar(8)  not null,
 F_C1 varchar(250)  null,
 F_C2 varchar(250)  null,
 F_C3 varchar(250)  null
)
go
create unique  index bfnote on BFNOTE(F_id)
go

/*合同文本表*/

CREATE TABLE doc_text 
(doc_no varchar(30) NOT NULL, 
doc_elec_no varchar(10) NOT NULL, 
doc_name varchar(160) NULL, 
doc_text text NULL  
) 
go
create unique  index DOC_TEXT_id on DOC_TEXT(doc_no, doc_elec_no)
go

DELETE LSCONF WHERE F_VKEY LIKE 'BF%'
go
INSERT INTO LSCONF(F_VKEY,F_VAL,F_NOTE) VALUES('BF_CYLS','1234','20030101')
INSERT INTO LSCONF(F_VKEY,F_VAL,F_NOTE) VALUES('BF_JZRQ','01','')
INSERT INTO LSCONF(F_VKEY,F_VAL,F_NOTE) VALUES('BF_BZW','0','')
INSERT INTO LSCONF(F_VKEY,F_VAL,F_NOTE) VALUES('BF_THD','1','')
INSERT INTO LSCONF(F_VKEY,F_VAL,F_NOTE) VALUES('BF_HYD','0','')
INSERT INTO LSCONF(F_VKEY,F_VAL,F_NOTE) VALUES('BF_KJND','2003','')
INSERT INTO LSCONF(F_VKEY,F_VAL,F_NOTE) VALUES('BF_KJQJ','09','')
INSERT INTO LSCONF(F_VKEY,F_VAL,F_NOTE) VALUES('BF_KJRQ','20030901','')
INSERT INTO LSCONF(F_VKEY,F_VAL,F_NOTE) VALUES('BF_HAVEJZ','','')
INSERT INTO LSCONF(F_VKEY,F_VAL,F_NOTE) VALUES('BF_YJQJ','','')
INSERT INTO LSCONF(F_VKEY,F_VAL,F_NOTE) VALUES('BF_ZJMK','ZJ','')
INSERT INTO LSCONF(F_VKEY,F_VAL,F_NOTE) VALUES('BF_JSMK','JS','')
INSERT INTO LSCONF(F_VKEY,F_VAL,F_NOTE) VALUES('BF_PJMK','PJ','')
INSERT INTO LSCONF(F_VKEY,F_VAL,F_NOTE) VALUES('BF_NDJZ','20070101','')
INSERT INTO LSCONF(F_VKEY,F_VAL,F_NOTE) VALUES('BF_CSNDQJ','','')
INSERT INTO LSCONF(F_VKEY,F_VAL,F_NOTE) VALUES('BF_SOFTMC','大宗物料管理信息系统','')
INSERT INTO LSCONF(F_VKEY,F_VAL,F_NOTE) VALUES('BF_DBVER','v8.71_080101','')	
go
INSERT INTO BFDYSET(BFDYSET_DYLX,BFDYSET_WLBH,BFDYSET_GSMC,BFDYSET_GSBH,BFDYSET_TOP,BFDYSET_BOTTOM,BFDYSET_LEFT
	          ,BFDYSET_RIGHT,BFDYSET_HEIGHT,BFDYSET_WIDTH,BFDYSET_SIZE)	
			VALUES('fhrbb','','发货日报表',0,'110','110','96','96','27.90','24.10','1')
go



CREATE TABLE BFFKD_MX
(F_BH varchar(30) NOT NULL, 
 F_SM1 text NULL,
 F_SM2 text NULL, 
 F_SM3 text NULL, 
 F_SM4 text NULL   
) 
go
create unique  index BFFKD_MX on BFFKD_MX(F_BH)
go

/*混样台帐*/
IF EXISTS (SELECT * FROM SYSOBJECTS WHERE RTRIM(TYPE)='U' AND RTRIM(NAME)='BFHYTZ')
   DROP TABLE BFHYTZ
go
CREATE TABLE BFHYTZ
(F_ID INT NOT NULL,  /*内码*/
 F_LSBH INT NOT NULL, /*序号*/
 F_RQ CHAR(8)   NULL, /*混样日期*/
 F_ZJDH VARCHAR(12) NULL, /*化验单号 二次编号*/
 F_TZBH VARCHAR(12) NULL, /*混样单号 一次编号*/
 F_CYBH VARCHAR(20)  NULL,/* 采样单号*/
 F_SL  INT   NULL,/*样品数*/
 F_XZ CHAR(1)  NULL,/**/
 F_HYBZ CHAR(1)  NULL,/**/
 F_C1 VARCHAR(30)  NULL,/*通知单号*/
 F_C2 VARCHAR(30)  NULL,/*现场样号*/
 F_C3 VARCHAR(30)  NULL,/*卡号*/
 F_HYZ	float Default 0,
 F_GS	INT Default 0
)
go
CREATE UNIQUE  INDEX BFHYTZ ON BFHYTZ(F_ID)
CREATE Clustered index BFHYTZ_ID ON BFHYTZ(F_RQ,F_LSBH)
go

CREATE TABLE dbo.Uf_BalToAuto(
	AutoId int  NOT NULL,
	BarId varchar(30) NOT NULL,
	Date1 datetime NULL,
	VenCode varchar(20) NULL,
	VenName varchar(60) NULL,
	InvCode varchar(20) NULL,
	InvName varchar(60) NULL,
	InvStd varchar(60) NULL,
	CZ bit NULL)
go
CREATE UNIQUE  INDEX Uf_BalToAuto ON Uf_BalToAuto(BarId)
go

CREATE TABLE BFYC
( F_YCBH  Char(12)   NOT Null,        /*要车编号*/
  F_YCSJ  DATETIME    Null,            /*要车时间*/
  F_SHBH  Char(20)       Null,        /*供应商编号*/
  F_SHDW  CHAR(60)       Null,        /*供应商名称*/
  F_WLBH  Char(200)   Null,            /*物料编号*/ 
  F_WLMC  Char(250)   Null,            /*物料编号*/ 
  F_WLBH1  Char(200)   Null,            /*物料编号*/ 
  F_WLMC1  Char(250)   Null,            /*物料编号*/ 
  F_FHDD  Char(30)   Null,            /*发货地点*/ 
  F_CS   INT   Default 1,      /*车数 */
  F_YCR  Char(10)         Null,      /*办卡人*/
  F_C1     Char(60)        Null,      /*备用1*/
  F_C2     Char(60)        NulL,      /*备用2*/  
  F_C3     Char(60)        Null,      /*备用3*/  
  F_C4     Char(60)        Null,      /*备用4*/  
  F_C5    Char(60)        Null,      /*派车单号*/ 
  F_U1     float           null,      /*备用数字1*/
  F_U2     float           null,      /*备用数字2*/
  F_U3     float           null       /*备用数字3*/
  )
go
CREATE Unique NonClustered index ID_BFYC ON BFYC(F_YCBH)
go
/*收发货人员字典*/

create table BFRYZD
(	F_BH	Char(6)		NOT Null,
	F_MC	Char(10)	NOT Null,
	F_PASS	Char(6)		 Null,
	F_SCJH	Char(4)	 Null,
	F_DDBH	VarChar(200)	 Null,
        F_XZ	INT  Default 0  null
)
go
CREATE UNIQUE  INDEX bfryzd ON bfryzd(f_bh)
go