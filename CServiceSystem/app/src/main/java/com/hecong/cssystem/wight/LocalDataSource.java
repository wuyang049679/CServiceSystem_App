package com.hecong.cssystem.wight;

import com.hecong.cssystem.entity.MessageDialogEntity;

import java.util.List;

public class LocalDataSource {


    private static List<MessageDialogEntity.DataBean.ListBean> NOTLIST;//未接待的集合
    private static List<MessageDialogEntity.DataBean.ListBean> TEAMLIST;//同事的集合


    public static List<MessageDialogEntity.DataBean.ListBean> getNOTLIST() {
        return NOTLIST;
    }

    public static void setNOTLIST(List<MessageDialogEntity.DataBean.ListBean> NOTLIST) {
        LocalDataSource.NOTLIST = NOTLIST;
    }

    public static List<MessageDialogEntity.DataBean.ListBean> getTEAMLIST() {
        return TEAMLIST;
    }

    public static void setTEAMLIST(List<MessageDialogEntity.DataBean.ListBean> TEAMLIST) {
        LocalDataSource.TEAMLIST = TEAMLIST;
    }
}
