package com.bcd.wx.data.response;

import com.bcd.wx.data.Message;
import com.bcd.wx.data.MsgType;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class ResponseTextMessage extends Message{
    @JacksonXmlProperty(localName = "Content")
//    @JacksonXmlCData
    protected String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ResponseTextMessage(String fromUserName,String toUserName,String content) {
        this.fromUserName=fromUserName;
        this.toUserName=toUserName;
        this.content = content;
        this.msgType= MsgType.text;
    }

    @Override
    public String toString() {
        return "ResponseTextMessage{" +
                "content='" + content + '\'' +
                ", toUserName='" + toUserName + '\'' +
                ", fromUserName='" + fromUserName + '\'' +
                ", createTime=" + createTime +
                ", msgType=" + msgType +
                '}';
    }
}
