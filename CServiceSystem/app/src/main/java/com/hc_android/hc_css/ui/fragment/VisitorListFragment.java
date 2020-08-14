package com.hc_android.hc_css.ui.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.adapter.VisitorAdapter;
import com.hc_android.hc_css.adapter.VisitorPathAdapter;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.base.BaseConfig;
import com.hc_android.hc_css.base.BaseFragment;
import com.hc_android.hc_css.contract.VisitorListFragmentContract;
import com.hc_android.hc_css.entity.CustomPathEntity;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.entity.MessageEntity;
import com.hc_android.hc_css.entity.VisitorEntity;
import com.hc_android.hc_css.presenter.VisitorListFragmentPresenter;
import com.hc_android.hc_css.ui.activity.MainActivity;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.DateUtils;
import com.hc_android.hc_css.utils.FastUtils;
import com.hc_android.hc_css.utils.JsonParseUtils;
import com.hc_android.hc_css.utils.NullUtils;
import com.hc_android.hc_css.utils.android.SharedPreferencesUtils;
import com.hc_android.hc_css.utils.socket.MessageEvent;
import com.hc_android.hc_css.utils.socket.MessageEventType;
import com.hc_android.hc_css.wight.CustomDialog;
import com.hc_android.hc_css.wight.RecyclerViewNoBugLinearLayoutManager;

import org.greenrobot.eventbus.EventBus;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;

import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_IDLE;
import static com.hc_android.hc_css.utils.Constant.EVENTBUS_NEWDIALOG_VISITOR;
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

public class VisitorListFragment extends BaseFragment<VisitorListFragmentPresenter, VisitorEntity.DataBean> implements VisitorListFragmentContract.View, BaseQuickAdapter.OnItemChildClickListener {

    @BindView(R.id.conmonTitleTextView)
    TextView conmonTitleTextView;
    @BindView(R.id.backImg)
    ImageView backImg;
    @BindView(R.id.backLayout)
    LinearLayout backLayout;
    @BindView(R.id.fg_visitor_recycler)
    RecyclerView fgVisitorRecycler;
    private VisitorAdapter visitorAdapter;
    private List<VisitorEntity.DataBean.ListBean> listBeans;
    private int mFirstVisiblePosition;
    private VisitorPathAdapter popWindowListAdapter;
    private CustomDialog customDialog;
    private List<CustomPathEntity.DataBean.ItemBean.RoutesBean> routesBeans;
    private String realtimeId;
    private boolean mUserVisibleHint;
    private int MESSAGE_INVITATION=1;//邀请对话

    public static VisitorListFragment newInstance() {

        Bundle args = new Bundle();

        VisitorListFragment fragment = new VisitorListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public VisitorListFragmentPresenter getPresenter() {
        return new VisitorListFragmentPresenter();
    }

    @Override
    protected int injectTarget() {
        return R.id.visitor_inject_lin;
    }

    @Override
    protected void doRetry() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.visitor_list_fragment;
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.theme_app)
                .titleBar(R.id.visitor_list_include)
                .init();
        conmonTitleTextView.setText(getHcActivity().getResources().getString(R.string.text_vistor));
        listBeans = new ArrayList<>();
        routesBeans = new ArrayList<>();
        popWindowListAdapter = new VisitorPathAdapter(routesBeans);
        visitorAdapter = new VisitorAdapter(listBeans);
        RecyclerViewNoBugLinearLayoutManager layoutParams = new RecyclerViewNoBugLinearLayoutManager(getHcActivity());
//        layoutParams.setStackFromEnd(true);//从底部开始显示第一条数据
        fgVisitorRecycler.setLayoutManager(layoutParams);
        fgVisitorRecycler.setAdapter(visitorAdapter);

