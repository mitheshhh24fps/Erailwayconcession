package com.example.demoapp.Models;

public class LoginModel {

    String name,password,age,dept,rollNo,email,id;

    public LoginModel() {
    }

    public LoginModel(String name, String password, String age, String dept, String rollNo, String email, String id) {
        this.name = name;
        this.password = password;
        this.age = age;
        this.dept = dept;
        this.rollNo = rollNo;
        this.email = email;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getRollNo() {
        return rollNo;
    }

    public void setRollNo(String rollNo) {
        this.rollNo = rollNo;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
