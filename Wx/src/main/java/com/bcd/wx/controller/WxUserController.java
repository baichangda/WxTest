package com.bcd.wx.controller;

import com.bcd.base.condition.Condition;
import com.bcd.base.condition.impl.*;
import com.bcd.base.controller.BaseController;
import com.bcd.base.define.MessageDefine;
import com.bcd.base.message.JsonMessage;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.List;
import org.springframework.validation.annotation.Validated;
import com.bcd.wx.bean.WxUserBean;
import com.bcd.wx.service.WxUserService;

@SuppressWarnings(value = "unchecked")
@Api(tags = "微信用户/WxUser")
@RestController
@RequestMapping("/api/wx/wxUser")
public class WxUserController extends BaseController {

    @Autowired
    private WxUserService wxUserService;



    /**
     * 查询微信用户列表
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value="查询微信用户列表",notes = "查询微信用户列表")
    @ApiResponse(code = 200,message = "微信用户列表")
    public JsonMessage<List<WxUserBean>> list(
        @ApiParam(value = "微信用户openId") @RequestParam(required = false) Date openId,
        @ApiParam(value = "微信用户名称") @RequestParam(required = false) Date name,
        @ApiParam(value = "主键(唯一标识符,自动生成)(不需要赋值)") @RequestParam(required = false) Date id
    ){
        Condition condition= Condition.and(
           new StringCondition("openId",openId),
           new StringCondition("name",name),
           new StringCondition("id",id)
        );
        return JsonMessage.success(wxUserService.findAll(condition));
    }

    /**
     * 查询微信用户分页
     * @return
     */
    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ApiOperation(value="查询微信用户分页",notes = "查询微信用户分页")
    @ApiResponse(code = 200,message = "微信用户分页结果集")
    public JsonMessage<Page<WxUserBean>> page(
        @ApiParam(value = "微信用户openId") @RequestParam(required = false) Date openId,
        @ApiParam(value = "微信用户名称") @RequestParam(required = false) Date name,
        @ApiParam(value = "主键(唯一标识符,自动生成)(不需要赋值)") @RequestParam(required = false) Date id,
        @ApiParam(value = "分页参数(页数)",defaultValue = "1")  @RequestParam(required = false,defaultValue = "1")Integer pageNum,
        @ApiParam(value = "分页参数(页大小)",defaultValue = "20") @RequestParam(required = false,defaultValue = "20") Integer pageSize
    ){
        Condition condition= Condition.and(
           new StringCondition("openId",openId),
           new StringCondition("name",name),
           new StringCondition("id",id)
        );
        return JsonMessage.success(wxUserService.findAll(condition,PageRequest.of(pageNum-1,pageSize)));
    }

    /**
     * 保存微信用户
     * @param wxUser
     * @return
     */
    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ApiOperation(value = "保存微信用户",notes = "保存微信用户")
    @ApiResponse(code = 200,message = "保存结果")
    public JsonMessage save(@ApiParam(value = "微信用户实体")  @RequestBody WxUserBean wxUser){
        wxUserService.save(wxUser);
        return MessageDefine.SUCCESS_SAVE.toJsonMessage(true);
    }


    /**
     * 删除微信用户
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.DELETE)
    @ApiOperation(value = "删除微信用户",notes = "删除微信用户")
    @ApiResponse(code = 200,message = "删除结果")
    public JsonMessage delete(@ApiParam(value = "微信用户id数组") @RequestParam String[] ids){
        wxUserService.deleteById(ids);
        return MessageDefine.SUCCESS_DELETE.toJsonMessage(true);
    }

}
