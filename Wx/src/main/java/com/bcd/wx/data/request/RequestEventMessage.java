package com.bcd.wx.data.request;

import com.bcd.wx.data.Event;
import com.bcd.wx.data.Message;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlCData;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class RequestEventMessage extends Message {
    @JacksonXmlProperty(localName = "Event")
    @JacksonXmlCData
    protected Event event;

    protected JsonNode data;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public JsonNode getData() {
        return data;
    }

    public void setData(JsonNode data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RequestEventMessage{" +
                "event=" + event +
                ", data=" + data +
                ", toUserName='" + toUserName + '\'' +
                ", fromUserName='" + fromUserName + '\'' +
                ", createTime=" + createTime +
                ", msgType=" + msgType +
                '}';
    }
}
