package com.hecong.cssystem.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.gyf.barlibrary.ImmersionBar;
import com.hecong.cssystem.R;
import com.hecong.cssystem.base.BaseActivity;
import com.hecong.cssystem.contract.MainActivityContract;
import com.hecong.cssystem.entity.MineEntity;
import com.hecong.cssystem.presenter.MainActivityPresenter;
import com.hecong.cssystem.ui.fragment.ChatListFragment;
import com.hecong.cssystem.ui.fragment.MineFragment;
import com.hecong.cssystem.ui.fragment.VisitorListFragment;
import com.next.easynavigation.constant.Anim;
import com.next.easynavigation.view.EasyNavigationBar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author wuyang
 */
public class MainActivity extends BaseActivity<MainActivityPresenter, MineEntity> implements MainActivityContract.View {


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

    }

    @Override
    protected void initView() {
        ImmersionBar.with(this)
                .statusBarColor(R.color.theme_app)
                .init();
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
    public void showDataSuccess(MineEntity datas) {

    }


    @Override
    public void showSuccess(MineEntity mineEntity) {

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
