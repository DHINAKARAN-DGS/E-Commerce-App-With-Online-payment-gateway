package com.daatstudios.cosmic_glow;

import java.util.ArrayList;

public class wishlistModel
{
    private String productImageWL;
    private long freecouponWL;
    private long totalRatings;
    private String productTtileWl,productPriceWL,cutterPriceWL,ratingWL,productID;
    private Boolean paymentMethod;
    boolean in_stock;
    private ArrayList<String> tags;


    public wishlistModel(String  productImageWL, long freecouponWL, long totalRatings, String productTtileWl, String productPriceWL, String cutterPriceWL, String ratingWL, Boolean paymentMethod,String productID,boolean in_stock) {
        this.productImageWL = productImageWL;
        this.freecouponWL = freecouponWL;
        this.totalRatings = totalRatings;
        this.productTtileWl = productTtileWl;
        this.productPriceWL = productPriceWL;
        this.cutterPriceWL = cutterPriceWL;
        this.ratingWL = ratingWL;
        this.paymentMethod = paymentMethod;
        this.productID = productID;
        this.in_stock = in_stock;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

    public boolean isIn_stock() {
        return in_stock;
    }

    public void setIn_stock(boolean in_stock) {
        this.in_stock = in_stock;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductImageWL() {
        return productImageWL;
    }

    public void setProductImageWL(String  productImageWL) {
        this.productImageWL = productImageWL;
    }

    public long getFreecouponWL() {
        return freecouponWL;
    }

    public void setFreecouponWL(long freecouponWL) {
        this.freecouponWL = freecouponWL;
    }

    public long getTotalRatings() {
        return totalRatings;
    }

    public void setTotalRatings(long totalRatings) {
        this.totalRatings = totalRatings;
    }

    public String getProductTtileWl() {
        return productTtileWl;
    }

    public void setProductTtileWl(String productTtileWl) {
        this.productTtileWl = productTtileWl;
    }

    public String getProductPriceWL() {
        return productPriceWL;
    }

    public void setProductPriceWL(String productPriceWL) {
        this.productPriceWL = productPriceWL;
    }

    public String getCutterPriceWL() {
        return cutterPriceWL;
    }

    public void setCutterPriceWL(String cutterPriceWL) {
        this.cutterPriceWL = cutterPriceWL;
    }

    public String getRatingWL() {
        return ratingWL;
    }

    public void setRatingWL(String ratingWL) {
        this.ratingWL = ratingWL;
    }

    public Boolean getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(Boolean paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
}
