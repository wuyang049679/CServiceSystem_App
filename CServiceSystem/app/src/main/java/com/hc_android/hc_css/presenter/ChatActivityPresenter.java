package com.hc_android.hc_css.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.base.BasePresenterIm;
import com.hc_android.hc_css.base.RxSubscribe;
import com.hc_android.hc_css.contract.ChatActivityContract;
import com.hc_android.hc_css.entity.ChatEntity;
import com.hc_android.hc_css.entity.CustomPathEntity;
import com.hc_android.hc_css.entity.FileEntity;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.entity.MessageDialogEntity;
import com.hc_android.hc_css.entity.MessageEntity;
import com.hc_android.hc_css.entity.SendEntity;
import com.hc_android.hc_css.entity.TokenEntity;
import com.hc_android.hc_css.model.ChatActivityModel;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.JsonParseUtils;
import com.hc_android.hc_css.utils.android.ToastUtils;
import com.hc_android.hc_css.utils.android.app.CacheData;
import com.hc_android.hc_css.utils.android.image.TintUtils;
import com.hc_android.hc_css.utils.android.image.UploadFileUtils;
import com.hc_android.hc_css.utils.socket.MessageEvent;
import com.hc_android.hc_css.utils.socket.MessageEventType;
import com.hc_android.hc_css.utils.thread.RetryWithDelay;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.disposables.Disposable;

import static com.hc_android.hc_css.utils.Constant.UI_FRESH;
import static com.hc_android.hc_css.utils.Constant._IMAGE;
import static com.hc_android.hc_css.utils.Constant._TEXT;
import static com.hc_android.hc_css.utils.Constant._VIDEO;
import static com.hc_android.hc_css.utils.Constant._VOICE;


public class ChatActivityPresenter extends BasePresenterIm<ChatActivityContract.View>implements ChatActivityContract.Presenter {

    ChatActivityModel chatActivityModel;

    public ChatActivityPresenter() {
        chatActivityModel=new ChatActivityModel();
    }

    @Override
    public void pGetroutes(String realtimeId, String customId,String visitorId) {
        chatActivityModel.getRoutes(realtimeId,customId,visitorId).subscribe(new RxSubscribe<CustomPathEntity.DataBean>() {
            @Override
            protected void onSuccess(CustomPathEntity.DataBean dataBean) {
                if (mView!=null) mView.showPathList(dataBean);
            }
            @Override
            protected void onFailed(int code, String msg) {
                ToastUtils.showShort(msg);
            }

            @Override
            public void onSubscribe(Disposable d) {
               addSubscription(d);
            }
        });
    }

    @Override
    public void pChatList(String dialogId, String serviceId, int entId, int limit, int skip) {
        chatActivityModel.getChatList(dialogId,serviceId,entId,limit,skip).subscribe(new RxSubscribe<ChatEntity.DataBean>() {
            @Override
            protected void onSuccess(ChatEntity.DataBean dataBean) {
                if (mView!=null) mView.showContentView();
                if (mView!=null)mView.showChatList(dataBean);
            }

            @Override
            protected void onFailed(int code, String msg) {

                if (mView!=null) mView.showNetErrorView();
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscription(d);
            }
        });
    }

