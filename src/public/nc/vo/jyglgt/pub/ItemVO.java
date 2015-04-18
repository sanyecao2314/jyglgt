package nc.vo.jyglgt.pub;

import java.util.ArrayList;

import nc.vo.ml.NCLangRes4VoTransl;
import nc.vo.pub.NullFieldException;
import nc.vo.pub.ValidationException;
import nc.vo.pub.ValueObject;
import nc.vo.pub.lang.UFBoolean;
import nc.vo.pub.lang.UFDouble;

public class ItemVO extends ValueObject
{
  private static final long serialVersionUID = -949828773596418547L;
  public String m_pk_wa_item;
  public Integer m_iitemid;
  public String m_vname;
  public Integer m_iitemtype;
  public Integer m_idefaultflag;
  public Integer m_iproperty;
  public String m_pk_corp;
  public Integer m_isealflag;
  public Integer m_iflddecimal;
  public Integer m_ifldwidth;
  public UFBoolean m_isintmoney;
  private Integer m_ifromflag;
  private Integer m_itaxflag;
  private String m_vformula;
  private String m_vformulastr;
  private UFDouble m_nfixamount;
  private Integer iclearflag;
  private String destitemcol = null;

  private String destitempk = null;
  private Integer ipsnceilflag;
  private Integer ipsnfloorflag;
  private Integer isumceilflag;
  private Integer isumfloorflag;
  private UFDouble npsnceil = new UFDouble(0);

  private UFDouble npsnfloor = new UFDouble(0);

  private UFDouble nsumceil = new UFDouble(0);

  private UFDouble nsumfloor = new UFDouble(0);
  private String wageformPK;
  private String docname;
  private String categoryid;
  private UFBoolean mid;
  public Integer m_iprivil;
  private UFBoolean m_isinhi;
  private String vofetch;
  
  

public String getVofetch() {
	return vofetch;
}

public void setVofetch(String vofetch) {
	this.vofetch = vofetch;
}

public ItemVO()
  {
  }

  public ItemVO(String newPk_wa_item)
  {
    this.m_pk_wa_item = newPk_wa_item;
  }

  public Object clone()
  {
    Object o = null;
    try {
      o = super.clone();
    } catch (Exception e) {
    }
    ItemVO item = (ItemVO)o;

    return item;
  }

  public String getEntityName()
  {
    return "Item";
  }

  public String getPrimaryKey()
  {
    return this.m_pk_wa_item;
  }

  public void setPrimaryKey(String newPk_wa_item)
  {
    this.m_pk_wa_item = newPk_wa_item;
  }

  public String getPk_wa_item()
  {
    return this.m_pk_wa_item;
  }

  public Integer getIitemid()
  {
    return this.m_iitemid;
  }

  public String getVname()
  {
    return this.m_vname;
  }

  public Integer getIitemtype()
  {
    return this.m_iitemtype;
  }

  public Integer getIdefaultflag()
  {
    return this.m_idefaultflag;
  }

  public Integer getIproperty()
  {
    return this.m_iproperty;
  }

  public String getPk_corp()
  {
    return this.m_pk_corp;
  }

  public Integer getIsealflag()
  {
    return this.m_isealflag;
  }

  public Integer getIflddecimal()
  {
    return this.m_iflddecimal;
  }

  public Integer getIfldwidth()
  {
    return this.m_ifldwidth;
  }

  public void setPk_wa_item(String newPk_wa_item)
  {
    this.m_pk_wa_item = newPk_wa_item;
  }

  public void setIitemid(Integer newIitemid)
  {
    this.m_iitemid = newIitemid;
  }

  public void setVname(String newVname)
  {
    this.m_vname = newVname;
  }

  public void setIprivil(Integer privil)
  {
    this.m_iprivil = privil;
  }

  public void setIitemtype(Integer newIitemtype)
  {
    this.m_iitemtype = newIitemtype;
  }

  public void setIdefaultflag(Integer newIdefaultflag)
  {
    this.m_idefaultflag = newIdefaultflag;
  }

  public void setIproperty(Integer newIproperty)
  {
    this.m_iproperty = newIproperty;
  }

  public void setPk_corp(String newPk_corp)
  {
    this.m_pk_corp = newPk_corp;
  }

  public void setIsealflag(Integer newIsealflag)
  {
    this.m_isealflag = newIsealflag;
  }

  public void setIflddecimal(Integer newIflddecimal)
  {
    this.m_iflddecimal = newIflddecimal;
  }

  public void setIfldwidth(Integer newIfldwidth)
  {
    this.m_ifldwidth = newIfldwidth;
  }

  public void validate()
    throws ValidationException
  {
    ArrayList errFields = new ArrayList();

    if (this.m_pk_wa_item == null) {
      errFields.add(new String("m_pk_wa_item"));
    }
    if (this.m_iitemid == null) {
      errFields.add(new String("m_iitemid"));
    }
    if (this.m_vname == null) {
      errFields.add(new String("m_vname"));
    }
    if (this.m_iitemtype == null) {
      errFields.add(new String("m_iitemtype"));
    }
    if (this.m_iproperty == null) {
      errFields.add(new String("m_iproperty"));
    }

    StringBuffer message = new StringBuffer();
    message.append(NCLangRes4VoTransl.getNCLangRes().getStrByID("60130104", "UPP60130104-000097") + ": ");
    if (errFields.size() > 0) {
      String[] temp = (String[])errFields.toArray(new String[0]);
      message.append(temp[0]);
      for (int i = 1; i < temp.length; i++) {
        message.append(",");
        message.append(temp[i]);
      }

      throw new NullFieldException(message.toString());
    }
  }

