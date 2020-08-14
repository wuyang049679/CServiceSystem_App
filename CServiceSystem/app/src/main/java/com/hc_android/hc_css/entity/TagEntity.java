package com.hc_android.hc_css.entity;

import java.util.List;

public class TagEntity {


    /**
     * code : 200
     * msg : ok
     * data : {"_suc":1,"list":[{"id":"5da957454cc93d05cae6deb7","name":"哦哦啪啪啪","color":"#00bcd4","usage":30},{"id":"5da956cb4cc93d05cae6ce87","name":"vip","color":"#795548","usage":18}],"session":{"hash":"9aea53ad3a3ce35633bb57842000ccc4","validity":"864000000"}}
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
         * list : [{"id":"5da957454cc93d05cae6deb7","name":"哦哦啪啪啪","color":"#00bcd4","usage":30},{"id":"5da956cb4cc93d05cae6ce87","name":"vip","color":"#795548","usage":18}]
         * session : {"hash":"9aea53ad3a3ce35633bb57842000ccc4","validity":"864000000"}
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
             * hash : 9aea53ad3a3ce35633bb57842000ccc4
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
             * id : 5da957454cc93d05cae6deb7
             * name : 哦哦啪啪啪
             * color : #00bcd4
             * usage : 30
             */

            private String id;
            private String name;
            private String color;
            private int usage;

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

            public String getColor() {
                return color;
            }

            public void setColor(String color) {
                this.color = color;
            }

            public int getUsage() {
                return usage;
            }

            public void setUsage(int usage) {
                this.usage = usage;
            }
        }
    }
}
