package com.hc_android.hc_css.entity;

public class CardUpdateEntity {


    private  String id;
    private String[] tag;
    private  Object card;


    public Object getCard() {
        return card;
    }

    public void setCard(Object card) {
        this.card = card;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String[] getTag() {
        return tag;
    }

    public void setTag(String[] tag) {
        this.tag = tag;
    }




}
