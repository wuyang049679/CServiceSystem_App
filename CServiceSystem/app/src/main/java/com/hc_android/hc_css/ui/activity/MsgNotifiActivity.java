package com.hc_android.hc_css.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationManagerCompat;

import com.gyf.immersionbar.ImmersionBar;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.base.BaseActivity;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.contract.MsgNotifiActivityContract;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.entity.LoginEntity;
import com.hc_android.hc_css.entity.MessageEntity;
import com.hc_android.hc_css.entity.UpdateUserEntity;
import com.hc_android.hc_css.presenter.MsgNotifiActivityPresenter;
import com.hc_android.hc_css.utils.JsonParseUtils;
import com.hc_android.hc_css.utils.android.app.AppConfig;
import com.hc_android.hc_css.utils.socket.MessageEvent;
import com.hc_android.hc_css.wight.ChoiceDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hc_android.hc_css.utils.Constant.UI_FRESH;

public class MsgNotifiActivity extends BaseActivity<MsgNotifiActivityPresenter, IneValuateEntity.DataBean> implements MsgNotifiActivityContract.View, CompoundButton.OnCheckedChangeListener {


    @BindView(R.id.backImg)
    ImageView backImg;
    @BindView(R.id.title_act_chat_set)
    TextView titleActChatSet;
    @BindView(R.id.wechat_switch)
    SwitchCompat wechatSwitch;
    @BindView(R.id.email_switch)
    Switch emailSwitch;
    @BindView(R.id.wechat_lin)
    LinearLayout wechatLin;
    @BindView(R.id.switch_web)
    Switch switchWeb;
    @BindView(R.id.switch_voice)
    Switch switchVoice;
    @BindView(R.id.switch_shock)
    Switch switchShock;
    @BindView(R.id.switch_dialog)
    Switch switchDialog;
    @BindView(R.id.switch_msg)
    Switch switchMsg;
    @BindView(R.id.switch_dialog_input)
    Switch switchDialogInput;
    @BindView(R.id.switch_dialog_output)
    Switch switchDialogOutput;
    @BindView(R.id.act_msg)
    ConstraintLayout actMsg;

    @BindView(R.id.msg_count_tv)
    TextView msgCountTv;

    private LoginEntity.DataBean.InfoBean userEntity;

    @Override
    protected void reloadActivity() {

    }

    @Override
    public MsgNotifiActivityPresenter getPresenter() {
        return new MsgNotifiActivityPresenter();
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
        userEntity = BaseApplication.getUserBean();
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .keyboardEnable(true)
                .statusBarColor(R.color.theme_app)
                .titleBar(R.id.act_msg)
                .init();


        setMsgCount();
        if (userEntity.getAppNotice() != null) {
            if (userEntity.getAppNotice().isPush()) {
                NotificationManagerCompat manager = NotificationManagerCompat.from(BaseApplication.getContext().getApplicationContext());
                boolean isOpened = manager.areNotificationsEnabled();
                // 根据isOpened结果，判断是否需要提醒用户跳转AppInfo页面，去打开App通知权限
                if (isOpened) {
                    wechatSwitch.setChecked(true);
                } else {
                    wechatSwitch.setChecked(false);
                    BaseApplication.getUserBean().getAppNotice().setPush(false);
                }
            } else {
                wechatSwitch.setChecked(false);

            }
            if (userEntity.getAppNotice().isSound()) {
                switchVoice.setChecked(true);
            } else {
                switchVoice.setChecked(false);
            }
            if (userEntity.getAppNotice().isVibration()) {
                switchShock.setChecked(true);
            } else {
                switchShock.setChecked(false);
            }

        }
        if (userEntity.getNotice() != null) {
            if (userEntity.getNotice().isMeanwhile()) {
                switchWeb.setChecked(false);
            } else {
                switchWeb.setChecked(true);
            }
            if (userEntity.getNotice().getRange() != null) {
                LoginEntity.DataBean.InfoBean.NoticeBean.RangeBean range = userEntity.getNotice().getRange();
                if (range.isNewDialog()) {
                    switchDialog.setChecked(true);
                } else {
                    switchDialog.setChecked(false);
                }
                if (range.isNewMessage()) {
                    switchMsg.setChecked(true);
                } else {
                    switchMsg.setChecked(false);
                }
                if (range.isInto()) {
                    switchDialogInput.setChecked(true);
                } else {
                    switchDialogInput.setChecked(false);
                }
                if (range.isTurnOut()) {
                    switchDialogOutput.setChecked(true);
                } else {
                    switchDialogOutput.setChecked(false);
                }
            }
        }
//        wechatSwitch.setOnCheckedChangeListener(this);
        switchDialog.setOnCheckedChangeListener(this);
        switchMsg.setOnCheckedChangeListener(this);
        switchShock.setOnCheckedChangeListener(this);
        switchVoice.setOnCheckedChangeListener(this);
        switchWeb.setOnCheckedChangeListener(this);
        switchDialogInput.setOnCheckedChangeListener(this);
        switchDialogOutput.setOnCheckedChangeListener(this);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_msg_notif;
    }

