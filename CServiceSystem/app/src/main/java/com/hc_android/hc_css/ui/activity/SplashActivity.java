package com.hc_android.hc_css.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.hc_android.hc_css.R;
import com.hc_android.hc_css.utils.Constant;

public class SplashActivity extends Activity {

    private String dialogId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) { //适配全面屏
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
            getWindow().setAttributes(lp);

        }
        setContentView(R.layout.activity_splash);
        Intent intent = getIntent();
        //小米，oppo
        if (intent !=null)dialogId = getIntent().getStringExtra(Constant.DIALOGID);
        //华为
        if (intent !=null && intent.getData()!=null) {
//            Log.i(TAG, "name1:"+intent.getData());
            // 方法1（参数之间用“&”相连）设置的数据通过如下方式获取
            String name1 = intent.getData().getQueryParameter("name");
//            int age1 = Integer.parseInt(intent.getData().getQueryParameter("age"));
//            // 方法2（intent直接添加参数）设置的数据通过如下方式获取
            String name2 = intent.getStringExtra("name");
//            int age2 = intent.getIntExtra("age", -1);

            dialogId = getIntent().getStringExtra(Constant.DIALOGID);

        }
        Log.i("wy_activity", "SplashActivity-dialogId:"+dialogId);
        Intent intent1 = new Intent(this,MainActivity.class);
//        intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent1.putExtra(Constant.DIALOGID,dialogId);
        startActivity(intent1);
        finish();
    }
}