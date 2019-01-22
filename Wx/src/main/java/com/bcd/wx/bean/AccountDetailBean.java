package com.bcd.wx.bean;

import com.bcd.mongodb.bean.SuperBaseBean;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

@Document(collection = "accountDetail")
public class AccountDetailBean extends SuperBaseBean<String>{
    //关联的借款信息
    private String accountId;

    //微信用户id
    private String userId;

    //借款人
    private String borrower;

    //借款金额(正数代表借款,负数代表还款)
    private BigDecimal money;

    //借款时间
    private Date time;

    //借款备注
    private String remark;

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

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
}