    @Override
    public void showDataSuccess(IneValuateEntity.DataBean datas) {

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }


    @OnClick({R.id.backImg, R.id.wechat_switch})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backImg:
                finish();
                break;
            case R.id.wechat_switch:
                UpdateUserEntity userEntitys = new UpdateUserEntity();
                LoginEntity.DataBean.InfoBean.AppNoticeBean appNotice = userEntity.getAppNotice();
                LoginEntity.DataBean.InfoBean.NoticeBean notice = userEntity.getNotice();
                boolean b = wechatSwitch.isChecked();

                NotificationManagerCompat manager = NotificationManagerCompat.from(BaseApplication.getContext().getApplicationContext());
                boolean isOpened = manager.areNotificationsEnabled();
                // 根据isOpened结果，判断是否需要提醒用户跳转AppInfo页面，去打开App通知权限
                if (!isOpened) {
                    ChoiceDialog choiceDialog = new ChoiceDialog(this, "手机系统合从客服通知设置未开启,前往开启？", 0);
                    choiceDialog.setCancelCallBack(new ChoiceDialog.ChoiceCancelCallBack() {
                        @Override
                        public void cancelBack() {
                            wechatSwitch.setChecked(false);
                        }

                        @Override
                        public void okBack() {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", BaseApplication.getContext().getApplicationContext().getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    });

                }

                UpdateUserEntity.AppNoticeBean appNoticeBean = new UpdateUserEntity.AppNoticeBean();
                appNoticeBean.setPush(b);
                appNoticeBean.setSound(appNotice.isSound());
                appNoticeBean.setVibration(appNotice.isVibration());
                userEntitys.setAppNotice(appNoticeBean);
                mPresenter.pAccountModify(JsonParseUtils.parseToJson(userEntitys));
                userEntity.getAppNotice().setPush(b);
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        UpdateUserEntity userEntitys = new UpdateUserEntity();
        LoginEntity.DataBean.InfoBean.AppNoticeBean appNotice = userEntity.getAppNotice();
        LoginEntity.DataBean.InfoBean.NoticeBean notice = userEntity.getNotice();

        switch (compoundButton.getId()) {
            case R.id.email_switch:
                break;
            case R.id.wechat_switch:

                NotificationManagerCompat manager = NotificationManagerCompat.from(BaseApplication.getContext().getApplicationContext());
                boolean isOpened = manager.areNotificationsEnabled();
                // 根据isOpened结果，判断是否需要提醒用户跳转AppInfo页面，去打开App通知权限
                if (!isOpened) {
                    ChoiceDialog choiceDialog = new ChoiceDialog(this, "手机系统合从客服通知设置未开启,前往开启？", 0);
                    choiceDialog.setCancelCallBack(new ChoiceDialog.ChoiceCancelCallBack() {
                        @Override
                        public void cancelBack() {
                            wechatSwitch.setChecked(false);
                        }

                        @Override
                        public void okBack() {
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", BaseApplication.getContext().getApplicationContext().getPackageName(), null);
                            intent.setData(uri);
                            startActivity(intent);
                        }
                    });

                }

                UpdateUserEntity.AppNoticeBean appNoticeBean = new UpdateUserEntity.AppNoticeBean();
                appNoticeBean.setPush(b);
                appNoticeBean.setSound(appNotice.isSound());
                appNoticeBean.setVibration(appNotice.isVibration());
                userEntitys.setAppNotice(appNoticeBean);
                mPresenter.pAccountModify(JsonParseUtils.parseToJson(userEntitys));
                userEntity.getAppNotice().setPush(b);
                BaseApplication.getUserBean().getAppNotice().setPush(b);
                break;
            case R.id.switch_web:
                UpdateUserEntity.NoticeBean appNoticeBean_web = new UpdateUserEntity.NoticeBean();
                appNoticeBean_web.setMeanwhile(!b);
                UpdateUserEntity.NoticeBean.RangeBean rangeBean_web = new UpdateUserEntity.NoticeBean.RangeBean();
                rangeBean_web.setInto(notice.getRange().isInto());
                rangeBean_web.setNewDialog(notice.getRange().isNewDialog());
                rangeBean_web.setNewMessage(notice.getRange().isNewMessage());
                rangeBean_web.setTurnOut(notice.getRange().isTurnOut());
                appNoticeBean_web.setRange(rangeBean_web);
                appNoticeBean_web.setSound(notice.getSound());
                userEntitys.setNotice(appNoticeBean_web);
                mPresenter.pAccountModify(JsonParseUtils.parseToJson(userEntitys));
                userEntity.getNotice().setMeanwhile(!b);
                BaseApplication.getUserBean().getNotice().setMeanwhile(!b);
                break;
            case R.id.switch_voice:

                UpdateUserEntity.AppNoticeBean appNoticeBean_voice = new UpdateUserEntity.AppNoticeBean();
                appNoticeBean_voice.setPush(appNotice.isPush());
                appNoticeBean_voice.setSound(b);
                appNoticeBean_voice.setVibration(appNotice.isVibration());
                userEntitys.setAppNotice(appNoticeBean_voice);
                mPresenter.pAccountModify(JsonParseUtils.parseToJson(userEntitys));
                userEntity.getAppNotice().setSound(b);
                BaseApplication.getUserBean().getAppNotice().setSound(b);
                break;
            case R.id.switch_shock:
                UpdateUserEntity.AppNoticeBean appNoticeBean_shock = new UpdateUserEntity.AppNoticeBean();
                appNoticeBean_shock.setPush(appNotice.isPush());
                appNoticeBean_shock.setSound(appNotice.isSound());
                appNoticeBean_shock.setVibration(b);
                userEntitys.setAppNotice(appNoticeBean_shock);
                mPresenter.pAccountModify(JsonParseUtils.parseToJson(userEntitys));
                userEntity.getAppNotice().setVibration(b);
                BaseApplication.getUserBean().getAppNotice().setVibration(b);
                break;
            case R.id.switch_dialog:
                UpdateUserEntity.NoticeBean appNoticeBean_dialog = new UpdateUserEntity.NoticeBean();
                UpdateUserEntity.NoticeBean.RangeBean rangeBean = new UpdateUserEntity.NoticeBean.RangeBean();
                rangeBean.setInto(notice.getRange().isInto());
                rangeBean.setNewDialog(b);
                rangeBean.setNewMessage(notice.getRange().isNewMessage());
                rangeBean.setTurnOut(notice.getRange().isTurnOut());
                appNoticeBean_dialog.setRange(rangeBean);
                appNoticeBean_dialog.setMeanwhile(notice.isMeanwhile());
                appNoticeBean_dialog.setEmail(notice.isEmail());
                appNoticeBean_dialog.setWechat(notice.isWechat());
                appNoticeBean_dialog.setSound(notice.getSound());
                userEntitys.setNotice(appNoticeBean_dialog);
                mPresenter.pAccountModify(JsonParseUtils.parseToJson(userEntitys));
                userEntity.getNotice().getRange().setNewDialog(b);
                BaseApplication.getUserBean().getNotice().getRange().setNewDialog(b);
                break;
            case R.id.switch_msg:
                UpdateUserEntity.NoticeBean appNoticeBean_msg = new UpdateUserEntity.NoticeBean();
                UpdateUserEntity.NoticeBean.RangeBean rangeBean_msg = new UpdateUserEntity.NoticeBean.RangeBean();
                rangeBean_msg.setInto(notice.getRange().isInto());
                rangeBean_msg.setNewDialog(notice.getRange().isNewDialog());
                rangeBean_msg.setNewMessage(b);
                rangeBean_msg.setTurnOut(notice.getRange().isTurnOut());
                appNoticeBean_msg.setRange(rangeBean_msg);
                userEntitys.setNotice(appNoticeBean_msg);
                appNoticeBean_msg.setMeanwhile(notice.isMeanwhile());
                appNoticeBean_msg.setEmail(notice.isEmail());
                appNoticeBean_msg.setWechat(notice.isWechat());
                appNoticeBean_msg.setSound(notice.getSound());
                userEntitys.setNotice(appNoticeBean_msg);
                mPresenter.pAccountModify(JsonParseUtils.parseToJson(userEntitys));
                userEntity.getNotice().getRange().setNewMessage(b);
                BaseApplication.getUserBean().getNotice().getRange().setNewMessage(b);
                break;
            case R.id.switch_dialog_input:
                UpdateUserEntity.NoticeBean appNoticeBean_dialog_input = new UpdateUserEntity.NoticeBean();
                UpdateUserEntity.NoticeBean.RangeBean rangeBean_dialog_input = new UpdateUserEntity.NoticeBean.RangeBean();
                rangeBean_dialog_input.setInto(b);
                rangeBean_dialog_input.setNewDialog(notice.getRange().isNewDialog());
                rangeBean_dialog_input.setNewMessage(notice.getRange().isNewMessage());
                rangeBean_dialog_input.setTurnOut(notice.getRange().isTurnOut());
                appNoticeBean_dialog_input.setRange(rangeBean_dialog_input);
                appNoticeBean_dialog_input.setMeanwhile(notice.isMeanwhile());
                appNoticeBean_dialog_input.setEmail(notice.isEmail());
                appNoticeBean_dialog_input.setWechat(notice.isWechat());
                appNoticeBean_dialog_input.setSound(notice.getSound());
                userEntitys.setNotice(appNoticeBean_dialog_input);
                mPresenter.pAccountModify(JsonParseUtils.parseToJson(userEntitys));
                userEntity.getNotice().getRange().setInto(b);
                BaseApplication.getUserBean().getNotice().getRange().setInto(b);
                break;
            case R.id.switch_dialog_output:
                UpdateUserEntity.NoticeBean appNoticeBean_dialog_output = new UpdateUserEntity.NoticeBean();
                UpdateUserEntity.NoticeBean.RangeBean rangeBean_dialog_output = new UpdateUserEntity.NoticeBean.RangeBean();
                rangeBean_dialog_output.setInto(notice.getRange().isInto());
                rangeBean_dialog_output.setNewDialog(notice.getRange().isNewDialog());
                rangeBean_dialog_output.setNewMessage(notice.getRange().isNewMessage());
                rangeBean_dialog_output.setTurnOut(b);
                appNoticeBean_dialog_output.setRange(rangeBean_dialog_output);
                appNoticeBean_dialog_output.setMeanwhile(notice.isMeanwhile());
                appNoticeBean_dialog_output.setEmail(notice.isEmail());
                appNoticeBean_dialog_output.setWechat(notice.isWechat());
                appNoticeBean_dialog_output.setSound(notice.getSound());
                userEntitys.setNotice(appNoticeBean_dialog_output);
                mPresenter.pAccountModify(JsonParseUtils.parseToJson(userEntitys));
                userEntity.getNotice().getRange().setTurnOut(b);
                BaseApplication.getUserBean().getNotice().getRange().setTurnOut(b);
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