  public String toString()
  {
    return this.m_vname;
  }

  public Integer getIprivil()
  {
    return this.m_iprivil;
  }

  public UFBoolean getIsinhi()
  {
    if (this.m_isinhi == null) {
      setIsinhi(new UFBoolean(false));
      return new UFBoolean(false);
    }
    return this.m_isinhi;
  }

  public void setIsinhi(UFBoolean newIsinhi)
  {
    this.m_isinhi = newIsinhi;
  }

  public UFBoolean getM_isintmoney()
  {
    if (this.m_isintmoney == null) {
      setM_isintmoney(new UFBoolean(false));
      return new UFBoolean(false);
    }
    return this.m_isintmoney;
  }

  public void setM_isintmoney(UFBoolean m_isintmoney)
  {
    this.m_isintmoney = m_isintmoney;
  }

  public Integer getM_ifromflag()
  {
    return this.m_ifromflag;
  }

  public void setM_ifromflag(Integer m_ifromflag)
  {
    this.m_ifromflag = m_ifromflag;
  }

  public Integer getM_itaxflag()
  {
    return this.m_itaxflag;
  }

  public void setM_itaxflag(Integer m_itaxflag)
  {
    this.m_itaxflag = m_itaxflag;
  }

  public UFDouble getM_nfixamount()
  {
    return this.m_nfixamount;
  }

  public void setM_nfixamount(UFDouble m_nfixamount)
  {
    this.m_nfixamount = m_nfixamount;
  }

  public String getM_vformula()
  {
    return this.m_vformula;
  }

  public void setM_vformula(String m_vformula)
  {
    this.m_vformula = m_vformula;
  }

  public String getM_vformulastr()
  {
    return this.m_vformulastr;
  }

  public void setM_vformulastr(String m_vformulastr)
  {
    this.m_vformulastr = m_vformulastr;
  }

  public String getDestitemcol()
  {
    return this.destitemcol;
  }

  public void setDestitemcol(String destitemcol)
  {
    this.destitemcol = destitemcol;
  }

  public String getDestitempk()
  {
    return this.destitempk;
  }

  public void setDestitempk(String destitempk)
  {
    this.destitempk = destitempk;
  }

  public Integer getIclearflag()
  {
    return this.iclearflag;
  }

  public void setIclearflag(Integer iclearflag)
  {
    this.iclearflag = iclearflag;
  }

  public Integer getIpsnceilflag()
  {
    return this.ipsnceilflag;
  }

  public void setIpsnceilflag(Integer ipsnceilflag)
  {
    this.ipsnceilflag = ipsnceilflag;
  }

  public Integer getIpsnfloorflag()
  {
    return this.ipsnfloorflag;
  }

  public void setIpsnfloorflag(Integer ipsnfloorflag)
  {
    this.ipsnfloorflag = ipsnfloorflag;
  }

  public Integer getIsumceilflag()
  {
    return this.isumceilflag;
  }

  public void setIsumceilflag(Integer isumceilflag)
  {
    this.isumceilflag = isumceilflag;
  }

  public Integer getIsumfloorflag()
  {
    return this.isumfloorflag;
  }

  public void setIsumfloorflag(Integer isumfloorflag)
  {
    this.isumfloorflag = isumfloorflag;
  }

  public UFDouble getNpsnceil()
  {
    return this.npsnceil;
  }

  public void setNpsnceil(UFDouble npsnceil)
  {
    this.npsnceil = npsnceil;
  }

  public UFDouble getNpsnfloor()
  {
    return this.npsnfloor;
  }

  public void setNpsnfloor(UFDouble npsnfloor)
  {
    this.npsnfloor = npsnfloor;
  }

  public UFDouble getNsumceil()
  {
    return this.nsumceil;
  }

  public void setNsumceil(UFDouble nsumceil)
  {
    this.nsumceil = nsumceil;
  }

  public UFDouble getNsumfloor()
  {
    return this.nsumfloor;
  }

  public void setNsumfloor(UFDouble nsumfloor)
  {
    this.nsumfloor = nsumfloor;
  }

  public String getWageformPK()
  {
    return this.wageformPK;
  }

  public void setWageformPK(String wageformPK)
  {
    this.wageformPK = wageformPK;
  }

  public String getCategoryid() {
	return categoryid;
  }

  public void setCategoryid(String categoryid) {
	this.categoryid = categoryid;
	}

  public UFBoolean getMid() {
    return this.mid;
  }

  public void setMid(UFBoolean mid) {
    this.mid = mid;
  }

  public String getDocname() {
    return this.docname;
  }

  public void setDocname(String docname) {
    this.docname = docname;
  }
}