        //列表滑动事件
        fgVisitorRecycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == SCROLL_STATE_IDLE || newState == SCROLL_STATE_DRAGGING) {
                    // DES: 找出当前可视Item位置
                    RecyclerView.LayoutManager layoutManager = fgVisitorRecycler.getLayoutManager();
                    if (layoutManager instanceof LinearLayoutManager) {
                        LinearLayoutManager linearManager = (LinearLayoutManager) layoutManager;
                        mFirstVisiblePosition = linearManager.findFirstVisibleItemPosition();
                        int mLastVisiblePosition = linearManager.findLastVisibleItemPosition();

                    }
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        visitorAdapter.setOnItemChildClickListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        //判断在线状态
//        if (!BaseApplication.getUserBean().getState().equals("break")) {
//            showLoadingView();
////            mPresenter.pVisitorList();
//        } else {
//            showEmptyView(getHcActivity().getResources().getString(R.string.break_online));
//        }
    }

    @Override
    public void showDataError(String errorMessage) {

    }

    @Override
    public void showDataSuccess(VisitorEntity.DataBean datas) {

        showContentView();
        if (datas.getList() != null) {
            //lis列表有添加的推送数据就做去重处理。有的就不添加
            for (int i = 0; i < datas.getList().size(); i++) {
                boolean needAdd = true;
                for (int x = 0; x < listBeans.size(); x++) {
                    VisitorEntity.DataBean.ListBean bean = datas.getList().get(i);
                    VisitorEntity.DataBean.ListBean visitorEntity = listBeans.get(x);
                    if (bean.get_id() != null && visitorEntity.get_id() != null && bean.get_id().equals(visitorEntity.get_id())) {
                        needAdd = false;
                    }
                    if (bean.getCustomerId() != null && visitorEntity.getCustomerId() != null && bean.getCustomerId().equals(visitorEntity.getCustomerId())) {
                        needAdd = false;
                    }
                    if (bean.getVisitorId() != null && visitorEntity.getVisitorId() != null && bean.getVisitorId().equals(visitorEntity.getVisitorId())) {
                        needAdd = false;
                    }

                }
                if (needAdd) {
                    listBeans.add(datas.getList().get(i));
                }
            }
            if (listBeans.size() == 0) {
                showEmptyView("当前没有在线访客");
            }else {
                sortList(listBeans);
            }
            visitorAdapter.notifyDataSetChanged();
            ((MainActivity) getHcActivity()).getNavigationBar().setMsgPointCount(1, listBeans.size());
        }
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
        } else {
            View emptyView = popWindowListAdapter.getEmptyView();
            emptyView.findViewById(R.id.empty_view).setVisibility(View.VISIBLE);
            emptyView.findViewById(R.id.visitor_loading).setVisibility(View.GONE);
        }
        popWindowListAdapter.notifyDataSetChanged();
    }

    /**
     * 根据list里面的对象的属性进行排序 升序排列(根据addtime降序)
     *
     * @param list
     */
    public static void sortList(List<VisitorEntity.DataBean.ListBean> list) {
        Collections.sort(list, (o1, o2) -> {
            try {
                String a = o1.getAddtime();
                String b = o2.getAddtime();
                //TODO: 升序排列
                return b.compareTo(a);
            }catch (Exception e){
                e.printStackTrace();
            }
            return 0;
        });
    }
    @Override
    public void showInvitation(IneValuateEntity.DataBean dataBean) {

        int suc = dataBean.get_suc();
        if (suc == 0) {
            showShortToast("该访客已离开，无法发起邀请");
            MessageEntity messageEntity=new MessageEntity();
            messageEntity.setRealtimeId(realtimeId);
            delData(messageEntity);
        }
        if (suc == 1) {
            String invitationTime = dataBean.getInvitationTime();
            addTime(invitationTime);
            postDelayed(30 * 1000);
        }
    }

    /**
     * 正在邀请延时变成可以邀请
     *
     * @param i
     */
    private void postDelayed(long i) {

        if (customDialog != null) {
            TextView yqdh = (TextView) customDialog.getView(R.id.visitor_yqdh);
            yqdh.setText("正在邀请...");
        }
        handler.postDelayed(runnable, i);
    }


    @Override
    public void showActive(IneValuateEntity.DataBean dataBean) {
        int suc = dataBean.get_suc();
        if (suc == 0) {
            showShortToast("该访客已离开，无法发起对话");
            MessageEntity messageEntity=new MessageEntity();
            messageEntity.setRealtimeId(realtimeId);
            delData(messageEntity);
        }
        if (suc == 1) {
            if (customDialog != null) {
                customDialog.dismiss();
            }
            MessageEntity message = new MessageEntity();
            message.setAct(EVENTBUS_NEWDIALOG_VISITOR);//通知跳转聊天页面
            message.setRealtimeId(realtimeId);
            MessageEvent event = new MessageEvent(MessageEventType.EventMessage, message);
            EventBus.getDefault().postSticky(event);
        }
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
                updateData(message);

                break;
            //实时访客修改
            case MESSAGE_REALTIME_MODIFY:
                //字段名称重复以object作为接收对象去解析
                VisitorEntity.DataBean.ListBean item = JsonParseUtils.parseToObject(message.getItem().toString(), VisitorEntity.DataBean.ListBean.class);
                for (int i = 0; i < listBeans.size(); i++) {
                    String id = item.get_id();
                    if (id == null) {
                        id = item.getId();
                    }
                    if (id != null) {
                        String listId = listBeans.get(i).get_id();
                        if (listId == null) {
                            listId = listBeans.get(i).getId();
                        }
                        if (listId.equals(id)) {
                            if (item.getName() != null) {
                                listBeans.get(i).setName(item.getName());
                            }
                            if (item.getHead() != null) {
                                listBeans.get(i).setHead(item.getHead());
                            }
                            if (item.getCurrent() != null) {
                                listBeans.get(i).setCurrent(item.getCurrent());
                            }
                            if (mUserVisibleHint) {
                                visitorAdapter.notifyItemChanged(i);
                            }
                        }
                    }
                }


                break;
            //实时访客删除
            case MESSAGE_REALTIME_DEL:
                delData(message);
                break;
            //客服在线状态变化
            case MESSAGE_STATEUPATE:
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
                changeState(message);
                break;
        }
    }

    /**
     * 添加访客
     *
     * @param message
     */
    private synchronized void updateData(MessageEntity message) {
        VisitorEntity.DataBean.ListBean visitorEntity = message.getRealtime();
        if (visitorEntity != null) {
            for (int i = 0; i < listBeans.size(); i++) {
                VisitorEntity.DataBean.ListBean bean = listBeans.get(i);
//                在新增前需要判定当前访客列表是否已经存在该实时访客 通过传递 json 里的 { id, customerId, visitorId } 三个参数来判定 遍历比对当前访客这三个值只有有其一相等则删除再新增
                boolean needDel = false;
                if ((bean.get_id() != null && visitorEntity.get_id() != null && bean.get_id().equals(visitorEntity.get_id())) || (bean.getId() != null && visitorEntity.getId() != null && bean.getId().equals(visitorEntity.getId()))) {
                    needDel = true;
                }
                if (bean.getCustomerId() != null && visitorEntity.getCustomerId() != null && bean.getCustomerId().equals(visitorEntity.getCustomerId())) {
                    needDel = true;
                }
                if (bean.getVisitorId() != null && visitorEntity.getVisitorId() != null && bean.getVisitorId().equals(visitorEntity.getVisitorId())) {
                    needDel = true;
                }
                if (needDel) {
                    visitorAdapter.remove(i);
                }
            }
            showContentView();
            visitorAdapter.addData(0, message.getRealtime());
            if (mFirstVisiblePosition < 5) {
                fgVisitorRecycler.smoothScrollToPosition(0);
            }
        }

            ((MainActivity) getHcActivity()).getNavigationBar().setMsgPointCount(1, listBeans.size());


    }

    /**
     * 删除访客
     * @param
     */
    private synchronized void delData(MessageEntity messageEntity) {


            for (int i = 0; i < listBeans.size(); i++) {
                VisitorEntity.DataBean.ListBean bean = listBeans.get(i);
// 删除一个访客
//删除提供的匹配字段{ realtimeId, vcId, customerId, visitorId }
//其中 vcId 有可能是 customerId 也有可能是 visitorId 因此这里的判定逻辑要复杂些。
                boolean needDel = false;

                //如果提供了realtimeId则直接根据Id判断删除
                if (messageEntity.getRealtimeId() != null && ((bean.getId()!=null&&messageEntity.getRealtimeId().equals(bean.getId()))||(bean.get_id()!=null&&bean.get_id().equals(messageEntity.getRealtimeId())))) {
                    needDel = true;
                }
                //如果访客有customerid则根据customerId和vcId判定删除
                if (bean.getCustomerId()!=null){
                    if (messageEntity.getCustomerId()!=null&&bean.getCustomerId().equals(messageEntity.getCustomerId()))needDel=true;
                    if (messageEntity.getVcId()!=null&&bean.getCustomerId().equals(messageEntity.getVcId()))needDel=true;
                }
                //如果当前访客有visitorId则根据visitorId和vcId判定删除
                if (bean.getVisitorId()!=null){
                    if (messageEntity.getVisitorId()!=null&&bean.getVisitorId().equals(messageEntity.getVisitorId()))needDel=true;
                    if (messageEntity.getVcId()!=null&&bean.getVisitorId().equals(messageEntity.getVcId()))needDel=true;
                }

                if (needDel) {
                    visitorAdapter.remove(i);
                }
            }
            if (listBeans.size() == 0) {
                showEmptyView("当前没有在线访客");
            }
                ((MainActivity) getHcActivity()).getNavigationBar().setMsgPointCount(1, listBeans.size());
        }


    /**
     * 添加正在邀请时间
     */
    private void addTime(String time) {

        if (time != null) {
            for (int i = 0; i < listBeans.size(); i++) {
                VisitorEntity.DataBean.ListBean bean = listBeans.get(i);
//                在新增前需要判定当前访客列表是否已经存在该实时访客 通过传递 json 里的 { id, customerId, visitorId } 三个参数来判定 遍历比对当前访客这三个值只有有其一相等则删除再新增
                boolean needAdd = false;
                if (bean.get_id() != null && bean.get_id().equals(realtimeId) || bean.getId() != null && bean.getId().equals(realtimeId)) {
                    needAdd = true;
                }
                if (bean.getCustomerId() != null && bean.getCustomerId().equals(realtimeId)) {
                    needAdd = true;
                }
                if (bean.getVisitorId() != null && bean.getVisitorId().equals(realtimeId)) {
                    needAdd = true;
                }
                if (needAdd) {
                    bean.setInvitationTime(time);
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){
            mUserVisibleHint = false;//不可见
            Log.i(TAG,"不可见");
        }else {
            mUserVisibleHint = true;//可见
            visitorAdapter.notifyDataSetChanged();
            Log.i(TAG,"可见");
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
        VisitorEntity.DataBean.ListBean visitorEntity = (VisitorEntity.DataBean.ListBean) listBeans.get(position);
        realtimeId = visitorEntity.get_id();//防止id字段拿不到报错
        if (realtimeId == null) {
            realtimeId = visitorEntity.getId();
        }
        routesBeans.clear();
        mPresenter.pGetroutes(realtimeId, null, null);
        //点击全部的分类操作
        CustomDialog.Builder builder = new CustomDialog.Builder(getHcActivity());
        if (customDialog == null) {
            customDialog = builder
                    .view(R.layout.visitor_dialog)
                    .style(R.style.Dialog)
                    .setRecyclerView(R.id.visitor_recycler_dialog, popWindowListAdapter)
                    .cancelTouchout(true)
                    .build();
        }

        TextView ip = (TextView) customDialog.getView(R.id.visitor_ip);
        TextView url = (TextView) customDialog.getView(R.id.visitor_url);
        TextView tag = (TextView) customDialog.getView(R.id.visitor_tag);
        TextView yqdh = (TextView) customDialog.getView(R.id.visitor_yqdh);
        TextView zddh = (TextView) customDialog.getView(R.id.visitor_zddh);
        yqdh.setText("邀请对话");
        RecyclerView recyclerView = (RecyclerView) customDialog.getView(R.id.fg_visitor_recycler);
        View loading = getHcActivity().getLayoutInflater().inflate(R.layout.visitor_empty_view, recyclerView, false);
        popWindowListAdapter.setEmptyView(loading);
        String state = BaseApplication.getUserBean().getState();
        if (state.equals("on") && visitorEntity.getDialogId() == null) {
            yqdh.setVisibility(View.VISIBLE);
            zddh.setVisibility(View.VISIBLE);
        }else {
            yqdh.setVisibility(View.GONE);
            zddh.setVisibility(View.GONE);
        }

        if (visitorEntity.getCustomerId() != null && visitorEntity.getDialogId() == null) {
            //必须有 customerId 并且 没有 dialogid 才是回头客
            tag.setVisibility(View.VISIBLE);
        } else {
            tag.setVisibility(View.GONE);
        }
        if (visitorEntity.getDevice() != null) {
            ip.setText(visitorEntity.getDevice().getIp());
//            if (visitorEntity.getDevice().getUrl() != null) {
//                String source = visitorEntity.getDevice().getUrl().getSource();
//                if (source != null && !source.equals("DirectEntry")) {//判断来源字段显示
//                    url.setTextColor(getHcActivity().getResources().getColor(R.color.break_tv));
//                    url.setText(source);
//                    url.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
//                            Intent intent = new Intent();
//                            intent.setAction("android.intent.action.VIEW");
//                            Uri targetUrl = Uri.parse(source);
//                            intent.setData(targetUrl);
//                            getHcActivity().startActivity(intent);
//                        }
//                    });
//
//                } else {
//                    url.setTextColor(getHcActivity().getResources().getColor(R.color.black_aa));
//                    url.setText("直接访问");
//                }
//            }
        }

        if (visitorEntity.getAnalysisUrl()!=null){
            VisitorEntity.DataBean.ListBean.AnalysisUrlBean.SourceBean source = visitorEntity.getAnalysisUrl().getSource();
            if ( source!= null) {//判断来源字段显示
                url.setTextColor(getHcActivity().getResources().getColor(R.color.break_tv));
                url.setText(source.getTitle());
                url.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri targetUrl = Uri.parse(source.getUrl());
                        intent.setData(targetUrl);
                        getHcActivity().startActivity(intent);
                    }
                });

            } else {
                url.setTextColor(getHcActivity().getResources().getColor(R.color.black_aa));
                url.setText("直接访问");
            }
        }
        //设置正在邀请和邀请

        if (visitorEntity.getInvitationTime() != null) {
            long time = DateUtils.getDate(visitorEntity.getInvitationTime()).getTime();
            long l = DateUtils.getCurrentTimeInLong() - time;
            if (l < 30 * 1000) {//如果正在邀请没有超过30S则显示正在邀请
                postDelayed(l);
            }
        }
        //邀请对话
        yqdh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FastUtils.isFastClick()) {
                    if (visitorEntity.getInvitationTime() != null) {
                        long time = DateUtils.getDate(visitorEntity.getInvitationTime()).getTime();
                        long l = DateUtils.getCurrentTimeInLong() - time;
                        if (l >= 30 * 1000) {//如果正在邀请没有超过30S则显示正在邀请
                            mPresenter.pVisitorInvitation(realtimeId);
                        }
                    } else {
                        mPresenter.pVisitorInvitation(realtimeId);
                    }
                }
            }
        });
        //主动对话
        zddh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FastUtils.isFastClick()) {
                    mPresenter.pRealtimeActive(realtimeId);
                }
            }
        });
        customDialog.show();
        customDialog.setAutoHeight();//设置自适应宽高
        customDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                handler.removeCallbacks(runnable);
            }
        });
    }

    /**
     * 全局handler
     */
    private Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message message) {
            if (message.what==MESSAGE_INVITATION){

            }
            return true;
        }
    });
    /**
     * 单列runnable
     */
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            if (customDialog != null) {
                TextView yqdh = (TextView) customDialog.getView(R.id.visitor_yqdh);
                yqdh.setText("邀请对话");
            }
        }
    };
    //全局状态变化
    private void changeState(MessageEntity message) {

                if (message.getState() != null && (message.getState().equals("on")||message.getState().equals("off"))) {
                    listBeans.clear();
                    visitorAdapter.notifyItemRangeChanged(0,listBeans.size());//防止java.lang.IndexOutOfBoundsException: Inconsistency detected. Invalid view holder adapter positionViewHolder
                    routesBeans.clear();
                    showLoadingView();
                    mPresenter.pVisitorList();
                }
                if (message.getState() != null && message.getState().equals("break")) {
                    showEmptyView(getHcActivity().getResources().getString(R.string.break_online));
                }

    }
}
