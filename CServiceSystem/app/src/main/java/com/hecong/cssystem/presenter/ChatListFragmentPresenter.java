package com.hecong.cssystem.presenter;

import com.hecong.cssystem.base.BasePresenterIm;
import com.hecong.cssystem.base.RxSubscribe;
import com.hecong.cssystem.contract.ChatListFragmentContract;
import com.hecong.cssystem.entity.MessageDialogEntity;
import com.hecong.cssystem.model.ChatListFragmentModel;
import com.hecong.cssystem.utils.android.ToastUtils;

import io.reactivex.disposables.Disposable;

public class ChatListFragmentPresenter extends BasePresenterIm<ChatListFragmentContract.View> implements ChatListFragmentContract.Presenter {


    ChatListFragmentModel chatListFragmentModel;

    public ChatListFragmentPresenter() {
        chatListFragmentModel=new ChatListFragmentModel();
    }

    @Override
    public void pShowMessageDialog(int limit,int skip) {
        chatListFragmentModel.showMessageDialog(limit,skip).subscribe(new RxSubscribe<MessageDialogEntity.DataBean>() {
            @Override
            protected void onSuccess(MessageDialogEntity.DataBean messageEntity) {

                mView.showDialogList(messageEntity);
            }

            @Override
            protected void onFailed(int code, String msg) {
                mView.showNetErrorView();
                ToastUtils.showShort(msg);
            }

            @Override
            public void onSubscribe(Disposable d) {
              addSubscription(d);
            }
        });
    }

    @Override
    public void pGetDialog(String dialogId) {
        chatListFragmentModel.getDialog(dialogId).subscribe(new RxSubscribe<MessageDialogEntity.DataBean>() {
            @Override
            protected void onSuccess(MessageDialogEntity.DataBean dataBean) {
                mView.showNewDialog(dataBean);
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
    public void onConnect(Object... args) {

    }

    @Override
    public void onDisconnect(Object... args) {

    }

    @Override
    public void onConnectError(Object... args) {

    }

    @Override
    public void onConnectTimeout(Object... args) {

    }

    @Override
    public void onNewMessage(Object... args) {

    }

    @Override
    public void onNewDialog(Object... args) {

    }

    @Override
    public void onUpdateDialog(Object... args) {

    }

    @Override
    public void onLeaveDialog(Object... args) {

    }

    @Override
    public void onReception(Object... args) {

    }

    @Override
    public void onUserJoined(Object... args) {

    }

    @Override
    public void onUserLeft(Object... args) {

    }

    @Override
    public void onTyping(Object... args) {

    }

    @Override
    public void onStopTyping(Object... args) {

    }

    @Override
    public void onUserState(Object... args) {

    }

    @Override
    public void onOffLine(Object... args) {

    }

    @Override
    public void onLine(Object... args) {

    }

    @Override
    public void onLogSuc(Object... args) {

    }
}
