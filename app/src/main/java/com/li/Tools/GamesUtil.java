package com.li.Tools;

import android.util.Log;

import com.li.games.PuzzleMain;

import java.util.ArrayList;
import java.util.List;


/**
 * 拼图工具类：实现拼图的交换与生成算法
 *
 * @author xys
 * Created by li on 16-1-13.
 */
public class GamesUtil {
    // 游戏信息单元格Bean
    public static List<ItemBeans> mItemBeans=new ArrayList<ItemBeans>();
    // 空格单元格
    public static ItemBeans mBlankItemBean = new ItemBeans();


    /**
     *生成 随机的 Item
     *
     */
    public static void getPuzzleGenerator(){
        int index=0;
        //Log.d("ImagesUtil","mBlankItemBean的第一次："+mBlankItemBean.getmBitmap());
        for (int i=0;i<mItemBeans.size();i++){
            index=(int)(Math.random()* PuzzleMain.mType*PuzzleMain.mType);
            //Log.d("ImagesUtil","index: "+index );
            swapItems(mItemBeans.get(index), GamesUtil.mBlankItemBean);
        }

        //得到每张图片的标识，  BitmapId();
        List<Integer> data=new ArrayList<Integer>();
        for(int i=0;i<mItemBeans.size();i++){
            data.add(mItemBeans.get(i).getmBitmapId());
        }
        //判断生成的是否有解
        if(canSove(data)){
            return;
        }else {
            //没有解的时候  重新再去生成 随机
            getPuzzleGenerator();
        }
    }

    /**
     * 该数据是否有解
     *
     * @param data  拼图数组数据
     * @return    该数据是否有解
     */
    private static boolean canSove(List<Integer> data) {
        int blankId=GamesUtil.mBlankItemBean.getmItemId();
        Log.d("GamesUtil",""+blankId);
        //如果序列A的宽度位奇数，那么每个可解的问题所定义的“倒置变量值”的和-Sum(t) 必须是偶数
        if (data.size()%2==1){
            return getInversion(data)%2==0;
        }else {
            //如果序列Ａ的宽度为偶数，那么当空格X位于从下往上数的奇数行中时，第一的“倒置变量值”
            //的和---Sum(t)必须是偶数，当空格X位于从下往上的偶数行中时，定义的“倒置变量值”的和
            //-----Sum(t)必须是奇数。
            if(((blankId-1)/PuzzleMain.mType%2==1)){
                return getInversion(data)%2==0;
            }else{
                return getInversion(data)%2==1;
            }

        }
    }


    /**
     * 计算倒置和算法
     *
     * @param data　　拼图数组数据
     * @return　　　　该序列是否有解
     */
    private static int getInversion(List<Integer> data) {
        int inversion=0;
        int inversionCount=0;
        for (int i=0;i<data.size();i++){
            for (int j=i+1;j<data.size();j++) {
                int index = data.get(i);
                if (data.get(j)!=0&&data.get(j)<index){
                    inversionCount++;
                }
            }
            inversion+=inversionCount;
            inversionCount=0;
        }
        return inversion;
    }



    /**
     * 生成随机图片的时候使用； 交换空格与点击Item 的位置时候 使用
     * 没有交换ItemBeans 的ItemID..
     * @param from       要交换的图
     * @param blank      空白 图
     */
    public static void swapItems(ItemBeans from, ItemBeans blank) {
        //交换的写法，设置中间值
        ItemBeans tempItemBean=new ItemBeans();
        //交换 BitmapId
        tempItemBean.setmBitmapId(from.getmBitmapId());
        from.setmBitmapId(blank.getmBitmapId());
        blank.setmBitmapId(tempItemBean.getmBitmapId());

        //交换 Bitmap
        tempItemBean.setmBitmap(from.getmBitmap());
        from.setmBitmap(blank.getmBitmap());
        blank.setmBitmap(tempItemBean.getmBitmap());

        //设置新的 blank
        GamesUtil.mBlankItemBean=from;
    }

    /**
     * 判断图片是否可以移动
     *
     */
    public static boolean isMoveable(int position){
        int type=PuzzleMain.mType;
        //获取空格 Item

        int blankId=GamesUtil.mBlankItemBean.getmItemId()-1;
        //不同行为相差为type
        if (Math.abs(blankId-position)==type){
            Log.d("lixuan","第一个if ");
            return  true;
        }
        if ((blankId/type==position/type)&&Math.abs(blankId-position)==1){  //abs获取绝对值
            Log.d("lixuan","第2个if ==return,");
            return true;
        }
            Log.d("lixuan","第3个 ==return ");
        return false;
    }

    /**
     * 判断移动图片后，，  是否成功了
     */
    public boolean isSuccess() {
        for (ItemBeans tempBean:GamesUtil.mItemBeans){
            if (tempBean.getmBitmapId()!=0&&(tempBean.getmItemId()==tempBean.getmBitmapId())){
                continue;
            }else if (tempBean.getmBitmapId()==0&&tempBean.getmItemId()==PuzzleMain.mType*PuzzleMain.mType){
                continue;
            }else
            return false;

        }
        return true;
    }
}
