/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.notifications
 * Electronic Personnel Access Control Security System
 * project file created : 8/25/21 11:54 AM
 * project file last modified : 8/25/21 11:54 AM
 */

package org.rmj.guanzongroup.ghostrider.notifications.Fragment;

import android.text.SpannableString;
import android.text.style.URLSpan;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class Fragment_ViewNotificationTest {

    CharSequence lsMessage = "To download you payslip for the period 08/01/2021 - 08/15/2021 please click the following URL: \n" +
            "\n" +
            "\n" +
            "\n" +
            "http://gts1.guanzongroup.com.ph:2007/repl/misc/download_ps.php?period=TTAwMTIwMDk=&client=TTA2MzExMDAwNDcw";

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testSpannable() throws Exception {
        SpannableString current = SpannableString.valueOf(lsMessage);
        URLSpan[] spans = current.getSpans(0, current.length(), URLSpan.class);

        assertNotEquals(0, spans.length);
//        for (URLSpan span : spans) {
//            int start = current.getSpanStart(span);
//            int end = current.getSpanEnd(span);
//            current.removeSpan(span);
//            current.setSpan(new Fragment_ViewNotification.MyUrlSpan(span.getURL(), mViewModel), start, end, 0);
//        }
    }
}