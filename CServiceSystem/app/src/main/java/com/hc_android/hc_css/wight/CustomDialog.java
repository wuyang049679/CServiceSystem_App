package com.hc_android.hc_css.wight;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hc_android.hc_css.R;
import com.hc_android.hc_css.adapter.PopWindowListAdapter;
import com.hc_android.hc_css.adapter.VisitorPathAdapter;
import com.hc_android.hc_css.utils.android.SystemUtils;
import com.scwang.smartrefresh.layout.util.DensityUtil;


/**
 * Desccribe:自定义dialog
 *
 * @author Created by wuyang on 2019/8/2
 */

public class CustomDialog extends Dialog {

    private Context context;

    private int height, width;

    private boolean cancelTouchout;

    private View view;



    private CustomDialog(Builder builder) {

        super(builder.context);

        context = builder.context;

        height = builder.height;

        width = builder.width;

        cancelTouchout = builder.cancelTouchout;

        view = builder.view;

    }



    public View getView(int viewRes){

       return view.findViewById(viewRes);
    }


    private CustomDialog(Builder builder, int resStyle) {

        super(builder.context, resStyle);

        context = builder.context;

        height = builder.height;

        width = builder.width;
        cancelTouchout = builder.cancelTouchout;
        view = builder.view;

    }



    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(view);
        setCanceledOnTouchOutside(cancelTouchout);
        Window win = getWindow();
        WindowManager.LayoutParams lp = win.getAttributes();
        lp.gravity = Gravity.BOTTOM;
        lp.height = height;
        lp.width = width;
        win.setAttributes(lp);
        win.setWindowAnimations(R.style.dialog_animation);

    }



    public static final class Builder {


        private Context context;

        private int height, width;

        private boolean cancelTouchout;

        private View view;

        private int resStyle = -1;


        public Builder(Context context) {

            this.context = context;

        }


        public Builder view(int resView) {

            view = LayoutInflater.from(context).inflate(resView, null);

            return this;

        }


        public Builder view(View resView) {

            view = resView;

            return this;

        }

        public Builder heightpx(int val) {

            height = val;

            return this;

        }


        public Builder widthpx(int val) {

            width = val;

            return this;

        }


        public Builder heightdp(int val) {

            height = DensityUtil.dp2px(val);

            return this;

        }


        public Builder widthdp(int val) {

            width = DensityUtil.dp2px(val);

            return this;

        }


        public Builder heightDimenRes(int dimenRes) {

            height = context.getResources().getDimensionPixelOffset(dimenRes);

            return this;

        }


        public Builder widthDimenRes(int dimenRes) {

            try {
                width = SystemUtils.getScreenDispaly(context)[0];

            } catch (Exception e) {
            }
            return this;

        }


        public Builder style(int resStyle) {

            this.resStyle = resStyle;

            return this;

        }


        public Builder cancelTouchout(boolean val) {

            cancelTouchout = val;

            return this;

        }


        public Builder addViewOnclick(int viewRes, View.OnClickListener listener) {

            view.findViewById(viewRes).setOnClickListener(listener);

            return this;

        }

        public Builder setViewText(int viewRes, String s) {

            TextView editText = (TextView) view.findViewById(viewRes);
            editText.setText(s);
            return this;

        }

        //list类型弹出是对话框
        public Builder setRecyclerView(int viewRes, PopWindowListAdapter adapter) {
            RecyclerView recyclerView = (RecyclerView) view.findViewById(viewRes);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            return this;
        }
        //访客轨迹类型弹出是对话框
        public Builder setRecyclerView(int viewRes, VisitorPathAdapter adapter) {
            RecyclerView recyclerView = (RecyclerView) view.findViewById(viewRes);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
            return this;
        }

        public CustomDialog build() {

            if (resStyle != -1) {

                return new CustomDialog(this, resStyle);

            } else {

                return new CustomDialog(this);

            }

        }


    }

    public void setAutoHeight() {
        //设置自适应的方法：
        WindowManager.LayoutParams dialogParams = getWindow().getAttributes();
        dialogParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        dialogParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(dialogParams);
    }
}





