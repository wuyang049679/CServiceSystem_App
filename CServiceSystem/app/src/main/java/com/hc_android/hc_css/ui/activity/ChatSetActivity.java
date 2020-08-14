package com.hc_android.hc_css.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.adapter.CardListAdapter;
import com.hc_android.hc_css.adapter.PopWindowListAdapter;
import com.hc_android.hc_css.base.BaseActivity;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.contract.ChatSetActivityContract;
import com.hc_android.hc_css.entity.BlackEntity;
import com.hc_android.hc_css.entity.CardEntity;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.entity.MessageDialogEntity;
import com.hc_android.hc_css.entity.MessageEntity;
import com.hc_android.hc_css.entity.ReceptionEntity;
import com.hc_android.hc_css.entity.TeamEntity;
import com.hc_android.hc_css.entity.TextListEntity;
import com.hc_android.hc_css.presenter.ChatSetActivityPresenter;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.JsonParseUtils;
import com.hc_android.hc_css.utils.NullUtils;
import com.hc_android.hc_css.utils.android.app.AppConfig;
import com.hc_android.hc_css.utils.socket.MessageEvent;
import com.hc_android.hc_css.wight.CustomDialog;
import com.hc_android.hc_css.wight.LocalDataSource;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.hc_android.hc_css.utils.Constant.UI_FRESH;

