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
import org.rmj.g3appdriver.GCircle.room.Entities.ERelation;
import org.rmj.g3appdriver.lib.Account.Model.iAuth;
import org.rmj.g3appdriver.lib.Etc.Relation;

import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestImportRelation {
    private static final String TAG = TestImportRelation.class.getSimpleName();

    private Application instance;

    private iAuth poAuth;
    private Relation poSys;

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
        instance = ApplicationProvider.getApplicationContext();
        poSys = new Relation(instance);
    }

    @Test
    public void test01ImportData() {
        boolean isSuccess = false;
        if(!poSys.ImportRelations()){
            Log.e(TAG, poSys.getMessage());
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test02GetRecords() {
        poSys.GetRelations().observeForever(new Observer<List<ERelation>>() {
            @Override
            public void onChanged(List<ERelation> eRelations) {
                if(eRelations == null){
                    return;
                }

                if(eRelations.size() == 0){
                    return;
                }

                for(int x = 0; x < eRelations.size(); x++){
                    Log.d(TAG, eRelations.get(x).getRelatnDs());
                }
            }
        });
    }
}
