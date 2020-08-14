package com.hc_android.hc_css.utils.android.image;


import android.content.Context;
import android.util.Log;

import com.qiniu.android.common.FixedZone;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.KeyGenerator;
import com.qiniu.android.storage.Recorder;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.qiniu.android.storage.persistent.FileRecorder;

import org.json.JSONException;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description 七牛文件上传工具
 * @Date 2018.09.26
 */
public class QiniuUploadManager {

    public interface OnUploadListener {
        void onStartUpload();

        void onUploadProgress(String key, double percent);

        void onUploadFailed(String key, String err);

        void onUploadBlockComplete(String key);

        void onUploadCompleted();

        void onUploadCancel();
    }

    private final String TAG = "wy_activity";
    private static QiniuUploadManager manager;

    public static QiniuUploadManager getInstance(Context context) {
        if (manager == null) {
            synchronized(QiniuUploadManager.class) {
                if(manager == null) {
                    manager = new QiniuUploadManager(context);
                }
            }
        }
        return manager;
    }

    private UploadManager uploadManager;
    private Object lock = new Object();
    private HashMap<OnUploadListener, Boolean> cancels = new HashMap<>();
    private List<OnUploadListener> uploadListeners = new ArrayList<>();

    private QiniuUploadManager(Context appContext) {
        initManager(appContext.getApplicationContext());
    }

