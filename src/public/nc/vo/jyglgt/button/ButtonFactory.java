package nc.vo.jyglgt.button;

/**
 * 说明: 按钮工厂
 * @author 公共开发者
 * 2012-1-5 上午09:20:03
 */
public class ButtonFactory
{
	/**
	 * ButtonFactory 构造子注解。
	 */
	private ButtonFactory()
	{
		super();
	}

	/**
	 * 直接创建按钮
	 * @param id
	 * @param code
	 * @param name
	 * @return
	 */
	public static nc.vo.trade.button.ButtonVO createButtonVO(int id, String code, String name)
	{
		nc.vo.trade.button.ButtonVO btn = new nc.vo.trade.button.ButtonVO();
		btn.setBtnNo(id);
		btn.setBtnName(code);
		btn.setHintStr(name);
		btn.setBtnCode(code);
		btn.setBtnChinaName(code);//带下拉框按钮时用到 对应 buttonObj里的code
		return btn;
	}
	
}