package com.bcd.wx.handler;

import com.bcd.wx.data.MsgType;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.StringWriter;
import java.util.Date;

@Component
public class TextHandler extends Handler {

    private final static Logger logger= LoggerFactory.getLogger(TextHandler.class);

    @Value("${wx.name}")
    String wxName;

    public TextHandler() {
        super(MsgType.text);
    }

    @Override
    public String handle(Element root) throws Exception {

        String fromUserName=root.elementText("FromUserName");
        String content=root.elementText("Content");

        Document res= DocumentHelper.createDocument();
        Element resRoot= res.addElement("xml");
        Element e1=resRoot.addElement("ToUserName");
        e1.setText(fromUserName);
        Element e2=resRoot.addElement("FromUserName");
        e2.setText(wxName);
        Element e3=resRoot.addElement("CreateTime");
        e3.setText(new Date().getTime()+"");
        Element e4=resRoot.addElement("MsgType");
        e4.setText(msgType.name());
        Element e5=resRoot.addElement("Content");
        e5.setText(content);

        String resStr;
        try(StringWriter writer=new StringWriter()){
            OutputFormat format = OutputFormat.createCompactFormat();
            XMLWriter output = new XMLWriter(writer, format);
            output.write(res);
            resStr=writer.toString();
        }
        logger.info("\nTextHandler Res: "+resStr);
        return resStr;
    }
}
