package com.daatstudios.cosmic_glow;


import java.util.Date;

public class MyOrderItemsModel {
    private String productID;
    private String product_image_orders;
    private int rating=-1;
    private String productTitleOrder;
    private String deliveryStatus, cuttedPrice, orderID, productPrice;
    private String productQty;
    private String paymentMethod;
    private String userID;
    private String date;
    private String city,deliveryPrice;



    public MyOrderItemsModel(String productID, String product_image_orders, String productTitleOrder, String deliveryStatus, String cuttedPrice, String orderID, String productPrice, String productQty, String paymentMethod, String userID, String date, String city, String deliveryPrice) {
        this.productID = productID;
        this.product_image_orders = product_image_orders;

        this.productTitleOrder = productTitleOrder;
        this.deliveryStatus = deliveryStatus;
        this.cuttedPrice = cuttedPrice;
        this.orderID = orderID;
        this.productPrice = productPrice;
        this.productQty = productQty;
        this.paymentMethod = paymentMethod;
        this.userID = userID;
        this.date = date;
        this.city = city;
        this.deliveryPrice=deliveryPrice;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProduct_image_orders() {
        return product_image_orders;
    }

    public void setProduct_image_orders(String product_image_orders) {
        this.product_image_orders = product_image_orders;
    }


    public String getProductTitleOrder() {
        return productTitleOrder;
    }

    public void setProductTitleOrder(String productTitleOrder) {
        this.productTitleOrder = productTitleOrder;
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public String getCuttedPrice() {
        return cuttedPrice;
    }

    public void setCuttedPrice(String cuttedPrice) {
        this.cuttedPrice = cuttedPrice;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductQty() {
        return productQty;
    }

    public void setProductQty(String productQty) {
        this.productQty = productQty;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}