package com.example.arijitlahiri.be_project;

import android.widget.ImageView;

public class Item {

    private ImageView logo;
    private String conid;
    private String inviter;

    public Item(ImageView logo, String conid, String inviter) {
        this.logo = logo;
        this.conid = conid;
        this.inviter = inviter;
    }

    public ImageView getLogo() {
        return logo;
    }

    public void setLogo(ImageView logo) {
        this.logo = logo;
    }

    public String getConid() {
        return conid;
    }

    public void setConid(String conid) {
        this.conid = conid;
    }

    public String getInviter() {
        return inviter;
    }

    public void setInviter(String inviter) {
        this.inviter = inviter;
    }
}
