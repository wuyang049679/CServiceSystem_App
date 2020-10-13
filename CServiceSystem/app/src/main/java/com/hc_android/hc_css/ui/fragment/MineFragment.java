package com.hc_android.hc_css.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.gyf.immersionbar.ImmersionBar;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.api.Address;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.base.BaseConfig;
import com.hc_android.hc_css.base.BaseFragment;
import com.hc_android.hc_css.contract.MineFragmentContract;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.entity.LoginEntity;
import com.hc_android.hc_css.entity.MessageEntity;
import com.hc_android.hc_css.entity.ParamEntity;
import com.hc_android.hc_css.presenter.MineFragmentPresenter;
import com.hc_android.hc_css.ui.activity.HistoryActivity;
import com.hc_android.hc_css.ui.activity.LoginActivity;
import com.hc_android.hc_css.ui.activity.MsgNotifiActivity;
import com.hc_android.hc_css.ui.activity.PersonalActivity;
import com.hc_android.hc_css.ui.activity.ProblemFbActivity;
import com.hc_android.hc_css.ui.activity.realauth.DisclaimerActivity;
import com.hc_android.hc_css.ui.activity.realauth.RealAuthenActivity;
import com.hc_android.hc_css.ui.activity.realauth.RemStateActivity;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.NullUtils;
import com.hc_android.hc_css.utils.android.SharedPreferencesUtils;
import com.hc_android.hc_css.utils.android.image.ImageLoaderManager;
import com.hc_android.hc_css.utils.socket.EventServiceImpl;
import com.hc_android.hc_css.utils.socket.MessageEvent;
import com.hc_android.hc_css.wight.CustomDialog;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static android.app.Activity.RESULT_OK;
import static com.hc_android.hc_css.utils.Constant.EVENTBUS_NOTIFICATION_STATE;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_INPUTING;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_JOIN;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_LEAVE;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_LOGINSUC;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_NEW;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_NEWDIALOG;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_OFFLINE;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_ONLINE;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_REALTIME_ADD;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_REALTIME_DEL;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_REALTIME_MODIFY;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_RECEPTION;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_REPORT_STATE;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_SERVICELEAVE;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_SERVICEONLY;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_STATEUPATE;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_UPATEDIALOG;
import static com.hc_android.hc_css.utils.Constant.UI_FRESH;
import static com.hc_android.hc_css.utils.socket.MessageEventType.EventMessage;

public class MineFragment extends BaseFragment<MineFragmentPresenter, IneValuateEntity.DataBean> implements MineFragmentContract.View {


    @BindView(R.id.backImg)
    ImageView backImg;
    @BindView(R.id.backLayout)
    LinearLayout backLayout;
    @BindView(R.id.conmonTitleTextView)
    TextView conmonTitleTextView;
    @BindView(R.id.img_jt)
    ImageView imgJt;
    @BindView(R.id.text_name)
    TextView textName;
    @BindView(R.id.text_qm)
    TextView textQm;
    @BindView(R.id.header_lin)
    RelativeLayout headerLin;
    @BindView(R.id.zxzt)
    TextView zxzt;
    @BindView(R.id.img_jt2)
    ImageView imgJt2;
    @BindView(R.id.jsjr)
    TextView jsjr;
    @BindView(R.id.img_jt3)
    ImageView imgJt3;
    @BindView(R.id.zxzt_lin)
    RelativeLayout zxztLin;
    @BindView(R.id.xxtz_lin)
    RelativeLayout xxtzLin;
    @BindView(R.id.lsdh_lin)
    RelativeLayout lsdhLin;
    @BindView(R.id.jsdh_lin)
    RelativeLayout jsdhLin;
    @BindView(R.id.tcdl_lin)
    RelativeLayout tcdlLin;
    @BindView(R.id.online_state)
    TextView onlineState;
    @BindView(R.id.state_hint)
    TextView stateHint;
    @BindView(R.id.user_iv)
    ImageView userIv;
    @BindView(R.id.tv_xxtz)
    TextView tvXxtz;
    @BindView(R.id.img_xxtz)
    ImageView imgXxtz;
    @BindView(R.id.notice_state)
    TextView noticeState;
    @BindView(R.id.smrz)
    TextView smrz;
    @BindView(R.id.img_jt6)
    ImageView imgJt6;
    @BindView(R.id.smrz_lin)
    RelativeLayout smrzLin;
    @BindView(R.id.smrz_tv)
    TextView smrzTv;

