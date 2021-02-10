package org.rmj.guanzongroup.onlinecreditapplication.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class OtherInfoModelTest {
    private OtherInfoModel infoModels;
    private ArrayList<OtherInfoModel> arrayList;
    @Before
    public void setUp() throws Exception {
        infoModels = new OtherInfoModel();
        arrayList = new ArrayList<>();
        infoModels = new OtherInfoModel("Jonathan Sabiniano", "Cawayan Bogtong", "20", "09452086661");
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
        assertTrue(infoModels.isValidSpinner());
    }

    @Test
    public void test_isValidReferences() {
        assertTrue(infoModels.isValidReferences());
    }
}