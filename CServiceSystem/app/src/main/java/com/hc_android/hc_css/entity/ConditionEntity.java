package com.hc_android.hc_css.entity;

import java.util.List;

public class ConditionEntity {


//    {
//        startTime: 时间戳, //对话开始时间 - 起 时间类型精确到分钟 默认查询30天内
//                endTime: 时间戳, //对话开始时间 - 止
//            endAtime: 时间戳， //对话结束时间 - 起
//        endBtime: 时间戳， //对话结束时间 - 止
//        vague: String, //快捷搜索，顾客名称、电话、名片字段
//                service: { id: String }, //查询的客服id 默认查询当前客服的对话
//        tag: [arr], //顾客标签列表，数组形式
//        ip: String, //IP地址
//                evaluate: [arr], //顾客评价条件数组，-1为无评价/0为差评/1为中评/2为好评
//        msg: String, //聊天内容模糊搜索
//                source: [arr], //接入渠道条件数组，web/link/wechat/program
//        address: { region: String, city: String }, //region 省份 city 城市
//        invalid: String, //无效对话 hide隐藏/only只看无效 不传则为显示
//                miss: String, //遗漏对话 hide隐藏/only只看遗漏 不传则为显示
//            remarks: String, //对话备注模糊搜索
//            keyWord: String, //对话来源关键词匹配搜索
//    }

    private String startTime;
    private String endTime;
    private String endAtime;
    private String endBtime;
    private String vague;
    private String ip;
    private String invalid;
    private String miss;
    private String remarks;
    private String keyWord;
    private String msg;
    private List<String> tag;
    private List<String> evaluate;
    private List<String> source;
    private MessageDialogEntity.DataBean.ListBean.CustomerBean.AddressBean address;
    private ServiceBean service;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEndAtime() {
        return endAtime;
    }

    public void setEndAtime(String endAtime) {
        this.endAtime = endAtime;
    }

    public String getEndBtime() {
        return endBtime;
    }

    public void setEndBtime(String endBtime) {
        this.endBtime = endBtime;
    }

    public String getVague() {
        return vague;
    }

    public void setVague(String vague) {
        this.vague = vague;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getInvalid() {
        return invalid;
    }

    public void setInvalid(String invalid) {
        this.invalid = invalid;
    }

    public String getMiss() {
        return miss;
    }

    public void setMiss(String miss) {
        this.miss = miss;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<String> getTag() {
        return tag;
    }

    public void setTag(List<String> tag) {
        this.tag = tag;
    }

    public List<String> getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(List<String> evaluate) {
        this.evaluate = evaluate;
    }

    public List<String> getSource() {
        return source;
    }

    public void setSource(List<String> source) {
        this.source = source;
    }

    public MessageDialogEntity.DataBean.ListBean.CustomerBean.AddressBean getAddress() {
        return address;
    }

    public void setAddress(MessageDialogEntity.DataBean.ListBean.CustomerBean.AddressBean address) {
        this.address = address;
    }

    public ServiceBean getService() {
        return service;
    }

    public void setService(ServiceBean service) {
        this.service = service;
    }

    public static class ServiceBean {
        String id;
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
