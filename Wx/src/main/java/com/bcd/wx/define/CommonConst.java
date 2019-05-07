package com.bcd.wx.define;

import com.bcd.base.map.ExpireConcurrentMap;
import com.bcd.wx.data.Mode;
import com.bcd.wx.handler.ModeHandler;
import com.bcd.wx.handler.TextHandler;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CommonConst {
    public final static Map<String,String> USER_ID_TO_NAME=new HashMap<>();
    static {
        USER_ID_TO_NAME.put("ovRhL1SkGRHBoHiJ0weuslK9te7c","木风");
        USER_ID_TO_NAME.put("ovRhL1cYLB5YXZcRx-cE-wz69uTg","依");
        USER_ID_TO_NAME.put("ovRhL1SfS8dao1VVmg_NqBVF9jD0","霞");
    }


    public final static DateTimeFormatter RESPONSE_DATE_FORMAT=DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.of("Asia/Shanghai"));

    public final static Map<String,TextHandler.ExpireMode> USER_ID_TO_EXPIRE_MODE=new ConcurrentHashMap<>();
    public final static long MODE_EXPIRE_MILLS=2*60*1000L;

    public final static EnumMap<Mode,ModeHandler> MODE_TO_HANDLER=new EnumMap<>(Mode.class);
}
