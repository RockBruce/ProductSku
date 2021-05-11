package cn.edsmall.skulibrary.bean;

import java.util.List;

public class SkuJsonEntity {
    private String mainImg;
    private List<SkuAttribute> skuSpecs;
    private double salePrice;
    private int isSale;
    private int saleable;
    private String model;
    private String title;
    private int stock;
    private String skuId;
    private double productPrice;
    private int moq;

    public String getMainImg() {
        return mainImg;
    }

    public void setMainImg(String mainImg) {
        this.mainImg = mainImg;
    }

    public List<SkuAttribute> getSkuSpecs() {
        return skuSpecs;
    }

    public void setSkuSpecs(List<SkuAttribute> skuSpecs) {
        this.skuSpecs = skuSpecs;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public int getIsSale() {
        return isSale;
    }

    public void setIsSale(int isSale) {
        this.isSale = isSale;
    }

    public int getSaleable() {
        return saleable;
    }

    public void setSaleable(int saleable) {
        this.saleable = saleable;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getMoq() {
        return moq;
    }

    public void setMoq(int moq) {
        this.moq = moq;
    }
}
