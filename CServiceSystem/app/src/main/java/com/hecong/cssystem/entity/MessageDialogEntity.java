package com.hecong.cssystem.entity;

import java.util.List;

public class MeesagDialogEntity {


    /**
     * _suc : 1
     * list : [{"id":"5da67ca74cc93d05ca998254","customerId":"5da4241a4cc93d05ca5e57f5","serviceId":"5d8f2c3058a74e5059143765","addtime":"2019-10-16T02:12:55.379Z","state":"active","top":false,"source":"link","address":"湖南长沙","device":{"type":"Desktop","browser":"Chrome 77.0.3865.120","system":"Win10","width":"1920","height":"1080","ip":"175.9.31.189","url":{"current":"{\"url\":\"https://15438.ahc.ink/chat.html\",\"title\":\"在线咨询\"}","entrance":"{\"url\":\"https://15438.ahc.ink/chat.html\",\"title\":\"在线咨询\"}"}},"lastMsg":{"time":"2019-10-16T06:46:35.653Z","contents":"213","type":"text","sendType":"customer"},"msgNumber":{"response":0,"customer":13,"service":0,"total":13},"tag":[],"unreadNum":13,"assignBeBusy":false,"assignGrade":false,"customer":{"id":"5da4241a4cc93d05ca5e57f5","numberId":2,"tag":[],"card":{},"many":3,"addtime":"2019-10-14T07:30:34.537Z","address":{"city":"长沙","region":"湖南","country":"中国"},"black":false,"attCard":{}}}]
     * autoEnd : {"time":720,"state":true}
     * offEnd : {"time":600,"state":false}
     * evaluate : true
     * session : {"hash":"c365fcf89ae61ad3cea3e5ccb0ef6ec2","validity":"864000000"}
     * aihecong_version : 1.9.1
     */

    private int _suc;
    private AutoEndBean autoEnd;
    private OffEndBean offEnd;
    private boolean evaluate;
    private SessionBean session;
    private String aihecong_version;
    private List<ListBean> list;

    public int get_suc() {
        return _suc;
    }

    public void set_suc(int _suc) {
        this._suc = _suc;
    }

    public AutoEndBean getAutoEnd() {
        return autoEnd;
    }

    public void setAutoEnd(AutoEndBean autoEnd) {
        this.autoEnd = autoEnd;
    }

    public OffEndBean getOffEnd() {
        return offEnd;
    }

    public void setOffEnd(OffEndBean offEnd) {
        this.offEnd = offEnd;
    }

    public boolean isEvaluate() {
        return evaluate;
    }

    public void setEvaluate(boolean evaluate) {
        this.evaluate = evaluate;
    }

    public SessionBean getSession() {
        return session;
    }

    public void setSession(SessionBean session) {
        this.session = session;
    }

    public String getAihecong_version() {
        return aihecong_version;
    }

