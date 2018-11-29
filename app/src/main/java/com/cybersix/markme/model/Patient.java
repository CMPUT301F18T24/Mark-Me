package com.cybersix.markme.model;

import com.cybersix.markme.model.CareProvider;
import com.cybersix.markme.model.ProblemModel;
import com.cybersix.markme.model.UserModel;
import com.cybersix.markme.model.UserModel.UsernameTooShortException;

import java.util.ArrayList;
import java.util.List;

public class Patient extends UserModel {
    private ArrayList<ProblemModel> problems;
    private ArrayList<CareProvider> providers;

    public Patient() {

    }

    public Patient(String username) throws UsernameTooShortException {
        super.setUsername(username);

        problems = new ArrayList<ProblemModel>();
        providers = new ArrayList<CareProvider>();
    }

    public void addProblem(ProblemModel problem){
        problems.add(problem);

        setChanged();
        notifyObservers();
    }

    public void setProblems(ArrayList<ProblemModel> problems) {
        this.problems = problems;
    }

    public ArrayList<ProblemModel> getProblems() {
        return problems;
    }

    public void addProblems(List<ProblemModel> problems) {
        this.problems.addAll(problems);

        setChanged();
        notifyObservers();
    }

    public ProblemModel removeProblem(ProblemModel problem){
        problems.remove(problem);
        return problem;
    }

    public void addCareProvider(CareProvider prov){
        providers.add(prov);

        setChanged();
        notifyObservers();
    }

    public ArrayList<CareProvider> getCareProviders() {
        return providers;
    }

    public CareProvider removeCareProvider(CareProvider careProv){
        providers.remove(careProv);
        return careProv;
    }
}
