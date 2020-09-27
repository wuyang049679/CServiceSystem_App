package com.hc_android.hc_css.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.SpannableString;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.gyf.immersionbar.ImmersionBar;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.adapter.ChatAdapter;
import com.hc_android.hc_css.adapter.EmojiAdapter;
import com.hc_android.hc_css.adapter.PathListAdapter;
import com.hc_android.hc_css.api.Address;
import com.hc_android.hc_css.base.BaseActivity;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.contract.ChatActivityContract;
import com.hc_android.hc_css.entity.ChatEntity;
import com.hc_android.hc_css.entity.CustomPathEntity;
import com.hc_android.hc_css.entity.EmojiEntity;
import com.hc_android.hc_css.entity.FileEntity;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.entity.LoginEntity;
import com.hc_android.hc_css.entity.MessageDialogEntity;
import com.hc_android.hc_css.entity.MessageEntity;
import com.hc_android.hc_css.entity.QuickEntity;
import com.hc_android.hc_css.entity.SendEntity;
import com.hc_android.hc_css.entity.TokenEntity;
import com.hc_android.hc_css.presenter.ChatActivityPresenter;
import com.hc_android.hc_css.ui.fragment.QuickReplyFragment;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.DateUtils;
import com.hc_android.hc_css.utils.FastUtils;
import com.hc_android.hc_css.utils.FileUtils;
import com.hc_android.hc_css.utils.JsonParseUtils;
import com.hc_android.hc_css.utils.NullUtils;
import com.hc_android.hc_css.utils.UriUtils;
import com.hc_android.hc_css.utils.android.KeyBoardUtils;
import com.hc_android.hc_css.utils.android.SharedPreferencesUtils;
import com.hc_android.hc_css.utils.android.SystemUtils;
import com.hc_android.hc_css.utils.android.ToastUtils;
import com.hc_android.hc_css.utils.android.app.AppConfig;
import com.hc_android.hc_css.utils.android.app.CacheData;
import com.hc_android.hc_css.utils.android.app.DataProce;
import com.hc_android.hc_css.utils.android.app.UIUtils;
import com.hc_android.hc_css.utils.android.image.FrescoUtils;
import com.hc_android.hc_css.utils.android.image.GlideEngine;
import com.hc_android.hc_css.utils.android.image.UploadFileUtils;
import com.hc_android.hc_css.utils.socket.EventServiceImpl;
import com.hc_android.hc_css.utils.socket.MessageEvent;
import com.hc_android.hc_css.utils.socket.MessageEventType;
import com.hc_android.hc_css.utils.thread.ExecutorServiceManager;
import com.hc_android.hc_css.utils.thread.OnReleasedListener;
import com.hc_android.hc_css.utils.thread.PollingUtil;
import com.hc_android.hc_css.wight.ChatUiHelper;
import com.hc_android.hc_css.wight.LocalDataSource;
import com.hc_android.hc_css.wight.MDialogFragment;
import com.hc_android.hc_css.wight.RecyclerViewNoBugLinearLayoutManager;
import com.hc_android.hc_css.wight.media.MediaManager;
import com.hc_android.hc_css.wight.media.RecordButton;
import com.hc_android.hc_css.wight.span.AnimatedImageSpan;
import com.hc_android.hc_css.wight.span.OperationPopWindow;
import com.hw.videoprocessor.VideoProcessor;
import com.hw.videoprocessor.util.VideoProgressListener;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.shuyu.gsyvideoplayer.GSYVideoManager;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_INPUTING;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_NEW;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_OFFLINE;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_ONLINE;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_STATEUPATE;
import static com.hc_android.hc_css.utils.Constant.MESSAGE_UNDO;
import static com.hc_android.hc_css.utils.Constant.UI_FRESH;
import static com.hc_android.hc_css.utils.Constant._CARD;
import static com.hc_android.hc_css.utils.Constant._FILE;
import static com.hc_android.hc_css.utils.Constant._FORM;
import static com.hc_android.hc_css.utils.Constant._HTML;
import static com.hc_android.hc_css.utils.Constant._IMAGE;
import static com.hc_android.hc_css.utils.Constant._MENU;
import static com.hc_android.hc_css.utils.Constant._TEXT;
import static com.hc_android.hc_css.utils.Constant._VIDEO;
import static com.hc_android.hc_css.utils.Constant._VOICE;

public class ChatActivity extends BaseActivity<ChatActivityPresenter, CustomPathEntity.DataBean> implements ChatActivityContract.View, OnReleasedListener, RecordButton.OnFinishedRecordListener, QuickReplyFragment.OnSendMsgListener, ExecutorServiceManager.ExecutorListener {


    @BindView(R.id.title_act_chat)
    TextView titleActChat;
    @BindView(R.id.btn_choose)
    ImageView btnChoose;
    @BindView(R.id.backImg)
    ImageView backImg;
    @BindView(R.id.path_btn)
    LinearLayout pathBtn;
    @BindView(R.id.act_chat_include)
    ConstraintLayout actChatInclude;
    @BindView(R.id.path_tv)
    TextView pathTv;
    @BindView(R.id.path_lin)
    RelativeLayout pathLin;
    @BindView(R.id.path_recycler)
    RecyclerView pathRecycler;
    @BindView(R.id.ex_lin)
    LinearLayout exLin;
    @BindView(R.id.recycler_lin)
    LinearLayout recyclerLin;
    @BindView(R.id.input_et)
    EditText inputEt;
    @BindView(R.id.add_img)
    ImageView addImg;
    @BindView(R.id.send_tv)
    TextView sendTv;
    @BindView(R.id.input_lin)
    RelativeLayout inputLin;
    @BindView(R.id.chat_lin)
    LinearLayout chatLin;
    @BindView(R.id.recycler_chat)
    RecyclerView recyclerChat;
    @BindView(R.id.line1)
    LinearLayout line1;
    @BindView(R.id.line2)
    LinearLayout line2;
    @BindView(R.id.line3)
    LinearLayout line3;
    @BindView(R.id.smile)
    ImageView smile;
    @BindView(R.id.btn_add)
    ConstraintLayout btnAdd;
    @BindView(R.id.inject_target)
    LinearLayout injectTarget;
    @BindView(R.id.emoji_recycler)
    RecyclerView emojiRecycler;
    @BindView(R.id.emoji_del)
    ImageView emojiDel;
    @BindView(R.id.emoji_lin)
    ConstraintLayout emojiLin;
    @BindView(R.id.yygl)
    ImageView yygl;
    @BindView(R.id.btnAudio)
    RecordButton btnAudio;
    @BindView(R.id.bottom_layout)
    FrameLayout bottomLayout;
    @BindView(R.id.recycler_chat_lin)
    LinearLayout recyclerChatLin;
    @BindView(R.id.smartRefresh)
    SmartRefreshLayout smartRefresh;
    @BindView(R.id.msg_count_tv)
    TextView msgCountTv;


    private PathListAdapter pathListAdapter;
    private EmojiAdapter emojiAdapter;
    private List<CustomPathEntity.DataBean.ItemBean.RoutesBean> routesBeans;
    private List<MessageEntity.MessageBean> chatList;
    private ChatAdapter chatAdapter;
    private MessageDialogEntity.DataBean.ListBean itembean;
    private List<EmojiEntity> imageList;

    int limit = 20;//限制每次访问多少条第一设置20条
    int skip = 0;//跳过已获取的条数
    int INDEXS = 0;
    private LoginEntity.DataBean.InfoBean userBean;
    private int mLastVisiblePosition;//recycler滑动item下标
    private ChatUiHelper mUiHelper;
    private MDialogFragment mDialogFragment;
    private boolean isShow;
    private static String INTENT_TYPE;
    private String currentServiceId;

    @Override
    protected void reloadActivity() {

        routesBeans.clear();
        chatList.clear();
        imageList.clear();
        skip = 0;
        if (NullUtils.isEmptyList(chatList)) {
            showLoadingView();
            mPresenter.pChatList(itembean.getId(), userBean.getId(), userBean.getEntId(), limit, skip);
        }
        mPresenter.pGetroutes(null, itembean.getCustomerId(), itembean.getCustomer().getVisitorId());
        //询问是否在线
        EventServiceImpl.getInstance().isOffLine(itembean.getCustomer().getId(), currentServiceId);
    }

