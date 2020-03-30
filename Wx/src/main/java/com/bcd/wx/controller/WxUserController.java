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
import com.bcd.wx.bean.WxUserBean;
import com.bcd.wx.service.WxUserService;

@SuppressWarnings(value = "unchecked")
@RestController
@RequestMapping("/api/wx/wxUser")
public class WxUserController extends BaseController {

    @Autowired
    private WxUserService wxUserService;


    /**
     * 查询微信用户信息列表
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value="查询微信用户信息列表",notes = "查询微信用户信息列表")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "openId", value = "微信用户openId", dataType = "String"),
        @ApiImplicitParam(name = "name", value = "微信用户名称", dataType = "String"),
        @ApiImplicitParam(name = "id", value = "主键(唯一标识符,自动生成)(不需要赋值)", dataType = "String")
    })
    @ApiResponse(code = 200,message = "微信用户信息列表")
    public JsonMessage<List<WxUserBean>> list(
        @RequestParam(value = "openId", required = false) String openId,
        @RequestParam(value = "name", required = false) String name,
        @RequestParam(value = "id", required = false) String id
    ){
        Condition condition= Condition.and(
            new StringCondition("openId",openId, StringCondition.Handler.ALL_LIKE),
            new StringCondition("name",name, StringCondition.Handler.ALL_LIKE),
            new StringCondition("id",id, StringCondition.Handler.EQUAL)
        );
        return JsonMessage.success(wxUserService.findAll(condition));
    }


    /**
     * 查询微信用户信息列表
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ApiOperation(value="查询微信用户信息分页",notes = "查询微信用户信息分页")
    @ApiImplicitParams({
        @ApiImplicitParam(name = "openId", value = "微信用户openId", dataType = "String"),
        @ApiImplicitParam(name = "name", value = "微信用户名称", dataType = "String"),
        @ApiImplicitParam(name = "id", value = "主键(唯一标识符,自动生成)(不需要赋值)", dataType = "String"),
        @ApiImplicitParam(name = "pageNum", value = "分页参数(页数)", dataType = "String"),
        @ApiImplicitParam(name = "pageSize", value = "分页参数(页大小)", dataType = "String")
    })
    @ApiResponse(code = 200,message = "微信用户信息分页结果集")
    public JsonMessage<Page<WxUserBean>> page(
        @RequestParam(value = "openId", required = false) String openId,
        @RequestParam(value = "name", required = false) String name,
        @RequestParam(value = "id", required = false) String id,
        @RequestParam(value = "pageNum",required = false)Integer pageNum,
        @RequestParam(value = "pageSize",required = false) Integer pageSize
    ){
        Condition condition= Condition.and(
            new StringCondition("openId",openId, StringCondition.Handler.ALL_LIKE),
            new StringCondition("name",name, StringCondition.Handler.ALL_LIKE),
            new StringCondition("id",id, StringCondition.Handler.EQUAL)
        );
        return JsonMessage.success(wxUserService.findAll(condition,PageRequest.of(pageNum-1,pageSize)));
    }

    /**
     * 保存微信用户信息
     * @param wxUser
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ApiOperation(value = "保存微信用户信息",notes = "保存微信用户信息")
    @ApiResponse(code = 200,message = "保存结果")
    public JsonMessage save(@ApiParam(value = "微信用户信息实体")  @RequestBody WxUserBean wxUser){
        wxUserService.save(wxUser);
        return MessageDefine.SUCCESS_SAVE.toJsonMessage(true);
    }


    /**
     * 删除微信用户信息
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    @ApiOperation(value = "删除微信用户信息",notes = "删除微信用户信息")
    @ApiResponse(code = 200,message = "删除结果")
    public JsonMessage delete(@ApiParam(value = "微信用户信息id数组") @RequestParam String[] ids){
        wxUserService.deleteById(ids);
        return MessageDefine.SUCCESS_DELETE.toJsonMessage(true);
    }
}
