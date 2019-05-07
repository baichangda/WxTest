package com.bcd.wx.bean;

import com.bcd.mongodb.bean.SuperBaseBean;
import com.bcd.mongodb.code.CodeGenerator;
import com.bcd.mongodb.code.CollectionConfig;
import com.bcd.mongodb.code.Config;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Document(collection = "cost")
public class CostBean extends SuperBaseBean<String> {
    //花费金额
    @ApiModelProperty(value = "花费金额")
    private BigDecimal balance=new BigDecimal(0);
    //花费描述
    @ApiModelProperty(value = "花费描述(正数代表花销,负数代表退还)")
    private String remark;
    //记录时间
    @ApiModelProperty(value = "记录时间")
    private Date createTime;
    //微信用户id
    @ApiModelProperty(value = "微信用户id")
    private String userId;

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static void main(String [] args){
        Config configProperties=new Config(
                new CollectionConfig("Cost","消费", CostBean.class)
        );
        CodeGenerator.generate(configProperties);
    }
}
