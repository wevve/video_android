package com.jyt.video.setting.entity;

import org.jetbrains.annotations.NotNull;

public class BankCardAccount implements WithdrawAccount{
    /**
     * carId : 72
     * icon :
     * bankName : 建设n
     * cardNum : 2621
     */

    private long carId;
    private String icon;
    private String bankName;
    private String cardNum;

    private String cardUser;

    public String getCardUser() {
        return cardUser;
    }

    public void setCardUser(String cardUser) {
        this.cardUser = cardUser;
    }

    public long getCarId() {
        return carId;
    }

    public void setCarId(long carId) {
        this.carId = carId;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return bankName+"("+cardNum+")";
    }
}
