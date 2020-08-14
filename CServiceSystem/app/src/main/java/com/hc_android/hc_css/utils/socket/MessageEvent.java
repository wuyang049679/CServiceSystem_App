package com.hc_android.hc_css.utils.socket;

/**
 * Created by fengli on 2018/2/8.
 */

public class MessageEvent {

    private MessageEventType type;
    private Object msg;

    public MessageEvent(MessageEventType type, Object msg) {
        this.type = type;
        this.msg = msg;
    }

    public MessageEventType getType() {
        return type;
    }

    public Object getMsg() {
        return msg;
    }


}
