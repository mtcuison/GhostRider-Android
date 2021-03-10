package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.PromiseToPayModel;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class VMPromiseToPayTest  {

    private String brnCode, collectName, date, remarks, appUnit;
    private String emptyBrnCode;
    PromiseToPayModel infoModels;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        infoModels = new PromiseToPayModel();
        date = "February 20, 2021";
        collectName = "Sabiniano, Jonathan";

        emptyBrnCode = "";
        appUnit = "0";
        remarks = "Promise to Pay";
        if (Integer.parseInt(appUnit) < 1){
            brnCode = "M042";
        }else{
            brnCode = "";
        }
        emptyBrnCode = brnCode;

        infoModels.setPtpBranch(emptyBrnCode);
        infoModels.setPtpDate(date);
        infoModels.setPtpCollectorName(collectName);
        infoModels.setPtpAppointmentUnit(appUnit);
        infoModels.setPtpRemarks(remarks);
    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void test_getInfoModels(){

        assertEquals(date, infoModels.getPtpDate());
        assertEquals(collectName, infoModels.getPtpCollectorName());
        assertEquals(emptyBrnCode, infoModels.getPtpBranch());
        assertEquals(appUnit, infoModels.getPtpAppointmentUnit());
        assertEquals(remarks, infoModels.getPtpRemarks());

        System.out.print("Branch code " + infoModels.getPtpBranch() + "\n");
        System.out.print("Application Unit " + infoModels.getPtpAppointmentUnit() + "\n");
        System.out.print("Colletion Name " + infoModels.getPtpCollectorName() + "\n");
        System.out.print("Promise Date " + infoModels.getPtpDate() + "\n");
        System.out.print("Remarks " + infoModels.getPtpRemarks());
    }
    @Test
    public void savePtpInfo() {
//        doReturn(true).when(mViewModel).savePtpInfo(infoModels, callback);
//        Assert.assertTrue(mViewModel.savePtpInfo(infoModels, callback));
    }



}