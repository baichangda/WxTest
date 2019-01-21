package com.bcd.wx.service;

import com.bcd.base.util.JsonUtil;
import com.bcd.wx.handler.Handler;
import org.apache.commons.codec.digest.DigestUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
            Document document= DocumentHelper.parseText(data);
            Element root= document.getRootElement();
            String msgType=root.elementText("MsgType");
            Handler handler= Handler.MSG_TYPE_TO_HANDLER.get(msgType);
            if(handler==null){
                return "failed";
            }
            return handler.handle(root);
        } catch (Exception e) {
            e.printStackTrace();
            return "failed";
        }

    }

    public static void main(String [] args) throws IOException {
        Document res= DocumentHelper.createDocument();
        Element resRoot= res.addElement("xml");
        Element e1=resRoot.addElement("ToUserName");
        e1.setText("1");
        Element e2=resRoot.addElement("FromUserName");
        e2.setText("2");
        Element e3=resRoot.addElement("CreateTime");
        e3.setText(new Date().getTime()+"");
        Element e4=resRoot.addElement("MsgType");
        e4.setText("text");
        Element e5=resRoot.addElement("Content");
        e5.setText("3");
        OutputFormat format = OutputFormat.createCompactFormat();
        StringWriter writer=new StringWriter();
        XMLWriter output = new XMLWriter(writer, format);
        output.write(res);
        logger.info("\nTextHandler Res: "+writer.toString());
    }
}
