package com.hc_android.hc_css.entity;

import java.util.List;

public class CustomPathEntity {


    /**
     * code : 200
     * msg : ok
     * data : {"_suc":1,"item":{"routes":[{"_id":"5dc4c78d3eed3805fd1362f2","url":"https://pubres.aihecong.com/testchat.html?entId=15438&repeat=1","title":"合从对话测试","customerId":"5dc4c78d3eed3805fd1362fd","resTime":5576,"addtime":1573177229285}],"id":"5dc4c78d3eed3805fd1362fd"},"session":{"hash":"b9ec43ad793198347e0c9770153b035a","validity":"864000000"}}
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
         * item : {"routes":[{"_id":"5dc4c78d3eed3805fd1362f2","url":"https://pubres.aihecong.com/testchat.html?entId=15438&repeat=1","title":"合从对话测试","customerId":"5dc4c78d3eed3805fd1362fd","resTime":5576,"addtime":1573177229285}],"id":"5dc4c78d3eed3805fd1362fd"}
         * session : {"hash":"b9ec43ad793198347e0c9770153b035a","validity":"864000000"}
         */

        private int _suc;
        private ItemBean item;
        private SessionBean session;

        public int get_suc() {
            return _suc;
        }

        public void set_suc(int _suc) {
            this._suc = _suc;
        }

        public ItemBean getItem() {
            return item;
        }

        public void setItem(ItemBean item) {
            this.item = item;
        }

        public SessionBean getSession() {
            return session;
        }

        public void setSession(SessionBean session) {
            this.session = session;
        }

        public static class ItemBean {
            /**
             * routes : [{"_id":"5dc4c78d3eed3805fd1362f2","url":"https://pubres.aihecong.com/testchat.html?entId=15438&repeat=1","title":"合从对话测试","customerId":"5dc4c78d3eed3805fd1362fd","resTime":5576,"addtime":1573177229285}]
             * id : 5dc4c78d3eed3805fd1362fd
             */

            private String id;
            private List<RoutesBean> routes;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public List<RoutesBean> getRoutes() {
                return routes;
            }

            public void setRoutes(List<RoutesBean> routes) {
                this.routes = routes;
            }

            public static class RoutesBean {
                /**
                 * _id : 5dc4c78d3eed3805fd1362f2
                 * url : https://pubres.aihecong.com/testchat.html?entId=15438&repeat=1
                 * title : 合从对话测试
                 * customerId : 5dc4c78d3eed3805fd1362fd
                 * resTime : 5576
                 * addtime : 1573177229285
                 */

                private String _id;
                private String url;
                private String title;
                private String customerId;
                private long resTime;
                private long addtime;


                public RoutesBean( String title,String url, long addtime) {
                    this.url = url;
                    this.title = title;
                    this.addtime = addtime;
                }

                public String get_id() {
                    return _id;
                }

                public void set_id(String _id) {
                    this._id = _id;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getCustomerId() {
                    return customerId;
                }

                public void setCustomerId(String customerId) {
                    this.customerId = customerId;
                }

                public long getResTime() {
                    return resTime;
                }

                public void setResTime(long resTime) {
                    this.resTime = resTime;
                }

                public long getAddtime() {
                    return addtime;
                }

                public void setAddtime(long addtime) {
                    this.addtime = addtime;
                }
            }
        }

        public static class SessionBean {
            /**
             * hash : b9ec43ad793198347e0c9770153b035a
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
