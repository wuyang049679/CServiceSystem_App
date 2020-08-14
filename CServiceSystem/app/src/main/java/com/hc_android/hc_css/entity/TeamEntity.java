package com.hc_android.hc_css.entity;

import java.util.List;

public class TeamEntity {


    /**
     * code : 200
     * msg : ok
     * data : {"_suc":1,"list":[{"_id":"5d8f2c3058a74e5059143765","tel":"15111014495","name":"管理员","password":"72a95471e3bf2d3891ccc98f30e6f6bb","nickname":"管理员","serviceId":16827,"entId":15438,"lastLoginTime":"2019-10-30T01:52:05.390Z","lastLoginMode":"app","loginCount":847,"head":"Fp7VQ_WfZQV3Z-nDur5uEXtWUDGu.jpg","registrationId":"13065ffa4e6f7fdee22","socketIdMobile":"XGIBVxzXnN8g8CCpRzUs","socketId":"stOSpYG7cWn6jPWeRzty","autoreply":{"welcome":{"type":"text","content":"很高兴为您服务，有什么可以为您效劳的吗？","state":false}},"systemNotice":{"state":true},"authority":{"assist":true},"personality":{"visitorNumber":true,"quickEnd":true,"chatListTag":true,"routeShwo":false,"visitorSame":false,"chatListType":"fold","activeHide":false},"notice":{"sound":"default","range":{"turnOut":true,"into":true,"newMessage":true,"newDialog":true},"meanwhile":true,"wechat":true,"email":false},"appNotice":{"vibration":true,"sound":true,"push":true},"enduranceH5":true,"state":"on","maxChat":999,"founding":true,"activation":true,"wechat":{"openid":"oaEMI1HiP9_vJktfECdtjPofXrm4","nickname":"腊肉小王子","sex":"1","province":"","city":"","country":"LB","unionid":"o6UNQ0gKJIAH_dTdqM0K-Pc8sxFU","mpOpenId":"opiPbwwE4FK3HR7G8kn7EG3-BwdE"},"loginLogs":[],"addtime":"2019-09-28T09:47:28.206Z","id":"5d8f2c3058a74e5059143765"},{"_id":"5db644104bdcaa3400179322","name":"我的小伙伴","nickname":"我的小伙伴","password":"021fb445dd4470ab1a5f6eddbe405640","entId":15438,"email":"619049679@qq.com","serviceId":18293,"lastLoginTime":"2019-10-30T01:47:18.095Z","lastLoginMode":"web","loginCount":7,"socketId":"sNzE4kRjOlPDimR7Rzh_","autoreply":{"welcome":{"type":"text","content":"很高兴为您服务，有什么可以为您效劳的吗？","state":false}},"systemNotice":{"state":true},"authority":{"assist":true},"personality":{"visitorNumber":true,"quickEnd":false,"chatListTag":true,"routeShwo":false,"visitorSame":false,"chatListType":"fold","activeHide":false},"notice":{"sound":"default","range":{"turnOut":true,"into":true,"newMessage":true,"newDialog":true},"meanwhile":true,"wechat":false,"email":false},"appNotice":{"vibration":true,"sound":true,"push":true},"enduranceH5":false,"state":"on","maxChat":99,"founding":false,"activation":true,"loginLogs":[],"addtime":"2019-10-28T01:27:44.949Z","id":"5db644104bdcaa3400179322"}],"session":{"hash":"cda767185bb79448ad2910709e917cce","validity":"864000000"}}
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

    public static class DataBean {
        /**
         * _suc : 1
         * list : [{"_id":"5d8f2c3058a74e5059143765","tel":"15111014495","name":"管理员","password":"72a95471e3bf2d3891ccc98f30e6f6bb","nickname":"管理员","serviceId":16827,"entId":15438,"lastLoginTime":"2019-10-30T01:52:05.390Z","lastLoginMode":"app","loginCount":847,"head":"Fp7VQ_WfZQV3Z-nDur5uEXtWUDGu.jpg","registrationId":"13065ffa4e6f7fdee22","socketIdMobile":"XGIBVxzXnN8g8CCpRzUs","socketId":"stOSpYG7cWn6jPWeRzty","autoreply":{"welcome":{"type":"text","content":"很高兴为您服务，有什么可以为您效劳的吗？","state":false}},"systemNotice":{"state":true},"authority":{"assist":true},"personality":{"visitorNumber":true,"quickEnd":true,"chatListTag":true,"routeShwo":false,"visitorSame":false,"chatListType":"fold","activeHide":false},"notice":{"sound":"default","range":{"turnOut":true,"into":true,"newMessage":true,"newDialog":true},"meanwhile":true,"wechat":true,"email":false},"appNotice":{"vibration":true,"sound":true,"push":true},"enduranceH5":true,"state":"on","maxChat":999,"founding":true,"activation":true,"wechat":{"openid":"oaEMI1HiP9_vJktfECdtjPofXrm4","nickname":"腊肉小王子","sex":"1","province":"","city":"","country":"LB","unionid":"o6UNQ0gKJIAH_dTdqM0K-Pc8sxFU","mpOpenId":"opiPbwwE4FK3HR7G8kn7EG3-BwdE"},"loginLogs":[],"addtime":"2019-09-28T09:47:28.206Z","id":"5d8f2c3058a74e5059143765"},{"_id":"5db644104bdcaa3400179322","name":"我的小伙伴","nickname":"我的小伙伴","password":"021fb445dd4470ab1a5f6eddbe405640","entId":15438,"email":"619049679@qq.com","serviceId":18293,"lastLoginTime":"2019-10-30T01:47:18.095Z","lastLoginMode":"web","loginCount":7,"socketId":"sNzE4kRjOlPDimR7Rzh_","autoreply":{"welcome":{"type":"text","content":"很高兴为您服务，有什么可以为您效劳的吗？","state":false}},"systemNotice":{"state":true},"authority":{"assist":true},"personality":{"visitorNumber":true,"quickEnd":false,"chatListTag":true,"routeShwo":false,"visitorSame":false,"chatListType":"fold","activeHide":false},"notice":{"sound":"default","range":{"turnOut":true,"into":true,"newMessage":true,"newDialog":true},"meanwhile":true,"wechat":false,"email":false},"appNotice":{"vibration":true,"sound":true,"push":true},"enduranceH5":false,"state":"on","maxChat":99,"founding":false,"activation":true,"loginLogs":[],"addtime":"2019-10-28T01:27:44.949Z","id":"5db644104bdcaa3400179322"}]
         * session : {"hash":"cda767185bb79448ad2910709e917cce","validity":"864000000"}
         */

