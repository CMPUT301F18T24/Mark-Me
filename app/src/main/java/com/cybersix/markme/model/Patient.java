/**
 * CMPUT 301 Team 24
 *
 * This model will keep hold of the data of the patient. This will be used for the record list fragment,
 * and the problem list fragment, as well as keeping track of the patients information to then sync
 * with the elastic search database.
 *
 * Version 0.1
 *
 * Date: 2018-11-11
 *
 * Copyright Notice
 * @author Vishal Patel TODO: Maybe someone else I don't know
 * @see com.cybersix.markme.fragment.RecordListFragment
 * @see com.cybersix.markme.fragment.ProblemListFragment
 */
package com.cybersix.markme.model;

import com.cybersix.markme.model.CareProvider;
import com.cybersix.markme.model.ProblemModel;
import com.cybersix.markme.model.UserModel;
import com.cybersix.markme.model.UserModel.UsernameTooShortException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Patient extends UserModel {
    private ArrayList<ProblemModel> problems;
    private ArrayList<CareProvider> providers;
    private Runnable onProblemsChanged = null;

    public Patient() {
        problems = new ArrayList<ProblemModel>();
        providers = new ArrayList<CareProvider>();
    }

    public Patient(String username) throws UsernameTooShortException {
        super.setUsername(username);

        problems = new ArrayList<ProblemModel>();
        providers = new ArrayList<CareProvider>();
    }

    public void addProblem(ProblemModel problem){
        problems.add(problem);
        problem.setPatientId(this.getUserId());

        setChanged();
        notifyObservers();
    }

    public void onProblemsChanged() {
        if (onProblemsChanged != null)
            onProblemsChanged.run();
    }

    public void setOnProblemsChanged(Runnable runnable) {
        onProblemsChanged = runnable;
    }

    public void setProblems(ArrayList<ProblemModel> problems) {
        this.problems = problems;
        onProblemsChanged();
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
