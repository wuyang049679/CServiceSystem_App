package com.hc_android.hc_css.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.api.Address;
import com.hc_android.hc_css.entity.TeamEntity;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.UriUtils;

import java.util.List;

public class PopWindowListAdapter extends BaseQuickAdapter<TeamEntity.DataBean.ListBean, BaseViewHolder> {


    private  boolean showMsgs;

    public PopWindowListAdapter(@Nullable List<TeamEntity.DataBean.ListBean> data) {
        super(R.layout.team_list_popwindow,data);
    }
    public PopWindowListAdapter(@Nullable List<TeamEntity.DataBean.ListBean> data,boolean showMsg) {
        super(R.layout.team_list_popwindow,data);
        showMsgs = showMsg;
    }
    @Override
    protected void convert(@NonNull BaseViewHolder helper, TeamEntity.DataBean.ListBean item) {
        TextView textView = helper.getView(R.id.team_content);
        if (item.isShowNum()){
            textView.setVisibility(View.VISIBLE);
        }else {
            textView.setVisibility(View.GONE);
        }
//        if (item.getNickname()!=null) {
//            helper.setText(R.id.team_name, item.getNickname());
//        }else {
            helper.setText(R.id.team_name,item.getName());
//        }
        helper.setText(R.id.team_content,"对话数 "+item.getDialogCount());
        helper.addOnClickListener(R.id.team_lin);
        SimpleDraweeView header = helper.getView(R.id.team_list_header);
       if (item.getHead()!=null&&!item.getHead().equals("undefined")){
           header.setImageURI(UriUtils.getUri(Constant.ONLINEPIC, Address.IMG_URL+item.getHead()+"?imageView2/1/w/100/h/100"));
       }else {
           header.setImageURI(UriUtils.getUri(Constant.ONLINEPIC, Address.SYSTEM_URL+"image/defaultAvatar.jpeg"));
       }
        ImageView msg=helper.getView(R.id.state_msg);
       if (showMsgs) {
       msg.setVisibility(View.GONE);
       }
       String state=item.getRealState()!=null?item.getRealState():item.getState();//realState真实在线状态为准
       switch (state){
           case "on":
               msg.setImageResource(R.drawable.oval_green);
               break;
           case "off":
               msg.setImageResource(R.drawable.oval_red);
               break;
           case "break":
               msg.setImageResource(R.drawable.oval_grag);
               break;
       }
    }
}
