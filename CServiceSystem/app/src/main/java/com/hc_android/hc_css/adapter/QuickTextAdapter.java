package com.hc_android.hc_css.adapter;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.entity.QuickGroupEntity;
import com.hc_android.hc_css.utils.android.app.DataProce;

import java.util.List;

public class QuickTextAdapter extends BaseQuickAdapter<QuickGroupEntity.DataBean.GroupingBean, BaseViewHolder> {
    public QuickTextAdapter(@Nullable List<QuickGroupEntity.DataBean.GroupingBean> data) {
        super(R.layout.quick_reply_text,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, QuickGroupEntity.DataBean.GroupingBean item) {

        TextView textView=helper.getView(R.id.text_quick);
        if (item.isSelected()){
            textView.setTextColor(mContext.getResources().getColor(R.color.black));
        }else {
            textView.setTextColor(mContext.getResources().getColor(R.color.break_tv));
        }
        helper.setText(R.id.text_quick, DataProce.getItemType(item.getName()));
        helper.addOnClickListener(R.id.text_quick);
    }
}