        private int _suc;
        private SessionBean session;
        private List<ListBean> list;

        public int get_suc() {
            return _suc;
        }

        public void set_suc(int _suc) {
            this._suc = _suc;
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

        public static class SessionBean {
            /**
             * hash : cda767185bb79448ad2910709e917cce
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

        public static class  ListBean {
            /**
             * _id : 5d8f2c3058a74e5059143765
             * tel : 15111014495
             * name : 管理员
             * password : 72a95471e3bf2d3891ccc98f30e6f6bb
             * nickname : 管理员
             * serviceId : 16827
             * entId : 15438
             * lastLoginTime : 2019-10-30T01:52:05.390Z
             * lastLoginMode : app
             * loginCount : 847
             * head : Fp7VQ_WfZQV3Z-nDur5uEXtWUDGu.jpg
             * registrationId : 13065ffa4e6f7fdee22
             * socketIdMobile : XGIBVxzXnN8g8CCpRzUs
             * socketId : stOSpYG7cWn6jPWeRzty
             * autoreply : {"welcome":{"type":"text","content":"很高兴为您服务，有什么可以为您效劳的吗？","state":false}}
             * systemNotice : {"state":true}
             * authority : {"assist":true}
             * personality : {"visitorNumber":true,"quickEnd":true,"chatListTag":true,"routeShwo":false,"visitorSame":false,"chatListType":"fold","activeHide":false}
             * notice : {"sound":"default","range":{"turnOut":true,"into":true,"newMessage":true,"newDialog":true},"meanwhile":true,"wechat":true,"email":false}
             * appNotice : {"vibration":true,"sound":true,"push":true}
             * enduranceH5 : true
             * state : on
             * maxChat : 999
             * founding : true
             * activation : true
             * wechat : {"openid":"oaEMI1HiP9_vJktfECdtjPofXrm4","nickname":"腊肉小王子","sex":"1","province":"","city":"","country":"LB","unionid":"o6UNQ0gKJIAH_dTdqM0K-Pc8sxFU","mpOpenId":"opiPbwwE4FK3HR7G8kn7EG3-BwdE"}
             * loginLogs : []
             * addtime : 2019-09-28T09:47:28.206Z
             * id : 5d8f2c3058a74e5059143765
             * email : 619049679@qq.com
             */

            private String _id;
            private String tel;
            private String name;
            private String password;
            private String nickname;
            private int serviceId;
            private int entId;
            private String lastLoginTime;
            private String lastLoginMode;
            private int loginCount;
            private String head;
            private String registrationId;
            private String socketIdMobile;
            private String socketId;
            private AutoreplyBean autoreply;
            private SystemNoticeBean systemNotice;
            private AuthorityBean authority;
            private PersonalityBean personality;
            private NoticeBean notice;
            private AppNoticeBean appNotice;
            private boolean enduranceH5;
            private String state;
            private String realState;
            private int maxChat;
            private boolean founding;
            private boolean activation;
            private WechatBean wechat;
            private String addtime;
            private String id;
            private String email;
            private List<?> loginLogs;
            private int dialogCount;
            private boolean showNum;//是否显示对话数


            public boolean isShowNum() {
                return showNum;
            }

            public void setShowNum(boolean showNum) {
                this.showNum = showNum;
            }

            public String getRealState() {
                return realState;
            }

            public void setRealState(String realState) {
                this.realState = realState;
            }

            public int getDialogCount() {
                return dialogCount;
            }

            public void setDialogCount(int dialogCount) {
                this.dialogCount = dialogCount;
            }

            public String get_id() {
                return _id;
            }

            public void set_id(String _id) {
                this._id = _id;
            }

            public String getTel() {
                return tel;
            }

            public void setTel(String tel) {
                this.tel = tel;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public int getServiceId() {
                return serviceId;
            }

            public void setServiceId(int serviceId) {
                this.serviceId = serviceId;
            }

            public int getEntId() {
                return entId;
            }

            public void setEntId(int entId) {
                this.entId = entId;
            }

            public String getLastLoginTime() {
                return lastLoginTime;
            }

            public void setLastLoginTime(String lastLoginTime) {
                this.lastLoginTime = lastLoginTime;
            }

            public String getLastLoginMode() {
                return lastLoginMode;
            }

            public void setLastLoginMode(String lastLoginMode) {
                this.lastLoginMode = lastLoginMode;
            }

            public int getLoginCount() {
                return loginCount;
            }

            public void setLoginCount(int loginCount) {
                this.loginCount = loginCount;
            }

            public String getHead() {
                return head;
            }

            public void setHead(String head) {
                this.head = head;
            }

            public String getRegistrationId() {
                return registrationId;
            }

            public void setRegistrationId(String registrationId) {
                this.registrationId = registrationId;
            }

            public String getSocketIdMobile() {
                return socketIdMobile;
            }

            public void setSocketIdMobile(String socketIdMobile) {
                this.socketIdMobile = socketIdMobile;
            }

            public String getSocketId() {
                return socketId;
            }

            public void setSocketId(String socketId) {
                this.socketId = socketId;
            }

            public AutoreplyBean getAutoreply() {
                return autoreply;
            }

            public void setAutoreply(AutoreplyBean autoreply) {
                this.autoreply = autoreply;
            }

            public SystemNoticeBean getSystemNotice() {
                return systemNotice;
            }

            public void setSystemNotice(SystemNoticeBean systemNotice) {
                this.systemNotice = systemNotice;
            }

            public AuthorityBean getAuthority() {
                return authority;
            }

            public void setAuthority(AuthorityBean authority) {
                this.authority = authority;
            }

            public PersonalityBean getPersonality() {
                return personality;
            }

            public void setPersonality(PersonalityBean personality) {
                this.personality = personality;
            }

            public NoticeBean getNotice() {
                return notice;
            }

            public void setNotice(NoticeBean notice) {
                this.notice = notice;
            }

            public AppNoticeBean getAppNotice() {
                return appNotice;
            }

            public void setAppNotice(AppNoticeBean appNotice) {
                this.appNotice = appNotice;
            }

            public boolean isEnduranceH5() {
                return enduranceH5;
            }

            public void setEnduranceH5(boolean enduranceH5) {
                this.enduranceH5 = enduranceH5;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public int getMaxChat() {
                return maxChat;
            }

            public void setMaxChat(int maxChat) {
                this.maxChat = maxChat;
            }

            public boolean isFounding() {
                return founding;
            }

            public void setFounding(boolean founding) {
                this.founding = founding;
            }

            public boolean isActivation() {
                return activation;
            }

            public void setActivation(boolean activation) {
                this.activation = activation;
            }

            public WechatBean getWechat() {
                return wechat;
            }

            public void setWechat(WechatBean wechat) {
                this.wechat = wechat;
            }

            public String getAddtime() {
                return addtime;
            }

            public void setAddtime(String addtime) {
                this.addtime = addtime;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public List<?> getLoginLogs() {
                return loginLogs;
            }

            public void setLoginLogs(List<?> loginLogs) {
                this.loginLogs = loginLogs;
            }

            public static class AutoreplyBean {
                /**
                 * welcome : {"type":"text","content":"很高兴为您服务，有什么可以为您效劳的吗？","state":false}
                 */

                private WelcomeBean welcome;

                public WelcomeBean getWelcome() {
                    return welcome;
                }

                public void setWelcome(WelcomeBean welcome) {
                    this.welcome = welcome;
                }

                public static class WelcomeBean {
                    /**
                     * type : text
                     * content : 很高兴为您服务，有什么可以为您效劳的吗？
                     * state : false
                     */

                    private String type;
                    private String content;
                    private boolean state;

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }

                    public String getContent() {
                        return content;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public boolean isState() {
                        return state;
                    }

                    public void setState(boolean state) {
                        this.state = state;
                    }
                }
            }

            public static class SystemNoticeBean {
                /**
                 * state : true
                 */

                private boolean state;

                public boolean isState() {
                    return state;
                }

                public void setState(boolean state) {
                    this.state = state;
                }
            }

            public static class AuthorityBean {
                /**
                 * assist : true
                 */

                private boolean assist;

                public boolean isAssist() {
                    return assist;
                }

                public void setAssist(boolean assist) {
                    this.assist = assist;
                }
            }

            public static class PersonalityBean {
                /**
                 * visitorNumber : true
                 * quickEnd : true
                 * chatListTag : true
                 * routeShwo : false
                 * visitorSame : false
                 * chatListType : fold
                 * activeHide : false
                 */

                private boolean visitorNumber;
                private boolean quickEnd;
                private boolean chatListTag;
                private boolean routeShwo;
                private boolean visitorSame;
                private String chatListType;
                private boolean activeHide;

                public boolean isVisitorNumber() {
                    return visitorNumber;
                }

                public void setVisitorNumber(boolean visitorNumber) {
                    this.visitorNumber = visitorNumber;
                }

                public boolean isQuickEnd() {
                    return quickEnd;
                }

                public void setQuickEnd(boolean quickEnd) {
                    this.quickEnd = quickEnd;
                }

                public boolean isChatListTag() {
                    return chatListTag;
                }

                public void setChatListTag(boolean chatListTag) {
                    this.chatListTag = chatListTag;
                }

                public boolean isRouteShwo() {
                    return routeShwo;
                }

                public void setRouteShwo(boolean routeShwo) {
                    this.routeShwo = routeShwo;
                }

                public boolean isVisitorSame() {
                    return visitorSame;
                }

                public void setVisitorSame(boolean visitorSame) {
                    this.visitorSame = visitorSame;
                }

                public String getChatListType() {
                    return chatListType;
                }

                public void setChatListType(String chatListType) {
                    this.chatListType = chatListType;
                }

                public boolean isActiveHide() {
                    return activeHide;
                }

                public void setActiveHide(boolean activeHide) {
                    this.activeHide = activeHide;
                }
            }

            public static class NoticeBean {
                /**
                 * sound : default
                 * range : {"turnOut":true,"into":true,"newMessage":true,"newDialog":true}
                 * meanwhile : true
                 * wechat : true
                 * email : false
                 */

                private String sound;
                private RangeBean range;
                private boolean meanwhile;
                private boolean wechat;
                private boolean email;

                public String getSound() {
                    return sound;
                }

                public void setSound(String sound) {
                    this.sound = sound;
                }

                public RangeBean getRange() {
                    return range;
                }

                public void setRange(RangeBean range) {
                    this.range = range;
                }

                public boolean isMeanwhile() {
                    return meanwhile;
                }

                public void setMeanwhile(boolean meanwhile) {
                    this.meanwhile = meanwhile;
                }

                public boolean isWechat() {
                    return wechat;
                }

                public void setWechat(boolean wechat) {
                    this.wechat = wechat;
                }

                public boolean isEmail() {
                    return email;
                }

                public void setEmail(boolean email) {
                    this.email = email;
                }

                public static class RangeBean {
                    /**
                     * turnOut : true
                     * into : true
                     * newMessage : true
                     * newDialog : true
                     */

                    private boolean turnOut;
                    private boolean into;
                    private boolean newMessage;
                    private boolean newDialog;

                    public boolean isTurnOut() {
                        return turnOut;
                    }

                    public void setTurnOut(boolean turnOut) {
                        this.turnOut = turnOut;
                    }

                    public boolean isInto() {
                        return into;
                    }

                    public void setInto(boolean into) {
                        this.into = into;
                    }

                    public boolean isNewMessage() {
                        return newMessage;
                    }

                    public void setNewMessage(boolean newMessage) {
                        this.newMessage = newMessage;
                    }

                    public boolean isNewDialog() {
                        return newDialog;
                    }

                    public void setNewDialog(boolean newDialog) {
                        this.newDialog = newDialog;
                    }
                }
            }

            public static class AppNoticeBean {
                /**
                 * vibration : true
                 * sound : true
                 * push : true
                 */

                private boolean vibration;
                private boolean sound;
                private boolean push;

                public boolean isVibration() {
                    return vibration;
                }

                public void setVibration(boolean vibration) {
                    this.vibration = vibration;
                }

                public boolean isSound() {
                    return sound;
                }

                public void setSound(boolean sound) {
                    this.sound = sound;
                }

                public boolean isPush() {
                    return push;
                }

                public void setPush(boolean push) {
                    this.push = push;
                }
            }

            public static class WechatBean {
                /**
                 * openid : oaEMI1HiP9_vJktfECdtjPofXrm4
                 * nickname : 腊肉小王子
                 * sex : 1
                 * province :
                 * city :
                 * country : LB
                 * unionid : o6UNQ0gKJIAH_dTdqM0K-Pc8sxFU
                 * mpOpenId : opiPbwwE4FK3HR7G8kn7EG3-BwdE
                 */

                private String openid;
                private String nickname;
                private String sex;
                private String province;
                private String city;
                private String country;
                private String unionid;
                private String mpOpenId;

                public String getOpenid() {
                    return openid;
                }

                public void setOpenid(String openid) {
                    this.openid = openid;
                }

                public String getNickname() {
                    return nickname;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }

                public String getSex() {
                    return sex;
                }

                public void setSex(String sex) {
                    this.sex = sex;
                }

                public String getProvince() {
                    return province;
                }

                public void setProvince(String province) {
                    this.province = province;
                }

                public String getCity() {
                    return city;
                }

                public void setCity(String city) {
                    this.city = city;
                }

                public String getCountry() {
                    return country;
                }

                public void setCountry(String country) {
                    this.country = country;
                }

                public String getUnionid() {
                    return unionid;
                }

                public void setUnionid(String unionid) {
                    this.unionid = unionid;
                }

                public String getMpOpenId() {
                    return mpOpenId;
                }

                public void setMpOpenId(String mpOpenId) {
                    this.mpOpenId = mpOpenId;
                }
            }
        }
    }
}
