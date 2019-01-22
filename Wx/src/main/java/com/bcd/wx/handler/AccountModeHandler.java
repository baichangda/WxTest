package com.bcd.wx.handler;

import com.bcd.base.condition.Condition;
import com.bcd.base.condition.impl.StringCondition;
import com.bcd.wx.bean.AccountBean;
import com.bcd.wx.bean.AccountDetailBean;
import com.bcd.wx.data.Mode;
import com.bcd.wx.service.AccountDetailService;
import com.bcd.wx.service.AccountService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class AccountModeHandler extends ModeHandler{

    @Service
    AccountService accountService;

    @Service
    AccountDetailService accountDetailService;

    public AccountModeHandler() {
        super(Mode.ACCOUNT_MODE);
    }

    @Override
    public String handle(String userId,String content) {
        //借款人 借款金额 备注
        String[] arr=content.trim().split(" ");
        if(arr.length>=2){
            try {
                String borrower=arr[0];
                BigDecimal money = new BigDecimal(arr[1]);
                AccountBean accountBean= accountService.findOne(Condition.and(
                        new StringCondition("userId",userId),
                        new StringCondition("borrower",borrower)
                ));
                if(accountBean==null){
                    accountBean=new AccountBean();
                    accountBean.setBorrower(borrower);
                    accountBean.setCreateTime(new Date());
                }
                accountBean.setBalance(accountBean.getBalance().add(money));
                accountService.save(accountBean);

                AccountDetailBean accountDetailBean=new AccountDetailBean();
                accountDetailBean.setAccountId(accountBean.getId());
                accountDetailBean.setUserId(accountBean.getUserId());
                accountDetailBean.setBorrower(borrower);
                accountDetailBean.setTime(new Date());
                accountDetailBean.setRemark(arr.length>=3?arr[2]:null);
                accountDetailBean.setMoney(money);

                accountDetailService.save(accountDetailBean);

                StringBuilder msg=new StringBuilder("记录成功,");
                msg.append(borrower);
                msg.append(",本次[");
                msg.append(money);
                msg.append("],总计[");
                msg.append(accountBean.getBalance());
                msg.append("]");
                return msg.toString();
            }catch (NumberFormatException e){
                logger.error("Error",e);
            }
        }
        StringBuilder msg=new StringBuilder("格式错误,示例如下:\n");
        msg.append("张三 ");
        msg.append("100 ");
        msg.append("麻将钱(可不填写)");
        msg.append("\n");
        msg.append("李四 ");
        msg.append("-100 ");
        msg.append("麻将还钱(可不填写)");
        return msg.toString();
    }
}
