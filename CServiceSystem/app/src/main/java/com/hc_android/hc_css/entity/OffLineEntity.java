package com.hc_android.hc_css.entity;

public class OffLineEntity {

    /**
     * act : offline
     * customerId : 5daecd6a4cc93d05ca884eeb
     */

    private String act;
    private String customerId;
    private String currentUrl;


    public String getCurrentUrl() {
        return currentUrl;
    }

    public void setCurrentUrl(String currentUrl) {
        this.currentUrl = currentUrl;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
