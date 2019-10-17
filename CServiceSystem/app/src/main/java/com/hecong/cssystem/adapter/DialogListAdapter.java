package com.hecong.cssystem.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.daimajia.swipe.SwipeLayout;
import com.hecong.cssystem.R;
import com.hecong.cssystem.entity.MessageDialogEntity;

import java.util.List;

public class DialogListAdapter extends BaseQuickAdapter<MessageDialogEntity.DataBean.ListBean, BaseViewHolder> {

    public DialogListAdapter(@Nullable List<MessageDialogEntity.DataBean.ListBean> data) {
        super( R.layout.dialog_list_adapter,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MessageDialogEntity.DataBean.ListBean item) {

        SwipeLayout swipeLayout = helper.getView(R.id.swipeLayout);
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {

            }

            @Override
            public void onOpen(SwipeLayout layout) {

            }

            @Override
            public void onStartClose(SwipeLayout layout) {

            }

            @Override
            public void onClose(SwipeLayout layout) {

            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {

            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {

            }
        });
    }
}
