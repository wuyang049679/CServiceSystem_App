package com.hc_android.hc_css.entity;

import java.util.List;

public class QuickGroupEntity {


    /**
     * code : 200
     * msg : ok
     * data : {"_suc":1,"grouping":[{"id":"5e09a8ddb4920c187d77c107","name":"常用的类型","type":"quick","serviceId":"5d8f2c3058a74e5059143765"},{"id":"5e09a924b4920c187d77cced","name":"不常用的","type":"quick","serviceId":"5d8f2c3058a74e5059143765"},{"id":"5e09a9a6b4920c187d77e0ad","name":"阿加大家","type":"quick","serviceId":"5d8f2c3058a74e5059143765"},{"id":"5e09a9abb4920c187d77e159","name":"叫阿三的f","type":"quick","serviceId":"5d8f2c3058a74e5059143765"},{"id":"5e09aac3b4920c187d780b45","name":"111","type":"quick","serviceId":"5d8f2c3058a74e5059143765"},{"id":"5e09aac7b4920c187d780bf5","name":"22","type":"quick","serviceId":"5d8f2c3058a74e5059143765"},{"id":"5e09aacbb4920c187d780c90","name":"33","type":"quick","serviceId":"5d8f2c3058a74e5059143765"},{"id":"5e09aaceb4920c187d780d16","name":"44","type":"quick","serviceId":"5d8f2c3058a74e5059143765"},{"id":"5e09ab33b4920c187d781d56","name":"3433","type":"quick","serviceId":"5d8f2c3058a74e5059143765"},{"id":"5e09ab37b4920c187d781dfd","name":"32323","type":"quick","serviceId":"5d8f2c3058a74e5059143765"}],"session":{"hash":"0cd476ce85ebdbdd362ac5aed9605d74","validity":"864000000"}}
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
         * grouping : [{"id":"5e09a8ddb4920c187d77c107","name":"常用的类型","type":"quick","serviceId":"5d8f2c3058a74e5059143765"},{"id":"5e09a924b4920c187d77cced","name":"不常用的","type":"quick","serviceId":"5d8f2c3058a74e5059143765"},{"id":"5e09a9a6b4920c187d77e0ad","name":"阿加大家","type":"quick","serviceId":"5d8f2c3058a74e5059143765"},{"id":"5e09a9abb4920c187d77e159","name":"叫阿三的f","type":"quick","serviceId":"5d8f2c3058a74e5059143765"},{"id":"5e09aac3b4920c187d780b45","name":"111","type":"quick","serviceId":"5d8f2c3058a74e5059143765"},{"id":"5e09aac7b4920c187d780bf5","name":"22","type":"quick","serviceId":"5d8f2c3058a74e5059143765"},{"id":"5e09aacbb4920c187d780c90","name":"33","type":"quick","serviceId":"5d8f2c3058a74e5059143765"},{"id":"5e09aaceb4920c187d780d16","name":"44","type":"quick","serviceId":"5d8f2c3058a74e5059143765"},{"id":"5e09ab33b4920c187d781d56","name":"3433","type":"quick","serviceId":"5d8f2c3058a74e5059143765"},{"id":"5e09ab37b4920c187d781dfd","name":"32323","type":"quick","serviceId":"5d8f2c3058a74e5059143765"}]
         * session : {"hash":"0cd476ce85ebdbdd362ac5aed9605d74","validity":"864000000"}
         */

        private int _suc;
        private SessionBean session;
        private List<GroupingBean> grouping;

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

        public List<GroupingBean> getGrouping() {
            return grouping;
        }

        public void setGrouping(List<GroupingBean> grouping) {
            this.grouping = grouping;
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

        public static class GroupingBean {
            /**
             * id : 5e09a8ddb4920c187d77c107
             * name : 常用的类型
             * type : quick
             * serviceId : 5d8f2c3058a74e5059143765
             */

            private String id;
            private String name;
            private String type;
            private String serviceId;
            private boolean isSelected;


            public GroupingBean(String name) {
                this.name = name;
            }

            public boolean isSelected() {
                return isSelected;
            }

            public void setSelected(boolean selected) {
                isSelected = selected;
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

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getServiceId() {
                return serviceId;
            }

            public void setServiceId(String serviceId) {
                this.serviceId = serviceId;
            }
        }
    }
}
