package com.bcd.wx.handler;

import com.bcd.wx.data.Mode;
import com.bcd.wx.define.CommonConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class ModeHandler {
    protected Logger logger= LoggerFactory.getLogger(this.getClass());

    protected Mode mode;

    public ModeHandler(Mode mode) {
        this.mode = mode;
        CommonConst.MODE_TO_HANDLER.put(mode,this);
    }

    /**
     * 模式处理器
     * @param userId 微信用户id
     * @param content 用户输入的内容
     * @return 返回的结果
     */
    public abstract String handle(String userId,String content);
}
