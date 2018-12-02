/**
 * CMPUT 301 Team 24
 *
 * This is the interface for the all the necessary functions required for getting and adding
 * problem models from and to elastic search
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

import com.cybersix.markme.model.ProblemModel;
import com.cybersix.markme.model.UserModel;

import java.util.ArrayList;

public interface ProblemModelIO {
    ProblemModel findProblem(String problemId);
    void addProblem(ProblemModel problem);
    ArrayList<ProblemModel> getProblems(UserModel user);
}
