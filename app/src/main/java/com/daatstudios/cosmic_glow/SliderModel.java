package com.daatstudios.cosmic_glow;

public class SliderModel
{

    private String  banner,action;


    public SliderModel(String banner,String action) {
        this.banner = banner;
        this.action = action;

    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }


}
