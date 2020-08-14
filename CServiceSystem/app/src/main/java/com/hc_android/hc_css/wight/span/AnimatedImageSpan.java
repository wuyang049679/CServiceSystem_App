package com.hc_android.hc_css.wight.span;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.DynamicDrawableSpan;
import android.widget.TextView;

import com.hc_android.hc_css.R;

import java.lang.ref.WeakReference;

public class AnimatedImageSpan extends DynamicDrawableSpan {

    private Drawable mDrawable;

    public AnimatedImageSpan(Drawable d) {
        super();
        mDrawable = d;
        // Use handler for 'ticks' to proceed to next frame
        final Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            public void run() {
                ((AnimatedGifDrawable)mDrawable).nextFrame();
                // Set next with a delay depending on the duration for this frame
                mHandler.postDelayed(this, ((AnimatedGifDrawable)mDrawable).getFrameDuration());
            }
        });
    }
    @Override
    public Drawable getDrawable() {
        return ((AnimatedGifDrawable)mDrawable).getDrawable();
    }


@Override
public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
    try {
        Drawable d = getDrawable();
        Rect rect = d.getBounds();
        if (fm != null) {
            Paint.FontMetricsInt fmPaint = paint.getFontMetricsInt();
            int fontHeight = fmPaint.bottom - fmPaint.top;
            int drHeight = rect.bottom - rect.top;

            int top = drHeight / 2 - fontHeight / 4;
            int bottom = drHeight / 2 + fontHeight / 4;

            fm.ascent = -bottom;
            fm.top = -bottom;
            fm.bottom = top;
            fm.descent = top;
        }
        return rect.right;
    } catch (Exception e) {
        return 20;
    }
}

@Override
public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
    try {
        Drawable d = getDrawable();
        canvas.save();
        int transY = 0;
        transY = ((bottom-top) - d.getBounds().bottom) / 2+top;
        canvas.translate(x, transY);
        d.draw(canvas);
        canvas.restore();
    } catch (Exception e) {
    }
}


    public static SpannableString convertNormalStringToSpannableString(String message , TextView tv, Context content) {
        SpannableString value = SpannableString.valueOf(message);

                    @SuppressLint("ResourceType")
                    WeakReference<AnimatedImageSpan> localImageSpanRef = new WeakReference<AnimatedImageSpan>(new AnimatedImageSpan(new AnimatedGifDrawable(content.getResources().openRawResource(R.drawable.chat_loading), new AnimatedGifDrawable.UpdateListener() {
                        @Override
                        public void update() {//update the textview
                            if(tv!=null){
                                tv.postInvalidate();
                            }
                        }
                    })));

                    value.setSpan(localImageSpanRef.get(), message.length()-1, message.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);

        return value;
    }
}