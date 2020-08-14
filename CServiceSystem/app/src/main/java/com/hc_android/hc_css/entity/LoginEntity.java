package com.hc_android.hc_css.entity;

public class LoginEntity {


    /**
     * code : 200
     * msg : ok
     * data : {"_suc":1,"hash":"db499bbe3777b499e3f1616b2b54e3cf","info":{"id":"5d8f2c3058a74e5059143765","name":"管理局","activation":true,"tel":"15111014495","head":"FsSc2vImy6sQ8oK4lxGoIOW0O3ys.jpg","nickname":"红红火火","autograph":"igjfhfhfhfngngjgjgjvjchc\ncghhhjjw体系一直嘻嘻嘻休息一下","autoreply":{"welcome":{"state":false,"content":"很高兴为您服务，有什么可以为您效劳的吗？","type":"text"}},"entId":15438,"founding":true,"maxChat":999,"serviceId":16827,"state":"on","company":{"edition":"standard","dueDate":"2020-10-08T06:40:12.887Z","openDate":"2019-10-08T06:40:12.887Z","experience":true,"channel":{"state":false},"evaluate":{"replyContent":"感谢您的评价","reply":false,"tips":"请输入评价内容（选填）","welcome":"请对我的服务进行评价","invitation":true,"state":true},"places":5,"coupon":{"grade":"B","expiryTime":"2019-10-01T09:47:28.212Z","state":true}},"viewConcealment":{},"appNotice":{"push":true,"sound":true,"vibration":true},"registrationId":"120c83f7604be83268a","enduranceH5":true,"systemNotice":{"state":true},"passwordOk":true,"authority":{"assist":true},"notice":{"email":false,"wechat":true,"meanwhile":true,"range":{"newDialog":true,"newMessage":true,"into":true,"turnOut":true},"sound":"default"},"personality":{"activeHide":false,"chatListType":"fold","visitorSame":false,"routeShwo":false,"chatListTag":true,"quickEnd":true,"visitorNumber":true},"wechat":{"mpOpenId":"opiPbwwE4FK3HR7G8kn7EG3-BwdE","unionid":"o6UNQ0gKJIAH_dTdqM0K-Pc8sxFU","country":"LB","city":"","province":"","sex":"1","nickname":"腊肉小王子","openid":"oaEMI1HiP9_vJktfECdtjPofXrm4"}},"validity":"864000000"}
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
         * hash : db499bbe3777b499e3f1616b2b54e3cf
         * info : {"id":"5d8f2c3058a74e5059143765","name":"管理局","activation":true,"tel":"15111014495","head":"FsSc2vImy6sQ8oK4lxGoIOW0O3ys.jpg","nickname":"红红火火","autograph":"igjfhfhfhfngngjgjgjvjchc\ncghhhjjw体系一直嘻嘻嘻休息一下","autoreply":{"welcome":{"state":false,"content":"很高兴为您服务，有什么可以为您效劳的吗？","type":"text"}},"entId":15438,"founding":true,"maxChat":999,"serviceId":16827,"state":"on","company":{"edition":"standard","dueDate":"2020-10-08T06:40:12.887Z","openDate":"2019-10-08T06:40:12.887Z","experience":true,"channel":{"state":false},"evaluate":{"replyContent":"感谢您的评价","reply":false,"tips":"请输入评价内容（选填）","welcome":"请对我的服务进行评价","invitation":true,"state":true},"places":5,"coupon":{"grade":"B","expiryTime":"2019-10-01T09:47:28.212Z","state":true}},"viewConcealment":{},"appNotice":{"push":true,"sound":true,"vibration":true},"registrationId":"120c83f7604be83268a","enduranceH5":true,"systemNotice":{"state":true},"passwordOk":true,"authority":{"assist":true},"notice":{"email":false,"wechat":true,"meanwhile":true,"range":{"newDialog":true,"newMessage":true,"into":true,"turnOut":true},"sound":"default"},"personality":{"activeHide":false,"chatListType":"fold","visitorSame":false,"routeShwo":false,"chatListTag":true,"quickEnd":true,"visitorNumber":true},"wechat":{"mpOpenId":"opiPbwwE4FK3HR7G8kn7EG3-BwdE","unionid":"o6UNQ0gKJIAH_dTdqM0K-Pc8sxFU","country":"LB","city":"","province":"","sex":"1","nickname":"腊肉小王子","openid":"oaEMI1HiP9_vJktfECdtjPofXrm4"}}
         * validity : 864000000
         */

        private int _suc;
        private String hash;
        private InfoBean info;
        private String validity;
        private String text;



        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

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

