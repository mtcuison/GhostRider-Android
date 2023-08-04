/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.authLibrary
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.petmanager;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertEquals;

import android.app.Application;
import android.content.Context;
import android.view.View;
import android.widget.ListView;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.lib.Account.AccountMaster;
import org.rmj.g3appdriver.lib.Account.Model.Auth;
import org.rmj.g3appdriver.lib.Account.Model.iAuth;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestLog {
    private static boolean isSuccess = false;
    private String sLeaveType, sPassword, sMobileNo;
    private View cAgree;
    private Application instance;

    private AccountMaster poAccount;
    private iAuth poSys;

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("org.rmj.guanzongroup.petmanager.test", appContext.getPackageName());

    }
    @Rule
    public ActivityTestRule<TestActivity> activityTestRule =
            new ActivityTestRule<>(TestActivity.class);
    @Rule
    public ActivityScenarioRule<TestActivity> activityRule =
            new ActivityScenarioRule<>(TestActivity.class);
    //
//    @Before
//    public void setup() {
//        // Initialize Intents for monitoring intents
//        Intents.init();
//
//    }
    @Before
    public void setUp() throws Exception {
        this.instance = ApplicationProvider.getApplicationContext();
        AppConfigPreference.getInstance(instance).setProductID("GuanzonApp");
        AppConfigPreference.getInstance(instance).setTestCase(true);
        this.poAccount = new AccountMaster(instance);
        this.poSys = poAccount.initGuanzonApp().getInstance(Auth.AUTHENTICATE);
    }

    @After
    public void tearDown() {
        // Release Intents after the test
        Intents.release();
    }

    @Test
    public void testSuccessApplicaiton() {
        sLeaveType = "Birthday";
        sPassword = "123456";
        sMobileNo = "09123654789";

        // Enter valid username and password
        onView(withId(R.id.spn_leaveType))
                .perform(ViewActions.typeText(sLeaveType));
        try {
            Thread.sleep(1000); // Wait for 1 second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onData(ViewMatchers.withText(sLeaveType))
                .inAdapterView(ViewMatchers.isAssignableFrom(ListView.class))
                .atPosition(0)
                .perform(ViewActions.click());
        onView(withId(R.id.spn_leaveType))
                .check(ViewAssertions.matches(ViewMatchers.withText(sLeaveType)));

//        if (isViewDisplayed(true)) {
//            onView(withId(R.id.tie_loginMobileNo))
//                    .perform(ViewActions.typeText(sMobileNo), ViewActions.closeSoftKeyboard());
//        }
//        if (ischeckboxnotChecked(true)) {
//            onView(withId(R.id.cbAgree))
//                    .perform(ViewActions.click());
//        }
//
//        // Perform login by clicking the login button
        onView(withId(R.id.btn_submit))
                .perform(ViewActions.click());



//        if (isLoginSuccess(true)) {
//            Log.e("TEEJEI", "Success!");
//        } else {
//            Log.e("TEEJEI", "failed!");
//        }
    }

//    private boolean isViewDisplayed(boolean isSuccessx) {
//        ViewInteraction txtLogMobile = onView(ViewMatchers.withId(R.id.tie_loginMobileNo));
//        if (txtLogMobile.equals(matches(not(isDisplayed())))) {
//            isSuccessx = true;
//        } else {
//            isSuccessx = false;
//
//        }
//        return isSuccessx;
//    }
//
//    private boolean ischeckboxnotChecked(boolean isSuccessx) {
//        ViewInteraction checkBox = onView(ViewMatchers.withId(R.id.cbAgree));
//        if (checkBox.equals(matches(isNotChecked()))) {
//            isSuccessx = true;
//        } else {
//            isSuccessx = false;
//        }
//        return isSuccessx;
//    }
//    private boolean isLoginSuccess(boolean isSuccessx) {
//        String expectedMessage = "GhostRider";
//        String expectedMessagex = "Login Success!";
//        try {
//
////            onView(withText(expectedMessage))
////                    .check(matches(isDisplayed()));
////            onView(withId(R.id.lbl_dialogTitle))
////                    .check(matches(withText(expectedMessage)));
////            Thread.sleep(1000);
//
////            onView(withText(expectedMessage)).inRoot(ToastMatcher.isToast()).check(matches(isDisplayed()));
//            isSuccessx = true;
//        } catch (Exception e){
//            isSuccessx = false;
//        }
//        return isSuccessx;
//    }
}
