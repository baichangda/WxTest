package com.bcd.wx.data;

public enum Mode {

    ACCOUNT_MODE("1","记账");

    private String id;
    private String name;



    Mode(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
