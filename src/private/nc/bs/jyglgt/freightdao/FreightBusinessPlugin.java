package nc.bs.jyglgt.freightdao;

import nc.bs.logging.Logger;
import nc.bs.pub.pa.IBusinessPlugin;
import nc.bs.pub.pa.html.IAlertMessage;
import nc.pub.jyglgt.proxy.Proxy;
import nc.vo.jyglgt.pub.Toolkits.IDatasourceConst;
import nc.vo.pub.BusinessException;
import nc.vo.pub.lang.UFDate;
import nc.vo.pub.pa.Key;

/**
 * ��ȡ�����Զ���ϵͳ���˷ѽ��㵥����NC�����������������̨Ԥ��
 * ��@author guoyt
 * */
public class FreightBusinessPlugin implements IBusinessPlugin {

	public int getImplmentsType() {
		// TODO Auto-generated method stub
		return IMPLEMENT_RETURNFORMATMSG;
	}

	public Key[] getKeys() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTypeDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getTypeName() {
		// TODO Auto-generated method stub
		return null;
	}

	public IAlertMessage implementReturnFormatMsg(Key[] keys, String corpPK,
			UFDate clientLoginDate) throws BusinessException {
		Logger.debug("fd log:��ȡ�����Զ���ϵͳ���˷�Ԥ��....Begin");
		Logger.debug("in the method implementReturnFromatMsg(keys[],corpPK,clientLoginDate)");	
//		new SaleoutDAO().insertInteFreightByBfgl(IDatasourceConst.BFJL);
		Proxy.getItf().insertInteFreightByBfgl(IDatasourceConst.BFJL);
		Logger.debug("��ȡ�����Զ���ϵͳ���ջ����������ݽ���");
		return null;
	}

	public String implementReturnMessage(Key[] arg0, String arg1, UFDate arg2)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	public Object implementReturnObject(Key[] arg0, String arg1, UFDate arg2)
			throws BusinessException {
		// TODO Auto-generated method stub
		return null;
	}

	public boolean implementWriteFile(Key[] arg0, String arg1, String arg2,
			UFDate arg3) throws BusinessException {
		// TODO Auto-generated method stub
		return false;
	}

}