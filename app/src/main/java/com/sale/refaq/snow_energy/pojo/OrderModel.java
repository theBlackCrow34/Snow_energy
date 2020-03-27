package com.sale.refaq.snow_energy.pojo;

public class OrderModel {

    private String area;
    private String block;
    private String name ;
    private String phone ;
    private String city ;
    private String deliveryDate ;
    private String deliveryTime ;
    private String floor ;
    private String location ;
    private long orderDate;
    private String orderId ;
    private long orderNumber ;
    private long deliveryTax ;
    private String payedWay ;
    private String progress ;
    private String status ;
    private String street ;
    private String totalPrice ;
    private String userId ;
    private String orderComplete ;

    public OrderModel() {
    }

    public OrderModel(String area, String block, String name, String phone, String city, String deliveryDate, String deliveryTime, long deliveryTax, String floor, String location, long orderDate, String orderId, long orderNumber, String payedWay, String progress, String status, String street, String totalPrice, String userId, String orderComplete) {
        this.area = area;
        this.block = block;
        this.name = name;
        this.phone = phone;
        this.city = city;
        this.deliveryDate = deliveryDate;
        this.deliveryTime = deliveryTime;
        this.deliveryTax = deliveryTax;
        this.floor = floor;
        this.location = location;
        this.orderDate = orderDate;
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.payedWay = payedWay;
        this.progress = progress;
        this.status = status;
        this.street = street;
        this.totalPrice = totalPrice;
        this.userId = userId;
        this.orderComplete = orderComplete;
    }

    public String getOrderComplete() {
        return orderComplete;
    }

    public void setOrderComplete(String orderComplete) {
        this.orderComplete = orderComplete;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    public long getDeliveryTax() {
        return deliveryTax;
    }

    public void setDeliveryTax(long deliveryTax) {
        this.deliveryTax = deliveryTax;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public long getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(long orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getPayedWay() {
        return payedWay;
    }

    public void setPayedWay(String payedWay) {
        this.payedWay = payedWay;
    }

    public String getProgress() {
        return progress;
    }

    public void setProgress(String progress) {
        this.progress = progress;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
