package com.hc_android.hc_css.wight;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hc_android.hc_css.R;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.util.DensityUtil;



public class CustomFooter extends LinearLayout implements RefreshFooter {

    private TextView mHeaderText;//标题文本
    private ProgressBar bar;
    private View padding;

    public static String REFRESH_FOOTER_PULLUP = "上拉加载更多";
    public static String REFRESH_FOOTER_RELEASE = "释放立即加载";
    public static String REFRESH_FOOTER_LOADING = "正在加载...";
    public static String REFRESH_FOOTER_REFRESHING = "正在刷新...";
    public static String REFRESH_FOOTER_FINISH = "加载完成";
    public static String REFRESH_FOOTER_FAILED = "加载失败";
    public static String REFRESH_FOOTER_ALLLOADED = "我是有底线的";
    public CustomFooter(Context context) {
        super(context);
        initView(context);
    }

    public CustomFooter(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public CustomFooter(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private void initView(Context context) {
        setGravity(Gravity.CENTER);
        mHeaderText = new TextView(context);
        bar=new ProgressBar(context);

        bar = new ProgressBar(context);
        bar.setIndeterminate(false);
        bar.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progressbar_loading_foot));
        bar.setScrollBarFadeDuration(50);

        addView(bar, DensityUtil.dp2px(20), DensityUtil.dp2px(20));
        padding = new View(context);
        addView(padding, DensityUtil.dp2px(20), DensityUtil.dp2px(20));
        addView(mHeaderText, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        setMinimumHeight(DensityUtil.dp2px(60));
    }
    @Override
    public boolean setNoMoreData(boolean noMoreData) {

            mHeaderText.setText(REFRESH_FOOTER_ALLLOADED);
            bar.setVisibility(GONE);
            padding.setVisibility(GONE);
        return true;
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {

    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {

    }



    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        if (success){
            mHeaderText.setText("");
            bar.setVisibility(GONE);
        } else {
            mHeaderText.setText("加载失败");
            bar.setVisibility(GONE);
        }
        return 10;//延迟10毫秒之后再弹回
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {

        switch (newState) {

            case Loading:
                mHeaderText.setText("");
                bar.setVisibility(VISIBLE);
                padding.setVisibility(VISIBLE);
                break;

        }
    }


}
