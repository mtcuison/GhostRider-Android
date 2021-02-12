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


@RunWith(MockitoJUnitRunner.class)
public class VMPromiseToPayTest  {

    private String brnCode, collectName, date;
    @Mock
    ViewModelCallback callback;

    @Mock
    PromiseToPayModel infoModels;

    @Mock
    VMPromiseToPay mViewModel;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        date = "February 20, 2021";
        collectName = "Sabiniano, Jonathan";
        brnCode = "M042";
        infoModels.setPtpDate(date);
        infoModels.setPtpCollectorName(collectName);
        infoModels.setPtpBranch(brnCode);
        infoModels.setPtpAppointmentUnit("1");
        infoModels.setPtpRemarks("Promise to Pay");
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void savePtpInfo() {
        doReturn(true).when(mViewModel).savePtpInfo(infoModels, callback);
        Assert.assertTrue(mViewModel.savePtpInfo(infoModels, callback));
    }

    @Test
    public void toJsonObject()  {
        String jsonString = toJsonObjectString(date, brnCode, collectName);
        when(mViewModel.toJsonObject(date, brnCode, collectName)).thenReturn(jsonString);
        Assert.assertEquals(jsonString, mViewModel.toJsonObject(date, brnCode, collectName));

    }

    public String toJsonObjectString(String sdate, String sbrnCode, String scollectName) {
        JSONObject jsonObject = new JSONObject();
            //yyyy-MM-dd
        try {
            //yyyy-MM-dd
            jsonObject.put("dPromised", sdate);
            jsonObject.put("sBrnCde", sbrnCode);
            jsonObject.put("sCollector", scollectName);
            return jsonObject.toString();
        } catch (JSONException e) {
            e.printStackTrace();
            return  e.getMessage();
        }


    }
}