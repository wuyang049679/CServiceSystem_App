package com.hc_android.hc_css.adapter;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.entity.TextListEntity;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.DateUtils;

import java.util.List;

public class CardListAdapter extends BaseMultiItemQuickAdapter<TextListEntity, BaseViewHolder> {
    public CardListAdapter(@Nullable List<TextListEntity> data) {
        super(data);
        addItemType(Constant.CARD_SETING, R.layout.card_adapter_item);
        addItemType(Constant.CARD_BROSWER, R.layout.card_adapter_item_broswer);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, TextListEntity item) {

        helper.setText(R.id.card_title,item.getTitle());
        if (item.getText()!=null&&item.getTextJson()==null) {
            if (item.getTitle().contains("关注时间")) {
                try {
                    long timeText = Long.parseLong(item.getText());
                    helper.setText(R.id.card_tv, DateUtils.getTime(timeText));
                } catch (Exception e) {
                }
            } else{
                helper.setText(R.id.card_tv, item.getText());
             }

        }
        if (item.getTextJson()!=null){
            TextView textView = helper.getView(R.id.card_tv);
            textView.setTextColor(mContext.getResources().getColor(R.color.break_tv));
            if (item.getTextJson().getTitle()!=null)
            textView.setText(item.getTextJson().getTitle());
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri targetUrl = Uri.parse(item.getTextJson().getUrl());
                        intent.setData(targetUrl);
                        mContext.startActivity(intent);
                    } catch (Exception e) {
                    }
                }
            });
        }
        helper.addOnClickListener(R.id.lin_card);
    }
}
