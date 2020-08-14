package com.hc_android.hc_css.contract;


import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.base.BaseView;
import com.hc_android.hc_css.entity.AppUpdateEntity;
import com.hc_android.hc_css.entity.LoginEntity;
import com.hc_android.hc_css.entity.TagEntity;

import io.reactivex.Observable;

/**
 * Desccribe:
 *
 * @author Created by wuyang on 2019/9/2
 */
public interface MainActivityContract {
    interface Model {
        Observable<BaseEntity<LoginEntity.DataBean>> checkLogin(String hash);
        Observable<BaseEntity<TagEntity.DataBean>> getTagList();
        Observable<BaseEntity<AppUpdateEntity.DataBean>> appUpdate();
    }

    interface View extends BaseView<LoginEntity.DataBean> {

        void showTagList(TagEntity.DataBean dataBean);
        void showAppUpdate(AppUpdateEntity.DataBean dataBean);
    }

    interface Presenter {
        void pCheckLogin(String hash);
        void pGetTagList();
        void pAppUpdate();
    }
}
