package com.hc_android.hc_css.entity;

public class EmojiEntity {
    private String name;
    private String pic;


    public EmojiEntity(String name, String pic) {
        this.name = name;
        this.pic = pic;
    }

    public EmojiEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
