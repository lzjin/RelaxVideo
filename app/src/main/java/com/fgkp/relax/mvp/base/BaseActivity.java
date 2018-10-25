package com.fgkp.relax.mvp.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fgkp.relax.mvp.config.ActivityController;
import com.fgkp.relax.mvp.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by lzj on 2018/10/4.
 */

public abstract class BaseActivity <P extends BasePresenter> extends AppCompatActivity implements BaseView{
    private P mPresenter;
    private Unbinder mUnbinder;//释放资源用
    public ActivityController mManagerActivity;//管理activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        mPresenter.detach();
       // EventBus.getDefault().unregister(this);//解注册
    }




}


