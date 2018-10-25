package com.fgkp.relax.mvp.model;

import android.content.Context;
import android.util.Log;
import android.widget.ProgressBar;

import com.fgkp.relax.mvp.base.BaseModel;
import com.fgkp.relax.mvp.bean.TestGetBean;
import com.fgkp.relax.mvp.bean.TestPostBean;
import com.fgkp.relax.mvp.config.Constants;
import com.fgkp.relax.mvp.retrofit.JsDownloadListener;
import com.fgkp.relax.mvp.retrofit.ProgressSubscriber;
import com.fgkp.relax.mvp.retrofit.RetrofitClientDownload;
import com.fgkp.relax.mvp.utils.FileUtil;
import com.fgkp.relax.mvp.utils.MLog;
import com.fgkp.relax.mvp.utils.SystemUtil;
import com.fgkp.relax.mvp.utils.ToastUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Subscriber;

/**
 * Created by lzj on 2018/10/4.
 */
@SuppressWarnings("unchecked")
public class TestModelImpl extends BaseModel{
   private static String tag="test";
   public Context mContext;

    public TestModelImpl(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * get  bean
     */
    public void getBean(String token,final Map<String,String> map,final OnResultListener listener) {
        apiService.getBean(token,map).compose(schedulersTransformer())
                .subscribe(new ProgressSubscriber(mContext) {
                    @Override
                    protected void _onNext(Object o) {
                        MLog.i("test", "MovieModelImpl-------->>onSuccess=");
                        TestGetBean bean= (TestGetBean) o;
                        MLog.i("test", "MovieModelImpl----解析---->>onSuccess="+bean.getTitle());
                        listener.onSuccess(bean.getTitle());

                    }
                    @Override
                    protected void _onError(String message) {
                        listener.onFailure(message);
                    }
                });
    }

    /**
     * get  json
     */
    public void getJson(String token,final Map<String,String> map,final OnResultListener listener) {
        apiService.getJson(token,map).compose(schedulersTransformer())
                .subscribe(new ProgressSubscriber(mContext) {
                    @Override
                    protected void _onNext(Object o) {
                        MLog.i("test", "MovieModelImpl-------->>onSuccess=");
                        ResponseBody responseBody = (ResponseBody) o;
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(responseBody.string());
                            //MLog.iJsonFormat(jsonObject.toString());
                            //MsgBean bean= GsonUtil.getUser(jsonObject.toString(), MsgBean.class);
                            MLog.i("test", "MovieModelImpl---解析----->>onSuccess="+jsonObject.getString("total"));
                            listener.onSuccess(jsonObject.getString("total"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    protected void _onError(String message) {
                        listener.onFailure(message);
                    }
                });
    }

    /**
     *   post bean
     */
    public void postBean(String token,final Map<String,String> map,final OnResultListener listener) {
        apiService.postBean(map).compose(schedulersTransformer())
                .subscribe(new ProgressSubscriber(mContext) {
                    @Override
                    protected void _onNext(Object o) {
                        MLog.i("test", "MovieModelImpl-------->>onSuccess=");
                        TestPostBean bean= (TestPostBean) o;
                        MLog.i("test", "MovieModelImpl----解析---->>onSuccess="+bean.getMessage());
                        listener.onSuccess(bean.getMessage());
                    }
                    @Override
                    protected void _onError(String message) {
                        listener.onFailure(message);
                    }
                });


    }


    /**
     *   post json
     */
    public void postJson(String token,final Map<String,String> map,final OnResultListener listener) {
        apiService.postJson(map).compose(schedulersTransformer())
                .subscribe(new ProgressSubscriber(mContext) {
                    @Override
                    protected void _onNext(Object o) {
                        MLog.i("test", "MovieModelImpl-------->>onSuccess=");
                        ResponseBody responseBody = (ResponseBody) o;
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(responseBody.string());
                            //MLog.iJsonFormat(jsonObject.toString());
                            //MsgBean bean= GsonUtil.getUser(jsonObject.toString(), MsgBean.class);
                            MLog.i("test", "MovieModelImpl---解析----->>onSuccess="+jsonObject.getString("message"));
                            listener.onSuccess(jsonObject.getString("message"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    protected void _onError(String message) {
                        listener.onFailure(message);
                    }
                });


    }

    /**
     * 下载 不带进度条
     */
    public void  getDownloadFiles(final String url,final String pathDown, final String saveName,final ProgressBar progressBar,
                                  final OnResultListener listener){
        apiService.getDownloadApps(url).compose(schedulersTransformer())
                .subscribe(new Subscriber<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        Log.i("test", "----------------APP下载完成 ");
                        SystemUtil.installApk(mContext,pathDown, Constants.APP_FILE_NAME_APK);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("test", "----------------APP下载失败: " + e.toString());
                        listener.onFailure("失败");
                    }

                    @Override
                    public void onNext(ResponseBody body) {
                        InputStream inputStream = null;
                        inputStream = body.byteStream();
                        try {
                            FileUtil.writeFile(inputStream ,FileUtil.mkFile(pathDown,saveName));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        listener.onSuccess("成功。");
                    }
                });
    }
    /**
     *  上传
     *  post
     * @param token
     * @param imagePath
     * 参考 https://blog.csdn.net/weixin_41307234/article/details/78948907
     */
    public void upLoadImg(String token,String imagePath,final OnResultListener listener){
//        MultipartBody.Part[] part = new MultipartBody.Part[fileList.size()];
//        for (int i = 0; i < part.length; i++) {
//            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), fileList.get(i));
//            part[i] = MultipartBody.Part.createFormData("attachment", fileList.get(i).getName(), requestFile);
//        }
        File file = new File(imagePath);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody multipartBody =new MultipartBody.Builder()
                //注意，后台需要的参数名与参数值
                .addFormDataPart("file_name",file.getName(),requestFile)
                .build();

        apiService.upLoadImg(token,multipartBody).compose(schedulersTransformer())
                .subscribe(new ProgressSubscriber(mContext) {
                    @Override
                    protected void _onNext(Object o) {
                        ResponseBody responseBody = (ResponseBody) o;
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(responseBody.string());
                            // MLog.iJsonFormat(jsonObject.toString());
                           // EvaluationBean bean= GsonUtil.getUser(jsonObject.toString(), EvaluationBean.class);
                            MLog.i("test", "MovieModelImpl-------->>onSuccess=");
                            listener.onSuccess(o);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    @Override
                    protected void _onError(String message) {
                         listener.onFailure(message);
                    }
                });
    }

    /**
     * 下载  带进度条
     */
    public void  getDownloadFile(final String url,final String pathDown, final String saveName,final ProgressBar progressBar){
        final JsDownloadListener listener=new JsDownloadListener() {
            @Override
            public void onStartDownload() {
                Log.i("test", "----------------APP下载开始 ");
            }

            @Override
            public void onProgress(int progress) {
                Log.i("test", "-----------------APP下载进度: " + progress);
                progressBar.setProgress(progress);
            }

            @Override
            public void onFinishDownload() {
                Log.i("test", "----------------APP下载完成 ");
                SystemUtil.installApk(mContext,pathDown, Constants.APP_FILE_NAME_APK);
            }

            @Override
            public void onFail(String errorInfo) {
                ToastUtil.showShort(mContext,"失败");
                Log.i("test", "----------------APP下载失败: " + errorInfo);
            }
        };

        RetrofitClientDownload downloadUtils=new RetrofitClientDownload(listener );
        downloadUtils.downApkFile(url,pathDown, saveName, new Subscriber() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onFail(e.toString());
            }

            @Override
            public void onNext(Object o) {
                listener.onFinishDownload();
            }
        });
    }
}
