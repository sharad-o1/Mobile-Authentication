package com.example.authenticationdemo;

public class Model {
    String Name;
    String Phone;
    String Email;
    String Password;

    String  Uid;

    public Model() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public Model(String name, String phone, String email, String password, String uid) {
        Name = name;
        Phone = phone;
        Email = email;
        Password = password;
        Uid = uid;
    }

    @Override
    public String toString() {
        return "Model{" +
                "Name='" + Name + '\'' +
                ", Phone='" + Phone + '\'' +
                ", Email='" + Email + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}
