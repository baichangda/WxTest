package com.bcd.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/wx")
@SuppressWarnings("unchecked")
public class WxController {

    @Value("${wx.token}")
    String wxToken;

    private final static Logger logger= LoggerFactory.getLogger(WxController.class);


    @RequestMapping(value = "/handle",method = RequestMethod.GET)
    @ApiOperation(value = "token验证",notes = "token验证")
    @ApiResponse(code = 200,message = "Token",response = String.class)
    public String token(
            @RequestParam(required = false) String signature,
            @RequestParam(required = false) String timestamp,
            @RequestParam(required = false) String nonce,
            @RequestParam(required = false) String echostr){
        logger.info("\nsignature: "+signature+"\ntimestamp: "+timestamp+"\nnonce: "+nonce+"\nechostr: "+echostr+"\n");
        List<String> list=new ArrayList<>();
        list.add(nonce);
        list.add(timestamp);
        list.add(wxToken);
        Collections.sort(list);
        String res= DigestUtils.sha1Hex(list.stream().reduce((e1, e2)->e1+e2).orElse(""));
        logger.info("\nres: "+res);
        if(res.equals(signature)){
            return echostr;
        }else{
            return null;
        }
    }

    @RequestMapping(value = "/handle",method = RequestMethod.POST)
    @ApiOperation(value = "处理微信请求",notes = "处理微信请求")
    @ApiResponse(code = 200,message = "处理结果",response = String.class)
    public String handle(@RequestBody String data){
       logger.info("\ndata: "+data);
       return "success";
    }
}
