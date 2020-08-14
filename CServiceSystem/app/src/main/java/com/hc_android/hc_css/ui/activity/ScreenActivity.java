package com.hc_android.hc_css.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.gyf.immersionbar.ImmersionBar;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.adapter.PopWindowListAdapter;
import com.hc_android.hc_css.base.BaseActivity;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.entity.ParamEntity;
import com.hc_android.hc_css.entity.ScreenSaveEntity;
import com.hc_android.hc_css.entity.TagEntity;
import com.hc_android.hc_css.entity.TeamEntity;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.DateUtils;
import com.hc_android.hc_css.utils.NullUtils;
import com.hc_android.hc_css.wight.CustomDateUtils;
import com.hc_android.hc_css.wight.CustomDialog;
import com.hc_android.hc_css.wight.LocalDataSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScreenActivity extends BaseActivity {


    @BindView(R.id.screen_cancel)
    TextView screenCancel;
    @BindView(R.id.title_act_screen)
    TextView titleActScreen;
    @BindView(R.id.history_qd)
    TextView historyQd;
    @BindView(R.id.act_screen)
    ConstraintLayout actScreen;
    @BindView(R.id.srceen_card)
    EditText srceenCard;
    @BindView(R.id.screen_source)
    EditText screenSource;
    @BindView(R.id.screen_remark)
    EditText screenRemark;
    @BindView(R.id.screen_ip)
    EditText screenIp;
    @BindView(R.id.screen_text)
    EditText screenText;
    @BindView(R.id.xm)
    TextView xm;
    @BindView(R.id.img_jt1)
    ImageView imgJt1;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.lin_zdcy)
    RelativeLayout linZdcy;
    @BindView(R.id.bq)
    TextView bq;
    @BindView(R.id.img_jt2)
    ImageView imgJt2;
    @BindView(R.id.bq_tv)
    TextView bqTv;
    @BindView(R.id.lin_bq)
    RelativeLayout linBq;
    @BindView(R.id.pj)
    TextView pj;
    @BindView(R.id.img_jt3)
    ImageView imgJt3;
    @BindView(R.id.pj_tv)
    TextView pjTv;
    @BindView(R.id.lin_pj)
    RelativeLayout linPj;
    @BindView(R.id.jrfs)
    TextView jrfs;
    @BindView(R.id.img_jt4)
    ImageView imgJt4;
    @BindView(R.id.jrfs_tv)
    TextView jrfsTv;
    @BindView(R.id.lin_jrfs)
    RelativeLayout linJrfs;
    @BindView(R.id.wxdh)
    TextView wxdh;
    @BindView(R.id.img_jt5)
    ImageView imgJt5;
    @BindView(R.id.wxdh_tv)
    TextView wxdhTv;
    @BindView(R.id.lin_wxdh)
    RelativeLayout linWxdh;
    @BindView(R.id.yldh)
    TextView yldh;
    @BindView(R.id.img_jt6)
    ImageView imgJt6;
    @BindView(R.id.yldh_tv)
    TextView yldhTv;
    @BindView(R.id.lin_yldh)
    RelativeLayout linYldh;
    @BindView(R.id.start_start_data)
    TextView startStartData;
    @BindView(R.id.start_start_time)
    TextView startStartTime;
    @BindView(R.id.start_end_data)
    TextView startEndData;
    @BindView(R.id.start_end_time)
    TextView startEndTime;
    @BindView(R.id.end_start_data)
    TextView endStartData;
    @BindView(R.id.end_start_time)
    TextView endStartTime;
    @BindView(R.id.end_end_start)
    TextView endEndStart;
    @BindView(R.id.end_end_end)
    TextView endEndEnd;
    @BindView(R.id.screen_reset)
    TextView screenReset;

    private  List<TeamEntity.DataBean.ListBean> teamlist;
    private PopWindowListAdapter popWindowListAdapter;
    private ArrayList<ParamEntity> paramEntities;
    private int SCREEN_ACT=10;

    private TextView clickTv;
    private CustomDialog customDialog;

    @Override
    protected void reloadActivity() {

    }

    @Override
    protected View injectTarget() {
        return null;
    }

    @Override
    protected void initData() {
       teamlist = LocalDataSource.getTEAMSERLIST();
       if (BaseApplication.getUserBean()!=null){
           name.setText(BaseApplication.getUserBean().getName());
       }
       paramEntities=new ArrayList<>();

       resetData(LocalDataSource.getScreenList());
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this)
                .fitsSystemWindows(true)
                .keyboardEnable(true)
                .statusBarColor(R.color.theme_app)
                .titleBar(R.id.act_screen)
                .init();
    }

    @Override
    public int getContentView() {
        return R.layout.activity_screen;
    }

    @Override
    public void showDataSuccess(Object datas) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.screen_cancel,R.id.history_qd,R.id.lin_zdcy, R.id.lin_bq, R.id.lin_pj, R.id.lin_jrfs, R.id.lin_wxdh, R.id.lin_yldh, R.id.start_start_data, R.id.start_start_time, R.id.start_end_data, R.id.start_end_time, R.id.end_start_data, R.id.end_start_time, R.id.end_end_start, R.id.end_end_end, R.id.screen_reset})
    public void onViewClicked(View view) {
        paramEntities.clear();
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.screen_cancel:
                finish();
                break;
            case R.id.history_qd:
                saveData();
                break;
            case R.id.lin_zdcy:
                if (teamlist!=null) {
                    createDiaLog();
                }
                break;
            case R.id.lin_bq:
                clickTv=bqTv;
                String s = bqTv.getText().toString();
                List<TagEntity.DataBean.ListBean> taglist = LocalDataSource.getTAGLIST();
                String[] split = s.split("-");

                if (taglist!=null) {
                    for (int i = 0; i < taglist.size(); i++) {
                        ParamEntity paramEntity = new ParamEntity(taglist.get(i).getName());
                        if (split!=null) {
                            for (int i1 = 0; i1 < split.length; i1++) {
                                if (!NullUtils.isNull(split[i1]) && split[i1].equals(taglist.get(i).getName())) {
                                    paramEntity.setChecked(true);
                                }
                            }
                        }else {
                            if (!NullUtils.isNull(s) && s.equals(taglist.get(i).getName())) {
                                paramEntity.setChecked(true);
                            }
                        }
                        paramEntities.add(paramEntity);
                    }
                }
                bundle.putString(Constant.STYLETYPE, Constant.LISTTYPE_);
                startToActivity(bundle,"标签");
                break;
            case R.id.lin_pj:
                clickTv=pjTv;
                String s1 = pjTv.getText().toString();
                List<String> strings = Arrays.asList(getResources().getStringArray(R.array.ping_status));
                String[] split2 = s1.split("-");
                for (int i = 0; i < strings.size(); i++) {
                    ParamEntity paramEntity = new ParamEntity(strings.get(i));
                    if (split2!=null){
                        for (int i1 = 0; i1 < split2.length; i1++) {
                            if (!NullUtils.isNull(split2[i1]) && split2[i1].equals(strings.get(i))) {
                                paramEntity.setChecked(true);
                            }
                        }
                    }else {
                        if (!NullUtils.isNull(s1) && s1.equals(strings.get(i))) {
                            paramEntity.setChecked(true);
                        }
                    }
                    paramEntities.add(paramEntity);
                }
                bundle.putString(Constant.STYLETYPE, Constant.LISTTYPE_);
                startToActivity(bundle,"评价");
                break;
            case R.id.lin_jrfs:
                clickTv=jrfsTv;
                String s2 = jrfsTv.getText().toString();
                List<String> strings2 = Arrays.asList(getResources().getStringArray(R.array.install_status));
                String[] split3 = s2.split("-");
                for (int i = 0; i < strings2.size(); i++) {
                    ParamEntity paramEntity = new ParamEntity(strings2.get(i));
                    if (split3!=null){
                        for (int i1 = 0; i1 < split3.length; i1++) {
                            if (!NullUtils.isNull(split3[i1]) && split3[i1].equals(strings2.get(i))) {
                                paramEntity.setChecked(true);
                            }
                        }
                    }else {
                        if (!NullUtils.isNull(s2) && s2.equals(strings2.get(i))) {
                            paramEntity.setChecked(true);
                        }
                    }
                    paramEntities.add(paramEntity);
                }
                bundle.putString(Constant.STYLETYPE, Constant.LISTTYPE_);
                startToActivity(bundle,"接入方式");
                break;
            case R.id.lin_wxdh:
                clickTv=wxdhTv;
                String s3 = wxdhTv.getText().toString();
                List<String> strings3 = Arrays.asList(getResources().getStringArray(R.array.look_status));
                for (int i = 0; i < strings3.size(); i++) {
                    ParamEntity paramEntity = new ParamEntity(strings3.get(i));
                    if (!NullUtils.isNull(s3)&&s3.equals(strings3.get(i))) {
                        paramEntity.setChecked(true);
                    }
                    paramEntities.add(paramEntity);
                }
                bundle.putString(Constant.STYLETYPE, Constant.LISTTYPE_ONE);
                startToActivity(bundle,"无效对话");
                break;
            case R.id.lin_yldh:
                clickTv=yldhTv;
                String s4= yldhTv.getText().toString();
                List<String> strings4= Arrays.asList(getResources().getStringArray(R.array.miss_status));
                for (int i = 0; i < strings4.size(); i++) {
                    ParamEntity paramEntity = new ParamEntity(strings4.get(i));
                    if (!NullUtils.isNull(s4)&&s4.equals(strings4.get(i))) {
                        paramEntity.setChecked(true);
                    }
                    paramEntities.add(paramEntity);
                }
                bundle.putString(Constant.STYLETYPE, Constant.LISTTYPE_ONE);
                startToActivity(bundle,"遗漏对话");
                break;
            case R.id.start_start_data:
                CustomDateUtils.getDateTime(this,startStartData,startStartTime,true);
                break;
            case R.id.start_start_time:
                CustomDateUtils.getDateTime(this,startStartData,startStartTime,false);
                break;
            case R.id.start_end_data:
                CustomDateUtils.getDateTime(this,startEndData,startEndTime,true);
                break;
            case R.id.start_end_time:
                CustomDateUtils.getDateTime(this,startEndData,startEndTime,false);
                break;
            case R.id.end_start_data:
                CustomDateUtils.getDateTime(this,endStartData,endStartTime,true);
                break;
            case R.id.end_start_time:
                CustomDateUtils.getDateTime(this,endStartData,endStartTime,false);
                break;
            case R.id.end_end_start:
                CustomDateUtils.getDateTime(this,endEndStart,endEndEnd,true);
                break;
            case R.id.end_end_end:
                CustomDateUtils.getDateTime(this,endEndStart,endEndEnd,false);
                break;
            case R.id.screen_reset:
                resetData(null);
                break;
        }
    }



    /**
     * 跳转操作
     * @param bundle
     * @param s
     */
    private void startToActivity(Bundle bundle, String s) {

        bundle.putString(Constant._TITLE, s);
        bundle.putParcelableArrayList(Constant._LISTSTRING, paramEntities);
        startActivityForResult(UpdatePwdActivity.class,bundle,SCREEN_ACT);
    }

    /**
     * 弹出式对话框
     *
     * @param
     */
    private void createDiaLog() {

        popWindowListAdapter = new PopWindowListAdapter(teamlist,true);
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        if (customDialog==null) {
            //点击全部的分类操作
            customDialog = builder.heightDimenRes(R.dimen.popWindow_height)
                    .widthDimenRes(R.dimen.popWindow_witch)
                    .view(R.layout.colleague_list_popwindow)
                    .style(R.style.Dialog)
                    .setRecyclerView(R.id.colleague_popwindow_recycler, popWindowListAdapter)
                    .cancelTouchout(true)
                    .addViewOnclick(R.id.all_team, view -> {  //点击全部的时候全部显示

                        name.setText(((TextView)view).getText().toString());
                        customDialog.dismiss();
                    })
                    .build();
        }

        customDialog.show();

        popWindowListAdapter.setOnItemChildClickListener((adapter, view, position) -> {//点击同事的时候分类显示
            TeamEntity.DataBean.ListBean bean = (TeamEntity.DataBean.ListBean) adapter.getData().get(position);
            String names = null;
            if (bean.getName() != null) names = bean.getName();
            name.setText(names);
            customDialog.dismiss();

        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==SCREEN_ACT&&data!=null){
            if (clickTv!=null){
                String content = data.getStringExtra("_CONTENT_TEXT");
                clickTv.setText(content);
            }
        }
    }

    /**
     * 重置筛选条件
     */
    private void resetData(ScreenSaveEntity saveEntity) {

        if (saveEntity==null) {
            srceenCard.setText("");
            screenSource.setText("");
            screenRemark.setText("");
            screenIp.setText("");
            screenText.setText("");
            if (BaseApplication.getUserBean()!=null&&BaseApplication.getUserBean().getName() != null) {
                name.setText(BaseApplication.getUserBean().getName());
            }
            bqTv.setText("");
            pjTv.setText("");
            jrfsTv.setText("");
            wxdhTv.setText("显示");
            yldhTv.setText("显示");
            startStartData.setText(DateUtils.getCurrentDateBefore30Day());
            startStartTime.setText(DateUtils.getNowTime_A() + "  " + DateUtils.getNowTime("hh:mm"));
            startEndData.setText("");
            startEndTime.setText("");
            endStartData.setText("");
            endStartTime.setText("");
            endEndStart.setText("");
            endEndEnd.setText("");
        }else {
            if (!NullUtils.isNull(saveEntity.getVague()))srceenCard.setText(saveEntity.getVague());
            if (!NullUtils.isNull(saveEntity.getKeyWord()))screenSource.setText(saveEntity.getKeyWord());
            if (!NullUtils.isNull(saveEntity.getRemarks())) screenRemark.setText(saveEntity.getRemarks());
            if (!NullUtils.isNull(saveEntity.getIp()))screenIp.setText(saveEntity.getIp());
            if (!NullUtils.isNull(saveEntity.getMsg())) screenText.setText(saveEntity.getMsg());
            if (!NullUtils.isNull(saveEntity.getServicename())) name.setText(saveEntity.getServicename());
            if (!NullUtils.isNull(saveEntity.getTag())) bqTv.setText(saveEntity.getTag());
            if (!NullUtils.isNull(saveEntity.getEvaluate()))pjTv.setText(saveEntity.getEvaluate());
            if (!NullUtils.isNull(saveEntity.getSource()))jrfsTv.setText(saveEntity.getSource());
            if (!NullUtils.isNull(saveEntity.getInvalid())) wxdhTv.setText(saveEntity.getInvalid());
            if (!NullUtils.isNull(saveEntity.getMiss())) yldhTv.setText(saveEntity.getMiss());
            if (!NullUtils.isNull(saveEntity.getStartDate())) startStartData.setText(saveEntity.getStartDate());
            if (!NullUtils.isNull(saveEntity.getStartTime()))startStartTime.setText(saveEntity.getStartTime());
            if (!NullUtils.isNull(saveEntity.getEndDate()))startEndData.setText(saveEntity.getEndDate());
            if (!NullUtils.isNull(saveEntity.getEndTime())) startEndTime.setText(saveEntity.getEndTime());
            if (!NullUtils.isNull(saveEntity.getEndAdate())) endStartData.setText(saveEntity.getEndAdate());
            if (!NullUtils.isNull(saveEntity.getEndAtime())) endStartTime.setText(saveEntity.getEndAtime());
            if (!NullUtils.isNull(saveEntity.getEndBdate()))endEndStart.setText(saveEntity.getEndBdate());
            if (!NullUtils.isNull(saveEntity.getEndBtime()))endEndEnd.setText(saveEntity.getEndBtime());
        }
    }
    /**
     * 保存筛选数据
     */
    private void saveData() {

        ScreenSaveEntity screenSaveEntity=new ScreenSaveEntity();
        //顾客名片
        if (!NullUtils.isNull(srceenCard.getText().toString())) {
            screenSaveEntity.setVague(srceenCard.getText().toString());
        }
        //来源
        if (!NullUtils.isNull(screenSource.getText().toString())) {
            screenSaveEntity.setKeyWord(screenSource.getText().toString());
        }
        //备注
        if (!NullUtils.isNull(screenRemark.getText().toString())) {
            screenSaveEntity.setRemarks(screenRemark.getText().toString());
        }
        //IP地址
        if (!NullUtils.isNull(screenIp.getText().toString())) {
            screenSaveEntity.setIp(screenIp.getText().toString());
        }
        //聊天内容
        if (!NullUtils.isNull(screenText.getText().toString())) {
            screenSaveEntity.setMsg(screenText.getText().toString());
        }
        //指定成员（客服id和name）
        if (!NullUtils.isNull(name.getText().toString())&&teamlist!=null) {

            for (int i = 0; i < teamlist.size(); i++) {
                if (teamlist.get(i).getName().equals(name.getText().toString())){
               screenSaveEntity.setServicename(name.getText().toString());
               screenSaveEntity.setId(teamlist.get(i).get_id());
                }
            }
            if (name.getText().equals("全部成员")){
                screenSaveEntity.setServicename("全部成员");
            }
        }else {
                screenSaveEntity.setServicename(BaseApplication.getUserBean().getName());
                screenSaveEntity.setId(BaseApplication.getUserBean().getId());

        }
        //标签
        if (!NullUtils.isNull(bqTv.getText().toString())) {
            screenSaveEntity.setTag(bqTv.getText().toString());
        }
        //评价
        if (!NullUtils.isNull(pjTv.getText().toString())) {
            screenSaveEntity.setEvaluate(pjTv.getText().toString());
        }
        //接入方式
        if (!NullUtils.isNull(jrfsTv.getText().toString())) {
            screenSaveEntity.setSource(jrfsTv.getText().toString());
        }
        //无效对话
        if (!NullUtils.isNull(wxdhTv.getText().toString())) {
            screenSaveEntity.setInvalid(wxdhTv.getText().toString());
        }
        //遗漏对话
        if (!NullUtils.isNull(yldhTv.getText().toString())) {
            screenSaveEntity.setMiss(yldhTv.getText().toString());
        }
        //对话时间
        if (!NullUtils.isNull(startStartData.getText().toString())) {
            screenSaveEntity.setStartDate(startStartData.getText().toString());
        }
        if (!NullUtils.isNull(startStartTime.getText().toString())) {
            screenSaveEntity.setStartTime(startStartTime.getText().toString());
        }
        if (!NullUtils.isNull(startEndData.getText().toString())) {
            screenSaveEntity.setEndDate(startEndData.getText().toString());
        }
        if (!NullUtils.isNull(startEndTime.getText().toString())) {
            screenSaveEntity.setEndTime(startEndTime.getText().toString());
        }
        if (!NullUtils.isNull(endStartData.getText().toString())) {
            screenSaveEntity.setEndAdate(endStartData.getText().toString());
        }
        if (!NullUtils.isNull(endStartTime.getText().toString())) {
            screenSaveEntity.setEndAtime(endStartTime.getText().toString());
        }
        if (!NullUtils.isNull(endEndStart.getText().toString())) {
            screenSaveEntity.setEndBdate(endEndStart.getText().toString());
        }
        if (!NullUtils.isNull(endEndEnd.getText().toString())) {
            screenSaveEntity.setEndBtime(endEndEnd.getText().toString());
        }
        LocalDataSource.setScreenList(screenSaveEntity);
        Intent intent=new Intent();
        intent.putExtra("SCREEN_SAVE",screenSaveEntity);
        setResult(RESULT_OK,intent);
        finish();
    }

}