        public static class InfoBean {
            /**
             * id : 5d8f2c3058a74e5059143765
             * name : 管理局
             * activation : true
             * tel : 15111014495
             * head : FsSc2vImy6sQ8oK4lxGoIOW0O3ys.jpg
             * nickname : 红红火火
             * autograph : igjfhfhfhfngngjgjgjvjchc
             cghhhjjw体系一直嘻嘻嘻休息一下
             * autoreply : {"welcome":{"state":false,"content":"很高兴为您服务，有什么可以为您效劳的吗？","type":"text"}}
             * entId : 15438
             * founding : true
             * maxChat : 999
             * serviceId : 16827
             * state : on
             * company : {"edition":"standard","dueDate":"2020-10-08T06:40:12.887Z","openDate":"2019-10-08T06:40:12.887Z","experience":true,"channel":{"state":false},"evaluate":{"replyContent":"感谢您的评价","reply":false,"tips":"请输入评价内容（选填）","welcome":"请对我的服务进行评价","invitation":true,"state":true},"places":5,"coupon":{"grade":"B","expiryTime":"2019-10-01T09:47:28.212Z","state":true}}
             * viewConcealment : {}
             * appNotice : {"push":true,"sound":true,"vibration":true}
             * registrationId : 120c83f7604be83268a
             * enduranceH5 : true
             * systemNotice : {"state":true}
             * passwordOk : true
             * authority : {"assist":true}
             * notice : {"email":false,"wechat":true,"meanwhile":true,"range":{"newDialog":true,"newMessage":true,"into":true,"turnOut":true},"sound":"default"}
             * personality : {"activeHide":false,"chatListType":"fold","visitorSame":false,"routeShwo":false,"chatListTag":true,"quickEnd":true,"visitorNumber":true}
             * wechat : {"mpOpenId":"opiPbwwE4FK3HR7G8kn7EG3-BwdE","unionid":"o6UNQ0gKJIAH_dTdqM0K-Pc8sxFU","country":"LB","city":"","province":"","sex":"1","nickname":"腊肉小王子","openid":"oaEMI1HiP9_vJktfECdtjPofXrm4"}
             */

            private String id;
            private String name;
            private boolean activation;
            private String tel;
            private String email;
            private String head;
            private String nickname;
            private String autograph;
            private AutoreplyBean autoreply;
            private int entId;
            private boolean founding;
            private int maxChat;
            private int serviceId;
            private String state;
            private CompanyBean company;
            private ViewConcealmentBean viewConcealment;
            private AppNoticeBean appNotice;
            private String registrationId;
            private boolean enduranceH5;
            private SystemNoticeBean systemNotice;
            private boolean passwordOk;
            private AuthorityBean authority;
            private NoticeBean notice;
            private PersonalityBean personality;
            private WechatBean wechat;
            private String socketId;//电脑在线时返回


            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getSocketId() {
                return socketId;
            }

            public void setSocketId(String socketId) {
                this.socketId = socketId;
            }

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

            public String getAutograph() {
                return autograph;
            }

            public void setAutograph(String autograph) {
                this.autograph = autograph;
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

            public String getRegistrationId() {
                return registrationId;
            }

            public void setRegistrationId(String registrationId) {
                this.registrationId = registrationId;
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

            public WechatBean getWechat() {
                return wechat;
            }

            public void setWechat(WechatBean wechat) {
                this.wechat = wechat;
            }

            public static class AutoreplyBean {
                /**
                 * welcome : {"state":false,"content":"很高兴为您服务，有什么可以为您效劳的吗？","type":"text"}
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
                     * state : false
                     * content : 很高兴为您服务，有什么可以为您效劳的吗？
                     * type : text
                     */

                    private boolean state;
                    private String content;
                    private String type;

                    public boolean isState() {
                        return state;
                    }

                    public void setState(boolean state) {
                        this.state = state;
                    }

                    public String getContent() {
                        return content;
                    }

                    public void setContent(String content) {
                        this.content = content;
                    }

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }
                }
            }

            public static class CompanyBean {
                String edition;
                RealNameAuthBean realNameAuth;

                public RealNameAuthBean getRealNameAuth() {
                    return realNameAuth;
                }

                public void setRealNameAuth(RealNameAuthBean realNameAuth) {
                    this.realNameAuth = realNameAuth;
                }

                public String getEdition() {
                    return edition;
                }

                public void setEdition(String edition) {
                    this.edition = edition;
                }

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
                     * replyContent : 感谢您的评价
                     * reply : false
                     * tips : 请输入评价内容（选填）
                     * welcome : 请对我的服务进行评价
                     * invitation : true
                     * state : true
                     */

