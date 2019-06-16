package com.jyt.video.recharge.entity;

public class PayWay {

    /**
     * payName : 支付宝支付
     * payCode : yunyiPay|aliPay
     * payIcon : http://v.msvodx.com//tpl/default/static/images/Alipay.png
     */

    private String payName;
    private String payCode;
    private String payIcon;

    public String getPayName() {
        return payName;
    }

    public void setPayName(String payName) {
        this.payName = payName;
    }

    public String getPayCode() {
        return payCode;
    }

    public void setPayCode(String payCode) {
        this.payCode = payCode;
    }

    public String getPayIcon() {
        return payIcon;
    }

    public void setPayIcon(String payIcon) {
        this.payIcon = payIcon;
    }
}
