package com.hecong.cssystem.wight;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

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

        lp.gravity = Gravity.CENTER;

        lp.height = height;

        lp.width = width;

        win.setAttributes(lp);

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

            width = context.getResources().getDimensionPixelOffset(dimenRes);

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



        public Builder addViewOnclick(int viewRes, View.OnClickListener listener){

            view.findViewById(viewRes).setOnClickListener(listener);

            return this;

        }
        public Builder setViewText(int viewRes, String s){

            TextView editText =(TextView) view.findViewById(viewRes);
            editText.setText(s);
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

}





