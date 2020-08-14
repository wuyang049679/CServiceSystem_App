package com.hc_android.hc_css.entity;

public class BusinessEntity {


    /**
     * code : 200
     * msg : ok
     * data : {"_suc":1,"result":{"businessScope":"软件设计与开发;企业信息化服务;网络工程设计施工;网站设计与开发;计算机软硬件销售;计算机技术开发、技术服务;商标、版权、著作权、专利的代理及转让;广告的设计、制作、代理、发布。(依法须经批准的项目,经相关部门批准后方可开展经营活动)","ifCreditValid":true,"expirationDate":"29991231","establishDate":"20140530","address":"陕西省西安市国家民用航天产业基地神舟四路239号2栋5层501-A04","name":"西安网极网络科技有限公司","owner":"张强","companyType":"有限责任公司(自然人投资或控股)","code":1000,"credit":"91610138397119703K","capital":"壹佰万元人民币"}}
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
         * result : {"businessScope":"软件设计与开发;企业信息化服务;网络工程设计施工;网站设计与开发;计算机软硬件销售;计算机技术开发、技术服务;商标、版权、著作权、专利的代理及转让;广告的设计、制作、代理、发布。(依法须经批准的项目,经相关部门批准后方可开展经营活动)","ifCreditValid":true,"expirationDate":"29991231","establishDate":"20140530","address":"陕西省西安市国家民用航天产业基地神舟四路239号2栋5层501-A04","name":"西安网极网络科技有限公司","owner":"张强","companyType":"有限责任公司(自然人投资或控股)","code":1000,"credit":"91610138397119703K","capital":"壹佰万元人民币"}
         */

        private int _suc;
        private ResultBean result;

        public int get_suc() {
            return _suc;
        }

        public void set_suc(int _suc) {
            this._suc = _suc;
        }

        public ResultBean getResult() {
            return result;
        }

        public void setResult(ResultBean result) {
            this.result = result;
        }

        public static class ResultBean {
            /**
             * businessScope : 软件设计与开发;企业信息化服务;网络工程设计施工;网站设计与开发;计算机软硬件销售;计算机技术开发、技术服务;商标、版权、著作权、专利的代理及转让;广告的设计、制作、代理、发布。(依法须经批准的项目,经相关部门批准后方可开展经营活动)
             * ifCreditValid : true
             * expirationDate : 29991231
             * establishDate : 20140530
             * address : 陕西省西安市国家民用航天产业基地神舟四路239号2栋5层501-A04
             * name : 西安网极网络科技有限公司
             * owner : 张强
             * companyType : 有限责任公司(自然人投资或控股)
             * code : 1000
             * credit : 91610138397119703K
             * capital : 壹佰万元人民币
             */

            private String businessScope;
            private boolean ifCreditValid;
            private String expirationDate;
            private String establishDate;
            private String address;
            private String name;
            private String owner;
            private String companyType;
            private int code;
            private String credit;
            private String capital;

            public String getBusinessScope() {
                return businessScope;
            }

            public void setBusinessScope(String businessScope) {
                this.businessScope = businessScope;
            }

            public boolean isIfCreditValid() {
                return ifCreditValid;
            }

            public void setIfCreditValid(boolean ifCreditValid) {
                this.ifCreditValid = ifCreditValid;
            }

            public String getExpirationDate() {
                return expirationDate;
            }

            public void setExpirationDate(String expirationDate) {
                this.expirationDate = expirationDate;
            }

            public String getEstablishDate() {
                return establishDate;
            }

            public void setEstablishDate(String establishDate) {
                this.establishDate = establishDate;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getOwner() {
                return owner;
            }

            public void setOwner(String owner) {
                this.owner = owner;
            }

            public String getCompanyType() {
                return companyType;
            }

            public void setCompanyType(String companyType) {
                this.companyType = companyType;
            }

            public int getCode() {
                return code;
            }

            public void setCode(int code) {
                this.code = code;
            }

            public String getCredit() {
                return credit;
            }

            public void setCredit(String credit) {
                this.credit = credit;
            }

            public String getCapital() {
                return capital;
            }

            public void setCapital(String capital) {
                this.capital = capital;
            }
        }
    }
}
