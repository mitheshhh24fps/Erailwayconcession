package com.example.demoapp.Models;

public class Model {

    String form_name,form_roll_no,form_faculty,form_source,form_dest,form_id;

    public Model(String form_name, String form_roll_no, String form_faculty, String form_source, String form_dest, String form_id) {
        this.form_name = form_name;
        this.form_roll_no = form_roll_no;
        this.form_faculty = form_faculty;
        this.form_source = form_source;
        this.form_dest = form_dest;
        this.form_id = form_id;
    }


    public String getForm_name() {
        return form_name;
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
