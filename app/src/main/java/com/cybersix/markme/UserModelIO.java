package com.cybersix.markme;

public interface UserModelIO {
    UserModel findUser(String username);
    boolean addUser(UserModel user);
    boolean deleteUser(UserModel user);
    void editUser(UserModel user);
}
