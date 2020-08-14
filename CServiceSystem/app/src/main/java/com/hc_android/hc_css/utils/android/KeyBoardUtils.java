package com.hc_android.hc_css.utils.android;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by sam on 2017/4/25.
 * 手机键盘工具类
 */

public class KeyBoardUtils {

    /**
     * 打开软键盘
     *
     * @param mEditText
     *            输入框
     * @param mContext
     *            上下文
     */
    public static void openKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    /**
     * 关闭软键盘
     *
     * @param mEditText
     *            输入框
     * @param mContext
     *            上下文
     */
    public static void closeKeybord(EditText mEditText, Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }


        private View rootView;//activity的根视图
        int rootViewVisibleHeight;//纪录根视图的显示高度
        private OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener;

        public KeyBoardUtils(Activity activity) {
            //获取activity的根视图
            rootView = activity.getWindow().getDecorView();

            //监听视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变
            rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    //获取当前根视图在屏幕上显示的大小
                    Rect r = new Rect();
                    rootView.getWindowVisibleDisplayFrame(r);

                    int visibleHeight = r.height();
                    System.out.println(""+visibleHeight);
                    if (rootViewVisibleHeight == 0) {
                        rootViewVisibleHeight = visibleHeight;
                        return;
                    }

                    //根视图显示高度没有变化，可以看作软键盘显示／隐藏状态没有改变
                    if (rootViewVisibleHeight == visibleHeight) {
                        return;
                    }

                    //根视图显示高度变小超过200，可以看作软键盘显示了
                    if (rootViewVisibleHeight - visibleHeight > 200) {
                        if (onSoftKeyBoardChangeListener != null) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                            onSoftKeyBoardChangeListener.keyBoardShow(rootViewVisibleHeight - visibleHeight);
                                    /**
                                     * 延时执行的代码（解决软键盘还未完全弹出布局上移问题）
                                     */
                                }
                            },100); // 延时1秒
                        }
                        rootViewVisibleHeight = visibleHeight;
                        return;
                    }

                    //根视图显示高度变大超过200，可以看作软键盘隐藏了
                    if (visibleHeight - rootViewVisibleHeight > 200) {
                        if (onSoftKeyBoardChangeListener != null) {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {

                            onSoftKeyBoardChangeListener.keyBoardHide(visibleHeight - rootViewVisibleHeight);
                                    /**
                                     * 延时执行的代码 （解决软键盘还未完全弹出布局上移问题）
                                     */

                                }
                            },100); // 延时1秒
                        }
                        rootViewVisibleHeight = visibleHeight;
                        return;
                    }

                }
            });
        }

        private void setOnSoftKeyBoardChangeListener(OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener) {
            this.onSoftKeyBoardChangeListener = onSoftKeyBoardChangeListener;
        }

        public interface OnSoftKeyBoardChangeListener {
            void keyBoardShow(int height);

            void keyBoardHide(int height);
        }

        public static void setListener(Activity activity, OnSoftKeyBoardChangeListener onSoftKeyBoardChangeListener) {
            KeyBoardUtils softKeyBoardListener = new KeyBoardUtils(activity);
            softKeyBoardListener.setOnSoftKeyBoardChangeListener(onSoftKeyBoardChangeListener);
        }


}
