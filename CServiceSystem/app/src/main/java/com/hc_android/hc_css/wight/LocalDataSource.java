package com.hc_android.hc_css.wight;

import com.google.gson.Gson;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.entity.MessageDialogEntity;
import com.hc_android.hc_css.entity.MessageEntity;
import com.hc_android.hc_css.entity.QuickEntity;
import com.hc_android.hc_css.entity.ScreenSaveEntity;
import com.hc_android.hc_css.entity.TagEntity;
import com.hc_android.hc_css.entity.TeamEntity;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.JsonParseUtils;
import com.hc_android.hc_css.utils.JsonUtils;
import com.hc_android.hc_css.utils.android.SharedPreferencesUtils;
import com.hc_android.hc_css.utils.android.app.CacheData;

import java.util.ArrayList;
import java.util.List;

public class LocalDataSource  {


    private static List<MessageDialogEntity.DataBean.ListBean> NOTLIST;//未接待的集合
    private static List<MessageDialogEntity.DataBean.ListBean> TEAMLIST;//同事的集合
    private static MessageDialogEntity.DataBean.ListBean ITEMBEAN;//点击单个对话聊天对象
    private static List<TeamEntity.DataBean.ListBean> TEAMSERLIST;//同事成员列表
    private static List<QuickEntity.DataBean.ListBean> QUICKELIST;//快捷的列表我的
    private static List<QuickEntity.DataBean.ListBean> QUICKETEAMLIST;//快捷的列表团队的
    private static List<TagEntity.DataBean.ListBean> TAGLIST;//全局的tag列表
    private static ScreenSaveEntity SCREEN_LIST;//筛选条件保存

    /**
     *
     * 获取聊天消息
     * @param dialogId
     * @return
     */
    public static List<MessageEntity.MessageBean> getChatList(String dialogId) {

        return CacheData.getCacheList(dialogId);
    }

    public static List<MessageDialogEntity.DataBean.ListBean> getNOTLIST() {
        String quicketeamlist = (String) SharedPreferencesUtils.getParam("NOTLIST", "");
        return JsonParseUtils.parseToPureList(quicketeamlist,MessageDialogEntity.DataBean.ListBean.class);
    }


    public static void setNOTLIST(List<MessageDialogEntity.DataBean.ListBean> NOTLIST) {
        Gson gson=new Gson();
        String s = gson.toJson(NOTLIST);
        SharedPreferencesUtils.setParam("NOTLIST",s);

    }
    public static List<QuickEntity.DataBean.ListBean> getQUICKETEAMLIST() {
        String quicketeamlist = (String) SharedPreferencesUtils.getParam("QUICKETEAMLIST"+BaseApplication.getUserBean().getId(), "");
        return JsonParseUtils.parseToPureList(quicketeamlist,QuickEntity.DataBean.ListBean.class);
    }

    public static void setQUICKETEAMLIST(List<QuickEntity.DataBean.ListBean> QUICKETEAMLIST) {
        Gson gson=new Gson();
        String s = gson.toJson(QUICKETEAMLIST);
        SharedPreferencesUtils.setParam("QUICKETEAMLIST"+BaseApplication.getUserBean().getId(),s);

    }

    public static List<QuickEntity.DataBean.ListBean> getQUICKELIST() {
        String quicketeamlist = (String) SharedPreferencesUtils.getParam("QUICKELIST"+BaseApplication.getUserBean().getId(), "");
        return JsonParseUtils.parseToPureList(quicketeamlist,QuickEntity.DataBean.ListBean.class);
    }

    public static void setQUICKELIST(List<QuickEntity.DataBean.ListBean> QUICKELIST) {
        Gson gson=new Gson();
        String s = gson.toJson(QUICKELIST);
        SharedPreferencesUtils.setParam("QUICKELIST"+BaseApplication.getUserBean().getId(),s);

    }

    public static List<TeamEntity.DataBean.ListBean> getTEAMSERLIST() {
        String quicketeamlist = (String) SharedPreferencesUtils.getParam("TEAMSERLIST", "");
        return JsonParseUtils.parseToPureList(quicketeamlist,TeamEntity.DataBean.ListBean.class);
    }

    public static void setTEAMSERLIST(List<TeamEntity.DataBean.ListBean> TEAMSERLIST) {
        Gson gson=new Gson();
        String s = gson.toJson(TEAMSERLIST);
        SharedPreferencesUtils.setParam("TEAMSERLIST",s);
    }


    public static List<MessageDialogEntity.DataBean.ListBean> getTEAMLIST() {
        String quicketeamlist = (String) SharedPreferencesUtils.getParam("TEAMLIST", "");
        return JsonParseUtils.parseToPureList(quicketeamlist,MessageDialogEntity.DataBean.ListBean.class);
    }

    public static void setTEAMLIST(List<MessageDialogEntity.DataBean.ListBean> TEAMLIST) {
        Gson gson=new Gson();
        String s1 = gson.toJson(TEAMLIST);
        SharedPreferencesUtils.setParam("TEAMLIST",s1);

    }

    public static MessageDialogEntity.DataBean.ListBean getITEMBEAN() {
        String quicketeamlist = (String) SharedPreferencesUtils.getParam("ITEMBEAN", "");
        return JsonParseUtils.parseToObject(quicketeamlist,MessageDialogEntity.DataBean.ListBean.class);
    }

