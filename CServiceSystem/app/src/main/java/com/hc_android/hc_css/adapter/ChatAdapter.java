package com.hc_android.hc_css.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;
import androidx.core.util.Pair;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.request.ImageRequest;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.api.Address;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.entity.FileEntity;
import com.hc_android.hc_css.entity.FormEntity;
import com.hc_android.hc_css.entity.MenuEntity;
import com.hc_android.hc_css.entity.MessageEntity;
import com.hc_android.hc_css.entity.TeamEntity;
import com.hc_android.hc_css.ui.activity.ChatActivity;
import com.hc_android.hc_css.ui.activity.FileBrowsActivity;
import com.hc_android.hc_css.ui.activity.PlayActivity;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.DateUtils;
import com.hc_android.hc_css.utils.FileUtils;
import com.hc_android.hc_css.utils.JsonParseUtils;
import com.hc_android.hc_css.utils.MoonEmoji.MoonUtils;
import com.hc_android.hc_css.utils.NullUtils;
import com.hc_android.hc_css.utils.UriUtils;
import com.hc_android.hc_css.utils.android.SpannableStringUtils;
import com.hc_android.hc_css.utils.android.SystemUtils;
import com.hc_android.hc_css.utils.android.app.DataProce;
import com.hc_android.hc_css.utils.android.image.FrescoUtils;
import com.hc_android.hc_css.wight.LocalDataSource;
import com.hc_android.hc_css.wight.media.MediaManager;
import com.hc_android.hc_css.wight.span.selectText.OperationItem;
import com.hc_android.hc_css.wight.span.selectText.SelectableTextView;

import java.util.Date;
import java.util.List;

import cc.shinichi.library.ImagePreview;

import static com.hc_android.hc_css.utils.Constant._CARD;
import static com.hc_android.hc_css.utils.Constant._EVALUATE;
import static com.hc_android.hc_css.utils.Constant._FILE;
import static com.hc_android.hc_css.utils.Constant._FORM;
import static com.hc_android.hc_css.utils.Constant._HTML;
import static com.hc_android.hc_css.utils.Constant._IMAGE;
import static com.hc_android.hc_css.utils.Constant._MENU;
import static com.hc_android.hc_css.utils.Constant._SYSTEM;
import static com.hc_android.hc_css.utils.Constant._TEXT;
import static com.hc_android.hc_css.utils.Constant._TIME;
import static com.hc_android.hc_css.utils.Constant._VIDEO;
import static com.hc_android.hc_css.utils.Constant._VOICE;

public class ChatAdapter extends BaseMultiItemQuickAdapter<MessageEntity.MessageBean, BaseViewHolder> {


    AnimationDrawable drawableing;//正在播放动画的图片

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public ChatAdapter(@NonNull List<MessageEntity.MessageBean> data) {
        super(data);
        addItemType(Constant.CHAT_LEFT, R.layout.left_chat_item);
        addItemType(Constant.CHAT_RIGHT, R.layout.right_chat_item);
        addItemType(Constant.CHAT_CENTER, R.layout.center_chat_item);
    }


