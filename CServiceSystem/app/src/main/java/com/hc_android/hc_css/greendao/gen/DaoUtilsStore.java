package com.hc_android.hc_css.greendao.gen;


/**
 * 存放DaoUtils
 */
public class DaoUtilsStore
{
    private volatile static DaoUtilsStore instance = new DaoUtilsStore();
    private CommonDaoUtils<CacheBean> meiziDaoUtils;
    private CommonDaoUtils<CacheBean> hanziDaoUtils;

    public static DaoUtilsStore getInstance()
    {
        return instance;
    }

    private DaoUtilsStore()
    {
        DaoManager mManager = DaoManager.getInstance();
        CacheBeanDao _MeiziDao = mManager.getDaoSession().getCacheBeanDao();
        meiziDaoUtils = new CommonDaoUtils(CacheBean.class, _MeiziDao);
//
//        HanziDao _HanziDao = mManager.getDaoSession().getHanziDao();
//        hanziDaoUtils = new CommonDaoUtils(Hanzi.class, _HanziDao);
    }

    public CommonDaoUtils<CacheBean> getMeiziDaoUtils()
    {
        return meiziDaoUtils;
    }

    public CommonDaoUtils<CacheBean> getHanziDaoUtils()
    {
        return hanziDaoUtils;
    }
}