package com.example.zonghe;

public class User {
    private int id;
    private String strUsername;
    private String strPassword;
    public User(){}
    public User(String username,String userpassword){
        strUsername = username;
        strPassword = userpassword;
    }
    public void setUsername(String username){
        strUsername = username;
    }
    public void setUserpassword(String userpassword){
        strPassword = userpassword;
    }
    public int getId(){
        return id;
    }
    public String getUsername(){
        return strUsername;
    }
    public String getStrPassword(){
        return strPassword;
    }
}
