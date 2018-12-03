/**
 * CMPUT 301 Team 24
 *
 * This is the interface for ensuring the formulas for getting and adding the user assignment
 * is added
 *
 * Version 0.1
 *
 * Date: 2018-12-02
 *
 * Copyright notice
 * @author Jose Ramirez
 * @see com.cybersix.markme.io.ElasticSearchIO
 */
package com.cybersix.markme.io;

import com.cybersix.markme.model.UserModel;

import java.util.ArrayList;

public interface AssignmentIO {
    ArrayList<UserModel> getAssignedUsers(String provider);
    void addAssignedUser(String patientID, String providerID);
    void removeAssignedUser(String patientID, String providerID);
}
