package org.rmj.guanzongroup.onlinecreditapplication.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OtherInfoModelTest {
    private OtherInfoModel infoModels;
    private ArrayList<OtherInfoModel> arrayList;
    @Before
    public void setUp() throws Exception {
        infoModels = new OtherInfoModel();
        arrayList = new ArrayList<>();
        infoModels = new OtherInfoModel("Jonathan Sabiniano", "Cawayan Bogtong", "0335", "09452086661");
        arrayList.add(infoModels);
        infoModels.setUnitUserModel("0");
        infoModels.setUserUnitPurposeModel("2");
        infoModels.setMonthlyPayerModel("0");
        infoModels.setSourceModel("0");
    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void test_otherInfo_size() {
        assertEquals(1, arrayList.size());
    }
    @Test
    public void test_isValidSpinner() {
        assertTrue(infoModels.isDataValid());
    }

    @Test
    public void test_isValidReferences() {
        assertTrue(infoModels.isValidReferences());
        System.out.println("Full Name : " + infoModels.getFullname());
        System.out.println("Address : " + infoModels.getAddress1());
        System.out.println("Town ID : " + infoModels.getTownCity());
        System.out.println("Contact No: " + infoModels.getContactN());
        System.out.println("Unit User index: " + infoModels.getUnitUserModel());
        System.out.println("Unit Purpose index: " + infoModels.getUserUnitPurposeModel());
        System.out.println("Payer index: " + infoModels.getMonthlyPayerModel());
        System.out.println("Company Source index: " + infoModels.getSourceModel());
    }
}