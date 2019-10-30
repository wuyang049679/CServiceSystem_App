package com.hecong.cssystem.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hecong.cssystem.R;
import com.hecong.cssystem.entity.TeamEntity;

import java.util.List;

public class PopWindowListAdapter extends BaseQuickAdapter<TeamEntity.DataBean.ListBean, BaseViewHolder> {

    public PopWindowListAdapter(@Nullable List<TeamEntity.DataBean.ListBean> data) {
        super(R.layout.team_list_popwindow,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, TeamEntity.DataBean.ListBean item) {
        if (item.getNickname()!=null) {
            helper.setText(R.id.team_name, item.getNickname());
        }else {
            helper.setText(R.id.team_name,item.getName());
        }
        helper.addOnClickListener(R.id.team_lin);
    }
}
