package org.rmj.g3appdriver.GRider.Etc;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.rmj.g3appdriver.GCircle.Apps.BullsEye.PerformancePeriod;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class PerformancePeriodTest extends TestCase {

    public Calendar poCalendr;
    public SimpleDateFormat poFormatx;

    @Before
    public void setUp() {
        poCalendr = Calendar.getInstance(TimeZone.getTimeZone("Asia/Manila"));
        poFormatx = new SimpleDateFormat("yyyyMM");
    }

    @After
    public void tearDown() {
        poCalendr = null;
        poFormatx = null;
    }

    @Test
    public void testGetList() {
        assertEquals(12, PerformancePeriod.getList().size());
        System.out.println("getList() fetched values:" + PerformancePeriod.getList());
    }

    @Test
    public void testGetLatestCompletePeriod() {
        String lnMontCom = poCalendr.getInstance().get(Calendar.MONTH) < 10 ?
                "0" + poCalendr.getInstance().get(Calendar.MONTH)
                : String.valueOf(poCalendr.getInstance().get(Calendar.MONTH));

        int lnCurYear = poCalendr.getInstance().get(Calendar.YEAR);
        String lsPastMos = String.valueOf(lnCurYear) + lnMontCom;

        assertEquals(lsPastMos, PerformancePeriod.getLatestCompletePeriod());
        System.out.println("getLatestCompletePeriod() fetched values:"
                + PerformancePeriod.getLatestCompletePeriod());
    }


}
