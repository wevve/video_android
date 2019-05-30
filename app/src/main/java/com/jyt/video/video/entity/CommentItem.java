package com.jyt.video.video.entity;

public class CommentItem {


    /**
     * id : 488
     * send_user : 3
     * content : 00000000000000
     * last_time : 1521704947
     * username : admin
     * headimgurl : http://v.msvodx.com/static/touxiang/01b499a1c63c49ac!400x400_big.jpg
     * nickname : Msvod
     */

    private Long id;
    private Long send_user;
    private String content;
    private Long last_time;
    private String username;
    private String headimgurl;
    private String nickname;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSend_user() {
        return send_user;
    }

    public void setSend_user(Long send_user) {
        this.send_user = send_user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getLast_time() {
        return last_time;
    }

    public void setLast_time(Long last_time) {
        this.last_time = last_time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
