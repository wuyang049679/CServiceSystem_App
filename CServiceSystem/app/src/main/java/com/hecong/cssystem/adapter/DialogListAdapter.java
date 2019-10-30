package com.hecong.cssystem.adapter;

import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hecong.cssystem.R;
import com.hecong.cssystem.entity.FileEntity;
import com.hecong.cssystem.entity.MessageDialogEntity;
import com.hecong.cssystem.utils.Constant;
import com.hecong.cssystem.utils.DateUtils;
import com.hecong.cssystem.utils.JsonParseUtils;
import com.hecong.cssystem.utils.UriUtils;
import com.hecong.cssystem.utils.socket.EventServiceImpl;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

public class DialogListAdapter extends BaseMultiItemQuickAdapter<MessageDialogEntity.DataBean.ListBean, BaseViewHolder> {

    public DialogListAdapter(@Nullable List<MessageDialogEntity.DataBean.ListBean> data) {
        super(data);
        addItemType(Constant.NOTRECEIVED,R.layout.dialog_list_noreceived_adapter);
        addItemType(Constant.HAVERECEIVED,R.layout.dialog_list_adapter);
        addItemType(Constant.NOTRECEIVED_ACT,R.layout.dialog_list_adapter);
        addItemType(Constant.COLLEAGUE,R.layout.dialog_list_colleague_adapter);
        addItemType(Constant.COLLEAGUE_ACT,R.layout.dialog_colleague_act_adapter);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MessageDialogEntity.DataBean.ListBean item) {
        EventServiceImpl.getInstance().joinRoom(item.getCustomerId());
        if (item.getItemType()==Constant.HAVERECEIVED) {//已接待
            helper.addOnClickListener(R.id.close_btn);
            setTitle(item, helper);
            setMsgPoint(item, helper);
            setUpdateTime(item, helper);
            setContent(item, helper);
            setTagList(item, helper);
            setImageView(item, helper);
        }
        if (item.getItemType()==Constant.NOTRECEIVED_ACT) {//未接待对话列表
            helper.addOnClickListener(R.id.close_btn);
            helper.getView(R.id.btn_jd).setVisibility(View.VISIBLE);
            setTitle(item, helper);
            setMsgPoint(item, helper);
            setUpdateTime(item, helper);
            setContent(item, helper);
            setTagList(item, helper);
            setImageView(item, helper);
        }
        if (item.getItemType()==Constant.NOTRECEIVED){//未接待
            helper.addOnClickListener(R.id.dialog_no_received_lin);
            setTitle(item, helper);
            setMsgPoint(item, helper);
            setUpdateTime(item, helper);
            setContent(item, helper);
        }
        if (item.getItemType()==Constant.COLLEAGUE){//同事的对话
            helper.addOnClickListener(R.id.dialog_list_colleague_lin);
            setTitle(item, helper);
            setMsgPoint(item, helper);
            setUpdateTime(item, helper);
            setContent(item, helper);
        }
        if (item.getItemType()==Constant.COLLEAGUE_ACT) {//同事的对话列表
            setTitle(item, helper);
            setMsgPoint(item, helper);
            setUpdateTime(item, helper);
            setContent(item, helper);
            setTagList(item, helper);
            setImageView(item, helper);
        }
    }

    /**
     * 设置图片
     * @param item
     * @param helper
     */
    private void setImageView(MessageDialogEntity.DataBean.ListBean item, BaseViewHolder helper) {
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
        if (item.getCustomer().getHead()!=null){
            img_lin.setVisibility(View.GONE);
            draweeView.setImageURI(UriUtils.getUri(Constant.ONLINEPIC, item.getCustomer().getHead()));
        }else {
            img_lin.setVisibility(View.VISIBLE);
            img.setImageResource(resId);
            GradientDrawable drawable = new GradientDrawable();
            drawable.setShape(GradientDrawable.RECTANGLE);
            drawable.setGradientType(GradientDrawable.RECTANGLE);
            drawable.setCornerRadius(6);
            if (item.getCustomerOffTime()==null){
            drawable.setColor(mContext.getResources().getColor(R.color.theme_app));
            }else {
            drawable.setColor(mContext.getResources().getColor(R.color.black_cc));
            }
            img_lin.setBackground(drawable);
        }
    }

