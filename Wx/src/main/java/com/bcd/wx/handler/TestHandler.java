package com.bcd.wx.handler;

import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.builder.outxml.TextBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class TestHandler implements WxMpMessageHandler {
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        TextBuilder textBuilder=WxMpXmlOutMessage.TEXT().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser());
        switch (wxMessage.getMsgType()){
            case WxConsts.XmlMsgType.TEXT:{
                StringBuilder msg=new StringBuilder("你好,");
                msg.append(wxMessage.getOpenId());
                msg.append(",欢迎使用,");
                msg.append(wxMessage.getContent());
                return textBuilder.content(msg.toString()).build();
            }
            default:{
                StringBuilder msg=new StringBuilder("你好,");
                msg.append(wxMessage.getOpenId());
                msg.append(",暂不支持消息类型[");
                msg.append(wxMessage.getMsgType());
                msg.append("]");
                return textBuilder.content(msg.toString()).build();
            }
        }
    }
}