    public static void setITEMBEAN(MessageDialogEntity.DataBean.ListBean ITEMBEAN) {
        String s = JsonParseUtils.parseToJson(ITEMBEAN);
        SharedPreferencesUtils.setParam("ITEMBEAN",s);
    }

    public static List<TagEntity.DataBean.ListBean> getTAGLIST() {
        String quicketeamlist = (String) SharedPreferencesUtils.getParam("TAGLIST", "");
        return JsonParseUtils.parseToPureList(quicketeamlist,TagEntity.DataBean.ListBean.class);
    }

    public static void setTAGLIST(List<TagEntity.DataBean.ListBean> TAGLIST) {
        Gson gson=new Gson();
        String s = gson.toJson(TAGLIST);
        SharedPreferencesUtils.setParam("TAGLIST",s);
    }
    public static ScreenSaveEntity getScreenList() {
        String quicketeamlist = (String) SharedPreferencesUtils.getParam("SCREENLIST" +BaseApplication.getUserBean().getId(), "");
        return JsonParseUtils.parseToObject(quicketeamlist,ScreenSaveEntity.class);
    }

    public static void setScreenList(ScreenSaveEntity screenList) {
        String s = JsonParseUtils.parseToJson(screenList);
        SharedPreferencesUtils.setParam("SCREENLIST"+ BaseApplication.getUserBean().getId(),s);//绑定客服id，防止条件错乱
    }

    /**
     * 聊天记录数据处理
     *
     * @param oldList
     * @param newList
     * @return
     */
    public static List<MessageEntity.MessageBean> getChatList(List<MessageEntity.MessageBean> oldList, List<MessageEntity.MessageBean> newList, MessageDialogEntity.DataBean.ListBean itembean,boolean isMessage) {
        List<MessageEntity.MessageBean> messageList = new ArrayList<>();
        long timeState = 0;
        //记录标记时间
        if (!isMessage) {//第一当oldList为长度为0时直接使用新数据
            timeState = newList.get(newList.size()-1).getTime();
            messageList.add(addItemTime(newList.get(newList.size()-1)));
        }else {

            for (int i = oldList.size() - 1; i >= 0; i--) {
                if (oldList.get(i).getType() .equals(Constant._TIME)) {
                    timeState = oldList.get(i).getTime();
                    break;
                }
            }
        }
        for (int i = newList.size() - 1; i >= 0; i--) {
            MessageEntity.MessageBean messageBean = newList.get(i);
            messageBean.setListBean(itembean);
            if (messageBean.getTime() - timeState >= (1000 * 60 * 5)) {
                timeState = messageBean.getTime();//更新时间标记
                messageList.add(addItemTime(messageBean));//添加一条时间消息
            }
            String sendType = messageBean.getSendType();
            if (sendType!=null) {
                if (sendType.equals("service")) {
                    messageBean.setItemType(Constant.CHAT_RIGHT);
                }
                if (sendType.equals("customer")) {
                    messageBean.setItemType(Constant.CHAT_LEFT);
                }
                if (sendType.equals("system")) {
                    messageBean.setItemType(Constant.CHAT_CENTER);
                }
                if (messageBean.getType()==null){
                    messageBean.setType("text");
                }
                if (messageBean.getType().equals(Constant._FORM)) {
                    messageBean.setItemType(Constant.CHAT_CENTER);
                }
                if (messageBean.getOneway() != null && messageBean.getOneway().equals("customer")) {
                    messageBean.setItemType(Constant.CHAT_CENTER);
                }
                if (messageBean.isUndo()) {
                    messageBean.setItemType(Constant.CHAT_CENTER);
                }
            }
//            if (messageBean.getType().equals(Constant._FORM)){
//                messageBean.setItemType(Constant.CHAT_CENTER);
////                if (messageBean.getSendType().equals("service")){
////                    messageBean.setType(_SYSTEM);//顾客已评价
////                }
//            }
//            if (messageBean.getSendType().equals("system")) {
//                messageBean.setItemType(Constant.CHAT_CENTER);
////                if (messageBean.getOneway() != null && messageBean.getOneway().equals("service")&&!messageBean.getType().equals(Constant._FORM)) {
////                    messageBean.setType(_SYSTEM);//顾客已评价
////                }
//            } else if (messageBean.getSendType().equals("service")&&!messageBean.getType().equals(_SYSTEM)) {
//                messageBean.setItemType(Constant.CHAT_RIGHT);
//            } else if (messageBean.getSendType().equals("customer")){
//                messageBean.setItemType(Constant.CHAT_LEFT);
//            }


            //如果时系统消息又是专门发给顾客的，而且时评价则不添加
//            if (messageBean.getOneway()!=null&&messageBean.getOneway().equals("customer")&&messageBean.isSystem()&&messageBean.getType()!=null&&messageBean.getType().equals("evaluate")){}else {
            messageList.add(messageBean);
//            }
        }
        return messageList;
    }

    /**
     * 添加时间列表,超过5分钟显示一个
     *
     * @param listBean
     */
    private static MessageEntity.MessageBean addItemTime(MessageEntity.MessageBean listBean) {
        MessageEntity.MessageBean bean = new MessageEntity.MessageBean();
        bean.setItemType(Constant.CHAT_CENTER);
        bean.setType(Constant._TIME);
        bean.setTime(listBean.getTime());
        return bean;
    }



}
