package com.cybersix.markme;

import com.cybersix.markme.model.CareProvider;
import com.cybersix.markme.model.ProblemModel.DescriptionTooLongException;
import com.cybersix.markme.model.Patient;
import com.cybersix.markme.model.ProblemModel;
import com.cybersix.markme.model.ProblemModel.TitleTooLongException;
import com.cybersix.markme.model.UserModel;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;


public class PatientTest {

    @Test
    public void testAddProblem() {
        try{
            Patient pat = new Patient("guy44");
            ProblemModel pm = new ProblemModel("major issue","");
            ProblemModel pm2 = new ProblemModel("big problem","");
            pat.addProblem(pm);
            pat.addProblem(pm2);

            ArrayList<ProblemModel> getProblem =  pat.getProblems();
            assertEquals(pm,getProblem.get(0));
            assertEquals(pm2,getProblem.get(1));
        } catch(TitleTooLongException e){
            fail();
        } catch (DescriptionTooLongException d) {
            fail();
        } catch(Exception e){
            fail();
        }

    }

    @Test
    public void testGetProblem() {
        try{
            Patient pat = new Patient("guy441");
            ProblemModel pm = new ProblemModel("major issue!","");
            ProblemModel pm2 = new ProblemModel("big problem!","");
            pat.addProblem(pm);


            ArrayList<ProblemModel> getProblem =  pat.getProblems();
            assertEquals(pm,getProblem.get(0));
            pat.addProblem(pm2);

            getProblem =  pat.getProblems();
            assertEquals(pm2,getProblem.get(1));
        } catch(TitleTooLongException e){
            fail();
        } catch (DescriptionTooLongException d) {
            fail();
        } catch(Exception e){
            fail();
        }
    }

    @Test
    public void testRemoveProblem() {
        try{
            Patient pat = new Patient("guy4asd41");
            ProblemModel pm = new ProblemModel("ma123jor issue!","");
            ProblemModel pm2 = new ProblemModel("big pr123oblem!","");
            pat.addProblem(pm);
            pat.addProblem(pm2);


            ArrayList<ProblemModel> getProblem =  pat.getProblems();
            assertEquals(pm,getProblem.get(0));
            assertEquals(pm2,getProblem.get(1));

            pat.removeProblem(pm);
            assertTrue(!pat.getProblems().contains(pm));

            pat.removeProblem(pm2);
            assertTrue(!pat.getProblems().contains(pm2));

        } catch(TitleTooLongException e){
            fail();
        } catch (DescriptionTooLongException d) {
            fail();
        } catch(Exception e){
            fail();
        }
    }

    @Test
    public void testRemoveProblemFromEmptyList() {
        try{
            Patient pat = new Patient("guy4asd41");
            ProblemModel pm = new ProblemModel("ma123jor issue!","");
            assertEquals(pat.getProblems().size(), 0); // Check empty before
            pat.removeProblem(pm);
            assertEquals(pat.getProblems().size(), 0); // Check empty after
        } catch(Exception e){
            fail();
        }
    }

    @Test
    public void testAddCareProvider() throws UserModel.UsernameTooShortException {
        Patient pat = new Patient("guy44");
        CareProvider careProv = new CareProvider("Joe");
        CareProvider careProv2 = new CareProvider("Jorge");

        pat.addCareProvider(careProv);
        pat.addCareProvider(careProv2);

        assertTrue(pat.getCareProviders().contains(careProv));
        assertTrue(pat.getCareProviders().contains(careProv2));
    }

    @Test
    public void testGetCareProvider() throws UserModel.UsernameTooShortException {
        Patient pat = new Patient("guy44");
        CareProvider careProv = new CareProvider("Joe");
        CareProvider careProv2 = new CareProvider("Jorge");

        pat.addCareProvider(careProv);
        ArrayList<CareProvider> careList = pat.getCareProviders();
        assertEquals(careProv,careList.get(0));


        pat.addCareProvider(careProv2);
        careList = pat.getCareProviders();
        assertEquals(careProv2,careList.get(1));
    }

    @Test
    public void testRemoveCareProvider() throws UserModel.UsernameTooShortException {
        Patient pat = new Patient("guy44");
        CareProvider careProv = new CareProvider("Joe");
        CareProvider careProv2 = new CareProvider("Jorge");

        pat.addCareProvider(careProv);
        pat.addCareProvider(careProv2);

        pat.removeCareProvider(careProv);
        assertTrue(!pat.getCareProviders().contains(careProv));

        pat.removeCareProvider(careProv2);
        assertTrue(!pat.getCareProviders().contains(careProv2));
    }

    @Test
    public void testRemoveCareProviderFromEmptyList() throws UserModel.UsernameTooShortException {
        Patient pat = new Patient("guy44");
        CareProvider careProv = new CareProvider("Joe");

        assertEquals(pat.getCareProviders().size(), 0); // Check empty before
        pat.removeCareProvider(careProv);
        assertEquals(pat.getCareProviders().size(), 0); // Check empty after
    }

}