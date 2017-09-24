package com.example.eagleweb.kotlinonedemo.bean;

public class TranslateBeanSentence {
    private String tts_mp3;
    private String tts_size;
    private String Network_en;
    private int    source_type;
    private int    source_id;
    private String Network_id;
    private String Network_cn;
    private String source_title;

    public String getTts_mp3() {
        return this.tts_mp3;
    }

    public void setTts_mp3(String tts_mp3) {
        this.tts_mp3 = tts_mp3;
    }

    public String getTts_size() {
        return this.tts_size;
    }

    public void setTts_size(String tts_size) {
        this.tts_size = tts_size;
    }

    public String getNetwork_en() {
        return this.Network_en;
    }

    public void setNetwork_en(String Network_en) {
        this.Network_en = Network_en;
    }

    public int getSource_type() {
        return this.source_type;
    }

    public void setSource_type(int source_type) {
        this.source_type = source_type;
    }

    public int getSource_id() {
        return this.source_id;
    }

    public void setSource_id(int source_id) {
        this.source_id = source_id;
    }

    public String getNetwork_id() {
        return this.Network_id;
    }

    public void setNetwork_id(String Network_id) {
        this.Network_id = Network_id;
    }

    public String getNetwork_cn() {
        return this.Network_cn;
    }

    public void setNetwork_cn(String Network_cn) {
        this.Network_cn = Network_cn;
    }

    public String getSource_title() {
        return this.source_title;
    }

    public void setSource_title(String source_title) {
        this.source_title = source_title;
    }
}
