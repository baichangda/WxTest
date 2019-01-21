package com.bcd.wx.data;

import com.fasterxml.jackson.databind.JsonNode;

public interface JsonNodeDataSupport {
    void setData(JsonNode data);
    JsonNode getData();
}
