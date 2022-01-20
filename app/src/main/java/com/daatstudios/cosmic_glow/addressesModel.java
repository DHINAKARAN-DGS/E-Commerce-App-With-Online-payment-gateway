package com.daatstudios.cosmic_glow;

public class addressesModel {
    private String name, phone, city, locality, flatno, pincode, landmark, onumber;
    private Boolean selected;


    public addressesModel(String name, String phone, String city, String locality, String flatno, String pincode, String landmark, String onumber, Boolean selected) {
        this.name = name;
        this.phone = phone;
        this.city = city;
        this.locality = locality;
        this.flatno = flatno;
        this.pincode = pincode;
        this.landmark = landmark;
        this.onumber = onumber;
        this.selected = selected;
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

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getFlatno() {
        return flatno;
    }

    public void setFlatno(String flatno) {
        this.flatno = flatno;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getLandmark() {
        return landmark;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getOnumber() {
        return onumber;
    }

    public void setOnumber(String onumber) {
        this.onumber = onumber;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }
}
