package com.hc_android.hc_css.utils.android.app;

import android.util.Log;

import com.hc_android.hc_css.entity.MessageEntity;
import com.hc_android.hc_css.greendao.gen.CacheBean;
import com.hc_android.hc_css.greendao.gen.CacheBeanDao;
import com.hc_android.hc_css.greendao.gen.CommonDaoUtils;
import com.hc_android.hc_css.greendao.gen.DaoUtilsStore;
import com.hc_android.hc_css.utils.Constant;
import com.hc_android.hc_css.utils.NullUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 处理聊天记录缓存
 */
public class CacheData {
    private static CommonDaoUtils<CacheBean> meiziDaoUtils;



    //维护一个map集合保存聊天记录
//    private static HashMap<String, List<MessageEntity.MessageBean>> hashMap=new HashMap<>();//聊天列表消息缓存



    /**
     * 保存聊天信息集合
     */
    public static synchronized void saveCache(String dialogId,List<MessageEntity.MessageBean> messageBeans){
//        if (hashMap.get(dialogId)==null){
//            hashMap.put(dialogId,messageBeans);
//        }else {
//
//        }
        DaoUtilsStore _Store = DaoUtilsStore.getInstance();
        meiziDaoUtils = _Store.getMeiziDaoUtils();
        CacheBean cacheBean=new CacheBean(null,dialogId,messageBeans);
        List<MessageEntity.MessageBean> cacheList = getCacheList(dialogId);
        if (cacheList==null) {
            meiziDaoUtils.insert(cacheBean);
        }else {
            meiziDaoUtils.replaces(cacheBean);
        }



    }
    /**
     * 保存单个聊天信息集合
     */
    public static synchronized void saveCache(String dialogId,MessageEntity.MessageBean messageBeans){
        List<MessageEntity.MessageBean> beans=null;
//        List<MessageEntity.MessageBean> hashBeans = hashMap.get(dialogId);
        List<MessageEntity.MessageBean> hashBeans = getCacheList(dialogId);

        if (hashBeans==null){
            beans=new ArrayList<>();
            beans.add(0,messageBeans);
//            hashMap.put(dialogId,beans);
            CacheBean cacheBean=new CacheBean(null,dialogId,beans);
            meiziDaoUtils.insert(cacheBean);
        }else {
//            beans=hashMap.get(dialogId);
            beans=hashBeans;
            //如果为自己上传的没有id,只有key的消息直接保存
            if (messageBeans.getId()==null&&messageBeans.getKey()!=null){
                beans.add(0,messageBeans);
            }

            //消息推送过来的消息，有id,比较是否存在id,没有直接保存
            if (messageBeans.getId()!=null){
                boolean needAdd=true;
            for (int i = 0; i < beans.size(); i++) {
               if (beans.get(i).getId()!=null&&beans.get(i).getId().equals(messageBeans.getId())){
                   needAdd=false;
               }
            }
            if (needAdd)beans.add(0,messageBeans);
            }
            if (beans.size()>=20) {
//                hashMap.put(dialogId, beans.subList(0, 20));
                CacheBean cacheBean=new CacheBean(null,dialogId,beans.subList(0,20));
                meiziDaoUtils.replaces(cacheBean);
            }else {
//                hashMap.put(dialogId, beans);
                CacheBean cacheBean=new CacheBean(null,dialogId,beans);
                meiziDaoUtils.replaces(cacheBean);
            }
        }
    }
    /**
     * 更新单个缓存数据（主要是发送成替换设置消息id，来定位消息位置）
     */
    public static synchronized void updateCache(String dialogId,MessageEntity.MessageBean messageBeans){
//        List<MessageEntity.MessageBean> beans = hashMap.get(dialogId);
        List<MessageEntity.MessageBean> beans = getCacheList(dialogId);
        if (beans!=null){
            for (int i = 0; i < beans.size(); i++) {
                if (beans.get(i).getKey()!=null&&beans.get(i).getKey().equals(messageBeans.getKey())){
                    //通过key是否相等来判断哪条消息
                    beans.get(i).setId(messageBeans.getId());
                    beans.get(i).setSendState(messageBeans.getSendState());
                    beans.get(i).setUndo(messageBeans.isUndo());
                    if (messageBeans.getItemType()!=0)beans.get(i).setItemType(messageBeans.getItemType());
                    if (messageBeans.getContents()!=null)beans.get(i).setContents(messageBeans.getContents());
                    if (messageBeans.getType()!=null)beans.get(i).setType(messageBeans.getType());
                    if (messageBeans.getSendType()!=null)beans.get(i).setSendType(messageBeans.getSendType());
                }
            }
//            hashMap.put(dialogId,beans);
            CacheBean cacheBean=new CacheBean(null,dialogId,beans);
           meiziDaoUtils.replaces(cacheBean);
        }
    }
    /**
     * 清除单个缓存数据（主要是发送成替换设置消息id，来定位消息位置）
     */
    public static synchronized void removeCache(String dialogId,MessageEntity.MessageBean messageBeans){
//        List<MessageEntity.MessageBean> beans = hashMap.get(dialogId);
        List<MessageEntity.MessageBean> beans = getCacheList(dialogId);
        if (beans!=null){
            for (int i = 0; i < beans.size(); i++) {
                if (beans.get(i).getKey()!=null&&beans.get(i).getKey().equals(messageBeans.getKey())){
                    //通过key是否相等来判断哪条消息
                    beans.remove(i);
                    break;
                }
            }
//            hashMap.put(dialogId,beans);
            CacheBean cacheBean=new CacheBean(null,dialogId,beans);
            meiziDaoUtils.replaces(cacheBean);
        }
    }
    /**
     * 清除某个对话缓存数据（主要是发送成替换设置消息id）
     */
    public static synchronized void removeCache(String dialogId){
//        List<MessageEntity.MessageBean> beans = hashMap.get(dialogId);
        List<MessageEntity.MessageBean> beans = getCacheList(dialogId);
        if (beans!=null){
            beans.clear();
            CacheBean cacheBean=new CacheBean(null,dialogId,beans);
            meiziDaoUtils.replaces(cacheBean);
        }
    }
    /**
     * 获取缓存列表
     */
    public static List<MessageEntity.MessageBean> getCacheList(String dialogId){

        if (meiziDaoUtils==null){
            DaoUtilsStore _Store = DaoUtilsStore.getInstance();
            meiziDaoUtils = _Store.getMeiziDaoUtils();
        }
//        return hashMap.get(dialogId);
        List<CacheBean> cacheBeans = meiziDaoUtils.queryByQueryBuilder(CacheBeanDao.Properties.DId.eq(dialogId));

        if (cacheBeans!=null&&cacheBeans.size()>0&&cacheBeans.get(0).getBeanList()!=null){
            return cacheBeans.get(0).getBeanList();
        }
        return null;
    }

    /**
     * 获取所有缓存(将所有正在加载发送状态置为发送失败)
     */
    public static  void  getAllCache(){

        if (meiziDaoUtils==null){
            DaoUtilsStore _Store = DaoUtilsStore.getInstance();
            meiziDaoUtils = _Store.getMeiziDaoUtils();
        }
        List<CacheBean> cacheBeans = meiziDaoUtils.queryAll();
        for (int i = 0; i < cacheBeans.size(); i++) {
            List<MessageEntity.MessageBean> beanList = cacheBeans.get(i).getBeanList();
            if (beanList!=null){
//                boolean needUpdate=false;
                for (int i1 = 0; i1 < beanList.size(); i1++) {
                    if (beanList.get(i1).getSendState()!=null&&beanList.get(i1).getSendState().equals(Constant._ISLOADING)){
                        beanList.get(i1).setSendState(Constant._ISFAILED);
                        updateCache(cacheBeans.get(i).getDId(),beanList.get(i1));
//                        needUpdate=true;
                    }
//                    if (needUpdate){
//                        updateCache(cacheBeans.get(i).getDId(),);
//                    }
                }

            }
        }
    }
}
