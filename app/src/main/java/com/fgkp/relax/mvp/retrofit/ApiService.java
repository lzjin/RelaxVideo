package com.fgkp.relax.mvp.retrofit;


import com.fgkp.relax.mvp.bean.TestGetBean;
import com.fgkp.relax.mvp.bean.TestPostBean;

import java.util.Map;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by lzj on 2018/4/28.
 *   所有网络接口
 */

public interface ApiService {
    //基类Url            //"http://www.co360.cn/web/";   // "https://api.douban.com/"; http://www.danqiuedu.com/
   public static final String Base_URL = "https://api.douban.com/";
    //public static final String Base_URL = "http://www.co360.cn/web/";
    public static final String Base_URL2 = "http://img-1253650823.cosgz.myqcloud.com/";
    /**
     * get 有参 bean  1
     */
    @GET("v2/movie/top250")
    Observable<TestGetBean> getBean(@Header("Token") String token, @QueryMap Map<String, String> map);
    /**
     * get 有参 json  2
     */
    @GET("v2/movie/top250")
    Observable<ResponseBody> getJson(@Header("Token") String token, @QueryMap Map<String, String> map);
    //Observable<ResponseBody> getMovies(@Query("start") int start, @Query("count") int count);

    /**
     * post  json  3
     */
    @Headers("Content-type:application/x-www-form-urlencoded")
    @POST("api/pub/user/codelog")
    @FormUrlEncoded
    Observable<ResponseBody> postJson(@FieldMap Map<String, String> map);
    /**
     *
     * post  bean  4
     *
     */
    @Headers("Content-type:application/x-www-form-urlencoded")
    @POST("api/pub/user/codelog")
    @FormUrlEncoded
    Observable<TestPostBean> postBean(@FieldMap Map<String, String> map);

    /**
     * 上传图片
     * post 有请求头token
     */
    @POST("api/pub/upload")
    @Multipart
    Observable<ResponseBody> upLoadImg(@Header("Token") String token, @Part() MultipartBody multipartBody);

    /**
     * 多文件上传
     * @param token
     * @param maps
     * @param file
     * @return
     */
    @POST("uapi/api/pri/teacher/publish/work")
    @Multipart
    Observable<ResponseBody> publishHomework(@Header("Token") String token, @PartMap Map<String, RequestBody> maps, @Part() MultipartBody.Part[] file);

    /**
     * 下载文件  带进度条
     *get
     */
    @Streaming
    @GET
    Observable<ResponseBody> getDownloadApp(@Url String url);

    /**
     * 下载文件
     *get
     */
    @Streaming
    @GET
    Observable<ResponseBody> getDownloadApps(@Url String url);









}
