package com.hc_android.hc_css.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.entity.ParamEntity;

import java.util.List;

public class ParamListAdapter extends BaseQuickAdapter<ParamEntity,BaseViewHolder> {
    public ParamListAdapter(@Nullable List<ParamEntity> data) {
        super(R.layout.text_adapter_item,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, ParamEntity item) {
        helper.setText(R.id.update_tv,item.getTitle());
        helper.addOnClickListener(R.id.text_item_lin);
        ImageView imageView = helper.getView(R.id.item_checked);
        if (item.isChecked()){
            imageView.setVisibility(View.VISIBLE);
        }else {
            imageView.setVisibility(View.GONE);
        }
    }

}
