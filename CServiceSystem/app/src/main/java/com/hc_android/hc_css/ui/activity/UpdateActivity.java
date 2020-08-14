package com.hc_android.hc_css.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.gyf.immersionbar.ImmersionBar;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.adapter.TextListAdapter;
import com.hc_android.hc_css.base.BaseActivity;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.contract.UpdateActivityContract;
import com.hc_android.hc_css.entity.CardUpdateEntity;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.entity.LoginEntity;
import com.hc_android.hc_css.entity.MessageDialogEntity;
import com.hc_android.hc_css.entity.TagEntity;
import com.hc_android.hc_css.entity.TextListEntity;
import com.hc_android.hc_css.entity.UpdateUserEntity;
import com.hc_android.hc_css.presenter.UpdateActivityPresenter;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.JsonParseUtils;
import com.hc_android.hc_css.utils.NullUtils;
import com.hc_android.hc_css.wight.LocalDataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdateActivity extends BaseActivity<UpdateActivityPresenter, IneValuateEntity.DataBean> implements UpdateActivityContract.View {


    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.title_act_update)
    TextView titleActUpdate;
    @BindView(R.id.update_save)
    TextView updateSave;
    @BindView(R.id.act_update)
    ConstraintLayout actUpdate;
    @BindView(R.id.update_et)
    EditText updateEt;
    @BindView(R.id.update_recycler)
    RecyclerView updateRecycler;


    private TextListAdapter textListAdapter;
    private List<TextListEntity> list;

    public static String MODE =Constant.INPUTTYPE_;
    private String title;
    private MessageDialogEntity.DataBean.ListBean itembean;
    private ArrayList<String> listString;
    private List<TagEntity.DataBean.ListBean> taglist;
    private ArrayList<String> contentList;
    private String _TYPE;
    private String params;
    private LoginEntity.DataBean.InfoBean userBean;


    @Override
    protected void reloadActivity() {

    }

    @Override
    public UpdateActivityPresenter getPresenter() {
        return new UpdateActivityPresenter();
    }

    @Override
    protected View injectTarget() {
        return null;
    }

    @Override
    protected void initData() {
        userBean = BaseApplication.getUserBean();
        itembean = LocalDataSource.getITEMBEAN();
        Bundle extras = getIntent().getExtras();
        _TYPE = extras.getString(Constant.INTENT_TYPE);
        MODE=extras.getString(Constant.STYLETYPE);
        title=extras.getString(Constant._TITLE);
        params=extras.getString(Constant._PARAMS);
        if (!NullUtils.isNull(title)) {
            titleActUpdate.setText("更改"+title);
        }

        switch (MODE){

            case Constant.INPUTTYPE_:
                String inputtext = extras.getString(Constant._INPUT);
                updateEt.setVisibility(View.VISIBLE);
                if (!NullUtils.isNull(inputtext)&&!inputtext.equals("未设置")){
                    updateEt.setText(inputtext);

                }
                updateEt.setFocusable(true);
                updateEt.setFocusableInTouchMode(true);
                updateEt.requestFocus();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                break;
            case Constant.LISTTYPE_:
                    contentList = extras.getStringArrayList(Constant._LISTSTRING);
                    updateEt.setVisibility(View.GONE);
                    taglist = LocalDataSource.getTAGLIST();
                    if (!NullUtils.isEmptyList(taglist)) {
                        for (int i = 0; i < taglist.size(); i++) {
                            if (!NullUtils.isEmptyList(contentList) && contentList.contains(taglist.get(i).getName())) {
                                list.add(new TextListEntity(true, taglist.get(i).getName()));
                            } else {
                                list.add(new TextListEntity(false, taglist.get(i).getName()));
                            }
                        }
                    }

                textListAdapter.notifyDataSetChanged();
                break;
        }


        updateEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//                    if (!TextUtils.isEmpty(updateEt.getText().toString().trim())){
                        updateSave.setClickable(true);
                        updateSave.setEnabled(true);
                        updateSave.setBackgroundResource(R.drawable.green_b);
//                    }else {
//                        updateSave.setEnabled(false);
//                        updateSave.setClickable(false);
//                        updateSave.setBackgroundResource(R.drawable.green_btn);
//                    }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .keyboardEnable(true)
                .statusBarColor(R.color.theme_app)
                .titleBar(R.id.act_update)
                .init();
        list = new ArrayList<>();
        textListAdapter = new TextListAdapter(list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        updateRecycler.setLayoutManager(layoutManager);
        updateRecycler.setAdapter(textListAdapter);

        textListAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            updateSave.setClickable(true);
            updateSave.setEnabled(true);
            updateSave.setBackgroundResource(R.drawable.green_b);
            TextListEntity textListEntity = (TextListEntity) adapter.getData().get(position);
            if (textListEntity.isChecked()) {
                view.findViewById(R.id.item_checked).setVisibility(View.GONE);
                textListEntity.setChecked(false);
            } else {
                view.findViewById(R.id.item_checked).setVisibility(View.VISIBLE);
                textListEntity.setChecked(true);
            }
        });
    }

    @Override
    public int getContentView() {
        return R.layout.activity_update;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.cancel, R.id.update_save})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                hintKbTwo();
                finish();
                break;
            case R.id.update_save:

                if (MODE.equals(Constant.INPUTTYPE_)){
                    String s = updateEt.getText().toString();
//                    if (!NullUtils.isNull(s)){
                    if (_TYPE.equals(Constant.CHATACT_)){//聊天界面
                    HashMap<String,String> hashMap=new HashMap<>();
                    hashMap.put(title,s);
                    String jsonString = JSON.toJSONString(hashMap);
                    Object o = JsonParseUtils.parseToObject(jsonString, Object.class);
                    CardUpdateEntity cardUpdateEntity=new CardUpdateEntity();
                    cardUpdateEntity.setId(itembean.getCustomerId());
                    cardUpdateEntity.setCard(o);
                    cardUpdateEntity.setTag(null);
                    String toJson = JsonParseUtils.parseToJson(cardUpdateEntity);
                    if (title.equals("备注")){
                        mPresenter.pUpdateRemarks(itembean.getId(),s);
                    }else {
                        mPresenter.pUpdateCards(itembean.getId(), toJson);
                    }}
                    if (_TYPE.equals(Constant.PERSONAL_)) {//个人中心修改
                        UpdateUserEntity updateUserEntity=new UpdateUserEntity();
                        if (params.equals("nickname")){
                            updateUserEntity.setNickname(updateEt.getText().toString());

                        }
                        if (params.equals("name")){
                            updateUserEntity.setName(updateEt.getText().toString());
                        }
                        if (params.equals("autograph")){
                            updateUserEntity.setAutograph(updateEt.getText().toString());
                        }
                        String json = JsonParseUtils.parseToJson(updateUserEntity);
                        mPresenter.pAccountModify(json);

                    }

//                    }
                }else if (MODE.equals(Constant.LISTTYPE_)){
                    listString=new ArrayList<>();

                    for (int i = 0; i < list.size(); i++) {
                        if (list.get(i).isChecked()){
                            listString.add(list.get(i).getText());
                        }
                    }
                    String[] str=listString.toArray(new String[listString.size()]);
                    CardUpdateEntity cardUpdateEntity=new CardUpdateEntity();
                    cardUpdateEntity.setId(itembean.getCustomerId());
                    cardUpdateEntity.setTag(str);
                    String toJson = JsonParseUtils.parseToJson(cardUpdateEntity);
                    mPresenter.pUpdateCards(itembean.getId(),toJson);
                    String mids = "";
                    for (int i = 0; i < listString.size(); i++) {//增加次数上传，去重
                        if (!contentList.contains(listString.get(i))){
                            if (mids.length() > 0) {
                                mids = mids + "," + taglist.get(i).getId();
                            } else {
                                mids = taglist.get(i).getId();
                            }
                        }
                    }
                    if (mids.length()>0){
                        mPresenter.pTagUsege(mids);
                    }
                }

                break;
        }
    }


    //此方法只是关闭软键盘 可以在finish之前调用一下
    private void hintKbTwo() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive() && getCurrentFocus() != null) {
            if (getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }


    }
    /**
     * 修改cards数据成功
     * @param datas
     */
    @Override
    public void showDataSuccess(IneValuateEntity.DataBean datas) {
        Intent intent=new Intent();
        intent.putStringArrayListExtra("_CONTENT_LIST",listString);
        intent.putExtra("_CONTENT_TEXT",updateEt.getText().toString());
        setResult(RESULT_OK,intent);
        hintKbTwo();
        finish();
    }

    /**
     * 修改备注数据成功
     * @param
     */
    @Override
    public void updateRemarksSuccess(IneValuateEntity.DataBean dataBean) {
        Intent intent=new Intent();
        intent.putStringArrayListExtra("_CONTENT_LIST",listString);
        intent.putExtra("_CONTENT_TEXT",updateEt.getText().toString());
        setResult(RESULT_OK,intent);
        hintKbTwo();
        finish();
    }

    @Override
    public void accountModifySuccess(IneValuateEntity.DataBean dataBean) {
        if (params.equals("nickname")){
            userBean.setNickname(updateEt.getText().toString());
        }
        if (params.equals("name")){
            userBean.setName(updateEt.getText().toString());
        }
        if (params.equals("autograph")){
            userBean.setAutograph(updateEt.getText().toString());
        }
        Intent intent=new Intent();
        intent.putExtra("_CONTENT_TEXT",updateEt.getText().toString());
        setResult(RESULT_OK,intent);
        hintKbTwo();
        finish();
    }
}