    private LoginEntity.DataBean.InfoBean userBean;
    private final static int MINE_FG = 100;
    private String STATE_ONLINE;//在线状态
    private CustomDialog customDialog;

    public static MineFragment newInstance() {

        Bundle args = new Bundle();

        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public MineFragmentPresenter getPresenter() {
        return new MineFragmentPresenter();
    }

    @Override
    protected int injectTarget() {
        return 0;
    }

    @Override
    protected void doRetry() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.mine_fragment;
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.theme_app)
                .titleBar(R.id.fg_mine_include)
                .init();
        conmonTitleTextView.setText(getHcActivity().getResources().getString(R.string.text_mine));
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        userBean = BaseApplication.getUserBean();
        if (userBean != null) {
//            FrescoUtils.setRoundGif(userHeader, UriUtils.getUri(Constant.ONLINEPIC, Address.IMG_URL + userBean.getHead()));
            String head = null;
            if (!NullUtils.isNull(userBean.getHead())) {
                head = Address.IMG_URL + userBean.getHead();
            } else {
                head = Address.SYSTEM_URL + "image/defaultAvatar.jpeg";
            }
            ImageLoaderManager.loadCircleImage(getHcActivity(), head, userIv);
            if (!NullUtils.isNull(userBean.getNickname())) {
                textName.setText(userBean.getNickname());
            }
            if (!NullUtils.isNull(userBean.getAutograph())) {
                textQm.setText(userBean.getAutograph());
            }
            setRealName();
//            if (userBean.getAppNotice().isPush()) {
//                NotificationManagerCompat manager = NotificationManagerCompat.from(BaseApplication.getContext().getApplicationContext());
//                boolean isOpened = manager.areNotificationsEnabled();//系统是否开启消息推送判断
//                if (isOpened) {
//                    noticeState.setVisibility(View.GONE);
//                }else {
//                    noticeState.setVisibility(View.VISIBLE);
//                }
//            }else {
//                noticeState.setVisibility(View.VISIBLE);
//            }


        }
    }

    /**
     * 设置实名认证状态
     */

    public void setRealName() {
        userBean = BaseApplication.getUserBean();
        if (smrzTv == null)return;
        smrzTv.setText("");
        smrzLin.setClickable(true);
        smrzLin.setEnabled(true);
        imgJt6.setVisibility(View.VISIBLE);
        if (userBean.getCompany() != null && userBean.getCompany().getRealNameAuth() != null) {
            LoginEntity.DataBean.InfoBean.CompanyBean.RealNameAuthBean realNameAuth = userBean.getCompany().getRealNameAuth();
            if (realNameAuth.getComplestatus() != null && realNameAuth.getComplestatus().equals("loading")) {
                smrzTv.setText("等待打款确认");
                smrzTv.setTextColor(getHcActivity().getResources().getColor(R.color.theme_app));
            }
            if (realNameAuth.getRemittance() != null && realNameAuth.getRemittance().getState().equals("remited")) {
                smrzTv.setText("已打款请确认");
                smrzTv.setTextColor(getHcActivity().getResources().getColor(R.color.theme_app));
            }
            if (realNameAuth.getAuthId() != null) {
                smrzLin.setClickable(false);
                smrzLin.setEnabled(false);
                imgJt6.setVisibility(View.GONE);
                smrzTv.setText("已完成个人实名认证");
                smrzTv.setTextColor(getHcActivity().getResources().getColor(R.color.green_btn));
            }
            if (realNameAuth.getComplestatus() != null && realNameAuth.getComplestatus().equals("complete")) {
                smrzLin.setClickable(false);
                smrzLin.setEnabled(false);
                imgJt6.setVisibility(View.GONE);
                smrzTv.setTextColor(getHcActivity().getResources().getColor(R.color.green_btn));
                smrzTv.setText("已完成个人实名认证");
                if (realNameAuth.getType()!=null && realNameAuth.getType().equals("legalperson")){
                smrzTv.setText("已完成企业实名认证");
                }
                if (realNameAuth.getType()!=null && realNameAuth.getType().equals("corporatebank")){
                smrzTv.setText("已完成企业实名认证");
                }
            }


        }
    }

    /**
     * 设置在线状态
     */
    private void setOnlineState() {
        Log.i(TAG, "setOnlineState:" + userBean.getState());
        if (!NullUtils.isNull(userBean.getState())) {
            if (userBean.getState().equals("on")) {
                onlineState.setText("在线");
                onlineState.setTextColor(getResources().getColor(R.color.green_btn));
                stateHint.setText("下班后记得设置成隐身哦");
                stateHint.setVisibility(View.VISIBLE);

            } else if ((userBean.getState().equals("off"))) {
                onlineState.setText("隐身");
                onlineState.setTextColor(getResources().getColor(R.color.red));
                stateHint.setText("设置成在线才会分配对话");
                stateHint.setVisibility(View.VISIBLE);
            } else if ((userBean.getState().equals("break"))) {
                onlineState.setText("离线");
                onlineState.setTextColor(getResources().getColor(R.color.black_aa));
                stateHint.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void showDataError(String errorMessage) {

    }

    @Override
    public void showDataSuccess(IneValuateEntity.DataBean datas) {
        EventServiceImpl.getInstance().disconnect();
        Intent intent = new Intent(getHcActivity(), LoginActivity.class);
        startActivity(intent);
        getHcActivity().finish();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == MINE_FG) {
                String content = data.getStringExtra("_CONTENT_TEXT");
                if (content != null) {
//                    String hash = (String) SharedPreferencesUtils.getParam(Constant.HASH, "");
//                    try {
//
//                        String state = "";
//                        if (content.equals("在线")) {
//                            EventServiceImpl.getInstance().disconnect();
//                            EventServiceImpl.getInstance().connect(hash);
//                            state = "on";
//                            BaseApplication.getUserBean().setState(state);
//                        } else if ((content.equals("隐身"))) {
//                            EventServiceImpl.getInstance().disconnect();
//                            EventServiceImpl.getInstance().connect(hash);
//                            state = "off";
//                            BaseApplication.getUserBean().setState(state);
//                        } else if ((content.equals("离线"))) {
//                            state = "break";
//                            EventServiceImpl.getInstance().disconnect();
//                            BaseConfig.setStateChange(state, false);
//                        }
//
//                    }catch (Exception e){}

                } else {
//                    FrescoUtils.setRoundGif(userHeader, UriUtils.getUri(Constant.ONLINEPIC, Address.IMG_URL + userBean.getHead()));//fresco圆形gif无效
                    String head;
                    if (!NullUtils.isNull(userBean.getHead())) {
                        head = Address.IMG_URL + userBean.getHead();
                    } else {
                        head = Address.SYSTEM_URL + "image/defaultAvatar.jpeg";
                    }
                    ImageLoaderManager.loadCircleImage(getHcActivity(), head, userIv);
                    textName.setText(userBean.getNickname());
                    textQm.setText(userBean.getAutograph());
                }
            }
        }
//        if (userBean.getAppNotice().isPush()) {
//            noticeState.setVisibility(View.GONE);
//        }else {
//            noticeState.setVisibility(View.VISIBLE);
//        }
    }

    @OnClick({R.id.header_lin, R.id.zxzt_lin, R.id.xxtz_lin, R.id.lsdh_lin, R.id.jsdh_lin, R.id.tcdl_lin, R.id.smrz_lin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.header_lin:
                startActivityForResult(PersonalActivity.class, MINE_FG);
                break;
            case R.id.zxzt_lin:
                showDiaLog();
                break;
            case R.id.xxtz_lin:
                startActivityForResult(MsgNotifiActivity.class, MINE_FG);
                break;
            case R.id.lsdh_lin:
                startActivity(HistoryActivity.class);
                break;
            case R.id.jsdh_lin:
                startActivity(ProblemFbActivity.class);
                break;
            case R.id.tcdl_lin:
                mPresenter.pLogout();
                break;
            case R.id.smrz_lin:
                if (smrzTv.getText().toString().equals("等待打款确认") || smrzTv.getText().toString().equals("已打款请确认")) {
                    startActivity(RemStateActivity.class);
                } else {
                    startActivity(DisclaimerActivity.class);
                }
                break;
        }
    }

    /**
     * 点击弹出窗口
     */
    private void showDiaLog() {

        String s = onlineState.getText().toString();
        ArrayList<ParamEntity> paramEntities = new ArrayList();
        List<String> stringlist = Arrays.asList(getResources().getStringArray(R.array.online_status));
        for (int i = 0; i < stringlist.size(); i++) {
            ParamEntity paramEntity = new ParamEntity(stringlist.get(i));
            if (stringlist.get(i).equals(s)) {
                paramEntity.setChecked(true);
            }
            paramEntities.add(paramEntity);
        }

        CustomDialog.Builder builder = new CustomDialog.Builder(getHcActivity());
        //点击全部的分类操作
        customDialog = builder.heightDimenRes(R.dimen.popWindow_height_state)
                .widthDimenRes(R.dimen.popWindow_witch)
                .view(R.layout.online_state_dialog)
                .style(R.style.Dialog)
                .cancelTouchout(true)
                .addViewOnclick(R.id.state_on, view -> {
                    mPresenter.pAccountState("on");
                    STATE_ONLINE = "on";
                    customDialog.dismiss();
                })
                .addViewOnclick(R.id.state_off, view -> {
                    mPresenter.pAccountState("off");
                    STATE_ONLINE = "off";
                    customDialog.dismiss();
                })
                .addViewOnclick(R.id.state_break, view -> {
                    mPresenter.pAccountState("break");
                    STATE_ONLINE = "break";
                    customDialog.dismiss();
                })
                .build();

        customDialog.show();
    }

    @Override
    public void onSocketEvent(MessageEvent msg) {
        super.onSocketEvent(msg);


        MessageEntity message = (MessageEntity) msg.getMsg();
        if (msg.getType() == EventMessage) ;
        switch (message.getAct()) {
            //连接成功
            case MESSAGE_LOGINSUC:
                //登录连接成功获取对话列表，并加入房间

                break;
            //加入房间
            case MESSAGE_JOIN:
                break;
            //结束对话后退出房间
            case MESSAGE_LEAVE:

                break;
            //只有客服能收到的退出房间消息
            case MESSAGE_SERVICELEAVE:
                break;
            //收到新消息
            case MESSAGE_NEW:

                break;
            //新对话加入
            case MESSAGE_NEWDIALOG:

                break;
            //接收顾客输入的文字
            case MESSAGE_INPUTING:
                break;
            //顾客上线
            case MESSAGE_ONLINE:

                break;
            //顾客离线
            case MESSAGE_OFFLINE:

                break;
            //对话被接待
            case MESSAGE_RECEPTION:

                break;
            //客服账号登录唯一性验证//保存serviceId
            case MESSAGE_SERVICEONLY:

                break;
            //实时访客增加
            case MESSAGE_REALTIME_ADD:

                break;
            //实时访客修改
            case MESSAGE_REALTIME_MODIFY:

                break;
            //实时访客删除
            case MESSAGE_REALTIME_DEL:

                break;
            //客服在线状态变化
            case MESSAGE_STATEUPATE:
                if (message.getServiceId() != null && message.getServiceId().equals(userBean.getId())) {
                    userBean.setState(message.getState());
//                    setOnlineState(true);
                }
                break;
            //更新对话（团队协作可能更新）
            case MESSAGE_UPATEDIALOG:

                break;
            //收到客服汇报的在线状态
            case MESSAGE_REPORT_STATE:
                break;
            //UI刷新
            case UI_FRESH:

                break;
            //全局状体变化
            case EVENTBUS_NOTIFICATION_STATE:
                setOnlineState();
                break;
        }
    }

    @Override
    public void showState(IneValuateEntity.DataBean dataBean) {
        String hash = (String) SharedPreferencesUtils.getParam(Constant.HASH, "");
        switch (STATE_ONLINE) {
            case "on":
                try {
                    EventServiceImpl.getInstance().connect(hash);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                BaseApplication.getUserBean().setState("on");
                break;
            case "off":
                try {
                    EventServiceImpl.getInstance().connect(hash);
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
                BaseApplication.getUserBean().setState("off");
                break;
            case "break":
                EventServiceImpl.getInstance().disconnect();
                BaseConfig.setStateChange("break", false, BaseConfig.ONLINE_STATE_DEFULTE);
                break;

        }
    }
}
