package com.example.agregator.entities;

public class ProductRetail {
    public String name;
    public String price;
    public String ref;
    public String otherInf;
    public String shop;
    public String img;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }
    public void setOtherInf(String otherInf){
        if(this.otherInf == null)
            this.otherInf = otherInf;
        else
            this.otherInf += otherInf;
    }
    public String getOtherInf(){
        return otherInf;
    }
}
