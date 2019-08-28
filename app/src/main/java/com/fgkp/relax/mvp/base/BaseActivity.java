package com.fgkp.relax.mvp.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fgkp.relax.mvp.R;
import com.fgkp.relax.mvp.config.ActivityController;
import com.fgkp.relax.mvp.utils.ToastUtil;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import pub.devrel.easypermissions.EasyPermissions;

import static android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;

/**
 * Created by lzj on 2018/10/4.
 */

public abstract class BaseActivity <P extends BasePresenter> extends AppCompatActivity implements BaseView{
    private P mPresenter;
    private Unbinder mUnbinder;//释放资源用
    public ActivityController mManagerActivity;//管理activity
    public BaseActivity activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        mPresenter=createPresenter();
        if(mPresenter!=null){
            mPresenter.attach(this);
        }
        setContentViewResId();
        initBase();
        initCreate(savedInstanceState);
    }
    public abstract P createPresenter();//创建Presenter
    public abstract void setContentViewResId();//设置布局
    public abstract void initCreate(Bundle savedInstanceState);//子类--初始化

    /**
     * 基类初始化
     */
    private void initBase() {
        mUnbinder = ButterKnife.bind(this);//绑定注解
        mManagerActivity = ActivityController.getInstance(); //把activity放入栈里面进行管理
        mManagerActivity.pushOneActivity(this);
        setStatusBar();//状态栏
    }
    /**
     * 状态栏（子类重写可改变）
     */
    protected void setStatusBar() {
        //Window window = this.getWindow();
        //window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);//用于引导页
        ImmersionBar.with(this)
                .statusBarDarkFont(true, 0.2f) //原理：如果当前设备支持状态栏字体变色，会设置状态栏字体为黑色，如果当前设备不支持状态栏字体变色，会使当前状态栏加上透明度，否则不执行透明度
                .fitsSystemWindows(true)
                .init();

//        activity.getWindow().setStatusBarColor(activity.getResources().getColor(R.color.white));
//        if(android.os.Build.VERSION.SDK_INT >= 23){
//            activity. getWindow().getDecorView().setSystemUiVisibility(SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        }


    }
    /**
     * 页面跳转
     * @param toActivity
     */
    public void IntenToActivity(Class toActivity){
        Intent intent = new Intent(this,toActivity);
        startActivity(intent);
    }

    /**
     *  Toast短显示
     */
    public void showToast(String str){
        ToastUtil.showShort(this,str);
    }
    /**
     *  Toast长显示
     */
    public void showLongToast(String str){
        ToastUtil.showLong(this,str);
    }




    /**
     * 权限框架 回调 (此处不写，就在每个子类重写这个方法)
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mPresenter!=null){
            mPresenter.detach();
        }

        // 必须调用该方法，防止内存泄漏
        ImmersionBar.with(this).destroy();
        // EventBus.getDefault().unregister(this);//解注册
    }




}


