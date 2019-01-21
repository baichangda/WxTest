package com.bcd.wx.handler;

import com.bcd.wx.data.MsgType;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class EventHandler extends Handler{
    private final static Logger logger= LoggerFactory.getLogger(EventHandler.class);
    public EventHandler() {
        super(MsgType.event);
    }

    @Override
    public String handle(Element root) throws Exception {
        String event=root.elementText("Event");
        switch (event){
            //用户取消关注时候
            case "unsubscribe":{
                return handleUnSubscribe(root);
            }
            //用户未关注时，进行关注后的事件推送(关注或者扫描二维码)
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
        return "欢迎订阅";
    }

    private String handleUnSubscribe(Element root){
        return "取消订阅";
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
