package com.bcd.wx.service;

import com.bcd.base.util.XmlUtil;
import com.bcd.wx.data.Message;
import com.bcd.wx.data.request.RequestEventMessage;
import com.bcd.wx.data.response.ResponseTextMessage;
import com.bcd.wx.handler.Handler;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

@Service
public class WxService {

    @Value("${wx.name}")
    String wxName;

    @Value("${wx.token}")
    String wxToken;



    private final static Logger logger= LoggerFactory.getLogger(WxService.class);

    public String token(String signature, String timestamp, String nonce, String echostr) {
        logger.debug("\nsignature: "+signature+"\ntimestamp: "+timestamp+"\nnonce: "+nonce+"\nechostr: "+echostr+"\n");
        List<String> list=new ArrayList<>();
        list.add(nonce);
        list.add(timestamp);
        list.add(wxToken);
        Collections.sort(list);
        String res= DigestUtils.sha1Hex(list.stream().reduce((e1, e2)->e1+e2).orElse(""));
        logger.debug("\nres: "+res);
        if(res.equals(signature)){
            return echostr;
        }else{
            return null;
        }
    }

    public String handle(String data) {
        logger.debug("\ndata: "+data);
        try {
            JsonNode jsonNode =XmlUtil.WX_XML_MAPPER.readTree(data);
            String msgType= jsonNode.get("MsgType").asText();
            Handler handler= Handler.MSG_TYPE_TO_HANDLER.get(msgType);
            Message res;
            if(handler==null){
                String toUserName=jsonNode.get("FromUserName").asText();
                res=new ResponseTextMessage(wxName,toUserName,"暂不支持");
            }else{
                Object message= jsonNode.traverse(XmlUtil.WX_XML_MAPPER).readValueAs(handler.getClazz());
                if(message instanceof RequestEventMessage){
                    ((RequestEventMessage) message).setData(jsonNode);
                }
                res= handler.handle((Message)message);
            }
            String resStr=XmlUtil.WX_XML_MAPPER.writeValueAsString(res);
            logger.debug("\nHandler Res: "+resStr);

            return resStr;
        } catch (Exception e) {
            logger.error("\nHandler Failed",e);
            return "failed";
        }
    }

    public static void main(String [] args) throws IOException {
        Map<String,String> dataMap=new HashMap<>();
        dataMap.put("A","a");
        dataMap.put("B","b");
        String res=XmlUtil.WX_XML_MAPPER.writeValueAsString(dataMap);

        logger.info("\nRes: "+res);
    }
}
