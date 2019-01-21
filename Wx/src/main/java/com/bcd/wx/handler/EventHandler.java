package com.bcd.wx.handler;

import com.bcd.wx.data.MsgType;
import org.dom4j.Element;
import org.springframework.stereotype.Component;

@Component
public class EventHandler extends Handler{
    public EventHandler() {
        super(MsgType.event);
    }

    @Override
    public String handle(Element root) throws Exception {
        String event=root.elementText("Event");
        switch (event){
            //用户未关注时，进行关注后的事件推送
            case "subscribe":{
                return handleSubscribe(root);
            }
            //用户已关注时的事件推送
            case "SCAN":{
                return handleScan(root);
            }
            //上报地理位置事件
            case "LOCATION":{
                return handleLocation(root);
            }
            //点击菜单拉取消息时的事件推送
            case "CLICK":{
                return handleClick(root);
            }
            //点击菜单跳转链接时的事件推送
            case "VIEW":{
                return handleView(root);
            }
            default:{
                return "";
            }
        }
    }

    private String handleSubscribe(Element root){
        return "欢迎第一次订阅";
    }

    private String handleScan(Element root){
        return "你又回来了";
    }

    private String handleLocation(Element root){
        return "上报位置";
    }

    private String handleClick(Element root){
        return "点击";
    }

    private String handleView(Element root){
        return "跳转";
    }
}
