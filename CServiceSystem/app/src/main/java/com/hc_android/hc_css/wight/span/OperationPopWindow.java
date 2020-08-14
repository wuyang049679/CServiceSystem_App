package com.hc_android.hc_css.wight.span;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.hc_android.hc_css.R;

public class OperationPopWindow {


    private Context context;
    private PopupWindow popupWindow;

    public OperationPopWindow(Context contexts) {
        context=contexts;
    }

    public void showOperationPopWindow(View view,OperationItemClickListener itemClickListener) {

        popupWindow = new PopupWindow(context);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LinearLayout contentView = new LinearLayout(context);
        contentView.setOrientation(LinearLayout.VERTICAL);


        LinearLayout ll_list=new LinearLayout(context);
        ll_list.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ll_list.setOrientation(LinearLayout.HORIZONTAL);
        ll_list.setBackgroundResource(R.drawable.bg_operation);
        ll_list.setPadding(40, 40, 40, 40);
        TextView tv_copy = new TextView(context);
        tv_copy.setText("撤回");
        tv_copy.setTextColor(Color.BLACK);
        tv_copy.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ll_list.addView(tv_copy);

        contentView.addView(ll_list);

        tv_copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                itemClickListener.onClickUndo(v);
            }
        });

        View arrow = new View(context);
        arrow.setBackgroundResource(R.drawable.triangle_down);
        LinearLayout.LayoutParams arrowLp = new LinearLayout.LayoutParams(17, 17);
        arrow.setLayoutParams(arrowLp);
//        contentView.addView(arrow);

        contentView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        popupWindow.setContentView(contentView);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);

        contentView.measure(0, 0);
        int height = popupWindow.getContentView().getMeasuredHeight();
        WindowManager manager = ((Activity)context).getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int screenWidth = outMetrics.widthPixels;
        int screenHeight = outMetrics.heightPixels;
        int[] loc = new int[2];
        view.getLocationInWindow(loc);
        int x = loc[0];
        if (x < 0)
            x = 20;
        int y = loc[1] - 20 - height;
        boolean isDown = true;
        if (y < screenHeight / 5) {
            y = loc[1] + 20 + view.getMeasuredHeight();
            isDown = false;
        }
        if (y > screenHeight / 5 * 4) {
            y = loc[1] - 20 - height;
            isDown = true;
        }
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(17, 17);
        lp.setMargins(20,0,0,0);
//        if (isDown) {
//            arrow.setBackgroundResource(R.drawable.triangle_down);
//            contentView.removeView(arrow);
//            contentView.addView(arrow,lp);
//        } else {
//            arrow.setBackgroundResource(R.drawable.triangle_up);
//            contentView.removeView(arrow);
//            contentView.addView(arrow, 0,lp);
//        }

        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, x, y);
    }

    public interface OperationItemClickListener{
        void onClickUndo(View view);
    }

}
