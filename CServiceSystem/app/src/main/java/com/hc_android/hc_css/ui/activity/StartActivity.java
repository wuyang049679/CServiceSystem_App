package com.hc_android.hc_css.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.TimeUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.gyf.immersionbar.ImmersionBar;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.base.BaseActivity;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.contract.StartActivityContract;
import com.hc_android.hc_css.entity.LoginEntity;
import com.hc_android.hc_css.entity.UserEntity;
import com.hc_android.hc_css.presenter.StartActivityPresenter;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.DateUtils;
import com.hc_android.hc_css.utils.JsonParseUtils;
import com.hc_android.hc_css.utils.NullUtils;
import com.hc_android.hc_css.utils.android.SharedPreferencesUtils;
import com.hc_android.hc_css.utils.android.SystemUtils;
import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.hms.aaid.HmsInstanceId;
import com.xiaomi.mipush.sdk.PushMessageHelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.provider.ContactsContract.Intents.Insert.ACTION;

public class StartActivity extends BaseActivity<StartActivityPresenter, LoginEntity.DataBean> implements StartActivityContract.View {


    private String hash;

    @Override
    public StartActivityPresenter getPresenter() {
        return new StartActivityPresenter();
    }

    @Override
    protected void reloadActivity() {

    }

    @Override
    protected View injectTarget() {
        return null;
    }

    @Override
    protected void initData() {
        //判断本地是否有hash来验证是否登录过
        hash = (String) SharedPreferencesUtils.getParam(Constant.HASH, "");

        UserEntity userEntity = BaseApplication.getUserEntity();
        userEntity.setHash(hash);
        BaseApplication.setUserEntity(userEntity);
        //oppo服务器推送


        if (NullUtils.isNull(hash.trim())) {
//            showDataError("请登录");
            Intent intent = new Intent(StartActivity.this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.activity_open, R.anim.activity_close);
            finish();
        } else {
//            mPresenter.pCheckLogin(hash);
            startActivity(MainActivity.class);
            overridePendingTransition(R.anim.activity_open, R.anim.activity_close);
            finish();

        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        if (intent !=null) {
            // 获取的值做打点统计

            String msgid = intent.getStringExtra("_push_msgid");
            String cmdType = intent.getStringExtra("_push_cmd_type");
            int notifyId = intent.getIntExtra("_push_notifyid", -1);
            Log.i(TAG, "receive data from push, intent = " + intent);
            // 获取data里的值
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String androidData = bundle.getString("androidData");

                for (String key : bundle.keySet()) {
                    String content = bundle.getString(key);
                    Log.i(TAG, "receive data from push, key = " + key + ", content = " + content);
                }
                Log.i(TAG, "receive data from push, androidData = " + androidData);
            }
            Log.i(TAG, "receive data from push, msgId = " + msgid + ", cmd = " + cmdType + ", notifyId = " + notifyId);

        } else {
            Log.i(TAG, "intent is null");
        }
    }

    @Override
    protected void initView() {
        //沉浸式状态栏
//        ImmersionBar.with(this)
////                .statusBarColor(R.color.white)
//                .init();

        Log.i(TAG,"获取设备信息"+ android.os.Build.MODEL);

    }

    @Override
    public int getContentView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) { //适配全面屏
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            getWindow().setAttributes(lp);

        }
        return R.layout.activity_start;

    }


    @Override
    public void showDataError(String errorMessage) {
        Intent intent = new Intent(StartActivity.this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.activity_open, R.anim.activity_close);
        finish();
    }



    @Override
    public void showDataSuccess(LoginEntity.DataBean datas) {
//        UserEntity userEntity = new UserEntity();
//        userEntity.setHash(datas.getHash());
//        userEntity.setUserbean(datas.getInfo());
//        BaseApplication.setUserEntity(userEntity);
//        startActivity(MainActivity.class);
//        overridePendingTransition(R.anim.activity_open, R.anim.activity_close);
//        finish();
    }

}
