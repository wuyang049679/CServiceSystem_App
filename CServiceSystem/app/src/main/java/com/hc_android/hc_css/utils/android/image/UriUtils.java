package com.hc_android.hc_css.utils.android.image;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.text.TextUtils;

import com.hc_android.hc_css.api.Address;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.entity.FileEntity;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.FileUtils;
import com.hc_android.hc_css.utils.NullUtils;


/**
 *
 * Description: Uri转换工具类，优化内存占用
 * @author xiangyao
 * Created by 2019/3/6 11:16 AM
 */
public class UriUtils {
    private  Uri uri;
    private  String path ="";
    private  int resId = Constant.INTDEFAULT;
    private  static   UriUtils uriUtils;
    private String IMGPATH;

    private UriUtils(){

    }

    public  static UriUtils getUriInstance(){
        if(uriUtils==null){
            synchronized (UriUtils.class){
                if(uriUtils==null){
                    uriUtils=new UriUtils();
                }
            }
        }
        return  uriUtils;

    }

    public Uri getUri(int mode, String url) {
        path = url;

        return !TextUtils.isEmpty(path) ? UriTool(mode) : null;

    }

    public Uri getUri(int mResId) {
        resId = mResId;

        return !TextUtils.isEmpty(path) ? UriTool(Constant.LOCALRESOURCE) : null;
    }
    public  Uri getUri(String url) {
        path = url;


        return !TextUtils.isEmpty(path) ? FileUtils.isFileExists(url)?UriTool(Constant.PHONEIMAGEPATH):UriTool(Constant.ONLINEPIC): null;
    }

    /**
     * 消息图片路径
     * @param url
     * @return
     */
    public  Uri getUri(String url,String msgimg) {
        path = url;
        if (msgimg!=null&&msgimg.equals("msgimg")){
            IMGPATH=Address.MSGIMG_URL;
        }else if (msgimg!=null&&msgimg.equals("img")){
            IMGPATH=Address.IMG_URL;
        }else {
            Uri uri = UriTool(Constant.PHONEIMAGEPATH);
            if (uri!=null){
                return uri;
            } else {
                return Uri.parse("file://"+path);
            }
        }

        return !TextUtils.isEmpty(path) ? FileUtils.isFileExists(url)?UriTool(Constant.PHONEIMAGEPATH):UriTool(Constant.ONLINEPIC): null;
    }
    /**
     * 消息图片
     * @param
     * @return
     */
    public Uri getUri(FileEntity fileEntity) {
        path = fileEntity.getPicUrl();
        if (fileEntity.isLocalPath()){
            Uri uri = UriTool(Constant.PHONEIMAGEPATH);
            if (uri!=null){
                return uri;
            } else {
                return Uri.parse("file://"+path);
            }
        }else {
            if (fileEntity.getBucket()!=null&&fileEntity.getBucket().equals("msgimg")){
                IMGPATH=Address.MSGIMG_URL;
            }else{
                IMGPATH=Address.IMG_URL;
            }
            return !TextUtils.isEmpty(path) ? FileUtils.isFileExists(fileEntity.getPicUrl())?UriTool(Constant.PHONEIMAGEPATH):UriTool(Constant.ONLINEPIC): Uri.parse("");
        }
    }
    public static String getRealFilePath(Context context, final Uri uri) {
        if (null == uri) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if (scheme == null)
            data = uri.getPath();
        else if (ContentResolver.SCHEME_FILE.equals(scheme)) {
            data = uri.getPath();
        } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{MediaStore.Images.ImageColumns.DATA}, null, null, null);
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                    if (index > -1) {
                        data = cursor.getString(index);
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
    /**
     * 获取path
     * @param
     * @return
     */

    public  String getPath(String url,String msgimg){
        Uri uri=getUri(url,msgimg);
        return uri!=null?uri.toString():"";
    }
    public  Uri getUri(int mode, int localResource) {
        resId = localResource;
        return UriTool(mode);
    }

    private   Uri UriTool(int mode) {
        if (mode == Constant.ONLINEPIC && !NullUtils.isNull(path)) {
            if(path.startsWith("http")){
                uri = Uri.parse(path);
            }else {
                uri = Uri.parse(IMGPATH+path);
            }

        } else if (mode == Constant.LOCALRESOURCE && resId != Constant.INTDEFAULT) {
            uri = Uri.parse("res://" + BaseApplication.getContext().getPackageName() + "/" + resId);
        }else if(mode==Constant.PHONEIMAGEPATH&& !NullUtils.isNull(path)){

            try {
            Uri mediaUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            Cursor cursor = BaseApplication.getContext().getContentResolver().query(mediaUri,
                    null,
                    MediaStore.Images.Media.DISPLAY_NAME + "= ?",
                    new String[] {path.substring(path.lastIndexOf("/") + 1)},
                    null);

            Uri uri = null;
            if(cursor.moveToFirst()) {
                uri = ContentUris.withAppendedId(mediaUri,
                        cursor.getLong(cursor.getColumnIndex(MediaStore.Images.Media._ID)));
            }
            cursor.close();
            return uri;
            }catch (Exception e){

            }

        }

        return uri;
    }


}
