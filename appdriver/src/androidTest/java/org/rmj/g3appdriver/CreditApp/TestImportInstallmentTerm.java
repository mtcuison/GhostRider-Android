package org.rmj.g3appdriver.CreditApp;

import static org.junit.Assert.assertTrue;

import android.app.Application;
import android.util.Log;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.Observer;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.GCircle.room.Entities.ELoanTerm;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.GCircle.Account.EmployeeMaster;
import org.rmj.g3appdriver.GCircle.Apps.CreditApp.CreditOnlineApplication;

import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestImportInstallmentTerm {
    private static final String TAG = TestImportInstallmentTerm.class.getSimpleName();

    private Application instance;

    private EmployeeMaster poUser;
    private CreditOnlineApplication poSys;

    private boolean isSuccess = false;
    private static String message;


    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    @Before
    public void setUp() throws Exception {
        instance = (Application) InstrumentationRegistry.getInstrumentation().getTargetContext().getApplicationContext();
        AppConfigPreference.getInstance(instance).setTestCase(true);
        poUser = new EmployeeMaster(instance);
        poSys = new CreditOnlineApplication(instance);

        EmployeeMaster.UserAuthInfo loAuth = new EmployeeMaster.UserAuthInfo("mikegarcia8748@gmail.com", "123456", "09171870011");
        assertTrue(poUser.AuthenticateUser(loAuth));
    }

    @Test
    public void test01ImportTerms() {
        if(!poSys.ImportInstallmentTerms()){
            Log.e(TAG, poSys.getMessage());
        } else {
            isSuccess = true;
        }

        assertTrue(isSuccess);
    }

    @Test
    public void test02RetrieveLoanTerms() {
        poSys.GetLoanTerms().observeForever(new Observer<List<ELoanTerm>>() {
            @Override
            public void onChanged(List<ELoanTerm> eLoanTerms) {
                if(eLoanTerms == null){
                    Log.e(TAG, "No record found");
                }

                if(eLoanTerms.size() == 0){
                    Log.e(TAG, "No record found");
                }

                for(int x = 0; x < eLoanTerms.size(); x++){
                    Log.d(TAG, eLoanTerms.get(x).getTermCode() + ", " + eLoanTerms.get(x).getTermDesc());
                }
            }
        });
    }
}
