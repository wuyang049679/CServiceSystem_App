package com.hecong.cssystem.entity;

/**
 * 全局用户信息保存
 */
public class UserEntity {


    private   String hash;
    private   String serviceId;
    private   int maxChat;//最大聊天
    private   boolean founding;//是否是管理原
    private   MessageDialogEntity.DataBean.AutoEndBean autoEnd; //自动结束
    private   MessageDialogEntity.DataBean.OffEndBean offEnd; //离线自动结束


    public MessageDialogEntity.DataBean.AutoEndBean getAutoEnd() {
        return autoEnd;
    }

    public void setAutoEnd(MessageDialogEntity.DataBean.AutoEndBean autoEnd) {
        this.autoEnd = autoEnd;
    }

    public MessageDialogEntity.DataBean.OffEndBean getOffEnd() {
        return offEnd;
    }

    public void setOffEnd(MessageDialogEntity.DataBean.OffEndBean offEnd) {
        this.offEnd = offEnd;
    }

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
