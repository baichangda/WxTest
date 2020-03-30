package com.bcd.wx.bean;

import com.bcd.mongodb.bean.SuperBaseBean;
import com.bcd.mongodb.code.CodeGenerator;
import com.bcd.mongodb.code.CollectionConfig;
import com.bcd.mongodb.code.Config;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "wxUser")
public class WxUserBean extends SuperBaseBean<String>{
    //微信用户id
    @ApiModelProperty(value = "微信用户openId")
    private String openId;

    //微信用户id
    @ApiModelProperty(value = "微信用户名称")
    private String name;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void main(String [] args){
        Config configProperties=new Config(
                new CollectionConfig("WxUser","微信用户信息", WxUserBean.class)
        );
        CodeGenerator.generate(configProperties);
    }
}
