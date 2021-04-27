/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.creditApp
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.onlinecreditapplication.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.rmj.g3appdriver.utils.CodeGenerator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class DisbursementInfoModelTest {
    private DisbursementInfoModel infoModel;
    String transnox;

    @Before
    public void setUp() throws Exception {
        transnox = new CodeGenerator().generateTransNox();
        infoModel = new DisbursementInfoModel();
        infoModel.setTransNo(transnox);
        infoModel.setElctX("1000");
        infoModel.setWaterX("1000");
        infoModel.setFoodX("3000");
        infoModel.setLoans("5000");
        infoModel.setBankN("BDO");
        infoModel.setStypeX("1");
        infoModel.setCcBnk("BDO");
        infoModel.setLimitCC("20000");
        infoModel.setYearS("2");
    }

    @After
    public void tearDown() throws Exception {
        infoModel = null;
    }
    @Test
    public void test_getTransNo() {
        assertEquals(transnox,infoModel.getTransNo());
    }
    @Test
    public void test_getElctX() {
        assertEquals(Double.parseDouble("1000"),infoModel.getElctX(), 0.0);
    }
    @Test
    public void test_getWaterX() {
        assertEquals(Double.parseDouble("1000"),infoModel.getWaterX(), 0.0);
    }
    @Test
    public void test_getFoodX() {
        assertEquals(Double.parseDouble("3000"),infoModel.getFoodX(), 0.0);
    }
    @Test
    public void test_getLoans() {
        assertEquals(Double.parseDouble("5000"),infoModel.getLoans(), 0.0);
    }
    @Test
    public void test_getBankN() {
        assertEquals("BDO",infoModel.getBankN());
    }
    @Test
    public void test_getStypeX() {
        assertEquals("1",infoModel.getStypeX());
    }
    @Test
    public void test_getCcBnk() {
        assertEquals("BDO",infoModel.getCcBnk());
    }
    @Test
    public void test_getLimitCC() {
        assertEquals(Double.parseDouble("20000"),infoModel.getLimitCC(), 0.0);
    }
    @Test
    public void test_getYearS() {
        assertEquals(Integer.parseInt("2"),infoModel.getYearS());
    }
    @Test
    public void test_isDataValid() {
        assertTrue(infoModel.isDataValid());
    }
}