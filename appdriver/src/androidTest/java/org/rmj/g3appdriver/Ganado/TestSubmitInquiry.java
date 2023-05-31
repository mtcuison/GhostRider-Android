package org.rmj.g3appdriver.Ganado;

import static org.junit.Assert.assertTrue;

import android.app.Application;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.lib.Ganado.Obj.Ganado;
import org.rmj.g3appdriver.lib.Ganado.pojo.InquiryInfo;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestSubmitInquiry {
    private static final String TAG = TestSubmitInquiry.class.getSimpleName();

    private Application instance;
    private Ganado poSys;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
        instance = ApplicationProvider.getApplicationContext();
        poSys = new Ganado(instance);
    }

    @Test
    public void test01CreateInquiry() {
        boolean isSuccess = false;
        InquiryInfo loInfo = new InquiryInfo();

        String lsResult = poSys.CreateInquiry(loInfo);

        if(lsResult == null){
            Log.d(TAG, poSys.getMessage());
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }
}
