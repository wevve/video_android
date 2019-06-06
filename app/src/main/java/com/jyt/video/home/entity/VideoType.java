package com.jyt.video.home.entity;

import java.util.ArrayList;
import java.util.List;

public class VideoType {


    public class TypeGroup{
        public String name;
        public List<Type> items;
    }


    public class Type{
        public int id;
        public String name;
        public ArrayList<Type> subItem;
        public int sort;
        public int type;
        public int status;
        public long last_time;

    }

}
