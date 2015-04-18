package nc.bs.trade.comstatus;

import java.util.ArrayList;

import nc.bs.dao.BaseDAO;
import nc.bs.logging.Logger;
import nc.bs.trade.business.HYSuperDMO;
import nc.bs.trade.combase.HYComBase;
import nc.itf.uap.pf.metadata.IFlowBizItf;
import nc.jdbc.framework.SQLParameter;
import nc.md.MDBaseQueryFacade;
import nc.md.data.access.NCObject;
import nc.md.model.IBean;
import nc.md.model.MetaDataException;
import nc.vo.pub.AggregatedValueObject;
import nc.vo.pub.BusinessException;
import nc.vo.pub.CircularlyAccessibleValueObject;
import nc.vo.pub.SuperVO;
import nc.vo.trade.pub.IBillStatus;
import nc.vo.uap.pf.PFBusinessException;
import nc.vo.uap.pf.PFRuntimeException;

/**
 * 
 * @modifier leijun 2008-6 �޸�Ϊ֧��Ԫ����
 * edit by gdt �������update �� public ��
 */
public class JYGLGTBillCommit extends HYComBase implements IBillCommit {
	/**
	 * BillCommit ������ע�⡣
	 */
	public JYGLGTBillCommit() {
		super();
	}

	/**
	 * �����ύ���ݴ�������ArrayList,0--�¼����� �������ڣ�(2004-2-27 18:58:22)
	 *
	 * @param billVo AggregatedValueObject
	 */
	public final ArrayList commitBill(AggregatedValueObject billVo) throws BusinessException {
		//��ѯ����VO�Ƿ�����Ԫ����ģ��
		IBean bean = null;
		try {
			bean = MDBaseQueryFacade.getInstance().getBeanByFullClassName(
					billVo.getParentVO().getClass().getName());
		} catch (MetaDataException e) {
			Logger.warn(e.getMessage(), e);
			bean = null;
		}
		if (bean == null)
			return commitBillWithoutMeta(billVo);
		else
			return commitBillWithMeta(bean, billVo);

	}

	/**
	 * �ύ���ݣ���Ԫ����
	 * @param bean 
	 * @param aggBillVo
	 * @return
	 * @throws BusinessException
	 * @since 5.5 leijun+2008-6
	 */
	private ArrayList commitBillWithMeta(IBean bean, AggregatedValueObject aggBillVo)
			throws BusinessException {

		ArrayList retList = new ArrayList();
		try {
			//1.���µ������ݿ��ĵ���״̬�ֶ�Ϊ�ύ̬
			//����ƽ̨ҵ��ӿڲ�ѯ������״̬�ֶ�����
			NCObject ncObj = NCObject.newInstance(bean, aggBillVo);
			IFlowBizItf fbi = ncObj.getBizInterface(IFlowBizItf.class);
			if (fbi == null)
				throw new PFRuntimeException("����ƽ̨������ʵ��û���ṩҵ��ӿ�IFlowBizInf��ʵ����");
			String strStatusColumn = fbi.getColumnName(IFlowBizItf.ATTRIBUTE_APPROVESTATUS);

			StringBuffer sbStr = new StringBuffer();
			sbStr.append("update ").append(bean.getTable().getName()).append(" set ");
			sbStr.append(strStatusColumn).append("=" + IBillStatus.COMMIT);
			sbStr.append(" where ").append(bean.getTable().getPrimaryKeyName()).append("=? ");//and ");
//			sbStr.append(strStatusColumn).append("=" + IBillStatus.FREE);

			BaseDAO dao = new BaseDAO();
			String billId = aggBillVo.getParentVO().getPrimaryKey();
			SQLParameter param = new SQLParameter();
			param.addParam(billId);
			dao.executeUpdate(sbStr.toString(), param);

			//�������⴦��
			specialCommit(aggBillVo, new HYSuperDMO());

			//2.����PK���²�ѯ������VO���Ի�ø��º��Ts
			aggBillVo.setParentVO((CircularlyAccessibleValueObject) dao.retrieveByPK(aggBillVo
					.getParentVO().getClass(), billId));

			//����ֵ��������Ϊ�����ű��ķ���ֵ
			retList.add(billId);
			retList.add(aggBillVo);
			return retList;
		} catch (BusinessException be) {
			Logger.error(be.getMessage(), be);
			throw be;
		} catch (Exception ex) {
			Logger.error(ex.getMessage(), ex);
			throw new PFBusinessException(ex.getMessage());
		}

	}

	/**
	 * �ύ���ݣ���Ԫ����
	 * @param billVo
	 * @return
	 * @throws BusinessException
	 */
	private ArrayList commitBillWithoutMeta(AggregatedValueObject billVo) throws BusinessException {
		ArrayList retList = new ArrayList();
		try {
			SuperVO headVO = (SuperVO) billVo.getParentVO();
			HYSuperDMO dmo = new HYSuperDMO();
			
			BaseDAO dao = new BaseDAO();
			headVO.setAttributeValue("vbillstatus", IBillStatus.COMMIT);
			dao.updateVO(headVO);// jyglgt ר��

			//�������⴦��
			specialCommit(billVo, dmo);

			//��ѯTS
			billVo.setParentVO(dmo.queryByPrimaryKey(headVO.getClass(), headVO.getPrimaryKey()));

			retList.add(headVO.getPrimaryKey());
			retList.add(billVo);
			return retList;
		} catch (BusinessException be) {
			Logger.error(be);
			throw be;
		} catch (Exception ex) {
			Logger.error(ex.getMessage(), ex);
			throw new BusinessException(ex.getMessage());
		}
	}

	/**
	 * ��ҵ������ɽ�������
	 *
	 * @param vo AggregatedValueObject
	 * @exception Exception �쳣˵����
	 */
	protected void specialCommit(AggregatedValueObject vo, HYSuperDMO dmo) throws Exception {
	}
}