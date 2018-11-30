package com.cybersix.markme.io;

import com.cybersix.markme.model.UserModel;

public class DiskIO implements UserModelIO {
    @Override
    public UserModel findUser(String username) {
        return null;
    }

    @Override
    public boolean addUser(UserModel user) {
        return false;
    }

    @Override
    public boolean deleteUser(UserModel user) {
        return false;
    }

    @Override
    public void editUser(UserModel user) {

    }
}
