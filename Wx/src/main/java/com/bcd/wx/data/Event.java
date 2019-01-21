package com.bcd.wx.data;

public enum Event {
    //用户取消关注时候
    unsubscribe,
    //用户未关注时，进行关注后的事件推送(关注或者扫描二维码)
    subscribe,
    //用户已关注时的事件推送
    scan,
    //上报地理位置事件
    location,
    //点击菜单拉取消息时的事件推送
    click,
    //点击菜单跳转链接时的事件推送
    view
}
