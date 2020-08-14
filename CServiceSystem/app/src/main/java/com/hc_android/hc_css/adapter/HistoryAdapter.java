package com.hc_android.hc_css.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.entity.MessageDialogEntity;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.DateUtils;
import com.hc_android.hc_css.utils.NullUtils;
import com.hc_android.hc_css.utils.android.app.DataProce;

import java.util.List;

public class HistoryAdapter extends BaseQuickAdapter<MessageDialogEntity.DataBean.ListBean, BaseViewHolder> {
    public HistoryAdapter(@Nullable List<MessageDialogEntity.DataBean.ListBean> data) {
        super(R.layout.history_adapter,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MessageDialogEntity.DataBean.ListBean item) {
        item.setItemtype(Constant.HAVERECEIVED);//设置和对话列表类型一样
        if (!NullUtils.isNull(item.getEndtime())){
        item.setCustomerOffTime(item.getEndtime());
        }else if (!NullUtils.isNull(item.getAddtime())){
        item.setCustomerOffTime(item.getAddtime());
        }else {
        item.setCustomerOffTime(DateUtils.getStringTime());
        }

        DataProce.setImageView(item,helper);
        DataProce.setTitle(item,helper);
        DataProce.setContent(item,helper,mContext);
        DataProce.setUpdateTime(item,helper);
        DataProce.setTagList(item,helper,mContext);
        helper.addOnClickListener(R.id.history_click_lin);

    }
}