    public void setAihecong_version(String aihecong_version) {
        this.aihecong_version = aihecong_version;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class AutoEndBean {
        /**
         * time : 720
         * state : true
         */

        private int time;
        private boolean state;

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public boolean isState() {
            return state;
        }

        public void setState(boolean state) {
            this.state = state;
        }
    }

    public static class OffEndBean {
        /**
         * time : 600
         * state : false
         */

        private int time;
        private boolean state;

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public boolean isState() {
            return state;
        }

        public void setState(boolean state) {
            this.state = state;
        }
    }

    public static class SessionBean {
        /**
         * hash : c365fcf89ae61ad3cea3e5ccb0ef6ec2
         * validity : 864000000
         */

        private String hash;
        private String validity;

        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        public String getValidity() {
            return validity;
        }

        public void setValidity(String validity) {
            this.validity = validity;
        }
    }

    public static class ListBean {
        /**
         * id : 5da67ca74cc93d05ca998254
         * customerId : 5da4241a4cc93d05ca5e57f5
         * serviceId : 5d8f2c3058a74e5059143765
         * addtime : 2019-10-16T02:12:55.379Z
         * state : active
         * top : false
         * source : link
         * address : 湖南长沙
         * device : {"type":"Desktop","browser":"Chrome 77.0.3865.120","system":"Win10","width":"1920","height":"1080","ip":"175.9.31.189","url":{"current":"{\"url\":\"https://15438.ahc.ink/chat.html\",\"title\":\"在线咨询\"}","entrance":"{\"url\":\"https://15438.ahc.ink/chat.html\",\"title\":\"在线咨询\"}"}}
         * lastMsg : {"time":"2019-10-16T06:46:35.653Z","contents":"213","type":"text","sendType":"customer"}
         * msgNumber : {"response":0,"customer":13,"service":0,"total":13}
         * tag : []
         * unreadNum : 13
         * assignBeBusy : false
         * assignGrade : false
         * customer : {"id":"5da4241a4cc93d05ca5e57f5","numberId":2,"tag":[],"card":{},"many":3,"addtime":"2019-10-14T07:30:34.537Z","address":{"city":"长沙","region":"湖南","country":"中国"},"black":false,"attCard":{}}
         */

        private String id;
        private String customerId;
        private String serviceId;
        private String addtime;
        private String state;
        private boolean top;
        private String source;
        private String address;
        private DeviceBean device;
        private LastMsgBean lastMsg;
        private MsgNumberBean msgNumber;
        private int unreadNum;
        private boolean assignBeBusy;
        private boolean assignGrade;
        private CustomerBean customer;
        private List<?> tag;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
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

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public boolean isTop() {
            return top;
        }

        public void setTop(boolean top) {
            this.top = top;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public DeviceBean getDevice() {
            return device;
        }

        public void setDevice(DeviceBean device) {
            this.device = device;
        }

        public LastMsgBean getLastMsg() {
            return lastMsg;
        }

        public void setLastMsg(LastMsgBean lastMsg) {
            this.lastMsg = lastMsg;
        }

        public MsgNumberBean getMsgNumber() {
            return msgNumber;
        }

        public void setMsgNumber(MsgNumberBean msgNumber) {
            this.msgNumber = msgNumber;
        }

        public int getUnreadNum() {
            return unreadNum;
        }

        public void setUnreadNum(int unreadNum) {
            this.unreadNum = unreadNum;
        }

        public boolean isAssignBeBusy() {
            return assignBeBusy;
        }

        public void setAssignBeBusy(boolean assignBeBusy) {
            this.assignBeBusy = assignBeBusy;
        }

        public boolean isAssignGrade() {
            return assignGrade;
        }

        public void setAssignGrade(boolean assignGrade) {
            this.assignGrade = assignGrade;
        }

        public CustomerBean getCustomer() {
            return customer;
        }

        public void setCustomer(CustomerBean customer) {
            this.customer = customer;
        }

        public List<?> getTag() {
            return tag;
        }

        public void setTag(List<?> tag) {
            this.tag = tag;
        }

        public static class DeviceBean {
            /**
             * type : Desktop
             * browser : Chrome 77.0.3865.120
             * system : Win10
             * width : 1920
             * height : 1080
             * ip : 175.9.31.189
             * url : {"current":"{\"url\":\"https://15438.ahc.ink/chat.html\",\"title\":\"在线咨询\"}","entrance":"{\"url\":\"https://15438.ahc.ink/chat.html\",\"title\":\"在线咨询\"}"}
             */

            private String type;
            private String browser;
            private String system;
            private String width;
            private String height;
            private String ip;
            private UrlBean url;

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getBrowser() {
                return browser;
            }

            public void setBrowser(String browser) {
                this.browser = browser;
            }

            public String getSystem() {
                return system;
            }

            public void setSystem(String system) {
                this.system = system;
            }

            public String getWidth() {
                return width;
            }

            public void setWidth(String width) {
                this.width = width;
            }

            public String getHeight() {
                return height;
            }

            public void setHeight(String height) {
                this.height = height;
            }

            public String getIp() {
                return ip;
            }

            public void setIp(String ip) {
                this.ip = ip;
            }

            public UrlBean getUrl() {
                return url;
            }

            public void setUrl(UrlBean url) {
                this.url = url;
            }

            public static class UrlBean {
                /**
                 * current : {"url":"https://15438.ahc.ink/chat.html","title":"在线咨询"}
                 * entrance : {"url":"https://15438.ahc.ink/chat.html","title":"在线咨询"}
                 */

                private String current;
                private String entrance;

                public String getCurrent() {
                    return current;
                }

                public void setCurrent(String current) {
                    this.current = current;
                }

                public String getEntrance() {
                    return entrance;
                }

                public void setEntrance(String entrance) {
                    this.entrance = entrance;
                }
            }
        }

        public static class LastMsgBean {
            /**
             * time : 2019-10-16T06:46:35.653Z
             * contents : 213
             * type : text
             * sendType : customer
             */

            private String time;
            private String contents;
            private String type;
            private String sendType;

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
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
        }

        public static class MsgNumberBean {
            /**
             * response : 0
             * customer : 13
             * service : 0
             * total : 13
             */

            private int response;
            private int customer;
            private int service;
            private int total;

            public int getResponse() {
                return response;
            }

            public void setResponse(int response) {
                this.response = response;
            }

            public int getCustomer() {
                return customer;
            }

            public void setCustomer(int customer) {
                this.customer = customer;
            }

            public int getService() {
                return service;
            }

            public void setService(int service) {
                this.service = service;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }
        }

        public static class CustomerBean {
            /**
             * id : 5da4241a4cc93d05ca5e57f5
             * numberId : 2
             * tag : []
             * card : {}
             * many : 3
             * addtime : 2019-10-14T07:30:34.537Z
             * address : {"city":"长沙","region":"湖南","country":"中国"}
             * black : false
             * attCard : {}
             */

            private String id;
            private int numberId;
            private CardBean card;
            private int many;
            private String addtime;
            private AddressBean address;
            private boolean black;
            private AttCardBean attCard;
            private List<?> tag;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public int getNumberId() {
                return numberId;
            }

            public void setNumberId(int numberId) {
                this.numberId = numberId;
            }

            public CardBean getCard() {
                return card;
            }

            public void setCard(CardBean card) {
                this.card = card;
            }

            public int getMany() {
                return many;
            }

            public void setMany(int many) {
                this.many = many;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public AddressBean getAddress() {
                return address;
            }

            public void setAddress(AddressBean address) {
                this.address = address;
            }

            public boolean isBlack() {
                return black;
            }

            public void setBlack(boolean black) {
                this.black = black;
            }

            public AttCardBean getAttCard() {
                return attCard;
            }

            public void setAttCard(AttCardBean attCard) {
                this.attCard = attCard;
            }

            public List<?> getTag() {
                return tag;
            }

            public void setTag(List<?> tag) {
                this.tag = tag;
            }

            public static class CardBean {
            }

            public static class AddressBean {
            }

            public static class AttCardBean {
            }
        }
    }
}
