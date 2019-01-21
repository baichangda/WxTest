package com.bcd.wx.data.request;

import com.bcd.wx.data.Message;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class RequestTextMessage extends Message {
    @JacksonXmlProperty(localName = "MsgId")
    @JacksonXmlCData
    protected Long msgId;

    @JacksonXmlProperty(localName = "Content")
    @JacksonXmlCData
    protected String content;

    public Long getMsgId() {
        return msgId;
    }

    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "RequestTextMessage{" +
                "msgId=" + msgId +
                ", content='" + content + '\'' +
                ", toUserName='" + toUserName + '\'' +
                ", fromUserName='" + fromUserName + '\'' +
                ", createTime=" + createTime +
                ", msgType=" + msgType +
                '}';
    }
}