    @Override
    protected View injectTarget() {
        return findViewById(R.id.inject_target);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_chat;
    }

    @Override
    protected void initData() {
        currentServiceId = BaseApplication.getUserBean().getId();
        if (!itembean.getSource().equals("web") && !itembean.getSource().equals("link")) {
            pathLin.setVisibility(View.GONE);
            pathRecycler.setVisibility(View.GONE);
        }

        imageList = new ArrayList<>();
        routesBeans = new ArrayList<>();
        chatList = new ArrayList<>();
        pathListAdapter = new PathListAdapter(routesBeans);
        List<MessageEntity.MessageBean> chatHash = LocalDataSource.getChatList(itembean.getId());
        if (!NullUtils.isEmptyList(chatHash)) {
            //判断lastMsg是否有id,缓存里面的是否包含最后一条Id
            if (itembean.getLastMsg() != null && itembean.getLastMsg().getId() != null) {
                int readCounts = 0;
                boolean hasId = false;
                for (MessageEntity.MessageBean hash : chatHash) {
                    if (hash.getState() != null && hash.getState().equals("save"))
                        readCounts++;//如果是未读添加未读数比较未读
                    if (hash.getId() != null && hash.getId().equals(itembean.getLastMsg().getId())) {
                        hasId = true;
                    }
                }
                if (itembean.getUnreadNum() == readCounts && hasId) {//如果未读消息数相等并且有lastId
                    skip += chatHash.size();
                    List<MessageEntity.MessageBean> chatList = LocalDataSource.getChatList(this.chatList, chatHash, itembean, true);
                    //判断是否有全局消息缓存
                    this.chatList.addAll(chatList);
                    sendReadMsg(this.chatList);
                }

            } else if (itembean.getLastMsg() == null || itembean.getLastMsg().getId() == null) {//如果没lastMsg id就可以直接拿缓存
                skip += chatHash.size();
                List<MessageEntity.MessageBean> chatList = LocalDataSource.getChatList(this.chatList, chatHash, itembean, true);
                //判断是否有全局消息缓存
                this.chatList.addAll(chatList);
                sendReadMsg(this.chatList);
            }
        }
        chatAdapter = new ChatAdapter(chatList);
        emojiAdapter = new EmojiAdapter(imageList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        RecyclerViewNoBugLinearLayoutManager layoutManager2 = new RecyclerViewNoBugLinearLayoutManager(this);
        layoutManager2.setStackFromEnd(true);//从底部开始显示第一条数据
        pathRecycler.setLayoutManager(layoutManager);
        recyclerChat.setLayoutManager(layoutManager2);
        pathRecycler.setAdapter(pathListAdapter);
        recyclerChat.setAdapter(chatAdapter);
        chatAdapter.bindToRecyclerView(recyclerChat);
        ((SimpleItemAnimator) recyclerChat.getItemAnimator()).setSupportsChangeAnimations(false);//Called attach on a child which is not detached出现该问题的原因是由于recyclerView自带动画，当动画结束的时候，recyclerView会再次回收item，我们也因此可以重用item，而恰恰的是，我们在动画还没结束的时候，就视图重用item
        setMsgCount();
        //设置轨迹列表
        View loading = getLayoutInflater().inflate(R.layout.visitor_empty_view, pathRecycler, false);
        pathListAdapter.setEmptyView(loading);
        //表情控件
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 8);
        emojiRecycler.setLayoutManager(gridLayoutManager);
//        emojiRecycler.setAdapter(emojiAdapter);
        initListener();
        if (NullUtils.isEmptyList(chatList)) {
            showLoadingView();
            chatList.clear();
            mPresenter.pChatList(itembean.getId(), userBean.getId(), userBean.getEntId(), limit, getSkip());
        }
        mPresenter.pGetroutes(null, itembean.getCustomerId(), itembean.getCustomer().getVisitorId());
        chatAdapter.setOnItemChildLongClickListener(new BaseQuickAdapter.OnItemChildLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public boolean onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {


                final MessageEntity.MessageBean message = (MessageEntity.MessageBean) adapter.getItem(position);
                if (message == null) {
                    return false;
                }
//                if (message.getId() != null && userBean.getId().equals(message.getServiceId()) && !message.getSendType().equals("system") && !message.isUndo()) {
//                    String id = message.getId();
//                    if (!NullUtils.isNull(id)) {
//                        if (DateUtils.getCurrentTimeMillis() - message.getTime() >= (1000 * 60 * 2)) {
//                            ToastUtils.showShort("发送时间超过两分钟,不能撤回");
//                        } else {
//                            if (message.getType().equals(_TEXT)) {//文本类型复制粘贴撤回消息处理
//                                if (DateUtils.getCurrentTimeMillis() - message.getTime() >= (1000 * 60 * 2)) {
//                                    ToastUtils.showShort("发送时间超过两分钟,不能撤回");
//                                } else {
//                                    mPresenter.pMsgUndo(id, currentServiceId, userBean.getEntId());
//                                }
//                            } else {
//                                if (!message.getType().equals(_FORM) && !message.getType().equals(_HTML)) {
//                                    OperationPopWindow operationPopWindow = new OperationPopWindow(ChatActivity.this);
//                                    operationPopWindow.showOperationPopWindow(view, new OperationPopWindow.OperationItemClickListener() {
//                                        @Override
//                                        public void onClickUndo(View view) {
//                                            if (DateUtils.getCurrentTimeMillis() - message.getTime() >= (1000 * 60 * 2)) {
//                                                ToastUtils.showShort("发送时间超过两分钟,不能撤回");
//                                            } else {
//                                                mPresenter.pMsgUndo(id, currentServiceId, userBean.getEntId());
//
//                                            }
//                                        }
//                                    });
//                                }
//                            }
//                        }
//                    }
//                }

//                    MenuPopup menuPopup = new MenuPopup(ChatActivity.this);
//                    BasePopupView pop = new XPopup.Builder(ChatActivity.this)
//                            .hasShadowBg(false)
//                            .hasStatusBarShadow(true)
////                        .popupAnimation(PopupAnimation.NoAnimation) //NoAnimation表示禁用动画
//                            .isCenterHorizontal(true) //是否与目标水平居中对齐
////                        .offsetY(-10)
////                          .popupPosition(PopupPosition.Top) //手动指定弹窗的位置
//                            .atView(view)  // 依附于所点击的View，内部会自动判断在上方或者下方显示
//                            .asCustom(menuPopup)
//                            .show();
////
//                    TextView textView = menuPopup.findViewById(R.id.msg_copy);
//                    TextView tv = view.findViewById(R.id.content);
//                    tv.setShowSoftInputOnFocus(true);
//                    tv.setFocusableInTouchMode(true);
//                    tv.setFocusable(true);
//                    tv.setClickable(true);
//                    tv.setLongClickable(true);
//                    tv.setMovementMethod(ArrowKeyMovementMethod.getInstance());
//                    textView.setText(textView.getText(), TextView.BufferType.SPANNABLE );
//                    SelectableTextHelper mSelectableTextHelper = new SelectableTextHelper.Builder(tv)
//                            .setSelectedColor(getResources().getColor(R.color.red))
//                            .setCursorHandleSizeInDp(20)
//                            .setCursorHandleColor(getResources().getColor(R.color.bg_yel))
//                            .build();
//                    mSelectableTextHelper.setSelectListener(new OnSelectListener() {
//                        @Override
//                        public void onTextSelected(CharSequence content) {
//
//                        }
//                    });
//                    if (message.getType().equals(_TEXT)) {
//                        textView.setVisibility(View.VISIBLE);
//
//                        textView.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                pop.dismiss();
//                                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//                                // 将文本内容放到系统剪贴板里。
//                                cm.setText(message.getContents());
//                            }
//                        });
//                    }
//                    if (message.getItemType() == Constant.CHAT_RIGHT) {
//                        TextView cancel = menuPopup.findViewById(R.id.msg_cancel);
//                        cancel.setVisibility(View.VISIBLE);
//                        menuPopup.findViewById(R.id.msg_cancel).setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                String id = chatList.get(position).getId();
//                                pop.dismiss();
//                                if (!NullUtils.isNull(id)) {
//                                    if (DateUtils.getCurrentTimeMillis() - message.getTime() >= (1000 * 60 * 2)) {
//                                        ToastUtils.showShort("发送时间超过两分钟,不能撤销");
//                                        return;
//                                    }
//                                    mPresenter.pMsgUndo(id, itembean.getServiceId(), userBean.getEntId());
//                                }
//                            }
//                        });
//                    }
//                }

                return true;
            }
        });
        chatAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {//重新发送事件监听
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if (view.getId() == R.id.send_failed) {
                    MessageEntity.MessageBean messageBean = (MessageEntity.MessageBean) adapter.getData().get(position);

                    //移除创建新的一条
//
//                    chatList.remove(messageBean);
//                    chatAdapter.notifyDataSetChanged();
//                    CacheData.removeCache(itembean.getId(), messageBean);
//                    sendMsg(messageBean.getType(), messageBean.getContents(), false);//重新发送
                    repeatSend(messageBean);
                }
                if (view.getId() == R.id.simple_drawViews || view.getId() == R.id.img_lin) {
                    Bundle bundle = new Bundle();
                    if (!NullUtils.isNull(INTENT_TYPE) && INTENT_TYPE.equals(Constant.HISTORYACT_)) {
                        bundle.putString(Constant.INTENT_TYPE, INTENT_TYPE);
                    }
                    startActivity(ChatSetActivity.class, bundle);
                }
            }
        });
    }

    /**
     * 注意：在进入对话首次获取聊天记录的时候，skip最好不要传递，这样就会从对话开始时间读取，如果首次获取聊天消息数为0，上拉读取历史聊天记录的时候 skip = -1 即可。
     * 获取skip(跳过条数以有ID的字段为主)
     *
     * @return
     */
    private int getSkip() {
        skip = 0;
        for (int i = 0; i < chatList.size(); i++) {
            if (chatList.get(i).getId() != null) {
                skip++;
            }
        }
        return skip;
    }

    /**
     * 重新发送
     */

    public void repeatSend(MessageEntity.MessageBean messageBean) {

        chatList.remove(messageBean);
        chatAdapter.notifyDataSetChanged();
        CacheData.removeCache(itembean.getId(), messageBean);
        sendMsg(messageBean.getType(), messageBean.getContents(), false);//重新发送
    }

    /**
     * 点击撤回
     *
     * @param message
     * @param view
     */
    public void toUndo(MessageEntity.MessageBean message, View view) {

        if (message.getId() != null && userBean.getId().equals(message.getServiceId()) && !message.getSendType().equals("system") && !message.isUndo()) {
            String id = message.getId();
            if (userBean.getCompany()!=null && userBean.getCompany().getMsgUndo() !=null && !userBean.getCompany().getMsgUndo().isState())return;
            if (!NullUtils.isNull(id)) {

                if (DateUtils.getCurrentTimeMillis() - message.getTime() >= (1000 * 60 * 2)) {
//                    ToastUtils.showShort("发送时间超过两分钟,不能撤回");
                } else {
                    if (message.getType().equals(_TEXT)) {//文本类型复制粘贴撤回消息处理
                        if (DateUtils.getCurrentTimeMillis() - message.getTime() >= (1000 * 60 * 2)) {
//                            ToastUtils.showShort("发送时间超过两分钟,不能撤回");
                        } else {
                            mPresenter.pMsgUndo(id, currentServiceId, userBean.getEntId());
                        }
                    } else {
                        if (!message.getType().equals(_FORM) && !message.getType().equals(_HTML) && message.getItemType() == Constant.CHAT_RIGHT) {
                            OperationPopWindow operationPopWindow = new OperationPopWindow(ChatActivity.this);
                            operationPopWindow.showOperationPopWindow(view, new OperationPopWindow.OperationItemClickListener() {
                                @Override
                                public void onClickUndo(View view) {
                                    if (DateUtils.getCurrentTimeMillis() - message.getTime() >= (1000 * 60 * 2)) {
                                        ToastUtils.showShort("发送时间超过两分钟,不能撤回");
                                    } else {
                                        mPresenter.pMsgUndo(id, currentServiceId, userBean.getEntId());

                                    }
                                }
                            });
                        }
                    }
                }
            }
        }
    }


    @Override
    protected void initView() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .keyboardEnable(true)
                .statusBarColor(R.color.theme_app)
                .titleBar(R.id.act_chat_include)
                .init();
        itembean = LocalDataSource.getITEMBEAN();
        userBean = BaseApplication.getUserBean();
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            INTENT_TYPE = extras.getString(Constant.INTENT_TYPE);
        } else {
            INTENT_TYPE = null;
        }


        if (itembean.getItemType() != 0 && itembean.getItemType() == Constant.NOTRECEIVED_ACT || (userBean.getState() != null && userBean.getState().equals("break"))) {
            inputLin.setVisibility(View.GONE);
        }
        if (!NullUtils.isNull(INTENT_TYPE) && INTENT_TYPE.equals(Constant.HISTORYACT_)) {
            inputLin.setVisibility(View.GONE);
        }
        if (itembean.getItemType() != 0 && itembean.getItemType() == Constant.COLLEAGUE_ACT) {
            inputEt.setHint("协助 Ta");
        }
        backImg.setImageResource(R.mipmap.back);
        titleActChat.setText(DataProce.getTitle(itembean));
        //获取本地草稿内容
        String draft = (String) SharedPreferencesUtils.getParam(itembean.getId(), "");
        if (!NullUtils.isNull(draft)) {
            inputEt.setText(draft);
        }
        if (itembean.getCustomerOffTime() != null) {
            pathTv.setTextColor(getResources().getColor(R.color.black_bf));
            pathTv.setText(getResources().getString(R.string.break_tv));
        }
        if (itembean.getPathMsgBean() != null) {
            String title = itembean.getPathMsgBean().getTitle();
            if (!NullUtils.isNull(title)) {
                pathTv.setText(title);
                pathTv.setTextColor(getResources().getColor(R.color.break_tv));
            } else {
                pathTv.setTextColor(getResources().getColor(R.color.black_bf));
                pathTv.setText(getResources().getString(R.string.break_tv));
            }
        }
        //询问是否在线
        EventServiceImpl.getInstance().isOffLine(itembean.getCustomer().getId(), currentServiceId);
    }

    /**
     * 监听事件
     */
    private void initListener() {

        mUiHelper = ChatUiHelper.with(this);
        mUiHelper.bindContentLayout(injectTarget)
                .bindttToSendButton(sendTv)
                .bindEditText(inputEt)
                .bindBottomLayout(bottomLayout)
                .bindEmojiLayout(emojiLin)
                .bindEmojiDel(emojiDel)
                .bindAddLayout(btnAdd)
                .bindToAddButton(addImg)
                .bindToEmojiButton(smile)
                .bindAudioBtn(btnAudio)
                .bindAudioIv(yygl)
                .bindEmojiData();


        btnAudio.setOnFinishedRecordListener(this);
        //下拉加载更多
        smartRefresh.setEnableLoadMore(false);
        chatAdapter.setUpFetchEnable(false);
        chatAdapter.setUpFetching(false);
//        if (!NullUtils.isEmptyList(chatList)) {//避免加载缓存时候无法下拉加载
//            chatAdapter.setUpFetchEnable(true);
//            chatAdapter.setUpFetching(false);
//        }
        chatAdapter.setStartUpFetchPosition(3);
        smartRefresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                smartRefresh.setEnableRefresh(false);
                chatAdapter.setUpFetchEnable(true);
                chatAdapter.setUpFetching(false);
                int skip = getSkip();
                if (skip == 0) {
                    skip = -1;
                }
                mPresenter.pChatList(itembean.getId(), userBean.getId(), userBean.getEntId(), limit, skip);
                if (chatAdapter.getHeaderLayout() != null) {
                    chatAdapter.getHeaderLayout().findViewById(R.id.chat_item_loading).setVisibility(View.VISIBLE);
                }
            }
        });
        chatAdapter.setUpFetchListener(() -> {
            chatAdapter.setUpFetching(true);
            mPresenter.pChatList(itembean.getId(), userBean.getId(), userBean.getEntId(), limit, getSkip());
            if (chatAdapter.getHeaderLayout() != null) {
                chatAdapter.getHeaderLayout().findViewById(R.id.chat_item_loading).setVisibility(View.VISIBLE);
            }

        });
        //顾客轨迹点击
        pathTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!NullUtils.isNull(itembean.getPathMsgBean()) && itembean.getPathMsgBean().getTitle() != null) {

                    try {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri targetUrl = Uri.parse(itembean.getPathMsgBean().getUrl());
                        intent.setData(targetUrl);
                        startActivity(intent);
                    } catch (Exception e) {
                    }
                }
            }
        });

        //点击表情事件
