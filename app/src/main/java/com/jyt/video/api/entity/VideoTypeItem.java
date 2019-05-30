package com.jyt.video.api.entity;

import java.util.List;

public class VideoTypeItem {

    String name;
    List<SubTypeItem> items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<SubTypeItem> getItems() {
        return items;
    }

    public void setItems(List<SubTypeItem> items) {
        this.items = items;
    }

    public class SubTypeItem{
        Long id;
        String name;
        Integer sort;
        Long type;
        Long last_time;
        Integer status;

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getSort() {
            return sort;
        }

        public void setSort(Integer sort) {
            this.sort = sort;
        }

        public Long getType() {
            return type;
        }

        public void setType(Long type) {
            this.type = type;
        }

        public Long getLast_time() {
            return last_time;
        }

        public void setLast_time(Long last_time) {
            this.last_time = last_time;
        }

        public Integer getStatus() {
            return status;
        }

        public void setStatus(Integer status) {
            this.status = status;
        }
    }
}
