package com.hc_android.hc_css.api;


import com.hc_android.hc_css.base.BaseEntity;
import com.hc_android.hc_css.entity.AppUpdateEntity;
import com.hc_android.hc_css.entity.BusinessEntity;
import com.hc_android.hc_css.entity.CardEntity;
import com.hc_android.hc_css.entity.ChatEntity;
import com.hc_android.hc_css.entity.CustomPathEntity;
import com.hc_android.hc_css.entity.IneValuateEntity;
import com.hc_android.hc_css.entity.LoginEntity;
import com.hc_android.hc_css.entity.MessageDialogEntity;
import com.hc_android.hc_css.entity.QuickEntity;
import com.hc_android.hc_css.entity.QuickGroupEntity;
import com.hc_android.hc_css.entity.ReceptionEntity;
import com.hc_android.hc_css.entity.SendEntity;
import com.hc_android.hc_css.entity.TagEntity;
import com.hc_android.hc_css.entity.TeamEntity;
import com.hc_android.hc_css.entity.TokenEntity;
import com.hc_android.hc_css.entity.VerityEntity;
import com.hc_android.hc_css.entity.VerityResultEntity;
import com.hc_android.hc_css.entity.VisitorEntity;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * The interface Apistore.
 *
 * @author wuayng
 */
public interface Apistore {


    /**
     * 登录操作
     * @param map
     * @return
     */
    @GET("account/login")
    Observable<BaseEntity<LoginEntity.DataBean>> toLogin(@QueryMap HashMap<String, String> map);
    /**
     * 登录操作(同步)
     * @param map
     * @return
     */
    @GET("account/login")
    Call<ResponseBody> toCallLogin(@QueryMap HashMap<String, String> map);
    /**
     * 微信登录操作
     * @param map
     * @return
     */
    @GET("account/wechatlogin")
    Observable<BaseEntity<LoginEntity.DataBean>> toWeChatLogin(@QueryMap HashMap<String, String> map);

    /**
     * 获取对话列表
     * @param map
     * @return
     */
    @GET("dialog/get")
    Observable<BaseEntity<MessageDialogEntity.DataBean>> showMessageDialog(@QueryMap HashMap<String, String> map);

    /**
     * 获取同事列表
     * @param map
     * @return
     */
    @GET("company/control")
    Observable<BaseEntity<TeamEntity.DataBean>> getTeamList(@QueryMap HashMap<String, String> map);

    /**
     * 获取新对话列表信息
     * @param map
     * @return
     */
    @GET("dialog/getone")
    Observable<BaseEntity<MessageDialogEntity.DataBean>> getNewDialog(@QueryMap HashMap<String, String> map);


   /**
     * 对话被接待
     * @param map
     * @return
     */
    @GET("dialog/reception")
    Observable<BaseEntity<ReceptionEntity.DataBean>> receptionDialog(@QueryMap HashMap<String, Object> map);

    /**
     * 对话结束
     * @param
     * @return
     */
    @FormUrlEncoded
    @POST("dialog/autoend")
    Observable<BaseEntity<ReceptionEntity.DataBean>> endDialog(@FieldMap Map<String, String> map);

    /**
     * 顾客访问轨迹
     * @param map
     * @return
     */
    @GET("realtime/getroutes")
    Observable<BaseEntity<CustomPathEntity.DataBean>> getRoutes(@QueryMap HashMap<String, String> map);

    /**
     * 聊天记录列表
     * @param map
     * @return
     */
    @GET("message/get")
    Observable<BaseEntity<ChatEntity.DataBean>> getChatList(@QueryMap HashMap<String, String> map);

    /**
     * 消息发送
     * @param map
     * @return
     */
    @GET("message/push")
    Observable<BaseEntity<SendEntity.DataBean>> sendMsg(@QueryMap HashMap<String, String> map);
    /**
     * 消息已读
     * @param map
     * @return
     */
    @GET("message/read")
    Observable<BaseEntity<TokenEntity.DataBean>> sendRead(@QueryMap HashMap<String, String> map);


