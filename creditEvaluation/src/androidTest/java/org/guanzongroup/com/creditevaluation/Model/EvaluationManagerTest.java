package org.guanzongroup.com.creditevaluation.Model;

import static org.junit.Assert.*;

import android.app.Application;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.json.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class EvaluationManagerTest {

    private Application instance;
    private EvaluationManager poEvaluator;

    private JSONObject poJson;

    private static boolean isSuccess = false;

    @Before
    public void setUp() throws Exception {
        instance = ApplicationProvider.getApplicationContext();
        poEvaluator = new EvaluationManager(instance);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void test01DownloadApplications(){
        poEvaluator.DownloadCreditApplications(new EvaluationManager.OnActionCallback() {
            @Override
            public void OnSuccess(String args) {
                try {
                    poJson = new JSONObject(args);
                    isSuccess = true;
                } catch (Exception e){
                    e.printStackTrace();
                    isSuccess = false;
                }
            }

            @Override
            public void OnFailed(String message) {
                isSuccess = false;
            }
        });
        assertFalse(isSuccess);
    }

    @Test
    public void test02SaveApplication(){
        isSuccess = poEvaluator.SaveApplication(poJson);
        assertFalse(isSuccess);
    }

    @Test
    public void test03AddApplication(){
        poEvaluator.AddApplication("", new EvaluationManager.OnActionCallback() {
            @Override
            public void OnSuccess(String args) {
                try{
                    isSuccess = true;
                    poJson = new JSONObject(args);
                } catch (Exception e){
                    e.printStackTrace();
                    isSuccess = false;
                }
            }

            @Override
            public void OnFailed(String message) {
                isSuccess = false;
            }
        });
        assertFalse(isSuccess);
    }
}