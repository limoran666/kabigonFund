package com.kabigon.crowd.entity.vo;

public class DetailReturnVO {
    //回报信息主键
    private Integer returnId;
    private Integer supportMoney;

    //单笔限购，取值为O时限额，取值为1时为具体限额
    private Integer signalPurchase;
    //限额具体数量
    private Integer purchase;

    private Integer supporterCount;

    //运费
    private Integer freight;

    //众筹成功后多少天发货
    private Integer returenDate;

    // 回报内容
    private String content;

    public DetailReturnVO() {
    }

    public DetailReturnVO(Integer returnId, Integer supportMoney, Integer signalPurchase, Integer supporterCount, Integer freight, Integer returenDate, String content) {
        this.returnId = returnId;
        this.supportMoney = supportMoney;
        this.signalPurchase = signalPurchase;
        this.supporterCount = supporterCount;
        this.freight = freight;
        this.returenDate = returenDate;
        this.content = content;
    }

    public Integer getReturnId() {
        return returnId;
    }

    public void setReturnId(Integer returnId) {
        this.returnId = returnId;
    }

    public Integer getSupportMoney() {
        return supportMoney;
    }

    public void setSupportMoney(Integer supportMoney) {
        this.supportMoney = supportMoney;
    }

    public Integer getSignalPurchase() {
        return signalPurchase;
    }

    public void setSignalPurchase(Integer signalPurchase) {
        this.signalPurchase = signalPurchase;
    }

    public Integer getSupporterCount() {
        return supporterCount;
    }

    public void setSupporterCount(Integer supporterCount) {
        this.supporterCount = supporterCount;
    }

    public Integer getFreight() {
        return freight;
    }

    public void setFreight(Integer freight) {
        this.freight = freight;
    }

    public Integer getReturenDate() {
        return returenDate;
    }

    public void setReturenDate(Integer returenDate) {
        this.returenDate = returenDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
