package com.fgkp.relax.mvp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.fgkp.relax.mvp.R;
import com.fgkp.relax.mvp.base.BaseActivity;
import com.fgkp.relax.mvp.base.BasePresenter;
import com.fgkp.relax.mvp.fragment.HomeFragment;
import com.fgkp.relax.mvp.fragment.MyFragment;
import com.fgkp.relax.mvp.fragment.StudyFragment;
import com.fgkp.relax.mvp.utils.FragmentUtil;
import com.gyf.barlibrary.ImmersionBar;

import butterknife.BindView;

import static android.view.View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;

/**
 * 首页
 */
public class HomeActivity extends BaseActivity {
    @BindView(R.id.main_fragment)
    FrameLayout mainFragment;
    @BindView(R.id.main_rb_home)
    RadioButton mainRbHome;
    @BindView(R.id.main_rb_study)
    RadioButton mainRbStudy;
    @BindView(R.id.main_rb_my)
    RadioButton mainRbMy;
    @BindView(R.id.main_group)
    RadioGroup mainGroup;


    HomeFragment   homeFragment= HomeFragment.getInstance();
    StudyFragment  studyFragment=   StudyFragment.getInstance();
    MyFragment     myFragment=   MyFragment.getInstance();

    private Fragment indexFragment = homeFragment;
    @Override
    public BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void setStatusBar() {
        super.setStatusBar();
    }

    @Override
    public void setContentViewResId() {
        setContentView( R.layout.activity_home);
    }

    @Override
    public void initCreate(Bundle savedInstanceState) {
        initData();
    }

    private void initData() {
        FragmentUtil.addFragment(R.id.main_fragment, activity, indexFragment);
        mainRbHome.setChecked(true);
        mainGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.main_rb_home:
                        FragmentUtil.setContentFragment(R.id.main_fragment, activity, homeFragment, indexFragment);
                        indexFragment = homeFragment;
                        ImmersionBar.with(activity).reset().init();
                        ImmersionBar.with(activity).statusBarDarkFont(true, 0.2f).fitsSystemWindows(true).init();
                        break;
                    case R.id.main_rb_study:
                        ImmersionBar.with(activity).reset().init();
                        ImmersionBar.with(activity).transparentStatusBar().fitsSystemWindows(false).init();
                        FragmentUtil.setContentFragment(R.id.main_fragment, activity, studyFragment, indexFragment);
                        indexFragment = studyFragment;

                        break;
                    case R.id.main_rb_my:
                        ImmersionBar.with(activity).reset().init();
                        ImmersionBar.with(activity).transparentStatusBar().fitsSystemWindows(false).init();
                       // ImmersionBar.with(activity).init();
                        FragmentUtil.setContentFragment(R.id.main_fragment, activity, myFragment, indexFragment);
                        indexFragment = myFragment;

                        break;
                }
            }
        });
    }

    @Override
    public void onLoading() {

    }
}
