package com.fgkp.relax.mvp.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.facebook.drawee.view.SimpleDraweeView;
import com.fgkp.relax.mvp.R;
import com.fgkp.relax.mvp.base.BaseActivity;
import com.fgkp.relax.mvp.config.Constants;
import com.fgkp.relax.mvp.fresco.ImageLoader;
import com.fgkp.relax.mvp.presenter.TestPresenter;
import com.fgkp.relax.mvp.utils.MLog;
import com.fgkp.relax.mvp.view.ITestView;
import com.jph.takephoto.app.TakePhoto;
import com.jph.takephoto.app.TakePhotoImpl;
import com.jph.takephoto.model.InvokeParam;
import com.jph.takephoto.model.TContextWrap;
import com.jph.takephoto.model.TResult;
import com.jph.takephoto.permission.InvokeListener;
import com.jph.takephoto.permission.PermissionManager;
import com.jph.takephoto.permission.TakePhotoInvocationHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by lzj on 2018/10/4.
 */

public class TestActivity extends BaseActivity<TestPresenter> implements ITestView, InvokeListener,TakePhoto.TakeResultListener {

    @BindView(R.id.bt_g_bean)
    Button btGBean;
    @BindView(R.id.bt_g_json)
    Button btGJson;
    @BindView(R.id.bt_p_json)
    Button btPJson;
    @BindView(R.id.bt_down)
    Button btDown;
    @BindView(R.id.drawee_img)
    SimpleDraweeView draweeImg;
    @BindView(R.id.bt_p_bean)
    Button btPBean;
    @BindView(R.id.bt_up)
    Button btUp;

    TakePhoto takePhoto;
    InvokeParam invokeParam;

    @BindView(R.id.progress)
    ProgressBar progressBar;

    private TestPresenter p;

    @Override
    public TestPresenter createPresenter() {
        p = new TestPresenter(this);
        return p;
    }

    @Override
    public void setContentViewResId() {
        setContentView(R.layout.activity_test);
    }

    @Override
    public void initCreate(Bundle savedInstanceState) {
        String url = "http://www.people.com.cn/mediafile/pic/20161022/76/4315084153778263996.jpg";
        ImageLoader.loadImageBlur(draweeImg, url);//高斯模糊
        getTakePhoto().onCreate(savedInstanceState);//191行
    }

    /**
     *  得到实列 001
     */
    public TakePhoto getTakePhoto(){
        if (takePhoto==null){
            takePhoto= (TakePhoto) TakePhotoInvocationHandler.of(this).bind(new TakePhotoImpl(this,this));
        }
        return takePhoto;
    }


    @Override
    public void onLoading() {

    }

    @Override
    public void colseLoading() {

    }

    @Override
    public void onError(String msg) {
        showToast("失败");
    }

    @Override
    public void loginSuccess(Object o) {
      showToast(""+o);
        MLog.i("test","--------act成功----"+o);
    }


    @OnClick({R.id.bt_g_bean, R.id.bt_g_json, R.id.bt_p_json,R.id.bt_p_bean, R.id.bt_down, R.id.bt_up})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_g_bean://get返回bean
                Map<String, String> map1 = new HashMap<>();
                map1.put("start","5");
                map1.put("count","1");
                p.getBean("",map1);
                break;
            case R.id.bt_g_json://get返回json
                Map<String, String> map2 = new HashMap<>();
                map2.put("start","5");
                map2.put("count","1");
                p.getJson("",   map2);
                break;
            case R.id.bt_p_json://post返回json
                Map<String, String> map3 = new HashMap<>();
                map3.put("account","18482128607");
                map3.put("code","111111");
                p.postJson("",map3);
                break;
            case R.id.bt_p_bean://post返回bean
                Map<String, String> map4 = new HashMap<>();
                map4.put("account","18482128607");
                map4.put("code","111111");
                p.postBean("",map4);
                break;
            case R.id.bt_down://get下载
                //必须现先有写入权限，7.0安装调用有问题，使用照相框架xml配置即可
                  String apkPath=Constants.SOFT_FILE_PATH+Constants.HEAD_DOWN_NAME;
                  p.getDown("http://img-1253650823.cosgz.myqcloud.com/dqonline.apk",apkPath,Constants.APP_FILE_NAME_APK,progressBar);
                break;
            case R.id.bt_up://post上传
                p.postUp("","");
                break;
        }
    }




    @Override
    public void takeSuccess(TResult result) {
        ImageLoader.loadImage(draweeImg,result.getImages().get(0).getCompressPath());
       // File_Utils.copyFile(result.getImages().get(0).getCompressPath(),photoFile.getAbsolutePath());
      //  loginModel.upLoadImg("vRgLZ1h3us5qiwN+Vu/GReeiDoAEF40s",filePath+fileName+".png");
    }

    @Override
    public void takeFail(TResult result, String msg) {
        showToast("拍照失败,请重试!");
    }

    @Override
    public void takeCancel() {
        showToast("已取消拍照!");
    }
    /**
     *  相机权限处理
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getTakePhoto().onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //以下代码为处理Android6.0、7.0动态权限所需
        PermissionManager.TPermissionType type=PermissionManager.onRequestPermissionsResult(requestCode,permissions,grantResults);
        PermissionManager.handlePermissionsResult(this,type,invokeParam,this);
    }
    @Override
    public PermissionManager.TPermissionType invoke(InvokeParam invokeParam) {
        PermissionManager.TPermissionType type=PermissionManager.checkPermission(TContextWrap.of(this),invokeParam.getMethod());
        if(PermissionManager.TPermissionType.WAIT.equals(type)){
            this.invokeParam=invokeParam;
        }
        return type;
    }
}
