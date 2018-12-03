package com.cybersix.markme.io;

import com.cybersix.markme.model.UserModel;

public interface UserModelIO {
    UserModel findUser(String username);
    boolean addUser(UserModel user);
    boolean deleteUser(UserModel user);
    void editUser(UserModel user);
    String transferUser(String shortCode);
    String generateTransferCode(String username);
}
