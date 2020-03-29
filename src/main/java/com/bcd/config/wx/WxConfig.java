package com.bcd.config.wx;

import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpMessageRouterRule;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import me.chanjar.weixin.mp.config.WxMpConfigStorage;
import me.chanjar.weixin.mp.config.impl.WxMpDefaultConfigImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableConfigurationProperties(WxProperties.class)
public class WxConfig {

    @Autowired
    WxProperties wxProperties;

    @Bean
    public WxMpDefaultConfigImpl wxMpDefaultConfig(){
        WxMpDefaultConfigImpl wxMpDefaultConfig=new WxMpDefaultConfigImpl();
        wxMpDefaultConfig.setAccessToken(wxProperties.token);
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
    public WxMpMessageRouter wxMpMessageRouter(WxMpService wxMpService){
        WxMpMessageRouter wxMpMessageRouter=new WxMpMessageRouter(wxMpService);
        return wxMpMessageRouter;
    }

    @Bean
    public WxMpMessageRouterRule wxMpMessageRouterRule(WxMpMessageRouter wxMpMessageRouter, List<WxMpMessageHandler> handlerList){
        WxMpMessageRouterRule wxMpMessageRouterRule=new WxMpMessageRouterRule(wxMpMessageRouter);
        wxMpMessageRouterRule.setHandlers(handlerList);
        return wxMpMessageRouterRule;
    }

    @Bean
    public WxMpConfigStorage wxMpConfigStorage(){
        WxMpConfigStorage wxMpConfigStorage=new WxMpDefaultConfigImpl();
        return wxMpConfigStorage;
    }
}