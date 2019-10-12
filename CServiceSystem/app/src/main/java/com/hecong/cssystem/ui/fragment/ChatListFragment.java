package com.hecong.cssystem.ui.fragment;

import android.os.Bundle;

import com.gyf.barlibrary.ImmersionBar;
import com.hecong.cssystem.R;
import com.hecong.cssystem.base.BaseFragment;

public class ChatListFragment extends BaseFragment {


    public static ChatListFragment newInstance() {
        
        Bundle args = new Bundle();
        
        ChatListFragment fragment = new ChatListFragment();
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
        return R.layout.chat_list_fragment;
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.theme_app)
                .titleBar(R.id.chat_list_include)
                .init();
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