                    private String replyContent;
                    private boolean reply;
                    private String tips;
                    private String welcome;
                    private boolean invitation;
                    private boolean state;

                    public String getReplyContent() {
                        return replyContent;
                    }

                    public void setReplyContent(String replyContent) {
                        this.replyContent = replyContent;
                    }

                    public boolean isReply() {
                        return reply;
                    }

                    public void setReply(boolean reply) {
                        this.reply = reply;
                    }

                    public String getTips() {
                        return tips;
                    }

                    public void setTips(String tips) {
                        this.tips = tips;
                    }

                    public String getWelcome() {
                        return welcome;
                    }

                    public void setWelcome(String welcome) {
                        this.welcome = welcome;
                    }

                    public boolean isInvitation() {
                        return invitation;
                    }

                    public void setInvitation(boolean invitation) {
                        this.invitation = invitation;
                    }

                    public boolean isState() {
                        return state;
                    }

                    public void setState(boolean state) {
                        this.state = state;
                    }
                }

                public static class CouponBean {
                }

                public static class RealNameAuthBean {

                    /**
                     * authId : 497f37e1fb11eecd74f32ea01785420d
                     * business : {"capital":"壹佰万元人民币","credit":"91610138397119703K","code":1000,"companyType":"有限责任公司(自然人投资或控股)","owner":"张强","name":"西安网极网络科技有限公司","address":"陕西省西安市国家民用航天产业基地神舟四路239号2栋5月501-A04","establishDate":"20140530","expirationDate":"29991231","ifCreditValid":true,"businessScope":"软件设计与开发,企业信息化服务;网络工程设计施工;网站计与开发;计算机软硬件销售;计算机技术开发、技术服务:商标、版权、著作权、专利的代理及转让;广告的设计、制作、理、发布。(依法须经批准的项目,经相关部门批准后方可开展经营活动)"}
                     * bankNum : {"bankphone":"15111014495","bankcardname":"1259","bankcardnum":"123456789123","name":"西安网极网络科技有限公司"}
                     * remittance : {"state":"checked","money":"0.91"}
                     * type : corporatebank
                     * complestatus : true
                     * state : true
                     */

                    private String authId;
                    private BusinessBean business;
                    private BankNumBean bankNum;
                    private RemittanceBean remittance;
                    private String type;
                    private String complestatus;
                    private boolean state;


                    public String getComplestatus() {
                        return complestatus;
                    }

                    public void setComplestatus(String complestatus) {
                        this.complestatus = complestatus;
                    }

                    public String getAuthId() {
                        return authId;
                    }

                    public void setAuthId(String authId) {
                        this.authId = authId;
                    }

                    public BusinessBean getBusiness() {
                        return business;
                    }

                    public void setBusiness(BusinessBean business) {
                        this.business = business;
                    }

                    public BankNumBean getBankNum() {
                        return bankNum;
                    }

                    public void setBankNum(BankNumBean bankNum) {
                        this.bankNum = bankNum;
                    }

                    public RemittanceBean getRemittance() {
                        return remittance;
                    }

                    public void setRemittance(RemittanceBean remittance) {
                        this.remittance = remittance;
                    }

                    public String getType() {
                        return type;
                    }

                    public void setType(String type) {
                        this.type = type;
                    }


                    public boolean isState() {
                        return state;
                    }

                    public void setState(boolean state) {
                        this.state = state;
                    }

                    public static class BusinessBean {
                        /**
                         * capital : 壹佰万元人民币
                         * credit : 91610138397119703K
                         * code : 1000
                         * companyType : 有限责任公司(自然人投资或控股)
                         * owner : 张强
                         * name : 西安网极网络科技有限公司
                         * address : 陕西省西安市国家民用航天产业基地神舟四路239号2栋5月501-A04
                         * establishDate : 20140530
                         * expirationDate : 29991231
                         * ifCreditValid : true
                         * businessScope : 软件设计与开发,企业信息化服务;网络工程设计施工;网站计与开发;计算机软硬件销售;计算机技术开发、技术服务:商标、版权、著作权、专利的代理及转让;广告的设计、制作、理、发布。(依法须经批准的项目,经相关部门批准后方可开展经营活动)
                         */

                        private String capital;
                        private String credit;
                        private int code;
                        private String companyType;
                        private String owner;
                        private String name;
                        private String address;
                        private String establishDate;
                        private String expirationDate;
                        private boolean ifCreditValid;
                        private String businessScope;

