package com.hc_android.hc_css.utils.android.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.graphics.ColorUtils;

import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.api.Address;
import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.entity.FileEntity;
import com.hc_android.hc_css.entity.MessageDialogEntity;
import com.hc_android.hc_css.entity.TagEntity;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.DateUtils;
import com.hc_android.hc_css.utils.HtmlUtils;
import com.hc_android.hc_css.utils.JsonParseUtils;
import com.hc_android.hc_css.utils.NullUtils;
import com.hc_android.hc_css.utils.UriUtils;
import com.hc_android.hc_css.utils.android.SharedPreferencesUtils;
import com.hc_android.hc_css.utils.android.image.FrescoUtils;
import com.hc_android.hc_css.wight.LocalDataSource;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataProce {



    /**
     * 设置图片
     * @param item
     * @param helper
     */
    @SuppressLint("WrongConstant")
    public static void setImageView(MessageDialogEntity.DataBean.ListBean item, BaseViewHolder helper) {
        SimpleDraweeView draweeView = helper.getView(R.id.simple_drawViews);
        LinearLayout img_lin = helper.getView(R.id.img_lin);
        ImageView img = helper.getView(R.id.img_icon);
        int resId=0;
        if (item.getSource().equals("web")||item.getSource().equals("link")){

            switch (item.getDevice().getType()) {
                case "Desktop":
                    resId=setDeskTop(item);
                    break;
                case "Android":
                    resId=R.mipmap.android;
                    break;
                case "IOS":
                    resId=R.mipmap.ios;
                    break;
            }
        }

        if (item.getSource().equals("program")) resId = R.mipmap.xcx;
        if(item.getSource().equals("wechat"))resId=R.mipmap.weixin;
        if (item.getCustomer().getHead()!=null){
            img_lin.setVisibility(View.GONE);
            if (item.getSource().equals("web")||item.getSource().equals("link")){
                FrescoUtils.setRoundGif(draweeView,UriUtils.getUri(Constant.ONLINEPIC, item.getCustomer().getHead()));
            }else {
                if (!item.getCustomer().getHead().equals("undefined")){
                    FrescoUtils.setRoundGif(draweeView,UriUtils.getUri(Constant.ONLINEPIC, Address.IMG_URL+item.getCustomer().getHead()+"?imageView2/1/w/100/h/100"));
                }else {
                    FrescoUtils.setRoundGif(draweeView,UriUtils.getUri(Constant.ONLINEPIC, Address.SYSTEM_URL+"image/defaultAvatar.jpeg"));
                }
            }
        }else {
            img_lin.setVisibility(View.VISIBLE);
            img.setImageResource(resId);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(GradientDrawable.RECTANGLE);
            drawable.setGradientType(GradientDrawable.RECTANGLE);
            drawable.setCornerRadius(6);
            if (item.getCustomerOffTime()==null){
                drawable.setColor(BaseApplication.getContext().getResources().getColor(R.color.theme_app));
            }else {
                drawable.setColor(BaseApplication.getContext().getResources().getColor(R.color.black_cc));
            }
            img_lin.setBackground(drawable);
        }
    }
    /**
     * 设置图片(正在输入状态头像)
     * @param item
     * @param helper
     */
    @SuppressLint("WrongConstant")
    public static void setImageView(MessageDialogEntity.DataBean.ListBean item, View helper) {
        try {
            SimpleDraweeView draweeView = helper.findViewById(R.id.simple_drawViews);
            LinearLayout img_lin = helper.findViewById(R.id.img_lin);
            ImageView img = helper.findViewById(R.id.img_icon);
            int resId = 0;
            if (item.getSource().equals("web") || item.getSource().equals("link")) {

                switch (item.getDevice().getType()) {
                    case "Desktop":
                        resId = setDeskTop(item);
                        break;
                    case "Android":
                        resId = R.mipmap.android;
                        break;
                    case "IOS":
                        resId = R.mipmap.ios;
                        break;
                }
            }

            if (item.getSource().equals("program")) resId = R.mipmap.xcx;
            if (item.getSource().equals("wechat")) resId = R.mipmap.weixin;
            if (item.getCustomer().getHead() != null) {
                img_lin.setVisibility(View.GONE);
                if (item.getSource().equals("web") || item.getSource().equals("link")) {
                    FrescoUtils.setRoundGif(draweeView, UriUtils.getUri(Constant.ONLINEPIC, item.getCustomer().getHead()));
                } else {
                    if (!item.getCustomer().getHead().equals("undefined")) {
                        FrescoUtils.setRoundGif(draweeView, UriUtils.getUri(Constant.ONLINEPIC, Address.IMG_URL + item.getCustomer().getHead() + "?imageView2/1/w/100/h/100"));
                    } else {
                        FrescoUtils.setRoundGif(draweeView, UriUtils.getUri(Constant.ONLINEPIC, Address.SYSTEM_URL + "image/defaultAvatar.jpeg"));
                    }
                }
            } else {
                img_lin.setVisibility(View.VISIBLE);
                img.setImageResource(resId);
                GradientDrawable drawable = new GradientDrawable();
                drawable.setShape(GradientDrawable.RECTANGLE);
                drawable.setGradientType(GradientDrawable.RECTANGLE);
                drawable.setCornerRadius(6);
                if (item.getCustomerOffTime() == null) {
                    drawable.setColor(BaseApplication.getContext().getResources().getColor(R.color.theme_app));
                } else {
                    drawable.setColor(BaseApplication.getContext().getResources().getColor(R.color.black_cc));
                }
                img_lin.setBackground(drawable);
            }
        }catch (Exception e){}
    }

    /**
     * 设置浏览器类型图标
     * @param item
     */
    private static int setDeskTop(MessageDialogEntity.DataBean.ListBean item) {
        String browser = item.getDevice().getBrowser();
        if (browser.contains("weixin")) return R.mipmap.weixin;
        if (item.getDevice().getSystem().equals("Mac")) return R.mipmap.mac;
        if (browser.contains("Safari")) return R.mipmap.safari;
        if (browser.contains("IE")) return R.mipmap.ie;
        if (browser.contains("Edge")) return R.mipmap.edge;
        if (browser.contains("Firefox")) return R.mipmap.firefox;
        if (browser.contains("Chrome")) return R.mipmap.chrome;
        if (browser.contains("Opera")) return R.mipmap.opera;
        return R.mipmap.desktop;
    }


    /**
     * 更新最新消息内容
     *  @param item
     * @param
     */
    public static String getContent(MessageDialogEntity.DataBean.ListBean item) {
        String contents = "";

        //消息类型
//        export const MessageSketch = (contents, type) => {
//            if(type == 'evaluate')contents = '[顾客已评价]'
//            if(type == 'form')contents = '[表单]'
//            if(type == 'image')contents = '[图片]'
//            if(type == 'voice')contents = '[语音]'
//            if(type == 'menu')contents = '[菜单]'
//            if(type == 'video')contents = '[视频]'
//            if(type == 'card')contents = '[卡片]'
//            if(type == 'file'){
//    const file = JSON.parse(contents);
//                contents = `[文件] ${file.name}`
//                if(file.type.indexOf('video/') == 0)contents = '[视频]'
//            }
//            if(type == 'html')contents = contents.replace(/<[^>]+>/g,"");
//            if(!type || type == 'text')contents = contents.replace(/[\r\n]/g," ") //换行符替换成空格
//            return contents;
//        }
        if (item.getLastMsg()==null)return contents;
        if (item.getLastMsg().getType()==null)return contents;
        String sendType = item.getLastMsg().getType();

        switch (sendType) {

            case "evaluate":
                contents = "[顾客已评价]";
                break;
            case "form":
                contents = "[表单]";
                break;
            case "image":
                contents = "[图片]";
                break;
            case "voice":
                contents = "[语音]";
                break;
            case "menu":
                contents = "[菜单]";
                break;
            case "video":
                contents = "[视频]";
                break;
            case "card":
                contents = "[卡片]";
                break;
            case "file":
                FileEntity fileEntity= JsonParseUtils.parseToObject(item.getLastMsg().getContents(), FileEntity.class);
                contents = "[文件] " + fileEntity.getName();
                if (fileEntity.getType().indexOf("video/") == 0) {
                    contents = "[视频]";
                }
                break;
            case "html":
                contents = HtmlUtils.delHTMLTag(item.getLastMsg().getContents());
                break;
            case "text":
                if (item.getLastMsg().getContents() != null) {
                    contents = item.getLastMsg().getContents().replace("/[\\r\\n]/g", " ");
                }
                break;
        }

        if (item.getItemType()!=0) {
            switch (item.getItemType()) {
                case Constant.NOTRECEIVED_ACT://未接待列表数据显示和已接待,同事列表一样
                case Constant.HAVERECEIVED:
                case Constant.COLLEAGUE_ACT:

                    return contents;
                case Constant.NOTRECEIVED://同事和未接待显示一样
                case Constant.COLLEAGUE:
                    //当来源为web和link是显示地址如果不是则不显示地址
                    if (item.getSource().equals("web") || item.getSource().equals("link")) {
                        if (item.getCustomer().getName() != null) {
                            contents = item.getAddress() + "  " + item.getCustomer().getName() + ": " + contents;
                        } else {
                            contents = item.getAddress() + " #" + item.getCustomer().getNumberId() + ": " + contents;
                        }
                    } else {
                        if (item.getCustomer().getName() != null) {
                            contents = item.getCustomer().getName() + ": " + contents;
                        } else {
                            contents = " #" + item.getCustomer().getNumberId() + ": " + contents;
                        }
                    }
                    return contents;

            }
        }
        return contents;
    }

    /**
     * 设置最后更新时间
     *  @param item
     * @param
     */
    public static String  getUpdateTime(MessageDialogEntity.DataBean.ListBean item) {
        String update=null;
        if (item.getLastMsg()!=null&&item.getLastMsg().getTime() != 0) {
            String dateFormat = DateUtils.formationDate(new Date(item.getLastMsg().getTime()));
            if (!dateFormat.contains("1970")) {//前面设置的时间戳位1970，为辅助数据不显示
                update=dateFormat;
            }
        } else {
            update= DateUtils.getDateFormat(item.getAddtime());
        }
        return update;
    }

    /**
     * 设置未读消息红点
     *  @param item
     * @param
     */
    public static void setMsgPoint(MessageDialogEntity.DataBean.ListBean item,TextView ponit_tv) {
        //设置未读消息数
        if (item.getUnreadNum() == 0) {
            ponit_tv.setVisibility(View.GONE);
        } else {
            ponit_tv.setVisibility(View.VISIBLE);
            ponit_tv.setText(item.getUnreadNum()+"");
            if (item.getUnreadNum() > 9&&item.getUnreadNum()<=99) {
                ponit_tv.setBackground(BaseApplication.getContext().getResources().getDrawable(R.drawable.radius_red));
            } else if (item.getUnreadNum() > 99){
                ponit_tv.setText("99+");
                ponit_tv.setBackground(BaseApplication.getContext().getResources().getDrawable(R.drawable.radius_red));
            }else {
                ponit_tv.setBackground(BaseApplication.getContext().getResources().getDrawable(R.drawable.oval_red));
            }
        }
    }

    /**
     * 设置标题title  地址和名称
     *  @param item
     * @param
     */
    public static String getTitle(MessageDialogEntity.DataBean.ListBean item) {

        String title="";

        switch (item.getItemType()) {

            case Constant.HAVERECEIVED://未接待列表数据显示和已接待,同事列表一样
            case Constant.NOTRECEIVED_ACT:
            case Constant.COLLEAGUE_ACT:
                //设置对话title
                String tv_title = null;
                if (item.getAddress() == null || TextUtils.isEmpty(item.getAddress())) {
                    title="未知地址";
                    tv_title = "未知地址";
                } else {
                    tv_title = item.getAddress();
                }
                //当来源为web和link是显示地址如果不是则不显示地址
                if (!NullUtils.isNull(item.getCustomer().getName())) {
                    if (item.getSource().equals("web") || item.getSource().equals("link")) {
                        tv_title = tv_title + " " + item.getCustomer().getName();
                    } else {
                        tv_title = item.getCustomer().getName();
                    }
                } else {
                    if (item.getSource().equals("web") || item.getSource().equals("link")) {
                        tv_title = tv_title + " #" + item.getCustomer().getNumberId();
                    } else {
                        tv_title = "#" + item.getCustomer().getNumberId();
                    }
                }
                title=tv_title;

                break;
            case Constant.NOTRECEIVED:
                title="未接待 · "+item.getUnCount();

                break;
            case Constant.COLLEAGUE:
                title="同事的对话 · "+item.getUnCount();
                break;
        }

        return title;
    }

    /**
     * 获取类型
     * @param types
     * @return
     */
    public static String getItemType(String types) {
        String type="";
        switch (types){
            case "evaluate":
                type = "顾客已评价";
                break;
            case "form":
                type = "表单";
                break;
            case "image":
                type = "图片";
                break;
            case "voice":
                type = "语音";
                break;
            case "menu":
                type = "菜单";
                break;
            case "video":
                type = "视频";
                break;
            case "card":
                type = "卡片";
                break;
            case "file":
                type = "文件";

                break;
            case "html":
                type = "富文本";
                break;
            case "text":
                type="文本";
                break;
        }
        return type;
    }

    /**
     * 设置标题title  地址和名称
     *  @param item
     * @param helper
     */
    public static void setTitle(MessageDialogEntity.DataBean.ListBean item, BaseViewHolder helper) {
        TextView title = helper.getView(R.id.dialog_item_title);

        switch (item.getItemType()) {

            case Constant.HAVERECEIVED://未接待列表数据显示和已接待,同事列表一样
            case Constant.NOTRECEIVED_ACT:
            case Constant.COLLEAGUE_ACT:
                //设置对话title
                String tv_title = null;
                if (item.getAddress() == null || TextUtils.isEmpty(item.getAddress())) {
                    title.setText("未知地址");
                    tv_title = "未知地址";
                } else {
                    tv_title = item.getAddress();
                }
                //当来源为web和link是显示地址如果不是则不显示地址
                if (!NullUtils.isNull(item.getCustomer().getName())) {
                    if (item.getSource().equals("web") || item.getSource().equals("link")) {
                        tv_title = tv_title + " " + item.getCustomer().getName();
                    } else {
                        tv_title = item.getCustomer().getName();
                    }
                } else {
                    if (item.getSource().equals("web") || item.getSource().equals("link")) {
                        tv_title = tv_title + " #" + item.getCustomer().getNumberId();
                    } else {
                        tv_title = "#" + item.getCustomer().getNumberId();
                    }
                }
                title.setText(tv_title);
                break;
            case Constant.NOTRECEIVED:
                title.setText("未接待 · "+item.getUnCount());
                break;
            case Constant.COLLEAGUE:
                title.setText("同事的对话 · "+item.getUnCount());
                break;
        }
    }
    /**
     * 设置最后更新时间
     *  @param item
     * @param helper
     */
    public static void setUpdateTime(MessageDialogEntity.DataBean.ListBean item, BaseViewHolder helper) {
        TextView last_time = helper.getView(R.id.update_time);

        if (item.getLastMsg()!=null&&item.getLastMsg().getTime() != 0) {
            String dateFormat = DateUtils.formationDate(new Date(item.getLastMsg().getTime()));
            if (!dateFormat.contains("1970")) {//前面设置的时间戳位1970，为辅助数据不显示
                last_time.setText(dateFormat);
            }
        } else {
            last_time.setText(DateUtils.getDateFormat(item.getAddtime()));
        }
    }
    /**
     * 更新最新消息内容
     *  @param item
     * @param helper
     */
    public static void setContent(MessageDialogEntity.DataBean.ListBean item, BaseViewHolder helper,Context mContext) {
        TextView content = helper.getView(R.id.tv_content);
        content.setText("");
        //消息类型
//        export const MessageSketch = (contents, type) => {
//            if(type == 'evaluate')contents = '[顾客已评价]'
//            if(type == 'form')contents = '[表单]'
//            if(type == 'image')contents = '[图片]'
//            if(type == 'voice')contents = '[语音]'
//            if(type == 'menu')contents = '[菜单]'
//            if(type == 'video')contents = '[视频]'
//            if(type == 'card')contents = '[卡片]'
//            if(type == 'file'){
//    const file = JSON.parse(contents);
//                contents = `[文件] ${file.name}`
//                if(file.type.indexOf('video/') == 0)contents = '[视频]'
//            }
//            if(type == 'html')contents = contents.replace(/<[^>]+>/g,"");
//            if(!type || type == 'text')contents = contents.replace(/[\r\n]/g," ") //换行符替换成空格
//            return contents;
//        }
        if (item.getLastMsg()==null)return;
        if (item.getLastMsg().getType()==null)return;

        String sendType = item.getLastMsg().getType();
        //获取本地草稿内容
        String draft = (String) SharedPreferencesUtils.getParam(item.getId(), "");
        if (!NullUtils.isNull(draft)){
            SpannableStringBuilder stringBuilder = new SpannableStringBuilder("[草稿] "+draft);
            stringBuilder.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.red_draft)), 0, 4, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            content.setText(stringBuilder);
            return;
        }
        String contents = "";
        switch (sendType) {

            case "evaluate":
                contents = "[顾客已评价]";
                break;
            case "form":
                contents = "[表单]";
                break;
            case "image":
                contents = "[图片]";
                break;
            case "voice":
                contents = "[语音]";
                break;
            case "menu":
                contents = "[菜单]";
                break;
            case "video":
                contents = "[视频]";
                break;
            case "card":
                contents = "[卡片]";
                break;
            case "file":
                FileEntity fileEntity=JsonParseUtils.parseToObject(item.getLastMsg().getContents(), FileEntity.class);
                contents = "[文件] " + fileEntity.getName();
                if (fileEntity.getType().indexOf("video/") == 0) {
                    contents = "[视频]";
                }
                break;
            case "html":
                contents = HtmlUtils.delHTMLTag(item.getLastMsg().getContents());
                break;
            case "text":
                if (item.getLastMsg().getContents() != null) {
                    contents = item.getLastMsg().getContents().replace("/[\\r\\n]/g", " ");
                }
                break;
        }

        switch (item.getItemType()){
            case Constant.NOTRECEIVED_ACT://未接待列表数据显示和已接待,同事列表一样
            case Constant.HAVERECEIVED:
            case Constant.COLLEAGUE_ACT:
                content.setText(contents);
                break;
            case Constant.NOTRECEIVED://同事和未接待显示一样
            case Constant.COLLEAGUE:
                //当来源为web和link是显示地址如果不是则不显示地址
                if (item.getSource().equals("web") || item.getSource().equals("link")) {
                    if (item.getCustomer().getName() != null) {
                        contents = item.getAddress() + "  " + item.getCustomer().getName() + ": " + contents;
                    } else {
                        contents = item.getAddress() + " #" + item.getCustomer().getNumberId() + ": " + contents;
                    }
                }else {
                    if (item.getCustomer().getName() != null) {
                        contents =item.getCustomer().getName() + ": " + contents;
                    } else {
                        contents = " #" + item.getCustomer().getNumberId() + ": " + contents;
                    }
                }
                content.setText(contents);
                break;

        }
    }
    /**
     * 设置标签
     *  @param item
     * @param helper
     */
    public static void setTagList(MessageDialogEntity.DataBean.ListBean item, BaseViewHolder helper, Context mContext) {
        TagFlowLayout flowLayout = helper.getView(R.id.flowLayout);
        List<String> list=new ArrayList<>();
        TagAdapter tagAdapter=new TagAdapter<String>(list) {

            @SuppressLint("WrongConstant")
            @Override
            public View getView(FlowLayout parent, int position, String o) {
                List<TagEntity.DataBean.ListBean> taglist = LocalDataSource.getTAGLIST();


                TextView tv = new TextView(mContext);
                tv.setPadding(20, 10, 20, 10);
                tv.setText(o);
                tv.setTextColor(mContext.getResources().getColor(R.color.black));
                tv.setMaxEms(20);
                tv.setSingleLine();
                GradientDrawable drawable = new GradientDrawable();
                drawable.setShape(GradientDrawable.RECTANGLE);
                drawable.setGradientType(GradientDrawable.RECTANGLE);
                drawable.setCornerRadius(6);
                drawable.setColor(Color.parseColor("#efefef"));//默认背景色
                tv.setBackground(drawable);
                if (!NullUtils.isEmptyList(taglist)){//设置标签颜色
                    for (TagEntity.DataBean.ListBean listBean : taglist) {
                        if (listBean.getName().equals(o)){
                            if (listBean.getColor()!=null) {
                                double v = ColorUtils.calculateLuminance(Color.parseColor(listBean.getColor()));
                                if (v>=0.5){
                                    tv.setTextColor(mContext.getResources().getColor(R.color.black));
                                }else {
                                    tv.setTextColor(mContext.getResources().getColor(R.color.white));
                                }
                                drawable.setColor(Color.parseColor(listBean.getColor()));
                            }
                        }
                    }
                }
                return tv;
            }
        };
        if (item.getCustomer() != null && item.getCustomer().getTag()!=null&&item.getCustomer().getTag().size()!=0) {
            list.addAll(item.getCustomer().getTag());
            flowLayout.setAdapter(tagAdapter);
        }else {
            //第一请求20条刷新的tag重新刷新一次,确保数据显示正确
            if (flowLayout.getAdapter()!=null){
                list.clear();
                flowLayout.setAdapter(tagAdapter);
            }
        }
    }

    /**
     * 获取上传参数
     * @return
     */
    public static String getStringParams(String s,String type){

        String str="";
     switch (type) {
         case Constant.EVALUATE_PARAMS:
         if (s.equals("好评")) str = "2";
         if (s.equals("中评")) str = "1";
         if (s.equals("差评")) str = "0";
         if (s.equals("无评价")) str = "-1";
         break;
         case Constant.SOURCE_PARAMS:
             if (s.equals("网页接入")) str = "web";
             if (s.equals("对话链接")) str = "link";
             if (s.equals("微信公众号")) str = "wechat";
             if (s.equals("微信小程序")) str = "program";
             break;
         case Constant.INVALID_PARAMS:
             if (s.equals("显示")) str =null;
             if (s.equals("隐藏")) str = "hide";
             if (s.equals("只看无效")) str = "only";
             break;
         case Constant.MISS_PARAMS:
             if (s.equals("显示")) str = null;
             if (s.equals("隐藏")) str = "hide";
             if (s.equals("只看遗漏")) str = "only";
             break;

     }
     return str;
    }
    /**
     * 合并时间获取时间戳
     */
    public static long getStringDate(String date,String time){

        String dateStr=null;
        if (!NullUtils.isNull(time)) {//设置时间

            String minute=null;
            if (time.contains("下午")) {
                String[] split = time.replace("下午", "").trim().split(":");
                int hours = 12 + Integer.parseInt(split[0]);
                minute=hours+":"+split[1];
            }
            if (time.contains("上午")) {
                String[] split = time.replace("上午", "").trim().split(":");
                int hours = Integer.parseInt(split[0]);
                if (hours<10){
                minute="0"+hours+":"+split[1];
                }else {
                minute=hours+":"+split[1];
                }
            }
            dateStr=date+" "+minute;
        }
        return DateUtils.dateToUnixTimestamp(dateStr,"yyyy-MM-dd HH:mm");
    }
}
