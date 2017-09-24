package com.example.eagleweb.kotlinonedemo.bean;

public class TranslateBean {
    private TranslateBeanContent content;
    private int                  status;

    public TranslateBeanContent getContent() {
        return this.content;
    }

    public void setContent(TranslateBeanContent content) {
        this.content = content;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
