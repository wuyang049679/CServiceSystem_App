package com.hc_android.hc_css.wight;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.hc_android.hc_css.R;
import com.hc_android.hc_css.entity.QuickCacheEntity;
import com.hc_android.hc_css.ui.fragment.QuickReplyFragment;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.JsonParseUtils;
import com.hc_android.hc_css.utils.android.SharedPreferencesUtils;
import com.next.easynavigation.adapter.ViewPagerAdapter;
import com.shehuan.nicedialog.BaseNiceDialog;
import com.shehuan.nicedialog.ViewHolder;

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

public class MDialogFragment extends BaseNiceDialog {


    private DialogInterface.OnDismissListener mOnClickListener;
    private ViewPager viewPager;
    private List<Fragment> mList;

    public void setOnDismissListener(DialogInterface.OnDismissListener listener){
        this.mOnClickListener = listener;
    }

    public static MDialogFragment newInstance() {

        Bundle args = new Bundle();

        MDialogFragment fragment = new MDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int intLayoutId() {
        return R.layout.quick_reply_popwindow;
    }



    @Override
    public void convertView(ViewHolder holder, BaseNiceDialog dialog) {
        viewPager=holder.getView(R.id.quick_reply_viewPager);
        MagicIndicator magicIndicator=holder.getView(R.id.magicIndicator);

        List<String> mTitleDataList= Arrays.asList("团队的","我的");
        CommonNavigator commonNavigator = new CommonNavigator(getContext());
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
                colorTransitionPagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP,15);
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
        viewPager.setAdapter(new ViewPagerAdapter(dialog.getChildFragmentManager(),mList));
        viewPager.setOffscreenPageLimit(2);
        String cache = (String) SharedPreferencesUtils.getInstance().get(Constant.QUICKCACHE, "");
        QuickCacheEntity cacheEntity = JsonParseUtils.parseToObject(cache, QuickCacheEntity.class);
        int current=0;
        if (cacheEntity!=null) {
            Integer s = cacheEntity.getvCurrent();
        if (s!=null){
         current=s;
        }
        }
        viewPager.setCurrentItem(current);
        ViewPagerHelper.bind(magicIndicator, viewPager);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        //保存本地
        int currentItem = viewPager.getCurrentItem();
        QuickReplyFragment fragment = (QuickReplyFragment) mList.get(currentItem);
        QuickCacheEntity cache = fragment.getCache(currentItem);
        cache.setvCurrent(currentItem);
        String s = JsonParseUtils.parseToJson(cache);
        SharedPreferencesUtils.getInstance().set(Constant.QUICKCACHE,s);
    }


}
