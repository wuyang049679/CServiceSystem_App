package com.hc_android.hc_css.wight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hc_android.hc_css.R;


/**
 * Created by 61904 on 2017/2/7.
 */


//自定义Edittext实现左右图片点击事件的工具类
@SuppressLint("AppCompatCustomView")
public class AddAndSubEditText extends EditText implements View.OnFocusChangeListener, TextWatcher {


    private static int Rest_Length = 0;

    /**
     * 删除按钮的引用
     */
    private Drawable mClearDrawable;
    /**
     * 控件是否有焦点
     */
    private boolean hasFoucs;

    private static boolean isShow = false;
    public static boolean isLoading=false;
    public AddAndSubEditText(Context context) {
        this(context, null);
    }

    public AddAndSubEditText(Context context, AttributeSet attrs) {
        //这里构造方法也很重要，不加这个很多属性不能再XML里面定义
        this(context, attrs, android.R.attr.editTextStyle);
    }

    public AddAndSubEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }



    private void init() {
        //获取EditText的DrawableRight,假如没有设置我们就使用默认的图片,getCompoundDrawables()获取Drawable的四个位置的数组
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            mClearDrawable = getResources().getDrawable(R.mipmap.clear);
//            throw new NullPointerException("You can add drawableRight attribute in XML");
        }
        //设置图标的位置以及大小,getIntrinsicWidth()获取显示出来的大小而不是原图片的带小
        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        //默认设置隐藏图标
        setClearIconVisible(false);
        //设置焦点改变的监听
        setOnFocusChangeListener(this);
        //设置输入框里面内容发生改变的监听
        addTextChangedListener(this);
    }


    /**
     * 因为我们不能直接给EditText设置点击事件，所以我们用记住我们按下的位置来模拟点击事件
     * 当我们按下的位置 在  EditText的宽度 - 图标到控件右边的间距 - 图标的宽度  和
     * EditText的宽度 - 图标到控件右边的间距之间我们就算点击了图标，竖直方向就没有考虑
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (getCompoundDrawables()[2] != null) {
                //getTotalPaddingRight()图标左边缘至控件右边缘的距离
                //getWidth() - getTotalPaddingRight()表示从最左边到图标左边缘的位置
                //getWidth() - getPaddingRight()表示最左边到图标右边缘的位置
                boolean touchable = event.getX() > (getWidth() - getTotalPaddingRight())
                        && (event.getX() < ((getWidth() - getPaddingRight())));

                if (touchable) {
                    this.setText("");
                }
            }
//            if(getCompoundDrawables()[0] != null){
//                boolean touchLeft = event.getX()>0 && event.getX()<getCompoundDrawables()[0].getIntrinsicWidth();
//                if(touchLeft){
//                    if(isShow==false){
//                        isShow = true;
//                        //设置为可见
//                        this.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                    }else{
//                        isShow = false;
//                        //设置为密码模式
//                        this.setTransformationMethod(PasswordTransformationMethod.getInstance());
//                    }
//                }
//            }
        }

        return super.onTouchEvent(event);
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面字符串长度设置清除图标的显示与隐藏
     */
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        this.hasFoucs = hasFocus;
        if (hasFocus) {
            setClearIconVisible(getText().length() > 0);
        } else {
            setClearIconVisible(false);
        }
    }

    /**
     * 设置清除图标的显示与隐藏，调用setCompoundDrawables为EditText绘制上去
     *
     * @param visible
     */
    public void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0],
                getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    /**
     * 当输入框里面内容发生变化的时候回调的方法
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int count,
                              int after) {
        if (hasFoucs) {
            setClearIconVisible(s.length() > 0);
        }

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }


//    @Override
//    protected void onSelectionChanged(int selStart, int selEnd) {
//        super.onSelectionChanged(selStart, selEnd);
//        //保证光标始终在最后面  
//        if (selStart==selEnd){//防止不能多选 
//            setSelection(getText().length());
//        }
//    }

    //获取当前手机的状态栏高度
    public static int getStatusHeight(Context context) {

        int statusHeight = -1;
        try {
            Class clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object)
                    .toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }


    public static void setEditTextChangedListener(final EditText editText, final TextView textView, final int maxLength) {


        //监听输入字数
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (Rest_Length >= 0) {
                            textView.setText("还能输入" + Rest_Length + "个字");
                        }
                    }
                });
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (Rest_Length >= 0) {

                    Rest_Length = editText.getText().toString().length();
                    if (Rest_Length < 0) {
                        Rest_Length = 0;
                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        if (maxLength == 500) {
                            if (Rest_Length >= 0) {
                                textView.setText("还能输入" + Rest_Length + "个字");
                            }
                        } else if (maxLength == 200) {
                            if (Rest_Length >= 0) {
                                textView.setText(Rest_Length + "/200");
                            }
                        }


                    }
                });

            }
        });
    }


    /**
     * 设置光标位置
     * @param editText
     */
    public  void setEditTextChangedListenerCusor(final EditText editText) {


        //监听输入字数
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                editText.setSelection(s.length());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 检测输入框是否都输入了内容
     * 从而改变按钮的是否可点击
     * 以及输入框后面的X的可见性，X点击删除输入框的内容
     */
    public static class TextChangeListener {
        private TextView button;
        private EditText[] editTexts;
        private boolean isShow;
        private boolean isCheck, hasCheckBox;

        public TextChangeListener(TextView button) {
            this.button = button;
            hasCheckBox = true;
        }


        public TextChangeListener addAllEditText(EditText... editTexts) {
            this.editTexts = editTexts;
            initEditListener();
            return this;
        }


        public void setCheckBoxListener(CheckBox checkBox) {

            hasCheckBox = true;


            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                    hasCheckBox = isChecked;

                    if (checkAllEdit() && isChecked) {
                        button.setEnabled(true);
                    } else {
                        button.setEnabled(false);
                    }


                }
            });
        }

        private void initEditListener() {
            Log.i("TAG", "调用了遍历editext的方法");
            for (EditText editText : editTexts) {
                editText.addTextChangedListener(new TextChange());
            }
            if (checkAllEdit()){//没值禁止点击
                button.setEnabled(true);
                button.setBackgroundResource(R.drawable.login_btn);
            }else {
                button.setEnabled(false);
                button.setBackgroundResource(R.drawable.login_btn_f);
            }
        }

        /**
         * edit输入的变化来改变按钮的是否点击
         */
        private  class TextChange implements TextWatcher {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

                if (hasCheckBox) {
                    if (checkAllEdit()) {
                        Log.i("TAG", "所有edittext有值了");
                        if (!isLoading) {
                            button.setEnabled(true);
                            button.setBackgroundResource(R.drawable.login_btn);
                        }
                    } else {
                        if (!isLoading) {
                            button.setEnabled(false);
                            button.setBackgroundResource(R.drawable.login_btn_f);
                        }
                    }

                } else {

                    if (checkAllEdit()) {
                        Log.i("TAG", "所有edittext有值了");
                        if (!isLoading) {
                            button.setEnabled(true);
                            button.setBackgroundResource(R.drawable.login_btn);
                        }
                    } else {
                        if (!isLoading) {
                            button.setEnabled(false);
                            button.setBackgroundResource(R.drawable.login_btn_f);
                        }
                        Log.i("TAG", "有edittext没值了");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        }

        /**
         * 检查所有的edit是否输入了数据
         *
         * @return
         */
        public boolean checkAllEdit() {
            for (EditText editText : editTexts) {
                if (!TextUtils.isEmpty(editText.getText().toString().trim())) {
                    continue;
                } else {
                    return false;
                }
            }
            return true;
        }
    }


    /**
     * 设置密码可见
     */


    public void setPassWordVisible(final ImageView imageView) {


//        imageView.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {

//                if (isShow == false) {
//                    isShow = true;
//                    //设置为可见
//                    setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//                    imageView.setImageResource(R.drawable.dl_ck);
//                } else {
//                    isShow = false;
//                    //设置为密码模式
//                    imageView.setImageResource(R.drawable.dl_gb);
//                    setTransformationMethod(PasswordTransformationMethod.getInstance());
//                }
//            }
//        });


    }
    /**
     * 隐藏软键盘(有输入框)
     * @param context
     * @param mEditText
     */
    public static void hideSoftKeyboard(@NonNull Context context,
                                        @NonNull EditText mEditText)
    {
        InputMethodManager inputmanger = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputmanger.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }
}
