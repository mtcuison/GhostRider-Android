package org.rmj.g3appdriver.EmployeeLoan;

import static org.junit.Assert.assertTrue;

import android.app.Application;
import android.util.Log;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.lib.Account.EmployeeMaster;
import org.rmj.g3appdriver.lib.EmployeeLoan.Obj.Contingency;
import org.rmj.g3appdriver.lib.EmployeeLoan.model.LoanApplication;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class ContingencyTest {
    private static final String TAG = ContingencyTest.class.getSimpleName();

    private Application instance;

    private Contingency poSys;

    private EmployeeMaster poUser;

    private boolean isSuccess = false;

    @Before
    public void setUp() throws Exception {
        Log.d(TAG, "Test started!");
        instance = ApplicationProvider.getApplicationContext();
        poSys = new Contingency(instance);
        poUser = new EmployeeMaster(instance);
        AppConfigPreference.getInstance(instance).setTestCase(true);
    }

    @Test
    public void test00LoginAccount() {
        if(poUser.IsSessionValid()){
            isSuccess = true;
            Log.e(TAG, "Session valid");
        } else if(!poUser.AuthenticateUser(new EmployeeMaster.UserAuthInfo("mikegarcia8748@gmail.com", "123456", "09171870011"))){
            Log.e(TAG, poUser.getMessage());
        } else {
            isSuccess = true;
        }
        assertTrue(isSuccess);
    }

    @Test
    public void test01ValidateEntry() {
        LoanApplication loLoan = new LoanApplication();
        loLoan.setLoanIDxx("11001");
        loLoan.setLoanType("MC LOAN");
        loLoan.setAmountxx(70000);
        loLoan.setInterest(15000);
        loLoan.setLoanTerm(36);
        if(loLoan.isDataValid()){
            isSuccess = true;
            Log.d(TAG, "Loan application is valid");
        } else {
            Log.d(TAG, loLoan.getMessage());
        }
        assertTrue(isSuccess);
        isSuccess = false;
    }

    @Test
    public void test02() {
        LoanApplication loLoan = new LoanApplication();
        loLoan.setLoanIDxx("11001");
        loLoan.setLoanType("MC LOAN");
        loLoan.setAmountxx(70000);
        loLoan.setInterest(15000);
        loLoan.setLoanTerm(36);
        if(poSys.SaveApplication(loLoan)){
           isSuccess = true;
            Log.d(TAG, "Loan application saved!");
        } else {
            Log.d(TAG, poSys.getMessage());
        }
        assertTrue(isSuccess);
        isSuccess = false;
    }
}
