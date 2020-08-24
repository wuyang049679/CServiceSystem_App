package com.hc_android.hc_css.wxapi;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.ui.activity.LoginActivity;
import com.hc_android.hc_css.ui.activity.MainActivity;
import com.hc_android.hc_css.ui.activity.PersonalActivity;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.android.app.AppParam;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

	private static final String WX_APP_ID = AppParam.APPID_WECHAT;
	private static final String WX_APP_SECRET = AppParam.APPSECRET_WECHAT;

	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
	private static final String TAG = "wy_activity";

	public static String wxCode;

	//    private HttpWxLogin httpWxLogin;
	public static String openid;
	public static String accessToken;
	public static String nickname;
	public static String headImgUrl;
	public static String unionId;
	private Dialog mDialog;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

//        mDialog=WebDialogUtils.createWebDialog(this,"登录中");
		//判断是否已经注册到微信
		BaseApplication.mWXapi.handleIntent(getIntent(), this);

	}

	// 微信发送请求到第三方应用时，会回调到该方法
	@Override
	public void onReq(BaseReq baseReq) {
		Log.i(TAG, "BaseReq: " + baseReq.toString());
	}

	// 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
	//app发送消息给微信，处理返回消息的回调
	@Override
	public void onResp(BaseResp baseResp) {
		Log.i(TAG, "onResp: " + baseResp.errStr);
		Log.i(TAG, "onResp: 错误码" + baseResp.errCode);
		//ERR_OK = 0(用户同意) ERR_AUTH_DENIED = -4（用户拒绝授权） ERR_USER_CANCEL = -2（用户取消）
		switch (baseResp.errCode) {
			case BaseResp.ErrCode.ERR_AUTH_DENIED:
				Toast.makeText(this, "用户拒绝授权登录", Toast.LENGTH_SHORT).show();
//                mDialog.dismiss();
				finish();
				break;
			case BaseResp.ErrCode.ERR_USER_CANCEL:
				Toast.makeText(this, "用户取消授权登录", Toast.LENGTH_SHORT).show();
//                mDialog.dismiss();
				finish();
				break;
			case BaseResp.ErrCode.ERR_OK:
				//用户同意授权。
				final String code = ((SendAuth.Resp) baseResp).code;
				final String state = ((SendAuth.Resp) baseResp).state;
				wxCode =code;
				Log.d(TAG, "code: " + code);
				Log.d(TAG, "((SendAuth.Resp) baseResp): " + ((SendAuth.Resp) baseResp).state);
				Intent intent = new Intent();
				if (state.equals("ufile_wx_login_ver")){
				intent.setClass(this, PersonalActivity.class);
				}else if (state.equals("ufile_wx_login")){
				intent.setClass(this, LoginActivity.class);
				}
				intent.putExtra(Constant.CODE,wxCode);
				startActivity(intent);
				finish();
				break;
		}
	}




}