package com.hecong.cssystem.activity;

import android.view.View;

import com.hecong.cssystem.R;
import com.hecong.cssystem.base.BaseActivity;
import com.hecong.cssystem.contract.MainActivityContract;
import com.hecong.cssystem.entity.MineEntity;
import com.hecong.cssystem.presenter.MainActivityPresenter;

/**
 * @author wuyang
 */
public class MainActivity extends BaseActivity<MainActivityPresenter, MineEntity> implements MainActivityContract.View {


    @Override
    protected void reloadActivity() {

    }

    @Override
    protected View injectTarget() {
        return null;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void showDataSuccess(MineEntity datas) {

    }


    @Override
    public void showSuccess(MineEntity mineEntity) {

    }
}
