package com.bcd.wx.handler;

import com.bcd.wx.data.MsgType;
import com.bcd.wx.data.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Handler<T extends Message>{

    protected Logger logger= LoggerFactory.getLogger(this.getClass());

    protected MsgType msgType;

    protected Class<T> clazz;


    public final static Map<String,Handler> MSG_TYPE_TO_HANDLER=new ConcurrentHashMap<>();

    public Handler(MsgType msgType,Class<T> clazz) {
        this.msgType=msgType;
        this.clazz=clazz;
        MSG_TYPE_TO_HANDLER.put(msgType.name(),this);
    }

    public abstract Message handle(T message) throws Exception;

    public MsgType getMsgType() {
        return msgType;
    }

    public void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }
}
