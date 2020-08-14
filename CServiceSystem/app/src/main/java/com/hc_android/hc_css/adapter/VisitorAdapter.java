package com.hc_android.hc_css.adapter;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.hc_android.hc_css.R;
import com.hc_android.hc_css.api.Address;
import com.hc_android.hc_css.entity.VisitorEntity;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.NullUtils;
import com.hc_android.hc_css.utils.UriUtils;
import com.hc_android.hc_css.utils.android.image.FrescoUtils;

import java.util.List;

public class VisitorAdapter extends BaseQuickAdapter<VisitorEntity.DataBean.ListBean, BaseViewHolder> {

    public VisitorAdapter(@Nullable List<VisitorEntity.DataBean.ListBean> data) {
        super(R.layout.visitor_adapter,data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder helper, VisitorEntity.DataBean.ListBean item) {
//        item._address = '未知地址'
//        if(item.address){
//      const { country, region, city } = item.address;
//            if(region){
//                if(country != '中国'){
//                    item._address = `${country} ${region}${city}`;
//                }else{
//                    item._address = `${region}${city}`;
//                    if(region.charAt(region.length - 1) == '市')item._address = city;
//                    if(region == city)item._address = city;
//                }
//            }else{
//                item._address = country;
//            }
//        }
        SimpleDraweeView draweeView=helper.getView(R.id.visitor_drawViews);
        if (item.getAddress()!=null){
            String name=item.getName();
            if (NullUtils.isNull(name)){
                name= "#"+item.getNumberId();
            }
            String itemAdresss = "未知地址";
            String address=item.getAddress().getRegion();
            if (!TextUtils.isEmpty(address)){
                if (item.getAddress().getCountry()!=null && !item.getAddress().getCountry().equals("中国")){
                    itemAdresss = item.getAddress().getCountry()+item.getAddress().getRegion()+item.getAddress().getCity();
                }else {
                    itemAdresss = item.getAddress().getRegion()+item.getAddress().getCity();
                    if (address.endsWith("市")) itemAdresss = item.getAddress().getCity();
                    if (item.getAddress().getCity()!=null && item.getAddress().getCity().equals(address)) itemAdresss = item.getAddress().getCity();
                }
            }else {
                    itemAdresss = item.getAddress().getCountry();
            }


            helper.setText(R.id.dialog_item_title,itemAdresss+"  "+name);
        }
        if (item.getDialogId()==null){//是否显示正在对话
            helper.getView(R.id.dialog_item_title_right).setVisibility(View.GONE);
        }else {
            helper.getView(R.id.dialog_item_title_right).setVisibility(View.VISIBLE);
        }
        if (item.getCurrent()!=null){
            helper.setText(R.id.tv_content,"正在访问："+item.getCurrent().getTitle());
        }
        setImageView(item,helper);
        helper.addOnClickListener(R.id.lin_constrain);
    }

    /**
     * 设置图片
     * @param item
     * @param helper
     */
    @SuppressLint("WrongConstant")
    private void setImageView(VisitorEntity.DataBean.ListBean item, BaseViewHolder helper) {
        SimpleDraweeView draweeView = helper.getView(R.id.visitor_drawViews);
        LinearLayout img_lin = helper.getView(R.id.img_lin);
        ImageView img = helper.getView(R.id.img_icon);
        int resId=0;
        if (item.getDevice()!=null&&item.getDevice().getType()!=null){

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
        if (item.getHead()==null){
            if (resId!=0){
                draweeView.setVisibility(View.GONE);
                img_lin.setVisibility(View.VISIBLE);
                img.setImageResource(resId);


            }else {
                img_lin.setVisibility(View.GONE);
                draweeView.setVisibility(View.VISIBLE);
                FrescoUtils.setRoundGif(draweeView, UriUtils.getUri(Constant.ONLINEPIC, Address.SYSTEM_URL + "image/defaultAvatar.jpeg"));
            }
        }else {
            img_lin.setVisibility(View.GONE);
            draweeView.setVisibility(View.VISIBLE);
            FrescoUtils.setRoundGif(draweeView, UriUtils.getUri(Constant.ONLINEPIC, item.getHead()));
        }
    }
    /**
     * 设置浏览器类型图标
     * @param item
     */
    private int setDeskTop(VisitorEntity.DataBean.ListBean item) {
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
}
