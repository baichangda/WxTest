package com.bcd.wx.controller;

import com.bcd.wx.service.WxService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
@RequestMapping("/wx")
@SuppressWarnings("unchecked")
public class WxController {

    @Autowired
    WxService wxService;

    @RequestMapping(value = "/handle")
    @ApiOperation(value = "接收微信消息",notes = "接收微信消息")
    @ApiResponse(code = 200,message = "Token",response = String.class)
    public void handle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        wxService.handle(request,response);
    }
}
