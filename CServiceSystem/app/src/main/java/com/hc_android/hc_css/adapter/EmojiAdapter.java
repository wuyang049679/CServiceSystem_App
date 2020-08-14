package com.hc_android.hc_css.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.entity.EmojiEntity;
import com.hc_android.hc_css.utils.FileUtils;

import java.util.List;

public class EmojiAdapter extends BaseQuickAdapter<EmojiEntity, BaseViewHolder> {
    public EmojiAdapter(@Nullable List<EmojiEntity> data) {
        super(R.layout.emoji_item,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, EmojiEntity item) {

        ImageView img = helper.getView(R.id.emoji_img);
        img.setImageBitmap(FileUtils.getImageFromAssetsFile(mContext,"stickers/"+item.getPic()));
        helper.addOnClickListener(R.id.emoji_btn);
    }
}
