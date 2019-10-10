package com.hecong.cssystem.entity;

public class LoginEntity {


    /**
     * _suc : 1
     * hash : a18659dc2c3ff8230b9294ac35040bc5
     * info : {"id":"5d8f2c3058a74e5059143765","name":"管理员","activation":true,"tel":"15111014495","head":"Fp7VQ_WfZQV3Z-nDur5uEXtWUDGu.jpg","nickname":"管理员","autoreply":{"welcome":{"type":"text","content":"很高兴为您服务，有什么可以为您效劳的吗？","state":false}},"entId":15438,"founding":true,"maxChat":99,"serviceId":16827,"state":"on","company":{"edition":"standard","dueDate":"2020-10-08T06:40:12.887Z","openDate":"2019-10-08T06:40:12.887Z","experience":true,"channel":{"state":false},"evaluate":{"state":true},"places":1,"coupon":{"grade":"B","expiryTime":"2019-10-01T09:47:28.212Z","state":true}},"viewConcealment":{},"appNotice":{"vibration":true,"sound":true,"push":true},"enduranceH5":true,"systemNotice":{"state":true},"passwordOk":true,"authority":{"assist":true},"notice":{"sound":"default","range":{"turnOut":true,"into":true,"newMessage":true,"newDialog":true},"meanwhile":true,"wechat":false,"email":false},"personality":{"visitorNumber":true,"quickEnd":false,"chatListTag":true,"routeShwo":false,"visitorSame":false,"chatListType":"tab","activeHide":false}}
     * validity : 864000000
     * aihecong_version : 1.9.1
     */

    private int _suc;
    private String hash;
    private InfoBean info;
    private String validity;
    private String aihecong_version;

    public int get_suc() {
        return _suc;
    }

    public void set_suc(int _suc) {
        this._suc = _suc;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }

    public String getAihecong_version() {
        return aihecong_version;
    }

    public void setAihecong_version(String aihecong_version) {
        this.aihecong_version = aihecong_version;
    }

    public static class InfoBean {
        /**
         * id : 5d8f2c3058a74e5059143765
         * name : 管理员
         * activation : true
         * tel : 15111014495
         * head : Fp7VQ_WfZQV3Z-nDur5uEXtWUDGu.jpg
         * nickname : 管理员
         * autoreply : {"welcome":{"type":"text","content":"很高兴为您服务，有什么可以为您效劳的吗？","state":false}}
         * entId : 15438
         * founding : true
         * maxChat : 99
         * serviceId : 16827
         * state : on
         * company : {"edition":"standard","dueDate":"2020-10-08T06:40:12.887Z","openDate":"2019-10-08T06:40:12.887Z","experience":true,"channel":{"state":false},"evaluate":{"state":true},"places":1,"coupon":{"grade":"B","expiryTime":"2019-10-01T09:47:28.212Z","state":true}}
         * viewConcealment : {}
         * appNotice : {"vibration":true,"sound":true,"push":true}
         * enduranceH5 : true
         * systemNotice : {"state":true}
         * passwordOk : true
         * authority : {"assist":true}
         * notice : {"sound":"default","range":{"turnOut":true,"into":true,"newMessage":true,"newDialog":true},"meanwhile":true,"wechat":false,"email":false}
         * personality : {"visitorNumber":true,"quickEnd":false,"chatListTag":true,"routeShwo":false,"visitorSame":false,"chatListType":"tab","activeHide":false}
         */

        private String id;
        private String name;
        private boolean activation;
        private String tel;
        private String head;
        private String nickname;
        private AutoreplyBean autoreply;
        private int entId;
        private boolean founding;
        private int maxChat;
        private int serviceId;
        private String state;
        private CompanyBean company;
        private ViewConcealmentBean viewConcealment;
        private AppNoticeBean appNotice;
        private boolean enduranceH5;
        private SystemNoticeBean systemNotice;
        private boolean passwordOk;
        private AuthorityBean authority;
        private NoticeBean notice;
        private PersonalityBean personality;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isActivation() {
            return activation;
        }

        public void setActivation(boolean activation) {
            this.activation = activation;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public AutoreplyBean getAutoreply() {
            return autoreply;
        }

        public void setAutoreply(AutoreplyBean autoreply) {
            this.autoreply = autoreply;
        }

        public int getEntId() {
            return entId;
        }

        public void setEntId(int entId) {
            this.entId = entId;
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

        public int getServiceId() {
            return serviceId;
        }

        public void setServiceId(int serviceId) {
            this.serviceId = serviceId;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public CompanyBean getCompany() {
            return company;
        }

        public void setCompany(CompanyBean company) {
            this.company = company;
        }

        public ViewConcealmentBean getViewConcealment() {
            return viewConcealment;
        }

        public void setViewConcealment(ViewConcealmentBean viewConcealment) {
            this.viewConcealment = viewConcealment;
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

        public SystemNoticeBean getSystemNotice() {
            return systemNotice;
        }

        public void setSystemNotice(SystemNoticeBean systemNotice) {
            this.systemNotice = systemNotice;
        }

        public boolean isPasswordOk() {
            return passwordOk;
        }

        public void setPasswordOk(boolean passwordOk) {
            this.passwordOk = passwordOk;
        }

        public AuthorityBean getAuthority() {
            return authority;
        }

        public void setAuthority(AuthorityBean authority) {
            this.authority = authority;
        }

        public NoticeBean getNotice() {
            return notice;
        }

        public void setNotice(NoticeBean notice) {
            this.notice = notice;
        }

        public PersonalityBean getPersonality() {
            return personality;
        }

        public void setPersonality(PersonalityBean personality) {
            this.personality = personality;
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

        public static class CompanyBean {
            public static class ChannelBean {
                /**
                 * state : false
                 */

                private boolean state;

                public boolean isState() {
                    return state;
                }

                public void setState(boolean state) {
                    this.state = state;
                }
            }

            public static class EvaluateBean {
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

            public static class CouponBean {
            }
        }

        public static class ViewConcealmentBean {
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

        public static class NoticeBean {
            /**
             * sound : default
             * range : {"turnOut":true,"into":true,"newMessage":true,"newDialog":true}
             * meanwhile : true
             * wechat : false
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

        public static class PersonalityBean {
            /**
             * visitorNumber : true
             * quickEnd : false
             * chatListTag : true
             * routeShwo : false
             * visitorSame : false
             * chatListType : tab
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
    }
}
