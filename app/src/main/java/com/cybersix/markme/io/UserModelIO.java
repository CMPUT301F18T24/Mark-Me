package com.cybersix.markme.io;

import com.cybersix.markme.model.UserModel;

public interface UserModelIO {
    void findUser(String username, OnTaskComplete handler);
    void addUser(UserModel user, OnTaskComplete handler);
    void deleteUser(UserModel user, OnTaskComplete handler);
    void editUser(UserModel user, OnTaskComplete handler);
}
