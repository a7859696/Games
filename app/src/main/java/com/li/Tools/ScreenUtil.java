package com.li.Tools;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

/**
 * Created by li on 16-1-9.
 */
public class ScreenUtil {
    /**
     * 获取屏幕相关参数
     *
     * @param context context
     * @return DisplayMetrics 屏幕宽高
     */
    public static DisplayMetrics getScreenSize(Context context){
        DisplayMetrics metrics=new DisplayMetrics();
        WindowManager windowManager= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display=windowManager.getDefaultDisplay();
        display.getMetrics(metrics);
        return metrics;
    }

    /**
     * 获取屏幕density
     *
     * @param
     */
    public  static float getDevicesDesity(Context context){
        DisplayMetrics metrics=new DisplayMetrics();
        WindowManager windowManager=(WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display=windowManager.getDefaultDisplay();
        display.getMetrics(metrics);

        return metrics.density;
    }
}