                        public String getCapital() {
                            return capital;
                        }

                        public void setCapital(String capital) {
                            this.capital = capital;
                        }

                        public String getCredit() {
                            return credit;
                        }

                        public void setCredit(String credit) {
                            this.credit = credit;
                        }

                        public int getCode() {
                            return code;
                        }

                        public void setCode(int code) {
                            this.code = code;
                        }

                        public String getCompanyType() {
                            return companyType;
                        }

                        public void setCompanyType(String companyType) {
                            this.companyType = companyType;
                        }

                        public String getOwner() {
                            return owner;
                        }

                        public void setOwner(String owner) {
                            this.owner = owner;
                        }

                        public String getName() {
                            return name;
                        }

                        public void setName(String name) {
                            this.name = name;
                        }

                        public String getAddress() {
                            return address;
                        }

                        public void setAddress(String address) {
                            this.address = address;
                        }

                        public String getEstablishDate() {
                            return establishDate;
                        }

                        public void setEstablishDate(String establishDate) {
                            this.establishDate = establishDate;
                        }

                        public String getExpirationDate() {
                            return expirationDate;
                        }

                        public void setExpirationDate(String expirationDate) {
                            this.expirationDate = expirationDate;
                        }

                        public boolean isIfCreditValid() {
                            return ifCreditValid;
                        }

                        public void setIfCreditValid(boolean ifCreditValid) {
                            this.ifCreditValid = ifCreditValid;
                        }

                        public String getBusinessScope() {
                            return businessScope;
                        }

                        public void setBusinessScope(String businessScope) {
                            this.businessScope = businessScope;
                        }
                    }

                    public static class BankNumBean {
                        /**
                         * bankphone : 15111014495
                         * bankcardname : 1259
                         * bankcardnum : 123456789123
                         * name : 西安网极网络科技有限公司
                         */

                        private String bankphone;
                        private String bankcardname;
                        private String bankcardnum;
                        private String name;

                        public String getBankphone() {
                            return bankphone;
                        }

                        public void setBankphone(String bankphone) {
                            this.bankphone = bankphone;
                        }

                        public String getBankcardname() {
                            return bankcardname;
                        }

                        public void setBankcardname(String bankcardname) {
                            this.bankcardname = bankcardname;
                        }

                        public String getBankcardnum() {
                            return bankcardnum;
                        }

                        public void setBankcardnum(String bankcardnum) {
                            this.bankcardnum = bankcardnum;
                        }

                        public String getName() {
                            return name;
                        }

                        public void setName(String name) {
                            this.name = name;
                        }
                    }

                    public static class RemittanceBean {
                        /**
                         * state : checked
                         * money : 0.91
                         */

                        private String state;
                        private String money;

                        public RemittanceBean(String state) {
                            this.state = state;
                        }

                        public String getState() {
                            return state;
                        }

                        public void setState(String state) {
                            this.state = state;
                        }

                        public String getMoney() {
                            return money;
                        }

                        public void setMoney(String money) {
                            this.money = money;
                        }
                    }
                }
            }

            public static class ViewConcealmentBean {
            }

            public static class AppNoticeBean {
                /**
                 * push : true
                 * sound : true
                 * vibration : true
                 */

                private boolean push;
                private boolean sound;
                private boolean vibration;

                public boolean isPush() {
                    return push;
                }

                public void setPush(boolean push) {
                    this.push = push;
                }

                public boolean isSound() {
                    return sound;
                }

                public void setSound(boolean sound) {
                    this.sound = sound;
                }

                public boolean isVibration() {
                    return vibration;
                }

                public void setVibration(boolean vibration) {
                    this.vibration = vibration;
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

                private boolean assist;//对话协助，是否开启，如果关闭，就不显示同事对话，和关闭转接对话功能

                public boolean isAssist() {
                    return assist;
                }

                public void setAssist(boolean assist) {
                    this.assist = assist;
                }
            }

            public static class NoticeBean {
                /**
                 * email : false
                 * wechat : true
                 * meanwhile : true
                 * range : {"newDialog":true,"newMessage":true,"into":true,"turnOut":true}
                 * sound : default
                 */

                private boolean email;
                private boolean wechat;
                private boolean meanwhile;
                private RangeBean range;
                private String sound;

                public boolean isEmail() {
                    return email;
                }

                public void setEmail(boolean email) {
                    this.email = email;
                }

                public boolean isWechat() {
                    return wechat;
                }

                public void setWechat(boolean wechat) {
                    this.wechat = wechat;
                }

                public boolean isMeanwhile() {
                    return meanwhile;
                }

