package org.rmj.g3appdriver.Ganado;


import static org.junit.Assert.assertTrue;

import android.app.Application;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.GCircle.room.Entities.EGanadoOnline;
import org.rmj.g3appdriver.lib.Ganado.Obj.Ganado;

import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestImportInquiries {
    private static final String TAG = TestImportInquiries.class.getSimpleName();

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
    public void test01ImportInquiries() {
        boolean isSuccess = false;
        if(poSys.ImportInquiries()){
            isSuccess = true;
        } else {
            Log.e(TAG, poSys.getMessage());
        }

        assertTrue(isSuccess);
    }

    @Test
    public void test02RetrieveInquiries() {
        final boolean[] isSuccess = {false};
        poSys.GetInquiries().observeForever(new Observer<List<EGanadoOnline>>() {
            @Override
            public void onChanged(List<EGanadoOnline> eGanadoOnlines) {
                if(eGanadoOnlines == null){
                    return;
                }

                if(eGanadoOnlines.size() == 0){
                    return;
                }

                for(int x = 0; x < eGanadoOnlines.size(); x++){
                    Log.d(TAG, eGanadoOnlines.get(x).getTransNox());
                }
                isSuccess[0] = true;
            }
        });

        assertTrue(isSuccess[0]);
    }
}
