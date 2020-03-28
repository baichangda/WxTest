package com.bcd.base.config.shiro.data;

public enum NotePermission {
    user_search("user:search", "用户查询"),
    user_edit("user:edit", "用户维护"),
    user_runAs("user:runAs", "用户身份授权"),

    sysTask_search("sysTask:search", "系统任务查询"),
    sysTask_stop("sysTask:destroy", "系统任务停止"),;

    private String code;
    private String note;

    NotePermission(String code, String note) {
        this.code = code;
        this.note = note;
    }

    public String getCode() {
        return code;
    }

    public String getNote() {
        return note;
    }
}
