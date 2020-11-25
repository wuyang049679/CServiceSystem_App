package com.hc_android.hc_css.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hc_android.hc_css.R;
import com.hc_android.hc_css.entity.MessageDialogEntity;
import com.hc_android.hc_css.entity.QuickEntity;
import com.hc_android.hc_css.ui.activity.ChatActivity;
import com.hc_android.hc_css.utils.android.app.AppConfig;
import com.hc_android.hc_css.utils.android.app.DataProce;

import java.util.ArrayList;
import java.util.List;

public class ChannelCompanyAdapter extends BaseAdapter implements Filterable {
    //实现过滤 重写过滤条件
    private List<QuickEntity.DataBean.ListBean> datas;
    private List<QuickEntity.DataBean.ListBean> Unfilterdatas;//未进行过滤的数据

    public ChannelCompanyAdapter(List<QuickEntity.DataBean.ListBean> datas) {
        this.datas = datas;
        Unfilterdatas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public QuickEntity.DataBean.ListBean getItem(int position) {
        return datas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Vholder vholder = null;
        if (convertView == null) {
            vholder = new Vholder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.quick_list_adapter, parent, false);
            vholder.tv = convertView.findViewById(R.id.quick_list_text);
            vholder.type = convertView.findViewById(R.id.quick_list_type);
            convertView.setTag(vholder);
        } else {
            vholder = (Vholder) convertView.getTag();
        }

        MessageDialogEntity.DataBean.ListBean listBean=new MessageDialogEntity.DataBean.ListBean();
        MessageDialogEntity.DataBean.ListBean.LastMsgBean lastMsgBean=new MessageDialogEntity.DataBean.ListBean.LastMsgBean();
        lastMsgBean.setContents(getItem(position).getContent());
        lastMsgBean.setType(getItem(position).getType());
        listBean.setLastMsg(lastMsgBean);
        String name=DataProce.getContent(listBean);
        vholder.tv.setText(name);
        vholder.type.setText(DataProce.getItemType(getItem(position).getType()));

        //快捷回复文本类型不显示标签
        if (getItem(position).getType().equals("text")){
            vholder.type.setVisibility(View.GONE);
        }else {
            vholder.type.setVisibility(View.VISIBLE);
        }
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((ChatActivity)parent.getContext()).toSendMsgForFragment((QuickEntity.DataBean.ListBean) getItem(position));
            }
        });
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new MyFilter();
    }

    class Vholder {
        private TextView tv;
        private TextView type;
    }

    class MyFilter extends Filter {
        //核心过滤

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            //文本框内容开始筛选
            FilterResults filterResults = new FilterResults();//过滤结果对象 values值 count大小
            if (Unfilterdatas.size() == 0) {
                return null;
            }
            if (constraint == null || constraint.length() == 0 || constraint.length() > 5) {
                //未输入东西默认所有展示 可更具需求设定
               return null;
            } else {
                String s = constraint.toString().toLowerCase();
                ArrayList<QuickEntity.DataBean.ListBean> newDatas = new ArrayList<>();
                for (QuickEntity.DataBean.ListBean bean : Unfilterdatas) {
                    boolean add = false;
                    if (bean.getType() != null && bean.getType().equals("text")) {
                        if (bean.getContent() != null && bean.getContent().contains(s)) {
                            add = true;
                        } else if (bean.getName() != null && bean.getName().contains(s)) {
                            add = true;
                        }
                    } else {
                        if (bean.getName() != null && bean.getName().contains(s)) add = true;;
                    }
                    if (add && newDatas.size() < 3 )newDatas.add(bean);
                }
                if (newDatas.size() == 0) {
                    //筛选结果没有展示所有数据
                    return null;
                } else {
                    //设置筛选结果数据
                    filterResults.values = newDatas;
                    filterResults.count = newDatas.size();
                }
            }
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            if (results !=null && results.count > 0) {
                //修改适配器数据
                datas = (ArrayList<QuickEntity.DataBean.ListBean>) results.values;
                if (datas.size()>3)datas = datas.subList(0,3);
                notifyDataSetChanged();
            } else {
//                datas.clear();
                notifyDataSetInvalidated();
            }
        }
    }
}
