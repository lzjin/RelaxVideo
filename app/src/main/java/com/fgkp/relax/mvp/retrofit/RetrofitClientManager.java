package com.fgkp.relax.mvp.retrofit;
import com.fgkp.relax.mvp.utils.MLog;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lzj on 2018/4/28.
 *  网络引擎Retrofit
 */

public class RetrofitClientManager {
    /**
     * 服务器 Url
     */
    public static  String baseUrl = ApiService.Base_URL;

    private static final int DEFAULT_TIMEOUT = 5;
    public   ApiService apiService;
    private OkHttpClient.Builder  okHttpClient;
    private static RetrofitClientManager mRetrofit;
    public  JsDownloadListener mDownloadListener;

    public static RetrofitClientManager getInstance() {
        synchronized (RetrofitClientManager.class) {
            if (mRetrofit == null)
                mRetrofit = new RetrofitClientManager();
        }
        return mRetrofit;
    }


    private RetrofitClientManager() {
        //默认拦截日志
       /* HttpLoggingInterceptor logging = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override public void log(String message) {
                LogUtil.i("test","--------------------日志:"+message);
            }
        });
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);//Level中还有其他等级*/

        //手动创建一个OkHttpClient并设置超时时间
        okHttpClient = new OkHttpClient.Builder();
        okHttpClient.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        okHttpClient.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        okHttpClient.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
         if(MLog.isShowLogo){
             okHttpClient.addInterceptor(new LoggingInterceptor());//自定义拦截器
             //okHttpClient.addInterceptor(logging);//默认拦截器DownloadProgressInterceptor
         }
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(baseUrl)
                .build();
        apiService = retrofit.create(ApiService.class);
    }





}