//        emojiAdapter.setOnItemChildClickListener((adapter, view, position) -> {
//            EmojiEntity emojiEntity = (EmojiEntity) adapter.getData().get(position);
//            int curPosition = inputEt.getSelectionStart();
//            StringBuilder sb = new StringBuilder(inputEt.getText().toString());
//            sb.insert(curPosition, emojiEntity.getName());
//            inputEt.setText(sb.toString());
//            inputEt.setSelection(curPosition + emojiEntity.getName().length());//光标位置
//        });
        //聊天输入框内容监听
//        inputEt.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//            }
//
//            @Override
//            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
//                if (charSequence.length() == 0) {
//                    sendTv.setVisibility(View.GONE);
//                    addImg.setVisibility(View.VISIBLE);
//                } else {
//                    sendTv.setVisibility(View.VISIBLE);
//                    addImg.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable editable) {
//
//            }
//        });
        //列表点击触摸事件（点击需要关闭软键盘）

        recyclerChat.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                KeyBoardUtils.closeKeybord(inputEt, ChatActivity.this);
                emojiLin.setVisibility(View.GONE);
                bottomLayout.setVisibility(View.GONE);
                smile.setImageResource(R.drawable.smile);
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
        //列表滑动事件
        recyclerChat.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == SCROLL_STATE_IDLE || newState == SCROLL_STATE_DRAGGING) {
                    // DES: 找出当前可视Item位置
                    RecyclerView.LayoutManager layoutManager = recyclerChat.getLayoutManager();
                    if (layoutManager instanceof LinearLayoutManager) {
                        LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                        int mFirstVisiblePosition = linearManager.findFirstVisibleItemPosition();
                        mLastVisiblePosition = linearManager.findLastVisibleItemPosition();

                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });

        //软键盘弹出监听事件
