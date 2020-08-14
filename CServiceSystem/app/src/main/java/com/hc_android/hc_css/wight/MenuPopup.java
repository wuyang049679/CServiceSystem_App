package com.hc_android.hc_css.wight;

import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.annotation.NonNull;

import com.hc_android.hc_css.R;
import com.lxj.xpopup.core.AttachPopupView;

public class MenuPopup extends AttachPopupView {
    public MenuPopup(@NonNull Context context) {
        super(context);
    }


    @Override
    protected int getImplLayoutId() {
        return R.layout.menu_popup;
    }



    @Override
    protected Drawable getPopupBackground() {
        return getResources().getDrawable(R.color.window_bg);
    }
}
