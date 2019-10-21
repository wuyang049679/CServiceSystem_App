package com.hecong.cssystem.adapter;

import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.hecong.cssystem.R;
import com.hecong.cssystem.entity.FileEntity;
import com.hecong.cssystem.entity.MessageDialogEntity;
import com.hecong.cssystem.utils.Constant;
import com.hecong.cssystem.utils.DateUtils;
import com.hecong.cssystem.utils.UriUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

public class DialogListAdapter extends BaseQuickAdapter<MessageDialogEntity.DataBean.ListBean, BaseViewHolder> {

    public DialogListAdapter(@Nullable List<MessageDialogEntity.DataBean.ListBean> data) {
        super( R.layout.dialog_list_adapter,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, MessageDialogEntity.DataBean.ListBean item) {
        SimpleDraweeView draweeView=helper.getView(R.id.simple_drawViews);
        TagFlowLayout flowLayout = helper.getView(R.id.flowLayout);
        TextView title=helper.getView(R.id.dialog_item_title);
        TextView ponit_tv=helper.getView(R.id.msg_count_tv);
        TextView last_time=helper.getView(R.id.update_time);
        TextView content=helper.getView(R.id.tv_content);
        Button button=helper.getView(R.id.btn_jd);
        
        draweeView.setImageURI(UriUtils.getUri(Constant.ONLINEPIC, item.getCustomer().getHead()));
        setTitle(item,title);
        setMsgPoint(item,ponit_tv);
        setUpdateTime(item,last_time);
        setContent(item,content);
        setTagList(item,flowLayout);

    }

    /**
     * 设置标签
     * @param item
     * @param flowLayout
     */
    private void setTagList(MessageDialogEntity.DataBean.ListBean item, TagFlowLayout flowLayout) {

        if (item.getTag()!=null&&item.getTag().size()!=0) {
            flowLayout.setAdapter(new TagAdapter<String>(item.getTag()) {
                @Override
                public View getView(FlowLayout parent, int position, String s) {


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

            });
        }
    }

    /**
     * 更新最新消息内容
     * @param item
     * @param content
     */
    private void setContent(MessageDialogEntity.DataBean.ListBean item, TextView content) {
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
        String sendType = item.getLastMsg().getType();
        String contents="";
        switch (sendType){

            case "evaluate":
                contents="[顾客已评价]";
            break;
            case "form":
                contents="[表单]";
                break;
            case "image":
                contents="[图片]";
                break;
            case "voice":
                contents="[语音]";
                break;
            case "menu":
                contents="[菜单]";
                break;
            case "video":
                contents="[视频]";
                break;
            case "card":
                contents="[卡片]";
                break;
            case "file":
                Gson gson=new Gson();
                FileEntity fileEntity = gson.fromJson(item.getLastMsg().getContents(), FileEntity.class);
                contents="[文件] "+fileEntity.getName();
                if (fileEntity.getType().indexOf("video/")==0){
                contents="[视频]";
                }
                break;
            case "html":
                contents= item.getLastMsg().getContents().replace("/<[^>]+>/g","");
                break;
            case "text":
                if (item.getLastMsg().getContents()!=null) {
                    contents = item.getLastMsg().getContents().replace("/[\\r\\n]/g", " ");
                }
                break;
        }
        content.setText(contents);

    }

    /**
     * 设置最后更新时间
     * @param item
     * @param last_time
     */
    private void setUpdateTime(MessageDialogEntity.DataBean.ListBean item, TextView last_time) {
        if (item.getLastMsg().getTime()!=null){
            last_time.setText(DateUtils.getDateFormat(item.getLastMsg().getTime()));
        }else {
            last_time.setText(DateUtils.getDateFormat(item.getAddtime()));
        }
    }

    /**
     * 设置未读消息红点
     * @param item
     * @param ponit_tv
     */
    private void setMsgPoint(MessageDialogEntity.DataBean.ListBean item, TextView ponit_tv) {
        //设置未读消息数
        if (item.getUnreadNum()==0){
            ponit_tv.setVisibility(View.GONE);
        }else {
            ponit_tv.setVisibility(View.VISIBLE);
            ponit_tv.setText(item.getUnreadNum());
            if (item.getUnreadNum()>9){
                ponit_tv.setBackground(mContext.getResources().getDrawable(R.drawable.radius_red));
            }else {
                ponit_tv.setBackground(mContext.getResources().getDrawable(R.drawable.oval_red));
            }
        }
    }

    /**
     * 设置标题title  地址和名称
     * @param item
     * @param title
     */
    private void setTitle(MessageDialogEntity.DataBean.ListBean item,TextView title) {
        //设置对话title
        String tv_title=null;
        if (item.getAddress()==null||TextUtils.isEmpty(item.getAddress())){
            title.setText("未知地址");
            tv_title="未知地址";
        }else {
            tv_title=item.getAddress();
        }
        //当来源为web和link是显示地址如果不是则不显示地址
        if (item.getCustomer().getName()!=null){
            if (item.getSource().equals("web")||item.getSource().equals("link")) {
                tv_title = tv_title + " " + item.getCustomer().getName();
            }else {
                tv_title=item.getCustomer().getName();
            }
        }else {
            if (item.getSource().equals("web")||item.getSource().equals("link")) {
                tv_title = tv_title + " #" + item.getCustomer().getNumberId();
            }else {
                tv_title="#"+item.getCustomer().getNumberId();
            }
        }
        title.setText(tv_title);
    }

}

