package nc.vo.jyglgt.button;

import java.util.LinkedList;

import nc.vo.jyglgt.pub.Toolkits.Toolkits;

/**
 * 
 * 说明:按钮工具,用于按钮组的追加,插入,删除
 * @author 公共开发者
 * 2012-1-5 上午09:25:42
 */  
public class ButtonTool {
    /**
     * 功能:插入按钮insbtn到tarbtns按钮组的pos前 
     * @param insbtn 要插入的按钮
     * @param tarbtns 被插入的按钮组
     * @param pos 所在按钮组位置
     * @return
     * @return:int[] 新按钮组
     */
    public static int[] insertButton(int insbtn,int[] tarbtns,int pos){
        return ButtonTool.insertButtons(new int[]{insbtn}, tarbtns, pos);
    }
    
    /**
     * 
     * 功能:插入按钮,将insbtns按钮组插入到tarbtns按钮组的指定下标前,
     * 1.如果pos超过目标按钮组的长度则将插入的按钮组置于目标组末尾 
     * 2.如果pos小于等于0则插入目标组开头
     * @param pos 目标按钮组的指定下标
     * @param insbtns 要插入的按钮组 
     * @param tarbtns 目标按钮组
     * @return 
     * @return:int[] 插入成功后的新按钮组
     * @throws Exception 
     */
    public static int[] insertButtons(int[] insbtns,int[] tarbtns,int pos){
        int[] newbtns=new int[insbtns.length+tarbtns.length];
        try {
            if(pos<=0){
                copyArray(insbtns,0,newbtns,0,insbtns.length);
                copyArray(tarbtns,0,newbtns,insbtns.length,tarbtns.length);
            }else if(pos>=tarbtns.length){
                copyArray(tarbtns,0,newbtns,0,tarbtns.length);
                copyArray(insbtns,0,newbtns,tarbtns.length,insbtns.length);
            }else{
                int[][] array=splitArray(tarbtns, pos);
                copyArray(array[0],0,newbtns,0,array[0].length);
                copyArray(insbtns,0,newbtns,pos,insbtns.length);
                copyArray(array[1],0,newbtns,array[0].length+insbtns.length,array[1].length);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return tarbtns;
        }
        return newbtns;
    }
    /**
     * 功能: 把数组src从spos开始复制,到数组tar的tpos,长度为len
     * @param src  源数组
     * @param tar 目标数组
     * @param spos 代表src的下标,范围必须在0到数组tar的长度之内
     * @param tpos 代表tar的下标
     * @param len 复制的最大长度
     * @return:void
     * @throws Exception 
     */
    public static void copyArray(int[] src,int spos,int[] tar,int tpos,int len) throws Exception{
        if(tar.length<len) 
            throw new Exception("源数组的长度超过了目标数组的长度!");
        for (int i = 0; i < len; i++) {
            tar[i+tpos]=src[spos+i];
        }
    }
    /**
     * 功能: 分离数组,将数组按pos分离成2段
     * @param tar 被分割的数组
     * @param pos 分割位置
     * @return
     * @throws Exception
     * @return:int[][] 新数组
     */
    public static int[][] splitArray(int[] tar,int pos) throws Exception{
        if(pos>tar.length)
            throw new Exception("分离的位置超过了目标数组的长度!");
        int[] arr1=new int[pos];
        int[] arr2=new int[tar.length-pos];
        copyArray(tar, 0, arr1, 0, pos);
        copyArray(tar, pos,arr2, 0, tar.length-pos);
        return new int[][]{arr1,arr2};
    }
    /**
     * 功能: 从按钮组btns中删除按钮delbtn,这里的delbtn不是索引
     * @param delbtn 被删除的按钮
     * @param btns 目标按钮组
     * @return
     * @return:int[] 新按钮组
     */
    public static int[] deleteButton(int delbtn,int[] btns){
        return deleteButtons(new int[]{delbtn}, btns);
    }
    /**
     * 功能:从按钮组btns中删除一组按钮delbtns,这里的delbtns不是索引数组
     * @param btns 目标按钮组
     * @param delbtns 被删除的按钮组
     * @return
     * @return:int[] 新按钮组
     */
    @SuppressWarnings("unchecked")
	public static int[] deleteButtons(int[] delbtns,int[] btns){
        LinkedList btnlist=Toolkits.toList(btns);
        for (int i = 0; i < delbtns.length; i++) {
            for (int j = 0; j < btns.length; j++) {
                if(delbtns[i]==btns[j]){
                    btnlist.remove(new Integer(btns[j]));
                }
            }
        }
        return Toolkits.toArray(btnlist);
    }
    
}
