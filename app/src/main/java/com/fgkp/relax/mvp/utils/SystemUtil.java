package com.fgkp.relax.mvp.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

public class SystemUtil {
    /**
     * 安装APK文件
     * 兼容7.0版本
     */
    public static void installApk(Context context, String apkPath, String apkName) {
        File apkfile = new File(apkPath, apkName);
        if (!apkfile.exists()) {
            return;
        }
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // 由于没有在Activity环境下启动Activity,设置下面的标签
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //包名+fileprovider
            Uri fileUri = FileProvider.getUriForFile(context,"com.fgkp.relax.mvp"+".fileprovider", apkfile);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//临时uri授权
            intent.setDataAndType(fileUri, "application/vnd.android.package-archive");
            MLog.i("test","------------安装位置:"+fileUri);
        } else {
            intent.setDataAndType(Uri.fromFile(apkfile), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }
    /**
     * [获取应用程序版本名称信息]
     * @param context
     * @return 当前应用的版本名称
     */
    public static synchronized String getPackageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
