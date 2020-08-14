package com.hc_android.hc_css.contract;

import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.base.BaseView;
import com.hc_android.hc_css.entity.MessageDialogEntity;

import io.reactivex.Observable;

public interface HistoryActivityContract {
    interface Model {
        Observable<BaseEntity<MessageDialogEntity.DataBean>> getHistoryList(String condition,int limit,int skip);//历史对话查询
    }

    interface View extends BaseView<MessageDialogEntity.DataBean> {
    }

    interface Presenter {
        void pGetHistoryList(String condition,int limit,int skip);
    }
}