    @Override
    protected void convert(@NonNull BaseViewHolder helper, MessageEntity.MessageBean item) {

        if (helper.getItemViewType() == Constant.CHAT_LEFT) {
            DataProce.setImageView(item.getListBean(), helper);
            helper.addOnClickListener(R.id.simple_drawViews);
            helper.addOnClickListener(R.id.img_lin);
        }
        if (helper.getItemViewType() == Constant.CHAT_RIGHT) {
            String head = null;
            List<TeamEntity.DataBean.ListBean> teamserlist = LocalDataSource.getTEAMSERLIST();
            if (!NullUtils.isNull(teamserlist)) {
                for (int i = teamserlist.size() - 1; i >= 0; i--) {
                    if (teamserlist.get(i).get_id().equals(item.getServiceId())) {
                        head = teamserlist.get(i).getHead();
                    }
                }
            }

                SimpleDraweeView draweeView = helper.getView(R.id.chat_header);
            if (head != null) {
                FrescoUtils.setRoundGif(draweeView, UriUtils.getUri(Constant.ONLINEPIC, Address.IMG_URL + head));
            }else {
                FrescoUtils.setRoundGif(draweeView, UriUtils.getUri(Constant.ONLINEPIC, Address.SYSTEM_URL+"image/defaultAvatar.jpeg"));
            }
        }
        LinearLayout view = helper.getView(R.id.content_view);

        if(item.getOneway()!=null&&item.getOneway().equals("customer")){
            helper.getConvertView().setVisibility(View.GONE);//消息评价系统消息隐藏不显示
            view.setPadding(0,0,0,0);
        }else {
            helper.getConvertView().setVisibility(View.VISIBLE);
        }
        //消息撤回
        if (item.isUndo()){
            String textUndo="你撤回了一条消息";
            String serviceId = item.getServiceId();
            List<TeamEntity.DataBean.ListBean> teamserlist = LocalDataSource.getTEAMSERLIST();
            if (teamserlist!=null&&!serviceId.equals(BaseApplication.getUserBean().getId())){
                for (TeamEntity.DataBean.ListBean listBean : teamserlist) {
                    if (listBean.get_id().equals(serviceId)){
                        textUndo=listBean.getName()+"撤回了一条消息";
                    }
                }
            }
            view.removeAllViews();
            view.addView(addViewText());
            TextView textView = view.findViewById(R.id.content_tv);
            textView.setText(textUndo);
            textView.setTextSize(14);
            textView.setTextColor(mContext.getResources().getColor(R.color.black_999));
            textView.setBackground(null);
            return;
        }
        switch (item.getType()) {
            case _TEXT:
                if (addViewText() != null) {
                    view.removeAllViews();
                    view.addView(addViewText());
                    sendState(item, view);
                    SelectableTextView textView = view.findViewById(R.id.content_tv);
//                    helper.addOnLongClickListener(R.id.content_tv);
                    textView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View view) {
                            textView.isLinkIsResponseLongClick();
                            textView.setLinkIsResponseLongClick(false);
                            textView.setInitDate(item,true);//设置是否显示撤回菜单
                            textView.setonClickUndoListener(new SelectableTextView.OperationItemClickListener() {//撤回点击事件
                                @Override
                                public void onClickUndo(OperationItem items) {//传给item点击事件
                                    ((ChatActivity)mContext).toUndo(item,textView);
                                }
                            });
                            return true;
                        }
                    });

                    if (item.getItemType() == Constant.CHAT_LEFT) {
                        textView.setBackgroundResource(R.drawable.left);
                        textView.setPadding(40, 20, 20, 20);
                    }
                    if (item.getItemType() == Constant.CHAT_RIGHT) {
                        textView.setBackgroundResource(R.drawable.right);
                        textView.setPadding(30, 20, 40, 20);
                    }

                    if (item.getItemType() == Constant.CHAT_CENTER) {
                        textView.setBackgroundResource(R.drawable.white_et);
                        textView.setPadding(30, 20, 30, 20);
                        view.setPadding(20,20,20,20);
                    }
                    MoonUtils.identifyFaceExpressionAndATags(mContext, textView, item.getContents(), ImageSpan.ALIGN_BOTTOM);

                }

