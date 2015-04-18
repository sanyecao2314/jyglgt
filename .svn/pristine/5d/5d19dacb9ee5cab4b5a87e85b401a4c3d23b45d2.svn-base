package nc.vo.jyglgt.pub;

import java.util.ArrayList;
import nc.vo.bd.BDMsg;
import nc.vo.pub.NullFieldException;
import nc.vo.pub.ValidationException;
import nc.vo.pub.ValueObject;

public class FreefldVO extends ValueObject
{
  private static final long serialVersionUID = 6780612484455160459L;
  public Integer m_pk_wa_freefld;
  public Integer m_ifieldtype;
  public String m_vfldtypename;
  public Integer m_iminno;
  public Integer m_iproducttype;
  public Integer m_lmaxno;

  public FreefldVO()
  {
  }

  public FreefldVO(Integer newPk_wa_freefld)
  {
    this.m_pk_wa_freefld = newPk_wa_freefld;
  }

  public Object clone()
  {
    Object o = null;
    try {
      o = super.clone(); } catch (Exception e) {
    }
    FreefldVO freefld = (FreefldVO)o;

    return freefld;
  }

  public String getEntityName()
  {
    return "Freefld";
  }

  public String getPrimaryKey()
  {
    return null;
  }

  public void setPrimaryKey(String newPk_wa_freefld)
  {
  }

  public Integer getPk_wa_freefld()
  {
    return this.m_pk_wa_freefld;
  }

  public Integer getIfieldtype()
  {
    return this.m_ifieldtype;
  }

  public String getVfldtypename()
  {
    return this.m_vfldtypename;
  }

  public Integer getIminno()
  {
    return this.m_iminno;
  }

  public Integer getIproducttype()
  {
    return this.m_iproducttype;
  }

  public Integer getLmaxno()
  {
    return this.m_lmaxno;
  }

  public void setPk_wa_freefld(Integer newPk_wa_freefld)
  {
    this.m_pk_wa_freefld = newPk_wa_freefld;
  }

  public void setIfieldtype(Integer newIfieldtype)
  {
    this.m_ifieldtype = newIfieldtype;
  }

  public void setVfldtypename(String newVfldtypename)
  {
    this.m_vfldtypename = newVfldtypename;
  }

  public void setIminno(Integer newIminno)
  {
    this.m_iminno = newIminno;
  }

  public void setIproducttype(Integer newIproducttype)
  {
    this.m_iproducttype = newIproducttype;
  }

  public void setLmaxno(Integer newLmaxno)
  {
    this.m_lmaxno = newLmaxno;
  }

  public void validate()
    throws ValidationException
  {
    ArrayList errFields = new ArrayList();

    if (this.m_pk_wa_freefld == null) {
      errFields.add(new String("m_pk_wa_freefld"));
    }

    StringBuffer message = new StringBuffer();
    message.append(BDMsg.MSG_NULL_FIELD());
    if (errFields.size() > 0) {
      String[] temp = (String[])(String[])errFields.toArray(new String[0]);
      message.append(temp[0]);
      for (int i = 1; i < temp.length; i++) {
        message.append(",");
        message.append(temp[i]);
      }

      throw new NullFieldException(message.toString());
    }
  }

  public Integer getMyPrimaryKey()
  {
    return this.m_pk_wa_freefld;
  }

  public void setMyPrimaryKey(Integer newPk_wa_freefld)
  {
    this.m_pk_wa_freefld = newPk_wa_freefld;
  }
}