package com.hc_android.hc_css.utils.thread;

import android.os.Handler;

import java.util.HashMap;
import java.util.Map;

/**
 * 轮询工具类（Handler实现）
 */
public class PollingUtil {

    private static Handler mHanlder;
    private static Map<String, Runnable> mTaskMap = new HashMap<String, Runnable>();
    private OnReleasedListener onReleasedListener;
    public PollingUtil(Handler handler) {
        mHanlder = handler;
    }



    /**
     * 开启定时任务
     * @param
     * @param interval   时间间隔
     * @param
     */
    public void startPolling(final String key, OnReleasedListener interval) {
        this.onReleasedListener=interval;
        Runnable task = mTaskMap.get(key);
        if (task == null) {
            task = new Runnable() {
                @Override
                public void run() {
                    onReleasedListener.threadKey(key);

                }
            };
            mTaskMap.put(key, task);
            post(key);
        }
    }

    /**
     * 结束某个定时任务
     * @param
     */
    public static void endPolling(String key) {
        if (mTaskMap.containsKey(key)) {
            mHanlder.removeCallbacks(mTaskMap.get(key));
        }
    }

    private void post(String key) {
        Runnable task = mTaskMap.get(key);
        mHanlder.postDelayed(task,3000);
    }

}