    /**
     * 获取七牛上传token
     * @param map
     * @return
     */
    @GET("message/qiniutoken")
    Observable<BaseEntity<TokenEntity.DataBean>> getToken(@QueryMap HashMap<String, String> map);
    /**
     * 获取快捷回复
     * @param
     * @return
     */
    @GET("quick/get")
    Observable<BaseEntity<QuickEntity.DataBean>> getQuickList(@QueryMap HashMap<String, String> map);
  /**
     * 获取快捷分组
     * @param
     * @return
     */
    @GET("grouping/get")
    Observable<BaseEntity<QuickGroupEntity.DataBean>> getQuickGroup(@QueryMap HashMap<String, String> map);
  /**
     * 快捷回复次数
     * @param
     * @return
     */
    @GET("quick/usage")
    Observable<BaseEntity<IneValuateEntity.DataBean>> quickUsage(@QueryMap HashMap<String, String> map);
  /**
     * 评价邀请
     * @param
     * @return
     */
    @GET("dialog/inevaluate")
    Observable<BaseEntity<IneValuateEntity.DataBean>> sendInevaluate(@QueryMap HashMap<String, String> map);

  /**
     * 消息撤回
     * @param
     * @return
     */
    @GET("message/undo")
    Observable<BaseEntity<IneValuateEntity.DataBean>> msgUndo(@QueryMap HashMap<String, String> map);
    /**
     * 获取顾客标签列表
     * @param
     * @return
     */
    @GET("tag/get")
    Observable<BaseEntity<TagEntity.DataBean>> getTag(@QueryMap HashMap<String, String> map);
    /**
     * 顾客标签增加使用次数
     * @param
     * @return
     */
    @GET("tag/usage")
    Observable<BaseEntity<IneValuateEntity.DataBean>> tagUsage(@QueryMap HashMap<String, String> map);
    /**
     * 获取顾客名片
     * @param
     * @return
     */
    @GET("card/get")
    Observable<BaseEntity<CardEntity.DataBean>> getCard(@QueryMap HashMap<String, String> map);
    /**
     * 修改顾客名片
     * @param
     * @return
     */
    @GET("customer/modify")
    Observable<BaseEntity<IneValuateEntity.DataBean>> updateCard(@QueryMap HashMap<String, String> map);

      /**
     * 修改顾客名片
     * @param
     * @return
     */
    @GET("dialog/transfer")
    Observable<BaseEntity<IneValuateEntity.DataBean>> dialogTransfer(@QueryMap HashMap<String, String> map);
    /**
     * 加入黑名单
     * @param
     * @return
     */
    @GET("blacklist/add")
    Observable<BaseEntity<IneValuateEntity.DataBean>> blackAdd(@QueryMap HashMap<String, String> map);
    /**
     * 移除黑名单
     * @param
     * @return
     */
    @GET("blacklist/delete")
    Observable<BaseEntity<IneValuateEntity.DataBean>> blackDel(@QueryMap HashMap<String, String> map);
    /**
     * 修改备注
     * @param
     * @return
     */
    @GET("dialog/remarks")
    Observable<BaseEntity<IneValuateEntity.DataBean>> updateRemarks(@QueryMap HashMap<String, String> map);
    /**
     * 访客列表
     * @param
     * @return
     */
    @GET("realtime/getlist")
    Observable<BaseEntity<VisitorEntity.DataBean>> getVisitorList(@QueryMap HashMap<String, String> map);
    /**
     * 邀请对话
     * @param
     * @return
     */
    @GET("realtime/invitation")
    Observable<BaseEntity<IneValuateEntity.DataBean>> visitorInvitation(@QueryMap HashMap<String, String> map);
    /**
     * 主动对话
     * @param
     * @return
     */
    @GET("realtime/active")
    Observable<BaseEntity<IneValuateEntity.DataBean>> realtimeActive(@QueryMap HashMap<String, String> map);
    /**
     * 客服资料修改
     * @param
     * @return
     */
    @GET("account/modify")
    Observable<BaseEntity<IneValuateEntity.DataBean>> accountModify(@QueryMap HashMap<String, String> map);
    /**
     * 密码修改
     * @param
     * @return
     */
    @GET("account/modifypassword")
    Observable<BaseEntity<IneValuateEntity.DataBean>> accountModifyPwd(@QueryMap HashMap<String, String> map);
    /**
     * 离线在线状态
     * @param
     * @return
     */
    @GET("account/state")
    Observable<BaseEntity<IneValuateEntity.DataBean>> accountState(@QueryMap HashMap<String, String> map);
    /**
     * 历史对话查询
     * @param
     * @return
     */
    @GET("history/get")
    Observable<BaseEntity<MessageDialogEntity.DataBean>> getHistoryList(@QueryMap HashMap<String, String> map);
    /**
     * 退出登录
     * @param
     * @return
     */
    @GET("account/loginout")
    Observable<BaseEntity<IneValuateEntity.DataBean>> loginout(@QueryMap HashMap<String, String> map);
    /**
     * 版本更新
     * @param
     * @return
     */
    @GET("account/androidversion")
    Observable<BaseEntity<AppUpdateEntity.DataBean>> appUpdate(@QueryMap HashMap<String, String> map);

