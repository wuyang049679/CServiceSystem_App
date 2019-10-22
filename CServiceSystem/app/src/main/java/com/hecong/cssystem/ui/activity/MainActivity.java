package com.hecong.cssystem.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;

import com.hecong.cssystem.R;
import com.hecong.cssystem.base.BaseActivity;
import com.hecong.cssystem.contract.MainActivityContract;
import com.hecong.cssystem.entity.LoginEntity;
import com.hecong.cssystem.presenter.MainActivityPresenter;
import com.hecong.cssystem.ui.fragment.ChatListFragment;
import com.hecong.cssystem.ui.fragment.MineFragment;
import com.hecong.cssystem.ui.fragment.VisitorListFragment;
import com.hecong.cssystem.utils.Constant;
import com.hecong.cssystem.utils.android.SharedPreferencesUtils;
import com.next.easynavigation.view.EasyNavigationBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wuyang
 */
public class MainActivity extends BaseActivity<MainActivityPresenter, LoginEntity.DataBean> implements MainActivityContract.View {


    @BindView(R.id.easyNavigationBar)
    EasyNavigationBar easyNavigationBar;


    private String[] tabText = {"对话", "访客", "我的"};
    //未选中icon
    private int[] normalIcon = {R.mipmap.chat_h, R.mipmap.fk_h, R.mipmap.user_h};
    //选中时icon
    private int[] selectIcon = {R.mipmap.chat, R.mipmap.fk, R.mipmap.user};

    private List<Fragment> fragments = new ArrayList<>();


    @Override
    public MainActivityPresenter getPresenter() {
        return new MainActivityPresenter();
    }

    @Override
    protected void reloadActivity() {

    }

    @Override
    protected View injectTarget() {
        return findViewById(R.id.easyNavigationBar);
    }

    @Override
    protected void initData() {
        //进行登录验证
        String hash = (String) SharedPreferencesUtils.getParam(Constant.HASH, "");
         mPresenter.pCheckLogin(hash);

    }

    @Override
    protected void initView() {

        fragments.add(ChatListFragment.newInstance());
        fragments.add(VisitorListFragment.newInstance());
        fragments.add(MineFragment.newInstance());

        easyNavigationBar.titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .fragmentList(fragments)
                .fragmentManager(getSupportFragmentManager())
                .addLayoutRule(EasyNavigationBar.RULE_BOTTOM)
                .iconSize(23)
                .tabTextSize(10)
                .normalTextColor(getResources().getColor(R.color.black_999))   //Tab未选中时字体颜色
                .selectTextColor(getResources().getColor(R.color.theme_app))
                .navigationHeight(50)
                .msgPointTop(-11)
                .msgPointSize(15)
                .msgPointTextSize(10)
                //分割线高度  默认1px
                .lineColor(getResources().getColor(R.color.black_line))
                .mode(EasyNavigationBar.MODE_NORMAL)
                .hasPadding(true)//fragment列表底部重叠，部分看不到
                .build();
       easyNavigationBar.setMsgPointCount(0, 9);
       easyNavigationBar.setMsgPointCount(1, 19);
       easyNavigationBar.setMsgPointCount(2, 100);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_main;
    }

    @Override
    public void showDataSuccess(LoginEntity.DataBean datas) {

    }

    @Override
    public void showDataError(String errorMessage) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
//        overridePendingTransition(R.anim.activity_open,R.anim.activity_close);
        finish();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
    public EasyNavigationBar getNavigationBar() {
        return easyNavigationBar;
    }
}
