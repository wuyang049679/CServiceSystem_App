package com.hc_android.hc_css.utils.android.image;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.hc_android.hc_css.api.Address;
import com.hc_android.hc_css.entity.FileEntity;
import com.hc_android.hc_css.utils.android.SystemUtils;

public class FrescoUtils {





    /**
     * 通过imageWidth 的宽度，自动适应高度
     * * @param simpleDraweeView view
     * * @param imagePath  Uri
     * * @param imageWidth width
     */
    public static void setControllerListener(final SimpleDraweeView simpleDraweeView, FileEntity fileEntity, Context mContext) {


        setImageLayout(mContext,simpleDraweeView, fileEntity);
        String imagePath = null;
        if (fileEntity.getType().contains("video")) {
            imagePath = Address.FILE_URL + fileEntity.getFileUrl() + "?vframe/png/offset/0";//视频缩略图
        }
        if (fileEntity.getType().contains("image")) {
            int width = simpleDraweeView.getLayoutParams().width;
            int height = simpleDraweeView.getLayoutParams().height;
            imagePath = fileEntity.getPicUrl() + "?imageView2/2/w/" + width / 2 + "/h/" + height / 2;//第一次加载图片缩略图(压缩图片显示// )
        }
//        RoundedCorners roundedCorners = new RoundedCorners(6);
//        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
//        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
//        Glide.with(mContext).load(Address.IMG_URL + fileEntity.getPicUrl()).apply(options).thumbnail(0.1f).into(simpleDraweeView);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setLowResImageRequest(fileEntity.isLocalPath()?null:ImageRequest.fromUri(UriUtils.getUriInstance().getUri(imagePath,fileEntity.getBucket())))//加载缩略图
                .setImageRequest(ImageRequest.fromUri(UriUtils.getUriInstance().getUri(fileEntity)))//加载原图
                .setAutoPlayAnimations(true)
                .build();
        simpleDraweeView.setController(controller);

    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * 根据图片大小设置image显示大小
     *
     * @param draweeView
     * @param fileEntity
     */
    public static void setImageLayout(Context mContext, View draweeView, FileEntity fileEntity) {

        float width = 0;
        float height = 0;
        if (fileEntity.getType().contains("video") && fileEntity.getThumbImg() != null && fileEntity.getThumbImg().getWidth() != 0 && fileEntity.getThumbImg().getHeight() != 0) {
            width = fileEntity.getThumbImg().getWidth();
            height = fileEntity.getThumbImg().getHeight();

        }
        if (fileEntity.getType().contains("image") && fileEntity.getWidth() != 0 && fileEntity.getHeight() != 0) {
            width = fileEntity.getWidth();
            height = fileEntity.getHeight();

        }
        int[] screenDispaly = SystemUtils.getScreenDispaly(mContext);
        float containerWidth = (screenDispaly[0]) * 2 / 5;//不超过屏幕宽度的2/5
        float containerHeight = (screenDispaly[1]) * 1 / 2;//不超过屏幕高度1/2

        if (width > containerWidth) {

            height = containerWidth / width * height;
            width = containerWidth;
        }

        if (height > containerHeight) {
            width = containerHeight / height * width;
            height = containerHeight;
        }
        ViewGroup.LayoutParams layoutParams = draweeView.getLayoutParams();
        layoutParams.width = (int) width;
        layoutParams.height = (int) height;
        draweeView.setLayoutParams(layoutParams);

    }

    /**
     * 支持gif图片
     * @param draweeView
     * @param uri
     */
    public static void setRoundGif(SimpleDraweeView draweeView, Uri uri){
        DraweeController draweeController =
                Fresco.newDraweeControllerBuilder()
                        .setUri(uri)
                        .setAutoPlayAnimations(true) // 设置加载图片完成后是否直接进行播放
                        .build();
        draweeView.setController(draweeController);
    }
}
