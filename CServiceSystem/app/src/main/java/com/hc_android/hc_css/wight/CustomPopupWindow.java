package com.hc_android.hc_css.wight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hc_android.hc_css.R;

public class CustomPopupWindow extends PopupWindow implements View.OnClickListener {
    private View mPopView;
//    TextView showCall;
//    RelativeLayout popup_secure_telephone, popup_ordinary_call;
//    Button popup_cancel;
    public CustomPopupWindow(Context context) {
        super(context);
        init(context);
        setPopupWindow();
//        popup_secure_telephone.setOnClickListener(this);
//        popup_ordinary_call.setOnClickListener(this);
//        popup_cancel.setOnClickListener(this);
    }

    private void init(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        mPopView = inflater.inflate(R.layout.popup_window_meun, null);
//        popup_cancel = (Button) mPopView.findViewById(R.id.popup_cancel);
//        popup_secure_telephone = (RelativeLayout) mPopView.findViewById(R.id.popup_secure_telephone);
//        popup_ordinary_call = (RelativeLayout) mPopView.findViewById(R.id.popup_ordinary_call);
//        showCall = (TextView) mPopView.findViewById(R.id.popup_top_call);
    }

    /**
     * 设置窗口的相关属性
     */
    @SuppressLint("InlinedApi")
    private void setPopupWindow() {
        this.setContentView(mPopView);// 设置View
//        showCall.setText(name);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的宽
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);// 设置弹出窗口的高
        this.setFocusable(true);// 设置弹出窗口可
//        this.setAnimationStyle(R.style.DetailAnimation);// 设置动画
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));// 设置背景透明
        this.setOutsideTouchable(true);
        this.setOnDismissListener(new OnDismissListener() { //退出popupwidon时显示父控件原来的颜色
            @Override
            public void onDismiss() {
//                WindowManager.LayoutParams lp = getWindow().getAttributes();
//                lp.alpha = 1.0f;
//                getWindow().setAttributes(lp);
            }
        });
        mPopView.setOnTouchListener(new View.OnTouchListener() {// 如果触摸位置在窗口外面则销毁
            @Override
            public boolean onTouch(View v, MotionEvent event) {
//                int height = mPopView.findViewById(R.id.id_pop_layout).getTop();
//                int y = (int) event.getY();
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    if (y < height) {
//                        dismiss();
//                    }
//                }
                return true;
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.popup_secure_telephone:
//                break;
//            case R.id.popup_ordinary_call:
//                break;
//            case R.id.popup_cancel:
//                customPopupWindow.dismiss();
//                break;
        }
    }


}
