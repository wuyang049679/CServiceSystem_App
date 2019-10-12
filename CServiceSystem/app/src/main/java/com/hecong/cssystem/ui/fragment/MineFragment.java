package com.hecong.cssystem.ui.fragment;

import android.os.Bundle;

import com.hecong.cssystem.R;
import com.hecong.cssystem.base.BaseFragment;

public class MineFragment extends BaseFragment {


    public static MineFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MineFragment fragment = new MineFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int injectTarget() {
        return 0;
    }

    @Override
    protected void doRetry() {

    }

    @Override
    protected int getLayoutResource() {
        return R.layout.mine_fragment;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public void showDataError(String errorMessage) {

    }

    @Override
    public void showDataSuccess(Object datas) {

    }
}
