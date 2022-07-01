package com.dina.aplikasipelayananmasyarakat;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {
    String email;
    String name;
    String phone;
    int role;

    public User(String email, String name, String phone, int role) {
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
