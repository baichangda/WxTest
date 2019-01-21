package com.bcd.wx.data;

import com.bcd.wx.data.MsgType;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.Date;

@JacksonXmlRootElement(localName = "xml")
public class Message {

    @JacksonXmlProperty(localName = "ToUserName")
//    @JacksonXmlCData
    protected String toUserName;

    @JacksonXmlProperty(localName = "FromUserName")
//    @JacksonXmlCData
    protected String fromUserName;

    @JacksonXmlProperty(localName = "CreateTime")
//    @JacksonXmlCData
    protected Date createTime;

    @JacksonXmlProperty(localName = "MsgType")
//    @JacksonXmlCData
    protected MsgType msgType;



    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public MsgType getMsgType() {
        return msgType;
    }

    public void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }

    @Override
    public String toString() {
        return "Message{" +
                "toUserName='" + toUserName + '\'' +
                ", fromUserName='" + fromUserName + '\'' +
                ", createTime=" + createTime +
                ", msgType=" + msgType +
                '}';
    }
}
