package com.sale.refaq.snow_energy.pojo;

public class CompanyModel {
    private String name;
    private String logo;
    private String id;

    public CompanyModel() {
    }

    public CompanyModel(String name, String logo, String id) {
        this.name = name;
        this.logo = logo;
        this.id = id;
    }

    public CompanyModel(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
