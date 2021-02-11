package org.rmj.guanzongroup.onlinecreditapplication.ViewModel;

import android.app.Application;

import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VMMeansInfoSelectionTest {

    private Application app;
    private VMMeansInfoSelection mViewModel;

    @Before
    public void setUp() throws Exception {
        app = (Application) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        mViewModel = new VMMeansInfoSelection(app);
    }

    @Test
    public void addMeansInfo() {
        assertTrue(mViewModel.addMeansInfo("employed"));
    }

    @Test
    public void getMeansInfoCount() {
        //assertEquals(1, mViewModel.getMeansInfoPage());
    }

    @Test
    public void removeMeansInfo() {
        assertTrue(mViewModel.removeMeansInfo("employed"));
    }
}