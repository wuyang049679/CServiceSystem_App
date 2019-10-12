package com.hecong.cssystem.ui.fragment;

import android.os.Bundle;

import com.hecong.cssystem.R;
import com.hecong.cssystem.base.BaseFragment;

public class VisitorListFragment extends BaseFragment {

    public static VisitorListFragment newInstance() {
        
        Bundle args = new Bundle();
        
        VisitorListFragment fragment = new VisitorListFragment();
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
        return R.layout.visitor_list_fragment;
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
