package com.bcd.wx.handler;

import com.bcd.wx.data.Message;
import com.bcd.wx.data.Mode;
import com.bcd.wx.data.MsgType;
import com.bcd.wx.data.request.RequestTextMessage;
import com.bcd.wx.data.response.ResponseTextMessage;
import com.bcd.wx.define.CommonConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



@Component
public class TextHandler extends Handler<RequestTextMessage> {

    private final static Logger logger= LoggerFactory.getLogger(TextHandler.class);

    @Value("${wx.name}")
    String wxName;

    public TextHandler() {
        super(MsgType.text,RequestTextMessage.class);
    }

    @Override
    public Message handle(RequestTextMessage message) throws Exception {
        String content=message.getContent();
        String fromUserName=message.getFromUserName();
        logger.debug(message.toString());
        //1、先检测当前用户是否处于模式下
        Mode mode= CommonConst.USER_ID_TO_MODE.get(fromUserName);
        if(mode==null){
            //2、如果不在模式下,则检测当前用户输入的内容是否对应模式编号
            mode=getMode(content);
            if(mode==null){
                //3、如果输入的不是编号,则检测输入的是否是模式名称部分字符
                String tipMsg=checkInMode(content);
                if(tipMsg==null){
                    //4、如果没有提示信息,则视为常规信息
                    String name=CommonConst.USER_ID_TO_NAME.get(message.getFromUserName());
                    return new ResponseTextMessage(wxName,message.getFromUserName(),name==null?message.getContent():(name+","+message.getContent()));
                }else{
                    //5、有则返回提示
                    StringBuilder tip=new StringBuilder();
                    tip.append("输入对应的模式编号进入:\n");
                    tip.append(tipMsg);
                    return new ResponseTextMessage(wxName,fromUserName,tip.toString());

                }
            }else{
                //6、如果输入的是模式编号,则进入对应模式,同时返回提示信息
                CommonConst.USER_ID_TO_MODE.put(fromUserName,mode,CommonConst.MODE_EXPIRE_MILLS);
                return new ResponseTextMessage(wxName,fromUserName,"进入["+mode.getName()+"]模式");
            }
        }else{
            //7、如果在模式下面,调用对应模式逻辑
            ModeHandler modeHandler= CommonConst.MODE_TO_HANDLER.get(mode);
            if(modeHandler==null){
                String msg=modeHandler.handle(fromUserName,content);
                return new ResponseTextMessage(wxName,fromUserName,msg);
            }else{
                return new ResponseTextMessage(wxName,fromUserName,"错误");
            }
        }
    }

    private Mode getMode(String content){
        for (Mode mode : CommonConst.MODE_LIST) {
            if(mode.getId().equals(content)){
                return mode;
            }
        }
        return null;
    }

    private String checkInMode(String content){
        String msg=CommonConst.MODE_LIST.stream().map(e->e.getName()).filter(e-> e.contains(content)).reduce("",(e1,e2)->e1+" "+e2,(e1,e2)->e1+"\n"+e2);
        if("".equals(msg)){
            return null;
        }else{
            return msg;
        }
    }

}
