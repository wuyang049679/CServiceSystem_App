package com.hc_android.hc_css.contract;

import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.base.BaseView;
import com.hc_android.hc_css.entity.CustomPathEntity;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.entity.VisitorEntity;

import io.reactivex.Observable;

public interface VisitorListFragmentContract {
    interface Model {

        Observable<BaseEntity<VisitorEntity.DataBean>> getVisitorList();//访客列表
        Observable<BaseEntity<CustomPathEntity.DataBean>> getRoutes(String realtimeId, String customId,String visitorId);//用户轨迹
        Observable<BaseEntity<IneValuateEntity.DataBean>> visitorInvitation(String realtimeId);//邀请对话
        Observable<BaseEntity<IneValuateEntity.DataBean>> realtimeActive(String realtimeId);//主动对话
    }

    interface View extends BaseView<VisitorEntity.DataBean> {
        void showPathList(CustomPathEntity.DataBean dataBean);
        void showInvitation(IneValuateEntity.DataBean dataBean);
        void showActive(IneValuateEntity.DataBean dataBean);
    }

    interface Presenter {
        void pVisitorList();
        void pGetroutes(String realtimeId, String customId,String visitorId);
        void pVisitorInvitation(String realtimeId);
        void pRealtimeActive(String realtimeId);
    }
}
