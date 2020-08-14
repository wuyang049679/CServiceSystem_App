package com.hc_android.hc_css.entity;

/**
 * Desccribe:
 *
 * @author Created by wuyang on 2019/9/2
 */
public class MineEntity {

    /**
     * code : 200
     * msg : 操作成功
     * data : null
     * version : v1.0
     */

    private int code;
    private String msg;
    private Object data;
    private String version;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
