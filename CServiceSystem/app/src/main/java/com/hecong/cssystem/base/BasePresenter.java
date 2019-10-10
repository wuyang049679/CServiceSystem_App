package com.hecong.cssystem.base;

import android.content.Context;

/**
 * Created by xiangyao on 2018/8/1.
 */

public interface BasePresenter {
    /**
     * 判断网络连接
     */
    Boolean checkNetWork(Context context);

    void unsubcrible();
}
