package com.hecong.cssystem.adapter;

import android.graphics.drawable.GradientDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hecong.cssystem.R;
import com.hecong.cssystem.entity.MessageDialogEntity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

public class DialogListAdapter extends BaseQuickAdapter<MessageDialogEntity.DataBean.ListBean, BaseViewHolder> {

    public DialogListAdapter(@Nullable List<MessageDialogEntity.DataBean.ListBean> data) {
        super( R.layout.dialog_list_adapter,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MessageDialogEntity.DataBean.ListBean item) {
        TagFlowLayout flowLayout = helper.getView(R.id.flowLayout);
        List<String> list=new ArrayList<>();
        for (int i = 0; i <1; i++) {
            list.add("Android");
            list.add("Java");
            list.add("python");
        }
        int [] ints=new int[]{R.color.colorAccent,R.color.theme_app,R.color.blue_btn,R.color.black_cc,R.color.colorAccent,R.color.theme_app,R.color.blue_btn,R.color.black_cc,R.color.colorAccent,R.color.theme_app,R.color.blue_btn,R.color.black_cc,R.color.colorAccent,R.color.theme_app,R.color.blue_btn,R.color.black_cc};
        flowLayout.setAdapter(new TagAdapter<String>(list) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {


                TextView tv = new TextView(mContext);
            tv.setPadding(20, 10, 20, 10);
            tv.setText(list.get(position));
            tv.setTextColor(mContext.getResources().getColor(R.color.white));
            tv.setMaxEms(20);
            tv.setSingleLine();
                GradientDrawable drawable=new GradientDrawable();
                drawable.setShape(GradientDrawable.RECTANGLE);
                drawable.setGradientType(GradientDrawable.RECTANGLE);
                drawable.setCornerRadius(6);
                drawable.setColor(mContext.getResources().getColor(ints[position]));
                tv.setBackground(drawable);
                return tv;
            }

        });

    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return super.onCreateViewHolder(parent, viewType);
    }
}

