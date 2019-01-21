package com.bcd.wx.controller;

import com.bcd.wx.service.WxService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/wx")
@SuppressWarnings("unchecked")
public class WxController {

    @Autowired
    WxService wxService;

    @RequestMapping(value = "/handle",method = RequestMethod.GET)
    @ApiOperation(value = "token验证",notes = "token验证")
    @ApiResponse(code = 200,message = "Token",response = String.class)
    public String token(
            @RequestParam(required = false) String signature,
            @RequestParam(required = false) String timestamp,
            @RequestParam(required = false) String nonce,
            @RequestParam(required = false) String echostr){
        return wxService.token(signature,timestamp,nonce,echostr);
    }

    @RequestMapping(value = "/handle",method = RequestMethod.POST)
    @ApiOperation(value = "处理微信请求",notes = "处理微信请求")
    @ApiResponse(code = 200,message = "处理结果",response = String.class)
    public String handle(@RequestBody String data){
       return wxService.handle(data);
    }


    @RequestMapping(value = "/sendAll",method = RequestMethod.POST)
    @ApiOperation(value = "群发消息",notes = "群发消息")
    public void sendAll(@RequestBody String text){
        wxService.sendAll(text);
    }
}
