package com.example.demoapp.Models;

public class DocumentListModel {
    String name,roll_no,Certificate,ID;

    public DocumentListModel() {
    }


    public DocumentListModel(String name, String roll_no, String certificate, String ID) {
        this.name = name;
        this.roll_no = roll_no;
        Certificate = certificate;
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoll_no() {
        return roll_no;
    }

    public void setRoll_no(String roll_no) {
        this.roll_no = roll_no;
    }

    public String getCertificate() {
        return Certificate;
    }

    public void setCertificate(String certificate) {
        Certificate = certificate;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
