package com.example.axellageraldinc.crudmysql;

/**
 * Created by axellageraldinc on 9/11/17.
 */

public class UserModel {
    private int id;
    private String username, password;

    public UserModel(int id, String username, String password){
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
