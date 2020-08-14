package com.hc_android.hc_css.entity;

import java.util.List;

public class FormEntity {

    /**
     * name : 下拉
     * type : select
     * option : ["1","2"]
     * cardName : 名称
     * required : true
     * value : 1
     * tips : 区输入
     */

    private String name;
    private String type;
    private String cardName;
    private boolean required;
    private String value;
    private String tips;
    private List<String> option;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public List<String> getOption() {
        return option;
    }

    public void setOption(List<String> option) {
        this.option = option;
    }
}
