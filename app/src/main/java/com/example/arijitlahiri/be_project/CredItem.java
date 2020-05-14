package com.example.arijitlahiri.be_project;

public class CredItem {

    private String cred_key,cred_value;

    public CredItem(String cred_key, String cred_value) {
        this.cred_key = cred_key;
        this.cred_value = cred_value;
    }

    public String getCred_key() {
        return cred_key;
    }

    public String getCred_value() {
        return cred_value;
    }

    public void setCred_key(String cred_key) {
        this.cred_key = cred_key;
    }

    public void setCred_value(String cred_value) {
        this.cred_value = cred_value;
    }
}
