package com.hecong.cssystem.entity;



import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.io.Serializable;
import java.util.List;

public class MessageDialogEntity implements Serializable{


    /**
     * code : 200
     * msg : ok
     * data : {"_suc":1,"list":[{"id":"5da56df82a91b28c743199b2","customerId":"5da56df82a91b28c743199b1","serviceId":"5da52c30d26268107e3140d1","addtime":"2019-10-15T06:58:00.075Z","state":"active","top":false,"source":"web","address":"四川成都","device":{"type":"Desktop","browser":"Chrome 77.0.3865.120","system":"Win10","width":"1920","height":"1080","ip":"171.15.228.85","url":{"current":"{\"url\":\"http://192.168.0.105:8888/phpwww/metinfo/?entId=81\",\"title\":\"网站关键词-网站名称\"}","entrance":"{\"url\":\"http://192.168.0.105:8888/phpwww/metinfo/?entId=81\",\"title\":\"网站关键词-网站名称\"}"}},"lastMsg":{"time":"2019-10-15T07:34:46.278Z","contents":"555555","type":"text","sendType":"customer"},"msgNumber":{"response":0,"customer":10,"service":0,"total":10},"tag":[],"customerOffTime":"2019-10-15T10:23:26.543Z","unreadNum":10,"assignBeBusy":false,"assignGrade":false,"customer":{"id":"5da56df82a91b28c743199b1","numberId":3,"uniqueId":"1571122689778","tag":[],"card":{},"many":1,"addtime":"2019-10-15T06:58:00.053Z","address":{"city":"成都","region":"四川","country":"中国"},"black":false,"attCard":{"ID":"1571122689778"}}},{"id":"5da562f6d26268107e31413a","customerId":"5da562f6d26268107e314139","serviceId":"5da52c30d26268107e3140d1","addtime":"2019-10-15T06:11:02.132Z","state":"active","top":false,"source":"web","address":"四川成都","device":{"type":"Desktop","browser":"Chrome 77.0.3865.120","system":"Win10","width":"1920","height":"1080","ip":"116.13.146.150","url":{"current":"{\"url\":\"http://192.168.0.105:8888/phpwww/metinfo/?entId=81\",\"title\":\"网站关键词-网站名称\"}","entrance":"{\"url\":\"http://192.168.0.105:8888/phpwww/metinfo/?entId=81\",\"title\":\"网站关键词-网站名称\"}"}},"lastMsg":{"time":"2019-10-15T06:23:51.346Z","contents":"11111111111112222222222222222222","type":"text","sendType":"customer"},"msgNumber":{"response":0,"customer":2,"service":0,"total":2},"tag":[],"customerOffTime":"2019-10-15T06:57:53.368Z","unreadNum":2,"assignBeBusy":false,"assignGrade":false,"customer":{"id":"5da562f6d26268107e314139","numberId":2,"uniqueId":"1571119856581","tag":[],"card":{},"many":1,"addtime":"2019-10-15T06:11:02.110Z","address":{"city":"成都","region":"四川","country":"中国"},"black":false,"attCard":{"ID":"1571119856581"}}},{"id":"5da56120d26268107e31412c","customerId":"5da56120d26268107e31412b","serviceId":"5da52c30d26268107e3140d1","addtime":"2019-10-15T06:03:12.080Z","state":"active","top":false,"source":"web","address":"四川成都","device":{"type":"Desktop","browser":"Chrome 77.0.3865.120","system":"Win10","width":"1920","height":"1080","ip":"118.114.120.66","url":{"current":"{\"url\":\"http://192.168.0.105:8888/phpwww/metinfo/?entId=81\",\"title\":\"网站关键词-网站名称\"}","entrance":"{\"url\":\"http://192.168.0.105:8888/phpwww/metinfo/?entId=81\",\"title\":\"网站关键词-网站名称\"}","source":"DirectEntry"}},"lastMsg":{"time":"2019-10-15T06:08:10.655Z","contents":"1111","type":"text","sendType":"customer"},"msgNumber":{"response":0,"customer":7,"service":0,"total":7},"tag":[],"customerOffTime":"2019-10-15T06:10:40.322Z","unreadNum":7,"assignBeBusy":false,"assignGrade":false,"customer":{"id":"5da56120d26268107e31412b","numberId":1,"uniqueId":"1571119400015","tag":[],"card":{},"many":1,"addtime":"2019-10-15T06:03:12.057Z","address":{"city":"成都","region":"四川","country":"中国"},"black":false,"attCard":{"ID":"1571119400015"}}}],"autoEnd":{"time":720,"state":true},"offEnd":{"time":600,"state":false},"evaluate":true,"session":{"hash":"d28a3d2ea15f4433e7af654618dd3078","validity":"864000000"}}
     * aihecong_version : 1.9.1
     * region : Shanghai
     */

