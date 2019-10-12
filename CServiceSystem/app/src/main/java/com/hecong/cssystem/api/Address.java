package com.hecong.cssystem.api;

/**
 * @author wuyang
 * @date 2018/8/1
 */

public class Address {

    private static ServerEnvironmentEnum serverEnvironment = ServerEnvironmentEnum.ONLINE;
    public static final String H5_URL = "https://api.aihecong.com/";


    /**
     * baseUrl
     */
    public static String BASEURL = "http://192.168.0.104:16999/";


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
                BASEURL = "http://192.168.0.104:16999/";

                break;
            // 线上环境
            case ONLINE:
                BASEURL = "https://api.aihecong.com/";

                break;
            // 预生产环
            case PRE_ONLINE:

                BASEURL = "https://api.aihecong.com/";
                break;
            // 压力测试环境
            case STRESS_TEST:
                BASEURL = "https://api.aihecong.com/";
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

    /**
     * 健康资讯文章
     */
    public static final String HEALTHLECTUREURL = BASEURL + "app/tag/article/list/healthLecture/";

}
