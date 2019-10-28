package com.hecong.cssystem.entity;

/**
 * 全局用户信息保存
 */
public class UserEntity {

    private   String hash;
    private   String serviceId;


    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }
}
