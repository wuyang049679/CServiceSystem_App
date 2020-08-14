package com.hc_android.hc_css.wight;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.hc_android.hc_css.R;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.util.DensityUtil;

public class CustomHeader extends LinearLayout implements RefreshHeader {

    private TextView mHeaderText;//标题文本
//    private PathsView mArrowView;//下拉箭头
    private ImageView mProgressView;//刷新动画视图
    private ImageView imageView;
//    private ProgressDrawable mProgressDrawable;//刷新动画
    private ProgressBar bar;


    public CustomHeader(Context context) {
        super(context);
        initView(context);
    }

    public CustomHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.initView(context);
    }

    public CustomHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.initView(context);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)

    public CustomHeader(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.initView(context);
    }
    private void initView(Context context) {
        setGravity(Gravity.CENTER);

        mHeaderText = new TextView(context);
//        mProgressDrawable = new ProgressDrawable();
        imageView=new ImageView(context);
        mProgressView = new ImageView(context);
        bar = new ProgressBar(context);
        bar.setIndeterminate(false);
        bar.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progressbar_loading));
        bar.setScrollBarFadeDuration(50);



//        Drawable drawable=context.getResources().getDrawable(R.drawable.loading_chat);
//        bar.setIndeterminateDrawable(drawable);
//        bar.setIndeterminate(true);
//        bar.setProgressDrawable(drawable);
//        imageView.setImageResource(R.drawable.loading_chat);
//        mProgressView.setImageResource(R.drawable.loading_chat);
//        addView(mProgressView, DensityUtil.dp2px(20), DensityUtil.dp2px(20));
//        addView(imageView, DensityUtil.dp2px(20), DensityUtil.dp2px(20));
        addView(bar, DensityUtil.dp2px(20), DensityUtil.dp2px(20));
//        addView(new View(context), DensityUtil.dp2px(20), DensityUtil.dp2px(20));
//        addView(mHeaderText, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        setMinimumHeight(DensityUtil.dp2px(60));
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

//        mProgressDrawable.start();//开始动画

    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
//        mProgressDrawable.stop();//停止动画

        if (success){

            mHeaderText.setText("刷新完成");
            imageView.setVisibility(VISIBLE);
            bar.setVisibility(GONE);
            mProgressView.setVisibility(GONE);//隐藏动画

        } else {
            mHeaderText.setText("刷新失败");
        }
        return 10;//延迟500毫秒之后再弹回
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
            case None:
                break;
            case PullDownToRefresh:
                mHeaderText.setText("下拉刷新");
//                mArrowView.setVisibility(VISIBLE);//显示下拉箭头
                //隐藏动画
                mProgressView.setVisibility(VISIBLE);
//                mArrowView.animate().rotation(0);//还原箭头方向
                imageView.setVisibility(GONE);
                bar.setVisibility(GONE);
                break;
            case Refreshing:
                imageView.setVisibility(GONE);
                mHeaderText.setText("正在刷新");
                //显示加载动画
                mProgressView.setVisibility(GONE);
                bar.setVisibility(VISIBLE);
//                mArrowView.setVisibility(GONE);//隐藏箭头
                break;
            case ReleaseToRefresh:
                imageView.setVisibility(GONE);
                mHeaderText.setText("释放刷新");
//                mArrowView.animate().rotation(180);//显示箭头改为朝上
                bar.setVisibility(GONE);
                break;
            default:
                break;
        }
    }
}
