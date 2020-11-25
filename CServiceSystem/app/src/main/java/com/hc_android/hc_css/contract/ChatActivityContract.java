package com.hc_android.hc_css.contract;

import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.base.BaseView;
import com.hc_android.hc_css.entity.ChatEntity;
import com.hc_android.hc_css.entity.CustomPathEntity;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.entity.MessageDialogEntity;
import com.hc_android.hc_css.entity.MessageEntity;
import com.hc_android.hc_css.entity.QuickEntity;
import com.hc_android.hc_css.entity.SendEntity;
import com.hc_android.hc_css.entity.TokenEntity;

import io.reactivex.Observable;

public interface ChatActivityContract {
    interface Model {
        Observable<BaseEntity<CustomPathEntity.DataBean>> getRoutes(String realtimeId, String customId,String visitorId);//用户轨迹
        Observable<BaseEntity<ChatEntity.DataBean>> getChatList(String dialogId, String serviceId,int entId, int limit,int skip);//获取聊天列表
        Observable<BaseEntity<SendEntity.DataBean>> sendMsg(String dialogId, String serviceId,String customerId,String socketId,String type,String contents,String key, int entId);//发送消息
        Observable<BaseEntity<TokenEntity.DataBean>> getToken(String type);//获取七牛token
        Observable<BaseEntity<TokenEntity.DataBean>> sendRead(String dialogId,String customerId,String mids,int entId);//发送消息已读
        Observable<BaseEntity<IneValuateEntity.DataBean>> sendIneValuate(String dialogId);//发送评价邀请
        Observable<BaseEntity<IneValuateEntity.DataBean>> msgUndo(String msgId,String serviceId,int entId);//消息撤回
        Observable<BaseEntity<QuickEntity.DataBean>> getQuickList(String serviceId, boolean team);//快捷回复列表
        Observable<BaseEntity<IneValuateEntity.DataBean>> quickUse(String id);//快捷回复使用
        Observable<BaseEntity<IneValuateEntity.DataBean>> reopen(String key,String customerId, String historyId, String source, String message); //历史对话重开
        Observable<BaseEntity<IneValuateEntity.DataBean>> realtimeActive(String realtimeId);//主动对话
        Observable<BaseEntity<IneValuateEntity.DataBean>> pushwechat(String key,String customerId, String historyId, String source, String message,String type, String contents, String entId);//微信小程序历史对话重开消息发送

    }

    interface View extends BaseView<CustomPathEntity.DataBean> {
        void showPathList(CustomPathEntity.DataBean dataBean);
        void showChatList(ChatEntity.DataBean dataBean);
        void showSendSuccess(SendEntity.DataBean dataBean);
        void msgUnEnable(String txt, String key);
        void showSendFailed(String msg,String key);
        void showToken(TokenEntity.DataBean dataBean,MessageEntity.MessageBean meesagebean);
        void showIneValuate(IneValuateEntity.DataBean dataBean);
        void showMsgUndo(IneValuateEntity.DataBean dataBean);
        void showMsgUndoFiled(String msg,String key);
        void showQuickList(QuickEntity.DataBean dataBean);
        void showReOpen(IneValuateEntity.DataBean dataBean);
        void showActive(IneValuateEntity.DataBean dataBean);
        void showPuhWechat(IneValuateEntity.DataBean dataBean);

    }

    interface Presenter {
        void pGetroutes(String realtimeId, String customId,String visitorId);
        void pChatList(String dialogId, String serviceId,int entId, int limit,int skip);
        void pSendMsg(String dialogId, String serviceId,String customerId,String socketId,String type,String contents,String key, int entId);
        void pGetToken(MessageDialogEntity.DataBean.ListBean itembean, MessageEntity.MessageBean messageBean, String type);
        void pSendRead(String dialogId,String customerId,String mids,int entId);
        void pSendIneValuate(String dialogId);
        void pMsgUndo(String msgId,String serviceId,int entId);
        void pGetQuickList(String serviceId,boolean team);
        void pQuickUse(String id);
        void pReopen(String key,String customerId, String historyId, String source, String message);
        void pRealtimeActive(String realtimeId);
        void pPushwechat(String key,String customerId, String historyId, String source, String message,String type, String contents, String entId);
    }
}
