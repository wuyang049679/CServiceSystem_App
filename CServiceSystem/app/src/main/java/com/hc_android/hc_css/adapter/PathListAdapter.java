package com.hc_android.hc_css.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.entity.CustomPathEntity;
import com.hc_android.hc_css.utils.DateUtils;
import com.hc_android.hc_css.utils.NullUtils;

import java.util.Date;
import java.util.List;

public class PathListAdapter extends BaseQuickAdapter<CustomPathEntity.DataBean.ItemBean.RoutesBean, BaseViewHolder> {
    public PathListAdapter(@Nullable List<CustomPathEntity.DataBean.ItemBean.RoutesBean> data) {
        super(R.layout.path_list_adapter,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, CustomPathEntity.DataBean.ItemBean.RoutesBean item) {

        if (item.getAddtime()!=0) {
            Date date=new Date(item.getAddtime());
            helper.setText(R.id.tv_state, DateUtils.formationDate_V(date));
        }
        helper.setText(R.id.path_name,item.getTitle());
        if (item.getResTime()!=0) {
            helper.setText(R.id.path_time, DateUtils.formatTime(item.getResTime()));
        }
        helper.getView(R.id.path_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!NullUtils.isNull(item.getUrl())) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri targetUrl = Uri.parse(item.getUrl());
                    intent.setData(targetUrl);
                    mContext.startActivity(intent);
                }
            }
        });
    }
}
