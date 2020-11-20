package com.hc_android.hc_css.presenter;

import android.annotation.SuppressLint;
import android.util.Log;

import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.base.BasePresenterIm;
import com.hc_android.hc_css.base.RxSubscribe;
import com.hc_android.hc_css.contract.ChatListFragmentContract;
import com.hc_android.hc_css.entity.LoginEntity;
import com.hc_android.hc_css.entity.MessageDialogEntity;
import com.hc_android.hc_css.entity.ReceptionEntity;
import com.hc_android.hc_css.entity.TeamEntity;
import com.hc_android.hc_css.model.ChatListFragmentModel;
import com.hc_android.hc_css.utils.JsonParseUtils;
import com.hc_android.hc_css.utils.android.ToastUtils;
import com.hc_android.hc_css.utils.thread.RetryWithDelay;
import com.transitionseverywhere.extra.Translation;

import java.util.concurrent.TimeUnit;

import cc.shinichi.library.tool.ui.ToastUtil;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class ChatListFragmentPresenter extends BasePresenterIm<ChatListFragmentContract.View> implements ChatListFragmentContract.Presenter {


    ChatListFragmentModel chatListFragmentModel;

    public ChatListFragmentPresenter() {
        chatListFragmentModel = new ChatListFragmentModel();
    }

    @Override
    public void pShowMessageDialog(int limit, int skip) {
        chatListFragmentModel.showMessageDialog(limit, skip).subscribe(new RxSubscribe<MessageDialogEntity.DataBean>() {
            @Override
            protected void onSuccess(MessageDialogEntity.DataBean messageEntity) {

                mView.showDialogList(messageEntity);
            }

            @Override
            protected void onFailed(int code, String msg) {
                mView.showDataError(msg);
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscription(d);
            }
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public void pGetDialog(String dialogId) {



//        //防止获取新对话信息是获取数据失败
//        Observable<BaseEntity<MessageDialogEntity.DataBean>> ob1 = chatListFragmentModel.getDialog(dialogId);
//        Observable<BaseEntity<MessageDialogEntity.DataBean>> ob2 = chatListFragmentModel.getDialog(dialogId);
//        Observable<BaseEntity<MessageDialogEntity.DataBean>> ob3 = chatListFragmentModel.getDialog(dialogId);
//
////        ob1.retryWhen(new RetryWithDelay(3,3000))
////                .subscribeOn(Schedulers.io())
////                .observeOn(AndroidSchedulers.mainThread())
////                .subscribe(new RxSubscribe<MessageDialogEntity.DataBean>() {
////                    @Override
////                    protected void onSuccess(MessageDialogEntity.DataBean dataBean) {
////                        mView.showNewDialog(dataBean);
////                    }
////
////                    @Override
////                    protected void onFailed(int code, String msg) {
////                        ToastUtils.showShort(msg);
////                        Observable.error(new Throwable());
////                    }
////
////                    @Override
////                    public void onSubscribe(Disposable d) {
////
////                    }
////                });
//
////
//        ob1.subscribeOn(Schedulers.io())               // （初始被观察者）切换到IO线程进行网络请求1
//                .observeOn(AndroidSchedulers.mainThread())  // （新观察者）切换到主线程 处理网络请求1的结果
//                .doOnNext(new Consumer<BaseEntity<MessageDialogEntity.DataBean>>() {
//                    @Override
//                    public void accept(BaseEntity<MessageDialogEntity.DataBean> dataBeanBaseEntity) throws Exception {
//                        if (dataBeanBaseEntity.getData().get_suc()==1) {
//                        mView.showNewDialog(dataBeanBaseEntity.getData());
//                        }
//                    }
//                })
//
//                .delay(1000, TimeUnit.MILLISECONDS)
//                .observeOn(Schedulers.io())
//                .concatMap(new Function<BaseEntity<MessageDialogEntity.DataBean>, Observable<BaseEntity<MessageDialogEntity.DataBean>>>() {
//                    @Override
//                    public Observable<BaseEntity<MessageDialogEntity.DataBean>> apply(BaseEntity<MessageDialogEntity.DataBean> dataBeanBaseEntity) throws Exception {
//                        if (dataBeanBaseEntity.getData().get_suc()==1){
//                            return null;
//                        }
//                        return ob2;
//                    }
//                })
//                .delay(1000, TimeUnit.MILLISECONDS)
//                .observeOn(Schedulers.io())
//                .concatMap(new Function<BaseEntity<MessageDialogEntity.DataBean>, Observable<BaseEntity<MessageDialogEntity.DataBean>>>() {
//                    @Override
//                    public Observable<BaseEntity<MessageDialogEntity.DataBean>> apply(BaseEntity<MessageDialogEntity.DataBean> dataBeanBaseEntity) throws Exception {
//
//                        if (dataBeanBaseEntity.getData().get_suc()==1){
//                            mView.showNewDialog(dataBeanBaseEntity.getData());
//                            return null;
//                        }
//                        return ob3;
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())  // （初始观察者）切换到主线程 处理网络请求2的结果
//                .subscribe(new Consumer<BaseEntity<MessageDialogEntity.DataBean>>() {
//                    @Override
//                    public void accept(BaseEntity<MessageDialogEntity.DataBean> dataBeanBaseEntity) throws Exception {
//                        if (dataBeanBaseEntity.getData().get_suc()==1) {
//                            mView.showNewDialog(dataBeanBaseEntity.getData());
//                        }
//                    }
//
//                  }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        System.out.println("登录失败");
//                    }
//                });
////

        //加入重试机制3次2s一次
        chatListFragmentModel.getDialog(dialogId).retryWhen(new RetryWithDelay(3,2000 )).subscribe(new RxSubscribe<MessageDialogEntity.DataBean>() {
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
    public void pEndDialog(String idList, String offEnd, String autoEnd) {
        chatListFragmentModel.endDialog(idList, offEnd, autoEnd).subscribe(new RxSubscribe<ReceptionEntity.DataBean>() {
            @Override
            protected void onSuccess(ReceptionEntity.DataBean dataBean) {
                mView.showEndDialog(dataBean);
            }

            @Override
            protected void onFailed(int code, String msg) {
//               mView.showNetErrorView();
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscription(d);
            }
        });
    }

    @Override
    public void pGetTeamList() {
        chatListFragmentModel.getTeamList().subscribe(new RxSubscribe<TeamEntity.DataBean>() {
            @Override
            protected void onSuccess(TeamEntity.DataBean dataBean) {
                mView.showTeamList(dataBean);
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
    public void pCheckLogin(String hash) {
        chatListFragmentModel.checkLogin(hash).subscribe(new RxSubscribe<LoginEntity.DataBean>() {
            @Override
            protected void onSuccess(LoginEntity.DataBean loginEntity) {
                mView.showCheckHash(loginEntity);
            }

            @Override
            protected void onFailed(int code, String msg) {
                mView.showDataError(msg);
            }

            @Override
            public void onSubscribe(Disposable d) {
                addSubscription(d);
            }
        });
    }
}
