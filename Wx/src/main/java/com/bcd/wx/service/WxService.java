package com.bcd.wx.service;

import com.bcd.base.util.JsonUtil;
import com.bcd.wx.bean.UserMessageBean;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class WxService {

    @Value("${wx.token}")
    String wxToken;

    private final static Logger logger= LoggerFactory.getLogger(WxService.class);

    public String token(String signature, String timestamp, String nonce, String echostr) {
        logger.info("\nsignature: "+signature+"\ntimestamp: "+timestamp+"\nnonce: "+nonce+"\nechostr: "+echostr+"\n");
        List<String> list=new ArrayList<>();
        list.add(nonce);
        list.add(timestamp);
        list.add(wxToken);
        Collections.sort(list);
        String res= DigestUtils.sha1Hex(list.stream().reduce((e1, e2)->e1+e2).orElse(""));
        logger.info("\nres: "+res);
        if(res.equals(signature)){
            return echostr;
        }else{
            return null;
        }
    }

    public String handle(String data) {
        logger.info("\ndata: "+data);
        try {
            UserMessageBean userMessageBean=JsonUtil.GLOBAL_OBJECT_MAPPER.readValue(data, UserMessageBean.class);
            logger.info("\nUserMessageBean: "+userMessageBean);
        } catch (Exception e) {
            e.printStackTrace();
            return "failed";
        }
        return "success";
    }
}
