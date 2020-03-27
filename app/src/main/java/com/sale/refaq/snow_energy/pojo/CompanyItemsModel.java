package com.sale.refaq.snow_energy.pojo;

public class CompanyItemsModel {

    private String exit;
    private String companyId;
    private String ItemId;
    private String logo;
    private String minOrder;
    private String name;
    private String price;
    private String size;

    public CompanyItemsModel() {
    }

    public CompanyItemsModel(String exit, String companyId, String itemId, String logo, String minOrder, String name, String price, String size) {
        this.exit = exit;
        this.companyId = companyId;
        ItemId = itemId;
        this.logo = logo;
        this.minOrder = minOrder;
        this.name = name;
        this.price = price;
        this.size = size;
    }

    public String getExit() {
        return exit;
    }

    public void setExit(String exit) {
        this.exit = exit;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getItemId() {
        return ItemId;
    }

    public void setItemId(String itemId) {
        ItemId = itemId;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getMinOrder() {
        return minOrder;
    }

    public void setMinOrder(String minOrder) {
        this.minOrder = minOrder;
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

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }
}
