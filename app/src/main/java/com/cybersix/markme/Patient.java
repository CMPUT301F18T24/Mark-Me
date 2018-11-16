package com.cybersix.markme;

import java.util.ArrayList;

public class Patient extends UserModel {

    private ArrayList<ProblemModel> problems;
    private ArrayList<CareProvider> providers;

    public Patient(String userID, String password){
        super();
        //super.setUserID(userID);
        super.setPassword(password);
        problems = new ArrayList<ProblemModel>();
        providers = new ArrayList<CareProvider>();
    }

    public void addProblem(ProblemModel problem){
        problems.add(problem);
    }

    public ArrayList<ProblemModel> getProblems() {
        return problems;
    }

    public ProblemModel removeProblem(ProblemModel problem){
        problems.remove(problem);
        return problem;
    }

    public void addCareProvider(CareProvider prov){
        providers.add(prov);
    }

    public ArrayList<CareProvider> getCareProviders() {
        return providers;
    }

    public CareProvider removeCareProvider(CareProvider careProv){
        providers.remove(careProv);
        return careProv;
    }

    public String getPatientID(){
        return this.getUserID();
    }
}
