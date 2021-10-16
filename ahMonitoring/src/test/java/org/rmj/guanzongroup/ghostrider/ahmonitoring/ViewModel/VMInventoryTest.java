/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 10/16/21, 2:35 PM
 * project file last modified : 10/16/21, 2:35 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel;

import android.os.Build;

import androidx.lifecycle.Observer;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.RandomItem;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = {Build.VERSION_CODES.O_MR1}, manifest= Config.NONE)
public class VMInventoryTest {
    private VMInventory mViewModel;

    @Before
    public void setUp() {
        mViewModel = new VMInventory(ApplicationProvider.getApplicationContext());
    }

    @After
    public void tearDown() {
        mViewModel = null;
    }

    @Test
    public void testGetRandomItemList() {
        mViewModel.getRandomItemList().observeForever(new Observer<List<RandomItem>>() {
            @Override
            public void onChanged(List<RandomItem> randomItems) {
                Assert.assertNotNull(randomItems);
            }
        });
    }
}
