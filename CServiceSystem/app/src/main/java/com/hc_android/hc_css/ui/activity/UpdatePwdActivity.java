package com.hc_android.hc_css.ui.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.adapter.ParamListAdapter;
import com.hc_android.hc_css.base.BaseActivity;
import com.hc_android.hc_css.base.BaseConfig;
import com.hc_android.hc_css.contract.UpdatePwdActivityContract;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.entity.ParamEntity;
import com.hc_android.hc_css.presenter.UpdatePwdActivityPresenter;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.NullUtils;
import com.hc_android.hc_css.wight.ChoiceDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UpdatePwdActivity extends BaseActivity<UpdatePwdActivityPresenter, IneValuateEntity.DataBean> implements UpdatePwdActivityContract.View, ChoiceDialog.ChoiceCancelCallBack {


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
    @BindView(R.id.update_pwd_new)
    EditText updatePwdNew;
    @BindView(R.id.lin_pwd)
    LinearLayout linPwd;
    @BindView(R.id.update_pwd_recycler)
    RecyclerView updatePwdRecycler;

    private List<ParamEntity> listEntityList;
    private String _TYPE;
    private String MODE="UPDATE";
    private String title;
    private String params;
    private ParamListAdapter textListAdapter;

    @Override
    public UpdatePwdActivityPresenter getPresenter() {
        return new UpdatePwdActivityPresenter();
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
        Bundle extras = getIntent().getExtras();
        if (extras!= null) {
            _TYPE = extras.getString(Constant.INTENT_TYPE);
            MODE = extras.getString(Constant.STYLETYPE);
            title = extras.getString(Constant._TITLE);
            params = extras.getString(Constant._PARAMS);
            titleActUpdate.setText(title);
            if (MODE!=null) {
                switch (MODE) {

                    case Constant.LISTTYPE_:
                    case Constant.LISTTYPE_ONE:
                        linPwd.setVisibility(View.GONE);
                        updatePwdRecycler.setVisibility(View.VISIBLE);

                        ArrayList<ParamEntity> parcelableArrayList = extras.getParcelableArrayList(Constant._LISTSTRING);
                        if (parcelableArrayList != null) {
                            listEntityList.addAll(parcelableArrayList);
                        }

                        textListAdapter.notifyDataSetChanged();
                        break;
                }
            }else {
                updateEt.setFocusable(true);
                updateEt.setFocusableInTouchMode(true);
                updateEt.requestFocus();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
            }
        }

    }

    @Override
    protected void initView() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .keyboardEnable(true)
                .statusBarColor(R.color.theme_app)
                .titleBar(R.id.act_update)
                .init();
        listEntityList=new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        updatePwdRecycler.setLayoutManager(layoutManager);
        textListAdapter=new ParamListAdapter(listEntityList);
        updatePwdRecycler.setAdapter(textListAdapter);
        textListAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                for (int i = 0; i < listEntityList.size(); i++) {

                    if (i==position){//判断是否是多选模式
                        if (MODE.equals(Constant.LISTTYPE_)) {
                            if (listEntityList.get(i).isChecked()) {
                                listEntityList.get(i).setChecked(false);
                            } else {
                                listEntityList.get(i).setChecked(true);//如果已选中则取消选中
                            }
                        }else if (MODE.equals(Constant.LISTTYPE_ONE)){
                            listEntityList.get(i).setChecked(true);
                        }
                    }else {
                        if (MODE.equals(Constant.LISTTYPE_ONE)) {
                            listEntityList.get(i).setChecked(false);
                        }
                    }
                }
                textListAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getContentView() {
        return R.layout.activity_update_pwd;
    }

    @Override
    public void showDataSuccess(IneValuateEntity.DataBean datas) {

        String text = "输入当前密码错误";
        if (datas.get_suc() == 1) {
            text = "修改成功";
        }
        ChoiceDialog customDialog = new ChoiceDialog(this, text,datas.get_suc());
        customDialog.setCancelCallBack(this);


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
                finish();
                break;
            case R.id.update_save:

                    if (MODE!=null&&(MODE.equals(Constant.LISTTYPE_)||MODE.equals(Constant.LISTTYPE_ONE))){//单选列表


                    if (!NullUtils.isNull(_TYPE)&&_TYPE.equals(Constant.MINEFG_)){//个人中心在线 状态
                        String checkedStr=null;
                        for (int i = 0; i < listEntityList.size(); i++) {
                            if (listEntityList.get(i).isChecked()){
                                checkedStr=listEntityList.get(i).getTitle();
                            }

                        }
                    String state="";
                    if (checkedStr.equals("在线"))state="on";
                    if (checkedStr.equals("隐身"))state="off";
                    if (checkedStr.equals("离线"))state="break";
                        mPresenter.pAccountState(state);
//                        Intent intent = new Intent();
//                        setResult(RESULT_OK, intent);
//                        finish();
                    }else {
                        String checkedString = null;
                        for (int i = 0; i < listEntityList.size(); i++) {
                            if (listEntityList.get(i).isChecked()){
                                if (checkedString!=null){
                                    checkedString=checkedString+"-"+listEntityList.get(i).getTitle();
                                }else {
                                    checkedString = listEntityList.get(i).getTitle();
                                }
                            }
                        }
                        Intent intent = new Intent();
                        intent.putExtra("_CONTENT_TEXT", checkedString);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }else {
                    String cutPwd = updateEt.getText().toString();
                    String newPwd = updatePwdNew.getText().toString();
                    //（提交前验证6至18位字符）

                    if (newPwd.length() > 18 && newPwd.length() < 6) {
                        ChoiceDialog choiceDialog = new ChoiceDialog(this, "请输入6至18位字符的新密码", 1);
                        choiceDialog.setCancelCallBack(this);
                        return;
                    }
                        mPresenter.pAccountModifyPwd(cutPwd, newPwd);

                }
                break;
        }
    }

    /**
     * 关闭界面回调
     */
    @Override
    public void cancelBack() {
        finish();
    }

    @Override
    public void okBack(String s) {
        finish();
    }

    @Override
    public void showState(IneValuateEntity.DataBean dataBean) {
        String checkedStr=null;
        for (int i = 0; i < listEntityList.size(); i++) {
            if (listEntityList.get(i).isChecked()){
                if (checkedStr!=null){
                    checkedStr="-"+listEntityList.get(i).getTitle();
                }else {
                    checkedStr = listEntityList.get(i).getTitle();
                }
            }

        }
        Intent intent=new Intent();
        intent.putExtra("_CONTENT_TEXT",checkedStr);
        setResult(RESULT_OK,intent);
        finish();
    }
}
