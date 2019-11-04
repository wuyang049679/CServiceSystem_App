package com.hecong.cssystem.entity;

/**
 * 全局用户信息保存
 */
public class UserEntity {

    private   String hash;
    private   String serviceId;
    private   int maxChat;
    private   boolean founding;


    public boolean isFounding() {
        return founding;
    }

    public void setFounding(boolean founding) {
        this.founding = founding;
    }

    public int getMaxChat() {
        return maxChat;
    }

    public void setMaxChat(int maxChat) {
        this.maxChat = maxChat;
    }

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
