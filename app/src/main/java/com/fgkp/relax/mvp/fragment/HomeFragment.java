package com.fgkp.relax.mvp.fragment;

import android.os.Bundle;

import com.fgkp.relax.mvp.R;
import com.fgkp.relax.mvp.base.BaseFragment;
import com.fgkp.relax.mvp.base.BasePresenter;

public class HomeFragment extends BaseFragment {
    /**
     * 单例
     */
    private static volatile HomeFragment instance = null;
    public static HomeFragment getInstance() {
        if (instance == null) {
            synchronized (HomeFragment.class) {
                if (instance == null) {
                    instance = new HomeFragment();
                }
            }
        }
        return instance;
    }
    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    public int setContentViewResId() {
        return R.layout.fragment_home;
    }

    @Override
    public void initCreateView(Bundle savedInstanceState) {

    }

    @Override
    public void onLoading() {

    }
}
