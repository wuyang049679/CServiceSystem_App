package com.hc_android.hc_css.utils.android.app;

import com.hc_android.hc_css.base.BaseApplication;
import com.hc_android.hc_css.entity.MessageEntity;
import com.hc_android.hc_css.entity.QConfigEntity;
import com.hc_android.hc_css.utils.JsonParseUtils;
import com.hc_android.hc_css.utils.NullUtils;
import com.hc_android.hc_css.utils.android.SharedPreferencesUtils;
import com.hc_android.hc_css.utils.socket.MessageEvent;
import com.hc_android.hc_css.utils.socket.MessageEventType;

import org.greenrobot.eventbus.EventBus;

import static com.hc_android.hc_css.utils.Constant.UI_FRESH;

/**
 * 应用全局配置
 */
public class AppConfig {

    /**
     * 是否开启消息通知
     */
    public static boolean isOpenNotice;
    /**
     * 是否开启声音
     */
    public static boolean isOpenVoice;
    /**
     * 是否开启震动
     */
    public static boolean isOpenVibrator;
    /**
     * 是否开启电脑在线时不通知
     */
    public static boolean isOpenMeanwhile;
    /**
     * 是否开启新对话提醒
     */
    public static boolean isOpenNewDialog;
    /**
     * 是否开启新消息提醒
     */
    public static boolean isOpenNewMsg;
    /**
     * 全局未读数
     * @return
     */
    public static int unReadCount;

    /**
     * 首次进来是否访问快捷回复列表
     * @return
     */
    public static boolean hasQuickList_M = true;//个人的
    public static boolean hasQuickList_T = true;//团队的


    /**
     * 快捷回复本地配置
     */

    public static QConfigEntity configEntity;


    public static QConfigEntity getConfigEntity() {
        String value = (String) SharedPreferencesUtils.getParam("quick_config" + BaseApplication.getUserBean().getId(), "");
        QConfigEntity qConfigEntity = JsonParseUtils.parseToObject(value, QConfigEntity.class);
        return NullUtils.isNull(qConfigEntity) ?new QConfigEntity(): (QConfigEntity) qConfigEntity;
    }

    public static void setConfigEntity(QConfigEntity configEntity) {
        String parseToJson = JsonParseUtils.parseToJson(configEntity);
        SharedPreferencesUtils.setParam("quick_config" + BaseApplication.getUserBean().getId(),parseToJson);
    }

    public static int getUnReadCount() {
        return unReadCount;
    }

    public static void setUnReadCount(int unMsgRead,String dialogId) {
        AppConfig.unReadCount = unMsgRead;
        MessageEntity message = new MessageEntity();
        //通知所有界面刷新（未读数）
        if (dialogId!=null)message.setDialogId(dialogId);
        message.setAct(UI_FRESH);
        MessageEvent event = new MessageEvent(MessageEventType.EventMessage, message);
        EventBus.getDefault().postSticky(event);
    }


    public static boolean isIsOpenNewMsg() {
        return BaseApplication.getUserBean().getNotice().getRange().isNewMessage();
    }

    public static void setIsOpenNewMsg(boolean isOpenNewMsg) {
        AppConfig.isOpenNewMsg = isOpenNewMsg;
    }

    public static boolean isIsOpenNewDialog() {
        return BaseApplication.getUserBean().getNotice().getRange().isNewDialog();
    }

    public static void setIsOpenNewDialog(boolean isOpenNewDialog) {
        AppConfig.isOpenNewDialog = isOpenNewDialog;
    }

    public static boolean isIsOpenMeanwhile() {
        return BaseApplication.getUserBean().getNotice().isMeanwhile();
    }

    public static void setIsOpenMeanwhile(boolean isOpenMeanwhile) {
        AppConfig.isOpenMeanwhile = isOpenMeanwhile;
    }

    public static boolean isIsOpenNotice() {
        return BaseApplication.getUserBean().getAppNotice().isPush();
    }

    public static void setIsOpenNotice(boolean isOpenNotice) {
        AppConfig.isOpenNotice = isOpenNotice;
    }

    public static boolean isIsOpenVoice() {
        return BaseApplication.getUserBean().getAppNotice().isSound();
    }

    public static void setIsOpenVoice(boolean isOpenVoice) {
        AppConfig.isOpenVoice = isOpenVoice;
    }

    public static boolean isIsOpenVibrator() {
        return BaseApplication.getUserBean().getAppNotice().isVibration();
    }

    public static void setIsOpenVibrator(boolean isOpenVibrator) {
        AppConfig.isOpenVibrator = isOpenVibrator;
    }
}
