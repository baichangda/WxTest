package com.bcd.wx.handler;

import com.bcd.base.util.JsonUtil;
import com.bcd.wx.data.Event;
import com.bcd.wx.data.Message;
import com.bcd.wx.data.MsgType;
import com.bcd.wx.data.request.RequestEventMessage;
import com.bcd.wx.data.response.ResponseTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EventHandler extends Handler<RequestEventMessage>{



    @Value("${wx.name}")
    String wxName;

    private final static Logger logger= LoggerFactory.getLogger(EventHandler.class);
    public EventHandler() {
        super(MsgType.event,RequestEventMessage.class);
    }

    @Override
    public Message handle(RequestEventMessage message) throws Exception {
        logger.debug(message.toString());

        Event event=message.getEvent();

        switch (event){
            //用户取消关注时候
            case unsubscribe:{
                return handleUnSubscribe(message);
            }
            //用户未关注时，进行关注后的事件推送(关注或者扫描二维码)
            case subscribe:{
                return handleSubscribe(message);
            }
            //用户已关注时的事件推送
            case scan:{
                return handleScan(message);
            }
            //上报地理位置事件
            case location:{
                return handleLocation(message);
            }
            //点击菜单拉取消息时的事件推送
            case click:{
                return handleClick(message);
            }
            //点击菜单跳转链接时的事件推送
            case view:{
                return handleView(message);
            }
            default:{
                return null;
            }
        }
    }

    private Message handleSubscribe(RequestEventMessage message){
        return new ResponseTextMessage(wxName,message.getFromUserName(),"欢迎订阅");
    }

    private Message handleUnSubscribe(RequestEventMessage message){
        return new ResponseTextMessage(wxName,message.getFromUserName(),"取消订阅");
    }

    private Message handleScan(RequestEventMessage message){
        return new ResponseTextMessage(wxName,message.getFromUserName(),"你又回来了");
    }

    private Message handleLocation(RequestEventMessage message){
        return new ResponseTextMessage(wxName,message.getFromUserName(),"上报位置");
    }

    private Message handleClick(RequestEventMessage message){
        return new ResponseTextMessage(wxName,message.getFromUserName(),"点击");
    }

    private Message handleView(RequestEventMessage message){
        return new ResponseTextMessage(wxName,message.getFromUserName(),"跳转");
    }
}
