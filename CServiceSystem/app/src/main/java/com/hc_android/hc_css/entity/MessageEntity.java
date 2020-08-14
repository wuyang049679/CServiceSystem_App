package com.hc_android.hc_css.entity;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;

public class MessageEntity implements Serializable {

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
    private String currentUrl;
    private MessageBean message;
    private String hash;
    private String roomID;
    private Object item;
    private long receptionTime;//被接待时间
    private String autoMsgType;//判断是否是自动消息推送，end为自动结束
    private String msgId;
    private VisitorEntity.DataBean.ListBean realtime;
    private String contents;
    private String vcId;
    private String realtimeId;//主动邀请对话Id
    private int configId;
    private String visitorId;
    private String id;
    private String mode;
    private int netWorkType;//网络类型
    private boolean isConnected;//网络是否连接


    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public boolean isConnected() {
        return isConnected;
    }

    public void setConnected(boolean connected) {
        isConnected = connected;
    }

    public int getNetWorkType() {
        return netWorkType;
    }

    public void setNetWorkType(int netWorkType) {
        this.netWorkType = netWorkType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }

    public int getConfigId() {
        return configId;
    }

    public void setConfigId(int configId) {
        this.configId = configId;
    }

    public String getRealtimeId() {
        return realtimeId;
    }

    public void setRealtimeId(String realtimeId) {
        this.realtimeId = realtimeId;
    }

    public String getVcId() {
        return vcId;
    }

    public void setVcId(String vcId) {
        this.vcId = vcId;
    }

    public VisitorEntity.DataBean.ListBean getRealtime() {
        return realtime;
    }

    public void setRealtime(VisitorEntity.DataBean.ListBean realtime) {
        this.realtime = realtime;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public String getAutoMsgType() {
        return autoMsgType;
    }


    public String getCurrentUrl() {
        return currentUrl;
    }

    public void setCurrentUrl(String currentUrl) {
        this.currentUrl = currentUrl;
    }

    public void setAutoMsgType(String autoMsgType) {
        this.autoMsgType = autoMsgType;
    }

    public long getReceptionTime() {
        return receptionTime;
    }

    public void setReceptionTime(long receptionTime) {
        this.receptionTime = receptionTime;
    }

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
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
    public static class MessageBean implements Serializable , MultiItemEntity {
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
        private String oneway;
        private boolean system;
        private int itemType;
        private MessageDialogEntity.DataBean.ListBean listBean;
        private String sendState;
        private boolean undo;


        public boolean isUndo() {
            return undo;
        }

        public void setUndo(boolean undo) {
            this.undo = undo;
        }

        @Override
        public int getItemType() {
            return itemType;
        }
        public boolean isSystem() {
            return system;
        }

        public String getSendState() {
            return sendState;
        }

        public void setSendState(String sendState) {
            this.sendState = sendState;
        }

        public void setSystem(boolean system) {
            this.system = system;
        }


        public void setItemType(int itemType) {
            this.itemType = itemType;
        }

        public MessageDialogEntity.DataBean.ListBean getListBean() {
            return listBean;
        }

        public void setListBean(MessageDialogEntity.DataBean.ListBean listBean) {
            this.listBean = listBean;
        }

        public String getOneway() {
            return oneway;
        }

        public void setOneway(String oneway) {
            this.oneway = oneway;
        }

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
