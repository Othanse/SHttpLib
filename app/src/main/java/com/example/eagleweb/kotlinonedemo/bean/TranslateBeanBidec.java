package com.example.eagleweb.kotlinonedemo.bean;

public class TranslateBeanBidec {
    private TranslateBeanBidecParts[] parts;
    private String                    word_name;

    public TranslateBeanBidecParts[] getParts() {
        return this.parts;
    }

    public void setParts(TranslateBeanBidecParts[] parts) {
        this.parts = parts;
    }

    public String getWord_name() {
        return this.word_name;
    }

    public void setWord_name(String word_name) {
        this.word_name = word_name;
    }
}
