/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.approvalCode
 * Electronic Personnel Access Control Security System
 * project file created : 10/18/21, 4:35 PM
 * project file last modified : 10/18/21, 4:35 PM
 */

package org.rmj.guanzongroup.ghostrider.approvalcode.ViewModel;

import android.os.Build;

import androidx.core.widget.TextViewCompat;
import androidx.test.core.app.ApplicationProvider;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest= Config.NONE)
public class VMApprovalSelectionTest extends TestCase {
    public VMApprovalSelection mViewModel;

    @Before
    public void setUp() {
        mViewModel = new VMApprovalSelection(ApplicationProvider.getApplicationContext());
    }

    @After
    public void tearDown() {
        mViewModel = null;
    }

    @Test
    public void testGetReferenceAuthList() {
        mViewModel.getReferenceAuthList("Req").observeForever(requests -> assertNotNull(requests));
    }

}

