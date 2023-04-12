package org.rmj.g3appdriver.lib.GawadPacita.Obj;

import static org.junit.Assert.*;

import android.app.Application;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.dev.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.dev.Database.Entities.EPacitaEvaluation;
import org.rmj.g3appdriver.dev.Database.Entities.EPacitaRule;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.lib.GawadPacita.pojo.BranchRate;
import org.rmj.g3appdriver.lib.Panalo.Obj.GPanalo;

import java.util.List;
import java.util.Random;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class PacitaTest {
    private static final String TAG = PacitaTest.class.getSimpleName();

    private Application instance;

    private EmployeeMaster poUser;
    private Pacita poSys;

    private boolean isSuccess = false;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
        instance = ApplicationProvider.getApplicationContext();
        AppConfigPreference.getInstance(instance).setTestCase(true);
        poUser = new EmployeeMaster(instance);
        poSys = new Pacita(instance);

        EmployeeMaster.UserAuthInfo loAuth = new EmployeeMaster.UserAuthInfo("mikegarcia8748@gmail.com", "123456", "09171870011");
        assertTrue(poUser.AuthenticateUser(loAuth));
    }

    @Test
    public void test01ImportPacitaRule() {
        if(!poSys.ImportPacitaRules()){
            Log.e(TAG, poSys.getMessage());
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);

        isSuccess = false;
    }

    @Test
    public void test02RetrievePacitaRules() {
        poSys.GetPacitaRules().observeForever(new Observer<List<EPacitaRule>>() {
            @Override
            public void onChanged(List<EPacitaRule> ePacitaRules) {
                if(ePacitaRules == null){
                    Log.e(TAG, "No pacita rules found.");
                    return;
                }



                if(ePacitaRules.size() == 0){
                    Log.e(TAG, "No pacita rules found.");
                    return;
                }

                Log.d(TAG, "Displaying the values of list");
                for(int x = 0; x < ePacitaRules.size(); x++){
                    Log.d(TAG, ePacitaRules.get(x).getFieldNmx());
                }

                isSuccess = true;
            }
        });

        assertTrue(isSuccess);

        isSuccess = false;
    }

    @Test
    public void test03BranchEvaluation() {
        String lsResult = poSys.InitializePacitaEvaluation("M001");
        if(lsResult == null){
            Log.e(TAG, poSys.getMessage());
        } else {
            poSys.GetEvaluationRecord(lsResult).observeForever(new Observer<EPacitaEvaluation>() {
                @Override
                public void onChanged(EPacitaEvaluation ePacitaEvaluation) {
                    if(ePacitaEvaluation == null){
                        Log.e(TAG, "No evaluation record found.");
                        return;
                    }

                    poSys.GetPacitaRules().observeForever(new Observer<List<EPacitaRule>>() {
                        @Override
                        public void onChanged(List<EPacitaRule> ePacitaRules) {
                            if(ePacitaRules == null){
                                Log.e(TAG, "No pacita rules found.");
                                return;
                            }

                            if(ePacitaRules.size() == 0){
                                Log.e(TAG, "No pacita rules found.");
                                return;
                            }

                            Log.d(TAG, "Displaying the values of list");
                            for(int x = 0; x < ePacitaRules.size(); x++){
                                Log.d(TAG, ePacitaRules.get(x).getFieldNmx());
                            }

                            String lsPayload = ePacitaEvaluation.getPayloadx();
                            Log.d(TAG, "Evaluation Payload: " + lsPayload);
                            List<BranchRate> loRate = PacitaRule.ParseBranchRate(lsPayload, ePacitaRules);

                            if(loRate == null){
                                Log.e(TAG, "Unable to parse payload and pacita rules for UI.");
                                return;
                            }

                            if(loRate.size() == 0){
                                Log.e(TAG, "Unable to parse payload and pacita rules for UI.");
                                return;
                            }

                            for(int x = 0; x < loRate.size(); x++){
                                Log.d(TAG, "Entry No: " + loRate.get(x).getsRateIDxx() + ", Field Name: " + loRate.get(x).getsRateName() +", Rating: " + loRate.get(x).getcPasRatex());
                            }
                        }
                    });
                }
            });
        }
    }

    @Test
    public void test04EvaluateBranch() {
        String lsResult = poSys.InitializePacitaEvaluation("M001");
        if(lsResult == null){
            Log.e(TAG, poSys.getMessage());
        } else {
            poSys.GetEvaluationRecord(lsResult).observeForever(new Observer<EPacitaEvaluation>() {
                @Override
                public void onChanged(EPacitaEvaluation ePacitaEvaluation) {
                    if(ePacitaEvaluation == null){
                        Log.e(TAG, "No evaluation record found.");
                        return;
                    }

                    poSys.GetPacitaRules().observeForever(new Observer<List<EPacitaRule>>() {
                        @Override
                        public void onChanged(List<EPacitaRule> ePacitaRules) {
                            if(ePacitaRules == null){
                                Log.e(TAG, "No pacita rules found.");
                                return;
                            }

                            if(ePacitaRules.size() == 0){
                                Log.e(TAG, "No pacita rules found.");
                                return;
                            }

                            Log.d(TAG, "Displaying the values of list");
                            for(int x = 0; x < ePacitaRules.size(); x++){
                                Log.d(TAG, ePacitaRules.get(x).getFieldNmx());
                            }

                            String lsPayload = ePacitaEvaluation.getPayloadx();
                            Log.d(TAG, "Evaluation Payload: " + lsPayload);
                            List<BranchRate> loRate = PacitaRule.ParseBranchRate(lsPayload, ePacitaRules);

                            if(loRate == null){
                                Log.e(TAG, "Unable to parse payload and pacita rules for UI.");
                                return;
                            }

                            if(loRate.size() == 0){
                                Log.e(TAG, "Unable to parse payload and pacita rules for UI.");
                                return;
                            }

                            for(int x = 0; x < loRate.size(); x++){
                                Log.d(TAG, "Entry No: " + loRate.get(x).getsRateIDxx() + ", Field Name: " + loRate.get(x).getsRateName() +", Rating: " + loRate.get(x).getcPasRatex());
                            }
                            Random random = new Random();
                            int randomNumber;

                            for(int x = 0; x < loRate.size(); x++){
                                String lsTransNo = ePacitaEvaluation.getTransNox();
                                randomNumber = random.nextInt(2); // generates a random integer either 0 or 1
                                poSys.UpdateBranchRate(lsTransNo, loRate.get(x).getsRateIDxx(), String.valueOf(randomNumber));
                            }
                        }
                    });
                }
            });
        }
    }

    @Test
    public void test05PostEvaluation() {
        String lsResult = poSys.InitializePacitaEvaluation("M001");
        if(lsResult == null){
            Log.e(TAG, poSys.getMessage());
            assertTrue(isSuccess);
            isSuccess = false;

            return;
        }

        if(!poSys.SaveBranchRatings(lsResult)){
            Log.e(TAG, poSys.getMessage());
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);

        isSuccess = false;
    }
}