package com.jyt.video.promotion.entity;

import java.util.List;

public class PromotionUserListResult {


    /**
     * total : 3
     * today : 0
     * list : [{"id":3,"pid":650,"name":"gszg5767","date":1558062283},{"id":1,"pid":650,"name":"gszg5769","date":1558062683},{"id":2,"pid":650,"name":"cghdjzsjh","date":1555063283}]
     */

    private int total;
    private int today;
    private List<ListBean> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getToday() {
        return today;
    }

    public void setToday(int today) {
        this.today = today;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * id : 3
         * pid : 650
         * name : gszg5767
         * date : 1558062283
         */

        private int id;
        private int pid;
        private String name;
        private int date;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getPid() {
            return pid;
        }

        public void setPid(int pid) {
            this.pid = pid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getDate() {
            return date;
        }

        public void setDate(int date) {
            this.date = date;
        }
    }
}
