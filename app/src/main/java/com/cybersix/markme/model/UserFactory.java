package com.cybersix.markme.model;

import com.cybersix.markme.io.ElasticSearchIO;

public class UserFactory {
    public static <T extends UserModel> T inflateUser(UserModel user) throws Exception {
        if (user.getUserType().equals(Patient.class.getSimpleName())) {
            Patient patient = new Patient(user.getUsername());
            patient.setEmail(user.getEmail());
            patient.setPhone(user.getPhone());
            patient.setUserId(user.getUserId());

            patient.addProblems( ElasticSearchIO.getInstance().getProblems(patient) );
            for (ProblemModel problem : patient.getProblems()) {
                problem.setRecords( ElasticSearchIO.getInstance().getRecords(problem) );
            }

            return (T) patient;
        } else if (user.getUserType().equals(CareProvider.class.getSimpleName())) {
            CareProvider careProvider = new CareProvider(user.getUsername());
            careProvider.setEmail(user.getEmail());
            careProvider.setPhone(user.getPhone());
            careProvider.setUserId(user.getUserId());
//            ElasticSearchIO.getInstance().getPatients(careProvider);

            return (T) careProvider;
        }

        return null;
    }
}
