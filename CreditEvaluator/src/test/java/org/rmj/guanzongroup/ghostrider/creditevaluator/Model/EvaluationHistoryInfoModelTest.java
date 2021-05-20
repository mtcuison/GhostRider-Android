/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.CreditEvaluator
 * Electronic Personnel Access Control Security System
 * project file created : 5/20/21 11:46 AM
 * project file last modified : 5/20/21 11:46 AM
 */

package org.rmj.guanzongroup.ghostrider.creditevaluator.Model;

import android.view.View;

import junit.framework.TestCase;
import junit.framework.TestResult;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EvaluationHistoryInfoModelTest extends TestCase {
    private static final String FAKE_HEADER = "Fake Header";
    private static final String FAKE_LABEL = "Fake Label";
    private static final String FAKE_CONTENT = "Fake Content";
    private static final int VISIBLE = View.VISIBLE;
    private static final int GONE = View.GONE;
    private EvaluationHistoryInfoModel pmHeadxxx, pmContent;

    @Before
    public void setUp() {
        pmHeadxxx = new EvaluationHistoryInfoModel(true, FAKE_HEADER, "", "");
        pmContent = new EvaluationHistoryInfoModel(false, "", FAKE_LABEL, FAKE_CONTENT);
    }

    @Test
    public void testIsHeader() {
        assertEquals(VISIBLE, pmHeadxxx.isHeader());
    }

    @Test
    public void testIsContent() {
        assertEquals(VISIBLE, pmContent.isContent());
    }

    @Test
    public void testGetHeader() {
        assertEquals(FAKE_HEADER, pmHeadxxx.getHeader());
    }

    @Test
    public void testGetLabel() {
        assertEquals(FAKE_LABEL + " :", pmContent.getLabel());
    }

    @Test
    public void testGeContent() {
        assertEquals(FAKE_CONTENT, pmContent.getContent());
    }

    @After
    public void tearDown() {
        pmHeadxxx = null;
        pmContent = null;
    }

}
