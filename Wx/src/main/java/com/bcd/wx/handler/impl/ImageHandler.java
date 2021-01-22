package com.bcd.wx.handler.impl;

import com.bcd.base.exception.BaseRuntimeException;
import com.bcd.base.util.SpringUtil;
import com.bcd.wx.bean.WxUserBean;
import com.bcd.wx.handler.AbstractWxMpMessageHandler;
import com.bcd.wx.service.WxUserService;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Component
public class ImageHandler extends AbstractWxMpMessageHandler {
    Logger logger= LoggerFactory.getLogger(ImageHandler.class);

    public ImageHandler() {
        super(WxConsts.XmlMsgType.IMAGE);
    }

    @Autowired
    WxUserService wxUserService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {
        logger.info("receive[{}]",wxMessage.toString());
        WxUserBean wxUserBean= wxUserService.findByOpenId(wxMessage.getFromUser());
        try (InputStream is = SpringUtil.getResource("image/test1.png").getInputStream()){
            WxMediaUploadResult wxMediaUploadResult= wxMpService.getMaterialService().mediaUpload(WxConsts.MediaFileType.IMAGE,"png",is);
            String mediaId=wxMediaUploadResult.getMediaId();
            logger.info("upload image succeed[{}]",mediaId);
            return WxMpXmlOutMessage.IMAGE().fromUser(wxMessage.getToUser()).toUser(wxMessage.getFromUser())
                    .mediaId(mediaId).build();
        } catch (IOException e) {
            throw BaseRuntimeException.getException(e);
        }
    }
}
