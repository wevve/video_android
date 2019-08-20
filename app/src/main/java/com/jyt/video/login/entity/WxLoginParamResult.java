package com.jyt.video.login.entity;

import java.util.List;

public class WxLoginParamResult {

    /**
     * status : 1
     * list : [{"login_code":"wechat","login_name":"微信登录","config":"[{\"name\":\"Appid\",\"type\":\"text\",\"value\":\"wx97a997257e35859e\",\"desc\":\"Appid\"},{\"name\":\"AppSecret\",\"type\":\"text\",\"value\":\"770c103e7f847e3f29209c3c673e7029\",\"desc\":\"AppSecret\"}]"}]
     */

    private int status;
    private List<ListBean> list;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * login_code : wechat
         * login_name : 微信登录
         * config : [{"name":"Appid","type":"text","value":"wx97a997257e35859e","desc":"Appid"},{"name":"AppSecret","type":"text","value":"770c103e7f847e3f29209c3c673e7029","desc":"AppSecret"}]
         */

        private String login_code;
        private String login_name;
        private String config;

        public String getLogin_code() {
            return login_code;
        }

        public void setLogin_code(String login_code) {
            this.login_code = login_code;
        }

        public String getLogin_name() {
            return login_name;
        }

        public void setLogin_name(String login_name) {
            this.login_name = login_name;
        }

        public String getConfig() {
            return config;
        }

        public void setConfig(String config) {
            this.config = config;
        }
    }
}
