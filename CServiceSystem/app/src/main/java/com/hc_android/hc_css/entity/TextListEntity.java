package com.hc_android.hc_css.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

public class TextListEntity implements MultiItemEntity , Serializable {
    private boolean isChecked;

    private String text;
    private String title;
    private TextJson textJson;
    private int itemType;
    public TextListEntity() {
    }


    public void setItemType(int itemType) {
        this.itemType = itemType;
    }

    public TextListEntity(String title) {
        this.title = title;
    }

    public TextListEntity(String text, String title) {
        this.text = text;
        this.title = title;
    }

    public TextListEntity( String title,String text, int itemType) {
        this.text = text;
        this.title = title;
        this.itemType = itemType;
    }

    public TextListEntity(boolean isChecked, String text) {
        this.isChecked = isChecked;
        this.text = text;
    }

    public TextJson getTextJson() {
        return textJson;
    }

    public void setTextJson(TextJson textJson) {
        this.textJson = textJson;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public static class TextJson implements Serializable{

        /**
         * url : http://www.aaa.com
         * title : 201804120397
         */

        private String url;
        private String title;

        public TextJson(String url, String title) {
            this.url = url;
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
