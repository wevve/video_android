package com.jyt.video.api.entity;

public class WxPayResult {

    /**
     * payment_code : nativePay
     * pay_channel : wxAppPay
     * price : 1
     * buy_type : 2
     * user_id : 1357
     * from_agent_id : 0
     * from_domain :
     * is_app : 1
     * buy_vip_info : {"id":15,"name":"包月会员","days":30,"price":"1.00","permanent":0,"info":"包月套餐，有效时间30天，价格只需要0.03元,精彩内容等着你。"}
     * order_sn : 2019081909424255836
     */

    private String payment_code;
    private String pay_channel;
    private int price;
    private int buy_type;
    private int user_id;
    private int from_agent_id;
    private String from_domain;
    private int is_app;
    private String buy_vip_info;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
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

    public int getIs_app() {
        return is_app;
    }

    public void setIs_app(int is_app) {
        this.is_app = is_app;
    }

    public String getBuy_vip_info() {
        return buy_vip_info;
    }

    public void setBuy_vip_info(String buy_vip_info) {
        this.buy_vip_info = buy_vip_info;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }
}
