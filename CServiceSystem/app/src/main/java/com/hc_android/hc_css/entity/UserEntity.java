package com.hc_android.hc_css.entity;

import java.io.Serializable;

/**
 * 全局用户信息保存
 */
public class UserEntity implements Serializable {


    private   String hash;
    private   MessageDialogEntity.DataBean.AutoEndBean autoEnd; //自动结束
    private   MessageDialogEntity.DataBean.OffEndBean offEnd; //离线自动结束
    private   LoginEntity.DataBean.InfoBean userbean;
    private  String socketId;

    public LoginEntity.DataBean.InfoBean getUserbean() {
        return userbean;
    }
    public void setUserbean(LoginEntity.DataBean.InfoBean userbean) {
        this.userbean = userbean;
    }
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

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getSocketId() {
        return socketId;
    }

    public void setSocketId(String socketId) {
        this.socketId = socketId;
    }
}
