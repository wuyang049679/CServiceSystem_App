package com.hc_android.hc_css.entity;

public class SocketEntity<T> {

    /**
     * _suc : 1
     */

    private String act;
    private T message;


    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }
}