public class ChatSetActivity extends BaseActivity<ChatSetActivityPresenter, CardEntity.DataBean> implements ChatSetActivityContract.View, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.backImg)
    ImageView backImg;
    @BindView(R.id.title_act_chat_set)
    TextView titleActChatSet;
    @BindView(R.id.act_chatSet)
    ConstraintLayout actChatSet;
    @BindView(R.id.ly)
    TextView ly;
    @BindView(R.id.lyy)
    TextView lyy;
    @BindView(R.id.zly)
    TextView zly;
    @BindView(R.id.tv_zly)
    TextView tvZly;
    @BindView(R.id.dhy)
    TextView dhy;
    @BindView(R.id.tv_dhy)
    TextView tvDhy;
    @BindView(R.id.bq)
    TextView bq;
    @BindView(R.id.tv_bq)
    TextView tvBq;
    @BindView(R.id.yj)
    ImageView yj;
    @BindView(R.id.lin_bq)
    RelativeLayout linBq;
    @BindView(R.id.bj)
    TextView bj;
    @BindView(R.id.tv_bj)
    TextView tvBj;
    @BindView(R.id.qj_bj)
    ImageView qjBj;
    @BindView(R.id.lin_bj)
    RelativeLayout linBj;
    @BindView(R.id.ip_adress)
    TextView ipAdress;
    @BindView(R.id.tv_ip)
    TextView tvIp;
    @BindView(R.id.llq)
    TextView llq;
    @BindView(R.id.tv_llq)
    TextView tvLlq;
    @BindView(R.id.pmcc)
    TextView pmcc;
    @BindView(R.id.tv_pmcc)
    TextView tvPmcc;
    @BindView(R.id.sb)
    TextView sb;
    @BindView(R.id.tv_sb)
    TextView tvSb;
    @BindView(R.id.zgts)
    TextView zgts;
    @BindView(R.id.lin_zgts)
    RelativeLayout linZgts;
    @BindView(R.id.jsdh)
    TextView jsdh;
    @BindView(R.id.lin_jsdh)
    RelativeLayout linJsdh;
    @BindView(R.id.jrhmd)
    TextView jrhmd;
    @BindView(R.id.lin_jhmd)
    RelativeLayout linJhmd;
    @BindView(R.id.card_recycler)
    RecyclerView cardRecycler;
    @BindView(R.id.fw_lin)
    LinearLayout fwLin;
    @BindView(R.id.qt_lin)
    LinearLayout qtLin;
    @BindView(R.id.msg_count_tv)
    TextView msgCountTv;
    @BindView(R.id.wechat_recycler)
    RecyclerView wechatRecycler;
    @BindView(R.id.wechat_lin)
    LinearLayout wechatLin;
    @BindView(R.id.fftg)
    TextView fftg;

    private TextView clickText;
    private MessageDialogEntity.DataBean.ListBean itembean;
    private CardListAdapter cardListAdapter;
    private CardListAdapter wechatListAdapter;
    private List<TextListEntity> textList;
    private List<TextListEntity> weChatTextList;
    private PopWindowListAdapter popWindowListAdapter;
    private TextView titleText;
    private static final int CHATSET_ACT = 20;
    private static String INTENT_TYPE;

    @Override
    public ChatSetActivityPresenter getPresenter() {
        return new ChatSetActivityPresenter();
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
        itembean = LocalDataSource.getITEMBEAN();

        if (!itembean.getSource().equals("web") && !itembean.getSource().equals("link")) {
            fwLin.setVisibility(View.GONE);
            qtLin.setVisibility(View.GONE);
        }
        if (itembean.getCustomer().isBlack()) {//判断是否是黑名单

            jrhmd.setText("移出黑名单");
        }
        textList = new ArrayList<>();
        weChatTextList = new ArrayList<>();
        mPresenter.pGetCardList();
        cardListAdapter = new CardListAdapter(textList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        cardRecycler.setLayoutManager(layoutManager);
        cardRecycler.setAdapter(cardListAdapter);
        wechatListAdapter = new CardListAdapter(weChatTextList);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        wechatRecycler.setLayoutManager(layoutManager2);
        wechatRecycler.setAdapter(wechatListAdapter);
        cardListAdapter.setOnItemChildClickListener(this);

        //设置设备信息
        if (itembean.getDevice() != null) {
            MessageDialogEntity.DataBean.ListBean.DeviceBean device = itembean.getDevice();
            tvIp.setText(device.getIp());
            tvLlq.setText(device.getBrowser());
            tvSb.setText(device.getSystem());
            tvPmcc.setText(device.getWidth() + "x" + device.getHeight());
            //是否展示付费推广
            if (itembean.getDevice().isPromote()) {
                fftg.setVisibility(View.VISIBLE);
            }
        }

        //备注
        if (itembean.getRemarks() != null) {
            tvBj.setText(itembean.getRemarks());
        }

        //设置tag

        List<String> tag = itembean.getCustomer().getTag();
        if (!NullUtils.isEmptyList(tag)) {
            tvBq.setText(getTagString(tag));
        }

        //来源信息
        if (itembean.getAnalysisUrl() != null) {

            if (itembean.getAnalysisUrl().getSource() != null) {
                String title = itembean.getAnalysisUrl().getSource().getTitle();
                lyy.setText(title != null ? title : "");
                lyy.setTextColor(getResources().getColor(R.color.break_tv));
                setClickIntent(lyy, itembean.getAnalysisUrl().getSource().getUrl());
            }

            if (itembean.getAnalysisUrl().getCurrent() != null) {
                String title = itembean.getAnalysisUrl().getCurrent().getTitle();
                tvDhy.setText(title != null ? title : "");
                setClickIntent(tvDhy, itembean.getAnalysisUrl().getCurrent().getUrl());
            }
            if (itembean.getAnalysisUrl().getEntrance() != null) {
                String title = itembean.getAnalysisUrl().getEntrance().getTitle();
                tvZly.setText(title != null ? title : "");
                setClickIntent(tvZly, itembean.getAnalysisUrl().getEntrance().getUrl());
            }
        }

        //微信公众号或者小程序
        if (itembean.getWechatInfo() != null) {
            wechatLin.setVisibility(View.VISIBLE);
            if (itembean.getWechatInfo().getName() != null) {
                String title = "来源";
                if (itembean.getSource().equals("program")) title = "小程序";
                if (itembean.getSource().equals("wechat")) title = "公众号";
                weChatTextList.add(new TextListEntity(title, itembean.getWechatInfo().getName(), Constant.CARD_BROSWER));
            }
            List<MessageDialogEntity.DataBean.ListBean.WechatInfoBean.ParasBean> paras = itembean.getWechatInfo().getParas();
            if (!NullUtils.isEmptyList(paras)) {
                for (MessageDialogEntity.DataBean.ListBean.WechatInfoBean.ParasBean para : paras) {
                    weChatTextList.add(new TextListEntity(para.getTitle(), para.getValue(), Constant.CARD_BROSWER));
                }
                wechatListAdapter.notifyDataSetChanged();
            }
        }
    }


    @Override
    protected void initView() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .keyboardEnable(true)
                .statusBarColor(R.color.theme_app)
                .titleBar(R.id.act_chatSet)
                .init();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            INTENT_TYPE = extras.getString(Constant.INTENT_TYPE);
        }
        if (!NullUtils.isNull(INTENT_TYPE) && INTENT_TYPE.equals(Constant.HISTORYACT_)) {
            linZgts.setVisibility(View.GONE);
            linJsdh.setVisibility(View.GONE);
        }
        if (!NullUtils.isNull(INTENT_TYPE) && INTENT_TYPE.equals(Constant.NOTRECEIVEDACT_)) {
            linZgts.setVisibility(View.GONE);
        }
        if (BaseApplication.getUserBean()!= null && BaseApplication.getUserBean().getAuthority() != null && !BaseApplication.getUserBean().getAuthority().isAssist()) {
            linZgts.setVisibility(View.GONE);
        }
        setMsgCount();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_chat_set;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.backImg, R.id.lin_bq, R.id.lin_bj, R.id.lin_zgts, R.id.lin_jsdh, R.id.lin_jhmd})
    public void onViewClicked(View view) {

        List<String> stringList = new ArrayList<>();
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.backImg:
                finish();
                break;
            case R.id.lin_bq:
                clickText = tvBq;
                if (!NullUtils.isEmptyList(itembean.getCustomer().getTag())) {
                    stringList.addAll(itembean.getCustomer().getTag());
                }

                bundle.putString(Constant.INTENT_TYPE, Constant.CHATACT_);
                bundle.putString(Constant.STYLETYPE, Constant.LISTTYPE_);
                bundle.putString(Constant._TITLE, "标签");
                bundle.putStringArrayList(Constant._LISTSTRING, (ArrayList<String>) stringList);
                startActivityForResult(UpdateActivity.class, bundle, CHATSET_ACT);
                break;
//            case R.id.lin_name:
//                clickText = tvName;
//                stringList.add(tvName.getText().toString());
//                UpdateActivity.activityStart(ChatSetActivity.this, "更改名称", stringList, UpdateActivity.INPUTTYPE);
//                break;
//            case R.id.lin_yx:
//                clickText = tvYx;
//                stringList.add(tvYx.getText().toString());
//                UpdateActivity.activityStart(ChatSetActivity.this, "更改邮箱", stringList, UpdateActivity.INPUTTYPE);
//                break;
//            case R.id.lin_sj:
//                clickText = tvSj;
//                stringList.add(tvSj.getText().toString());
//                UpdateActivity.activityStart(ChatSetActivity.this, "更改手机", stringList, UpdateActivity.INPUTTYPE);
//                break;
//            case R.id.lin_ms:
//                clickText = tvMs;
//                stringList.add(tvMs.getText().toString());
//                UpdateActivity.activityStart(ChatSetActivity.this, "更改" + ms.getText(), stringList, UpdateActivity.INPUTTYPE);
//                break;
            case R.id.lin_bj:
                clickText = tvBj;

                bundle.putString(Constant.INTENT_TYPE, Constant.CHATACT_);
                bundle.putString(Constant.STYLETYPE, Constant.INPUTTYPE_);
                bundle.putString(Constant._TITLE, "备注");
                bundle.putString(Constant._INPUT, tvBj.getText().toString());
                startActivityForResult(UpdateActivity.class, bundle, CHATSET_ACT);

                break;
            case R.id.lin_zgts:
                createDiaLog();
                break;
            case R.id.lin_jsdh:
                mPresenter.pEndDialog(itembean.getId(), null, null);
                break;
            case R.id.lin_jhmd:
                if (itembean.getCustomer().isBlack()) {
                    mPresenter.pBlackDel(itembean.getId(), itembean.getCustomerId());
                } else {
                    BlackEntity blackEntity = new BlackEntity();
                    blackEntity.setCustomerId(itembean.getCustomerId());
                    blackEntity.setServiceId(BaseApplication.getUserBean().getId());
                    blackEntity.setType("customer");
                    String toJson = JsonParseUtils.parseToJson(blackEntity);
                    mPresenter.pBlackAdd(itembean.getId(), toJson);
                }
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            if (requestCode == CHATSET_ACT) {
                String content = data.getStringExtra("_CONTENT_TEXT");
                clickText.setText("未设置");
                if (NullUtils.isNull(content)) {
                    clickText.setText("未设置");
                    if (titleText != null && titleText.getText().toString().equals("名称")) {
                        clickText.setText("#" + itembean.getCustomer().getNumberId());
                    }
                } else {
                    clickText.setText(content);
                }
                //列表集合是否有数据
                ArrayList<String> contents = data.getStringArrayListExtra("_CONTENT_LIST");
                if (!NullUtils.isEmptyList(contents)) {
                    itembean.setTag(contents);
                    clickText.setText(getTagString(contents));
                }
            }
        }
    }

    @Override
    public void showDataSuccess(CardEntity.DataBean datas) {
        if (!NullUtils.isEmptyList(datas.getList())) {
            List<CardEntity.DataBean.ListBean> list = datas.getList();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getCore() != null && list.get(i).getCore().equals("name")) {//默认设置numberId为名称
                    TextListEntity textListEntity = new TextListEntity("#" + itembean.getCustomer().getNumberId(), list.get(i).getName());
                    textListEntity.setItemType(Constant.CARD_SETING);
                    textList.add(textListEntity);
                } else {
                    TextListEntity textListEntity = new TextListEntity(list.get(i).getName());
                    textListEntity.setItemType(Constant.CARD_SETING);
                    textList.add(textListEntity);
                }
            }
            getCardList(itembean.getCustomer().getCard());
