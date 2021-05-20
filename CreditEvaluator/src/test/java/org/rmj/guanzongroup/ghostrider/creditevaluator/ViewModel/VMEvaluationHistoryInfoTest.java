/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.CreditEvaluator
 * Electronic Personnel Access Control Security System
 * project file created : 5/20/21 10:56 AM
 * project file last modified : 5/20/21 10:56 AM
 */

package org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel;

import android.app.Application;
import android.os.Build;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.rmj.guanzongroup.ghostrider.creditevaluator.CiTestConstants.APPLICATION;
import static org.rmj.guanzongroup.ghostrider.creditevaluator.CiTestConstants.TRANSACTION_NO;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest=Config.NONE)
public class VMEvaluationHistoryInfoTest extends TestCase {
    private VMEvaluationHistoryInfo mViewModel;
    private boolean bTransNox;

    @Before
    public void setUp() {
        mViewModel = new VMEvaluationHistoryInfo(APPLICATION);
        bTransNox = mViewModel.setTransNo(TRANSACTION_NO);
    }

    @Test
    public void testSetTransNo() {
        assertTrue(bTransNox);
    }

    @Test
    public void testGetAllDoneCiInfo() {
        mViewModel.getAllDoneCiInfo().observeForever(eciEvaluation -> assertNotNull(eciEvaluation));
    }

    @Test
    public void testOnFetchCreditEvaluationDetail() {
        mViewModel.getAllDoneCiInfo().observeForever(eciEvaluation -> {
            int lnSize = mViewModel.onFetchCreditEvaluationDetail(eciEvaluation).size();
            assertTrue(lnSize > 0);
        });
    }

    @After
    public void tearDown() {
        mViewModel = null;
    }

}
