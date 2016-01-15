package com.li.Tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.util.Log;

import com.li.games.PuzzleMain;
import com.li.games.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 图像工具类：实现图像的分割与自适应
 * Created by li on 16-1-12.
 * @author li
 */
public class ImagesUtil {
    public ItemBeans itemBeans;
    public String TAG="ImagesUtil";
    /**
     * 切图，初始状态（正常顺序）
     *
     *@param type 游戏的种类
     *@param picSelect  传递进来的图片
     *@param context
     */
    public void createInitBitmaps(int type,Bitmap picSelect,Context context){
        Bitmap bitmap=null;
        List<Bitmap> bitmapItems=new ArrayList<Bitmap>();

        int itemWidth=picSelect.getWidth()/type;
        int itemHeight=picSelect.getHeight()/type;
        Log.d(TAG,"itemWidth:    itemWidth:    type:"+itemWidth+"  "+itemHeight+"   "+type);
        for (int i=1;i<=type;i++){
            for (int j=1;j<=type;j++) {
                bitmap = Bitmap.createBitmap(picSelect, (j - 1) * itemWidth, (i - 1) * itemHeight, itemWidth, itemHeight);
                bitmapItems.add(bitmap);

                itemBeans = new ItemBeans((i - 1) * type + j, (i - 1) * type + j, bitmap);//1 ,2, 3,4

                GamesUtil.mItemBeans.add(itemBeans);
            }
        }
        //用于 保存最后一个图片在拼图完成时填充
        PuzzleMain.mLastBitmap=bitmapItems.get(type*type-1);
        //设置最后一个 为空 Item
        bitmapItems.remove(type * type - 1);
        GamesUtil.mItemBeans.remove(type * type - 1);
        Bitmap blankBitmap= BitmapFactory.decodeResource(context.getResources(), R.drawable.blank1);
        Log.d(TAG,"blankbitmapwidth: "+blankBitmap.getWidth());
        blankBitmap=Bitmap.createBitmap(blankBitmap,0,0,itemWidth,itemHeight);
        bitmapItems.add(blankBitmap);
        GamesUtil.mItemBeans.add(new ItemBeans(type * type, 0, blankBitmap));
        //Log.d(TAG,""+GamesUtil.mItemBeans.size());
        GamesUtil.mBlankItemBean = GamesUtil.mItemBeans.get(type * type - 1);
    }

    /**
     * 处理图片 放大、缩小到合适位置
     *
     * @param newWidth  缩放后Width
     * @param newHeight 缩放后Height
     * @param bitmap    bitmap
     * @return bitmap
     */
   public static Bitmap resizeBitmap(float newWidth,float newHeight,Bitmap bitmap){
    Matrix matrix=new Matrix();
    //M' = S(sx, sy) * M,, 左乘 矩阵
    matrix.postScale(newWidth/bitmap.getWidth(),newHeight/bitmap.getHeight());

    Bitmap newBitmap=Bitmap.createBitmap(bitmap,0,0,bitmap.getWidth(),bitmap.getHeight(),matrix,true);

    return newBitmap;
}

}
