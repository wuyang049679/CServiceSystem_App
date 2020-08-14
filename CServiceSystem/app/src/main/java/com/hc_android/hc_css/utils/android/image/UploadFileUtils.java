package com.hc_android.hc_css.utils.android.image;

import android.util.Log;

import com.hc_android.hc_css.entity.FileEntity;
import com.hc_android.hc_css.entity.MessageEntity;
import com.hc_android.hc_css.utils.JsonParseUtils;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import java.io.File;
import java.util.Date;

public class UploadFileUtils {


    private String TAG="wy_activity";
    private UploadManager uploadManager;

    public UploadFileUtils() {
        //config配置上传参数
        Configuration configuration = new Configuration.Builder()
                .connectTimeout(10)
                .responseTimeout(60)
                //.zone(zone)
                //.dns(buildDefaultDns())//指定dns服务器
                .responseTimeout(60).build();

        if (this.uploadManager == null) {
            //this.uploadManager = new UploadManager(fileRecorder);
            this.uploadManager = new UploadManager(configuration, 3);
        }

    }

    public static UploadFileUtils getInstance() {


        return new UploadFileUtils();
    }

    /**
     * 头像上传
     * @param uploadToken
     * @param filePath
     * @param upCompletionHandler
     */
    public void upload(String uploadToken,String filePath,UpCompletionHandler upCompletionHandler){
        UploadOptions opt = new UploadOptions(null, null, true, new UpProgressHandler() {
            @Override
            public void progress(String key, double percent) {
                Log.i(TAG, "percent:" + percent);
            }
        }, null);
        File uploadFile = new File(filePath);
        this.uploadManager.put(uploadFile, null, uploadToken,upCompletionHandler, opt);
    }
    public void upload(String uploadToken, MessageEntity.MessageBean messageBean,UpCompletionHandler upCompletionHandler) {
        final long startTime = System.currentTimeMillis();
        //可以自定义zone
        //Zone zone = new FixedZone(new String[]{"domain1","domain2"});

        //手动指定上传区域
        //Zone zone = FixedZone.zone0;//华东

        //配置断点续传
        /**
         FileRecorder fileRecorder = null;
         try {
         fileRecorder = new FileRecorder("directory");
         } catch (IOException e) {
         e.printStackTrace();
         }
         */


        UploadOptions opt = new UploadOptions(null, null, true, new UpProgressHandler() {
            @Override
            public void progress(String key, double percent) {
                Log.i(TAG, "percent:" + percent);
            }
        }, null);
        FileEntity fileEntity= JsonParseUtils.parseToObject(messageBean.getContents(),FileEntity.class);
        if (fileEntity!=null&&fileEntity.getFileUrl()!=null) {
            File uploadFile = new File(fileEntity.getFileUrl());
            long time = new Date().getTime();
            this.uploadManager.put(uploadFile, null, uploadToken, upCompletionHandler, opt);
        }
    }

//    private void upload(String uploadToken, MessageEntity.MessageBean messageBean) {
//        final long startTime = System.currentTimeMillis();
//        //可以自定义zone
//        //Zone zone = new FixedZone(new String[]{"domain1","domain2"});
//
//        //手动指定上传区域
//        //Zone zone = FixedZone.zone0;//华东
//
//        //配置断点续传
//        /**
//         FileRecorder fileRecorder = null;
//         try {
//         fileRecorder = new FileRecorder("directory");
//         } catch (IOException e) {
//         e.printStackTrace();
//         }
//         */
//
//        //config配置上传参数
//        Configuration configuration = new Configuration.Builder()
//                .connectTimeout(10)
//                //.zone(zone)
//                //.dns(buildDefaultDns())//指定dns服务器
//                .responseTimeout(60).build();
//
//        if (this.uploadManager == null) {
//            //this.uploadManager = new UploadManager(fileRecorder);
//            this.uploadManager = new UploadManager(configuration, 3);
//        }
//
//        UploadOptions opt = new UploadOptions(null, null, true, new UpProgressHandler() {
//            @Override
//            public void progress(String key, double percent) {
//                Log.i(TAG, "percent:" + percent);
//            }
//        }, null);
//        FileEntity fileEntity=JsonParseUtils.parseToObject(messageBean.getContents(),FileEntity.class);
//        File uploadFile = new File(fileEntity.getFileUrl());
//        long time = new Date().getTime();
//        this.uploadManager.put(uploadFile, fileEntity.getKey(), uploadToken,
//                new UpCompletionHandler() {
//                    @Override
//                    public void complete(String key, ResponseInfo respInfo,
//                                         JSONObject jsonData) {
//                        long endTime = System.currentTimeMillis();
//                        if (respInfo.isOK()) {
//                            try {
//                                Log.e("zw", jsonData.toString() + respInfo.toString());
//                                Log.i(TAG,"--------------------------------UPTime/ms: " + (endTime - startTime));
//                                String fileKey = jsonData.getString("key");
//                                String fileHash = jsonData.getString("hash");
//                                Log.i(TAG,"File Size: " +uploadFile.length());
//                                Log.i(TAG,"File Key: " + fileKey);
//                                Log.i(TAG,"File Hash: " + fileHash);
//                                Log.i(TAG,"X-Reqid: " + respInfo.reqId);
//                                Log.i(TAG,"X-Via: " + respInfo.xvia);
//                                Log.i(TAG,messageBean.getKey()+"--------------------------------" + "\n上传成功");
//
//                            } catch (JSONException e) {
//
//                                if (jsonData != null) {
//                                    Log.i(TAG,jsonData.toString());
//                                }
//                                Log.i(TAG,"--------------------------------" + "\n上传失败");
//                            }
//                        } else {
//                            Log.i(TAG,respInfo.toString());
//                            if (jsonData != null) {
//                                Log.i(TAG,jsonData.toString());
//                            }
//                            Log.i(TAG,"--------------------------------" + "\n上传失败");
//                        }
//                    }
//
//                }, opt);
//    }
}
