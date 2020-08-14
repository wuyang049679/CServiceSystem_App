package com.hc_android.hc_css.greendao.gen;

import com.hc_android.hc_css.entity.MessageEntity;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;

@Entity//聊天记录本地缓存
public class CacheBean {

    @Id(autoincrement = true)
    private Long keyId;
    @Unique//唯一性
    private String dId;//对话id
    @Convert(converter = SubCachedBeanConverter.class, columnType = String.class)//聊天列表
    private List<MessageEntity.MessageBean> beanList;
    @Generated(hash = 273446677)
    public CacheBean(Long keyId, String dId, List<MessageEntity.MessageBean> beanList) {
        this.keyId = keyId;
        this.dId = dId;
        this.beanList = beanList;
    }
    @Generated(hash = 573552170)
    public CacheBean() {
    }
    public Long getKeyId() {
        return this.keyId;
    }
    public void setKeyId(Long keyId) {
        this.keyId = keyId;
    }
    public String getDId() {
        return this.dId;
    }
    public void setDId(String dId) {
        this.dId = dId;
    }
    public List<MessageEntity.MessageBean> getBeanList() {
        return this.beanList;
    }
    public void setBeanList(List<MessageEntity.MessageBean> beanList) {
        this.beanList = beanList;
    }


}
