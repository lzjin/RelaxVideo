package com.fgkp.relax.mvp.fresco;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.facebook.common.references.CloseableReference;
import com.facebook.common.util.UriUtil;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.common.RotationOptions;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.BasePostprocessor;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.util.concurrent.Executor;


/**
 *
 * 图片加载方式封装
 */

public class ImageLoader {
    /**
     * 根据url加载图片
     * 主要方式之一
     */
    public static void loadImage(SimpleDraweeView simpleDraweeView, String url) {
        if(url != null){
            simpleDraweeView.setImageURI(Uri.parse(url));
        }
    }

    //从本地文件（比如SDCard上）加载图片
    public static void loadFile(final SimpleDraweeView draweeView, String filePath, final int reqWidth, final int reqHeight) {
        Uri uri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_FILE_SCHEME)
                .path(filePath)
                .build();
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setRotationOptions(RotationOptions.autoRotate())
                .setLocalThumbnailPreviewsEnabled(true)
                .setResizeOptions(new ResizeOptions(reqWidth, reqHeight))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(draweeView.getController())
                .setControllerListener(new BaseControllerListener<ImageInfo>() {
                    @Override
                    public void onFinalImageSet(String id, @Nullable ImageInfo imageInfo, @Nullable Animatable anim) {
                        if (imageInfo == null) {
                            return;
                        }

                        ViewGroup.LayoutParams vp = draweeView.getLayoutParams();
                        vp.width = reqWidth;
                        vp.height = reqHeight;
                        draweeView.requestLayout();
                    }
                })
                .build();
        draweeView.setController(controller);
    }

    //从本地资源（Resources）加载图片
    public static void loadDrawable(SimpleDraweeView draweeView, int resId) {
        Uri uri = new Uri.Builder()
                .scheme(UriUtil.LOCAL_RESOURCE_SCHEME)
                .path(String.valueOf(resId))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setOldController(draweeView.getController())
                .build();
        draweeView.setController(controller);
    }

    /**
     * 对图片进行性高斯模糊处理  1
     * @param draweeView
     * @param url
     */
    public static void loadImageBlur(final SimpleDraweeView draweeView, String url) {
        blurImage(draweeView, url, new BasePostprocessor() {
            @Override
            public String getName() {
                return "blurPostprocessor";
            }

            @Override
            public void process(Bitmap bitmap) {
                BitmapBlurHelper.blur(bitmap, 35, true);
            }
        });
    }

    /**
     * 上述调用的方法 2
     * @param simpleDraweeView
     * @param url
     * @param processor
     */
    public static void blurImage(SimpleDraweeView simpleDraweeView, String url, BasePostprocessor processor) {
        if (TextUtils.isEmpty(url)) {
            return;
        }

        Uri uri = Uri.parse(url);
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setRotationOptions(RotationOptions.autoRotate())
                .setPostprocessor(processor)
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(simpleDraweeView.getController())
                .build();
        simpleDraweeView.setController(controller);
    }

    private static final Executor UiExecutor = new Executor() {
        final Handler sHandler = new Handler(Looper.getMainLooper());

        @Override
        public void execute(Runnable runnable) {
            sHandler.post(runnable);
        }
    };

    public static void displayImageView(final ImageView view, String url) {
        final ImageRequest request = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(url))
                .build();
        Fresco.getImagePipeline()
                .fetchDecodedImage(request, view.getContext().getApplicationContext())
                .subscribe(new BaseBitmapDataSubscriber() {
                    @Override
                    protected void onNewResultImpl(Bitmap bitmap) {
                        if (bitmap != null) {
                            view.setImageBitmap(bitmap);
                        }
                    }

                    @Override
                    protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {

                    }
                }, UiExecutor);
    }

    public static void getFrescoBitmap(String url, Context context, final ResultBitmap resultBitmap){
        final ImageRequest request = ImageRequestBuilder
                .newBuilderWithSource(Uri.parse(url))
                .build();
        Fresco.getImagePipeline()
                .fetchDecodedImage(request, context)
                .subscribe(new BaseBitmapDataSubscriber() {
                    @Override
                    protected void onNewResultImpl(Bitmap bitmap) {
                        if (bitmap != null) {
                            resultBitmap.getBitmap(bitmap);
                        }
                    }

                    @Override
                    protected void onFailureImpl(DataSource<CloseableReference<CloseableImage>> dataSource) {

                    }
                }, UiExecutor);
    }

    public interface ResultBitmap{
        void getBitmap(Bitmap bitmap);
    }


    //从内存缓存中移除指定图片的缓存
    public static void removeMemoryCache(String originalUrl){
        if (!TextUtils.isEmpty(originalUrl)) {
            ImagePipeline imagePipeline = Fresco.getImagePipeline();
            Uri uri = Uri.parse(originalUrl);
            if (imagePipeline.isInBitmapMemoryCache(uri)) {
                imagePipeline.evictFromMemoryCache(uri);
            }
        }
    }

    //从磁盘缓存中移除指定图片的缓存
    public static void removeDiskCache(String originalUrl) {
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        Uri uri = Uri.parse(originalUrl);
        // 下面的操作是异步的
        if (imagePipeline.isInDiskCacheSync(uri)) {
            imagePipeline.evictFromDiskCache(uri);
        }
    }
    //清空内存缓存
    public static void clearMemoryCache(){
        Fresco.getImagePipeline().clearMemoryCaches();
    }
    //清空磁盘缓存
    public static void clearDiskCache(){
        Fresco.getImagePipeline().clearDiskCaches();
    }
    //清空缓存（内存缓存 + 磁盘缓存）
    public static void clearAllCache(){
        Fresco.getImagePipeline().clearCaches();
    }

    /**
     * 在列表视图滚动时，不加载图片，等滚动停止后再开始加载图片，提升列表视图的滚动流畅度。
     * // 需要暂停网络请求时调用
     */
    public static void pauseLoad(){
        Fresco.getImagePipeline().pause();
    }

    // 需要恢复网络请求时调用
    public static void resumeLoad(){
        Fresco.getImagePipeline().resume();
    }



}