    private int code;
    private String msg;
    private DataBean data;
    private String aihecong_version;
    private String region;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getAihecong_version() {
        return aihecong_version;
    }

    public void setAihecong_version(String aihecong_version) {
        this.aihecong_version = aihecong_version;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public static class DataBean implements Serializable{
        /**
         * _suc : 1
         * list : [{"id":"5da56df82a91b28c743199b2","customerId":"5da56df82a91b28c743199b1","serviceId":"5da52c30d26268107e3140d1","addtime":"2019-10-15T06:58:00.075Z","state":"active","top":false,"source":"web","address":"四川成都","device":{"type":"Desktop","browser":"Chrome 77.0.3865.120","system":"Win10","width":"1920","height":"1080","ip":"171.15.228.85","url":{"current":"{\"url\":\"http://192.168.0.105:8888/phpwww/metinfo/?entId=81\",\"title\":\"网站关键词-网站名称\"}","entrance":"{\"url\":\"http://192.168.0.105:8888/phpwww/metinfo/?entId=81\",\"title\":\"网站关键词-网站名称\"}"}},"lastMsg":{"time":"2019-10-15T07:34:46.278Z","contents":"555555","type":"text","sendType":"customer"},"msgNumber":{"response":0,"customer":10,"service":0,"total":10},"tag":[],"customerOffTime":"2019-10-15T10:23:26.543Z","unreadNum":10,"assignBeBusy":false,"assignGrade":false,"customer":{"id":"5da56df82a91b28c743199b1","numberId":3,"uniqueId":"1571122689778","tag":[],"card":{},"many":1,"addtime":"2019-10-15T06:58:00.053Z","address":{"city":"成都","region":"四川","country":"中国"},"black":false,"attCard":{"ID":"1571122689778"}}},{"id":"5da562f6d26268107e31413a","customerId":"5da562f6d26268107e314139","serviceId":"5da52c30d26268107e3140d1","addtime":"2019-10-15T06:11:02.132Z","state":"active","top":false,"source":"web","address":"四川成都","device":{"type":"Desktop","browser":"Chrome 77.0.3865.120","system":"Win10","width":"1920","height":"1080","ip":"116.13.146.150","url":{"current":"{\"url\":\"http://192.168.0.105:8888/phpwww/metinfo/?entId=81\",\"title\":\"网站关键词-网站名称\"}","entrance":"{\"url\":\"http://192.168.0.105:8888/phpwww/metinfo/?entId=81\",\"title\":\"网站关键词-网站名称\"}"}},"lastMsg":{"time":"2019-10-15T06:23:51.346Z","contents":"11111111111112222222222222222222","type":"text","sendType":"customer"},"msgNumber":{"response":0,"customer":2,"service":0,"total":2},"tag":[],"customerOffTime":"2019-10-15T06:57:53.368Z","unreadNum":2,"assignBeBusy":false,"assignGrade":false,"customer":{"id":"5da562f6d26268107e314139","numberId":2,"uniqueId":"1571119856581","tag":[],"card":{},"many":1,"addtime":"2019-10-15T06:11:02.110Z","address":{"city":"成都","region":"四川","country":"中国"},"black":false,"attCard":{"ID":"1571119856581"}}},{"id":"5da56120d26268107e31412c","customerId":"5da56120d26268107e31412b","serviceId":"5da52c30d26268107e3140d1","addtime":"2019-10-15T06:03:12.080Z","state":"active","top":false,"source":"web","address":"四川成都","device":{"type":"Desktop","browser":"Chrome 77.0.3865.120","system":"Win10","width":"1920","height":"1080","ip":"118.114.120.66","url":{"current":"{\"url\":\"http://192.168.0.105:8888/phpwww/metinfo/?entId=81\",\"title\":\"网站关键词-网站名称\"}","entrance":"{\"url\":\"http://192.168.0.105:8888/phpwww/metinfo/?entId=81\",\"title\":\"网站关键词-网站名称\"}","source":"DirectEntry"}},"lastMsg":{"time":"2019-10-15T06:08:10.655Z","contents":"1111","type":"text","sendType":"customer"},"msgNumber":{"response":0,"customer":7,"service":0,"total":7},"tag":[],"customerOffTime":"2019-10-15T06:10:40.322Z","unreadNum":7,"assignBeBusy":false,"assignGrade":false,"customer":{"id":"5da56120d26268107e31412b","numberId":1,"uniqueId":"1571119400015","tag":[],"card":{},"many":1,"addtime":"2019-10-15T06:03:12.057Z","address":{"city":"成都","region":"四川","country":"中国"},"black":false,"attCard":{"ID":"1571119400015"}}}]
         * autoEnd : {"time":720,"state":true}
         * offEnd : {"time":600,"state":false}
         * evaluate : true
         * session : {"hash":"d28a3d2ea15f4433e7af654618dd3078","validity":"864000000"}
         */

        private int _suc;
        private AutoEndBean autoEnd;
        private OffEndBean offEnd;
        private boolean evaluate;
        private SessionBean session;
        private List<ListBean> list;
        private ListBean dialog;//新对话消息

        public ListBean getDialog() {
            return dialog;
        }

        public void setDialog(ListBean dialog) {
            this.dialog = dialog;
        }

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

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class AutoEndBean implements Serializable{
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

        public static class OffEndBean implements Serializable{
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

        public static class SessionBean implements Serializable{
            /**
             * hash : d28a3d2ea15f4433e7af654618dd3078
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

        public static class ListBean implements MultiItemEntity ,Serializable,Cloneable{
            /**
             * id : 5da56df82a91b28c743199b2
             * customerId : 5da56df82a91b28c743199b1
             * serviceId : 5da52c30d26268107e3140d1
             * addtime : 2019-10-15T06:58:00.075Z
             * state : active
             * top : false
             * source : web
             * address : 四川成都
             * device : {"type":"Desktop","browser":"Chrome 77.0.3865.120","system":"Win10","width":"1920","height":"1080","ip":"171.15.228.85","url":{"current":"{\"url\":\"http://192.168.0.105:8888/phpwww/metinfo/?entId=81\",\"title\":\"网站关键词-网站名称\"}","entrance":"{\"url\":\"http://192.168.0.105:8888/phpwww/metinfo/?entId=81\",\"title\":\"网站关键词-网站名称\"}"}}
             * lastMsg : {"time":"2019-10-15T07:34:46.278Z","contents":"555555","type":"text","sendType":"customer"}
             * msgNumber : {"response":0,"customer":10,"service":0,"total":10}
             * tag : []
             * customerOffTime : 2019-10-15T10:23:26.543Z
             * unreadNum : 10
             * assignBeBusy : false
             * assignGrade : false
             * customer : {"id":"5da56df82a91b28c743199b1","numberId":3,"uniqueId":"1571122689778","tag":[],"card":{},"many":1,"addtime":"2019-10-15T06:58:00.053Z","address":{"city":"成都","region":"四川","country":"中国"},"black":false,"attCard":{"ID":"1571122689778"}}
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
            private String customerOffTime;
            private int unreadNum;
            private boolean assignBeBusy;
            private boolean assignGrade;
            private CustomerBean customer;
            private List<String> tag;
            private int itemtype;
            private int unCount;//未接待个数
            private int read;//读取消息数量，用于点击有未读消息有更新红点
            private String receptionTime;


            @Override
            public ListBean clone() throws CloneNotSupportedException {
                return (ListBean)super.clone();
            }

            public String getReceptionTime() {
                return receptionTime;
            }

            public void setReceptionTime(String receptionTime) {
                this.receptionTime = receptionTime;
            }

            public int getRead() {
                return read;
            }

            public void setRead(int read) {
                this.read = read;
            }

            public int getUnCount() {
                return unCount;
            }

            public void setUnCount(int unCount) {
                this.unCount = unCount;
            }

            public ListBean() {
            }

            public ListBean(int itemtype) {
                this.itemtype = itemtype;
            }

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

            public String getCustomerOffTime() {
                return customerOffTime;
            }

            public void setCustomerOffTime(String customerOffTime) {
                this.customerOffTime = customerOffTime;
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

            public List<String> getTag() {
                return tag;
            }

            public void setTag(List<String> tag) {
                this.tag = tag;
            }

            public int getItemtype() {
                return itemtype;
            }

            public void setItemtype(int itemtype) {
                this.itemtype = itemtype;
            }

            @Override
            public int getItemType() {
                return itemtype;
            }

            public static class DeviceBean implements Serializable{
                /**
                 * type : Desktop
                 * browser : Chrome 77.0.3865.120
                 * system : Win10
                 * width : 1920
                 * height : 1080
                 * ip : 171.15.228.85
                 * url : {"current":"{\"url\":\"http://192.168.0.105:8888/phpwww/metinfo/?entId=81\",\"title\":\"网站关键词-网站名称\"}","entrance":"{\"url\":\"http://192.168.0.105:8888/phpwww/metinfo/?entId=81\",\"title\":\"网站关键词-网站名称\"}"}
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

                public static class UrlBean implements Serializable{
                    /**
                     * current : {"url":"http://192.168.0.105:8888/phpwww/metinfo/?entId=81","title":"网站关键词-网站名称"}
                     * entrance : {"url":"http://192.168.0.105:8888/phpwww/metinfo/?entId=81","title":"网站关键词-网站名称"}
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

            public static class LastMsgBean implements Serializable{
                /**
                 * time : 2019-10-15T07:34:46.278Z
                 * contents : 555555
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

            public static class MsgNumberBean implements Serializable{
                /**
                 * response : 0
                 * customer : 10
                 * service : 0
                 * total : 10
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

            public static class CustomerBean implements Serializable{
                /**
                 * id : 5da56df82a91b28c743199b1
                 * numberId : 3
                 * uniqueId : 1571122689778
                 * tag : []
                 * card : {}
                 * many : 1
                 * addtime : 2019-10-15T06:58:00.053Z
                 * address : {"city":"成都","region":"四川","country":"中国"}
                 * black : false
                 * attCard : {"ID":"1571122689778"}
                 */

                private String id;
                private int numberId;
                private String uniqueId;
                private CardBean card;
                private int many;
                private String addtime;
                private AddressBean address;
                private boolean black;
                private AttCardBean attCard;
                private List<?> tag;
                private String head;
                private String name;


                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getHead() {
                    return head;
                }

                public void setHead(String head) {
                    this.head = head;
                }

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

                public String getUniqueId() {
                    return uniqueId;
                }

                public void setUniqueId(String uniqueId) {
                    this.uniqueId = uniqueId;
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

                public static class CardBean implements Serializable{
                }

                public static class AddressBean implements Serializable{
                    /**
                     * city : 成都
                     * region : 四川
                     * country : 中国
                     */

                    private String city;
                    private String region;
                    private String country;

                    public String getCity() {
                        return city;
                    }

                    public void setCity(String city) {
                        this.city = city;
                    }

                    public String getRegion() {
                        return region;
                    }

                    public void setRegion(String region) {
                        this.region = region;
                    }

                    public String getCountry() {
                        return country;
                    }

                    public void setCountry(String country) {
                        this.country = country;
                    }
                }

                public static class AttCardBean implements Serializable{
                    /**
                     * ID : 1571122689778
                     */

                    private String ID;

                    public String getID() {
                        return ID;
                    }

                    public void setID(String ID) {
                        this.ID = ID;
                    }
                }
            }
        }
    }
}
