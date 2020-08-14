package com.hc_android.hc_css.entity;

import com.chad.library.adapter.base.entity.AbstractExpandableItem;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.hc_android.hc_css.adapter.ExpandAdapter;

public class ExpandEntity extends AbstractExpandableItem<QuickEntity.DataBean.ListBean> implements MultiItemEntity {

    private String title;
    private String count;

    public ExpandEntity(String title, String count) {
        this.title = title;
        this.count = count;
    }

    public ExpandEntity(String title) {
        this.title = title;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public int getItemType() {
        return ExpandAdapter.TYPE_PARENT;
    }
}
