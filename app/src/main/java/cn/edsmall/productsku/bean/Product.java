package cn.edsmall.productsku.bean;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import cn.edsmall.productsku.R;
import cn.edsmall.skulibrary.bean.DescriptionEntity;
import cn.edsmall.skulibrary.bean.ImgsJsonEntity;
import cn.edsmall.skulibrary.bean.SkuJsonEntity;
import cn.edsmall.skulibrary.bean.SpecJsonEntity;

public class Product {
    private int proxyStatus;
    private String supplierId;
    private int deliveryTime;
    private ImgsJsonEntity imgsJson;
    private int isXls;
    private List<DescriptionEntity> description;
    private String labelTitle;
    private String title;
    private int type;
    private String templateId;
    private List<SpecJsonEntity> specJson;
    private int saleAuth;
    private String subTitle;
    private String model;
    private int stock;
    private double salePrice;
    private String collectId;
    private int isShowVideo;
    private List<SkuJsonEntity> skuJson;
    private String brandId;
    private String factoryVideo;
    private String shareUrl;
    private String spuId;
    private double productPrice;
    private int collectStatus;
    public static Product get(Context context) {
        String json = context.getString(R.string.product);
        return new Gson().fromJson(json, new TypeToken<Product>() {
        }.getType());
    }
    public int getProxyStatus() {
        return proxyStatus;
    }

    public void setProxyStatus(int proxyStatus) {
        this.proxyStatus = proxyStatus;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public int getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(int deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public ImgsJsonEntity getImgsJson() {
        return imgsJson;
    }

    public void setImgsJson(ImgsJsonEntity imgsJson) {
        this.imgsJson = imgsJson;
    }

    public int getIsXls() {
        return isXls;
    }

    public void setIsXls(int isXls) {
        this.isXls = isXls;
    }

    public List<DescriptionEntity> getDescription() {
        return description;
    }

    public void setDescription(List<DescriptionEntity> description) {
        this.description = description;
    }

    public String getLabelTitle() {
        return labelTitle;
    }

    public void setLabelTitle(String labelTitle) {
        this.labelTitle = labelTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public List<SpecJsonEntity> getSpecJson() {
        return specJson;
    }

    public void setSpecJson(List<SpecJsonEntity> specJson) {
        this.specJson = specJson;
    }

    public int getSaleAuth() {
        return saleAuth;
    }

    public void setSaleAuth(int saleAuth) {
        this.saleAuth = saleAuth;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public double getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(double salePrice) {
        this.salePrice = salePrice;
    }

    public String getCollectId() {
        return collectId;
    }

    public void setCollectId(String collectId) {
        this.collectId = collectId;
    }

    public int getIsShowVideo() {
        return isShowVideo;
    }

    public void setIsShowVideo(int isShowVideo) {
        this.isShowVideo = isShowVideo;
    }

    public List<SkuJsonEntity> getSkuJson() {
        return skuJson;
    }

    public void setSkuJson(List<SkuJsonEntity> skuJson) {
        this.skuJson = skuJson;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    public String getFactoryVideo() {
        return factoryVideo;
    }

    public void setFactoryVideo(String factoryVideo) {
        this.factoryVideo = factoryVideo;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public String getSpuId() {
        return spuId;
    }

    public void setSpuId(String spuId) {
        this.spuId = spuId;
    }

    public double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }

    public int getCollectStatus() {
        return collectStatus;
    }

    public void setCollectStatus(int collectStatus) {
        this.collectStatus = collectStatus;
    }
}
