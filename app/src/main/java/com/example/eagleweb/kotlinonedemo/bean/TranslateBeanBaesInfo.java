package com.example.eagleweb.kotlinonedemo.bean;

public class TranslateBeanBaesInfo {
    private int                            translate_type;
    private String                         is_CRI;
    private TranslateBeanBaesInfoExchange  exchange;
    private String                         word_name;
    private TranslateBeanBaesInfoSymbols[] symbols;

    public int getTranslate_type() {
        return this.translate_type;
    }

    public void setTranslate_type(int translate_type) {
        this.translate_type = translate_type;
    }

    public String getIs_CRI() {
        return this.is_CRI;
    }

    public void setIs_CRI(String is_CRI) {
        this.is_CRI = is_CRI;
    }

    public TranslateBeanBaesInfoExchange getExchange() {
        return this.exchange;
    }

    public void setExchange(TranslateBeanBaesInfoExchange exchange) {
        this.exchange = exchange;
    }

    public String getWord_name() {
        return this.word_name;
    }

    public void setWord_name(String word_name) {
        this.word_name = word_name;
    }

    public TranslateBeanBaesInfoSymbols[] getSymbols() {
        return this.symbols;
    }

    public void setSymbols(TranslateBeanBaesInfoSymbols[] symbols) {
        this.symbols = symbols;
    }
}
