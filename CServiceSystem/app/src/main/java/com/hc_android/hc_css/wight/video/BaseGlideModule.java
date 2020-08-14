package com.hc_android.hc_css.wight.video;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;


@GlideModule
public class BaseGlideModule extends AppGlideModule {
    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {
        super.registerComponents(context, glide, registry);

        // 替换底层网络框架为okhttp3
//        registry.replace(GlideUrl.class, InputStream.class,
//                new OkHttpUrlLoader.Factory(ProgressManager.getOkHttpClient()));
    }
}
