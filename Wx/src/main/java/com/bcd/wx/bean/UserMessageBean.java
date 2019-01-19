package com.bcd.wx.bean;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class UserMessageBean {
    @JsonProperty("URL")
    private String URL;
    @JsonProperty("ToUserName")
    private String ToUserName;
    @JsonProperty("FromUserName")
    private String FromUserName;
    @JsonProperty("CreateTime")
    private Date CreateTime;
    @JsonProperty("MsgType")
    private String MsgType;
    @JsonProperty("Content")
    private String Content;
    @JsonProperty("MsgId")
    private Long MsgId;

    public String getURL() {
        return URL;
    }

    public void setURL(String URL) {
        this.URL = URL;
    }

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public Date getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Date createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public Long getMsgId() {
        return MsgId;
    }

    public void setMsgId(Long msgId) {
        MsgId = msgId;
    }

    @Override
    public String toString() {
        return "UserMessageBean{" +
                "URL='" + URL + '\'' +
                ", ToUserName='" + ToUserName + '\'' +
                ", FromUserName='" + FromUserName + '\'' +
                ", CreateTime=" + CreateTime +
                ", MsgType='" + MsgType + '\'' +
                ", Content='" + Content + '\'' +
                ", MsgId=" + MsgId +
                '}';
    }
}
