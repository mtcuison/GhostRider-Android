package org.rmj.guanzongroup.onlinecreditapplication.Fragment;

import android.app.Application;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMSelfEmployedInfo;

import static org.junit.Assert.*;

public class Fragment_SelfEmployedInfoTest {

    private Application app;

    private VMSelfEmployedInfo mViewModel;

    @Before
    public void setUp() throws Exception {
        app = (Application) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        mViewModel = new VMSelfEmployedInfo(app);
    }


}