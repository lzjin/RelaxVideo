package com.fgkp.relax.mvp.config;

import android.app.Application;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.fgkp.relax.mvp.fresco.ImageLoaderConfig;

/**
 * Created by Administrator on 2018/9/26.
 *
 */

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
       // Fresco.initialize(this);
        //配置缓存的初始化
        Fresco.initialize(this, ImageLoaderConfig.getImagePipelineConfig(this));
    }
}
