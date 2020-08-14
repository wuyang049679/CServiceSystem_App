package com.hc_android.hc_css.entity;

public class UpdateUserEntity {
    String name;
    String nickname;
    String head;
    String autograph;
    String registrationId;//消息推送token
    String registrationIdType;//消息推送厂商类型 ios/huawei/xiaomi/oppo
    AppNoticeBean appNotice;
    NoticeBean notice;
    public String getRegistrationId() {
        return registrationId;
    }

    public void setRegistrationId(String registrationId) {
        this.registrationId = registrationId;
    }

    public String getRegistrationIdType() {
        return registrationIdType;
    }

    public void setRegistrationIdType(String registrationIdType) {
        this.registrationIdType = registrationIdType;
    }

    public String getAutograph() {
        return autograph;
    }

    public void setAutograph(String autograph) {
        this.autograph = autograph;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public AppNoticeBean getAppNotice() {
        return appNotice;
    }

    public void setAppNotice(AppNoticeBean appNotice) {
        this.appNotice = appNotice;
    }

    public NoticeBean getNotice() {
        return notice;
    }

    public void setNotice(NoticeBean notice) {
        this.notice = notice;
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
        private String customRoute;

        public String getCustomRoute() {
            return customRoute;
        }

        public void setCustomRoute(String customRoute) {
            this.customRoute = customRoute;
        }

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
}
