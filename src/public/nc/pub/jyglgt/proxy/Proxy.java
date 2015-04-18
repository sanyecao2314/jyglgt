package nc.pub.jyglgt.proxy;

import nc.bs.framework.common.NCLocator;
import nc.bs.framework.naming.Context;
import nc.itf.jyglgt.pub.IJyglgtItf;



public class Proxy {
	private final static Proxy m_Proxy=new Proxy();
	private Proxy() {
		super();
	}


	public static Proxy getInstance() {
		return m_Proxy;
	}
	

	private  static Context getLocator() {
		return NCLocator.getInstance();
	}

	public static IJyglgtItf getItf(){
		try{ 
	 		return (IJyglgtItf) getLocator().lookup(IJyglgtItf.class.getName());  
	 	}catch(Exception e){ 
	 		e.printStackTrace(); 
	 		return null; 
	 	}
	}
	
	
}
