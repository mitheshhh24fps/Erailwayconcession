package com.example.demoapp.Models;

public class FormModel {
    String form_name,form_roll_no,form_faculty,form_source,form_dest,form_id,email;


    public FormModel() {
    }

    public FormModel(String form_name, String form_roll_no, String form_faculty, String form_source, String form_dest, String form_id, String email) {
        this.form_name = form_name;
        this.form_roll_no = form_roll_no;
        this.form_faculty = form_faculty;
        this.form_source = form_source;
        this.form_dest = form_dest;
        this.form_id = form_id;
        this.email = email;
    }

    public String getForm_name() {
        return form_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setForm_name(String form_name) {
        this.form_name = form_name;
    }

    public String getForm_roll_no() {
        return form_roll_no;
    }

    public void setForm_roll_no(String form_roll_no) {
        this.form_roll_no = form_roll_no;
    }

    public String getForm_faculty() {
        return form_faculty;
    }

    public void setForm_faculty(String form_faculty) {
        this.form_faculty = form_faculty;
    }

    public String getForm_source() {
        return form_source;
    }

    public void setForm_source(String form_source) {
        this.form_source = form_source;
    }

    public String getForm_dest() {
        return form_dest;
    }

    public void setForm_dest(String form_dest) {
        this.form_dest = form_dest;
    }

    public String getForm_id() {
        return form_id;
    }

    public void setForm_id(String form_id) {
        this.form_id = form_id;
    }
}