//        KeyBoardUtils.setListener(this, new KeyBoardUtils.OnSoftKeyBoardChangeListener() {
//            @Override
//            public void keyBoardShow(int height) {
//                onViewBottom();
//                btnAdd.setVisibility(View.GONE);
//                emojiLin.setVisibility(View.GONE);
//                smile.setImageResource(R.drawable.smile);
//            }
//
//            @Override
//            public void keyBoardHide(int height) {
//
//            }
//        });

    }

    @Override
    public ChatActivityPresenter getPresenter() {
        return new ChatActivityPresenter();
    }


    @Override
    public void showDataSuccess(CustomPathEntity.DataBean datas) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.path_btn, R.id.backImg, R.id.btn_choose, R.id.input_lin, R.id.recycler_chat, R.id.input_et, R.id.send_tv, R.id.line1, R.id.line2, R.id.line3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.backImg:
                onBackPressed();
                break;
            case R.id.btn_choose:
                Bundle bundle = new Bundle();
                if (!NullUtils.isNull(INTENT_TYPE) && (INTENT_TYPE.equals(Constant.HISTORYACT_) || INTENT_TYPE.equals(Constant.NOTRECEIVEDACT_))) {
                    bundle.putString(Constant.INTENT_TYPE, INTENT_TYPE);
                }
                startActivity(ChatSetActivity.class, bundle);
                break;
            case R.id.path_btn://点击轨迹
                if (recyclerLin.getVisibility() == View.GONE) {
                    recyclerLin.setVisibility(View.VISIBLE);
                    routesBeans.clear();
                    mPresenter.pGetroutes(null, itembean.getCustomerId(), itembean.getCustomer().getVisitorId());
                    KeyBoardUtils.closeKeybord(inputEt, this);
                } else {
                    recyclerLin.setVisibility(View.GONE);
                }
                break;
            case R.id.recycler_chat://点击聊天记录列表消失
                KeyBoardUtils.closeKeybord(inputEt, this);
                break;
            case R.id.input_et://点击输入框
                onViewBottom();
                break;
            case R.id.input_lin:
                onViewBottom();
                break;
            case R.id.smile://点击表情按钮
//                clickSmile();
                break;
            case R.id.add_img://点击添加按钮
//                KeyBoardUtils.closeKeybord(inputEt, this);
//                if (btnAdd.getVisibility() == View.GONE) {
//                    btnAdd.setVisibility(View.VISIBLE);
//                } else {
//                    btnAdd.setVisibility(View.GONE);
//                    KeyBoardUtils.openKeybord(inputEt, this);
//                }

                break;
            case R.id.emoji_del://表情删除
//                dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
                break;

            case R.id.yygl://点击语音功能

