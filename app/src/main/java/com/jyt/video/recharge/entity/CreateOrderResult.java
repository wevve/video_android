package com.jyt.video.recharge.entity;

public class CreateOrderResult {

    /**
     * payment_code : yunyiPay
     * pay_channel : aliPay
     * price : 0.9
     * buy_type : 1
     * user_id : 650
     * from_agent_id : 0
     * from_domain :
     * buy_glod_num : 0
     * order_sn : 2019051717595757573
     */

    private String payment_code;
    private String pay_channel;
    private double price;
    private int buy_type;
    private int user_id;
    private int from_agent_id;
    private String from_domain;
    private int buy_glod_num;
    private String order_sn;

    public String getPayment_code() {
        return payment_code;
    }

    public void setPayment_code(String payment_code) {
        this.payment_code = payment_code;
    }

    public String getPay_channel() {
        return pay_channel;
    }

    public void setPay_channel(String pay_channel) {
        this.pay_channel = pay_channel;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getBuy_type() {
        return buy_type;
    }

    public void setBuy_type(int buy_type) {
        this.buy_type = buy_type;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getFrom_agent_id() {
        return from_agent_id;
    }

    public void setFrom_agent_id(int from_agent_id) {
        this.from_agent_id = from_agent_id;
    }

    public String getFrom_domain() {
        return from_domain;
    }

    public void setFrom_domain(String from_domain) {
        this.from_domain = from_domain;
    }

    public int getBuy_glod_num() {
        return buy_glod_num;
    }

    public void setBuy_glod_num(int buy_glod_num) {
        this.buy_glod_num = buy_glod_num;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }
}
