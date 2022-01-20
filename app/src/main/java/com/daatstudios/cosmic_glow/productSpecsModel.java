package com.daatstudios.cosmic_glow;

public class productSpecsModel
{

    public static final int SPECS_TITLE=0;
    public static final int SPECS_BODY=1;


    private int type ;
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }


    ///////SPECS TITLE
    private String  title;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public productSpecsModel(int type, String title) {
        this.type = type;
        this.title = title;
    }
    public productSpecsModel(String title) {
        this.title = title;
    }
    ///////SPECS TITLE


    //////SPECS BODY
    String featureName;
    String featureValue;

    public productSpecsModel(int type, String featureName, String featureValue) {
        this.type = type;
        this.featureName = featureName;
        this.featureValue = featureValue;
    }
    public String getFeatureName() {
        return featureName;
    }
    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }
    public String getFeatureValue() {
        return featureValue;
    }
    public void setFeatureValue(String featureValue) {
        this.featureValue = featureValue;
    }
    //////SPECS BODY
}