//                if (btnAudio.getVisibility() == View.GONE) {
//                    yygl.setImageResource(R.drawable.keybod);
//                    btnAudio.setVisibility(View.VISIBLE);
//                    inputEt.setVisibility(View.GONE);
//                    KeyBoardUtils.closeKeybord(inputEt, this);
//                } else {
//
//                    yygl.setImageResource(R.mipmap.yygl);
//                    btnAudio.setVisibility(View.GONE);
//                    btnAdd.setVisibility(View.GONE);
//                    emojiLin.setVisibility(View.GONE);
//                    smile.setImageResource(R.drawable.smile);
//                    inputEt.setVisibility(View.VISIBLE);
//                    KeyBoardUtils.openKeybord(inputEt, this);
//                    onViewBottom();
//
//                }
                break;
            case R.id.btnAudio://点击按住语音
                break;
            case R.id.send_tv:
                //清空当前草稿
                SharedPreferencesUtils.remove(this, itembean.getId());
                sendMsg(_TEXT, inputEt.getText().toString(), false);
                break;
            case R.id.line1://视频相册

                PictureSelector.create(this)
                        .openGallery(PictureMimeType.ofAll())
                        .loadImageEngine(GlideEngine.createGlideEngine())
                        .isWeChatStyle(true)
                        .previewImage(true)// 是否可预览图片
                        .previewVideo(true)// 是否可预览视频
                        .isGif(true)
                        .selectionMode(PictureConfig.SINGLE)//单选模式
                        .isOriginalImageControl(true)
                        .forResult(PictureConfig.CHOOSE_REQUEST);
                break;
            case R.id.line2://点击快捷回复
                if (FastUtils.isFastClick()) {
                    mDialogFragment = MDialogFragment.newInstance();
                    mDialogFragment.setDimAmount(0.5f)     //调节灰色背景透明度[0-1]，默认0.5f
                            .setGravity(Gravity.BOTTOM)     //可选，设置dialog的位置，默认居中，可通过系统Gravity的类的常量修改，例如Gravity.BOTTOM（底部），Gravity.Right（右边），Gravity.BOTTOM|Gravity.Right（右下）
                            .setMargin(0)     //dialog左右两边到屏幕边缘的距离（单位：dp），默认0dp
                            .setHeight(540)     //dialog高度（单位：dp），默认为WRAP_CONTENT
                            .setOutCancel(true)     //点击dialog外是否可取消，默认true
                            .setAnimStyle(R.style.dialog_animation)
                            //设置dialog进入、退出的自定义动画；根据设置的Gravity，默认提供了左、上、右、下位置进入退出的动画
                            .show(getSupportFragmentManager());
                }
                break;
            case R.id.line3://发送评价邀请
                mPresenter.pSendIneValuate(itembean.getId());
                break;
        }
    }

    /**
     * 消息发送类型
     *
     * @param msgType
     */
    public synchronized void sendMsg(String msgType, String content, boolean isQuick) {
        String key = DateUtils.getCurrentTimeMillis() + "" + chatList.size();
        MessageEntity.MessageBean messageBean = new MessageEntity.MessageBean();
        Log.i(TAG, "key:" + key);
        messageBean.setItemType(Constant.CHAT_RIGHT);
        if (msgType.equals(_FORM)) {
            messageBean.setItemType(Constant.CHAT_CENTER);
        }
        messageBean.setType(msgType);
        messageBean.setSendType("service");//设置类型为客服发送，避免adapter数据出错
        messageBean.setServiceId(BaseApplication.getUserBean().getId());
        messageBean.setContents(content);
        messageBean.setKey(key);
        messageBean.setListBean(itembean);
        messageBean.setTime(DateUtils.getCurrentTimeInLong());

        chatAdapter.addData(messageBean);
        skip = skip + 1;
        CacheData.saveCache(itembean.getId(), messageBean);

        onViewBottom();
        switch (msgType) {
            case _TEXT:
                runTime(msgType, key, content, isQuick);
                if (!isQuick) {
                    mPresenter.pSendMsg(itembean.getId(), currentServiceId, itembean.getCustomerId(), BaseApplication.getUserEntity().getSocketId(), msgType, content, key, userBean.getEntId());
                }
                break;
            case _IMAGE:
                runTime(msgType, key, content, isQuick);
                if (!isQuick) {
                    mPresenter.pGetToken(itembean, messageBean, "msgimg");
                }
                break;
            case _VIDEO:
                runTime(msgType, key, content, isQuick);

                if (!isQuick) {
                    try {
                        toVideoCompression(messageBean);
                    } catch (Exception e) {
                        messageBean.setSendState(Constant._ISFAILED);
                        CacheData.updateCache(itembean.getId(), messageBean);
                    }
                }
                break;
            case _VOICE:
                runTime(msgType, key, content, isQuick);
                if (!isQuick) { //如果不是快捷回复就上传文件操作
                    mPresenter.pGetToken(itembean, messageBean, "voice");
                }
                break;
            case _CARD:
                runTime(msgType, key, content, isQuick);
                if (!isQuick) { //如果不是快捷回复就上传文件操作
                    mPresenter.pGetToken(itembean, messageBean, _CARD);
                }
                break;
            case _MENU:
                runTime(msgType, key, content, isQuick);
                if (!isQuick) { //如果不是快捷回复就上传文件操作
                    mPresenter.pGetToken(itembean, messageBean, _MENU);
                }
                break;
            case _HTML:
                runTime(msgType, key, content, isQuick);
                if (!isQuick) { //如果不是快捷回复就上传文件操作
                    mPresenter.pGetToken(itembean, messageBean, _HTML);
                }
                break;
            case _FORM:
                runTime(msgType, key, content, isQuick);
                if (!isQuick) { //如果不是快捷回复就上传文件操作
                    mPresenter.pGetToken(itembean, messageBean, _FORM);
                }
                break;
            case _FILE:
                runTime(msgType, key, content, isQuick);
                if (!isQuick) { //如果不是快捷回复就上传文件操作
                    mPresenter.pGetToken(itembean, messageBean, _FILE);
                }
                break;
        }

    }

    private void toVideoCompression(MessageEntity.MessageBean messageBean) {
        FileEntity fileEntity = JsonParseUtils.parseToObject(messageBean.getContents(), FileEntity.class);

        MessageEntity.MessageBean messageBean1 = new MessageEntity.MessageBean();
        messageBean1.setSendState(messageBean.getSendState());
        messageBean1.setContents(JsonParseUtils.parseToJson(fileEntity));
        messageBean1.setId(messageBean.getId());
        messageBean1.setItemType(messageBean.getItemType());
        messageBean1.setKey(messageBean.getKey());
        messageBean1.setListBean(messageBean.getListBean());
        messageBean1.setOneway(messageBean.getOneway());
        messageBean1.setSendType(messageBean.getSendType());
        messageBean1.setServiceId(messageBean.getServiceId());
        messageBean1.setState(messageBean.getState());
        messageBean1.setSystem(messageBean.isSystem());
        messageBean1.setTime(messageBean.getTime());
        messageBean1.setType(messageBean.getType());
        messageBean1.setUndo(messageBean.isUndo());
        ExecutorServiceManager.startExecutor(messageBean1, itembean, this);

    }

    /**
     * 发送到服务器和3秒钟延时进度条
     *
     * @param msgType
     * @param key
     * @param isQuick
     */
    private void runTime(String msgType, String key, String content, boolean isQuick) {

        inputEt.setText("");
        PollingUtil pollingUtil = new PollingUtil(new Handler(getMainLooper()));
        pollingUtil.startPolling(key, this);

        if (isQuick) {
            mPresenter.pSendMsg(itembean.getId(), currentServiceId, itembean.getCustomerId(), BaseApplication.getUserEntity().getSocketId(), msgType, content, key, userBean.getEntId());
        }
    }

    /**
     * 點擊表情處理事件
     */
    private void clickSmile() {
        if (NullUtils.isEmptyList(imageList)) {
            String[] stringArray = getResources().getStringArray(R.array.emoji_list);
            String[] stringArray2 = getResources().getStringArray(R.array.emoji_text);
            List<String> list = FileUtils.copyStickerToStickerPath(this, "stickers");
            for (int i = 0; i < stringArray.length; i++) {
                for (int x = 0; x < list.size(); x++) {
                    if (list.get(x).contains(stringArray[i])) {
                        imageList.add(new EmojiEntity(stringArray2[i], list.get(x)));
                    }
                }
            }
            emojiAdapter.notifyDataSetChanged();
        }
        KeyBoardUtils.closeKeybord(inputEt, this);
        new Handler().postDelayed(() -> {
            if (emojiLin.getVisibility() == View.GONE) {
                emojiLin.setVisibility(View.VISIBLE);
                smile.setImageResource(R.drawable.keybod);
            } else {
                emojiLin.setVisibility(View.GONE);
                smile.setImageResource(R.drawable.smile);
                KeyBoardUtils.openKeybord(inputEt, ChatActivity.this);
            }
            /**
             * 延时执行的代码（解决软键盘还未完全弹出布局上移问题）
             */
        }, 100); // 延时1秒
    }

    /**
     * 客户访问轨迹
     *
     * @param dataBean
     */
    @Override
    public void showPathList(CustomPathEntity.DataBean dataBean) {
        if (dataBean != null && dataBean.getItem() != null && !NullUtils.isEmptyList(dataBean.getItem().getRoutes())) {
            routesBeans.addAll(dataBean.getItem().getRoutes());
            path_notifyItem();

        } else {
            View emptyView = pathListAdapter.getEmptyView();
            emptyView.findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
            emptyView.findViewById(R.id.visitor_loading).setVisibility(View.GONE);
//            pathListAdapter.notifyDataSetChanged();
            path_notifyItem();
        }
    }

    /**
     * 聊天记录列表
     *
     * @param dataBean
     */
    @Override
    public void showChatList(ChatEntity.DataBean dataBean) {
        if (smartRefresh != null) {
            smartRefresh.finishRefresh();
        }
        if (chatAdapter.getHeaderLayout() != null) {
            chatAdapter.getHeaderLayout().findViewById(R.id.chat_item_loading).setVisibility(View.GONE);
        }

        List<MessageEntity.MessageBean> list = dataBean.getList();

        if (!NullUtils.isEmptyList(list)) {
            List<MessageEntity.MessageBean> cList = LocalDataSource.getChatList(chatList, list, itembean, false);
            if (skip > 0) {
                chatAdapter.addData(0, cList);
                chatAdapter.setUpFetching(false);

            } else {
                //等第一数据加载完成再添加头部避免首次就加载历史聊天记录，触发UpFetch
                View headerView = LayoutInflater.from(this).inflate(R.layout.chat_item_header, null, false);
                chatAdapter.addHeaderView(headerView);
                chatAdapter.addData(cList);

//                chatAdapter.setUpFetchEnable(true);
//                chatAdapter.setUpFetching(false);
                CacheData.saveCache(itembean.getId(), list);
            }
            if (list.size() == 0) {
                chatAdapter.setUpFetchEnable(false);
            }
            skip += list.size();

        } else {
            skip = -1;
        }
        sendReadMsg(list);
    }

    /**
     * 消息发送成功
     *
     * @param dataBean
     */
    @Override
    public void showSendSuccess(SendEntity.DataBean dataBean) {
        Log.i(TAG, "key:Success" + dataBean.getKey());
        PollingUtil.endPolling(dataBean.getKey());
        for (int i = 0; i < chatList.size(); i++) {//设置发送成功的消息id
            if (chatList.get(i).getKey() != null && chatList.get(i).getKey().equals(dataBean.getKey())) {
                chatList.get(i).setId(dataBean.getId());
//                chatList.get(i).setTime(dataBean.getTime());
                chatList.get(i).setSendState(null);
                notifyItem(i);
                CacheData.updateCache(itembean.getId(), chatList.get(i));
            }
        }
    }

    /**
     * 发送不支持的消息
     *
     * @param txt
     * @param key
     */
    @Override
    public void msgUnEnable(String txt, String key) {
        PollingUtil.endPolling(key);
        for (int i = chatList.size() - 1; i >= 0; i--) {
            if (chatList.get(i).getKey() != null) {
                String key1 = chatList.get(i).getKey();
                if (key1.equals(key)) {
                    chatList.get(i).setSendState(null);
                    chatList.get(i).setItemType(Constant.CHAT_CENTER);
                    chatList.get(i).setContents(txt);
                    chatList.get(i).setType(_TEXT);
                    CacheData.updateCache(itembean.getId(), chatList.get(i));
                    int finalI = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            notifyItem(finalI);
                        }
                    });
                }
            }
        }
    }

    /**
     * 消息发送失败
     */
    @Override
    public void showSendFailed(String msg, String key) {
        PollingUtil.endPolling(key);
        for (int i = chatList.size() - 1; i >= 0; i--) {
            if (chatList.get(i).getKey() != null) {
                String key1 = chatList.get(i).getKey();
                if (key1.equals(key)) {
                    chatList.get(i).setSendState(Constant._ISFAILED);
                    CacheData.updateCache(itembean.getId(), chatList.get(i));
                    MessageEntity.MessageBean messageBean = chatList.get(i);
                    int finalI = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            notifyItem(finalI);

                        }
                    });

                }
            }
        }
    }

    /**
     * 获取上传文件的token
     *
     * @param dataBean
     */
    @Override
    public void showToken(TokenEntity.DataBean dataBean, MessageEntity.MessageBean messageBean) {
        if (dataBean.getToken() != null) {
            UploadFileUtils.getInstance().upload(dataBean.getToken(), messageBean, (s, responseInfo, jsonObject) -> {
                if (responseInfo.isOK()) {
                    try {
                        String content = null;
                        String fileKey = jsonObject.getString("key");
                        String type = messageBean.getType();
                        FileEntity fileEntity = JsonParseUtils.parseToObject(messageBean.getContents(), FileEntity.class);
                        switch (type) {
                            case _IMAGE:
                                FileEntity.ImageEntity imageEntity = new FileEntity.ImageEntity();
                                imageEntity.setHeight(fileEntity.getHeight());
                                imageEntity.setWidth(fileEntity.getWidth());
                                imageEntity.setName(fileEntity.getName());
                                imageEntity.setPicUrl(fileKey);
                                imageEntity.setType(fileEntity.getType());
                                imageEntity.setSize(fileEntity.getSize());
                                imageEntity.setBucket("msgimg");
                                content = JsonParseUtils.parseToJson(imageEntity);
                                break;
                            case _VIDEO:
                                FileEntity.VideoEntity videoEntity = new FileEntity.VideoEntity();
                                FileEntity.VideoEntity.ThumbImgBean thumbImgBean = new FileEntity.VideoEntity.ThumbImgBean();
                                thumbImgBean.setHeight(fileEntity.getHeight());
                                thumbImgBean.setWidth(fileEntity.getWidth());
                                videoEntity.setThumbImg(thumbImgBean);
                                videoEntity.setFileUrl(fileKey);
                                videoEntity.setName(fileEntity.getName());
                                videoEntity.setSize(fileEntity.getSize());
                                videoEntity.setType(fileEntity.getType());
                                content = JsonParseUtils.parseToJson(videoEntity);
                                break;
                            case _VOICE:
                                FileEntity.VoiceEntity voiceEntity = new FileEntity.VoiceEntity();
                                voiceEntity.setType(fileEntity.getType());
                                voiceEntity.setDuration(fileEntity.getDuration() + "");
                                voiceEntity.setKey(fileKey);
                                voiceEntity.setSize(fileEntity.getSize());
                                content = JsonParseUtils.parseToJson(voiceEntity);
                                break;
                        }


                        if (content != null) {
                            mPresenter.pSendMsg(itembean.getId(), currentServiceId, itembean.getCustomerId(), BaseApplication.getUserEntity().getSocketId(), type, content, messageBean.getKey(), userBean.getEntId());
                        }
                    } catch (JSONException e) {
                        showSendFailed("网络错误", messageBean.getKey());
                    }
                } else {
                    showSendFailed("网络错误", messageBean.getKey());
                }
            });
        }
    }

    /**
     * 发送评价邀请
     *
     * @param dataBean
     */
    @Override
    public void showIneValuate(IneValuateEntity.DataBean dataBean) {

    }

    /**
     * 消息撤回成功
     *
     * @param dataBean
     */
    @Override
    public void showMsgUndo(IneValuateEntity.DataBean dataBean) {

    }

    /**
     * 消息撤回失败
     *
     * @param msg
     * @param key
     */
    @Override
    public void showMsgUndoFiled(String msg, String key) {

    }

    /**
     * 更新消息已读
     *
     * @param list
     */

    public void sendReadMsg(List<MessageEntity.MessageBean> list) {
        if (!NullUtils.isEmptyList(list)) {
            String mids = "";
            for (int i = 0; i < list.size(); i++) {
                MessageEntity.MessageBean bean = list.get(i);
                if (bean.getState() != null && bean.getState().equals("save")) {
                    if (mids.length() > 0) {
                        mids = mids + "," + bean.getId();
                    } else {
                        mids = bean.getId();
                    }
                    //更新缓存(已读状态)
                    bean.setState("read");
                    if (itembean.getServiceId().equals(BaseApplication.getUserBean().getId())) {
                        CacheData.updateCache(itembean.getId(), bean);
                    }
                }
            }

            if (mids.length() > 0) {
                //同事的聊天只有该同事自己进入才发送已读通知
                if (itembean.getServiceId().equals(BaseApplication.getUserBean().getId())) {
                    mPresenter.pSendRead(itembean.getId(), itembean.getCustomerId(), mids, userBean.getEntId());
                }
            }
        }
    }

    /**
     * 新消息通知
     *
     * @param msg
     */

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onSocketEvent(MessageEvent msg) {
        super.onSocketEvent(msg);
        MessageEntity messages = (MessageEntity) msg.getMsg();
        switch (messages.getAct()) {

            case MESSAGE_NEW:
//                if (messages.getAutoMsgType() != null && messages.getAutoMsgType().equals("end"))
//                    return;

                String socketId = BaseApplication.getUserEntity().getSocketId();
                if (!messages.getSocketId().equals(socketId) && messages.getDialogId().equals(itembean.getId())) {//判断是不是自己发送的，不是的则更新数据
                    List<MessageEntity.MessageBean> listBean = new ArrayList<>();
                    listBean.add(messages.getMessage());
                    List<MessageEntity.MessageBean> beans = LocalDataSource.getChatList(this.chatList, listBean, itembean, true);
                    if (!NullUtils.isEmptyList(beans)) {
                        chatAdapter.addData(beans);
                        skip = skip + 1;
                        if (mLastVisiblePosition == 0 || mLastVisiblePosition > chatList.size() - 10) {//滑上去就不移动页面
                            onViewBottom();
                        }
                    }

                    //发送消息已读
                    sendReadMsg(listBean);
                }
//                //更新耗时中未显示的消息，例如上传视频文件退出页面后再次进入（延时0.5秒，防止推送比方法回调慢）
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (messages.getSocketId().equals(socketId) && messages.getDialogId().equals(itembean.getId())) {
//
//                            boolean isAdd = true;
//                            for (int i = 0; i < chatList.size(); i++) {
//                                if (chatList.get(i).getId()!=null&&chatList.get(i).getId().equals(messages.getMessage().getId())) {
//                                    isAdd = false;
//                                }
//                            }
//
//                            if (isAdd) {
//                                List<MessageEntity.MessageBean> listBean = new ArrayList<>();
//                                listBean.add(messages.getMessage());
//                                List<MessageEntity.MessageBean> beans = LocalDataSource.getChatList(chatList, listBean, itembean, true);
//                                if (!NullUtils.isEmptyList(beans)) {
//                                    chatAdapter.addData(beans);
//                                    cacheList.add(messages.getMessage());//添加缓存
//                                    skip=skip+1;
//                                    if (mLastVisiblePosition == 0 || mLastVisiblePosition > chatList.size() - 10) {//滑上去就不移动页面
//                                        onViewBottom();
//                                    }
//                                }
//
//                                //发送消息已读
//                                sendReadMsg(listBean);
//                            }
//                        }
//                    }
//                },500);

                break;
            //顾客上线
            case MESSAGE_ONLINE:
                if (messages.getCustomerId().equals(itembean.getCustomerId())) {

//                    mPresenter.pGetroutes(null, itembean.getCustomerId(), itembean.getCustomer().getVisitorId());
                    if (messages.getCurrentUrl() != null) {
                        MessageDialogEntity.DataBean.ListBean.PathMsgBean pathMsgBean = JsonParseUtils.parseToObject(messages.getCurrentUrl(), MessageDialogEntity.DataBean.ListBean.PathMsgBean.class);
                        if (pathMsgBean != null) {
                            routesBeans.add(0, new CustomPathEntity.DataBean.ItemBean.RoutesBean(pathMsgBean.getTitle(), pathMsgBean.getUrl(), DateUtils.getCurrentTimeMillis()));
                            path_notifyItem();
//                           pathListAdapter.addData(0,new CustomPathEntity.DataBean.ItemBean.RoutesBean(pathMsgBean.getTitle(),pathMsgBean.getUrl(),DateUtils.getCurrentTimeMillis()));
                            pathTv.setText(pathMsgBean.getTitle());
                            pathTv.setTextColor(getResources().getColor(R.color.break_tv));
                            itembean.setPathMsgBean(pathMsgBean);
                        }

                    }
                }
                break;
            //顾客离线
            case MESSAGE_OFFLINE:
                if (messages.getCustomerId().equals(itembean.getCustomerId())) {
                    pathTv.setTextColor(getResources().getColor(R.color.black_bf));
                    pathTv.setText(getResources().getString(R.string.break_tv));
                    itembean.setPathMsgBean(new MessageDialogEntity.DataBean.ListBean.PathMsgBean());
                }
                break;
            //消息撤回
            case MESSAGE_UNDO:
                if (messages.getMsgId() != null) {

                    for (int i = chatList.size() - 1; i >= 0; i--) {
                        if (chatList.get(i).getId() != null && chatList.get(i).getId().equals(messages.getMsgId())) {
                            chatList.get(i).setUndo(true);
                            chatList.get(i).setItemType(Constant.CHAT_CENTER);
                            notifyItem(i);
                            CacheData.updateCache(itembean.getId(), chatList.get(i));
                        }
                    }


                }
                break;
            case MESSAGE_INPUTING:
                if (userBean.getPersonality()!=null &&!userBean.getPersonality().isInputing())return;
                //等第一数据加载完成再添加头部避免首次就加载历史聊天记录，触发UpFetch
                if (messages.getDialogId() != null && messages.getDialogId().equals(itembean.getId())) {

                    View footerView = chatAdapter.getFooterLayout();
                    if (footerView == null) {
                        footerView = LayoutInflater.from(this).inflate(R.layout.chat_item_footer, null, false);
                        chatAdapter.addFooterView(footerView);
                        recyclerChat.smoothScrollToPosition(chatList.size() + 1);
                        DataProce.setImageView(itembean, footerView);

                    }
                    TextView textView = footerView.findViewById(R.id.foot_content);
                    ConstraintLayout footerlin = footerView.findViewById(R.id.foot_lin);

                    if (messages.getContents() != null) {
                        titleActChat.setText("顾客正在输入...");
                        String text = messages.getContents() + "  ";
                        SpannableString spannableString = AnimatedImageSpan.convertNormalStringToSpannableString(text + "0", textView, this);
                        textView.setText(spannableString);

                        if (footerlin.getVisibility() == View.GONE) {
                            footerlin.setVisibility(View.VISIBLE);
                            recyclerChat.smoothScrollToPosition(chatList.size() + 2);
                        }
                    } else {
                        titleActChat.setText(DataProce.getTitle(itembean));
                        if (footerlin.getVisibility() == View.VISIBLE) {
                            footerlin.setVisibility(View.GONE);
                            chatAdapter.removeFooterView(footerView);
                        }
                    }
                }
                break;
            case MESSAGE_STATEUPATE://全局离线通知
                if (messages.getServiceId().equals(userBean.getId())) {//先判断是不是本机客服
                    if (messages.getState() != null && messages.getState().equals("break")) {
                        inputLin.setVisibility(View.GONE);
                    }
                }
                break;
            case UI_FRESH:

                if (messages.getMessage() != null) {
                    for (int i = 0; i < chatList.size(); i++) {//设置发送成功的消息id
                        if (chatList.get(i).getKey() != null && chatList.get(i).getKey().equals(messages.getMessage().getKey())) {
                            chatList.set(i, messages.getMessage());
                            PollingUtil.endPolling(chatList.get(i).getKey());
                            notifyItem(i);
//                            String sendState = messages.getMessage().getSendState();
//                             View viewloading = chatAdapter.getViewByPosition(i, R.id.send_loading);
//                              if (sendState==null||!sendState.equals(Constant._ISFAILED)){
//                                  viewloading.setVisibility(View.GONE);
//                              }else {
//                                  notifyItem(i);
//                              }
                        }
                    }
                } else {
                    //未读数刷新
                    if (messages.getDialogId() == null || !messages.getDialogId().equals(itembean.getId())) {
                        setMsgCount();
                    }
                }

                break;
        }
    }

    /**
     * 列表刷新底，移动至最底部
     */
    public void onViewBottom() {
        if (chatList.size() > 0) {
//            recyclerChat.smoothScrollToPosition(chatList.size()+chatAdapter.getHeaderLayoutCount()+chatAdapter.getFooterLayoutCount());
            UIUtils.postTaskDelay(() -> {//避免闪烁（滑动时item还没渲染完成）

//                        int position = (chatList.size()+chatAdapter.getHeaderLayoutCount()+chatAdapter.getFooterLayoutCount()) - 1;
//                        if (position < 0) {
//                            return;
//                        }
//                        recyclerChat.scrollToPosition(position);
                        recyclerChat.smoothScrollToPosition(chatList.size() + chatAdapter.getHeaderLayoutCount() + chatAdapter.getFooterLayoutCount());
                    },
                    200);
        }
    }


    /**
     * 监听软键盘删除键
     *
     * @param event
     * @return
     */

    @SuppressLint("RestrictedApi")
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_DEL && event.getAction() != KeyEvent.ACTION_UP) {
            String string = inputEt.getText().toString();
            int selectionStart = inputEt.getSelectionStart();
            int lenth = string.length();
            if (selectionStart == lenth && string.endsWith("]")) {//删除表情处理
                StringBuilder sb = new StringBuilder(string);
                int i = sb.lastIndexOf("[");
                if (i != -1) {//如何前面有”[“才进行删除处理
                    String s = sb.delete(i, sb.length()).toString();
                    inputEt.setText(s);
                    if (inputEt.isCursorVisible()) {//解决光标覆盖最后一个个文字的问题
                        inputEt.setText(s + " ");
                    }
                    inputEt.setSelection(inputEt.getText().length());//光标位置

                }
            }
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onBackPressed() {
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        if (!NullUtils.isNull(inputEt.getText().toString().trim())) {//草稿刷新
            SharedPreferencesUtils.getInstance().setParam(this, itembean.getId(), inputEt.getText().toString());
            MessageEntity message = new MessageEntity();
            //通知对话列表刷新
            message.setDialogId(itembean.getId());
            message.setAct(UI_FRESH);
            MessageEvent event = new MessageEvent(MessageEventType.EventMessage, message);
            EventBus.getDefault().postSticky(event);
        } else {
            String draft = (String) SharedPreferencesUtils.getInstance().get(itembean.getId(), "");
            if (!NullUtils.isNull(draft)) {
                SharedPreferencesUtils.remove(this, itembean.getId());
                //通知对话列表刷新
                MessageEntity message = new MessageEntity();
                message.setDialogId(itembean.getId());
                message.setAct(UI_FRESH);
                MessageEvent event = new MessageEvent(MessageEventType.EventMessage, message);
                EventBus.getDefault().postSticky(event);
            }

        }

        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        GSYVideoManager.onPause();
        MediaManager.reset();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GSYVideoManager.onResume();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        GSYVideoManager.releaseAllVideos();
        MediaManager.release();
    }

    /**
     * 线程延时三秒的操作结果显示加载中
     *
     * @param key
     */
    @Override
    public void threadKey(String key) {
        for (int i = chatList.size() - 1; i >= 0; i--) {
            if (chatList.get(i).getKey() != null) {
                String key1 = chatList.get(i).getKey();
                if (key1.equals(key)) {
                    chatList.get(i).setSendState(Constant._ISLOADING);
                    CacheData.updateCache(itembean.getId(), chatList.get(i));
                    int finalI = i;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            notifyItem(finalI);
                        }
                    });

                }
            }
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
                    // 图片选择结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);

                    // 例如 LocalMedia 里面返回五种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 4.media.getOriginalPath()); media.isOriginal());为true时此字段才有值
                    // 5.media.getAndroidQToPath();为Android Q版本特有返回的字段，此字段有值就用来做上传使用
                    // 如果同时开启裁剪和压缩，则取压缩路径为准因为是先裁剪后压缩
                    for (LocalMedia media : selectList) {
                        Log.i(TAG, "是否压缩:" + media.isCompressed());
                        Log.i(TAG, "压缩:" + media.getCompressPath());
                        Log.i(TAG, "原图:" + media.getPath());
                        Log.i(TAG, "是否裁剪:" + media.isCut());
                        Log.i(TAG, "裁剪:" + media.getCutPath());
                        Log.i(TAG, "是否开启原图:" + media.isOriginal());
                        Log.i(TAG, "原图路径:" + media.getOriginalPath());
                        Log.i(TAG, "Android Q 特有Path:" + media.getAndroidQToPath());
                        Log.i(TAG, "getMimeType():" + media.getMimeType());
                        Log.i(TAG, "getChooseModel():" + media.getChooseModel());
                        Log.i(TAG, "getFileName():" + media.getFileName());
                        Log.i(TAG, "media.getDuration():" + media.getDuration());
                        Log.i(TAG, "media.getSize():" + media.getSize());
                        Log.i(TAG, "getHeight():" + media.getHeight());
                        Log.i(TAG, "getWidth():" + media.getWidth());
                    }

                    if (!NullUtils.isEmptyList(selectList)) {
                        LocalMedia localMedia = selectList.get(0);
                        String path = localMedia.getPath();
                        if (localMedia.isOriginal()) {
                            path = localMedia.getOriginalPath();
                            Log.i(TAG, "原图:path" + localMedia.getPath());
                        }
                        if (localMedia.isCompressed()) {
                            path = localMedia.getCompressPath();
                            Log.i(TAG, "压缩:path" + path);
                        }
                        if (localMedia.getAndroidQToPath() != null) {

                            path = localMedia.getAndroidQToPath();
                        }
                        if (path != null) {
                            FileEntity fileEntity = new FileEntity();
                            fileEntity.setPicUrl(path);
                            fileEntity.setHeight(localMedia.getHeight());
                            fileEntity.setWidth(localMedia.getWidth());
                            fileEntity.setType(localMedia.getMimeType());
                            fileEntity.setName(localMedia.getFileName());
                            fileEntity.setFileUrl(path);
                            fileEntity.setSize(localMedia.getSize());
                            fileEntity.setLocalPath(true);
                            if (localMedia.getDuration() != 0)
                                fileEntity.setDuration(localMedia.getDuration());
                            String content = JsonParseUtils.parseToJson(fileEntity);
                            if (localMedia.getMimeType().contains(_VIDEO)) {
                                if (localMedia.getWidth() == 0) {
                                    int[] screenDispaly = SystemUtils.getScreenDispaly(this);
                                    if (screenDispaly.length > 0) {
                                        fileEntity.setWidth(screenDispaly[0]);
                                        fileEntity.setHeight(screenDispaly[1]);
                                    }

                                }
//                                if (fileEntity.getWidth() > fileEntity.getHeight()) {
//                                    fileEntity.setWidth(localMedia.getHeight());
//                                    fileEntity.setHeight(localMedia.getWidth());
//                                }
                                fileEntity.setThumbImg(fileEntity);
                                content = JsonParseUtils.parseToJson(fileEntity);
                                sendMsg(_VIDEO, content, false);
                            } else {
                                if (FileUtils.fileSize(localMedia.getSize(), localMedia.getMimeType(), itembean.getSource())) {
                                    sendMsg(_IMAGE, content, false);
                                }
                            }
                        }
                    } else {
                        showShortToast("获取文件出错");
                    }
                    break;
            }
        }
    }


    /**
     * 语音发送监听
     *
     * @param audioPath
     * @param time
     */

    @Override
    public void onFinishedRecord(String audioPath, float time) {

        if (audioPath != null && FileUtils.fileSize(FileUtils.getFileSize(audioPath), "file", itembean.getSource())) {
            FileEntity fileEntity = new FileEntity();
            fileEntity.setType("audio/mp3");
            fileEntity.setName(FileUtils.getFileName(audioPath));
            fileEntity.setSize(FileUtils.getFileSize(audioPath));
            fileEntity.setFileUrl(audioPath);
            fileEntity.setKey(audioPath);
            fileEntity.setLocalPath(true);
            fileEntity.setDuration(time);
            String content = JsonParseUtils.parseToJson(fileEntity);
            sendMsg(_VOICE, content, false);

        }
    }

    /**
     * 快捷回复的消息发送
     *
     * @param listBean
     */

    @Override
    public void toSendMsgForFragment(QuickEntity.DataBean.ListBean listBean) {
        mDialogFragment.dismiss();
        if (AppConfig.getConfigEntity().isAddInput() && listBean.getType().equals("text")) {
            inputEt.setText(listBean.getContent());
        } else {
            sendMsg(listBean.getType(), listBean.getContent(), true);
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (!NullUtils.isNull(inputEt.getText().toString().trim())) {
            SharedPreferencesUtils.getInstance().setParam(this, itembean.getId(), inputEt.getText().toString());
        }
    }


    @Override
    public void onFinish(MessageEntity.MessageBean messageBean) {
        FileEntity fileEntity = JsonParseUtils.parseToObject(messageBean.getContents(), FileEntity.class);
        File file = new File(fileEntity.getFileUrl());
        if (FileUtils.fileSize(file.length(), fileEntity.getType(), itembean.getSource())) {
            mPresenter.pGetToken(itembean, messageBean, "file");
            Log.i(TAG, "RxFFmpegInvoke:onFinish");
        }
    }


    @Override
    public void onProgress(int progress, long progressTime) {
        Log.i(TAG, "RxFFmpegInvoke:onProgress" + progress);
    }

    @Override
    public void onCancel(MessageEntity.MessageBean messageBean) {

        showSendFailed(null, messageBean.getKey());
    }

    @Override
    public void onError(String message, MessageEntity.MessageBean messageBean) {

        showSendFailed(message, messageBean.getKey());
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
     * chatAdapter单个刷新逻辑
     */
    private synchronized void notifyItem(int i) {
//        if (chatAdapter.getHeaderLayout()==null&&chatAdapter.getFooterLayout()==null){
//            chatAdapter.notifyItemChanged(i);
//            Log.i(TAG,"notifyItemChanged+0");
//        }else if (chatAdapter.getHeaderLayout()!=null||chatAdapter.getFooterLayout()!=null){
//            chatAdapter.notifyItemChanged(i + 1);
//            Log.i(TAG,"notifyItemChanged+1");
//        }else if (chatAdapter.getHeaderLayout()!=null&&chatAdapter.getFooterLayout()!=null){
//            chatAdapter.notifyItemChanged(i + 2);
//            Log.i(TAG,"notifyItemChanged+2");
//        }
        chatAdapter.notifyItemChanged(i + chatAdapter.getHeaderLayoutCount() + chatAdapter.getFooterLayoutCount());
        Log.i(TAG, "notifyItemChanged+===" + chatAdapter.getHeaderLayoutCount() + chatAdapter.getFooterLayoutCount());
    }

    /**
     * 轨迹同步刷新
     */
    private synchronized void path_notifyItem() {
        pathListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onStop() {
        super.onStop();
        KeyBoardUtils.closeKeybord(inputEt, this);//防止锁屏后软件盘空白
    }

    /**
     *
     */

    public void dismissDialog() {
        if (mDialogFragment != null) mDialogFragment.dismiss();
    }
}


