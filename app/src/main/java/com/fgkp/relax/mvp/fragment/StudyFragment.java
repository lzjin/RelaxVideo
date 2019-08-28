package com.fgkp.relax.mvp.fragment;

import android.os.Bundle;

import com.fgkp.relax.mvp.R;
import com.fgkp.relax.mvp.base.BaseFragment;
import com.fgkp.relax.mvp.base.BasePresenter;
import com.gyf.barlibrary.ImmersionBar;

public class StudyFragment extends BaseFragment {
    /**
     * 单例
     */
    private static volatile StudyFragment instance = null;
    public static StudyFragment getInstance() {
        if (instance == null) {
            synchronized (StudyFragment.class) {
                if (instance == null) {
                    instance = new StudyFragment();
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
        return R.layout.fragment_study;
    }

    @Override
    public void initCreateView(Bundle savedInstanceState) {

    }

    @Override
    public void onLoading() {

    }
}
