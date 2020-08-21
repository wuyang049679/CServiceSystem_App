package com.hc_android.hc_css.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.gyf.immersionbar.ImmersionBar;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.api.Address;
import com.hc_android.hc_css.base.BaseActivity;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.contract.PersonalActivityContract;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.entity.LoginEntity;
import com.hc_android.hc_css.entity.MessageEntity;
import com.hc_android.hc_css.entity.ParamEntity;
import com.hc_android.hc_css.entity.TokenEntity;
import com.hc_android.hc_css.entity.UpdateUserEntity;
import com.hc_android.hc_css.presenter.PersonalActivityPresenter;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.JsonParseUtils;
import com.hc_android.hc_css.utils.NullUtils;
import com.hc_android.hc_css.utils.android.ToastUtils;
import com.hc_android.hc_css.utils.android.app.AppConfig;
import com.hc_android.hc_css.utils.android.image.GlideEngine;
import com.hc_android.hc_css.utils.android.image.ImageLoaderManager;
import com.hc_android.hc_css.utils.android.image.UploadFileUtils;
import com.hc_android.hc_css.utils.socket.MessageEvent;
import com.hc_android.hc_css.wight.CustomDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hc_android.hc_css.utils.Constant.UI_FRESH;

/**
 * 个人信息中心
 */
public class PersonalActivity extends BaseActivity<PersonalActivityPresenter, TokenEntity.DataBean> implements PersonalActivityContract.View {


