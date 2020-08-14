package com.hc_android.hc_css.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.entity.MenuEntity;
import com.hc_android.hc_css.ui.activity.ChatActivity;
import com.hc_android.hc_css.wight.span.selectText.OperationItem;
import com.hc_android.hc_css.wight.span.selectText.SelectableTextView;

import java.util.List;

public class MenuAdapter extends BaseQuickAdapter<MenuEntity.ListBean, BaseViewHolder>
{
    public MenuAdapter(@Nullable List<MenuEntity.ListBean> data) {
        super( R.layout.menu_adapter_item,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MenuEntity.ListBean item) {
        SelectableTextView selectableTextView = helper.getView(R.id.menu_text);
        selectableTextView.setText(item.getContent());
        selectableTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                selectableTextView.setInitDate(null,false);//设置是否显示撤回菜单
                return true;
            }
        });
        if (item.getType()!=null&&item.getType().equals("link"))
        selectableTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri targetUrl = Uri.parse(item.getValue().getLink());
                    intent.setData(targetUrl);
                    mContext.startActivity(intent);
                }catch (Exception e){}
            }
        });
    }
}
