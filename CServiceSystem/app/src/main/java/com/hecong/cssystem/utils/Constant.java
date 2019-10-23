package com.hecong.cssystem.utils;


/**
 * Created by Administrator on 2017/09/15.
 */


public class Constant {
    /**
     * 默认的Int
     **/
    public static int INTDEFAULT = -1;
    /**
     * 默认的String
     **/
    public static String STRINGDEFAULT = "";
    /**
     * Uri 在线模式
     **/
    public static int ONLINEPIC = 1;
    /**
     * Uri 本地模式
     **/
    public static int LOCALRESOURCE = 2;


    /**
     * 本地登录认证的hash值
     */
    public static String HASH="login_hash";
    /**
     * 用户名和密码
     */
    public static String USERNAME="_username";
    public static String PASSWORD="_password";
    /**
     * 请求类型key
     */
    public static String REQUEST_TYPE="request_type";
    public static String KEY_HASH="hash";

    /**
     * get请求的常用value
     */
    public static String STANDARD="standard";

    /**
     * 对话列表类型
     */

    public static final int NOTRECEIVED=1;//未接待类型
    public static final int HAVERECEIVED=2;//已接待
    public static final int COLLEAGUE=3;//同事的对话
}
