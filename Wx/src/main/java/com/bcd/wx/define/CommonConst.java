package com.bcd.wx.define;

import com.bcd.base.map.ExpireConcurrentMap;
import com.bcd.wx.data.Mode;
import com.bcd.wx.handler.ModeHandler;

import java.util.*;

public class CommonConst {
    public final static Map<String,String> USER_ID_TO_NAME=new HashMap<>();
    static {
        USER_ID_TO_NAME.put("ovRhL1SkGRHBoHiJ0weuslK9te7c","木风");
        USER_ID_TO_NAME.put("ovRhL1cYLB5YXZcRx-cE-wz69uTg","依");
        USER_ID_TO_NAME.put("ovRhL1SfS8dao1VVmg_NqBVF9jD0","霞");
    }


    public final static List<Mode> MODE_LIST=new ArrayList<>();
    static {
        MODE_LIST.add(Mode.ACCOUNT_MODE);
    }

    public final static ExpireConcurrentMap<String,Mode> USER_ID_TO_MODE=new ExpireConcurrentMap<>();
    public final static long MODE_EXPIRE_MILLS=2*60*1000L;

    public final static Map<Mode,ModeHandler> MODE_TO_HANDLER=new HashMap<>();
}
