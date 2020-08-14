package com.hc_android.hc_css.utils.thread;

import android.content.Context;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.hc_android.hc_css.api.ApiManager;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.base.RxSubscribe;
import com.hc_android.hc_css.entity.FileEntity;
import com.hc_android.hc_css.entity.MessageDialogEntity;
import com.hc_android.hc_css.entity.MessageEntity;
import com.hc_android.hc_css.entity.SendEntity;
import com.hc_android.hc_css.entity.TokenEntity;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.DateUtils;
import com.hc_android.hc_css.utils.JsonParseUtils;
import com.hc_android.hc_css.utils.android.app.CacheData;
import com.hc_android.hc_css.utils.android.image.QiniuUploadManager;
import com.hc_android.hc_css.utils.socket.MessageEvent;
import com.hc_android.hc_css.utils.socket.MessageEventType;
import com.hw.videoprocessor.VideoProcessor;
import com.hw.videoprocessor.util.VideoProgressListener;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.hc_android.hc_css.utils.Constant.UI_FRESH;
import static com.hc_android.hc_css.utils.Constant._IMAGE;
import static com.hc_android.hc_css.utils.Constant._TEXT;
import static com.hc_android.hc_css.utils.Constant._VIDEO;
import static com.hc_android.hc_css.utils.Constant._VOICE;

public class ExecutorServiceManager {
    static String TAG = "wy_activity";
    static ExecutorService executorService = Executors.newFixedThreadPool(1);//维护一个线程，等待上一个执行完成在继续下一个

    public static void startExecutor(MessageEntity.MessageBean messageBean, MessageDialogEntity.DataBean.ListBean itemBean, ExecutorListener executorListeners) {
        executorService.execute(new CRunnable(messageBean, itemBean));
    }

    public static class CRunnable implements Runnable {

        private MessageEntity.MessageBean messageBean;
        private MessageDialogEntity.DataBean.ListBean itembean;

        public CRunnable(MessageEntity.MessageBean messageBeans, MessageDialogEntity.DataBean.ListBean itembeans) {

            this.messageBean = messageBeans;
            this.itembean = itembeans;

        }

        @Override
        public void run() {
            videoProcessor(messageBean, itembean);
        }

    }

