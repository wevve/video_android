package com.jyt.video.setting.entity;

import com.contrarywind.interfaces.IPickerViewData;

public class Bank implements IPickerViewData {

    /**
     * title : ABC
     * name : 农业银行
     * img : http://v.msvodx.com/static/bankimgfpng/ABC.png
     */

    private String title;
    private String name;
    private String img;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String getPickerViewText() {
        return getName();
    }
}