    /**
     * 号码认证服务
     * @param
     * @return
     */
    @GET("app/verity")
    Observable<BaseEntity<VerityEntity.DataBean>> verityPhone(@QueryMap HashMap<String, String> map);

    /**
     * 实名认证（获取token）
     * @param
     * @return
     */
    @GET("/account/realnametoken")
    Observable<BaseEntity<VerityEntity.DataBean>> verityToken(@QueryMap HashMap<String, String> map);

    /**
     * 实名认证（查询结果）
     * @param
     * @return
     */
    @GET("/account/realnametauth")
    Observable<BaseEntity<VerityResultEntity.DataBean>> verityResult(@QueryMap HashMap<String, String> map);

    /**
     * 企业营业执照（查询结果）
     * @param
     * @return
     */

    @GET("/account/realbusiness")
    Observable<BaseEntity<BusinessEntity.DataBean>> verityBusiness(@QueryMap HashMap<String, String> map);
    /**
     * 企业对公账号信息上传
     * @param
     * @return
     */
    @GET("/account/realbankdata")
    Observable<BaseEntity<IneValuateEntity.DataBean>> realBankData(@QueryMap HashMap<String, String> map);
    /**
     * 确认打款金额上传
     * @param
     * @return
     */
    @GET("/account/realconfirm")
    Observable<BaseEntity<VerityResultEntity.DataBean>> realConfirm(@QueryMap HashMap<String, String> map);

    /**
     * 验证绑定手机邮箱，微信
     * @param
     * @return
     */
    @GET("/account/verification")
    Observable<BaseEntity<IneValuateEntity.DataBean>> verification(@QueryMap HashMap<String, String> map);

    /**
     * 绑定手机邮箱，微信（发送验证码）
     * @param
     * @return
     */
    @GET("/account/vercode")
    Observable<BaseEntity<IneValuateEntity.DataBean>> vercode(@QueryMap HashMap<String, String> map);

    /**
     * 解除绑定绑定手机邮箱，微信
     * @param
     * @return
     */
    @GET("/account/relievebind")
    Observable<BaseEntity<IneValuateEntity.DataBean>> relievebind(@QueryMap HashMap<String, String> map);

    /**
     * 绑定手机邮箱，微信
     */
    @GET("/account/bind")
    Observable<BaseEntity<IneValuateEntity.DataBean>> bind(@QueryMap HashMap<String, String> map);

    /**
     * 账号注册
     */
    @GET("/account/register")
    Observable<BaseEntity<IneValuateEntity.DataBean>> regisiter(@QueryMap HashMap<String, String> map);
    /**
     * 消息免打扰
     */
    @GET("/dialog/disturb")
    Observable<BaseEntity<IneValuateEntity.DataBean>> disturb(@QueryMap HashMap<String, String> map);
    /**
     * 消息置顶
     */
    @GET("/dialog/top")
    Observable<BaseEntity<IneValuateEntity.DataBean>> top(@QueryMap HashMap<String, Object> map);
    /**
     * 历史对话重开
     */
    @GET("/dialog/reopen")
    Observable<BaseEntity<IneValuateEntity.DataBean>> reopen(@QueryMap HashMap<String, String> map);
    /**
     * 微信小程序历史对话重开
     */
    @GET("/message/pushwechat")
    Observable<BaseEntity<IneValuateEntity.DataBean>> pushwechat(@QueryMap HashMap<String, String> map);
}