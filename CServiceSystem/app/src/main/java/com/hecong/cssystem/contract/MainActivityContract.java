package com.hecong.cssystem.contract;


import com.hecong.cssystem.base.BaseEntity;
import com.hecong.cssystem.base.BaseView;
import com.hecong.cssystem.entity.MineEntity;

import io.reactivex.Observable;

/**
 * Desccribe:
 *
 * @author Created by wuyang on 2019/9/2
 */
public interface MainActivityContract {
    interface Model {

        Observable<BaseEntity<MineEntity>> forexample(String version);
    }

    interface View extends BaseView<MineEntity> {
        void showSuccess(MineEntity mineEntity);
    }

    interface Presenter {
        void pexample(String version);
    }
}
