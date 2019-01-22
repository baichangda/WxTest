package com.bcd.wx.handler;

import com.bcd.base.condition.Condition;
import com.bcd.base.condition.impl.StringCondition;
import com.bcd.wx.bean.AccountBean;
import com.bcd.wx.bean.AccountDetailBean;
import com.bcd.wx.data.Mode;
import com.bcd.wx.service.AccountDetailService;
import com.bcd.wx.service.AccountService;
import com.mongodb.client.result.DeleteResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class AccountModeHandler extends ModeHandler{

    @Autowired
    AccountService accountService;

    @Autowired
    AccountDetailService accountDetailService;

    public AccountModeHandler() {
        super(Mode.ACCOUNT_MODE);
    }

    public String getHelp(){
        StringBuilder msg=new StringBuilder();
        msg.append("示例命令如下:\n");
        msg.append("--添加借款,格式:\n");
        msg.append("张三 100 借用钱(备注可不传)\n");
        msg.append("--查询借款信息,格式:\n");
        msg.append("1 姓名(不传则查出所有)\n");
        msg.append("--查询借款明细信息,格式:\n");
        msg.append("2 姓名(不传则查出所有)\n");
        msg.append("--删除借款信息,格式:\n");
        msg.append("3 姓名");
        msg.append("--退出[记账]模式");
        msg.append("0");
        return msg.toString();
    }

    @Override
    public String handle(String userId,String content) {
        String trimContent=content.trim();
        //帮助
        String[] arr=trimContent.split(" ");
        switch (arr[0]){
            case "1":{
                List<Condition> conditionList=new ArrayList<>();
                conditionList.add(new StringCondition("userId",userId));
                if(arr.length>=2){
                    conditionList.add(new StringCondition("borrower",arr[1]));
                }
                List<AccountBean> accountBeanList= accountService.findAll(Condition.and(conditionList));
                if(accountBeanList.size()==0){
                    return "查询不到信息";
                }else{
                    return accountBeanList.stream().map(e->e.getBorrower()+" "+e.getBalance()).reduce((e1,e2)->e1+"\n"+e2).get();
                }
            }
            case "2":{
                List<Condition> conditionList=new ArrayList<>();
                conditionList.add(new StringCondition("userId",userId));
                if(arr.length>=2){
                    conditionList.add(new StringCondition("borrower",arr[1]));
                }
                List<AccountDetailBean> accountDetailBeanList= accountDetailService.findAll(Condition.and(conditionList));
                if(accountDetailBeanList.size()==0){
                    return "查询不到信息";
                }else{
                    DateTimeFormatter formatter=DateTimeFormatter.ofPattern("yyyy年MM月dd日HH点mm分ss秒").withZone(ZoneId.of("Asia/Shanghai"));
                    return accountDetailBeanList.stream().map(e->e.getBorrower()+" "+e.getMoney()+" "+formatter.format(e.getTime().toInstant())).reduce((e1,e2)->e1+"\n"+e2).get();
                }
            }
            case "3":{
                if(arr.length<2){
                    return getHelp();
                }
                List<Condition> conditionList=new ArrayList<>();
                conditionList.add(new StringCondition("userId",userId));
                conditionList.add(new StringCondition("borrower",arr[1]));
                Condition condition= Condition.and(conditionList);
                DeleteResult result=accountService.delete(condition);
                accountDetailService.delete(condition);
                if(result.getDeletedCount()==0){
                    return "找不到要删除的信息";
                }else{
                    return "删除成功";
                }
            }
            default:{
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
                            accountBean.setUserId(userId);
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
                return getHelp();
            }
        }
    }
}
