package com.sale.refaq.snow_energy.pojo;

public class OrderItemItemModel {
    private String key;
    private String companyId;
    private String userId;
    private String itemId;
    private String logo;
    private String itemName;
    private String price;
    private String quantity;
    private String companyName;
    private String ordered;

    public OrderItemItemModel() {
    }

    public OrderItemItemModel(String key, String companyId, String userId, String itemId, String logo, String itemName, String price, String quantity, String companyName, String ordered) {
        this.key = key;
        this.companyId = companyId;
        this.userId = userId;
        this.itemId = itemId;
        this.logo = logo;
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
        this.companyName = companyName;
        this.ordered = ordered;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getOrdered() {
        return ordered;
    }

    public void setOrdered(String ordered) {
        this.ordered = ordered;
    }
}
