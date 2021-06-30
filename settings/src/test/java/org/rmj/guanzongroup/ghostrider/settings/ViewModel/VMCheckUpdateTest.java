/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.settings
 * Electronic Personnel Access Control Security System
 * project file created : 6/28/21 4:03 PM
 * project file last modified : 6/28/21 4:03 PM
 */

package org.rmj.guanzongroup.ghostrider.settings.ViewModel;

import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import static org.junit.Assert.*;

public class VMCheckUpdateTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void checkUpdate() {
        BigDecimal total = BigDecimal.valueOf(99129450);
        BigDecimal bps = BigDecimal.valueOf(3304315);
        int process = 29;
        for(int x = 0; x < process; x++){
            bps = bps.add(BigDecimal.valueOf(3304315));
            System.out.println(bps);
            BigDecimal percent = percentage(bps, total).divide(BigDecimal.ONE, 2, RoundingMode.HALF_UP);
            System.out.println(percent+"%");
        }
        assertEquals(BigDecimal.valueOf(99129450), total);
    }

    @Test
    public void downloadUpdate() {
        BigDecimal fileLenght = BigDecimal.valueOf(99129450.0);
        BigDecimal lbTotal;
        int progress = 0;
        for(int x = 0; x < 30; x++){
            progress = progress + 3304315;
            lbTotal = BigDecimal.valueOf(progress);
            lbTotal = lbTotal.divide(fileLenght);
            BigDecimal lnProgress = lbTotal.divide(ONE_HUNDRED);
            System.out.println(lnProgress.intValue()+"%");
        }
    }

    public static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

    public static BigDecimal percentage(BigDecimal base, BigDecimal pct){
        return base.divide(pct, 2, RoundingMode.HALF_UP);
    }
}