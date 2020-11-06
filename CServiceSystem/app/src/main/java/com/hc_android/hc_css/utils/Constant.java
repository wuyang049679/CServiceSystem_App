package com.hc_android.hc_css.utils;


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
//    public static String STRINGDEFAULT = "";
    /**
     * Uri 在线模式
     **/
    public static int ONLINEPIC = 1;
    /**
     * Uri 本地模式
     **/
    public static int LOCALRESOURCE = 2;

    /**
     * 手机图片
     *
     * */
    public static int PHONEIMAGEPATH=3;

    /**
     * 本地登录认证的hash值
     */
    public static String HASH="login_hash";
    /**
     * 微信登录code
     */
    public static String CODE="login_code";
    /**
     * 用户名和密码
     */
    public static String USERNAME="_username";
    public static String PASSWORD="_password";
    /**
     * 快捷回复记忆缓存
     */
    public static String QUICKCACHE="_quick_cache";
    /**
     * 请求类型key
     */
    public static String REQUEST_TYPE="request_type";
    public static String KEY_HASH="hash";

    /**
     * get请求的常用value
     */
    public static String STANDARD="standard";
    public static String SERVICEID="serviceId";
    public static String DIALOGID="dialogId";
    public static String CUSTOMERID="customerId";
    public static String SOCKETID="socketId";
    public static String ENTID="entId";

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
     * 消息推送类型
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
    public static final String MESSAGE_SYSTEMNOTICE = "systemNotice";
    public static final String MESSAGE_REPORT_STATE = "report_state";
    public static final String MESSAGE_WORKTIME_STATE = "workTime"; //上班时间段更改配置
    public static final String EVENTBUS_NEWDIALOG = "eventBus_newdialog";//eventBus消息通知
    public static final String MESSAGE_UNDO = "messageUndo";//消息撤回
    public static final String UI_FRESH = "ui_fresh";//界面刷新
    public static final String EVENTBUS_NEWDIALOG_VISITOR = "eventBus_newdialog_visitor";//访客界面主动邀请对话成功
    public static final String EVENTBUS_NOTIFICATION_STATE = "eventBus_notification_state";//全局配置通知
    public static final String EVENTBUS_NETWORK_STATE = "eventBus_network_state";//全局网络状态
    public static final String EVENTBUS_HASH_STATE = "eventBus_hash_state";//权限hash过期

    /**
     * 消息对象类型
     */
    public static final int CHAT_LEFT = 1;
    public static final int CHAT_RIGHT = 2;
    public static final int CHAT_CENTER= 3;
    /**
     * 消息聊天信息类型
     */
    public static final String _EVALUATE = "evaluate";
    public static final String _FORM = "form";
    public static final String _IMAGE = "image";
    public static final String _VOICE = "voice";
    public static final String _MENU = "menu";
    public static final String _VIDEO = "video";
    public static final String _CARD = "card";
    public static final String _FILE = "file";
    public static final String _HTML = "html";
    public static final String _TEXT = "text";
    public static final String _TIME = "interval";
    public static final String _SYSTEM = "system";

    /**
     * 消息发送状态
     */
    public static final String _ISLOADING = "isLoading";
    public static final String _ISFAILED = "isFailed";

    /**
     * 顾客资料-卡片展示类型
     */
    public static final int CARD_SETING = 1;
    public static final int CARD_BROSWER = 2;

    /**
     * 修改更新类型
     */
    public static final String INTENT_TYPE= "intent_type";//跳转类型（个人信息还是名片）
    public static final String HISTORYACT_= "history_act";//历史对话页面
    public static final String NOTRECEIVEDACT_= "not_received_act";//未接待页面
    public static final String PERSONAL_= "personal_act";//个人信息页面
    public static final String CHATACT_ = "chat_act";//聊天页面
    public static final String MINEFG_ = "mine_frag";//个人中心类型
    public static final String STYLETYPE = "style_type"; //展示类型（输入框还是，列表）
    public static final String INPUTTYPE_ = "input_type";//输入框修改类型
    public static final String LISTTYPE_ = "list_type";//列表修改类型多选模式
    public static final String LISTTYPE_ONE = "list_type_one";//列表修改类型_单选模式
    public static final String _TITLE = "_title";//标题
    public static final String _INPUT = "_input_text";//输入框内容
    public static final String _LISTSTRING = "_list_text";//列表集合内容
    public static final String _PARAMS = "_params";//参数集合

    /**
     * 参数类型
     */
    public static final String EVALUATE_PARAMS = "evaluate_params";//评价参数集合
    public static final String SOURCE_PARAMS = "source_params";//接入方式参数集合
    public static final String INVALID_PARAMS = "invalid_params";//有效对话参数集合
    public static final String MISS_PARAMS = "miss_params";//遗漏对话参数集合
}
