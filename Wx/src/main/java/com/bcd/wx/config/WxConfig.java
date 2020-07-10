package com.bcd.wx.config;

import com.bcd.wx.handler.AbstractWxMpMessageHandler;
import com.bcd.wx.handler.ImageHandler;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpMessageRouterRule;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(WxProperties.class)
public class WxConfig {

    @Autowired
    WxProperties wxProperties;

    @Bean
    public WxMpDefaultConfigImpl wxMpDefaultConfig(){
        WxMpDefaultConfigImpl wxMpDefaultConfig=new WxMpDefaultConfigImpl();
        wxMpDefaultConfig.setToken(wxProperties.token);
        wxMpDefaultConfig.setAesKey(wxProperties.aesKey);
        wxMpDefaultConfig.setAppId(wxProperties.appId);
        wxMpDefaultConfig.setSecret(wxProperties.appSecret);
        return wxMpDefaultConfig;
    }

    @Bean
    public WxMpService wxMpService(WxMpDefaultConfigImpl wxMpDefaultConfig){
        WxMpService wxService = new WxMpServiceImpl();// 实际项目中请注意要保持单例，不要在每次请求时构造实例，具体可以参考demo项目
        wxService.setWxMpConfigStorage(wxMpDefaultConfig);
        return wxService;
    }

    @Bean
    public WxMpMessageRouter wxMpMessageRouter(WxMpService wxMpService, AbstractWxMpMessageHandler[] handlers){
        WxMpMessageRouter wxMpMessageRouter=new WxMpMessageRouter(wxMpService);
        for (AbstractWxMpMessageHandler handler : handlers) {
            wxMpMessageRouter.rule().async(false).msgType(handler.getMsgType()).handler(handler).end();
        }
        return wxMpMessageRouter;
    }

    @Bean
    public WxMpConfigStorage wxMpConfigStorage(){
        WxMpConfigStorage wxMpConfigStorage=new WxMpDefaultConfigImpl();
        return wxMpConfigStorage;
    }
}
