package com.cybersix.markme.io;

import com.cybersix.markme.model.ProblemModel;
import com.cybersix.markme.model.UserModel;

import java.util.ArrayList;

public interface ProblemModelIO {
    void findProblem(String problemId, OnTaskComplete handler);
    void addProblem(ProblemModel problem, OnTaskComplete handler);
    void getProblems(UserModel user, OnTaskComplete handler);
}
