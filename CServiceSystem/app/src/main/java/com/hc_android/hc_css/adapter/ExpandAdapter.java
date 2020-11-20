package com.hc_android.hc_css.adapter;

import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.MultipleItemRvAdapter;
import com.chad.library.adapter.base.entity.IExpandable;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.entity.ExpandEntity;
import com.hc_android.hc_css.entity.MessageDialogEntity;
import com.hc_android.hc_css.entity.QuickEntity;
import com.hc_android.hc_css.utils.NullUtils;
import com.hc_android.hc_css.utils.android.app.AppConfig;
import com.hc_android.hc_css.utils.android.app.DataProce;

import java.util.List;

public class ExpandAdapter  extends BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder> {

    public static final int TYPE_PARENT = 0;
    public static final int TYPE_CHILD = 1;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ExpandAdapter(List<MultiItemEntity> data) {
        super(data);
        addItemType(TYPE_PARENT, R.layout.expand_list_adapter);
        addItemType(TYPE_CHILD, R.layout.expand_child_list_adapter);
    }


    @Override
    protected void convert(@NonNull BaseViewHolder helper,MultiItemEntity item) {
        switch (helper.getItemViewType()){

            case TYPE_PARENT:


                ExpandEntity expandEntity= (ExpandEntity) item;
                helper.setText(R.id.ep_tv,expandEntity.getTitle()).setText(R.id.ep_count,expandEntity.getCount());
                ImageView view = helper.getView(R.id.ep_iv);
                view.setRotation(expandEntity.isExpanded() ? 90 : 0);
                helper.itemView.setOnClickListener(v -> {
                    int pos = helper.getAdapterPosition();

                    if (expandEntity.isExpanded()){

                        collapse(pos);
                    }else {
                        if (getData().size()>pos && (getData().get(pos)) instanceof ExpandEntity) {

                            IExpandable willExpandItem = (IExpandable) getData().get(pos);
                            for (int i = getData().size() - 1; i >= 0; i--) {
                                if (i == pos) {
                                    expand(getData().indexOf(willExpandItem) + getHeaderLayoutCount());
                                } else {

                                    if ((getData().get(i)) instanceof ExpandEntity && (((ExpandEntity) (getData().get(i))).isExpanded())) {
                                        collapse(i);
                                    }
                                }
                            }
                        }
                    }
                });
                break;
            case TYPE_CHILD:
                QuickEntity.DataBean.ListBean bean = (QuickEntity.DataBean.ListBean) item;
                MessageDialogEntity.DataBean.ListBean listBean=new MessageDialogEntity.DataBean.ListBean();
                MessageDialogEntity.DataBean.ListBean.LastMsgBean lastMsgBean=new MessageDialogEntity.DataBean.ListBean.LastMsgBean();
                lastMsgBean.setContents(bean.getContent());
                lastMsgBean.setType(bean.getType());
                listBean.setLastMsg(lastMsgBean);
                String name= DataProce.getContent(listBean);
                if (!NullUtils.isNull(bean.getName()))name=bean.getName();
                helper.setText(R.id.quick_list_text, name);
                helper.setText(R.id.quick_list_type,DataProce.getItemType(bean.getType()));
                if (AppConfig.getConfigEntity().isDisplayCounts()) {
                    TextView text = helper.getView(R.id.quick_list_count);
                    text.setVisibility(View.VISIBLE);
                    text.setText(bean.getUsage() + "");

                }
                helper.addOnClickListener(R.id.quick_list_lin);
                //快捷回复文本类型不显示标签
                if (bean.getType().equals("text")){
                    helper.getView(R.id.quick_list_type).setVisibility(View.GONE);
                }else {
                    helper.getView(R.id.quick_list_type).setVisibility(View.VISIBLE);
                }
                break;
        }
        helper.addOnClickListener(R.id.quick_list_lin);
    }




}
