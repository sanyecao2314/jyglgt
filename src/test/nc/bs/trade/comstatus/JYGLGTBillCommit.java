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
 * @modifier leijun 2008-6 修改为支持元数据
 * edit by gdt 此类请打到update 的 public 端
 */
public class JYGLGTBillCommit extends HYComBase implements IBillCommit {
	/**
	 * BillCommit 构造子注解。
	 */
	public JYGLGTBillCommit() {
		super();
	}

	/**
	 * 进行提交单据处理，返回ArrayList,0--事件戳。 创建日期：(2004-2-27 18:58:22)
	 *
	 * @param billVo AggregatedValueObject
	 */
	public final ArrayList commitBill(AggregatedValueObject billVo) throws BusinessException {
		//查询主表VO是否定义了元数据模型
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
	 * 提交单据，有元数据
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
			//1.更新单据数据库表的单据状态字段为提交态
			//根据平台业务接口查询出单据状态字段名称
			NCObject ncObj = NCObject.newInstance(bean, aggBillVo);
			IFlowBizItf fbi = ncObj.getBizInterface(IFlowBizItf.class);
			if (fbi == null)
				throw new PFRuntimeException("流程平台：单据实体没有提供业务接口IFlowBizInf的实现类");
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

			//进行特殊处理
			specialCommit(aggBillVo, new HYSuperDMO());

			//2.根据PK重新查询出主表VO，以获得更新后的Ts
			aggBillVo.setParentVO((CircularlyAccessibleValueObject) dao.retrieveByPK(aggBillVo
					.getParentVO().getClass(), billId));

			//返回值，可以作为动作脚本的返回值
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
	 * 提交单据，无元数据
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
			dao.updateVO(headVO);// jyglgt 专用

			//进行特殊处理
			specialCommit(billVo, dmo);

			//查询TS
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
	 * 各业务组件可进行重载
	 *
	 * @param vo AggregatedValueObject
	 * @exception Exception 异常说明。
	 */
	protected void specialCommit(AggregatedValueObject vo, HYSuperDMO dmo) throws Exception {
	}
}