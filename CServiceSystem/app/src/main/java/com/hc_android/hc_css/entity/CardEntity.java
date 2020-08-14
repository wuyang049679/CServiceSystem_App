package com.hc_android.hc_css.entity;

import java.util.List;

public class CardEntity {


    /**
     * code : 200
     * msg : ok
     * data : {"_suc":1,"list":[{"id":"5d8f2c3058a74e5059143767","name":"名称","order":0,"core":"name"},{"id":"5d8f2c3058a74e5059143769","name":"邮箱","order":1,"core":"email"},{"id":"5d8f2c3058a74e505914376a","name":"手机","order":2,"core":"tel"},{"id":"5dc4dbdd3eed3805fd16ca30","name":"和家家户户","order":3},{"id":"5e1c1f9beb308c6fcf584250","name":"顾客名片编辑","order":4}],"session":{"hash":"9aea53ad3a3ce35633bb57842000ccc4","validity":"864000000"}}
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
         * list : [{"id":"5d8f2c3058a74e5059143767","name":"名称","order":0,"core":"name"},{"id":"5d8f2c3058a74e5059143769","name":"邮箱","order":1,"core":"email"},{"id":"5d8f2c3058a74e505914376a","name":"手机","order":2,"core":"tel"},{"id":"5dc4dbdd3eed3805fd16ca30","name":"和家家户户","order":3},{"id":"5e1c1f9beb308c6fcf584250","name":"顾客名片编辑","order":4}]
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
             * id : 5d8f2c3058a74e5059143767
             * name : 名称
             * order : 0
             * core : name
             */

            private String id;
            private String name;
            private int order;
            private String core;

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

            public int getOrder() {
                return order;
            }

            public void setOrder(int order) {
                this.order = order;
            }

            public String getCore() {
                return core;
            }

            public void setCore(String core) {
                this.core = core;
            }
        }
    }
}
