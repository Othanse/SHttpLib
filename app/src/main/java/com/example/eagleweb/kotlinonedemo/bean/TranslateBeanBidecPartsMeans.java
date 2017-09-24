package com.example.eagleweb.kotlinonedemo.bean;

public class TranslateBeanBidecPartsMeans {
    private String                                  word_mean;
    private TranslateBeanBidecPartsMeansSentences[] sentences;
    private String                                  part_id;
    private String                                  mean_id;

    public String getWord_mean() {
        return this.word_mean;
    }

    public void setWord_mean(String word_mean) {
        this.word_mean = word_mean;
    }

    public TranslateBeanBidecPartsMeansSentences[] getSentences() {
        return this.sentences;
    }

    public void setSentences(TranslateBeanBidecPartsMeansSentences[] sentences) {
        this.sentences = sentences;
    }

    public String getPart_id() {
        return this.part_id;
    }

    public void setPart_id(String part_id) {
        this.part_id = part_id;
    }

    public String getMean_id() {
        return this.mean_id;
    }

    public void setMean_id(String mean_id) {
        this.mean_id = mean_id;
    }
}
