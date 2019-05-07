package com.bcd.wx.handler;

import com.bcd.wx.data.Mode;
import org.springframework.stereotype.Component;

@Component
public class CostModeHandler extends ModeHandler{
    public CostModeHandler() {
        super(Mode.COST_MODE);
    }

    @Override
    public String handle(String userId, String content) {
        logger.debug(userId+"\n"+content);
        return null;
    }
}
