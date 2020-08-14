package com.hc_android.hc_css.contract;

import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.base.BaseView;
import com.hc_android.hc_css.entity.TagEntity;

import io.reactivex.Observable;

public interface LocalDataContract {
    interface Model {

        Observable<BaseEntity<TagEntity.DataBean>> getTagList();
    }

    interface View extends BaseView<TagEntity.DataBean> {
        void show(TagEntity.DataBean dataBean);
    }

    interface Presenter {
        void pGetTagList();
    }
}
