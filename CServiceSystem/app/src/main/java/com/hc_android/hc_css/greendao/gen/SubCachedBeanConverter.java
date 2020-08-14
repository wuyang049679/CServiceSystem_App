package com.hc_android.hc_css.greendao.gen;

import com.hc_android.hc_css.entity.MessageEntity;
import com.hc_android.hc_css.utils.JsonParseUtils;

import org.greenrobot.greendao.converter.PropertyConverter;

import java.util.List;

public class SubCachedBeanConverter implements PropertyConverter<List<MessageEntity.MessageBean>,String> {

    @Override
    public List<MessageEntity.MessageBean> convertToEntityProperty(String databaseValue) {
        return JsonParseUtils.parseToPureList(databaseValue, MessageEntity.MessageBean.class);
    }

    @Override
    public String convertToDatabaseValue(List<MessageEntity.MessageBean> entityProperty) {
        return JsonParseUtils.parseToJson(entityProperty);
    }
}
