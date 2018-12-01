package com.cybersix.markme.io;

import com.cybersix.markme.model.ProblemModel;
import com.cybersix.markme.model.UserModel;

import java.util.ArrayList;

public interface ProblemModelIO {
    ProblemModel findProblem(String problemId);
    void addProblem(ProblemModel problem);
    ArrayList<ProblemModel> getProblems(UserModel user);
}
