package com.bcd.config.exception.handler.impl;

import com.bcd.base.message.JsonMessage;
import com.bcd.base.util.ExceptionUtil;
import com.bcd.config.exception.handler.ExceptionResponseHandler;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("unchecked")
public class DefaultExceptionResponseHandler implements ExceptionResponseHandler {
    private HttpMessageConverter converter;

    public DefaultExceptionResponseHandler(HttpMessageConverter converter) {
        this.converter = converter;
    }

    @Override
    public void handle(HttpServletResponse response, Throwable throwable) throws IOException {
        Throwable realException= ExceptionUtil.parseRealException(throwable);
        JsonMessage result= ExceptionUtil.toJsonMessage(realException);
        handle(response, result);
    }


    @Override
    public void handle(HttpServletResponse response, Object result) throws IOException {
        ServletServerHttpResponse servletServerHttpResponse=new ServletServerHttpResponse(response);
        converter.write(result,
                MediaType.APPLICATION_JSON,
                servletServerHttpResponse);
    }
}
