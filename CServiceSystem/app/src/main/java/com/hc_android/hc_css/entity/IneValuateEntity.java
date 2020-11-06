package com.hc_android.hc_css.entity;

public class IneValuateEntity {


    /**
     * code : 200
     * msg : ok
     * data : {"_suc":1,"session":{"hash":"0cd476ce85ebdbdd362ac5aed9605d74","validity":"864000000"}}
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
         * session : {"hash":"0cd476ce85ebdbdd362ac5aed9605d74","validity":"864000000"}
         */

        private int _suc;
        private SessionBean session;
        private String invitationTime;
        private boolean existence;
        private String text;
        private String state;
        private String txt;

        public String getTxt() {
            return txt;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public boolean isExistence() {
            return existence;
        }

        public void setExistence(boolean existence) {
            this.existence = existence;
        }

        public String getInvitationTime() {
            return invitationTime;
        }

        public void setInvitationTime(String invitationTime) {
            this.invitationTime = invitationTime;
        }

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

        public static class SessionBean {
            /**
             * hash : 0cd476ce85ebdbdd362ac5aed9605d74
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
    }
}
