/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.CreditEvaluator
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.creditevaluator.Model;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CIDisbursementInfoModelTest {
    private String ciDbmWater;
    private String ciDbmElectricity;
    private String ciDbmFood;
    private String ciDbmLoans;

    private String ciDbmEducation;
    private String ciDbmOthers;
    private String ciDbmTotalExpenses;
    CIDisbursementInfoModel infoModel;
    @Before
    public void setUp() throws Exception {
        infoModel = new CIDisbursementInfoModel();
        ciDbmWater = "500";
        ciDbmElectricity = "1800";
        ciDbmFood = "3000";
        ciDbmLoans = "3000";
        ciDbmEducation = "400";
        ciDbmOthers = "200";

    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void setData(){
        infoModel.setCiDbmWater(ciDbmWater);
        infoModel.setCiDbmElectricity(ciDbmElectricity);
        infoModel.setCiDbmFood(ciDbmFood);
        infoModel.setCiDbmLoans(ciDbmLoans);
        infoModel.setCiDbmOthers(ciDbmOthers);
        infoModel.setCiDbmEducation(ciDbmEducation);
        assertEquals(ciDbmWater, infoModel.getCiDbmWater());
        assertEquals(ciDbmElectricity, infoModel.getCiDbmElectricity());
        assertEquals(ciDbmFood, infoModel.getCiDbmFood());
        assertEquals(ciDbmLoans, infoModel.getCiDbmLoans());
        assertEquals(ciDbmEducation, infoModel.getCiDbmEducation());
        assertEquals(ciDbmOthers, infoModel.getCiDbmOthers());
    }
}