    /**
     * 设置浏览器类型图标
     * @param item
     */
    private int setDeskTop(MessageDialogEntity.DataBean.ListBean item) {
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
     * 设置标签
     *  @param item
     * @param helper
     */
    private void setTagList(MessageDialogEntity.DataBean.ListBean item, BaseViewHolder helper) {
        TagFlowLayout flowLayout = helper.getView(R.id.flowLayout);
        List<String> list=new ArrayList<>();
        TagAdapter tagAdapter=new TagAdapter<String>(list) {
            @Override
            public View getView(FlowLayout parent, int position, String o) {
                TextView tv = new TextView(mContext);
                tv.setPadding(20, 10, 20, 10);
                tv.setText(item.getTag().get(position));
                tv.setTextColor(mContext.getResources().getColor(R.color.white));
                tv.setMaxEms(20);
                tv.setSingleLine();
                GradientDrawable drawable = new GradientDrawable();
                drawable.setShape(GradientDrawable.RECTANGLE);
                drawable.setGradientType(GradientDrawable.RECTANGLE);
                drawable.setCornerRadius(6);
                drawable.setColor(mContext.getResources().getColor(R.color.colorAccent));
                tv.setBackground(drawable);
                return tv;
            }
        };
        if (item.getTag() != null && item.getTag().size() != 0) {
                list.addAll(item.getTag());
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
     * 更新最新消息内容
     *  @param item
     * @param helper
     */
    private void setContent(MessageDialogEntity.DataBean.ListBean item, BaseViewHolder helper) {
        TextView content = helper.getView(R.id.tv_content);

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
        String sendType = item.getLastMsg().getType();
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
                contents = item.getLastMsg().getContents().replace("/<[^>]+>/g", "");
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
     * 设置最后更新时间
     *  @param item
     * @param helper
     */
    private void setUpdateTime(MessageDialogEntity.DataBean.ListBean item, BaseViewHolder helper) {
        TextView last_time = helper.getView(R.id.update_time);
        if (item.getLastMsg()!=null&&item.getLastMsg().getTime() != null) {
            last_time.setText(DateUtils.getDateFormat(item.getLastMsg().getTime()));
        } else {
            last_time.setText(DateUtils.getDateFormat(item.getAddtime()));
        }
    }

    /**
     * 设置未读消息红点
     *  @param item
     * @param helper
     */
    private void setMsgPoint(MessageDialogEntity.DataBean.ListBean item, BaseViewHolder helper) {
        TextView ponit_tv = helper.getView(R.id.msg_count_tv);
        //设置未读消息数
        if (item.getUnreadNum() == 0) {
            ponit_tv.setVisibility(View.GONE);
        } else {
            ponit_tv.setVisibility(View.VISIBLE);
            ponit_tv.setText(item.getUnreadNum()+"");
            if (item.getUnreadNum() > 9) {
                ponit_tv.setBackground(mContext.getResources().getDrawable(R.drawable.radius_red));
            } else {
                ponit_tv.setBackground(mContext.getResources().getDrawable(R.drawable.oval_red));
            }
        }
    }

    /**
     * 设置标题title  地址和名称
     *  @param item
     * @param helper
     */
    private void setTitle(MessageDialogEntity.DataBean.ListBean item, BaseViewHolder helper) {
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
            if (item.getCustomer().getName() != null) {
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
            title.setText("未接待："+item.getUnCount());
                break;
            case Constant.COLLEAGUE:
            title.setText("同事的对话："+item.getUnCount());
                break;
        }
    }

}

