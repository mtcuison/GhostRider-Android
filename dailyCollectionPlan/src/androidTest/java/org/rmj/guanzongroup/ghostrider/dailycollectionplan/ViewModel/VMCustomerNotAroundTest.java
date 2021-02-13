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