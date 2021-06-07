/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dailyCollectionPlan
 * Electronic Personnel Access Control Security System
 * project file created : 6/4/21 2:25 PM
 * project file last modified : 6/4/21 2:25 PM
 */

package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities;

import android.os.Build;

import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCP_Remittance;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMCollectionRemittance;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMLoanUnit;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Objects;

import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest=Config.NONE)
public class Activity_CollectionRemittanceTest {
    private String psCltCashx = "0.0", psCltCheck = "0.0";
    private String totalAmnt = "5,000";
    private String remitType, branch = "GMC Dagupan - Honda", amount = "";
    VMCollectionRemittance mViewModel;
    @Mock
    Activity_CollectionRemittance activity;
    private boolean isCheck = false;
    private EDCP_Remittance poRemit;
    @Before
    public void setUp() throws Exception {
        poRemit = new EDCP_Remittance();
        remitType = "0";
        mViewModel = new VMCollectionRemittance(ApplicationProvider.getApplicationContext());

    }

    @After
    public void tearDown() throws Exception {
        Assert.assertTrue(activity.isDataValid());
    }

    @Test
    public void test_isValidData(){

    }

}