package com.bcd.wx.handler;

import me.chanjar.weixin.mp.api.WxMpMessageHandler;

public abstract class AbstractWxMpMessageHandler implements WxMpMessageHandler {
    protected String msgType;

    public AbstractWxMpMessageHandler(String msgType) {
        this.msgType = msgType;
    }

    public String getMsgType() {
        return msgType;
    }
}
