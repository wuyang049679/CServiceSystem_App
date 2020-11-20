package com.hc_android.hc_css.wight;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.ListPopupWindow;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.hc_android.hc_css.R;

import com.hc_android.hc_css.adapter.ChannelCompanyAdapter;
import com.hc_android.hc_css.adapter.EmojiAdapter;
import com.hc_android.hc_css.adapter.QuickListAdapter;
import com.hc_android.hc_css.entity.EmojiEntity;

import com.hc_android.hc_css.entity.QuickEntity;
import com.hc_android.hc_css.ui.activity.ChatActivity;
import com.hc_android.hc_css.utils.DateUtils;
import com.hc_android.hc_css.utils.FileUtils;
import com.hc_android.hc_css.utils.NullUtils;
import com.hc_android.hc_css.wight.media.RecordButton;

import java.util.ArrayList;
import java.util.List;

public class ChatUiHelper {
    private static final String SHARE_PREFERENCE_NAME = "com.hc_android.hc_css.wight";
    private static final String SHARE_PREFERENCE_TAG = "soft_input_height";
    private Activity mActivity;
    private LinearLayout mContentLayout;//整体界面布局
    private FrameLayout mBottomLayout;//底部布局
    private RelativeLayout inputLin; // 输入布局
    private ConstraintLayout mEmojiLayout;//表情布局
    private ConstraintLayout mAddLayout;//添加布局
    private TextView mSendBtn;//发送按钮
    private View mAddButton;//加号按钮
    private Button mAudioButton;//录音按钮
    private ImageView mAudioIv;//录音图片


    private AutoCompleteTextView mEditText;
    private InputMethodManager mInputManager;
    private SharedPreferences mSp;
    private ImageView mIvEmoji;
    private RecyclerView recyclerView;
    private RecyclerView chatRecycler;
    private ImageView eEoDel;
    private List<QuickEntity.DataBean.ListBean> listBeans = new ArrayList<>();

    private ChannelCompanyAdapter autoCompleteAdapter;

    public ChatUiHelper() {

    }

    public static ChatUiHelper with(Activity activity) {
        ChatUiHelper mChatUiHelper = new ChatUiHelper();
        //   AndroidBug5497Workaround.assistActivity(activity);
        mChatUiHelper.mActivity = activity;
        mChatUiHelper.mInputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        mChatUiHelper.mSp = activity.getSharedPreferences(SHARE_PREFERENCE_NAME, Context.MODE_PRIVATE);
        return mChatUiHelper;
    }
    public static final int EVERY_PAGE_SIZE = 21;
    private List<EmojiEntity> mListEmoji;
    private EmojiAdapter emojiAdapter;
    public ChatUiHelper bindEmojiData() {

        //表情控件
        recyclerView=mEmojiLayout.findViewById(R.id.emoji_recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(mActivity, 8);
        recyclerView.setLayoutManager(gridLayoutManager);
        if (NullUtils.isEmptyList(mListEmoji)) {
            mListEmoji=new ArrayList<>();
            String[] stringArray = mActivity.getResources().getStringArray(R.array.emoji_list);
            String[] stringArray2 = mActivity.getResources().getStringArray(R.array.emoji_text);
            List<String> list = FileUtils.copyStickerToStickerPath(mActivity, "stickers");
            for (int i = 0; i < stringArray.length; i++) {
                for (int x = 0; x < list.size(); x++) {
                    if (list.get(x).contains(stringArray[i])) {
                        mListEmoji.add(new EmojiEntity(stringArray2[i], list.get(x)));
                    }
                }
            }
            emojiAdapter = new EmojiAdapter(mListEmoji);
            recyclerView.setAdapter(emojiAdapter);
        }
//点击表情事件
        emojiAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            EmojiEntity emojiEntity = (EmojiEntity) adapter.getData().get(position);
            int curPosition = mEditText.getSelectionStart();
            StringBuilder sb = new StringBuilder(mEditText.getText().toString());
            sb.insert(curPosition, emojiEntity.getName());
            mEditText.setText(sb.toString());
            mEditText.setSelection(curPosition + emojiEntity.getName().length());//光标位置
        });


