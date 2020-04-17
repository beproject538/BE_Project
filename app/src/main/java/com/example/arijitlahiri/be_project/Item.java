package com.example.arijitlahiri.be_project;

import android.widget.ImageView;

public class Item {

    private ImageView logo;
    private String did;
    private String name;

    public Item(ImageView logo, String did, String name) {
        this.logo = logo;
        this.did = did;
        this.name = name;
    }

    public ImageView getLogo() {
        return logo;
    }

    public void setLogo(ImageView logo) {
        this.logo = logo;
    }

    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
