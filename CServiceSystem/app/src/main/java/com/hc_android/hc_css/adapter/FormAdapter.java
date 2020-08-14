package com.hc_android.hc_css.adapter;

import android.text.style.ImageSpan;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.entity.FormEntity;
import com.hc_android.hc_css.utils.MoonEmoji.MoonUtils;
import com.hc_android.hc_css.wight.span.selectText.SelectableTextView;

import java.util.List;

public class FormAdapter extends BaseQuickAdapter<FormEntity, BaseViewHolder> {
    public FormAdapter(@Nullable List<FormEntity> data) {
        super(R.layout.form_adapter_item, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, FormEntity item) {

        helper.setText(R.id.form_title,item.getName());
        SelectableTextView view = helper.getView(R.id.form_title);
        SelectableTextView selectableTextView=helper.getView(R.id.form_text);
        MoonUtils.identifyFaceExpressionAndATags(mContext, selectableTextView, item.getValue(), ImageSpan.ALIGN_BASELINE);
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                view.setInitDate(null,false);//设置是否显示撤回菜单
                return true;
            }
        });
        selectableTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                selectableTextView.setInitDate(null,false);//设置是否显示撤回菜单
                return true;
            }
        });
    }
}
