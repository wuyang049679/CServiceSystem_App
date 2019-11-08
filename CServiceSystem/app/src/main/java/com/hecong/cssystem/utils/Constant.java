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
    public static final int NOTRECEIVED_ACT=4;//未接待的对话列表界面
    public static final int COLLEAGUE_ACT=5;//同事的对话列表界面
    /**
     * 对话列表数据类型
     */
    public static final String NOTRECEIVED_LIST="not_received_list";//未接待类型
    public static final String HAVERECEIVED_LIST="have_received_list";//已接待
    public static final String COLLEAGUE_LIST="colleague_list";//同事的对话

    /**
     * 已接待对话数量
     */
    public static final String HAVERECEIVED_NUM="have_received_num";
    /**
     * 常用或ID
     */
    public static final String DIALOGID="_dialogId";

    /**
     * 消息类型
     */
    public static final String MESSAGE_LOGINSUC = "loginSuc";
    public static final String MESSAGE_JOIN = "join";
    public static final String MESSAGE_LEAVE = "leave";
    public static final String MESSAGE_SERVICELEAVE = "serviceLeave";
    public static final String MESSAGE_NEW = "message";
    public static final String MESSAGE_NEWDIALOG = "newdialog";
    public static final String MESSAGE_INPUTING = "inputing";
    public static final String MESSAGE_ONLINE = "online";
    public static final String MESSAGE_OFFLINE = "offline";
    public static final String MESSAGE_RECEPTION = "reception";
    public static final String MESSAGE_SERVICEONLY = "serviceOnly";
    public static final String MESSAGE_REALTIME_ADD = "realtime_add";
    public static final String MESSAGE_REALTIME_MODIFY = "realtime_modify";
    public static final String MESSAGE_REALTIME_DEL = "realtime_del";
    public static final String MESSAGE_STATEUPATE = "stateUpate";
    public static final String MESSAGE_UPATEDIALOG = "upateDialog";
    public static final String MESSAGE_REPORT_STATE = "report_state";
}