    @Override
    public void pSendMsg(String dialogId, String serviceId, String customerId, String socketId, String type, String contents, String key, int entId) {
        chatActivityModel.sendMsg(dialogId,serviceId,customerId,socketId,type,contents,key,entId).subscribe(new RxSubscribe<SendEntity.DataBean>() {
            @Override
            protected void onSuccess(SendEntity.DataBean dataBean) {
                if (dataBean.get_suc()==1){
                    if (mView!=null) {
                      Log.i("wy_activity", "key:onSuccess" + dataBean.getKey());
                        mView.showSendSuccess(dataBean);
                    }else {
                        Log.i("wy_activity", "界面关闭key:onSuccess" + dataBean.getKey());
                        //界面关闭出现mView为null
                        MessageEntity.MessageBean messageBean=new MessageEntity.MessageBean();
                        messageBean.setId(dataBean.getId());
                        messageBean.setKey(dataBean.getKey());
                        messageBean.setSendState(null);
                        CacheData.updateCache(dialogId,messageBean);//通知对话列表刷新
                        MessageEntity message = new MessageEntity();
                        message.setDialogId(dialogId);
                        message.setAct(UI_FRESH);
                        message.setMessage(messageBean);
                        MessageEvent event = new MessageEvent(MessageEventType.EventMessage, message);
                        EventBus.getDefault().postSticky(event);
                    }
                }
                if (dataBean.get_suc()==0){
                    if (mView!=null){mView.msgUnEnable(dataBean.getTxt(),key);}
                    else {
                        MessageEntity.MessageBean messageBean=new MessageEntity.MessageBean();
                        messageBean.setId(dataBean.getId());
                        messageBean.setSendState(null);
                        messageBean.setKey(dataBean.getKey());
                        messageBean.setItemType(Constant.CHAT_CENTER);
                        messageBean.setContents(dataBean.getTxt());
                        messageBean.setSendState(null);
                        messageBean.setType(_TEXT);
                        CacheData.updateCache(dialogId,messageBean);
                    }
                }
            }

            @Override
            protected void onFailed(int code, String msg) {
                if (mView!=null){mView.showSendFailed(msg,key);}
                else {
                    MessageEntity.MessageBean messageBean=new MessageEntity.MessageBean();
                    messageBean.setSendState(Constant._ISFAILED);
                    messageBean.setKey(key);
                    CacheData.updateCache(dialogId,messageBean);
                }
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscription(d);
            }
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public  void pGetToken(MessageDialogEntity.DataBean.ListBean itembean, MessageEntity.MessageBean messageBean, String type) {

        chatActivityModel.getToken(type).subscribe(new RxSubscribe<TokenEntity.DataBean>() {
            @Override
            protected void onSuccess(TokenEntity.DataBean dataBean) {
                if (mView != null) {//如何界面关闭就直接访问如果没有就回调
                    mView.showToken(dataBean, messageBean);
                } else {
                    UploadFileUtils.getInstance().upload(dataBean.getToken(), messageBean, (s, responseInfo, jsonObject) -> {
                        if (responseInfo.isOK()) {
                            try {
                                String content = null;
                                String fileKey = jsonObject.getString("key");
                                String type = messageBean.getType();
                                FileEntity fileEntity = JsonParseUtils.parseToObject(messageBean.getContents(), FileEntity.class);
                                switch (type) {
                                    case _IMAGE:
                                        FileEntity.ImageEntity imageEntity = new FileEntity.ImageEntity();
                                        imageEntity.setHeight(fileEntity.getHeight());
                                        imageEntity.setWidth(fileEntity.getWidth());
                                        imageEntity.setName(fileEntity.getName());
                                        imageEntity.setPicUrl(fileKey);
                                        imageEntity.setType(fileEntity.getType());
                                        imageEntity.setSize(fileEntity.getSize());
                                        imageEntity.setBucket("msgimg");
                                        content = JsonParseUtils.parseToJson(imageEntity);
                                        break;
                                    case _VIDEO:
                                        FileEntity.VideoEntity videoEntity = new FileEntity.VideoEntity();
                                        FileEntity.VideoEntity.ThumbImgBean thumbImgBean = new FileEntity.VideoEntity.ThumbImgBean();
                                        thumbImgBean.setHeight(fileEntity.getHeight());
                                        thumbImgBean.setWidth(fileEntity.getWidth());
                                        videoEntity.setThumbImg(thumbImgBean);
                                        videoEntity.setFileUrl(fileKey);
                                        videoEntity.setName(fileEntity.getName());
                                        videoEntity.setSize(fileEntity.getSize());
                                        videoEntity.setType(fileEntity.getType());
                                        content = JsonParseUtils.parseToJson(videoEntity);
                                        break;
                                    case _VOICE:
                                        FileEntity.VoiceEntity voiceEntity = new FileEntity.VoiceEntity();
                                        voiceEntity.setType(fileEntity.getType());
                                        voiceEntity.setDuration(fileEntity.getDuration() + "");
                                        voiceEntity.setKey(fileKey);
                                        voiceEntity.setSize(fileEntity.getSize());
                                        content = JsonParseUtils.parseToJson(voiceEntity);
                                        break;
                                }
                                if (content != null) {
                                    pSendMsg(itembean.getId(), BaseApplication.getUserBean().getId(), itembean.getCustomerId(), BaseApplication.getUserEntity().getSocketId(), type, content, messageBean.getKey(), BaseApplication.getUserBean().getEntId());
                                }
                            }catch (Exception e){}
                            }

                    });
                }
            }

            @Override
            protected void onFailed(int code, String msg) {
                if (mView!=null)mView.showSendFailed(msg,messageBean.getKey());
            }

            @Override
            public void onNext(BaseEntity<TokenEntity.DataBean> baseModel) {
                super.onNext(baseModel);
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscription(d);
            }
        });
    }

    @Override
    public void pSendRead(String dialogId, String customerId, String mids, int entId) {
        chatActivityModel.sendRead(dialogId,customerId,mids,entId).subscribe(new RxSubscribe<TokenEntity.DataBean>() {
            @Override
            protected void onSuccess(TokenEntity.DataBean dataBean) {

            }

            @Override
            protected void onFailed(int code, String msg) {

            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscription(d);
            }
        });
    }

    @Override
    public void pSendIneValuate(String dialogId) {
        chatActivityModel.sendIneValuate(dialogId).subscribe(new RxSubscribe<IneValuateEntity.DataBean>() {
            @Override
            protected void onSuccess(IneValuateEntity.DataBean dataBean) {
                if (mView!=null)mView.showIneValuate(dataBean);
            }

            @Override
            protected void onFailed(int code, String msg) {
                ToastUtils.showShort(msg);
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscription(d);
            }
        });
    }

    @Override
    public void pMsgUndo(String msgId, String serviceId, int entId) {
        chatActivityModel.msgUndo(msgId,serviceId,entId).subscribe(new RxSubscribe<IneValuateEntity.DataBean>() {
            @Override
            protected void onSuccess(IneValuateEntity.DataBean dataBean) {
                if (dataBean.get_suc()==1){
                    if (mView!=null) mView.showMsgUndo(dataBean);
                }
                if (dataBean.get_suc()==0){
                    if (mView!=null) mView.showMsgUndoFiled("无法撤回消息",null);
                }
            }

            @Override
            protected void onFailed(int code, String msg) {
                ToastUtils.showShort(msg);
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscription(d);
            }
        });
    }


}
