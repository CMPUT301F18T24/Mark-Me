/**
 * CMPUT 301 Team 24
 *
 * This is the GeneralIO "controller" that will be used for saving offline and/or online. If the
 * online server can't connect, then everything is saved locally and later synced.
 *
 * Version 0.1
 *
 * Date: 2018-12-02
 *
 * Copyright Notice
 * @author Rizwan Qureshi
 * @see com.cybersix.markme.io.ElasticSearchIO
 * @see com.cybersix.markme.io.DiskIO
 */
package com.cybersix.markme.io;

import com.cybersix.markme.model.UserModel;

public class GeneralIO implements UserModelIO {
    private DiskIO diskIO = null;
    private ElasticSearchIO elasticSearchIO = null;

    public GeneralIO() {

    }

    @Override
    public UserModel findUser(String username) {
        if (elasticSearchIO.isConnected()) // give preference to cloud before local
            return elasticSearchIO.findUser(username);
        else
            return diskIO.findUser(username);
    }

    @Override
    public boolean addUser(UserModel user) {
        if (elasticSearchIO.isConnected())
            return elasticSearchIO.addUser(user);
        return diskIO.addUser(user);
    }

    @Override
    public boolean deleteUser(UserModel user) {
        if (elasticSearchIO.isConnected())
            return elasticSearchIO.deleteUser(user);

        return diskIO.deleteUser(user);
    }

    @Override
    public void editUser(UserModel user) {
        if (elasticSearchIO.isConnected())
            elasticSearchIO.editUser(user);

        diskIO.editUser(user);
    }
}
