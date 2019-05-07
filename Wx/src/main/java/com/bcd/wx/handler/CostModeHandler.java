package com.bcd.wx.handler;

import com.bcd.base.condition.Condition;
import com.bcd.base.condition.impl.DateCondition;
import com.bcd.base.util.DateUtil;
import com.bcd.base.util.DateZoneUtil;
import com.bcd.base.util.ExceptionUtil;
import com.bcd.wx.bean.CostBean;
import com.bcd.wx.data.Mode;
import com.bcd.wx.define.CommonConst;
import com.bcd.wx.service.CostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Component
public class CostModeHandler extends ModeHandler{

    @Autowired
    CostService costService;


    public final DateTimeFormatter requestDateMinute=DateTimeFormatter.ofPattern("yyyyMMdd").withZone(ZoneId.of("Asia/Shanghai"));

    public final DateTimeFormatter requestDateSecond=DateTimeFormatter.ofPattern("yyyyMMddHHmmss").withZone(ZoneId.of("Asia/Shanghai"));

    public CostModeHandler() {
        super(Mode.COST_MODE);
    }

    public String getHelp(){
        StringBuilder msg=new StringBuilder();
        msg.append("示例命令如下:\n");
        msg.append("--添加花费,格式:\n");
        msg.append("100 xxx瓷砖花费 20190507(可不传)\n");
        msg.append("--查询花费,格式:\n");
        msg.append("<20190507(查询包括日期之前)");
        msg.append(">20190507(查询包括日期之后)");
        msg.append("=20190507(查询日期当天)");
        msg.append("=(查询当天)");
        msg.append("<(查询当天之前)");
        return msg.toString();
    }

    @Override
    public String handle(String userId, String content) {
        //1、检查格式
        if(content==null||content.trim().isEmpty()){
            return getHelp();
        }else {
            String[] arr = content.split(" ");
            if(arr[0].contains("<")||arr[0].contains(">")||arr[0].contains("=")){
                //2、如果是查询
                //2.1、解析查询
                List<Condition> conditionList=new ArrayList<>();
                for(int i=0;i<=arr.length-1;i++){
                    //2.1.1、验证格式
                    boolean isLt=arr[0].contains("<");
                    boolean isGt=arr[0].contains("<");
                    boolean isEq=arr[0].contains("=");
                    if(!isLt&&!isGt&&!isEq){
                        return getHelp();
                    }
                    //2.1.2、获取日期
                    Date date;
                    if(arr[i].length()==1){
                        date=new Date();
                    }else{
                        String dateStr=arr[i].substring(1);
                        date= Date.from(Instant.from(requestDateMinute.parse(dateStr)));
                    }

                    //2.1.3、根据条件格式化日期并组装条件
                    if(isLt){
                        conditionList.add(new DateCondition("createTime",DateZoneUtil.getCeilDate(date,ChronoUnit.DAYS), DateCondition.Handler.LT));
                    }else if(isGt){
                        conditionList.add(new DateCondition("createTime",DateZoneUtil.getFloorDate(date,ChronoUnit.DAYS), DateCondition.Handler.GE));
                    }else{
                        conditionList.clear();
                        conditionList.add(new DateCondition("createTime",DateZoneUtil.getCeilDate(date,ChronoUnit.DAYS), DateCondition.Handler.LT));
                        conditionList.add(new DateCondition("createTime",DateZoneUtil.getFloorDate(date,ChronoUnit.DAYS), DateCondition.Handler.GE));
                        break;
                    }
                }
                List<CostBean> costBeanList= costService.findAll(Condition.and(conditionList));
                costBeanList.stream().sorted(Comparator.comparing(e->((CostBean)e).getCreateTime()).reversed());
                double sum=costBeanList.stream().map(e->e.getBalance()).reduce((e1,e2)->e1.add(e2)).orElse(new BigDecimal(0)).doubleValue();
                StringBuilder sb=new StringBuilder();
                sb.append("合计: ");
                sb.append(sum);
                sb.append("\n");
                costBeanList.forEach(e->{
                    sb.append(e.getBalance().doubleValue());
                    sb.append(" ");
                    sb.append(e.getRemark());
                    sb.append(" ");
                    sb.append(CommonConst.RESPONSE_DATE_FORMAT.format(e.getCreateTime().toInstant()));
                    sb.append("\n");
                });
                return sb.toString();
            }else{
                try {
                    //3、如果是添加花费
                    BigDecimal balance = new BigDecimal(arr[0]);
                    if(arr.length<2){
                        return getHelp();
                    }
                    CostBean costBean=new CostBean();
                    costBean.setBalance(balance);
                    costBean.setRemark(arr[1]);
                    if(arr.length>=3){
                        switch (arr[2].length()){
                            case 8:{
                                costBean.setCreateTime(Date.from(Instant.from(requestDateMinute.parse(arr[2]))));
                                break;
                            }
                            case 14:{
                                costBean.setCreateTime(Date.from(Instant.from(requestDateSecond.parse(arr[2]))));
                                break;
                            }
                            default:{
                                return getHelp();
                            }
                        }
                    }else{
                        costBean.setCreateTime(new Date());
                    }
                    costService.save(costBean);
                    return "记录成功";
                }catch (NumberFormatException ex){
                    ExceptionUtil.printException(ex);
                    return getHelp();
                }
            }
        }
    }
}
