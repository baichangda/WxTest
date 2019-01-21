package com.bcd.wx.handler;

import com.bcd.wx.data.MsgType;
import org.dom4j.Element;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Handler {

    protected MsgType msgType;

    public final static Map<String,Handler> MSG_TYPE_TO_HANDLER=new ConcurrentHashMap<>();

    public Handler(MsgType msgType) {
        this.msgType=msgType;
        MSG_TYPE_TO_HANDLER.put(msgType.name(),this);
    }

    public abstract String handle(Element root) throws Exception;
}