    @BindView(R.id.backImg)
    ImageView backImg;
    @BindView(R.id.title_act_chat_set)
    TextView titleActChatSet;
    @BindView(R.id.act_personal)
    ConstraintLayout actPersonal;
    @BindView(R.id.user_header)
    ImageView userHeader;
    @BindView(R.id.img_jt)
    ImageView imgJt;
    @BindView(R.id.header_click)
    RelativeLayout headerClick;
    @BindView(R.id.name_user)
    TextView nameUser;
    @BindView(R.id.img_jt2)
    ImageView imgJt2;
    @BindView(R.id.gxqm)
    TextView gxqm;
    @BindView(R.id.img_jt3)
    ImageView imgJt3;
    @BindView(R.id.xm)
    TextView xm;
    @BindView(R.id.img_jt4)
    ImageView imgJt4;
    @BindView(R.id.mobile)
    TextView mobile;
    @BindView(R.id.wechat)
    TextView wechat;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.xgmm)
    TextView xgmm;
    @BindView(R.id.personal_nickname)
    RelativeLayout personalNickname;
    @BindView(R.id.lin_gxqm)
    RelativeLayout linGxqm;
    @BindView(R.id.lin_xm)
    RelativeLayout linXm;
    @BindView(R.id.lin_mobile)
    RelativeLayout linMobile;
    @BindView(R.id.lin_wechat)
    RelativeLayout linWechat;
    @BindView(R.id.lin_email)
    RelativeLayout linEmail;
    @BindView(R.id.lin_xgmm)
    RelativeLayout linXgmm;

    private final static int PERSONAL_ACT = 10;
    @BindView(R.id.nick_name)
    TextView nickName;
    @BindView(R.id.autograph)
    TextView autograph;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.mobile_tv)
    TextView mobileTv;
    @BindView(R.id.wechat_tv)
    TextView wechatTv;
    @BindView(R.id.email_tv)
    TextView emailTv;
    @BindView(R.id.progress_header)
    ProgressBar progressHeader;
    @BindView(R.id.msg_count_tv)
    TextView msgCountTv;
    private LoginEntity.DataBean.InfoBean userBean;
    private String cutPath;
    private TextView clickText;
    private CustomDialog customDialog;

    @Override
    protected void reloadActivity() {

    }

    @Override
    public PersonalActivityPresenter getPresenter() {
        return new PersonalActivityPresenter();
    }

    @Override
    protected View injectTarget() {
        return null;
    }

    @Override
    protected void initData() {
        userBean = BaseApplication.getUserBean();
        if (userBean != null) {
//            FrescoUtils.setRoundGif(userHeader, UriUtils.getUri(Constant.ONLINEPIC, Address.IMG_URL + userBean.getHead()));
            String head = null;
            if (!NullUtils.isNull(userBean.getHead())) {
                head = Address.IMG_URL + userBean.getHead();
            } else {
                head = Address.SYSTEM_URL + "image/defaultAvatar.jpeg";
            }
            ImageLoaderManager.loadCircleImage(this, head, userHeader);
            if (!NullUtils.isNull(userBean.getNickname())) {
                nickName.setText(userBean.getNickname());
            }
            if (!NullUtils.isNull(userBean.getAutograph())) {
                autograph.setText(userBean.getAutograph());
            }
            if (!NullUtils.isNull(userBean.getName())) {
                name.setText(userBean.getName());
            }
            if (!NullUtils.isNull(userBean.getTel())) {
                mobileTv.setText(userBean.getTel());
            }
            if (!NullUtils.isNull(userBean.getWechat())) {
                wechatTv.setText("已绑定");
            }
            if (!NullUtils.isNull(userBean.getEmail())) {
                emailTv.setText(userBean.getEmail());
            }
        }

    }

    @Override
    protected void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.theme_app)
                .titleBar(R.id.act_personal)
                .init();
        setMsgCount();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_personal;
    }

    @Override
    public void showDataSuccess(TokenEntity.DataBean datas) {


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.backImg, R.id.header_click, R.id.personal_nickname, R.id.lin_gxqm, R.id.lin_xm, R.id.lin_mobile, R.id.lin_wechat, R.id.lin_email, R.id.lin_xgmm})
    public void onViewClicked(View view) {
        userBean = BaseApplication.getUserBean();
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.backImg:
                onBackPressed();
                break;
            case R.id.header_click:
                PictureSelector.create(this)
                        .openGallery(PictureMimeType.ofImage())
                        .enableCrop(true)
                        .loadImageEngine(GlideEngine.createGlideEngine())
                        .previewImage(true)// 是否可预览图片
                        .previewVideo(true)// 是否可预览视频
                        .isGif(true)
                        .circleDimmedLayer(true)//圆形剪切
                        .selectionMode(PictureConfig.SINGLE)//单选模式
                        .isOriginalImageControl(true)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.personal_nickname:
                clickText = nickName;
                bundle.putString(Constant.INTENT_TYPE, Constant.PERSONAL_);
                bundle.putString(Constant.STYLETYPE, Constant.INPUTTYPE_);
                bundle.putString(Constant._TITLE, "昵称");
                bundle.putString(Constant._INPUT, nickName.getText().toString());
                bundle.putString(Constant._PARAMS, "nickname");
                startActivityForResult(UpdateActivity.class, bundle, PERSONAL_ACT);
                break;
            case R.id.lin_gxqm:
                clickText = autograph;
                bundle.putString(Constant.INTENT_TYPE, Constant.PERSONAL_);
                bundle.putString(Constant.STYLETYPE, Constant.INPUTTYPE_);
                bundle.putString(Constant._TITLE, "个性签名");
                bundle.putString(Constant._INPUT, autograph.getText().toString());
                bundle.putString(Constant._PARAMS, "autograph");
                startActivityForResult(UpdateActivity.class, bundle, PERSONAL_ACT);
                break;
            case R.id.lin_xm:
                clickText = name;
                bundle.putString(Constant.INTENT_TYPE, Constant.PERSONAL_);
                bundle.putString(Constant.STYLETYPE, Constant.INPUTTYPE_);
                bundle.putString(Constant._TITLE, "姓名");
                bundle.putString(Constant._INPUT, name.getText().toString());
                bundle.putString(Constant._PARAMS, "name");
                startActivityForResult(UpdateActivity.class, bundle, PERSONAL_ACT);
                break;
            case R.id.lin_mobile:
                clickText = mobileTv;
                String title1 = "手机号";
                bundle.putString(Constant._TITLE, title1);
                if (NullUtils.isNull(userBean.getTel())) {
                    startActivityForResult(BindActivity.class, bundle, PERSONAL_ACT);
                } else {
                    showDiaLog(title1, bundle);
                }
                break;
            case R.id.lin_wechat:
                clickText = wechatTv;
                String title2 = "微信";
                bundle.putString(Constant._TITLE, title2);
                if (NullUtils.isNull(userBean.getWechat())) {
                    startActivityForResult(BindActivity.class, bundle, PERSONAL_ACT);
                } else {
                    showDiaLog(title2,bundle);
                }
                break;
            case R.id.lin_email:
                clickText = emailTv;
                String title3 = "邮箱";
                bundle.putString(Constant._TITLE, title3);
                if (NullUtils.isNull(userBean.getEmail())) {
                    startActivityForResult(BindActivity.class, bundle, PERSONAL_ACT);
                } else {
                    showDiaLog(title3,bundle);
                }
                break;
            case R.id.lin_xgmm:
                bundle.putString(Constant.INTENT_TYPE, Constant.PERSONAL_);
                bundle.putString(Constant._TITLE, "修改密码");
                startActivity(UpdatePwdActivity.class, bundle);
                break;
        }
    }

    /**
     * 相片回调
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    if (!NullUtils.isEmptyList(selectList)) {
                        LocalMedia localMedia = selectList.get(0);

                        cutPath = localMedia.getCutPath();
                        if (cutPath == null) {
                            if (cutPath == null) {
                                cutPath = localMedia.getOriginalPath();
                            }
                            if (cutPath == null) {
                                cutPath = localMedia.getAndroidQToPath();
                            }
                        }
                        mPresenter.pGetToken("img", localMedia.getMimeType());
//                        Uri uri = Uri.parse("file://" + cutPath);
//                        userHeader.setImageURI(uri);
                        progressHeader.setVisibility(View.VISIBLE);
                        userHeader.setVisibility(View.GONE);
                    }
                    break;
                case PERSONAL_ACT:
                    String content = data.getStringExtra("_CONTENT_TEXT");
                    clickText.setText("未设置");
                    if (!NullUtils.isNull(content)) {
                        clickText.setText(content);
                    }
                    break;
            }
        }
    }

    @Override
    public void showToken(TokenEntity.DataBean dataBean) {
        UploadFileUtils.getInstance().upload(dataBean.getToken(), cutPath, new UpCompletionHandler() {
            @Override
            public void complete(String s, ResponseInfo responseInfo, JSONObject jsonObject) {
                progressHeader.setVisibility(View.GONE);
                userHeader.setVisibility(View.VISIBLE);
                if (responseInfo.isOK()) {
                    try {
                        String content = null;
                        String fileKey = jsonObject.getString("key");
                        UpdateUserEntity updateUserEntity = new UpdateUserEntity();
                        updateUserEntity.setHead(fileKey);
                        userBean.setHead(fileKey);
                        mPresenter.pAccountModify(JsonParseUtils.parseToJson(updateUserEntity));
                    } catch (Exception e) {
                        ToastUtils.showShort("网络错误");
                    }
                } else {
                    ToastUtils.showShort("网络错误");
                }
            }
        });
    }

    @Override
    public void accountModifySuccess(IneValuateEntity.DataBean dataBean) {
        if (userBean.getHead() != null) {
//            FrescoUtils.setRoundGif(userHeader, UriUtils.getUri(Constant.ONLINEPIC, Address.IMG_URL + userBean.getHead()));
            ImageLoaderManager.loadCircleImage(this, Address.IMG_URL + userBean.getHead(), userHeader);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public void onSocketEvent(MessageEvent message) {
        super.onSocketEvent(message);
        MessageEntity messageEntity = (MessageEntity) message.getMsg();
        switch (messageEntity.getAct()) {

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
        if (unReadCount == 0) {
            msgCountTv.setVisibility(View.GONE);
        } else {
            msgCountTv.setVisibility(View.VISIBLE);
            msgCountTv.setText(unReadCount + "");
        }
    }

    /**
     * 点击弹出窗口
     */
    private void showDiaLog(String string, Bundle bundle) {

        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        //点击全部的分类操作
        customDialog = builder.heightDimenRes(R.dimen.popWindow_height_bind)
                .widthDimenRes(R.dimen.popWindow_witch)
                .view(R.layout.bind_state_dialog)
                .style(R.style.Dialog)
                .cancelTouchout(true)
                .addViewOnclick(R.id.state_bind, view -> {
                    customDialog.dismiss();
                })
                .addViewOnclick(R.id.state_change, view -> {

                    startActivity(BindActivity.class, bundle);
                    customDialog.dismiss();
                })
                .build();
        TextView textView = (TextView) customDialog.getView(R.id.state_bind);
        TextView change = (TextView) customDialog.getView(R.id.state_change);
        textView.setText("解除绑定" + string);
        change.setText("更换绑定" + string);
        customDialog.show();
    }
}