//                View view2 = helper.getView(R.id.send_failed);
//                if (view2!=null){
////                    helper.addOnClickListener(R.id.send_failed);
//                    view2.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//
////                    chatList.remove(messageBean);
////                    chatAdapter.notifyDataSetChanged();
////                            CacheData.removeCache(item.getId(), item);
//                            ((ChatActivity)mContext).repeatSend(item);
////                            sendMsg(messageBean.getType(), messageBean.getContents(), false);//重新发送
//                        }
//                    });
//                }
                break;
            case _IMAGE:
                if (addViewImage() != null) {
                    view.removeAllViews();
                    view.addView(addViewImage());
                    sendState(item, view);
                    SimpleDraweeView img = view.findViewById(R.id.chat_img);
                    LinearLayout lin = view.findViewById(R.id.img_layout);
                    onItemSetLongClickListener(lin,item);
                    FileEntity fileEntity = JsonParseUtils.parseToObject(item.getContents(), FileEntity.class);
                    FrescoUtils.setControllerListener(img, fileEntity, mContext);
                    try{
                    lin.setOnClickListener(view1 ->
                            ImagePreview.getInstance()
                            .setContext(mContext, img)
                            // 设置从第几张开始看（索引从0开始）
                            .setIndex(0)
                            .setLoadStrategy(ImagePreview.LoadStrategy.AlwaysOrigin)
                            // 有三种设置数据集合的方式，根据自己的需求进行三选一：
                            // 1：第一步生成的imageInfo List
//                                    .setImageInfoList(imageInfoList)
                            // 2：直接传url List
                            //.setImageList(List<String> imageList)
                            // 3：只有一张图片的情况，可以直接传入这张图片的url
                            .setImage(com.hc_android.hc_css.utils.android.image.UriUtils.getUriInstance().getUri(fileEntity).toString())
                            .isGif(fileEntity.getPicUrl())
                            .setEnableUpDragClose(true)
                            .setEnableClickClose(true)
                            .setEnableDragClose(true)
                            .setDownIconResId(R.mipmap.download)
                            // 开启预览
                            .start());
                    }catch (Exception e){}
                }
                helper.addOnClickListener(R.id.send_failed);
                break;
            case _VIDEO:
                addFile(item, view);
                View lin_video=view.findViewById(R.id.video_click);
                onItemSetLongClickListener(lin_video,item);
                helper.addOnClickListener(R.id.send_failed);
                break;
            case _FILE:
                addFile(item, view);
                View lin_file=view.findViewById(R.id.file_lin);
                onItemSetLongClickListener(lin_file,item);
                break;
            case _TIME:
                Date date = new Date(item.getTime());
                if (addTimeView() != null) {
                    view.removeAllViews();
                    view.addView(addTimeView());
                    TextView textView = view.findViewById(R.id.chat_item_time);
                    textView.setText(DateUtils.formationDate(date));
                }
                helper.addOnClickListener(R.id.send_failed);
                break;
            case _SYSTEM:
                view.removeAllViews();
                if (addEvaluateView() != null && !isCustom(item)) {
                    view.addView(addEvaluateView());
                    SelectableTextView textView = view.findViewById(R.id.chat_item_system);
                    textView.setPadding(20, 20, 20, 20);
                    textView.setText(item.getContents());
                    setOnLongClicklistener(textView,item);

                }
                helper.addOnClickListener(R.id.send_failed);
                break;
            case _EVALUATE://隐藏dialogId类型的消息
                view.removeAllViews();
                if (addEvaluateView() != null && !isCustom(item)) {
                    view.addView(addEvaluateView());
                    FileEntity fileEntity = JsonParseUtils.parseToObject(item.getContents(), FileEntity.class);
                    if (fileEntity != null) {
                        int score = fileEntity.getScore();
                        SelectableTextView textView = view.findViewById(R.id.chat_item_system);
                        SpannableStringBuilder stringBuilder = new SpannableStringBuilder("顾客已评价 ");
                        SpannableStringUtils.RoundedBackgroundSpan span = null;
                        String tag = "";
                        switch (score) {

                            case 0:
                                tag = "差评";
                                span = new SpannableStringUtils.RoundedBackgroundSpan(ContextCompat.getColor(mContext, R.color.red), ContextCompat.getColor(mContext, R.color.white));
                                break;
                            case 1:
                                tag = "中评";
                                span = new SpannableStringUtils.RoundedBackgroundSpan(ContextCompat.getColor(mContext, R.color.bg_yel), ContextCompat.getColor(mContext, R.color.black));
                                break;
                            case 2:
                                tag = "好评";
                                span = new SpannableStringUtils.RoundedBackgroundSpan(ContextCompat.getColor(mContext, R.color.green_btn), ContextCompat.getColor(mContext, R.color.white));
                                break;
                        }

                        stringBuilder.append(tag);
                        stringBuilder.setSpan(span, stringBuilder.length() - tag.length(), stringBuilder.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        if (!NullUtils.isNull(fileEntity.getContent())) {
                            stringBuilder.append(" " + fileEntity.getContent());
                        }
                        textView.setText(stringBuilder);
                        setOnLongClicklistener(textView,item);
                    }


                }

//                if (item.getOneway()!=null&&item.getOneway().equals("customer")&&item.isSystem()&&item.getType()!=null&&item.getType().equals("evaluate")){
//                    //系统消息隐藏
//                    view.setVisibility(View.GONE);
//                }else {
//                    view.setVisibility(View.VISIBLE);
//                }
                helper.addOnClickListener(R.id.send_failed);

                break;
            case _VOICE://语音
                if (addAudioVideo(item.getItemType()) != null) {
                    view.removeAllViews();
                    view.addView(addAudioVideo(item.getItemType()));
                    sendState(item, view);

                    TextView textView = view.findViewById(R.id.tv_num);
                    ImageView imgae = view.findViewById(R.id.img_audio);
                    LinearLayout lin = view.findViewById(R.id.chat_audio_lin);
                    onItemSetLongClickListener(lin,item);
                    AnimationDrawable drawable = (AnimationDrawable) imgae.getBackground();
                    FileEntity fileEntity3 = JsonParseUtils.parseToObject(item.getContents(), FileEntity.class);
                    //根据语音时长设置控件宽度
                    int rint = (int) Math.rint(fileEntity3.getDuration());
                    textView.setText(rint + "''");
                    rint = rint > 90 ? 90 : rint;//限制最大长度
                    ViewGroup.LayoutParams layoutParams = lin.getLayoutParams();
                    layoutParams.width = 200 + rint * 5;
                    lin.setLayoutParams(layoutParams);

                    lin.setOnClickListener(view12 -> {
                        voiceState(drawable, fileEntity3);
                    });
                }
                helper.addOnClickListener(R.id.send_failed);
                break;

            case _HTML:
                view.removeAllViews();
                if (addHtmlText() != null && !isCustom(item)) {
                    view.addView(addHtmlText());
                    sendState(item, view);
                    WebView htmlTextVie = view.findViewById(R.id.html_text);
                    htmlTextVie.setActivated(true);
                    onItemSetLongClickListener(view,item);
                    htmlTextVie.loadDataWithBaseURL(null, item.getContents(), "text/html", "utf-8", null);
                }
                helper.addOnClickListener(R.id.send_failed);
                break;
            case _CARD:
                view.removeAllViews();
                if (addCardView() != null && !isCustom(item)) {
                    view.addView(addCardView());
                    View view1 = helper.getView(R.id.card_lin);
                    onItemSetLongClickListener(view1,item);
                    sendState(item, view);
                    FileEntity fileEntity3 = JsonParseUtils.parseToObject(item.getContents(), FileEntity.class);
                    FileEntity.ListBean listBean = fileEntity3.getList().get(0);
                    LinearLayout lin = view.findViewById(R.id.card_lin);
                    SimpleDraweeView img = view.findViewById(R.id.card_img);
                    SelectableTextView title = view.findViewById(R.id.title_card);
                    SelectableTextView dec = view.findViewById(R.id.dec_card);
                    SelectableTextView price = view.findViewById(R.id.price_card);
                    setOnLongClicklistener(title,item);
                    setOnLongClicklistener(dec,item);
                    setOnLongClicklistener(price,item);
                    DraweeController controller = Fresco.newDraweeControllerBuilder()
                            .setImageRequest(ImageRequest.fromUri(UriUtils.getUri(Constant.ONLINEPIC, Address.IMG_URL + listBean.getPicurl())))//加载原图
                            .setAutoPlayAnimations(true)
                            .build();
                    img.setController(controller);

                    title.setText(listBean.getTitle());
                    if (!NullUtils.isNull(listBean.getDescription())) {
                        dec.setVisibility(View.VISIBLE);
                        dec.setText(listBean.getDescription());
                    } else {
                        dec.setVisibility(View.GONE);
                    }
                    if (!NullUtils.isNull(listBean.getPrice())) {
                        price.setVisibility(View.VISIBLE);
                        price.setText(listBean.getPrice());
                    } else {
                        dec.setVisibility(View.GONE);
                    }
                    lin.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                Intent intent = new Intent();
                                intent.setAction("android.intent.action.VIEW");
                                Uri targetUrl = Uri.parse(listBean.getUrl());
                                intent.setData(targetUrl);
                                mContext.startActivity(intent);
                            }catch (Exception e){}
                        }
                    });
                }
                helper.addOnClickListener(R.id.send_failed);
                break;
            case _MENU:
                view.removeAllViews();
                if (addMenuView() != null && !isCustom(item)) {
                    if (item.getOneway() == null || !item.getOneway().equals("customer")) {
                        view.addView(addMenuView());
                        sendState(item, view);
                        SelectableTextView title = view.findViewById(R.id.menu_title);
                        SelectableTextView end = view.findViewById(R.id.menu_end);
                        RecyclerView list = view.findViewById(R.id.menu_list);
                        LinearLayout lin=view.findViewById(R.id.menu_lin);
                        onItemSetLongClickListener(lin,item);
                        setOnLongClicklistener(title,item);
                        setOnLongClicklistener(end,item);
                        MenuEntity menuEntity = JsonParseUtils.parseToObject(item.getContents(), MenuEntity.class);
                        if (!NullUtils.isNull(menuEntity.getHead_content())){
                            title.setVisibility(View.VISIBLE);
                            title.setText(menuEntity.getHead_content());
                        }else {
                            title.setVisibility(View.GONE);
                        }
                        if (!NullUtils.isNull(menuEntity.getTail_content())){
                            end.setVisibility(View.VISIBLE);
                            end.setText(menuEntity.getTail_content());
                        }else {
                            end.setVisibility(View.GONE);
                        }
                        if (!NullUtils.isEmptyList(menuEntity.getList())) {
                            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                            list.setLayoutManager(layoutManager);
                            list.setAdapter(new MenuAdapter(menuEntity.getList()));
                        }
                    }
                }
                helper.addOnClickListener(R.id.send_failed);
                break;
            case _FORM:

                if (item.getSendType().equals("service")) {
                    view.removeAllViews();

                    if (addEvaluateView() != null && !isCustom(item)) {
                        view.addView(addEvaluateView());
                        sendState(item, view);
                        SelectableTextView textView = view.findViewById(R.id.chat_item_system);
                        textView.setText("已发送表单邀请顾客填写");
                        setOnLongClicklistener(textView,item);

                    }
                } else {
                    view.removeAllViews();
                    if (addFormView() != null && !isCustom(item)) {
                        view.addView(addFormView());
                        sendState(item, view);
                        RecyclerView list = view.findViewById(R.id.form_list);
                        List<FormEntity> formEntities = JsonParseUtils.parseToPureList(item.getContents(), FormEntity.class);
                        if (!NullUtils.isEmptyList(formEntities)) {
                            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                            list.setLayoutManager(layoutManager);
                            list.setAdapter(new FormAdapter(formEntities));
                        }
                    }
                }
                helper.addOnClickListener(R.id.send_failed);
                break;
        }


    }

    /**
     * 设置各项长按监听事件(解决部分点击事件长按无效)
     * @param view
     * @param item
     */
    private void onItemSetLongClickListener(View view, MessageEntity.MessageBean item) {

        if (view!=null) {
            view.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    ((ChatActivity) mContext).toUndo(item, view);
                    return true;
                }
            });
        }
    }

    /**
     * 判断是否是发送给顾客的消息
     *
     * @param item
     * @return
     */
    private boolean isCustom(MessageEntity.MessageBean item) {

        if (item.getOneway() != null && item.getOneway().equals("customer")) {

            return true;
        } else {
            return false;
        }
    }

    /***
     * 文件类型包含视频文件
     * @param item
     * @param view
     */
    private void addFile(MessageEntity.MessageBean item, LinearLayout view) {
        FileEntity fileEntity2 = JsonParseUtils.parseToObject(item.getContents(), FileEntity.class);
        if (fileEntity2.getType().contains("video")) {
            if (addViewVideo() != null) {
                view.removeAllViews();
                view.addView(addViewVideo());
                sendState(item, view);
                ImageView draweeView = view.findViewById(R.id.chat_video_img);
                RelativeLayout click = view.findViewById(R.id.video_click);

                setImageLayout(draweeView, fileEntity2);
                //设置图片圆角角度
                RoundedCorners roundedCorners = new RoundedCorners(6);
                //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
                RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
                String imagePath = Address.FILE_URL + fileEntity2.getFileUrl() + "?vframe/png/offset/0";//视频缩略图
                String filePath = Address.FILE_URL + fileEntity2.getFileUrl();
                if (fileEntity2.isLocalPath()) {
                    imagePath = fileEntity2.getFileUrl();
                    filePath = fileEntity2.getFileUrl();
                }
                Glide.with(mContext).load(filePath).placeholder(R.mipmap.photos).apply(options).into(draweeView);
                String finalFilePath = filePath;

                click.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, PlayActivity.class);
                        intent.putExtra(PlayActivity.TRANSITION, true);
                        intent.putExtra(_FILE, finalFilePath);
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                            Pair pair = new Pair<>(view, PlayActivity.IMG_TRANSITION);
                            ActivityOptionsCompat activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                                    (Activity) mContext, pair);
                            ActivityCompat.startActivity(mContext, intent, activityOptions.toBundle());
                        } else {
                            mContext.startActivity(intent);
                            ((Activity) mContext).overridePendingTransition(R.anim.abc_fade_in, R.anim.abc_fade_out);
                        }
                    }
                });


            }


        } else {
            if (addViewFile() != null) {
                view.removeAllViews();
                view.addView(addViewFile());
                sendState(item, view);
                TextView size = view.findViewById(R.id.filesize);
                TextView name = view.findViewById(R.id.filename);
                TextView type = view.findViewById(R.id.fileTypes);
                RelativeLayout bg = view.findViewById(R.id.filetype);
                RelativeLayout lin = view.findViewById(R.id.file_lin);
                size.setText(FileUtils.bytes2kb(fileEntity2.getSize()));
                name.setText(fileEntity2.getName());
                type.setText(FileUtils.fileType(fileEntity2.getName(), fileEntity2.getType(), bg));
                lin.setOnClickListener(view1 -> FileBrowsActivity.show(mContext, fileEntity2));
            }
        }
    }

    /**
     * 设置消息发送状态
     * @param item
     * @param view
     */
    private void sendState(MessageEntity.MessageBean item, LinearLayout view) {
        if (item.getSendState() != null) {
            if (item.getSendState().equals(Constant._ISLOADING)) {
                view.addView(addSendLoading(), 0);
            }
            if (item.getSendState().equals(Constant._ISFAILED)) {
                view.addView(addSendFailed(), 0);
                view.findViewById(R.id.send_failed).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((ChatActivity)mContext).repeatSend(item);
                    }
                });
            }
        }
    }

    /**
     * 设置语音状态
     *
     * @param drawable
     * @param fileEntity
     */
    private void voiceState(AnimationDrawable drawable, FileEntity fileEntity) {
        MediaManager.reset();
        if (drawableing != null) {//关闭上次打开打开的语音
            if (drawableing.isRunning() && drawable.equals(drawableing)) {
                drawableing.stop();
                drawableing.selectDrawable(0);
                return;//点击的同一个就不去打开，置为关闭状态
            }
            drawableing.stop();
            drawableing.selectDrawable(0);

        }
        drawable.start();
        drawableing = drawable;
        String url = fileEntity.isLocalPath() ? fileEntity.getKey() : Address.FILE_URL + fileEntity.getKey();
        MediaManager.playSound(mContext, url, mp -> {
            drawable.stop();
            drawable.selectDrawable(0);
            MediaManager.release();
        }, new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.start();
            }
        });
    }

    /**
     * 根据图片大小设置image显示大小
     *
     * @param draweeView
     * @param fileEntity
     */
    private void setImageLayout(View draweeView, FileEntity fileEntity) {

        float width = 0;
        float height = 0;
        if (fileEntity.getType().contains("video") && fileEntity.getThumbImg() != null && fileEntity.getThumbImg().getWidth() != 0 && fileEntity.getThumbImg().getHeight() != 0) {
            width = fileEntity.getThumbImg().getWidth();
            height = fileEntity.getThumbImg().getHeight();

        }
        if (fileEntity.getType().contains("image") && fileEntity.getWidth() != 0 && fileEntity.getHeight() != 0) {
            width = fileEntity.getWidth();
            height = fileEntity.getHeight();

        }
        int[] screenDispaly = SystemUtils.getScreenDispaly(mContext);
        float containerWidth = (screenDispaly[0]) * 2 / 5;//不超过屏幕宽度的2/5
        float containerHeight = (screenDispaly[1]) * 1 / 2;//不超过屏幕高度1/2

        if (width > containerWidth) {

            height = containerWidth / width * height;
            width = containerWidth;
        }

        if (height > containerHeight) {
            width = containerHeight / height * width;
            height = containerHeight;
        }
        ViewGroup.LayoutParams layoutParams = draweeView.getLayoutParams();
        layoutParams.width = (int) width;
        layoutParams.height = (int) height;
        draweeView.setLayoutParams(layoutParams);

    }

    //文本
    private View addViewText() {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.chat_text_item, null);
        return view;
    }

    //图片
    private View addViewImage() {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.chat_img_item, null);

        return view;
    }

    //视频
    private View addViewVideo() {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.chat_video_item, null);

        return view;
    }

    //语音
    private View addAudioVideo(int itemType) {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = null;
        if (itemType == Constant.CHAT_RIGHT)
            view = inflater.inflate(R.layout.chat_audio_item_right, null);
        if (itemType == Constant.CHAT_LEFT)
            view = inflater.inflate(R.layout.chat_audio_item_left, null);

        return view;
    }

    //文件
    private View addViewFile() {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.chat_file_item, null);
        return view;

    }

    //富文本显示
    private View addHtmlText() {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.chat_html_item, null);
        return view;

    }

    //富文本显示
    private View addCardView() {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.chat_card_item, null);
        return view;

    }

    //菜单显示
    private View addMenuView() {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.chat_menu_item, null);
        return view;

    }

    //表单显示
    private View addFormView() {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.chat_form_item, null);
        return view;

    }

    //顾客评价中间布局
    private View addEvaluateView() {

        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.chat_evaluate_item, null);
        return view;

    }

    //记录时间的item
    private View addTimeView() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.chat_time_item, null);
        return view;

    }

    //消息发送失败
    public View addSendFailed() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.chat_send_failed_item, null);

        return view;

    }

    //消息发送中
    public View addSendLoading() {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.chat_send_loading_item, null);
        return view;

    }

    /**
     * 通过imageWidth 的宽度，自动适应高度
     * * @param simpleDraweeView view
     * * @param imagePath  Uri
     * * @param imageWidth width
     */
    public void setControllerListener(final SimpleDraweeView simpleDraweeView, FileEntity fileEntity, Context mContext) {


        setImageLayout(simpleDraweeView, fileEntity);
        String imagePath = null;
        if (fileEntity.getType().contains("video")) {
            imagePath = Address.FILE_URL + fileEntity.getFileUrl() + "?vframe/png/offset/0";//视频缩略图
        }
        if (fileEntity.getType().contains("image")) {
            int width = simpleDraweeView.getLayoutParams().width;
            int height = simpleDraweeView.getLayoutParams().height;
            imagePath = Address.IMG_URL + fileEntity.getPicUrl() + "?imageView2/2/w/" + width / 2 + "/h/" + height / 2;//第一次加载图片缩略图(压缩图片显示// )
        }
//        RoundedCorners roundedCorners = new RoundedCorners(6);
//        //通过RequestOptions扩展功能,override:采样率,因为ImageView就这么大,可以压缩图片,降低内存消耗
//        RequestOptions options = RequestOptions.bitmapTransform(roundedCorners);
//        Glide.with(mContext).load(Address.IMG_URL + fileEntity.getPicUrl()).apply(options).thumbnail(0.1f).into(simpleDraweeView);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setLowResImageRequest(ImageRequest.fromUri(UriUtils.getUri(Constant.ONLINEPIC, imagePath)))//加载缩略图
                .setImageRequest(ImageRequest.fromUri(UriUtils.getUri(Constant.ONLINEPIC, Address.IMG_URL + fileEntity.getPicUrl())))//加载原图
                .setAutoPlayAnimations(true)
                .build();
        simpleDraweeView.setController(controller);

    }

    public void  setOnLongClicklistener(SelectableTextView selectableTextView, MessageEntity.MessageBean item){

        selectableTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                selectableTextView.setInitDate(item,false);//设置是否显示撤回菜单
                selectableTextView.setonClickUndoListener(new SelectableTextView.OperationItemClickListener() {//撤回点击事件
                    @Override
                    public void onClickUndo(OperationItem items) {//传给item点击事件
                        ((ChatActivity)mContext).toUndo(item,selectableTextView);
                    }
                });
                return true;
            }
        });
    }

}
