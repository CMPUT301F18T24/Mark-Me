package com.cybersix.markme.io;

import com.cybersix.markme.model.UserModel;

public class GeneralIO implements UserModelIO {
    private DiskIO diskIO = null;
    private ElasticSearchIO elasticSearchIO = null;

    public GeneralIO() {

    }

    @Override
    public UserModel findUser(String username) {
//        if (elasticSearchIO.isConnected()) // give preference to cloud before local
            return elasticSearchIO.findUser(username);
    }

    @Override
    public boolean addUser(UserModel user) {
//        if (elasticSearchIO.isConnected())
            return elasticSearchIO.addUser(user);
//        return diskIO.addUser(user);
    }

    @Override
    public boolean deleteUser(UserModel user) {
//        if (elasticSearchIO.isConnected())
            return elasticSearchIO.deleteUser(user);

//        return diskIO.deleteUser(user);
    }

    @Override
    public void editUser(UserModel user) {
//        if (elasticSearchIO.isConnected())
            elasticSearchIO.editUser(user);

//        diskIO.editUser(user);
    }
}
