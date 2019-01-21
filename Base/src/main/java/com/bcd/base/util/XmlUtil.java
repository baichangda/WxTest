package com.bcd.base.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class XmlUtil {
    public final static XmlMapper GLOBAL_XML_MAPPER= withConfig(new XmlMapper());

    public static XmlMapper withConfig(XmlMapper xmlMapper){
        //1、应用json过滤器配置
        JsonUtil.withConfig(xmlMapper);
        //2、配置时间转换和解析器
        SimpleModule simpleModule=new SimpleModule();
        simpleModule.addSerializer(Date.class, new JsonSerializer<Date>() {
            @Override
            public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                if(value!=null){
                    gen.writeNumber(value.toInstant().getEpochSecond());
                }
            }
        });
        simpleModule.addDeserializer(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
                String text=p.getText();
                if(text==null||text.equals("")){
                    return null;
                }else{
                    return new Date(Long.parseLong(text)*1000);
                }
            }
        });
        xmlMapper.registerModule(simpleModule);
        return xmlMapper;
    }

    public static void main(String [] args) throws JsonProcessingException {
        Map<String,Object> dataMap=new HashMap<>();
        dataMap.put("A","a");
        dataMap.put("B","b");
        dataMap.put("C",new HashMap<String,String>(){{
            put("D","d");
        }});
        String res= XmlUtil.GLOBAL_XML_MAPPER.writeValueAsString(dataMap);

        System.out.println("\nRes: "+res);
    }
}
