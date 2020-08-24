package com.hc_android.hc_css.api;

/**
 * @author wuyang
 * @date 2018/8/1
 */

public class Address {

    private static ServerEnvironmentEnum serverEnvironment = ServerEnvironmentEnum.TEST;

    /**
     * 推送服务器地址
     */
    public static  String SOCKET_URL = "https://socket.aihecong.com/";

    /**
     * 图片服务器地址
     */
    public static  String IMG_URL = "https://chatimg.aihecong.com/";
    /**
     * baseUrl
     */
    public static String BASEURL = "http://192.168.0.105:16999/";

    /**
     * 消息图片服务器
     */
    public static  String MSGIMG_URL = "https://msgimg.aihecong.com/";
    /**
     * 文件服务器
     */
    public static  String FILE_URL = "https://chatfile.aihecong.com/";
    /**
     * 系统服务器
     */
    public static  String SYSTEM_URL = "https://pubres.aihecong.com/";
    /**
     * INTERIM服务器
     */
    public static  String INTERIM_URL = "https://interimfile.aihecong.com/";
    /**
     * qiniu服务器
     */
    public static  String QINIU_URL = "https://upload-z2.qiniup.com";


    static {
        initHost();
    }

    /**
     * 加载 HOST地址
     */
    private static void initHost() {
        switch (serverEnvironment) {
            // 测试环境
            case TEST:
                BASEURL = "http://192.168.31.125:16999/";
                SOCKET_URL="http://192.168.31.125:17106/";
//                SOCKET_URL="https://socket.aihecong.com/";
                break;
            // 线上环境
            case ONLINE:
                BASEURL = "https://api.aihecong.com/";
                SOCKET_URL="https://socket.aihecong.com/";
                break;
            // 预生产环
            case PRE_ONLINE:
                BASEURL = "http://192.168.0.105:16999/";
                SOCKET_URL="https://192.168.0.105:17106/";
                break;
            // 压力测试环境
            case STRESS_TEST:
                BASEURL = "http://192.168.0.105:16999/";
                SOCKET_URL="https://192.168.0.105:17106/";
                break;
            default:
        }
    }

    /**
     * @NATIVEDEBUG 本地环境
     * @TEST 测试环境
     * @ONLINE 线上环境
     * @PRE_ONLINE 预发布环境
     * @STRESS_TEST 压力测试环境
     */
    public static enum ServerEnvironmentEnum {

        NATIVEDEBUG(" - Nativedebug"), TEST(" - Test"), ONLINE(""), PRE_ONLINE(" - Pre_Online"), STRESS_TEST(
                " - Stress_Test");

        private String displayName;

        public String getDisplayName() {
            return displayName;
        }

        private ServerEnvironmentEnum(String displayName) {
            this.displayName = displayName;
        }
    }

}
