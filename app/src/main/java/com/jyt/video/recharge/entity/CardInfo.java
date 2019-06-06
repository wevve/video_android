package com.jyt.video.recharge.entity;

public class CardInfo {

    /**
     * cardType : 金币卡
     * vipTime : 0
     * chargeCorn : 10
     * endTime : 1586112822
     * cardStatus : 未使用
     */

    private String cardType;
    private Long vipTime;
    private String chargeCorn;
    private Long endTime;
    private String cardStatus;

    public String getCardType() {
        return cardType;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    public Long getVipTime() {
        return vipTime;
    }

    public void setVipTime(Long vipTime) {
        this.vipTime = vipTime;
    }

    public String getChargeCorn() {
        return chargeCorn;
    }

    public void setChargeCorn(String chargeCorn) {
        this.chargeCorn = chargeCorn;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public String getCardStatus() {
        return cardStatus;
    }

    public void setCardStatus(String cardStatus) {
        this.cardStatus = cardStatus;
    }
}
