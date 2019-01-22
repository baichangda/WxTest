package com.bcd.wx.controller;

import com.bcd.base.condition.Condition;
import com.bcd.base.condition.impl.*;
import com.bcd.base.controller.BaseController;
import com.bcd.base.define.MessageDefine;
import com.bcd.base.message.JsonMessage;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import com.bcd.wx.bean.AccountDetailBean;
import com.bcd.wx.service.AccountDetailService;

@SuppressWarnings(value = "unchecked")
@RestController
@RequestMapping("/api/wx/accountDetail")
public class AccountDetailController extends BaseController {

    @Autowired
    private AccountDetailService accountDetailService;


    /**
     * 查询借款明细信息列表
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value="查询借款明细信息列表",notes = "查询借款明细信息列表")
    @ApiResponse(code = 200,message = "借款明细信息列表")
    public JsonMessage<List<AccountDetailBean>> list(
            @ApiParam(value = "关联的借款信息")
            @RequestParam(value = "accountId",required = false) String accountId,
            @ApiParam(value = "借款人")
            @RequestParam(value = "borrower",required = false) String borrower,
            @ApiParam(value = "借款备注")
            @RequestParam(value = "remark",required = false) String remark,
            @ApiParam(value = "主键")
            @RequestParam(value = "id",required = false) String id,
            @ApiParam(value = "借款时间开始")
            @RequestParam(value = "timeBegin",required = false) Date timeBegin,
            @ApiParam(value = "借款时间截止")
            @RequestParam(value = "timeEnd",required = false) Date timeEnd,
            @ApiParam(value = "微信用户id")
            @RequestParam(value = "userId",required = false) String userId
            ){
        Condition condition= Condition.and(
            new StringCondition("accountId",accountId, StringCondition.Handler.ALL_LIKE),
            new StringCondition("borrower",borrower, StringCondition.Handler.ALL_LIKE),
            new StringCondition("remark",remark, StringCondition.Handler.ALL_LIKE),
            new StringCondition("id",id, StringCondition.Handler.EQUAL),
            new DateCondition("time",timeBegin, DateCondition.Handler.GE),
            new DateCondition("time",timeEnd, DateCondition.Handler.LE),
            new StringCondition("userId",userId, StringCondition.Handler.ALL_LIKE)
        );
        return JsonMessage.success(accountDetailService.findAll(condition));
    }


    /**
     * 查询借款明细信息列表
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ApiOperation(value="查询借款明细信息分页",notes = "查询借款明细信息分页")
    @ApiResponse(code = 200,message = "借款明细信息分页结果集")
    public JsonMessage<Page<AccountDetailBean>> page(
            @ApiParam(value = "关联的借款信息")
            @RequestParam(value = "accountId",required = false) String accountId,
            @ApiParam(value = "借款人")
            @RequestParam(value = "borrower",required = false) String borrower,
            @ApiParam(value = "借款备注")
            @RequestParam(value = "remark",required = false) String remark,
            @ApiParam(value = "主键")
            @RequestParam(value = "id",required = false) String id,
            @ApiParam(value = "借款时间开始")
            @RequestParam(value = "timeBegin",required = false) Date timeBegin,
            @ApiParam(value = "借款时间截止")
            @RequestParam(value = "timeEnd",required = false) Date timeEnd,
            @ApiParam(value = "微信用户id")
            @RequestParam(value = "userId",required = false) String userId,
            @ApiParam(value = "分页参数(页数)",example="1")
            @RequestParam(value = "pageNum",required = false)Integer pageNum,
            @ApiParam(value = "分页参数(页大小)",example="20")
            @RequestParam(value = "pageSize",required = false) Integer pageSize
            ){
        Condition condition= Condition.and(
            new StringCondition("accountId",accountId, StringCondition.Handler.ALL_LIKE),
            new StringCondition("borrower",borrower, StringCondition.Handler.ALL_LIKE),
            new StringCondition("remark",remark, StringCondition.Handler.ALL_LIKE),
            new StringCondition("id",id, StringCondition.Handler.EQUAL),
            new DateCondition("time",timeBegin, DateCondition.Handler.GE),
            new DateCondition("time",timeEnd, DateCondition.Handler.LE),
            new StringCondition("userId",userId, StringCondition.Handler.ALL_LIKE)
        );
        return JsonMessage.success(accountDetailService.findAll(condition,PageRequest.of(pageNum-1,pageSize)));

    }

    /**
     * 保存借款明细信息
     * @param accountDetail
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ApiOperation(value = "保存借款明细信息",notes = "保存借款明细信息")
    @ApiResponse(code = 200,message = "保存结果")
    public JsonMessage save(@ApiParam(value = "借款明细信息实体")  @RequestBody AccountDetailBean accountDetail){
        accountDetailService.save(accountDetail);
        return MessageDefine.SUCCESS_SAVE.toJsonMessage(true);
    }


    /**
     * 删除借款明细信息
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    @ApiOperation(value = "删除借款明细信息",notes = "删除借款明细信息")
    @ApiResponse(code = 200,message = "删除结果")
    public JsonMessage delete(@ApiParam(value = "借款明细信息id数组") @RequestParam String[] ids){
        accountDetailService.deleteById(ids);
        return MessageDefine.SUCCESS_DELETE.toJsonMessage(true);
    }
}
