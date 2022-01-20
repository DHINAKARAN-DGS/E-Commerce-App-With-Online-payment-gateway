package com.daatstudios.cosmic_glow;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nullable;

public class CartItemsModel {
    public static final int CART_ITEM_LAYOUT = 0;
    public static final int CART_TOTAL_PRICE = 1;
    public static final int CONTINUE_BTN = 2;


    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    ////CartItEMS
    private String product_image, productID;
    private Long offersApplied, free_coupons, product_quantity, coupons_applied, maxQuantity, stockQuantity;
    private String product_title, product_price, cuttedPrice;
    private boolean inStock;
    private List<String> quantityIds;
    private boolean qtrError;


    public CartItemsModel(int type, String product_image, Long offersApplied,
                          Long free_coupons, Long product_quantity,
                          Long coupons_applied, String product_title,
                          String product_price, String cuttedPrice, String productID, boolean inStock, Long maxQuantity, Long stockQuantity) {
        this.type = type;
        this.product_image = product_image;
        this.offersApplied = offersApplied;
        this.free_coupons = free_coupons;
        this.product_quantity = product_quantity;
        this.coupons_applied = coupons_applied;
        this.product_title = product_title;
        this.product_price = product_price;
        this.cuttedPrice = cuttedPrice;
        this.productID = productID;
        this.inStock = inStock;
        this.maxQuantity = maxQuantity;
        quantityIds = new ArrayList<>();
        this.stockQuantity = stockQuantity;
        qtrError = false;
    }


    public boolean isQtrError() {
        return qtrError;
    }

    public void setQtrError(boolean qtrError) {
        this.qtrError = qtrError;
    }

    public Long getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Long stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public List<String> getQuantityIds() {
        return quantityIds;
    }

    public void setQuantityIds(List<String> quantityIds) {
        this.quantityIds = quantityIds;
    }

    public Long getMaxQuantity() {
        return maxQuantity;
    }

    public void setMaxQuantity(Long maxQuantity) {
        this.maxQuantity = maxQuantity;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProduct_image() {
        return product_image;
    }

    public void setProduct_image(String product_image) {
        this.product_image = product_image;
    }

    public Long getOffersApplied() {
        return offersApplied;
    }

    public void setOffersApplied(Long offersApplied) {
        this.offersApplied = offersApplied;
    }

    public Long getFree_coupons() {
        return free_coupons;
    }

    public void setFree_coupons(Long free_coupons) {
        this.free_coupons = free_coupons;
    }

    public Long getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(Long product_quantity) {
        this.product_quantity = product_quantity;
    }

    public Long getCoupons_applied() {
        return coupons_applied;
    }

    public void setCoupons_applied(Long coupons_applied) {
        this.coupons_applied = coupons_applied;
    }

    public String getProduct_title() {
        return product_title;
    }

    public void setProduct_title(String product_title) {
        this.product_title = product_title;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public String getCuttedPrice() {
        return cuttedPrice;
    }

    public void setCuttedPrice(String cuttedPrice) {
        this.cuttedPrice = cuttedPrice;
    }
    ////CartItEMS/////////////


    /////CART TOTAL PRICE////////////
    private int totalItems,totalItemPrice,totalAmount,savedAmount;
    private String deliveryPrice;
    public CartItemsModel(int type) {
        this.type = type;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public int getTotalItemPrice() {
        return totalItemPrice;
    }

    public void setTotalItemPrice(int totalItemPrice) {
        this.totalItemPrice = totalItemPrice;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public int getSavedAmount() {
        return savedAmount;
    }

    public void setSavedAmount(int savedAmount) {
        this.savedAmount = savedAmount;
    }

    public String getDeliveryPrice() {
        return deliveryPrice;
    }

    public void setDeliveryPrice(String deliveryPrice) {
        this.deliveryPrice = deliveryPrice;
    }
    /////CART TOTAL PRICE




}