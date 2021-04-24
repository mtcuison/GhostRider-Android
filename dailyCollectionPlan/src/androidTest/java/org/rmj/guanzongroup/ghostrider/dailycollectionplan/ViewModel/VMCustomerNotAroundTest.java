/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.dailyCollectionPlan
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.app.Application;

import androidx.lifecycle.Observer;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.GRider.Database.Repositories.RTown;

import java.util.List;

import static org.junit.Assert.*;

public class VMCustomerNotAroundTest {

    RTown poTown;

    @Before
    public void setUp() throws Exception {
        poTown = new RTown((Application) InstrumentationRegistry.getInstrumentation().getContext());
    }

    @Test
    public void getTownProvinceInfo() {
        poTown.getTownProvinceInfo().observe(null, new Observer<List<DTownInfo.TownProvinceInfo>>() {
            @Override
            public void onChanged(List<DTownInfo.TownProvinceInfo> townProvinceInfos) {

            }
        });
    }
}