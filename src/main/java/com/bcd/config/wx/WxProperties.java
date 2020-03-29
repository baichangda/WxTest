package com.bcd.config.wx;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "wx")
public class WxProperties {
    public String token;
    public String aesKey;
    public String name;
    public String appId;
    public String appSecret;
    public String openId;
}
