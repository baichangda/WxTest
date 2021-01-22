package com.bcd.wx.service;

import com.bcd.base.condition.impl.StringCondition;
import com.bcd.mongodb.service.BaseService;
import org.springframework.stereotype.Service;
import com.bcd.wx.bean.WxUserBean;

@Service
public class WxUserService extends BaseService<WxUserBean,String> {

    public WxUserBean findByOpenId(String fromUser) {
        return findOne(new StringCondition("openId",fromUser));
    }
}
