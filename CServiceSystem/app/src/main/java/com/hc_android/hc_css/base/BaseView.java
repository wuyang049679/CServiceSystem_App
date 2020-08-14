package com.hc_android.hc_css.base;


/**
 * Created by wuyang on 2019/8/30.
 */

public interface BaseView<V> {

    /**
     * 数据加载失败
     * tag==1,正常错误，可以只显示toast
     * tag=0,非正常错误，需要考虑显示错误界面
     */
    void showDataError(String errorMessage);

    /**
     * 数据加载成功
     */
    void showDataSuccess(V datas);

    /**
     * 显示重试布局
     **/
    void showRetryView();

    /**
     * 显示加载中视图文案的
     */
    void showLoadingView(String msg);
    /**
     * 显示加载中视图带
     */
    void showLoadingView();
    /**
     * 显示无网络视图
     */
    void showNetErrorView();

    /**
     * 显示无评论视图
     */
    void showCommentEmptyView();

    /**
     * 没有加载到内容
     */
    void showEmptyView(String msg);

    /**
     * 显示内容View
     */
    void showContentView();

    /**
     * 显示自定义图片缺省用
     *
     * @param imgRes 图片资源
     * @param msg    提示语
     */
    void showDiyView(int imgRes, String msg);

    void logCat(String m);

    void startToLogIn();

    void hideLoading();

}
