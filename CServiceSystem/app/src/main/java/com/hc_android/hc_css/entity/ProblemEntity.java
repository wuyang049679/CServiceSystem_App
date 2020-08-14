package com.hc_android.hc_css.entity;

public class ProblemEntity {
    String head;
    String 名称;
    String 邮箱;
    String 手机;
    String 团队ID;
    String 版本;
    String app版本;
    String 设备信息;

    public ProblemEntity() {
    }

    public ProblemEntity(String head, String 名称, String 邮箱, String 手机, String 团队ID, String 版本, String app版本) {
        this.head = head;
        this.名称 = 名称;
        this.邮箱 = 邮箱;
        this.手机 = 手机;
        this.团队ID = 团队ID;
        this.版本 = 版本;
        this.app版本 = app版本;
    }

    public String get设备信息() {
        return 设备信息;
    }

    public void set设备信息(String 设备信息) {
        this.设备信息 = 设备信息;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String get名称() {
        return 名称;
    }

    public void set名称(String 名称) {
        this.名称 = 名称;
    }

    public String get邮箱() {
        return 邮箱;
    }

    public void set邮箱(String 邮箱) {
        this.邮箱 = 邮箱;
    }

    public String get手机() {
        return 手机;
    }

    public void set手机(String 手机) {
        this.手机 = 手机;
    }

    public String get团队ID() {
        return 团队ID;
    }

    public void set团队ID(String 团队ID) {
        this.团队ID = 团队ID;
    }

    public String get版本() {
        return 版本;
    }

    public void set版本(String 版本) {
        this.版本 = 版本;
    }

    public String getApp版本() {
        return app版本;
    }

    public void setApp版本(String app版本) {
        this.app版本 = app版本;
    }
}