    private void initManager(Context appContext) {
        String dirPath = appContext.getExternalCacheDir().getPath() + File.separator + "QiniuTemp";
        Log.d(TAG, dirPath);
        Recorder recorder = null;
        try {
            recorder = new FileRecorder(dirPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        KeyGenerator keyGen = (key, file) -> key + "_._" + new StringBuffer(file.getAbsolutePath()).reverse();
        Configuration.Builder builder = new Configuration.Builder();
        builder.chunkSize(512 * 1024)              // 分片上传时，每片的大小。 默认256K
                .putThreshhold(1024 * 1024)        // 启用分片上传阀值。默认512K
                .connectTimeout(10)                // 链接超时。默认10秒
                .responseTimeout(60)              // 服务器响应超时。默认60秒
                .zone(FixedZone.zone2);            // 设置区域，指定不同区域的上传域名、备用域名、备用IP。
        if (recorder != null) {
            builder = builder.recorder(recorder)   // recorder分片上传时，已上传片记录器。默认null
                    .recorder(recorder, keyGen);   // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
        }
        Configuration config = builder.build();
        uploadManager = new UploadManager(config);
    }

    /**
     * 上传单个文件到七牛服务器
     *
     * @param param
     * @param uploadListener
     * @return 文件有效，开始上传返回true，否则返回false
     */
    public synchronized boolean upload(QiniuUploadFile param, OnUploadListener uploadListener) {
        Log.d(TAG, "upload开始上传(" + param.getKey() + "): " + param.getFilePath());
        if (param == null) {
            return false;
        }
        File uploadFile = new File(param.getFilePath());
//        if (!uploadFile.exists() || uploadFile.isDirectory()) {
//            Log.d(TAG, "文件不存在");
//            return false; // 如果是文件夹，或者文件不存在，那么返回false
//        }
        if (uploadListener != null) {
            uploadListener.onStartUpload();
            Log.d(TAG, "开始上传(" + param.getKey() + "): " + param.getFilePath());
        }
        // 注册回调对象，用户取消上传时使用这些对象
        uploadListeners.add(uploadListener);
        cancels.put(uploadListener, false);
        uploadManager.put(uploadFile, param.getKey(), param.getToken(),
                (key, info, response) -> {
                    synchronized (lock) {
                        if (uploadListener == null) {
                            Log.d(TAG, "uploadListener:return ");
                            return;
                        }
                        if (info.isOK()) {
                            Log.d(TAG, "上传成功(" + key +"): " + info.duration);
                            try {
                                String md5=response.getString("key");
                                uploadListener.onUploadBlockComplete(md5);
                                uploadListener.onUploadCompleted();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {
                            Log.d(TAG, "上传失败(" + key + "): " + info.error);
                            uploadListener.onUploadFailed(key, info.error);
                        }
                        // 清理回调等资源
                        Log.d(TAG, "上传完成(" + key +"): " + info.duration);
                        uploadListeners.remove(uploadListener);
                        cancels.remove(uploadListener);
                    }
                },
                new UploadOptions(null, null, false,
                        (key12, percent) -> {
                            synchronized (lock) {
//                                Log.d(TAG, "progress(" + key12 + "):" + percent);
                                if (uploadListener != null) {
                                    uploadListener.onUploadProgress(key12, percent);
                                }
                            }
                        },
                        () -> {
                            synchronized (lock) {
                                if (uploadListener == null) {
                                    return false;
                                }
                                Boolean result = cancels.get(uploadListener);
                                // Log.d(TAG, "检查取消标识(" + param.getKey() + "): " + result);
                                if (result != null && result) {
                                    cancels.remove(uploadListener);
                                }
                                // 有出现一次true后还继续调用的情况，需要判null
                                return result == null ? true : result;
                            }
                        }));
        return true;
    }

    /**
     * 同时上传多个文件
     * @param params 需要上传的文件
     * @param uploadListener 回调
     * @return 开始上传返回 true，如果参数无效，或者文件不存在等，返回false，不上传
     */
    public synchronized boolean upload(List<QiniuUploadFile> params, OnUploadListener uploadListener) {
        if (params == null || params.size() == 0) {
            return false;
        }
        AtomicInteger completedCount = new AtomicInteger();  // 完成(失败也算完成)的数量
        List<QiniuUploadFile> needUploadFile = new ArrayList<>();
        for (QiniuUploadFile param : params) {
            File uploadFile = new File(param.getFilePath());
            if (!uploadFile.exists() || uploadFile.isDirectory()) {
                continue;  // 过滤无效的文件
            }
            needUploadFile.add(param);
        }
        if (needUploadFile.size() == 0) {
            return false;
        }
        if (uploadListener != null) {
            Log.d(TAG, "开始上传(size=" + needUploadFile.size() + ")");
            uploadListener.onStartUpload(); // 开始上传任务
        }
        uploadListeners.add(uploadListener);
        cancels.put(uploadListener, false);
        for (QiniuUploadFile param : needUploadFile) {
            File uploadFile = new File(param.getFilePath());
            uploadManager.put(uploadFile, param.getKey(), param.getToken(),
                    (key, info, response) -> {
                        synchronized (lock) {
                            completedCount.getAndIncrement();
                            if (uploadListener == null) {
                                return;
                            }
                            if (info.isOK()) {
                                Log.d(TAG, "上传成功(" + key +"): " + info.duration);
                                uploadListener.onUploadBlockComplete(key);
                            } else {
                                Log.d(TAG, "上传失败(" + key + "): " + info.error);
                                uploadListener.onUploadFailed(key, info.error);
                            }
                            if (completedCount.get() == needUploadFile.size()) {
                                Log.d(TAG, "上传完成(" + needUploadFile.size() +")");
                                uploadListener.onUploadCompleted();
                                // 如果所有任务都完成了，那么清理回调资源
                                uploadListeners.remove(uploadListener);
                                cancels.remove(uploadListener);
                            }
                        }
                    },
                    new UploadOptions(null, param.getMimeType(), false,
                            (key1, percent) -> {
                                synchronized (lock) {
                                    Log.d(TAG, "progress(" + key1 + "):" + percent);
                                    if (uploadListener != null) {
                                        uploadListener.onUploadProgress(key1, percent);
                                    }
                                }
                            },
                            () -> {
                                if (uploadListener == null) {
                                    return false;
                                }
                                Boolean result = cancels.get(uploadListener);
                                // Log.d(TAG, "检查取消标识(" + param.getKey() + "): " + result);
                                if (result != null && result) {
                                    cancels.remove(uploadListener);
                                }
                                // 由于同时上传多个文件是共享一个：uploadListener，
                                // 所以，后面读取到的都是null，null，标识取消
                                return result == null ? true : result;
                            }));
        }
        return true;
    }

    /**
     * 排队的方式上传文件，上传完前一个才继续上传下一个
     * 注意，这个方法没有 onStartUpload 回调
     * @param params 需要上传的文件
     * @param uploadListener 回调接口
     */
    public synchronized void queueUpload(Queue<QiniuUploadFile> params, OnUploadListener uploadListener) {
        if (params == null || params.size() == 0) {
            return;
        }
        Queue<QiniuUploadFile> files = new LinkedList<>();
        for(QiniuUploadFile param : params) {
            File uploadFile = new File(param.getFilePath());
            if (uploadFile.exists() && !uploadFile.isDirectory()) {
                files.add(param);
            }
        }
        QiniuUploadFile param = files.poll();
        if(param == null) {
            return;
        }
        File uploadFile = new File(param.getFilePath());
        // 注册回调对象
        uploadListeners.add(uploadListener);
        Boolean cancel = cancels.get(uploadListener);
        if(cancel != null && cancel) {
            cancels.remove(uploadListener); // 在这里移除取消任务标志
            return;
        }
        cancels.put(uploadListener, false);
        uploadManager.put(uploadFile, param.getKey(), param.getToken(),
                (key, info, response) -> {
                    synchronized (lock) {
                        if (info.isOK()) {
                            Log.d(TAG, "上传成功(" + key +"): " + info.duration);
                            if(uploadListener != null) {
                                uploadListener.onUploadBlockComplete(key);
                            }
                        } else {
                            Log.d(TAG, "上传失败(" + key + "): " + info.error);
                            if(uploadListener != null) {
                                uploadListener.onUploadFailed(key, info.error);
                            }
                        }
                        if(files.size() == 0) {
                            // 清理回调等资源
                            Log.d(TAG, "上传完成(" + key + "): " + info.duration);
                            if(uploadListener != null) {
                                uploadListeners.remove(uploadListener);
                                cancels.remove(uploadListener);
                            }
                        } else {
                            // 未上传完成，继续队列中的下一个任务
                            queueUpload(files, uploadListener);
                        }
                    }
                },
                new UploadOptions(null, null, false,
                        (key12, percent) -> {
                            synchronized (lock) {
                                Log.d(TAG, "progress(" + key12 + "):" + percent);
                                if (uploadListener != null) {
                                    uploadListener.onUploadProgress(key12, percent);
                                }
                            }
                        },
                        () -> {
                            synchronized (lock) {
                                if (uploadListener == null) {
                                    return false;
                                }
                                Boolean result = cancels.get(uploadListener);
                                //Log.d(TAG, "取消(" + param.getKey() + "): " + result);
                                // 有出现一次true后还继续调用的情况，所以需要判null
                                return result == null ? true : result;
                            }
                        }));
    }

    /**
     * 取消指定上传任务
     *
     * @param listener
     */
    public void cancel(OnUploadListener listener) {
        synchronized (lock) {
            cancels.put(listener, true);
            for (OnUploadListener uploadListener : uploadListeners) {
                if (uploadListener == listener) {
                    try {
                        uploadListener.onUploadCancel();
                        Log.d(TAG, "取消上传");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
            }
            uploadListeners.remove(listener);
        }
    }

    /**
     * 取消所有的上传任务
     */
    public void cancel() {
        synchronized (lock) {
            for (OnUploadListener key : cancels.keySet()) {
                cancels.put(key, true);
            }
            for (OnUploadListener listener : uploadListeners) {
                try {
                    listener.onUploadCancel();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            uploadListeners.clear();
            Log.d(TAG, "取消所有上传任务");
        }
    }

    public static class QiniuUploadFile {
        private String filePath;  // 文件的路径
        private String key;       // 文件上传到服务器的路径，如：files/images/test.jpg
        private String mimeType;  // 文件类型
        private String token;     // 从后台获取的token值，只在一定时间内有效

        public QiniuUploadFile(String filePath, String key, String mimeType, String token) {
            this.filePath = filePath;
            this.key = key;
            this.mimeType = mimeType;
            this.token = token;
        }

        public String getFilePath() {
            return filePath;
        }

        public String getKey() {
            return key;
        }

        public String getMimeType() {
            return mimeType;
        }

        public String getToken() {
            return token;
        }
    }


}
