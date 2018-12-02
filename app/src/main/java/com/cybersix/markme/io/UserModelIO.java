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
    UserModel findUser(String username);
    boolean addUser(UserModel user);
    boolean deleteUser(UserModel user);
    void editUser(UserModel user);
}
