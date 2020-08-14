package com.hc_android.hc_css.ui.activity;

import android.annotation.TargetApi;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Handler;
import android.transition.Transition;
import android.view.View;
import android.widget.ImageView;

import androidx.core.view.ViewCompat;

import com.bumptech.glide.Glide;
import com.gyf.immersionbar.ImmersionBar;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.base.BaseActivity;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.DownloadUtil;
import com.hc_android.hc_css.wight.video.OnTransitionListener;
import com.hc_android.hc_css.wight.video.SampleCoverVideo;
import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;

import butterknife.BindView;

public class PlayActivity extends BaseActivity {

    @BindView(R.id.sampleVideo)
    SampleCoverVideo videoPlayer;
    private OrientationUtils orientationUtils;
    private boolean isTransition;
    private Transition transition;
    public final static String IMG_TRANSITION = "IMG_TRANSITION";
    public final static String TRANSITION = "TRANSITION";
    private String filePath;
    @Override
    protected void reloadActivity() {

    }

    @Override
    protected View injectTarget() {
        return null;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        ImmersionBar.with(this)
                .transparentBar()
                .init();
        isTransition = getIntent().getBooleanExtra(TRANSITION, false);
        filePath=getIntent().getStringExtra(Constant._FILE);
        init();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_play;
    }

    @Override
    public void showDataSuccess(Object datas) {

    }


    private void init() {

        videoPlayer.setUp(filePath, true,null );
        //增加封面
        ImageView imageView = new ImageView(this);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        Glide.with(this).load(filePath).into(imageView);
        videoPlayer.setThumbImageView(imageView);
        //增加title
        videoPlayer.getTitleTextView().setVisibility(View.VISIBLE);
        //设置返回键
        videoPlayer.getBackButton().setVisibility(View.VISIBLE);
        //设置旋转
        orientationUtils = new OrientationUtils(this, videoPlayer);
        //设置全屏按键功能,这是使用的是选择屏幕，而不是全屏
        videoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orientationUtils.resolveByClick();
            }
        });
        //是否可以滑动调整
        videoPlayer.setIsTouchWiget(true);
        //设置返回按键功能
        videoPlayer.getBackButton().setOnClickListener(v -> onBackPressed());
        videoPlayer.getVideoDownload().setOnClickListener(view -> DownloadUtil.get().download(PlayActivity.this, filePath, "hc", new DownloadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(String savePath) {
            }

            @Override
            public void onDownloading(int progress) {
            }

            @Override
            public void onDownloadFailed() {
            }
        }));

        //过渡动画
        initTransition();
    }


    @Override
    protected void onPause() {
        super.onPause();
        videoPlayer.onVideoPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        videoPlayer.onVideoResume();
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    @Override
    public void onBackPressed() {
        //先返回正常状态
        if (orientationUtils.getScreenType() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            videoPlayer.getFullscreenButton().performClick();
            return;
        }
        //释放所有
        videoPlayer.setVideoAllCallBack(null);
        GSYVideoManager.releaseAllVideos();
        if (isTransition && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            super.onBackPressed();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    finish();
                    overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                }
            }, 500);
        }
    }


    private void initTransition() {
        if (isTransition && Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            postponeEnterTransition();
            ViewCompat.setTransitionName(videoPlayer, IMG_TRANSITION);
//            addTransitionListener();
            videoPlayer.startPlayLogic();
            startPostponedEnterTransition();
        } else {
            videoPlayer.startPlayLogic();
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private boolean addTransitionListener() {
        transition = getWindow().getSharedElementEnterTransition();
        if (transition != null) {
            transition.addListener(new OnTransitionListener(){
                @Override
                public void onTransitionEnd(Transition transition) {
                    super.onTransitionEnd(transition);
                    videoPlayer.startPlayLogic();
                    transition.removeListener(this);
                }
            });
            return true;
        }
        return false;
    }
}
