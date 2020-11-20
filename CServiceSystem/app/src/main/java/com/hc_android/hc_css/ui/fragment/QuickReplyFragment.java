package com.hc_android.hc_css.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.adapter.ExpandAdapter;
import com.hc_android.hc_css.adapter.QuickListAdapter;
import com.hc_android.hc_css.adapter.QuickTextAdapter;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.base.BaseFragment;
import com.hc_android.hc_css.contract.MDiaglogFragmentContract;
import com.hc_android.hc_css.entity.ExpandEntity;
import com.hc_android.hc_css.entity.QConfigEntity;
import com.hc_android.hc_css.entity.QuickCacheEntity;
import com.hc_android.hc_css.entity.QuickEntity;
import com.hc_android.hc_css.entity.QuickGroupEntity;
import com.hc_android.hc_css.presenter.MDiaglogFragmentPresenter;
import com.hc_android.hc_css.ui.activity.ChatActivity;
import com.hc_android.hc_css.ui.activity.QSettingActivity;
import com.hc_android.hc_css.ui.activity.QuickActivity;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.JsonParseUtils;
import com.hc_android.hc_css.utils.NullUtils;
import com.hc_android.hc_css.utils.android.SharedPreferencesUtils;
import com.hc_android.hc_css.utils.android.app.AppConfig;
import com.hc_android.hc_css.wight.LocalDataSource;
import com.hc_android.hc_css.wight.MDialogFragment;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class QuickReplyFragment extends BaseFragment<MDiaglogFragmentPresenter, QuickEntity.DataBean> implements MDiaglogFragmentContract.View, BaseQuickAdapter.OnItemChildClickListener {


    @BindView(R.id.quick_reply_recyclerView)
    RecyclerView quickReplyRecyclerView;
    @BindView(R.id.flowLayout_quick)
    TagFlowLayout flowLayoutQuick;
    @BindView(R.id.quick_reply_list)
    RecyclerView quickReplyList;
    @BindView(R.id.no_group)
    TextView noGroup;
    @BindView(R.id.search_et)
    EditText searchEt;
    @BindView(R.id.quick_setting)
    TextView quickSetting;
    @BindView(R.id.tag_lin)
    RelativeLayout tagLin;
    private QuickTextAdapter quickTextAdapter;
    private QuickListAdapter quickListAdapter;
    private ExpandAdapter expandAdapter;
    private TagAdapter tagAdapter;
    private List<QuickGroupEntity.DataBean.GroupingBean> strings;
    private static final int TEAM = 0;
    private static final int MINE = 1;
    List<QuickEntity.DataBean.ListBean> listBeans;
    List<QuickGroupEntity.DataBean.GroupingBean> groupingBeans;
    List<MultiItemEntity> multiItemEntities;
    private Integer stringType, mineType;
    private QConfigEntity qconfig;

    public static QuickReplyFragment newInstance(int mode) {
        Bundle bundle = new Bundle();
        bundle.putInt("MODE", mode);
        QuickReplyFragment fragment = new QuickReplyFragment();
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public MDiaglogFragmentPresenter getPresenter() {
        return new MDiaglogFragmentPresenter();
    }

    @Override
    protected int injectTarget() {
        return 0;
    }

    @Override
    protected void doRetry() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.i(TAG, "onAttach");
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.quick_reply_fragment;
    }

    @Override
    protected void initView() {
        strings = new ArrayList<>();
        listBeans = new ArrayList<>();
        groupingBeans = new ArrayList<>();
        qconfig = AppConfig.getConfigEntity();
        List<String> stringList = Arrays.asList(getResources().getStringArray(R.array.quick_text_list));
        for (int i = 0; i < stringList.size(); i++) {
            strings.add(new QuickGroupEntity.DataBean.GroupingBean(stringList.get(i)));
        }
        quickTextAdapter = new QuickTextAdapter(strings);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getHcActivity(), 5);
        quickReplyRecyclerView.setLayoutManager(gridLayoutManager);
        quickReplyRecyclerView.setAdapter(quickTextAdapter);


        tagAdapter = new TagAdapter<QuickGroupEntity.DataBean.GroupingBean>(groupingBeans) {
            @Override
            public View getView(FlowLayout parent, int position, QuickGroupEntity.DataBean.GroupingBean o) {
                View view = getHcActivity().getLayoutInflater().inflate(R.layout.quick_reply_text, null);
                TextView textView = view.findViewById(R.id.text_quick);
                textView.setMaxEms(10);
                textView.setPadding(0, 0, 0, 0);
                textView.setText(o.getName());
                if (o.isSelected()) {
                    textView.setTextColor(getContext().getResources().getColor(R.color.black));
                } else {
                    textView.setTextColor(getContext().getResources().getColor(R.color.break_tv));
                }
                textView.setOnClickListener(view1 -> {
                    for (int i = 0; i < groupingBeans.size(); i++) {
                        if (position == i) {
                            if (groupingBeans.get(i).isSelected()) {
                                groupingBeans.get(i).setSelected(false);
                                mineType = null;
                            } else {
                                groupingBeans.get(i).setSelected(true);
                                mineType = i;
                            }
                        } else {
                            groupingBeans.get(i).setSelected(false);
                        }
                    }

                    dataChange();
                });
                return view;
            }

        };
        flowLayoutQuick.setAdapter(tagAdapter);

        multiItemEntities = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        quickReplyList.setLayoutManager(layoutManager);
        quickListAdapter = new QuickListAdapter(listBeans);
        expandAdapter = new ExpandAdapter(multiItemEntities);

        if (qconfig!=null) {
            //设置快捷回复配置
            if (qconfig.isOpenTypePick()) {
                quickReplyRecyclerView.setVisibility(View.VISIBLE);
            } else {
                quickReplyRecyclerView.setVisibility(View.GONE);
            }
            if (qconfig.isFoldType()) {
                tagLin.setVisibility(View.GONE);
                quickReplyRecyclerView.setVisibility(View.GONE);
                quickReplyList.setAdapter(expandAdapter);
            } else {
                tagLin.setVisibility(View.VISIBLE);
                quickReplyRecyclerView.setVisibility(View.VISIBLE);
                quickReplyList.setAdapter(quickListAdapter);
            }
        }



        //搜索框实时监听
        searchEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.length() != 0 && s.toString().trim().length() != 0) {
                    List<QuickEntity.DataBean.ListBean> list = new ArrayList<>();
                    for (int i = 0; i < listBeans.size(); i++) {
                        QuickEntity.DataBean.ListBean bean = listBeans.get(i);
                        //如果是图片就查看是否包含
                        if (bean.getType() != null && bean.getType().equals("text")) {
                            if (bean.getContent() != null && bean.getContent().contains(s)) {
                                list.add(bean);
                            } else if (bean.getName() != null && bean.getName().contains(s)) {
                                list.add(bean);
                            }
                        } else {
                            if (bean.getName() != null && bean.getName().contains(s)) list.add(bean);
                        }
                    }
                    tagLin.setVisibility(View.GONE);
                    quickReplyRecyclerView.setVisibility(View.GONE);
                    quickListAdapter.setNewData(list);
                    quickReplyList.setAdapter(quickListAdapter);
                    quickListAdapter.notifyDataSetChanged();

                } else {
                    tagLin.setVisibility(View.VISIBLE);
                    quickReplyRecyclerView.setVisibility(View.VISIBLE);
                    quickReplyList.setAdapter(expandAdapter);
                    quickListAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        quickTextAdapter.bindToRecyclerView(quickReplyRecyclerView);
        quickListAdapter.setOnItemChildClickListener(this);
        quickTextAdapter.setOnItemChildClickListener(this);
        expandAdapter.setOnItemChildClickListener(this);

        int mode = getArguments().getInt("MODE");
        String cache = (String) SharedPreferencesUtils.getInstance().get(Constant.QUICKCACHE, "");
        QuickCacheEntity cacheEntity = JsonParseUtils.parseToObject(cache, QuickCacheEntity.class);

        switch (mode) {
            case TEAM:
                if (cacheEntity != null && cacheEntity.getvCurrent() != null && cacheEntity.getvCurrent() == TEAM) {//记忆缓存获取
                    stringType = cacheEntity.getStringType();
                    mineType = cacheEntity.getMineType();
                }
                mPresenter.pGetQuickGroup(null, "quick");

                if (AppConfig.hasQuickList_T) {
                    mPresenter.pGetQuickList(null, true);
                } else {
                    if (!NullUtils.isEmptyList(LocalDataSource.getQUICKETEAMLIST())) {
                        listBeans.addAll(LocalDataSource.getQUICKETEAMLIST());
                        dataChange();
                    } else {
                        mPresenter.pGetQuickList(null, true);
                    }
                }
                break;
            case MINE:
                if (cacheEntity != null && cacheEntity.getvCurrent() != null && cacheEntity.getvCurrent() == MINE) {//记忆缓存获取
                    stringType = cacheEntity.getStringType();
                    mineType = cacheEntity.getMineType();
                }
                mPresenter.pGetQuickGroup(BaseApplication.getUserBean().getId(), "quick");

                if (AppConfig.hasQuickList_M) {
                    mPresenter.pGetQuickList(BaseApplication.getUserBean().getId(), false);
                } else {
                    if (!NullUtils.isEmptyList(LocalDataSource.getQUICKELIST())) {
                        listBeans.addAll(LocalDataSource.getQUICKELIST());
                        dataChange();
                    } else {
                        mPresenter.pGetQuickList(BaseApplication.getUserBean().getId(), false);
                    }
                }
                break;
        }
    }

    @Override
    public void showDataError(String errorMessage) {

    }

    @Override
    public void showDataSuccess(QuickEntity.DataBean datas) {

    }


    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

        switch (view.getId()) {
            case R.id.text_quick:

                for (int i = 0; i < strings.size(); i++) {
                    if (position == i) {
                        if (strings.get(i).isSelected()) {
                            strings.get(i).setSelected(false);
                            stringType = null;
                        } else {
                            strings.get(i).setSelected(true);
                            stringType = i;
                        }
                    } else {
                        strings.get(i).setSelected(false);
                    }
                }

                dataChange();
                break;
            case R.id.quick_list_lin:
                QuickEntity.DataBean.ListBean listBean = (QuickEntity.DataBean.ListBean) adapter.getData().get(position);
//                mPresenter.pQuickUse(((QuickEntity.DataBean.ListBean) adapter.getData().get(position)).getId());
                String s = JsonParseUtils.parseToJson(listBean);
                Intent intent = new Intent();
                intent.putExtra("QUICKLIST",s);
                getActivity().setResult(Activity.RESULT_OK, intent);
                getActivity().finish();

                if (getHcActivity() instanceof OnSendMsgListener) {

                    if (listBean!=null)((OnSendMsgListener) getHcActivity()).toSendMsgForFragment(listBean);
                }
                break;
        }
    }

    /**
     * 类型数据改变更新列表
     */
    private void dataChange() {

        String type = null;
        String tags = null;

        for (int i = 0; i < strings.size(); i++) {
            if (stringType != null && i == stringType) {
                strings.get(i).setSelected(true);
            }
            if (strings.get(i).isSelected()) {
                type = strings.get(i).getName();
            }
        }
        for (int i = 0; i < groupingBeans.size(); i++) {
            if (mineType != null && i == mineType) {
                groupingBeans.get(i).setSelected(true);
            }
            if (groupingBeans.get(i).isSelected()) {
                tags = groupingBeans.get(i).getId();
            }
        }
        quickTextAdapter.notifyDataSetChanged();
        tagAdapter.notifyDataChanged();
        List<QuickEntity.DataBean.ListBean> listBean = new ArrayList<>();
        List<QuickEntity.DataBean.ListBean> quickelist = null;//本地原始数据
        int mode = getArguments().getInt("MODE");
        if (mode == MINE) {
            quickelist = LocalDataSource.getQUICKELIST();
        }
        if (mode == TEAM) {
            quickelist = LocalDataSource.getQUICKETEAMLIST();
        }
        if (quickelist == null) return;

        if (quickelist.size()>0&&!qconfig.isUseCounts()){//如果没有选择按照使用次数排序则按addtime排序
            Collections.sort(quickelist, (o1, o2) -> {
                if (o2.getAddtime()<o1.getAddtime())return -1;
                return 1;
            });
        }
        for (int i = 0; i < quickelist.size(); i++) {//筛选符合条件的列表
            if (type == null && tags == null) {
                listBean.add(quickelist.get(i));
            } else if (tags != null && type != null) {
                if (quickelist.get(i).getGroupingId() != null && quickelist.get(i).getGroupingId().equals(tags) && quickelist.get(i).getType().equals(type)) {
                    listBean.add(quickelist.get(i));
                }
            } else if (tags != null) {
                if (quickelist.get(i).getGroupingId() != null && quickelist.get(i).getGroupingId().equals(tags)) {
                    listBean.add(quickelist.get(i));
                }
            } else if (type != null) {
                if (quickelist.get(i).getType().equals(type)) {
                    listBean.add(quickelist.get(i));
                }
            }
        }
        //更新展示数据
        quickListAdapter.setNewData(listBean);
        quickListAdapter.notifyDataSetChanged();

    }

    @Override
    public void showQuickList(QuickEntity.DataBean dataBean) {
        int mode = getArguments().getInt("MODE");
        if (!NullUtils.isEmptyList(dataBean.getList())) {
//            listBeans.addAll(dataBean.getList());
//            dataChange();
            if (mode == MINE) {
                AppConfig.hasQuickList_M = false;
                LocalDataSource.setQUICKELIST(dataBean.getList());
                listBeans.addAll(LocalDataSource.getQUICKELIST());
                if (!qconfig.isFoldType()){
                dataChange();
                }
            }
            if (mode == TEAM) {
                AppConfig.hasQuickList_T = false;
                LocalDataSource.setQUICKETEAMLIST(dataBean.getList());
                listBeans.addAll(LocalDataSource.getQUICKETEAMLIST());
                if (!qconfig.isFoldType()){
                dataChange();
                }
            }
            if (qconfig.isFoldType()){
            setExpandList();
            }
        }
    }

    @Override
    public void showQuickGroup(QuickGroupEntity.DataBean dataBean) {

        if (!NullUtils.isEmptyList(dataBean.getGrouping())) {
            groupingBeans.addAll(dataBean.getGrouping());
            if (!qconfig.isFoldType()) {
                dataChange();
            }
            if (noGroup != null) noGroup.setVisibility(View.GONE);
        } else {
            if (noGroup != null) noGroup.setVisibility(View.VISIBLE);
        }
        if (qconfig.isFoldType()){
        setExpandList();

        }

    }

    @OnClick(R.id.quick_setting)
    public void onViewClicked() {
        startActivity(QSettingActivity.class);
    }


    public interface OnSendMsgListener {

        void toSendMsgForFragment(QuickEntity.DataBean.ListBean listBean);
    }

    public QuickCacheEntity getCache(int currentItem) {

        QuickCacheEntity quickCacheEntity = new QuickCacheEntity();
        if (stringType != null) {
            quickCacheEntity.setStringType(stringType);
        }
        if (mineType != null) {
            quickCacheEntity.setMineType(mineType);
        }
        return quickCacheEntity;
    }

    /**
     * 获取分组列表
     */
    public synchronized void setExpandList() {

        if (listBeans.size() != 0) {
            //如果没有选择按照使用次数排序则按addtime排序
            if (!qconfig.isUseCounts()) {
                Collections.sort(listBeans, (o1, o2) -> {
                    if (o2.getAddtime()<o1.getAddtime())return -1;
                    return 1;
                });
            }
            boolean isHas = false;
            for (MultiItemEntity multiItemEntity : multiItemEntities) {
                if (((ExpandEntity)multiItemEntity).getTitle().equals("未分组"))isHas = true;
            }
            //首先添加没有id表示未分组的列表
            ExpandEntity expandEntity = new ExpandEntity("未分组", "0");
            if (!isHas) {
                for (int i = 0; i < listBeans.size(); i++) {
                    String groupingId = listBeans.get(i).getGroupingId();
                    if (groupingId == null) {
                        expandEntity.addSubItem(listBeans.get(i));
                    } else {
                        if (groupingBeans != null && groupingBeans.size() > 0) {//groupId没有匹配的也放到未分组里面
                            boolean isAdd = true;
                            for (int i1 = 0; i1 < groupingBeans.size(); i1++) {
                                if (groupingId != null && groupingId.equals(groupingBeans.get(i1).getId()))
                                    isAdd = false;
                            }
                            if (isAdd) expandEntity.addSubItem(listBeans.get(i));
                        }
                    }
                }
                if (expandEntity.getSubItems() != null) {
                    expandEntity.setCount(expandEntity.getSubItems().size() + "");
                }
                multiItemEntities.add(expandEntity);
            }
            if (groupingBeans != null && groupingBeans.size() > 0) {
                //添加已分组列表(223233)
                for (int i = 0; i < groupingBeans.size(); i++) {
                    ExpandEntity expandEntity1 = new ExpandEntity(groupingBeans.get(i).getName(), "0");
                    for (int i1 = 0; i1 < listBeans.size(); i1++) {
                        String groupingId = listBeans.get(i1).getGroupingId();
                        if (groupingId != null && groupingId.equals(groupingBeans.get(i).getId())) {
                            expandEntity1.addSubItem(listBeans.get(i1));
                        }
                    }
                    if (expandEntity1.getSubItems() != null) {
                        expandEntity1.setCount(expandEntity1.getSubItems() != null ? expandEntity1.getSubItems().size() + "" : "0");
                    }
                        multiItemEntities.add(expandEntity1);

                }
            }

            expandAdapter.setNewData(multiItemEntities);
            expandAdapter.notifyDataSetChanged();
        }

    }

}
