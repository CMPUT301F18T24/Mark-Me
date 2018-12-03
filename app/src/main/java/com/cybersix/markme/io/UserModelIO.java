/**
 * CMPUT 301 Team 24
 *
 * This is the interface for the User model functions required to be implemented by the elastic
 * search IO.
 *
 * Version 0.1
 *
 * Date: 2018-12-02
 *
 * Copyright Notice
 * @author Rizwan Qureshi
 * @see com.cybersix.markme.io.ElasticSearchIO
 */
package com.cybersix.markme.io;

import com.cybersix.markme.model.UserModel;

public interface UserModelIO {
    void findUser(String username, OnTaskComplete handler);
    void addUser(UserModel user, OnTaskComplete handler);
    void deleteUser(UserModel user, OnTaskComplete handler);
    void editUser(UserModel user, OnTaskComplete handler);
    String transferUser(String shortCode);
    String generateTransferCode(String username);
}
