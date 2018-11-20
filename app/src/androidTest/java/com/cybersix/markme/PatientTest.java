package com.cybersix.markme;

import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;


public class PatientTest {

    @Test
    public void testAddProblem() {
        Patient pat = new Patient("guy44","password!");
        try{
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
        Patient pat = new Patient("guy441","passwor123d!");
        try{
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
        Patient pat = new Patient("guy4asd41","pasasd123swor123d!");
        try{
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
        Patient pat = new Patient("guy4asd41","pasasd123swor123d!");
        try{
            ProblemModel pm = new ProblemModel("ma123jor issue!","");
            assertEquals(pat.getProblems().size(), 0); // Check empty before
            pat.removeProblem(pm);
            assertEquals(pat.getProblems().size(), 0); // Check empty after
        } catch(Exception e){
            fail();
        }
    }

    @Test
    public void testAddCareProvider() {
        Patient pat = new Patient("guy44","password!");
        CareProvider careProv = new CareProvider("Joe");
        CareProvider careProv2 = new CareProvider("Jorge");

        pat.addCareProvider(careProv);
        pat.addCareProvider(careProv2);

        assertTrue(pat.getCareProviders().contains(careProv));
        assertTrue(pat.getCareProviders().contains(careProv2));
    }

    @Test
    public void testGetCareProvider() {
        Patient pat = new Patient("guy44","password!");
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
    public void testRemoveCareProvider() {
        Patient pat = new Patient("guy44","password!");
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
    public void testRemoveCareProviderFromEmptyList() {
        Patient pat = new Patient("guy44","password!");
        CareProvider careProv = new CareProvider("Joe");

        assertEquals(pat.getCareProviders().size(), 0); // Check empty before
        pat.removeCareProvider(careProv);
        assertEquals(pat.getCareProviders().size(), 0); // Check empty after
    }

}