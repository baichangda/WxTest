package com.bcd.wx.bean;

import com.bcd.mongodb.bean.SuperBaseBean;
import com.bcd.mongodb.code.freemarker.CodeGenerator;
import com.bcd.mongodb.code.freemarker.CollectionConfig;
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
        CollectionConfig config=new CollectionConfig("WxUser","微信用户",WxUserBean.class);
        CodeGenerator.generate(config);
    }
}
