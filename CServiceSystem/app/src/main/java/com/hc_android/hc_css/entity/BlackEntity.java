package com.hc_android.hc_css.entity;

public class BlackEntity {


    /**
     * type : customer
     * customerId : 顾客ID
     * serviceId : 操作人
     */

    private String type;
    private String customerId;
    private String serviceId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
