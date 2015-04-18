package nc.ui.so.so001.panel;

//import fuction.*;
import com.sun.jna.Native;

public class ICCard
{
	
	private ICCard(){
		
	}
	
	private static ICCard iccard = new ICCard();
	
	public static ICCard getInstance(){
		return iccard;
	}
	
	/**
	 * @param args
	 */
	short st=1;
	int icdev ;
	byte Snr[]=new byte[5];
	
	/**
	 * 连接卡
	 * @param epen
	 */
	public void DevConnect(Declare.mwrf epen)
	{
		byte[] ver=new byte[20];
		icdev =epen.rf_usbopen();
		st=epen.rf_get_status(icdev, ver);
		if(st==0)
		{
			String str=new String(ver,0,18);
			System.out.println("设备初始化成功！" + str); 
		}
		else
		{
			System.out.println("设备连接失败!");
		}
		for(short i=0;i<16;i++)
		{
//			byte[] key=new byte[]{(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff,(byte)0xff};
//			ickey = Char(251)+Char(11)+Char(213)+Char(187)+Char(232)+Char(123)
			byte[] key=new byte[]{(byte)0xfb,(byte)0x0b,(byte)0xd5,(byte)0xbb,(byte)0xe8,(byte)0x7b};
			st=epen.rf_load_key(icdev, (short)0, i, key);
			if(st!=0)
			{
				System.out.println("加载 "+i+" 扇区密码失败!");
			}
		}
		epen.rf_beep(icdev, (short)30);
	}
	
	public String read(Declare.mwrf epen)
	{
		String readData = null;
		short sector=1;
		st=epen.rf_card(icdev,(short)1,Snr);
		if(st==0)
		{
			byte[] Snrhex=new byte[9];
			epen.hex_a(Snr,Snrhex,(short)4);
			String str=new String(Snrhex,0,8);
			System.out.println(str);
		}
		else
		{
			System.out.println("寻卡失败！");
		}
		
		//读取车号--1扇区6块
		st=epen.rf_authentication(icdev, (short)0, sector);
		if(st!=0)
		{
			System.out.println(sector+"扇区密码验证 错误!");
		}

		byte[] rdata=new byte[17];
//		st=epen.rf_read(icdev, (short)(sector*4), rdata);
		st=epen.rf_read(icdev, (short)(6), rdata);
		if(st==0)
		{
			String stri=new String(rdata);
			System.out.println("读车号数据成功，数据： "+stri);
			readData = stri;
		}
		else
		{
			System.out.println("读车号数据失败！");
		}
		
		//读取派车单号--2扇区8块
		sector = 2;
		st=epen.rf_authentication(icdev, (short)0, sector);
		if(st!=0)
		{
			System.out.println(sector+"扇区密码验证 错误!");
		}

		rdata=new byte[17];
//		st=epen.rf_read(icdev, (short)(sector*4), rdata);
		st=epen.rf_read(icdev, (short)(8), rdata);
		if(st==0)
		{
			String stri=new String(rdata);
			System.out.println("读派车单号数据成功，数据： "+stri);
			readData = readData + "," + stri;
		}
		else
		{
			System.out.println("读派车单号数据失败！");
		}
		
		return readData;
	}
	
	/**
	 * 
	 * @param epen
	 */
	public void disconnectDev(Declare.mwrf epen)
	{
		epen.rf_usbclose(icdev);
		//System.out.println("断开设备");
	}
	public void MifarePROCpu(Declare.mwrf epen)
	{
		byte[] resetData=new byte[50];
		st=epen.rf_card(icdev,(short)1,Snr);
		if(st==0)
		{
			byte[] Snrhex=new byte[9];
			epen.hex_a(Snr,Snrhex,(short)4);
			String str=new String(Snrhex,0,8);
			System.out.println(str);
		}
		else
		{
			System.out.println("寻卡失败！");
		}
		st=epen.rf_pro_rst(icdev, resetData);
		if(st!=0)
		{
			System.out.println("复位失败！"+st);
		}
		else
		{
			byte[] resetDataHex=new byte[100];
			epen.hex_a(resetData, resetDataHex, (short)resetData.length);
			String str=new String(resetDataHex,0,resetData[0]*2);
			System.out.println("复位成功！"+str);
		}
		byte[] cmd=new byte[]{0x00,0x00,0x00,0x05,0x00,(byte)0x84,0x00,0x00,0x08};
		byte[] returnData=new byte[50];
		byte[] returnDataHex=new byte[100];
		st=epen.rf_pro_trn(icdev, cmd, returnData);
		if(st!=0)
		{
			System.out.println("发送命令失败！");
		}
		else
		{
			epen.hex_a(returnData, returnDataHex, (short)(returnData[3]+4));
			String strData=new String(returnDataHex,0,(short)(returnData[3]+4)*2);
			System.out.println(strData);
		}
	}
	
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		Declare.mwrf epen = (Declare.mwrf) Native.loadLibrary("mwhrf_bj", Declare.mwrf.class);   
		if (epen != null)   
			System.out.println("DLL加载成功！"); 
		else
			System.out.println("DLL加载失败！");	
		ICCard con=new ICCard();
		con.DevConnect(epen);
//		con.mifareOne(epen);
		con.MifarePROCpu(epen);
		con.disconnectDev(epen);
	}   
}
