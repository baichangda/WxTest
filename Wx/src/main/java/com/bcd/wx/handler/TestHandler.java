package com.bcd.wx.handler;

import com.bcd.base.exception.BaseRuntimeException;
import com.bcd.base.util.SpringUtil;
import com.bcd.wx.bean.WxUserBean;
import com.bcd.wx.service.WxUserService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.material.WxMediaImgUploadResult;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

@Component
public class TestHandler implements WxMpMessageHandler {
    Logger logger= LoggerFactory.getLogger(TestHandler.class);

    @Autowired
    WxUserService wxUserService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        logger.info("receive[{}]",wxMessage.toString());
        WxUserBean wxUserBean= wxUserService.findByOpenId(wxMessage.getFromUser());
        try (InputStream is = SpringUtil.getResource("image/test1.jpg").getInputStream()){
            Path path=Files.createTempFile("test1","jpg");
            Files.copy(is,path);
            WxMediaImgUploadResult wxMediaImgUploadResult= wxMpService.getMaterialService().mediaImgUpload(path.toFile());
            String imageUrl=wxMediaImgUploadResult.getUrl();
            return WxMpXmlOutMessage.IMAGE().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
                    .mediaId(imageUrl).build();
        } catch (IOException e) {
            throw BaseRuntimeException.getException(e);
        }
    }
}
