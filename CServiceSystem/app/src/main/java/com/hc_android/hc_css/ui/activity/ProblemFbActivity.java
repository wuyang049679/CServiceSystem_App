package com.hc_android.hc_css.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.FileProvider;

import com.gyf.immersionbar.ImmersionBar;
import com.hc_android.hc_css.BuildConfig;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.api.Address;
import com.hc_android.hc_css.base.BaseActivity;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.base.BaseConfig;
import com.hc_android.hc_css.entity.LoginEntity;
import com.hc_android.hc_css.entity.MessageEntity;
import com.hc_android.hc_css.entity.ProblemEntity;
import com.hc_android.hc_css.utils.JsonParseUtils;
import com.hc_android.hc_css.utils.NullUtils;
import com.hc_android.hc_css.utils.android.SystemUtils;
import com.hc_android.hc_css.utils.android.app.AppConfig;
import com.hc_android.hc_css.utils.android.image.PickUtils;
import com.hc_android.hc_css.utils.socket.MessageEvent;
import com.just.agentweb.AgentWeb;
import com.just.agentweb.WebChromeClient;
import com.just.agentweb.WebViewClient;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hc_android.hc_css.utils.Constant.UI_FRESH;

/**
 * 问题反馈
 */
public class ProblemFbActivity extends BaseActivity {


    @BindView(R.id.backImg)
    ImageView backImg;
    @BindView(R.id.msg_count_tv)
    TextView msgCountTv;
    @BindView(R.id.title_act_pro)
    TextView titleActPro;
    @BindView(R.id.act_pro_include)
    ConstraintLayout actProInclude;
    @BindView(R.id.webView)
    LinearLayout webView;
    private AgentWeb agentWeb;
    private ValueCallback<Uri[]> uploadMessageAboveL = null;
    private String mCameraFilePath;
    private int REQUEST_CODE_FILE_CHOOSER = 10;
    private LoginEntity.DataBean.InfoBean userBean;

    @Override
    protected void reloadActivity() {

    }

    @Override
    protected View injectTarget() {
        return null;
    }

