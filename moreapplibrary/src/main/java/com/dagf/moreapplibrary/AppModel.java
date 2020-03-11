package com.dagf.moreapplibrary;

public class AppModel {
    public String slug;
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getAppDescShort() {
        return appDescShort;
    }

    public void setAppDescShort(String appDescShort) {
        this.appDescShort = appDescShort;
    }

    public String getAppDesc() {
        return appDesc;
    }

    public void setAppDesc(String appDesc) {
        this.appDesc = appDesc;
    }

    public String getImgBig() {
        return imgBig;
    }

    public void setImgBig(String imgBig) {
        this.imgBig = imgBig;
    }

    public String getImgSmall() {
        return imgSmall;
    }

    public void setImgSmall(String imgSmall) {
        this.imgSmall = imgSmall;
    }

    public String getPackagen() {
        return packagen;
    }

    public void setPackagen(String packagen) {
        this.packagen = packagen;
    }

    public boolean yaSalio;
    public int status;
    private String appName;
    private String appDescShort;

    public int getInstalls() {
        return installs;
    }

    public void setInstalls(int installs) {
        this.installs = installs;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    private int installs;
    private float rate;

    public String getPromotional() {
        return promotional;
    }

    public void setPromotional(String promotional) {
        this.promotional = promotional;
    }

    private String promotional;
    private String appDesc;
    private String imgBig;
    private String imgSmall;
    private String packagen;
}
