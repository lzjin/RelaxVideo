package com.fgkp.relax.mvp.base;

public class BasePresenter <V extends BaseView>{
    private V mView;
/*    protected CompositeSubscription mCompositeSubscription;

    //RXjava取消注册，以避免内存泄露
    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }

    //RXjava注册
    public void addSubscription(Subscription subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(subscriber);
    }*/
    /**
     * 绑定
     */
    public void attach(V view) {
        this.mView=view;
    }
    /**
     * 解绑，避免在加载过程未完成时，用户点击返回finish了当前页面
     * 如果finish当前页面就不用再回调到activity中
     */
    public void detach() {
        mView=null;
    }

    /**
     * 得到view
     * @return
     */
    public V getView() {
        return mView;
    }



}
