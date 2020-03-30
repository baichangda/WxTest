package com.bcd.wx.service;

import com.bcd.base.condition.Condition;
import com.bcd.base.condition.impl.StringCondition;
import com.bcd.base.config.init.SpringInitializable;
import com.bcd.mongodb.service.BaseService;
import com.bcd.wx.config.WxProperties;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import com.bcd.wx.bean.WxUserBean;

/**
 *
 */
@Service
public class WxUserService extends BaseService<WxUserBean,String> implements SpringInitializable {

    @Autowired
    WxProperties wxProperties;



    @Override
    public void init(ContextRefreshedEvent event) {
        String name=wxProperties.name;
        String openId=wxProperties.openId;
        if(findByName(name)==null){
            WxUserBean wxUserBean=new WxUserBean();
            wxUserBean.setName(name);
            wxUserBean.setOpenId(openId);
            save(wxUserBean);
        }
    }

    public WxUserBean findByName(String name){
        Condition condition=new StringCondition("name",name, StringCondition.Handler.EQUAL);
        return findOne(condition);
    }

    @Cacheable
    public WxUserBean findByOpenId(String openId){
        Condition condition=new StringCondition("openId",openId, StringCondition.Handler.EQUAL);
        return findOne(condition);
    }
}
