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
    private String dialogId;
    private String customerId;
    private MessageBean message;
    private String hash;
    private String roomID;
    private MessageDialogEntity.DataBean.ListBean item;


    public MessageDialogEntity.DataBean.ListBean getItem() {
        return item;
    }

    public void setItem(MessageDialogEntity.DataBean.ListBean item) {
        this.item = item;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getDialogId() {
        return dialogId;
    }

    public void setDialogId(String dialogId) {
        this.dialogId = dialogId;
    }

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

    public MessageBean getMessage() {
        return message;
    }

    public void setMessage(MessageBean message) {
        this.message = message;
    }

    //所有数据类型模型


    //新消息类型数据
    public  class MessageBean {
            /**
             * id : 5db7acc54bdcaa34003f809d
             * key : 5db7acc54bdcaa34003f809d
             * time : 1572318405793
             * contents : 1010
             * type : text
             * sendType : customer
             * state : save
             * serviceId : 5d8f2c3058a74e5059143765
             */

            private String id;
            private String key;
            private long time;
            private String contents;
            private String type;
            private String sendType;
            private String state;
            private String serviceId;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public String getContents() {
                return contents;
            }

            public void setContents(String contents) {
                this.contents = contents;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getSendType() {
                return sendType;
            }

            public void setSendType(String sendType) {
                this.sendType = sendType;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getServiceId() {
                return serviceId;
            }

            public void setServiceId(String serviceId) {
                this.serviceId = serviceId;
            }
        }

}
