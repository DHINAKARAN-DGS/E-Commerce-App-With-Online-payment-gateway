package com.daatstudios.cosmic_glow;

public class rewardsModel
{
    private String title,body,validity;

    public rewardsModel(String title, String body, String validity) {
        this.title = title;
        this.body = body;
        this.validity = validity;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getValidity() {
        return validity;
    }

    public void setValidity(String validity) {
        this.validity = validity;
    }
}
