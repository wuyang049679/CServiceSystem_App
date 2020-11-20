package com.hc_android.hc_css.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.entity.MessageDialogEntity;
import com.hc_android.hc_css.entity.QuickEntity;
import com.hc_android.hc_css.utils.NullUtils;
import com.hc_android.hc_css.utils.android.app.AppConfig;
import com.hc_android.hc_css.utils.android.app.DataProce;

import java.util.List;

public class QuickListAdapter extends BaseQuickAdapter<QuickEntity.DataBean.ListBean, BaseViewHolder> {
    public QuickListAdapter(@Nullable List<QuickEntity.DataBean.ListBean> data) {
        super(R.layout.quick_list_adapter,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, QuickEntity.DataBean.ListBean item) {
        MessageDialogEntity.DataBean.ListBean listBean=new MessageDialogEntity.DataBean.ListBean();
        MessageDialogEntity.DataBean.ListBean.LastMsgBean lastMsgBean=new MessageDialogEntity.DataBean.ListBean.LastMsgBean();
        lastMsgBean.setContents(item.getContent());
        lastMsgBean.setType(item.getType());
        listBean.setLastMsg(lastMsgBean);
        String name=DataProce.getContent(listBean);
        if (!NullUtils.isNull(item.getName()))name=item.getName();
        if (name != null)helper.setText(R.id.quick_list_text, name);
        if (item.getType() != null)helper.setText(R.id.quick_list_type,DataProce.getItemType(item.getType()));
        if (AppConfig.getConfigEntity().isDisplayCounts()) {
            TextView view = helper.getView(R.id.quick_list_count);
            view.setVisibility(View.VISIBLE);
            view.setText(item.getUsage()+"");
        }
        helper.addOnClickListener(R.id.quick_list_lin);
        //快捷回复文本类型不显示标签
        if (item.getType().equals("text")){
            helper.getView(R.id.quick_list_type).setVisibility(View.GONE);
        }else {
            helper.getView(R.id.quick_list_type).setVisibility(View.VISIBLE);
        }
    }
}
