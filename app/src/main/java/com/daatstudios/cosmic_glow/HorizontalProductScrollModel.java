package com.daatstudios.cosmic_glow;

public class HorizontalProductScrollModel
{
    private String productImage,productName,productDiscription,productPrice;
    private String  productId;
    public HorizontalProductScrollModel(String productImage,String productId, String productName, String productDiscription, String productPrice) {
        this.productImage = productImage;
        this.productName = productName;
        this.productDiscription = productDiscription;
        this.productPrice = productPrice;
        this.productId = productId;
    }




    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductDiscription() {
        return productDiscription;
    }

    public void setProductDiscription(String productDiscription) {
        this.productDiscription = productDiscription;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

   }
