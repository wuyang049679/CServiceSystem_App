package com.hc_android.hc_css.entity;

public class VerityEntity {


    /**
     * code : 200
     * msg : ok
     * data : {"_suc":1,"bizId":"b9dde5aa2366a92f056386ba48a933c4","result":{"RequestId":"4DD1E15D-3834-4AC4-BB22-ADFA326F9613","VerifyToken":"bca0bcd8287a46b391d5a8c69b1115e9"}}
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
         * bizId : b9dde5aa2366a92f056386ba48a933c4
         * result : {"RequestId":"4DD1E15D-3834-4AC4-BB22-ADFA326F9613","VerifyToken":"bca0bcd8287a46b391d5a8c69b1115e9"}
         */

        private int _suc;
        private String bizId;
        private ResultBean result;
        private boolean isPass;

        public boolean isPass() {
            return isPass;
        }

        public void setPass(boolean pass) {
            isPass = pass;
        }

        public int get_suc() {
            return _suc;
        }

        public void set_suc(int _suc) {
            this._suc = _suc;
        }

        public String getBizId() {
            return bizId;
        }

        public void setBizId(String bizId) {
            this.bizId = bizId;
        }

        public ResultBean getResult() {
            return result;
        }

        public void setResult(ResultBean result) {
            this.result = result;
        }

        public static class ResultBean {
            /**
             * RequestId : 4DD1E15D-3834-4AC4-BB22-ADFA326F9613
             * VerifyToken : bca0bcd8287a46b391d5a8c69b1115e9
             */

            private String RequestId;
            private String VerifyToken;

            public String getRequestId() {
                return RequestId;
            }

            public void setRequestId(String RequestId) {
                this.RequestId = RequestId;
            }

            public String getVerifyToken() {
                return VerifyToken;
            }

            public void setVerifyToken(String VerifyToken) {
                this.VerifyToken = VerifyToken;
            }
        }
    }
}
