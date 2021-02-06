package org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel;

import android.app.Application;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VMCollectionListTest {

    private Application app;
    private VMCollectionList mViewModel;

    @Before
    public void setUp() throws Exception {
        app = (Application) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        mViewModel = new VMCollectionList(app);
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void downloadDcp() {
        boolean result = mViewModel.ImportData();
        assertTrue(result);
    }
}