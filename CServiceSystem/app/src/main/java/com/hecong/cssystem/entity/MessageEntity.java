package com.hecong.cssystem.entity;

public class MessageEntity {

    /**
     * act : loginSuc
     * socketId : qIn8MswlKpjYWxmnMSW8
     * state : on
     */

    private String act;
    private String socketId;
    private String state;
    private String serviceId;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String getSocketId() {
        return socketId;
    }

    public void setSocketId(String socketId) {
        this.socketId = socketId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
