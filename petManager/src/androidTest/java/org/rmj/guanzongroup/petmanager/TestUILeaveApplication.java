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
import static org.junit.Assert.assertEquals;

import android.app.Application;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.fragment.app.FragmentActivity;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.g3appdriver.lib.Account.AccountMaster;
import org.rmj.g3appdriver.lib.Account.Model.Auth;
import org.rmj.g3appdriver.lib.Account.Model.iAuth;
import org.rmj.guanzongroup.petmanager.Fragment.Fragment_LeaveApplication;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class TestUILeaveApplication {

        private static boolean isSuccess = false;
        private String sEmail, sPassword, sMobileNo;
        private View cAgree;
        private Application instance;

        private AccountMaster poAccount;
        private iAuth poSys;

        @Test
        public void useAppContext() {
            // Context of the app under test.
            Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
            assertEquals("org.rmj.g3appdriver.test", appContext.getPackageName());

        }
        @Rule
        public ActivityScenarioRule<FragmentActivity> activityRule = new ActivityScenarioRule<>(Fragment_LeaveApplication.class);
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

        @Test
        public void testApplication(){
            if (testLeavetype(true)){
                Log.d("System Success" , "Leave Type");
            }
        }

        @Test
        private boolean testLeavetype(boolean isSuccess) {
            String searchText = "Example";
            try {
                onView(ViewMatchers.withId(R.id.spn_leaveType))
                        .perform(ViewActions.typeText(searchText));

                // Wait for the suggestions to appear (you may need to adjust this delay as per your app's behavior)
                try {
                    Thread.sleep(1000); // Wait for 1 second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // Click on the first suggestion in the dropdown (assuming suggestions are visible now)
                onData(ViewMatchers.withText(searchText))
                        .inAdapterView(ViewMatchers.isAssignableFrom(ListView.class))
                        .atPosition(0)
                        .perform(ViewActions.click());

                // Verify if the selected suggestion is populated in the AutoCompleteTextView
                onView(ViewMatchers.withId(R.id.spn_leaveType))
                        .check(ViewAssertions.matches(ViewMatchers.withText(searchText)));

                isSuccess = true;
            }catch (Exception e){
                isSuccess=false;
            }
            return isSuccess;
        }
}
//            sEmail = "mikegarcia8748@gmail.com";
//            sPassword = "123456";
//            sMobileNo = "09123654789";
//
//            // Enter valid username and password
//            onView(withId(R.id.spn_leaveType))
//                    .perform(ViewActions.typeText(sEmail), ViewActions.closeSoftKeyboard());
//            onView(withId(R.id.tie_loginPassword))
//                    .perform(ViewActions.typeText(sPassword), ViewActions.closeSoftKeyboard());
//
//            if (isViewDisplayed(true)) {
//                onView(withId(R.id.tie_loginMobileNo))
//                        .perform(ViewActions.typeText(sMobileNo), ViewActions.closeSoftKeyboard());
//            }
//            if (ischeckboxnotChecked(true)) {
//                onView(withId(R.id.cbAgree))
//                        .perform(ViewActions.click());
//            }
//
//            // Perform login by clicking the login button
//            onView(withId(R.id.btn_login))
//                    .perform(ViewActions.click());
//
//
//
//            if (isLoginSuccess(true)) {
//                Log.e("TEEJEI", "Success!");
//            } else {
//                Log.e("TEEJEI", "failed!");
//            }
//        }
//
//        private boolean isViewDisplayed(boolean isSuccessx) {
//            ViewInteraction txtLogMobile = onView(withId(R.id.tie_loginMobileNo));
//            if (txtLogMobile.equals(matches(not(isDisplayed())))) {
//                isSuccessx = true;
//            } else {
//                isSuccessx = false;
//
//            }
//            return isSuccessx;
//        }
//
//        private boolean ischeckboxnotChecked(boolean isSuccessx) {
//            ViewInteraction checkBox = onView(withId(R.id.cbAgree));
//            if (checkBox.equals(matches(isNotChecked()))) {
//                isSuccessx = true;
//            } else {
//                isSuccessx = false;
//            }
//            return isSuccessx;
//        }
//        private boolean isLoginSuccess(boolean isSuccessx) {
//            String expectedMessage = "GhostRider";
//            String expectedMessagex = "Login Success!";
//            try {
//
////            onView(withText(expectedMessage))
////                    .check(matches(isDisplayed()));
////            onView(withId(R.id.lbl_dialogTitle))
////                    .check(matches(withText(expectedMessage)));
////            Thread.sleep(1000);
//
//                onView(withText(expectedMessage)).inRoot(ToastMatcher.isToast()).check(matches(isDisplayed()));
//                isSuccessx = true;
//            } catch (Exception e){
//                isSuccessx = false;
//            }
//            return isSuccessx;
//        }
//    }