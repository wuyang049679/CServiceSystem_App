package com.hc_android.hc_css.utils.android;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.ColorMatrixColorFilter;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

/**
 * @Description:主要功能:控件点击效果动画工具类
 * @Prject: CommonUtilLibrary
 * @Package: com.jingewenku.abrahamcaijin.commonutil
 * @author: AbrahamCaiJin
 * @date: 2017年05月15日 11:42
 * @Copyright: 个人版权所有
 * @Company:
 * @version: 1.0.0
 */

public class AnimationUtils {
    /**
     * 给视图添加点击效果,让背景变深
     * */
    public static void addTouchDrak(View view, boolean isClick) {
        view.setOnTouchListener(VIEW_TOUCH_DARK);

        if (!isClick) {
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                }
            });
        }
    }

    /**
     * 给视图添加点击效果,让背景变暗
     * */
    public static void addTouchLight(View view, boolean isClick) {
        view.setOnTouchListener(VIEW_TOUCH_LIGHT);

        if (!isClick) {
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                }
            });
        }
    }

    /**
     * 让控件点击时，颜色变深
     * */
    public static final OnTouchListener VIEW_TOUCH_DARK = new OnTouchListener() {

        public final float[] BT_SELECTED = new float[] { 1, 0, 0, 0, -50, 0, 1,
            0, 0, -50, 0, 0, 1, 0, -50, 0, 0, 0, 1, 0 };
        public final float[] BT_NOT_SELECTED = new float[] { 1, 0, 0, 0, 0, 0,
            1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0 };

        @SuppressWarnings("deprecation")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (v instanceof ImageView) {
                    ImageView iv = (ImageView) v;
                    iv.setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));
                } else {
                    v.getBackground().setColorFilter(
                        new ColorMatrixColorFilter(BT_SELECTED));
                    v.setBackgroundDrawable(v.getBackground());
                }
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                if (v instanceof ImageView) {
                    ImageView iv = (ImageView) v;
                    iv.setColorFilter(new ColorMatrixColorFilter(
                        BT_NOT_SELECTED));
                } else {
                    v.getBackground().setColorFilter(
                        new ColorMatrixColorFilter(BT_NOT_SELECTED));
                    v.setBackgroundDrawable(v.getBackground());
                }
            }
            return false;
        }
    };

    /**
     * 让控件点击时，颜色变暗
     * */
    public static final OnTouchListener VIEW_TOUCH_LIGHT = new OnTouchListener() {

        public final float[] BT_SELECTED = new float[] { 1, 0, 0, 0, 50, 0, 1,
            0, 0, 50, 0, 0, 1, 0, 50, 0, 0, 0, 1, 0 };
        public final float[] BT_NOT_SELECTED = new float[] { 1, 0, 0, 0, 0, 0,
            1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0 };

        @SuppressWarnings("deprecation")
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                if (v instanceof ImageView) {
                    ImageView iv = (ImageView) v;
                    iv.setDrawingCacheEnabled(true);

                    iv.setColorFilter(new ColorMatrixColorFilter(BT_SELECTED));
                } else {
                    v.getBackground().setColorFilter(
                        new ColorMatrixColorFilter(BT_SELECTED));
                    v.setBackgroundDrawable(v.getBackground());
                }
            } else if (event.getAction() == MotionEvent.ACTION_UP) {
                if (v instanceof ImageView) {
                    ImageView iv = (ImageView) v;
                    iv.setColorFilter(new ColorMatrixColorFilter(
                        BT_NOT_SELECTED));
                } else {
                    v.getBackground().setColorFilter(
                        new ColorMatrixColorFilter(BT_NOT_SELECTED));
                    v.setBackgroundDrawable(v.getBackground());
                }
            }
            return false;
        }
    };


    private int mWight;//伸展高度

    private View hideView,down;//需要展开隐藏的布局，开关控件

    private RotateAnimation animation;//旋转动画

    /**
     * 构造器(可根据自己需要修改传参)
     * @param context 上下文
     * @param hideView 需要隐藏或显示的布局view
     * @param down 按钮开关的view
     * @param wight 布局展开的高度(根据实际需要传)
     */
    public static AnimationUtils newInstance(Context context, View hideView, View down, int wight){
        return new AnimationUtils(context,hideView,down,wight);
    }

    private AnimationUtils(Context context,View hideView,View down,int wight){
        this.hideView = hideView;
        this.down = down;
        float mDensity = context.getResources().getDisplayMetrics().density;
        mWight = (int) (mDensity * wight + 0.5);//伸展高度
    }

    /**
     * 开关
     */
    public void toggle(){
//        startAnimation();
        if (View.VISIBLE == hideView.getVisibility()) {
            closeAnimate(hideView);//布局隐藏
        } else {
            openAnim(hideView);//布局铺开
        }
    }

    /**
     * 开关旋转动画
     */
    private void startAnimation() {
        if (View.VISIBLE == hideView.getVisibility()) {
            animation = new RotateAnimation(0, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        } else {
            animation = new RotateAnimation(0, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        }
        animation.setDuration(30);//设置动画持续时间
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatMode(Animation.REVERSE);//设置反方向执行
        animation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        down.startAnimation(animation);
    }

    public void openAnim(View v) {
        v.setVisibility(View.VISIBLE);
        ValueAnimator animator = createDropAnimator(v, 0, mWight);
        animator.start();
    }

    public void closeAnimate(final View view) {
        int origHeight = view.getHeight();
        ValueAnimator animator = createDropAnimator(view, origHeight, 0);
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                view.setVisibility(View.GONE);
            }
        });
        animator.start();
    }

    private ValueAnimator createDropAnimator(final View v, int start, int end) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator arg0) {
                int value = (int) arg0.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = v.getLayoutParams();
                layoutParams.width = value;
                v.setLayoutParams(layoutParams);
            }
        });
        return animator;
    }


}
