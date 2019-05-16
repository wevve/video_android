package com.jyt.video.event;

public class RefreshEvent {

    public enum RefreshType{
        LOGIN
    }

    RefreshType type;
    Object data;

    public RefreshEvent() {
    }

    public RefreshEvent(RefreshType type) {
        this.type = type;
    }

    public RefreshEvent(RefreshType type, Object data) {
        this.type = type;
        this.data = data;
    }

    public RefreshType getType() {
        return type;
    }

    public void setType(RefreshType type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
