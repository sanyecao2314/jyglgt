package nc.ui.jyglgt.scm.ic.measurerate;

import java.util.Hashtable;

import nc.ui.pub.beans.UIRefPane;
import nc.vo.bd.b15.MeasureRateVO;

/**
 * 此处插入类型说明。 创建日期：(2001-7-17 13:55:38)
 * 
 * @author：Administrator
 */
public class InvMeasRate {
	/** Key:pk_invmandoc: value:MeasureRateVO[] */
	Hashtable m_htCastUnit = new Hashtable();

	/** Key:pk_invmandoc ; value:用于计量档案参照的过滤子句 */
	Hashtable m_whereString = new Hashtable();

	/**
	 * InvMeasRate 构造子注解。
	 */
	public InvMeasRate() {
		super();
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-6-27 11:01:47)
	 * 
	 * @param row
	 *            int
	 */
	public void filterMeas(String pk_corp, String pk_invmandoc,
			UIRefPane measRef) {
		if (measRef == null)
			return;
		if (pk_corp == null || pk_invmandoc == null) {
			measRef.setWhereString(" where 1<0");
			return;
		}

		Object bufferValue = m_whereString.get(pk_invmandoc);
		String tempID = null;

		if (bufferValue == null) {

			try {

				//MeasureRateVO[] voMeas
				// =nc.ui.bd.b15.InventoryBO_Client.queryInvMeasVO(pk_corp,pk_invmandoc);
				//"20110003")));//
				MeasureRateVO[] voMeas = getMeasInfo(pk_corp, pk_invmandoc);

				if (voMeas != null && voMeas.length > 0) {
					//					getBillCardPanel().getBillData().getBodyItem("castunitname").setEnabled(true);
					//					getBillCardPanel().getBillData().getBodyItem(m_sAstItemKey).setEnabled(true);

					//					ArrayList alUnit = (ArrayList) alRes.get(0);
					String sPK = voMeas[0].getPk_measdoc();
					tempID = "(pk_measdoc='" + sPK + "'";
					//m_htCastUnit.put(pk_invmandoc, voMeas);

					for (int i = 1; i < voMeas.length; i++) {
						sPK = voMeas[i].getPk_measdoc();
						tempID = tempID + " or pk_measdoc='" + sPK + "'";
					}
					tempID += ")";
					m_whereString.put(pk_invmandoc, tempID);
				}

			} catch (Exception e) {

			}
		} else {
			tempID = bufferValue.toString();
		}

		if (tempID == null)
			measRef.setWhereString(" where 1<0");
		else
			measRef.setWhereString(tempID);
		return;
	}

	/**
	 * 此处插入方法说明。 创建日期：(2001-7-17 14:11:48)
	 * 
	 * @return nc.vo.bd.b15.MeasureRateVO
	 * @param pk_invmandoc
	 *            java.lang.String
	 * @param pk_measdoc
	 *            java.lang.String
	 */
	public nc.vo.bd.b15.MeasureRateVO getMeasureRate(String pk_invmandoc,
			String pk_measdoc) {
		nc.vo.bd.b15.MeasureRateVO[] voMeas = (nc.vo.bd.b15.MeasureRateVO[]) m_htCastUnit
				.get(pk_invmandoc);
		if (voMeas != null) {
			for (int i = 0; i < voMeas.length; i++) {
				if (voMeas[i].getPk_measdoc().equals(pk_measdoc)) {
					return voMeas[i];

				}

			}
		}

		return null;
	}
	
	/**
	 * 创建人：刘家清 创建时间：2008-10-17 上午09:23:20 创建原因： 如果配置有的话，一定要取到。
	 * @param pk_invmandoc
	 * @param pk_measdoc
	 * @return
	 */
	public nc.vo.bd.b15.MeasureRateVO getMeasureRateDirect(String pk_corp,String pk_invmandoc,
			String pk_measdoc) {
		
		nc.vo.bd.b15.MeasureRateVO[] voMeas = (nc.vo.bd.b15.MeasureRateVO[]) m_htCastUnit
				.get(pk_invmandoc);
		
		
		if (voMeas == null) {
			voMeas = getMeasInfo(pk_corp,pk_invmandoc);

		}
		
		if (voMeas != null) {
			for (int i = 0; i < voMeas.length; i++) {
				if (voMeas[i].getPk_measdoc().equals(pk_measdoc)) {
					return voMeas[i];

				}

			}
		}

		return null;
	}

	/**
	 * 功能：获得目的存货所有辅计量信息 参数：存货管理档案 返回： 例外： 日期：(2005-1-19 14:21:08)
	 * 修改日期，修改人，修改原因，注释标志：
	 * 
	 * @return nc.vo.bd.b15.MeasureRateVO[]
	 * @param pk_measdoc
	 *            java.lang.String
	 */
	public MeasureRateVO[] getMeasInfo(String pk_corp, String pk_invmandoc) {
		if (pk_corp != null && pk_invmandoc != null) {
			MeasureRateVO[] voMeas = null;
			if (m_htCastUnit != null && m_htCastUnit.size() > 0) {
				voMeas = (MeasureRateVO[]) m_htCastUnit.get(pk_invmandoc);
				if (voMeas == null) {
					try {
						voMeas = nc.ui.bd.b15.InventoryBO_Client
								.queryInvMeasVO(pk_corp, pk_invmandoc);
					} catch (Exception e) {
						nc.vo.scm.pub.SCMEnv.out(e);
					}
					if (voMeas != null)
						m_htCastUnit.put(pk_invmandoc, voMeas);
				}
				return voMeas;
			} else {
				m_htCastUnit = new Hashtable();
				try {
					voMeas = nc.ui.bd.b15.InventoryBO_Client.queryInvMeasVO(
							pk_corp, pk_invmandoc);
				} catch (Exception e) {
					nc.vo.scm.pub.SCMEnv.out(e);
				}
				if (voMeas != null)
					m_htCastUnit.put(pk_invmandoc, voMeas);
				return voMeas;
			}
		}
		return null;
	}
}