//package com.hc_android.hc_css.utils;
//
//import java.util.List;
//
//public class NotifyChangeUtils {
//    /**
//     * 内部数据集
//     */
//    private List modelList;
//
//    /**
//     * 刷新列表
//     *
//     * @param modelList
//     */
//    public void refreshList(List modelList) {
//        int preSize = this.modelList.size();
//        this.modelList.clear();
//        notifyItemRangeRemoved(0, preSize);
//        this.modelList.addAll(modelList);
//        notifyItemRangeInserted(0, modelList.size());
//    }
//
//    /**
//     * 刷新某个item
//     *
//     * @param model
//     */
//    public void refreshItem(Model model) {
//        if (this.modelList.contains(model)) {
//            int index = this.modelList.indexOf(model);
//            notifyItemChanged(index);
//        }
//    }
//
//    /**
//     * 添加
//     *
//     * @param model
//     */
//    public void add(Model model) {
//        this.modelList.add(model);
//        notifyItemInserted(modelList.size() - 1);
//    }
//
//    /**
//     * 加载更多
//     * @param modelList
//     */
//    public void loadMore(List<Model> modelList) {
//        int curIndex = this.modelList.size();
//        this.modelList.addAll(modelList);
//        notifyItemRangeInserted(curIndex, modelList.size());
//    }
//
//    /**
//     * 删除
//     *
//     * @param model
//     */
//    public void delete(Model model) {
//        if (this.modelList.contains(model)) {
//            int index = this.modelList.indexOf(model);
//            this.modelList.remove(model);
//            notifyItemRemoved(index);
//        }
//    }
//
//}
