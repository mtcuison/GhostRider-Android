package org.rmj.g3appdriver.AuthTest;


import static org.junit.Assert.assertTrue;

import android.app.Application;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class TestLoginAccount {

    private Application instance;

    private REmployee poUser;

    @Before
    public void setup() throws Exception{
        instance = ApplicationProvider.getApplicationContext();
        poUser = new REmployee(instance);
    }

    @Test
    public void test01ValidateEntries() throws Exception{
        REmployee.UserAuthInfo loAuth = new REmployee.UserAuthInfo("mikegarcia8748@gmail.com", "123456", "09171870011");
        assertTrue(loAuth.isAuthInfoValid());
    }

    @Test
    public void test02LoginAccount() throws Exception{
        REmployee.UserAuthInfo loAuth = new REmployee.UserAuthInfo("mikegarcia8748@gmail.com", "123456", "09171870011");
        assertTrue(poUser.AuthenticateUser(loAuth));
    }
}
