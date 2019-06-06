package com.jyt.video.video.entity;

public class Gift {

    /**
     * id : 12
     * name : 狗粮
     * images : http://v.msvodx.com/XResource/20190604/z8pFhXXaiCdfPnzzFMEwJcYCcaKTjEAW.jpg
     * price : 1
     * info : 狗粮
     */

    private Long id;
    private String name;
    private String images;
    private String price;
    private String info;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
