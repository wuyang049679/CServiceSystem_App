package com.hc_android.hc_css.utils.android.app;

import com.hc_android.hc_css.base.BaseApplication;

import me.leolin.shortcutbadger.ShortcutBadger;

/**
 * 创建时间: 2018/4/4
 * gxx
 * 注释描述:角标个数
 */
public class ShortCutBadgerCount {
    private ShortCutBadgerCount() {
    }
    private int cutCount=0;
    private volatile static ShortCutBadgerCount shortCutBadgerCount;
    public static ShortCutBadgerCount getShortCutBadgerCount(){
        if (shortCutBadgerCount==null){
            synchronized (ShortCutBadgerCount.class){
                if (shortCutBadgerCount==null){
                    shortCutBadgerCount = new ShortCutBadgerCount();
                }
            }
        }
        return shortCutBadgerCount;
    }
    /**
     *作者：GaoXiaoXiong
     *创建时间:2018/4/4
     *注释描述:添加1个角标
     */
    public int addCount(){
        this.cutCount+=1;
        setCutCount(this.cutCount);
        return cutCount;
    }
    /**
     *作者：GaoXiaoXiong
     *创建时间:2018/4/4
     *注释描述:设置个数
     */
    public void setCutCount(int cutCount) {
        this.cutCount = cutCount;
    }
    /**
     *作者：GaoXiaoXiong
     *创建时间:2018/4/8
     *注释描述:消息个数
     */
    public int getCutCount() {
        return cutCount;
    }
    /**
     *作者：GaoXiaoXiong
     *创建时间:2018/4/4
     *注释描述:清零
     */
    public void clearCount(){
        cutCount = 0;
    }
    /**
     *作者：GaoXiaoXiong
     *创建时间:2018/4/8
     *注释描述:销毁
     */
    public void destory(){
        if (shortCutBadgerCount!=null){
            removeShortcutBadgerACount();
            shortCutBadgerCount=null;
        }
    }

    /**
     *作者：GaoXiaoXiong
     *创建时间:2018/4/8
     *注释描述:移除角标和清空统计的角标个数
     */
    public void removeShortcutBadgerACount(){
        ShortcutBadger.removeCount(BaseApplication.getContext().getApplicationContext()); //移除桌面角标
        ShortCutBadgerCount.getShortCutBadgerCount().clearCount();//清除个数
    }
}