package com.hc_android.hc_css.entity;

public class TokenEntity {


    /**
     * code : 200
     * msg : ok
     * data : {"_suc":1,"token":"59lrv-uXrd_xFlaATEbuNFEMatiu_Mtiy0dO-6Mf:IjKvfZcTnRHoj1N5oXUw3yswmVc=:eyJmc2l6ZUxpbWl0IjoxMDQ4NTc2MCwicmV0dXJuQm9keSI6IntcbiAgICAgIFwia2V5XCI6XCIkKGtleSlcIixcbiAgICAgIFwiaGFzaFwiOlwiJChldGFnKVwiLFxuICAgICAgXCJuYW1lXCI6IFwiJChmbmFtZSlcIixcbiAgICAgIFwic2l6ZVwiOiBcIiQoZnNpemUpXCIsXG4gICAgICBcInR5cGVcIjogXCIkKG1pbWVUeXBlKVwiLFxuICAgICAgXCJ3XCI6IFwiJChpbWFnZUluZm8ud2lkdGgpXCIsXG4gICAgICBcImhcIjogXCIkKGltYWdlSW5mby5oZWlnaHQpXCJcbiAgICB9Iiwic2F2ZUtleSI6IiQoZXRhZykkKGV4dCkiLCJzY29wZSI6ImJ1Y2tldC1pbWciLCJkZWFkbGluZSI6MTU3NzE1NTMzM30="}
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
         * token : 59lrv-uXrd_xFlaATEbuNFEMatiu_Mtiy0dO-6Mf:IjKvfZcTnRHoj1N5oXUw3yswmVc=:eyJmc2l6ZUxpbWl0IjoxMDQ4NTc2MCwicmV0dXJuQm9keSI6IntcbiAgICAgIFwia2V5XCI6XCIkKGtleSlcIixcbiAgICAgIFwiaGFzaFwiOlwiJChldGFnKVwiLFxuICAgICAgXCJuYW1lXCI6IFwiJChmbmFtZSlcIixcbiAgICAgIFwic2l6ZVwiOiBcIiQoZnNpemUpXCIsXG4gICAgICBcInR5cGVcIjogXCIkKG1pbWVUeXBlKVwiLFxuICAgICAgXCJ3XCI6IFwiJChpbWFnZUluZm8ud2lkdGgpXCIsXG4gICAgICBcImhcIjogXCIkKGltYWdlSW5mby5oZWlnaHQpXCJcbiAgICB9Iiwic2F2ZUtleSI6IiQoZXRhZykkKGV4dCkiLCJzY29wZSI6ImJ1Y2tldC1pbWciLCJkZWFkbGluZSI6MTU3NzE1NTMzM30=
         */

        private int _suc;
        private String token;

        public int get_suc() {
            return _suc;
        }

        public void set_suc(int _suc) {
            this._suc = _suc;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }
}
