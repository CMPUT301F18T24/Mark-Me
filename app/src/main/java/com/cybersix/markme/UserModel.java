package com.cybersix.markme;
//Click the class or method you want to test, then press Ctrl+Shift+T (⇧⌘T).

import android.os.Bundle;

public class UserModel {
    public String userID;
    String name;
    private String email;
    private String phone;
    private String password;
    private String userType;

    /*added a constructor*/
    public UserModel(String userID,String password){
        this.userID = userID;
        this.password = password;
    }

    public UserModel(){

    }

    private boolean isUser(String UserID){
        return UserID!=null;
    }
    private String getUsername(){
        return this.userID;
    }

    public String getUserID() {
        return userID;
    }

    private String getUserType(){

        return this.userType;
    }

    public boolean passwordExist(){
        return password!= null;
    }

    private String getEmail(){
        return this.email;
    }

    private String getPhone(){
        return this.phone;
    }

    private String getPassword(){
        return this.password;
    }

    public boolean useridExist(){
        return userID!=null;
    }
}
