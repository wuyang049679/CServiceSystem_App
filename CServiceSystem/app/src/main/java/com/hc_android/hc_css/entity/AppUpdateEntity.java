package com.hc_android.hc_css.entity;

public class AppUpdateEntity {


    /**
     * code : 200
     * msg : ok
     * data : {"_suc":1,"version":1,"downloadAddress":"https://pubres.aihecong.com/hecong_for_android.apk"}
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
         * version : 1
         * downloadAddress : https://pubres.aihecong.com/hecong_for_android.apk
         */

        private int _suc;
        private int version;
        private String downloadAddress;

        public int get_suc() {
            return _suc;
        }

        public void set_suc(int _suc) {
            this._suc = _suc;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }

        public String getDownloadAddress() {
            return downloadAddress;
        }

        public void setDownloadAddress(String downloadAddress) {
            this.downloadAddress = downloadAddress;
        }
    }
}
