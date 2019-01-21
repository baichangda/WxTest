package com.bcd.wx.service;

import com.bcd.base.util.JsonUtil;
import com.bcd.base.util.XmlUtil;
import com.bcd.wx.data.JsonNodeDataSupport;
import com.bcd.wx.data.Message;
import com.bcd.wx.data.MsgType;
import com.bcd.wx.data.response.ResponseTextMessage;
import com.bcd.wx.handler.Handler;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;

@Service
public class WxService {

    @Value("${wx.name}")
    String wxName;

    @Value("${wx.token}")
    String wxToken;

    @Value("${wx.aesKey}")
    String wxAesKey;

    @Autowired
    RestTemplate restTemplate;

    //token,过期时间,获取时间
    Object[] accessTokenData;



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
                if(message instanceof JsonNodeDataSupport){
                    ((JsonNodeDataSupport) message).setData(jsonNode);
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

    public void sendAll(String text) {
        Map<String,Object> dataMap=new HashMap<>();
        dataMap.put("filter",new HashMap<String,Object>(){{
            put("is_to_all",Boolean.TRUE);
        }});
        dataMap.put("text",new HashMap<String,String>(){{
            put("content",text);
        }});
        dataMap.put("msgtype", MsgType.text);
        ResponseEntity<Map> responseEntity= restTemplate.postForEntity("https://api.weixin.qq.com/cgi-bin/message/mass/sendall?access_token="+getAccessToken(), dataMap,Map.class);
        logger.debug("SendAll Res: "+JsonUtil.toJson(responseEntity.getBody()));

    }

    public String getAccessToken(){
        if(accessTokenData==null||(System.currentTimeMillis()-(long)accessTokenData[2])>(long)accessTokenData[1]){
            ResponseEntity<JsonNode> responseEntity=restTemplate.getForEntity("https://api.weixin.qq.com/cgi-bin/token?grant_type={1}&appid={2}&secret={3}",JsonNode.class,"client_credential",wxToken,wxAesKey);
            JsonNode jsonNode= responseEntity.getBody();
            logger.debug("getAccessToken Res: "+JsonUtil.toJson(jsonNode));
            String accessToken=jsonNode.get("access_token").asText();
            int expiresIn=jsonNode.get("expires_in").asInt();
            accessTokenData=new Object[]{accessToken,expiresIn*1000L,System.currentTimeMillis()};
        }
        return accessTokenData[0].toString();
    }
}
