package com.example.eagleweb.kotlinonedemo.bean;

public class QueryBean {
    private TranslateBeanSentence[]      sentence;
    private int                          errno;
    private TranslateBeanJushi[]         jushi;
    private String[]                     exchanges;
    private String                       errmsg;
    private TranslateBeanTrade_means[]   trade_means;
    private int                          _word_flag;
    private TranslateBeanAuth_sentence[] auth_sentence;
    private TranslateBeanBidec           bidec;
    private TranslateBeanCollins[]       collins;
    private TranslateBeanEe_mean[]       ee_mean;
    private TranslateBeanBaesInfo        baesInfo;
    private TranslateBeanNetmean         netmean;

    public TranslateBeanSentence[] getSentence() {
        return this.sentence;
    }

    public void setSentence(TranslateBeanSentence[] sentence) {
        this.sentence = sentence;
    }

    public int getErrno() {
        return this.errno;
    }

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public TranslateBeanJushi[] getJushi() {
        return this.jushi;
    }

    public void setJushi(TranslateBeanJushi[] jushi) {
        this.jushi = jushi;
    }

    public String[] getExchanges() {
        return this.exchanges;
    }

    public void setExchanges(String[] exchanges) {
        this.exchanges = exchanges;
    }

    public String getErrmsg() {
        return this.errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public TranslateBeanTrade_means[] getTrade_means() {
        return this.trade_means;
    }

    public void setTrade_means(TranslateBeanTrade_means[] trade_means) {
        this.trade_means = trade_means;
    }

    public int get_word_flag() {
        return this._word_flag;
    }

    public void set_word_flag(int _word_flag) {
        this._word_flag = _word_flag;
    }

    public TranslateBeanAuth_sentence[] getAuth_sentence() {
        return this.auth_sentence;
    }

    public void setAuth_sentence(TranslateBeanAuth_sentence[] auth_sentence) {
        this.auth_sentence = auth_sentence;
    }

    public TranslateBeanBidec getBidec() {
        return this.bidec;
    }

    public void setBidec(TranslateBeanBidec bidec) {
        this.bidec = bidec;
    }

    public TranslateBeanCollins[] getCollins() {
        return this.collins;
    }

    public void setCollins(TranslateBeanCollins[] collins) {
        this.collins = collins;
    }

    public TranslateBeanEe_mean[] getEe_mean() {
        return this.ee_mean;
    }

    public void setEe_mean(TranslateBeanEe_mean[] ee_mean) {
        this.ee_mean = ee_mean;
    }

    public TranslateBeanBaesInfo getBaesInfo() {
        return this.baesInfo;
    }

    public void setBaesInfo(TranslateBeanBaesInfo baesInfo) {
        this.baesInfo = baesInfo;
    }

    public TranslateBeanNetmean getNetmean() {
        return this.netmean;
    }

    public void setNetmean(TranslateBeanNetmean netmean) {
        this.netmean = netmean;
    }
}
