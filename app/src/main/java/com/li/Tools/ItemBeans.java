package com.li.Tools;

import android.graphics.Bitmap;

/**
 * Created by li on 16-1-12.
 */
public class ItemBeans {
    private int mItemId;
    private int mBitmapId;
    private Bitmap mBitmap;

    public ItemBeans(){

    }

    /**
     * ItemBeans  的构造函数，传入3个参数
     *
     * @param mItemId   Item 的Id
     * @param mBitmapId Bitmap 的Id
     * @param mBitmap   图片 mBitmap
     */
    public ItemBeans(int mItemId, int mBitmapId, Bitmap mBitmap) {
        //Item 的Id
        this.mItemId = mItemId;
        //Bitmap 的Id
        this.mBitmapId = mBitmapId;
        //mBitmap
        this.mBitmap = mBitmap;
    }

    public int getmItemId() {
        return mItemId;
    }

    public void setmItemId(int mItemId) {
        this.mItemId = mItemId;
    }

    public int getmBitmapId() {
        return mBitmapId;
    }

    public void setmBitmapId(int mBitmapId) {
        this.mBitmapId = mBitmapId;
    }

    public Bitmap getmBitmap() {
        return mBitmap;
    }

    public void setmBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
    }
}
