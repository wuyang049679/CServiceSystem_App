package com.hecong.cssystem.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.hecong.cssystem.utils.socket.MessageEvent;
import com.hecong.cssystem.wight.stateview.StateView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.annotations.Nullable;


/**
 * @author wuyang
 * @apiNote frgment的基类
 */

public abstract class BaseFragment<T extends BasePresenterIm, V> extends Fragment implements BaseView<V> {
    public String TAG = "TAG";
    protected View rootView;
    private StateView mStateView;
    public T mPresenter;
    public int mUserId = -1;
    Unbinder unbinder;
    private int PHONELENGTH = 11;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(getLayoutResource(), container, false);
            unbinder = ButterKnife.bind(this, rootView);
            if (null != getPresenter()) {
                mPresenter = getPresenter();
                mPresenter.attachView(this, getActivity());
//                mUserId = ShareUtil.getInt("userId");
            }
            initStateLayout();
        }

        return rootView;
    }

    /**
     * 获取presenter 对象
     */
    public T getPresenter() {
        return mPresenter;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void refreshView(Message message) {
        int what = message.what;

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            //frg隐藏
            hideFragment();
        } else {
            //frgshow
            showFragment();
        }
    }

    /**
     * fragment 显示的时候调用
     */
    protected void showFragment() {
        Log.i(TAG, "showFragment: " + mUserId);
//        mUserId = ShareUtil.getInt("userId");
    }

    /**
     * fragment隐藏时调用
     */
    protected void hideFragment() {
        hideLoading();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        if (view != null) {
            Log.i(TAG, "onViewCreated" + mUserId);
            initView();
            initData(savedInstanceState);
        }

        if (null != getPresenter()) {
            mPresenter = getPresenter();
            mPresenter.attachView(this, getActivity());
        }

        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSocketEvent(MessageEvent msg) {

    }

    /**
     * 注入statelayout
     */
    private void initStateLayout() {
        int resId = injectTarget();
        Log.i("yeye", "id == " + resId);
        if (resId == 0) {
            return;
        }
        View view = rootView.findViewById(injectTarget());
        if (view == null) {
            Log.i("yeye", "view == " + "null");
            return;
        }
        mStateView = StateView.inject(view);
        mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() {
            @Override
            public void onRetryClick() {
                //点击重试响应事件
              doRetry();
            }
        });
    }

    /**
     * 注入要替换的View
     */
    protected abstract int injectTarget();


    /**
     * 显示空布局
     */
    @Override
    public void showEmptyView(String msg) {
        mStateView.showEmpty(msg);
    }

    /**
     * 显示自定义图片提示语
     *
     * @param imgRes 图片资源
     * @param msg    提示语
     */
    @Override
    public void showDiyView(int imgRes, String msg) {
        mStateView.showDiyView(imgRes, msg);
    }

    /**
     * 显示加载视图
     */
    @Override
    public void showLoadingView() {
        mStateView.showLoading();
    }

    @Override
    public void showLoadingView(String msg) {

    }

    public void showShopCarEmey() {
        mStateView.showContent();

    }

    /**
     * 显示默认视图
     */
    @Override
    public void showContentView() {
        mStateView.showContent();
    }

    @Override
    public void showCommentEmptyView() {
//        if (mDialog != null && mDialog.isShowing())
//            mDialog.dismiss();
        mStateView.showCommentEmpty();
    }

    /**
     * 显示错误视图
     */
    @Override
    public void showNetErrorView() {
        mStateView.showRetry();
    }

    @Override
    public void showRetryView() {
        mStateView.showRetry();
    }


    /**
     * 点击重试要做的操作
     * ps：如果多个接口可以判断一下需要重新加载的数据
     */
    protected abstract void doRetry();

    /**
     * 获取布局文件
     */
    protected abstract int getLayoutResource();


    /**
     * 初始化view
     * prensenter attachView 或者一些初始化页面的操作
     */
    protected abstract void initView();

    /**
     * 初始化数据
     *
     * @param savedInstanceState
     */
    protected abstract void initData(Bundle savedInstanceState);


    /**
     * 通过Class跳转界面
     **/
    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivityForResult(Class<?> cls, Bundle bundle,
                                       int requestCode) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 含有Bundle通过Class跳转界面
     **/
    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }


    /**
     * 短暂显示Toast提示(来自String)
     **/
    public void showShortToast(String text) {
//        ToastUtils.show(text);
    }

    /**
     * 短暂显示Toast提示(id)
     **/
    public void showShortToast(int resId) {
//        ToastUtils.show(getString(resId));
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unRegisterSomething();
        if (mPresenter != null) {
            mPresenter.unsubcrible();
        }
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
            EventBus.getDefault().removeAllStickyEvents();
        }

    }

    /**
     * 父类中onDestroyView方法
     */
    public void unRegisterSomething() {


    }

    @Override
    public void logCat(String m) {

    }

    @Override
    public void startToLogIn() {
//        startActivity(LogInActivity.class);
    }

    @Override
    public void hideLoading() {
//        loadingDialog.dismiss();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


}