    @Override
    protected void initData() {
        userBean = BaseApplication.getUserBean();
        String customer=null;
        if (userBean!=null){
            try {
            ProblemEntity problemEntity=new ProblemEntity();
            if (userBean.getHead()!=null)problemEntity.setHead(Address.IMG_URL+userBean.getHead()+"?imageView2/1/w/100/h/100");
            if (userBean.getName()!=null)problemEntity.set名称(userBean.getName());
            if (userBean.getEmail()!=null)problemEntity.set邮箱(userBean.getEmail());
            if (userBean.getEntId()!=0)problemEntity.set团队ID(userBean.getEntId()+"");
            problemEntity.setApp版本(BuildConfig.VERSION_CODE+"");
            problemEntity.set版本(userBean.getCompany()!=null&&userBean.getCompany().getEdition().equals("free")?"免费版":"标准版");
            problemEntity.set设备信息(SystemUtils.getAndroidVersion(this));
            customer= JsonParseUtils.parseToJson(problemEntity);
            }catch (Exception e){}
        }
        String url="https://10003.ahc.ink/chat.html?headHidden=1&customer="+customer+"&uniqueId="+userBean.getId();
        agentWeb = AgentWeb.with(this)
                .setAgentWebParent(webView, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator(0, 0)
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .createAgentWeb()
                .ready()
                .go(url);

        setMsgCount();
    }

    private WebViewClient mWebViewClient = new WebViewClient() {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            //do you  work
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

            if (!NullUtils.isNull(request.getUrl()!=null)){

                try {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    intent.setData(request.getUrl());
                    startActivity(intent);
                    return true;
                }catch (Exception e){}
            }

            return false;

        }

    };
    private WebChromeClient mWebChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            //do you work
        }

        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            uploadMessageAboveL = filePathCallback;
            Log.i(TAG, "调用文件选择");
            if (hasPermission()) {
                showFileChooser();
            }else {
                clearUploadMessage();

            }
            return true;
        }
    };

    /**
     * 打开选择文件/相机
     */
    private void showFileChooser() {

        Intent intent1 = new Intent(Intent.ACTION_PICK, null);
        intent1.setDataAndType(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*;video/*");
//        Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
//        intent1.addCategory(Intent.CATEGORY_OPENABLE);
//        intent1.setType("image/*;video/*");

        Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        mCameraFilePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator +
                System.currentTimeMillis() + ".jpg";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // android7.0注意uri的获取方式改变
            Uri photoOutputUri = FileProvider.getUriForFile(
                    ProblemFbActivity.this,
                    BuildConfig.APPLICATION_ID + ".fileprovider",
                    new File(mCameraFilePath));
            intent2.putExtra(MediaStore.EXTRA_OUTPUT, photoOutputUri);
        } else {
            intent2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(mCameraFilePath)));
        }
//
//        Intent chooser = new Intent(Intent.ACTION_CHOOSER);
//        chooser.putExtra(Intent.EXTRA_TITLE, "File Chooser");
//        chooser.putExtra(Intent.EXTRA_INTENT, intent1);
//        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{intent2});
        startActivityForResult(intent1, REQUEST_CODE_FILE_CHOOSER);
    }

    // 获取文件的真实路径
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_FILE_CHOOSER) {
            Uri result = data == null || resultCode != RESULT_OK ? null : data.getData();
            if (result == null && !TextUtils.isEmpty(mCameraFilePath)) {
                // 看是否从相机返回
                File cameraFile = new File(mCameraFilePath);
                if (cameraFile.exists()) {
                    result = Uri.fromFile(cameraFile);
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, result));
                }
            }
            if (result != null) {
                String path = PickUtils.getPath(this, result);
                if (!TextUtils.isEmpty(path)) {
                    File f = new File(path);
                    if (f.exists() && f.isFile()) {
                        Uri newUri = Uri.fromFile(f);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            if (uploadMessageAboveL != null) {
                                if (newUri != null) {
                                    uploadMessageAboveL.onReceiveValue(new Uri[]{newUri});
                                    uploadMessageAboveL = null;
                                    return;
                                }
                            }
                        }
//                        else if (mUploadCallBack != null) {
//                            if (newUri != null) {
//                                mUploadCallBack.onReceiveValue(newUri);
//                                mUploadCallBack = null;
//                                return;
//                            }
//                        }
                    }
                }
            }
            clearUploadMessage();
            return;
        }
    }

    /**
     * webview没有选择文件也要传null，防止下次无法执行
     */
    private void clearUploadMessage() {
        if (uploadMessageAboveL != null) {
            uploadMessageAboveL.onReceiveValue(null);
            uploadMessageAboveL = null;
        }
//        if (mUploadCallBack != null) {
//            mUploadCallBack.onReceiveValue(null);
//            mUploadCallBack = null;
//        }
    }

    public static Uri getUriFromFile(Context context, File file) {
        if (context == null || file == null) {
            throw new NullPointerException();
        }
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            uri = FileProvider.getUriForFile(context.getApplicationContext(), BuildConfig.APPLICATION_ID, file);
        } else {
            uri = Uri.fromFile(file);
        }
        return uri;
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .keyboardEnable(true)
                .statusBarColor(R.color.theme_app)
                .titleBar(R.id.act_pro_include)
                .init();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_problem_fb;
    }

    @Override
    public void showDataSuccess(Object datas) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onPause() {
        agentWeb.getWebLifeCycle().onPause();
        super.onPause();

    }

    @Override
    protected void onResume() {
        agentWeb.getWebLifeCycle().onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        agentWeb.getWebLifeCycle().onDestroy();
        super.onDestroy();
    }

    @OnClick(R.id.backImg)
    public void onViewClicked(View view) {
        switch (view.getId()){

            case R.id.backImg:
                finish();
                break;
        }
    }

    @Override
    public void onSocketEvent(MessageEvent message) {
        super.onSocketEvent(message);
        MessageEntity messageEntity= (MessageEntity) message.getMsg();
        switch (messageEntity.getAct()){

            case UI_FRESH:
                setMsgCount();
                break;
        }
    }
    /**
     * 设置未读数
     */
    private void setMsgCount() {
        int unReadCount = AppConfig.unReadCount;
        if (unReadCount==0){
            msgCountTv.setVisibility(View.GONE);
        }else {
            msgCountTv.setVisibility(View.VISIBLE);
            msgCountTv.setText(unReadCount+"");
        }
    }
}
