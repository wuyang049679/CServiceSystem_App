package com.hc_android.hc_css.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.base.BaseActivity;
import com.hc_android.hc_css.contract.MDiaglogFragmentContract;
import com.hc_android.hc_css.entity.QuickCacheEntity;
import com.hc_android.hc_css.entity.QuickEntity;
import com.hc_android.hc_css.entity.QuickGroupEntity;
import com.hc_android.hc_css.presenter.MDiaglogFragmentPresenter;
import com.hc_android.hc_css.ui.fragment.QuickReplyFragment;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.JsonParseUtils;
import com.hc_android.hc_css.utils.android.SharedPreferencesUtils;
import com.next.easynavigation.adapter.ViewPagerAdapter;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.ColorTransitionPagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuickActivity extends BaseActivity<MDiaglogFragmentPresenter, QuickEntity.DataBean> implements MDiaglogFragmentContract.View, BaseQuickAdapter.OnItemChildClickListener {


    @BindView(R.id.magicIndicator)
    MagicIndicator magicIndicator;
    @BindView(R.id.quick_reply_viewPager)
    ViewPager viewPager;
    private List<Fragment> mList;

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


        List<String> mTitleDataList = Arrays.asList("团队的", "我的");
        CommonNavigator commonNavigator = new CommonNavigator(this);
        commonNavigator.setAdjustMode(true);
        commonNavigator.setAdapter(new CommonNavigatorAdapter() {

            @Override
            public int getCount() {

                return mTitleDataList == null ? 0 : mTitleDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                ColorTransitionPagerTitleView colorTransitionPagerTitleView = new ColorTransitionPagerTitleView(context);
                colorTransitionPagerTitleView.setNormalColor(context.getResources().getColor(R.color.black));
                colorTransitionPagerTitleView.setSelectedColor(context.getResources().getColor(R.color.break_tv));
                colorTransitionPagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                colorTransitionPagerTitleView.setText(mTitleDataList.get(index));
                colorTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewPager.setCurrentItem(index);
                    }
                });

                return colorTransitionPagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator indicator = new LinePagerIndicator(context);
                indicator.setMode(LinePagerIndicator.MODE_MATCH_EDGE);
                indicator.setColors(context.getResources().getColor(R.color.break_tv));

                indicator.setLineHeight(3);
                return indicator;
            }
        });
        magicIndicator.setNavigator(commonNavigator);
        mList = new ArrayList<>();
        mList.add(QuickReplyFragment.newInstance(0));
        mList.add(QuickReplyFragment.newInstance(1));
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), mList));
        viewPager.setOffscreenPageLimit(2);
        String cache = (String) SharedPreferencesUtils.getInstance().get(Constant.QUICKCACHE, "");
        QuickCacheEntity cacheEntity = JsonParseUtils.parseToObject(cache, QuickCacheEntity.class);
        int current = 0;
        if (cacheEntity != null) {
            Integer s = cacheEntity.getvCurrent();
            if (s != null) {
                current = s;
            }
        }
        viewPager.setCurrentItem(current);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    @Override
    public int getContentView() {
        return R.layout.activity_quick;
    }

    @Override
    public void showDataSuccess(QuickEntity.DataBean datas) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void showQuickList(QuickEntity.DataBean dataBean) {

    }

    @Override
    public void showQuickGroup(QuickGroupEntity.DataBean dataBean) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.backImg)
    public void onViewClicked() {
        finish();
    }
}