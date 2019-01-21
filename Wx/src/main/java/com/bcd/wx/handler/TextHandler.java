package com.bcd.wx.handler;

import com.bcd.wx.data.Message;
import com.bcd.wx.data.MsgType;
import com.bcd.wx.data.request.RequestTextMessage;
import com.bcd.wx.data.response.ResponseTextMessage;
import com.bcd.wx.define.CommonConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class TextHandler extends Handler<RequestTextMessage> {

    private final static Logger logger= LoggerFactory.getLogger(TextHandler.class);

    @Value("${wx.name}")
    String wxName;

    public TextHandler() {
        super(MsgType.text,RequestTextMessage.class);
    }

    @Override
    public Message handle(RequestTextMessage message) throws Exception {
        logger.debug(message.toString());
        String name=CommonConst.USER_ID_TO_NAME.get(message.getFromUserName());
        return new ResponseTextMessage(wxName,message.getFromUserName(),name==null?message.getContent():(name+","+message.getContent()));
    }

}