    /**
     * 压缩处理
     */
    public static void videoProcessor(MessageEntity.MessageBean messageBeans, MessageDialogEntity.DataBean.ListBean itembeans) {
        FileEntity fileEntity = JsonParseUtils.parseToObject(messageBeans.getContents(), FileEntity.class);
        File downloadFile = new File(Environment.getExternalStorageDirectory(), "hecong/chatfile");
        if (!downloadFile.mkdirs()) {
            try {
                downloadFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                eventPost(messageBeans, itembeans, false);
            }
        }
        Uri selectedVideoUri = Uri.parse(fileEntity.getFileUrl());
        String destinationDirectory = downloadFile.getAbsolutePath() + "/" + DateUtils.getStringTime() + ".mp4";
        boolean isSuccess = true;
        try {

            MediaMetadataRetriever retriever = new MediaMetadataRetriever();
            retriever.setDataSource(fileEntity.getFileUrl());

            int bitrate = Integer.parseInt(retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_BITRATE));

            VideoProcessor.processor(BaseApplication.getContext())
                    .input(selectedVideoUri)
                    .output(destinationDirectory)
                    .startTimeMs(0)
                    .endTimeMs((int) fileEntity.getDuration())
                    .bitrate(bitrate / 8)
                    .progressListener(new VideoProgressListener() {
                        @Override
                        public void onProgress(float progress) {
                            Log.i(TAG, "VideoProcessor:onProgress" + progress);

                        }
                    })
                    .process();

        } catch (Exception e) {
            isSuccess = false;
            e.printStackTrace();
            eventPost(messageBeans, itembeans, false);
        }
        if (isSuccess) {
            fileEntity.setFileUrl(destinationDirectory);
            messageBeans.setContents(JsonParseUtils.parseToJson(fileEntity));
            getServiceToken(messageBeans, itembeans);
        }


    }

    /**
     * 上传文件
     */
    private static void getServiceToken(MessageEntity.MessageBean messageBean, MessageDialogEntity.DataBean.ListBean itembeans) {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Constant.REQUEST_TYPE, Constant.STANDARD);
        hashMap.put(Constant.KEY_HASH, BaseApplication.getUserEntity().getHash());
        hashMap.put("type", "file");
        ApiManager.getApistore().getToken(hashMap).subscribeOn(Schedulers.io())
                .subscribe(new RxSubscribe<TokenEntity.DataBean>() {
                    @Override
                    protected void onSuccess(TokenEntity.DataBean dataBean) {
                        Log.i(TAG, "getToken:onFinish" + dataBean.getToken());
                        QiniuUploadManager manager = QiniuUploadManager.getInstance(BaseApplication.getContext());
                        FileEntity fileEntity = JsonParseUtils.parseToObject(messageBean.getContents(), FileEntity.class);

                        QiniuUploadManager.QiniuUploadFile param = new QiniuUploadManager.QiniuUploadFile(fileEntity.getFileUrl(), null, fileEntity.getType(), dataBean.getToken());
                        manager.upload(param, new QiniuUploadManager.OnUploadListener() {
                            @Override
                            public void onStartUpload() {
                                Log.e(TAG, "onStartUpload");
                            }

                            @Override
                            public void onUploadProgress(String key, double percent) {
                                Log.e(TAG, "onUploadProgress:" + key + percent);
                            }

                            @Override
                            public void onUploadFailed(String key, String err) {
                                Log.e(TAG, "onUploadFailed:" + err);
                                eventPost(messageBean, itembeans, false);
                            }

                            @Override
                            public void onUploadBlockComplete(String key) {
                                Log.e(TAG, "onUploadBlockComplete" + key);
                                fileEntity.setFileUrl(key);
                                fileEntity.setLocalPath(false);
                                String s = JsonParseUtils.parseToJson(fileEntity);
                                if (s!=null)messageBean.setContents(s);
                                toSendMsg(messageBean, key, itembeans);

                            }

                            @Override
                            public void onUploadCompleted() {
                                Log.e(TAG, "onUploadCompleted");
                            }

                            @Override
                            public void onUploadCancel() {
                                Log.e(TAG, "onUploadCancel");
                            }
                        });

                    }

                    @Override
                    protected void onFailed(int code, String msg) {
                        eventPost(messageBean, itembeans, false);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                });
    }

    /**
     * IFFmpegListener监听接口
     */
    public static interface ExecutorListener {

        /**
         * 执行完成
         */
        void onFinish(MessageEntity.MessageBean messageBean);

        /**
         * 进度回调
         *
         * @param progress     执行进度
         * @param progressTime 执行的时间，相对于总时间 单位：微秒
         */
        void onProgress(int progress, long progressTime);

        /**
         * 执行取消
         */
        void onCancel(MessageEntity.MessageBean messageBean);

        /**
         * 执行出错
         *
         * @param message
         */
        void onError(String message, MessageEntity.MessageBean messageBean);

    }

    /**
     * 发送消息
     *
     * @param messageBean
     * @param key
     * @param itembean
     */
    public static void toSendMsg(MessageEntity.MessageBean messageBean, String key, MessageDialogEntity.DataBean.ListBean itembean) {

        FileEntity fileEntity = JsonParseUtils.parseToObject(messageBean.getContents(), FileEntity.class);
        String content = null;
        switch (messageBean.getType()) {
            case _IMAGE:
                FileEntity.ImageEntity imageEntity = new FileEntity.ImageEntity();
                imageEntity.setHeight(fileEntity.getHeight());
                imageEntity.setWidth(fileEntity.getWidth());
                imageEntity.setName(fileEntity.getName());
                imageEntity.setPicUrl(key);
                imageEntity.setType(fileEntity.getType());
                imageEntity.setSize(fileEntity.getSize());
                imageEntity.setBucket("msgimg");
                content = JsonParseUtils.parseToJson(imageEntity);
                break;
            case _VIDEO:
                FileEntity.VideoEntity videoEntity = new FileEntity.VideoEntity();
                FileEntity.VideoEntity.ThumbImgBean thumbImgBean = new FileEntity.VideoEntity.ThumbImgBean();
                thumbImgBean.setHeight(fileEntity.getHeight());
                thumbImgBean.setWidth(fileEntity.getWidth());
                videoEntity.setThumbImg(thumbImgBean);
                videoEntity.setFileUrl(key);
                videoEntity.setName(fileEntity.getName());
                videoEntity.setSize(fileEntity.getSize());
                videoEntity.setType(fileEntity.getType());
                content = JsonParseUtils.parseToJson(videoEntity);
                break;
            case _VOICE:
                FileEntity.VoiceEntity voiceEntity = new FileEntity.VoiceEntity();
                voiceEntity.setType(fileEntity.getType());
                voiceEntity.setDuration(fileEntity.getDuration() + "");
                voiceEntity.setKey(key);
                voiceEntity.setSize(fileEntity.getSize());
                content = JsonParseUtils.parseToJson(voiceEntity);
                break;
        }

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(Constant.REQUEST_TYPE, Constant.STANDARD);
        hashMap.put(Constant.SERVICEID, BaseApplication.getUserBean().getId());
        hashMap.put(Constant.DIALOGID, itembean.getId());
        hashMap.put(Constant.CUSTOMERID, itembean.getCustomerId());
        hashMap.put(Constant.SOCKETID, BaseApplication.getUserEntity().getSocketId());
        hashMap.put(Constant.ENTID, BaseApplication.getUserBean().getEntId() + "");
        hashMap.put("sendType", "service");
        hashMap.put("contents", content);
        hashMap.put("type", messageBean.getType());
        hashMap.put("key", messageBean.getKey());
        ApiManager.getApistore().sendMsg(hashMap).subscribeOn(Schedulers.io())
                .subscribe(new RxSubscribe<SendEntity.DataBean>() {
                    @Override
                    protected void onSuccess(SendEntity.DataBean dataBean) {
                        if (dataBean.get_suc() == 1) {
                            Log.i("wy_activity", "界面关闭key:onSuccess" + dataBean.getKey());
                            //界面关闭出现mView为null
                            messageBean.setId(dataBean.getId());
                            messageBean.setKey(dataBean.getKey());
                            messageBean.setSendState(null);
                        }
                        if (dataBean.get_suc() == 0) {
                            if (dataBean.getId() != null) messageBean.setId(dataBean.getId());
                            messageBean.setSendState(null);
                            messageBean.setItemType(Constant.CHAT_CENTER);
                            messageBean.setType(_TEXT);
                            if (dataBean.getTxt() != null)
                                messageBean.setContents(dataBean.getTxt());
                            messageBean.setSendType("system");

                        }
                        eventPost(messageBean, itembean, true);
                    }

                    @Override
                    protected void onFailed(int code, String msg) {
                        eventPost(messageBean, itembean, false);
                    }

                    @Override
                    public void onSubscribe(Disposable d) {

                    }
                });

    }

    /**
     * 通知发送结果
     */
    private static void eventPost(MessageEntity.MessageBean messageBean, MessageDialogEntity.DataBean.ListBean itembean, boolean success) {

        if (!success) {
            messageBean.setSendState(Constant._ISFAILED);
        }
        CacheData.updateCache(itembean.getId(), messageBean);
        MessageEntity message = new MessageEntity();
        message.setAct(UI_FRESH);
        message.setMessage(messageBean);
        MessageEvent event = new MessageEvent(MessageEventType.EventMessage, message);
        EventBus.getDefault().postSticky(event);

    }

}
