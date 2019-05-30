package com.jyt.video.deal.entity;

public class Record {

    /**
     * orderID : 2019051718160366219
     * recordId : 2019051718160366219
     * date : 1558088163
     * title : 充值明细
     * content : 金币充值 1元-未支付
     * remark : 金币充值 1元-未支付
     * status : 未支付
     */

    private String orderID;
    private String recordId;
    private long date;
    private String title;
    private String content;
    private String remark;
    private String status;

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
