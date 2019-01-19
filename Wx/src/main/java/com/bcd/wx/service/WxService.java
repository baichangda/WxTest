package com.bcd.wx.service;

import com.bcd.base.util.JsonUtil;
import com.bcd.wx.bean.UserMessageBean;
import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class WxService {

    @Value("${wx.token}")
    String wxToken;

    @Value("${wx.name}")
    String wxName;

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
            Document document= DocumentHelper.parseText(data);
            Element root= document.getRootElement();
            String fromUserName=root.elementText("FromUserName");
            String content=root.elementText("Content");
            UserMessageBean res=new UserMessageBean();
            res.setToUserName(fromUserName);
            res.setFromUserName(wxName);
            res.setCreateTime(new Date());
            res.setMsgType("text");
            res.setContent(content);
            String resStr=JsonUtil.GLOBAL_OBJECT_MAPPER.writeValueAsString(res);
            logger.info("\nres: "+resStr);
            return resStr;
        } catch (Exception e) {
            e.printStackTrace();
            return "failed";
        }

    }
}
