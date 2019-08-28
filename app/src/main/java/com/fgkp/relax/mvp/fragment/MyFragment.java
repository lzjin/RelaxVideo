package com.fgkp.relax.mvp.fragment;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.fgkp.relax.mvp.R;
import com.fgkp.relax.mvp.base.BaseFragment;
import com.fgkp.relax.mvp.base.BasePresenter;
import com.gyf.barlibrary.ImmersionBar;
import com.gyf.barlibrary.SimpleImmersionOwner;
import com.gyf.barlibrary.SimpleImmersionProxy;

public class MyFragment extends BaseFragment implements SimpleImmersionOwner {
    /**
     * 单例
     */
    private static volatile MyFragment instance = null;
    public static MyFragment getInstance() {
        if (instance == null) {
            synchronized (MyFragment.class) {
                if (instance == null) {
                    instance = new MyFragment();
                }
            }
        }
        return instance;
    }

    /**
     * ImmersionBar代理类
     */
    private SimpleImmersionProxy mSimpleImmersionProxy = new SimpleImmersionProxy(this);

    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    public int setContentViewResId() {
        return R.layout.fragment_my;
    }

    @Override
    public void initCreateView(Bundle savedInstanceState) {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        mSimpleImmersionProxy.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSimpleImmersionProxy.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mSimpleImmersionProxy.onDestroy();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        mSimpleImmersionProxy.onHiddenChanged(hidden);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mSimpleImmersionProxy.onConfigurationChanged(newConfig);
    }

    @Override
    public void initImmersionBar() {
       // ImmersionBar.with(this).init();
    }

    /**
     * 是否可以实现沉浸式，当为true的时候才可以执行initImmersionBar方法
     * Immersion bar enabled boolean.
     *
     * @return the boolean
     */
    @Override
    public boolean immersionBarEnabled() {
        return false;
    }
}
