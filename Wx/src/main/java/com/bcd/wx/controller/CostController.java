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
import com.bcd.wx.bean.CostBean;
import com.bcd.wx.service.CostService;

@SuppressWarnings(value = "unchecked")
@RestController
@RequestMapping("/api/wx/cost")
public class CostController extends BaseController {

    @Autowired
    private CostService costService;


    /**
     * 查询消费列表
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value="查询消费列表",notes = "查询消费列表")
    @ApiResponse(code = 200,message = "消费列表")
    public JsonMessage<List<CostBean>> list(
            @ApiParam(value = "花费描述")
            @RequestParam(value = "remark",required = false) String remark,
            @ApiParam(value = "主键")
            @RequestParam(value = "id",required = false) String id,
            @ApiParam(value = "微信用户id")
            @RequestParam(value = "userId",required = false) String userId
            ){
        Condition condition= Condition.and(
            new StringCondition("remark",remark, StringCondition.Handler.ALL_LIKE),
            new StringCondition("id",id, StringCondition.Handler.EQUAL),
            new StringCondition("userId",userId, StringCondition.Handler.ALL_LIKE)
        );
        return JsonMessage.success(costService.findAll(condition));
    }


    /**
     * 查询消费列表
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ApiOperation(value="查询消费分页",notes = "查询消费分页")
    @ApiResponse(code = 200,message = "消费分页结果集")
    public JsonMessage<Page<CostBean>> page(
            @ApiParam(value = "花费描述")
            @RequestParam(value = "remark",required = false) String remark,
            @ApiParam(value = "主键")
            @RequestParam(value = "id",required = false) String id,
            @ApiParam(value = "微信用户id")
            @RequestParam(value = "userId",required = false) String userId,
            @ApiParam(value = "分页参数(页数)",example="1")
            @RequestParam(value = "pageNum",required = false)Integer pageNum,
            @ApiParam(value = "分页参数(页大小)",example="20")
            @RequestParam(value = "pageSize",required = false) Integer pageSize
            ){
        Condition condition= Condition.and(
            new StringCondition("remark",remark, StringCondition.Handler.ALL_LIKE),
            new StringCondition("id",id, StringCondition.Handler.EQUAL),
            new StringCondition("userId",userId, StringCondition.Handler.ALL_LIKE)
        );
        return JsonMessage.success(costService.findAll(condition,PageRequest.of(pageNum-1,pageSize)));

    }

    /**
     * 保存消费
     * @param cost
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ApiOperation(value = "保存消费",notes = "保存消费")
    @ApiResponse(code = 200,message = "保存结果")
    public JsonMessage save(@ApiParam(value = "消费实体")  @RequestBody CostBean cost){
        costService.save(cost);
        return MessageDefine.SUCCESS_SAVE.toJsonMessage(true);
    }


    /**
     * 删除消费
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    @ApiOperation(value = "删除消费",notes = "删除消费")
    @ApiResponse(code = 200,message = "删除结果")
    public JsonMessage delete(@ApiParam(value = "消费id数组") @RequestParam String[] ids){
        costService.deleteById(ids);
        return MessageDefine.SUCCESS_DELETE.toJsonMessage(true);
    }
}
