package com.fgkp.relax.mvp.view;

import com.fgkp.relax.mvp.base.BaseView;

/**
 * Created by lzj on 2018/10/4.
 */

public interface ITestView extends BaseView{

    void colseLoading();//关闭
    void onError(String msg);//通用失败
    void loginSuccess(Object o);//成功
}
