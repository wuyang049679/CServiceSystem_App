package com.hc_android.hc_css.wight;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListPopupWindow;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.PopupWindowCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hc_android.hc_css.R;
import com.hc_android.hc_css.adapter.QuickListAdapter;
import com.hc_android.hc_css.adapter.QuickTextAdapter;
import com.hc_android.hc_css.entity.QuickEntity;
import com.hc_android.hc_css.utils.android.SystemUtils;
import com.luck.picture.lib.tools.ScreenUtils;

import java.util.ArrayList;
import java.util.List;


public class QuickPopWindow extends PopupWindow {

    public Context activity;

    public QuickPopWindow(Context context) {
        super(context);
        this.activity = context;
    }

    public QuickPopWindow(View contentView, int width, int height, Context activity) {
        super(contentView, width, height);
        this.activity = activity;
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        // 7.0 以下或者高度为WRAP_CONTENT, 默认显示
        if (Build.VERSION.SDK_INT < 24 || getHeight() == ViewGroup.LayoutParams.WRAP_CONTENT) {
            super.showAsDropDown(anchor, xoff, yoff, gravity);
        } else {
            if (getContentView().getContext() instanceof Activity) {
                Activity activity = (Activity) getContentView().getContext();
                int screenHeight;
                // 获取屏幕真实高度, 减掉虚拟按键的高度
                screenHeight = SystemUtils.getSysScreenHeight(activity);
                int[] location = new int[2];
                // 获取控件在屏幕的位置
                anchor.getLocationOnScreen(location);
                // 算出popwindow最大高度
                int maxHeight = screenHeight - location[1] - anchor.getHeight();
                // popupwindow  有具体的高度值，但是小于anchor下边缘与屏幕底部的距离， 正常显示
                if(getHeight() > 0 && getHeight() < maxHeight){
                    super.showAsDropDown(anchor, xoff, yoff, gravity);
                }else {
                    // match_parent 或者 popwinddow的具体高度值大于anchor下边缘与屏幕底部的距离， 都设置为最大可用高度
                    setHeight(maxHeight);
                    super.showAsDropDown(anchor, xoff, yoff, gravity);
                }

            }
        }
    }

    /**
     * 计算出来的位置，y方向就在anchorView的上面和下面对齐显示，x方向就是与屏幕右边对齐显示
     * 如果anchorView的位置有变化，就可以适当自己额外加入偏移来修正
     * @param anchorView  呼出window的view
     * @param contentView   window的内容布局
     * @return window显示的左上角的xOff,yOff坐标
     */
    public  int[] calculatePopWindowPos(final View anchorView, final View contentView) {
        final int windowPos[] = new int[2];
        final int anchorLoc[] = new int[2];
       // 获取锚点View在屏幕上的左上角坐标位置
        anchorView.getLocationOnScreen(anchorLoc);
        final int anchorHeight = anchorView.getHeight();
        // 获取屏幕的高宽
        final int screenHeight = ScreenUtils.getScreenHeight(anchorView.getContext());
        final int screenWidth = ScreenUtils.getScreenWidth(anchorView.getContext());
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        // 计算contentView的高宽
        final int windowHeight = contentView.getMeasuredHeight();
        final int windowWidth = contentView.getMeasuredWidth();
        // 判断需要向上弹出还是向下弹出显示
        final boolean isNeedShowUp = (screenHeight - anchorLoc[1] - anchorHeight < windowHeight);
        if (isNeedShowUp) {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] - windowHeight;
        } else {
            windowPos[0] = screenWidth - windowWidth;
            windowPos[1] = anchorLoc[1] + anchorHeight;
        }
        return windowPos;
    }
}
