package com.bcd.wx.bean;

import com.bcd.mongodb.bean.SuperBaseBean;
import com.bcd.mongodb.code.CodeGenerator;
import com.bcd.mongodb.code.CollectionConfig;
import com.bcd.mongodb.code.Config;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Document(collection = "account")
public class AccountBean extends SuperBaseBean<String>{
    //微信用户id
    private String userId;

    //借款人
    private String borrower;

    //借款总金额(正数代表欠款,负数代表还款)
    private BigDecimal balance=new BigDecimal(0);

    //借款人创建时间
    private Date createTime;

    //借款人备注
    private String remark;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public static void main(String [] args){
        Config configProperties=new Config(
                new CollectionConfig("Account","借款信息", AccountBean.class),
                new CollectionConfig("AccountDetail","借款明细信息", AccountDetailBean.class)
        );
        CodeGenerator.generate(configProperties);
    }
}