//            getAttCardList(itembean.getCustomer().getAttCard());
            cardListAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 获取卡片cardlist
     *
     * @param card
     */
    private void getCardList(Object card) {

        try {
            // result 就是请求接口返回的数据
            String string = JsonParseUtils.parseToJson(card);
            JSONObject json = new JSONObject(string);
            // 获取一个迭代器
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                // 循环遍历里面的keys  根据keys获取key
                String key = keys.next() + "";
                // 判断当key为data时 取出 data 转成对象
                String str = json.getString(key);
                // 将获取得value值添加到 集合中   接下来你可以直接拿到data里的所有数据该干嘛干嘛

                boolean haskey = false;
                for (int i = 0; i < textList.size(); i++) {
                    if (textList.get(i).getTitle().equals(key)) {
                        haskey = true;
                    }
                }
                if (haskey) {
                    for (int i = 0; i < textList.size(); i++) {
                        if (textList.get(i).getTitle().equals(key)) {
                            if (str != null) {
                                textList.get(i).setText(str + "");
                            }
                        }
                    }
                } else {
                    if (str != null) {
                        TextListEntity.TextJson textJson = null;
                        if (JSON.isValid(str)) {//判断是否包含可点击的连接url
                            try {
                                textJson = JsonParseUtils.parseToObject(str, TextListEntity.TextJson.class);
                            } catch (Exception e) {
                            }
                        }
                        TextListEntity textListEntity = new TextListEntity(str, key);
                        textListEntity.setItemType(Constant.CARD_BROSWER);
                        if (textJson != null) {
                            textListEntity.setTextJson(textJson);
                        }
                        textList.add(textListEntity);
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取卡片attCard
     *
     * @param attCard
     */
    private void getAttCardList(Object attCard) {

        try {

            // result 就是请求接口返回的数据
            String s1 = JsonParseUtils.parseToJson(attCard);
            JSONObject json = new JSONObject(s1);
            // 获取一个迭代器
            Iterator<String> keys = json.keys();
            while (keys.hasNext()) {
                // 循环遍历里面的keys  根据keys获取key
                String key = keys.next();
                // 判断当key为data时 取出 data 转成对象
                String str = json.getString(key);
                // 将获取得value值添加到 集合中   接下来你可以直接拿到data里的所有数据该干嘛干嘛

                boolean haskey = false;
                for (int i = 0; i < textList.size(); i++) {
                    if (textList.get(i).getTitle().equals(key)) {
                        haskey = true;
                    }
                }
                if (haskey) {
                    for (int i = 0; i < textList.size(); i++) {
                        if (textList.get(i).getTitle().equals(key)) {
                            if (str != null) {
                                textList.get(i).setText(str);
                            }
                        }
                    }
                } else {
                    if (str != null) {
                        TextListEntity.TextJson textJson = null;
                        if (JSON.isValid(str)) {//判断是否包含可点击的连接url
                            textJson = JsonParseUtils.parseToObject(str, TextListEntity.TextJson.class);
                        }
                        TextListEntity textListEntity = new TextListEntity(str, key);
                        textListEntity.setItemType(Constant.CARD_BROSWER);
                        if (textJson != null) {
                            textListEntity.setTextJson(textJson);
                        }
                        textList.add(textListEntity);
                    }
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        TextListEntity ss = (TextListEntity) adapter.getData().get(position);
        clickText = view.findViewById(R.id.card_tv);
        titleText = view.findViewById(R.id.card_title);
        List<String> stringList = new ArrayList<>();
        if (!clickText.getText().toString().equals("未设置")) {
            stringList.add(clickText.getText().toString());
        }
        Bundle bundle = new Bundle();
        bundle.putString(Constant.INTENT_TYPE, Constant.CHATACT_);
        bundle.putString(Constant.STYLETYPE, Constant.INPUTTYPE_);
        bundle.putString(Constant._TITLE, ss.getTitle());
        bundle.putString(Constant._INPUT, clickText.getText().toString());
        startActivityForResult(UpdateActivity.class, bundle, CHATSET_ACT);

    }

    /**
     * 获取tag显示string
     *
     * @param tag
     */
    private String getTagString(List<String> tag) {
        String tags = "";
        for (int i = 0; i < tag.size(); i++) {
            if (i == tag.size() - 1) {
                tags = tags + tag.get(i);
            } else {
                tags = tags + tag.get(i) + ",";
            }
        }
        return tags;
    }

    /**
     * 弹出式对话框
     *
     * @param
     */
    private void createDiaLog() {
        List<TeamEntity.DataBean.ListBean> list = LocalDataSource.getTEAMSERLIST();
        List<TeamEntity.DataBean.ListBean> listBeans = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {//当前对话的同事
            if (!itembean.getServiceId().equals(list.get(i).get_id())) {
                listBeans.add(list.get(i));
            }
        }
        popWindowListAdapter = new PopWindowListAdapter(listBeans);
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        //点击全部的分类操作
        CustomDialog customDialog = builder.heightDimenRes(R.dimen.popWindow_height)
                .widthDimenRes(R.dimen.popWindow_witch)
                .view(R.layout.colleague_list_popwindow)
                .style(R.style.Dialog)
                .setRecyclerView(R.id.colleague_popwindow_recycler, popWindowListAdapter)
                .cancelTouchout(true)
                .addViewOnclick(R.id.all_team, view -> {  //点击全部的时候全部显示

                })
                .build();

        customDialog.show();

        popWindowListAdapter.setOnItemChildClickListener((adapter, view, position) -> {//点击同事的时候分类显示
            TeamEntity.DataBean.ListBean bean = (TeamEntity.DataBean.ListBean) adapter.getData().get(position);
            String name = null;
            if (bean.getName() != null) name = bean.getName();
            mPresenter.pDialogTransfer(bean.get_id(), itembean.getId());
            customDialog.dismiss();

        });

    }

    @Override
    public void onSocketEvent(MessageEvent message) {
        super.onSocketEvent(message);
        MessageEntity messageEntity = (MessageEntity) message.getMsg();
        switch (messageEntity.getAct()) {

            case UI_FRESH:
                if (messageEntity.getDialogId() == null || !messageEntity.getDialogId().equals(itembean.getId())) {
                    setMsgCount();
                }
                break;
        }
    }

    /**
     * 转接对话成功
     *
     * @param dataBean
     */
    @Override
    public void showTransferSuccess(IneValuateEntity.DataBean dataBean) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 对话结束
     *
     * @param messageEntity
     */
    @Override
    public void showEndDialog(ReceptionEntity.DataBean messageEntity) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 加入黑名单
     *
     * @param dataBean
     */
    @Override
    public void blackAddSuccess(IneValuateEntity.DataBean dataBean) {

        itembean.getCustomer().setBlack(true);
        jrhmd.setText("移出黑名单");
    }

    /**
     * 移除黑名单
     *
     * @param dataBean
     */
    @Override
    public void blackDelSuccess(IneValuateEntity.DataBean dataBean) {
        itembean.getCustomer().setBlack(false);
        jrhmd.setText("加入黑名单");
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
     * 点击跳转网页
     */
    private void setClickIntent(View view, String url) {

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri targetUrl = Uri.parse(url);
                    intent.setData(targetUrl);
                    startActivity(intent);
                } catch (Exception e) {
                }
            }
        });
    }
}
