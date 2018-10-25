package com.fgkp.relax.mvp;

import android.Manifest;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.fgkp.relax.mvp.activity.TestActivity;
import com.fgkp.relax.mvp.base.BaseActivity;
import com.fgkp.relax.mvp.base.BasePresenter;
import com.fgkp.relax.mvp.utils.MLog;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import pub.devrel.easypermissions.EasyPermissions;

public class MainActivity extends BaseActivity implements  EasyPermissions.PermissionCallbacks {

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void setContentViewResId() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initCreate(Bundle savedInstanceState) {
        checkPermission();
      timeMain(TestActivity.class);
    }

    //定时跳转到主页面
    private void timeMain(final Class toActivity) {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                IntenToActivity(toActivity); //执行
            }
        };
        timer.schedule(task, 1000 * 1);
    }

    @Override
    public void onLoading() {

    }
    /**
     * 2 检测权限
     */
    private void checkPermission() {
        String perms[] = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE
                , Manifest.permission.CAMERA};
        if (EasyPermissions.hasPermissions(this, perms)) {
            MLog.i("test","----------------------已授权");
        } else {
            EasyPermissions.requestPermissions(this, "权限申请\n此权限用于基本服务", 10, perms);
        }
    }
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        switch (requestCode) {
            case 10:
                break;
        }
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        showToast("您已拒绝此权限,部分功能将无法使用");
    }
}