        if (mEditText.getText().toString().trim().length() > 0) {
            mSendBtn.setVisibility(View.VISIBLE);
            mAddButton.setVisibility(View.GONE);
            mEditText.requestFocus();
            mEditText.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mInputManager.showSoftInput(mEditText, 0);
                }
            },500);
            ((ChatActivity)mActivity).onViewBottom();
        } else {
            mSendBtn.setVisibility(View.GONE);
            mAddButton.setVisibility(View.VISIBLE);
        }
        return this;
    }


    //绑定整体界面布局
    public ChatUiHelper bindContentLayout(LinearLayout bottomLayout) {
        mContentLayout = bottomLayout;
        inputLin = mContentLayout.findViewById(R.id.input_lin);
        return this;
    }


    //绑定输入框
    @SuppressLint("ClickableViewAccessibility")
    public ChatUiHelper bindEditText(EditText editText) {
        mEditText = (AutoCompleteTextView)editText;
//        mEditText.requestFocus();
        mEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP && mBottomLayout.isShown()) {
                    lockContentHeight();//显示软件盘时，锁定内容高度，防止跳闪。
                    hideBottomLayout(true);//隐藏表情布局，显示软件盘
                    mIvEmoji.setImageResource(R.drawable.smile);
                    //软件盘显示后，释放内容高度
                    mEditText.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            unlockContentHeightDelayed();
                        }
                    }, 200L);
                }
                return false;
            }
        });

        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (mEditText.getText().toString().trim().length() > 0) {
                    mSendBtn.setVisibility(View.VISIBLE);
                    mAddButton.setVisibility(View.GONE);
                    setListPopupWindow();
                } else {
                    mSendBtn.setVisibility(View.GONE);
                    mAddButton.setVisibility(View.VISIBLE);

                }


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return this;
    }




    //绑定底部布局
    public ChatUiHelper bindBottomLayout(FrameLayout bottomLayout) {
        mBottomLayout = bottomLayout;
        return this;
    }


    //绑定表情布局
    public ChatUiHelper bindEmojiLayout(ConstraintLayout emojiLayout) {
        mEmojiLayout = emojiLayout;
        return this;
    }
    //绑定表情删除按钮
    public ChatUiHelper bindEmojiDel(ImageView emoDel) {
        eEoDel = emoDel;
        emoDel.setOnClickListener(view -> {
            mActivity.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
        });
        return this;
    }

    //绑定添加布局
    public ChatUiHelper bindAddLayout(ConstraintLayout addLayout) {
        mAddLayout = addLayout;
        return this;
    }

    //绑定发送按钮
    public ChatUiHelper bindttToSendButton(TextView sendbtn) {
        mSendBtn = sendbtn;
        return this;
    }


    //绑定语音按钮点击事件
    public ChatUiHelper bindAudioBtn(RecordButton audioBtn) {
        mAudioButton = audioBtn;
        return this;
    }

    //绑定语音图片点击事件
    public ChatUiHelper bindAudioIv(ImageView audioIv) {
        mAudioIv = audioIv;
        audioIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ChatActivity)mActivity).onViewBottom();
                //如果录音按钮显示
                if (mAudioButton.isShown()) {
                    hideAudioButton();
                    mEditText.requestFocus();
                    showSoftInput();

                } else {
                    mEditText.clearFocus();
                    showAudioButton();
                    hideEmotionLayout();
                    hideMoreLayout();
                }
            }
        });

        // UIUtils.postTaskDelay(() -> mRvMsg.smoothMoveToPosition(mRvMsg.getAdapter().getItemCount() - 1), 50);
        return this;
    }

    private void hideAudioButton() {
        mAudioButton.setVisibility(View.GONE);
        mEditText.setVisibility(View.VISIBLE);
        mAudioIv.setImageResource(R.mipmap.yygl);

    }


    private void showAudioButton() {
        mAudioButton.setVisibility(View.VISIBLE);
        mEditText.setVisibility(View.GONE);
        mAudioIv.setImageResource(R.drawable.keybod);
        if (mBottomLayout.isShown()) {
            hideBottomLayout(false);
        } else {
            hideSoftInput();
        }
    }



    //绑定表情按钮点击事件
    public ChatUiHelper bindToEmojiButton(ImageView emojiBtn) {
        mIvEmoji = emojiBtn;
        emojiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ChatActivity)mActivity).onViewBottom();
                mEditText.requestFocus();
                if (!mEmojiLayout.isShown()) {
                    if (mAddLayout.isShown()) {
                        showEmotionLayout();
                        hideMoreLayout();
                        hideAudioButton();
                        return;
                    }
                } else if (mEmojiLayout.isShown() && !mAddLayout.isShown()) {
                    mIvEmoji.setImageResource(R.drawable.smile);
                    if (mBottomLayout.isShown()) {
                        lockContentHeight();//显示软件盘时，锁定内容高度，防止跳闪。
                        hideBottomLayout(true);//隐藏表情布局，显示软件盘
                        unlockContentHeightDelayed();//软件盘显示后，释放内容高度
                    } else {
                        if (isSoftInputShown()) {//同上
                            lockContentHeight();
                            showBottomLayout();
                            unlockContentHeightDelayed();
                        } else {
                            showBottomLayout();//两者都没显示，直接显示表情布局
                        }
                    }


                    return;
                }
                showEmotionLayout();
                hideMoreLayout();
                hideAudioButton();
                if (mBottomLayout.isShown()) {
                    lockContentHeight();//显示软件盘时，锁定内容高度，防止跳闪。
                    hideBottomLayout(true);//隐藏表情布局，显示软件盘
                    unlockContentHeightDelayed();//软件盘显示后，释放内容高度
                } else {
                    if (isSoftInputShown()) {//同上
                        lockContentHeight();
                        showBottomLayout();
                        unlockContentHeightDelayed();
                    } else {
                        showBottomLayout();//两者都没显示，直接显示表情布局
                    }
                }
            }
        });
        return this;
    }


    //绑定底部加号按钮
    public ChatUiHelper bindToAddButton(View addButton) {
        mAddButton = addButton;
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ChatActivity)mActivity).onViewBottom();
                 mEditText.clearFocus();
                 hideAudioButton();
                 if (mBottomLayout.isShown()){
                  if (mAddLayout.isShown()){
                      lockContentHeight();//显示软件盘时，锁定内容高度，防止跳闪。
                      hideBottomLayout(true);//隐藏表情布局，显示软件盘
                      unlockContentHeightDelayed();//软件盘显示后，释放内容高度
                  }else{
                      showMoreLayout();
                      hideEmotionLayout();
                  }
              }else{
                  if (isSoftInputShown()) {//同上
                      hideEmotionLayout();
                      showMoreLayout();
                      lockContentHeight();
                      showBottomLayout();
                      unlockContentHeightDelayed();
                  } else {
                       showMoreLayout();
                       hideEmotionLayout();
                       showBottomLayout();//两者都没显示，直接显示表情布局
                  }
              }
            }
        });
        return this;
    }


    private void hideMoreLayout() {
        mAddLayout.setVisibility(View.GONE);

    }

    private void showMoreLayout() {
        mAddLayout.setVisibility(View.VISIBLE);
    }


    /**
     * 隐藏底部布局
     *
     * @param showSoftInput 是否显示软件盘
     */
    public void hideBottomLayout(boolean showSoftInput) {
        if (mBottomLayout.isShown()) {
            mBottomLayout.setVisibility(View.GONE);
            if (showSoftInput) {
                showSoftInput();
            }
        }
    }

    private void showBottomLayout() {
        int softInputHeight = getSupportSoftInputHeight();
         if (softInputHeight == 0) {
            softInputHeight = mSp.getInt(SHARE_PREFERENCE_TAG, dip2Px(270));
        }
        hideSoftInput();
//        mBottomLayout.getLayoutParams().height = softInputHeight;
        new Handler().postDelayed(() -> {
            mBottomLayout.setVisibility(View.VISIBLE);
            /**
             * 延时执行的代码（解决软键盘还未完全弹出布局上移问题）
             */
        }, 100); // 延时1秒
    }


    private void showEmotionLayout() {
        mEmojiLayout.setVisibility(View.VISIBLE);
        mIvEmoji.setImageResource(R.drawable.keybod);
    }

    private void hideEmotionLayout() {
        mEmojiLayout.setVisibility(View.GONE);
        mIvEmoji.setImageResource(R.drawable.smile);
    }

    /**
     * 是否显示软件盘
     *
     * @return
     */
    public boolean isSoftInputShown() {
        return getSupportSoftInputHeight() != 0;
    }

    public int dip2Px(int dip) {
        float density = mActivity.getApplicationContext().getResources().getDisplayMetrics().density;
        int px = (int) (dip * density + 0.5f);
        return px;
    }


    /**
     * 输入自动匹配快捷回复
     */
    private void setListPopupWindow(){
            if (autoCompleteAdapter == null) {
                List<QuickEntity.DataBean.ListBean> quickelistall = LocalDataSource.getQUICKELISTALL();
                if (!NullUtils.isEmptyList(quickelistall)) {
                    autoCompleteAdapter = new ChannelCompanyAdapter(quickelistall);
                    mEditText.setDropDownBackgroundDrawable(new BitmapDrawable());
                    mEditText.setAdapter(autoCompleteAdapter);
                    mEditText.setDropDownAnchor(R.id.input_lin);
                    mEditText.showDropDown();
                }
            }
    }
    /**
     * 隐藏软件盘
     */
    public void hideSoftInput() {
        mInputManager.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
    }



    /**
     * 获取软件盘的高度
     *
     * @return
     */
    private int getSupportSoftInputHeight() {
        Rect r = new Rect();
        /*  *
         * decorView是window中的最顶层view，可以从window中通过getDecorView获取到decorView。
         * 通过decorView获取到程序显示的区域，包括标题栏，但不包括状态栏。*/
        mActivity.getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
        //获取屏幕的高度
        int screenHeight = mActivity.getWindow().getDecorView().getRootView().getHeight();
        //计算软件盘的高度
        int softInputHeight = screenHeight - r.bottom;

        if (isNavigationBarExist(mActivity)) {
            softInputHeight = softInputHeight - getNavigationHeight(mActivity);
        }
        //存一份到本地
        if (softInputHeight > 0) {
            mSp.edit().putInt(SHARE_PREFERENCE_TAG, softInputHeight).apply();
        }
        return softInputHeight;
    }


    public void showSoftInput() {
        mEditText.requestFocus();
        mEditText.post(new Runnable() {
            @Override
            public void run() {
                mInputManager.showSoftInput(mEditText, 0);
            }
        });
        ((ChatActivity)mActivity).onViewBottom();
    }

    /**
     * 锁定内容高度，防止跳闪
     */
    private void lockContentHeight() {
//        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mContentLayout.getLayoutParams();
//        params.height = mContentLayout.getHeight();
//        params.weight = 0.0F;
    }

    /**
     * 释放被锁定的内容高度
     */
    public void unlockContentHeightDelayed() {
        mEditText.postDelayed(new Runnable() {
            @Override
            public void run() {
//                ((LinearLayout.LayoutParams) mContentLayout.getLayoutParams()).weight = 1.0F;
            }
        }, 200L);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private int getSoftButtonsBarHeight() {
        DisplayMetrics metrics = new DisplayMetrics();
        mActivity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int usableHeight = metrics.heightPixels;
        mActivity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int realHeight = metrics.heightPixels;
        if (realHeight > usableHeight) {
            return realHeight - usableHeight;
        } else {
            return 0;
        }
    }


    private static final String NAVIGATION = "navigationBarBackground";

    // 该方法需要在View完全被绘制出来之后调用，否则判断不了
    //在比如 onWindowFocusChanged（）方法中可以得到正确的结果
    public boolean isNavigationBarExist(@NonNull Activity activity) {
        ViewGroup vp = (ViewGroup) activity.getWindow().getDecorView();
        if (vp != null) {
            for (int i = 0; i < vp.getChildCount(); i++) {
                vp.getChildAt(i).getContext().getPackageName();
                if (vp.getChildAt(i).getId() != View.NO_ID &&
                        NAVIGATION.equals(activity.getResources().getResourceEntryName(vp.getChildAt(i).getId()))) {
                     return true;
                }
            }
        }
        return false;
    }


    public int getNavigationHeight(Context activity) {
        if (activity == null) {
            return 0;
        }
        Resources resources = activity.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height",
                "dimen", "android");
        int height = 0;
        if (resourceId > 0) {
            //获取NavigationBar的高度
            height = resources.getDimensionPixelSize(resourceId);
        }
        return height;
    }


}
