package com.fgkp.relax.mvp.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Admin
 * Toast 显示提醒
 */

public class ToastUtil {

    private static Toast toast;

    /**
     *  短时间
     */
    public static void showShort(Context context, String text){
        if(toast == null) toast = Toast.makeText(context,text, Toast.LENGTH_SHORT);
        else toast.setText(text);
        toast.show();
    }

    /**
     *  长时间
     */
    public static void showLong(Context context, String text){
        if(toast == null) toast = Toast.makeText(context,text, Toast.LENGTH_LONG);
        else toast.setText(text);
        toast.show();
    }

    /**
     *引用资源内容
     */
    public static void showShort(Context context, int textRes){
        if(toast == null) toast = Toast.makeText(context,context.getResources().getString(textRes), Toast.LENGTH_SHORT);
        else toast.setText(context.getResources().getString(textRes));
        toast.show();
    }

}