                public void setMeanwhile(boolean meanwhile) {
                    this.meanwhile = meanwhile;
                }

                public RangeBean getRange() {
                    return range;
                }

                public void setRange(RangeBean range) {
                    this.range = range;
                }

                public String getSound() {
                    return sound;
                }

                public void setSound(String sound) {
                    this.sound = sound;
                }

                public static class RangeBean {
                    /**
                     * newDialog : true
                     * newMessage : true
                     * into : true
                     * turnOut : true
                     */

                    private boolean newDialog;
                    private boolean newMessage;
                    private boolean into;
                    private boolean turnOut;

                    public boolean isNewDialog() {
                        return newDialog;
                    }

                    public void setNewDialog(boolean newDialog) {
                        this.newDialog = newDialog;
                    }

                    public boolean isNewMessage() {
                        return newMessage;
                    }

                    public void setNewMessage(boolean newMessage) {
                        this.newMessage = newMessage;
                    }

                    public boolean isInto() {
                        return into;
                    }

                    public void setInto(boolean into) {
                        this.into = into;
                    }

                    public boolean isTurnOut() {
                        return turnOut;
                    }

                    public void setTurnOut(boolean turnOut) {
                        this.turnOut = turnOut;
                    }
                }
            }

            public static class PersonalityBean {
                /**
                 * activeHide : false
                 * chatListType : fold
                 * visitorSame : false
                 * routeShwo : false
                 * chatListTag : true
                 * quickEnd : true
                 * visitorNumber : true
                 */

                private boolean activeHide;
                private String chatListType;
                private boolean visitorSame;
                private boolean routeShwo;
                private boolean chatListTag;
                private boolean quickEnd;
                private boolean visitorNumber;

                public boolean isActiveHide() {
                    return activeHide;
                }

                public void setActiveHide(boolean activeHide) {
                    this.activeHide = activeHide;
                }

                public String getChatListType() {
                    return chatListType;
                }

                public void setChatListType(String chatListType) {
                    this.chatListType = chatListType;
                }

                public boolean isVisitorSame() {
                    return visitorSame;
                }

                public void setVisitorSame(boolean visitorSame) {
                    this.visitorSame = visitorSame;
                }

                public boolean isRouteShwo() {
                    return routeShwo;
                }

                public void setRouteShwo(boolean routeShwo) {
                    this.routeShwo = routeShwo;
                }

                public boolean isChatListTag() {
                    return chatListTag;
                }

                public void setChatListTag(boolean chatListTag) {
                    this.chatListTag = chatListTag;
                }

                public boolean isQuickEnd() {
                    return quickEnd;
                }

                public void setQuickEnd(boolean quickEnd) {
                    this.quickEnd = quickEnd;
                }

                public boolean isVisitorNumber() {
                    return visitorNumber;
                }

                public void setVisitorNumber(boolean visitorNumber) {
                    this.visitorNumber = visitorNumber;
                }
            }

            public static class WechatBean {
                /**
                 * mpOpenId : opiPbwwE4FK3HR7G8kn7EG3-BwdE
                 * unionid : o6UNQ0gKJIAH_dTdqM0K-Pc8sxFU
                 * country : LB
                 * city :
                 * province :
                 * sex : 1
                 * nickname : 腊肉小王子
                 * openid : oaEMI1HiP9_vJktfECdtjPofXrm4
                 */

                private String mpOpenId;
                private String unionid;
                private String country;
                private String city;
                private String province;
                private String sex;
                private String nickname;
                private String openid;

                public String getMpOpenId() {
                    return mpOpenId;
                }

                public void setMpOpenId(String mpOpenId) {
                    this.mpOpenId = mpOpenId;
                }

                public String getUnionid() {
                    return unionid;
                }

                public void setUnionid(String unionid) {
                    this.unionid = unionid;
                }

                public String getCountry() {
                    return country;
                }

                public void setCountry(String country) {
                    this.country = country;
                }

                public String getCity() {
                    return city;
                }

                public void setCity(String city) {
                    this.city = city;
                }

                public String getProvince() {
                    return province;
                }

                public void setProvince(String province) {
                    this.province = province;
                }

                public String getSex() {
                    return sex;
                }

                public void setSex(String sex) {
                    this.sex = sex;
                }

                public String getNickname() {
                    return nickname;
                }

                public void setNickname(String nickname) {
                    this.nickname = nickname;
                }

                public String getOpenid() {
                    return openid;
                }

                public void setOpenid(String openid) {
                    this.openid = openid;
                }
            }
        }
    }


}
