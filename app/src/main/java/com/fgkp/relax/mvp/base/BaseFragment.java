package com.fgkp.relax.mvp.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fgkp.relax.mvp.config.ActivityController;
import com.fgkp.relax.mvp.utils.ToastUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 *
 */
public abstract class BaseFragment <P extends BasePresenter> extends Fragment implements BaseView {
    private View mRootView;
    private P mPresenter;
    private Unbinder mUnbinder;//释放资源用
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRootView = inflater.inflate(setContentViewResId(), container, false);
        mUnbinder = ButterKnife.bind(this, mRootView);
        initBase();
        initCreateView(savedInstanceState);
        return mRootView;
    }

    public abstract P createPresenter();//创建Presenter
    public abstract int setContentViewResId();//设置布局
    public abstract void initCreateView(Bundle savedInstanceState);//子类--初始化

    /**
     * 基类初始化
     */
    private void initBase() {


    }

    /**
     *  Toast短显示
     */
    public void showToast(String str){
        ToastUtil.showShort(getActivity(),str);
    }
    /**
     *  Toast长显示
     */
    public void showLongToast(String str){
        ToastUtil.showLong(getActivity(),str);
    }

    /**
     * 检测 字符是否长度为0
     * @param textView
     * @return
     */
    public boolean isEmptyTextView(TextView textView){
        if(TextUtils.isEmpty(textView.getText().toString().trim())){
            return true;
        }else {
            return false;
        }
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }
}
