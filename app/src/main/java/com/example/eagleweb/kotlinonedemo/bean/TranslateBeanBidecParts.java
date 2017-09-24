package com.example.eagleweb.kotlinonedemo.bean;

public class TranslateBeanBidecParts {
    private TranslateBeanBidecPartsMeans[] means;
    private String                         word_id;
    private String                         part_id;
    private String                         part_name;

    public TranslateBeanBidecPartsMeans[] getMeans() {
        return this.means;
    }

    public void setMeans(TranslateBeanBidecPartsMeans[] means) {
        this.means = means;
    }

    public String getWord_id() {
        return this.word_id;
    }

    public void setWord_id(String word_id) {
        this.word_id = word_id;
    }

    public String getPart_id() {
        return this.part_id;
    }

    public void setPart_id(String part_id) {
        this.part_id = part_id;
    }

    public String getPart_name() {
        return this.part_name;
    }

    public void setPart_name(String part_name) {
        this.part_name = part_name;
    }
}
