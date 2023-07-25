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

package org.rmj.guanzongroup.authlibrary;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertEquals;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
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
import org.rmj.guanzongroup.authlibrary.Activity.Activity_Authenticate;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class tesLogin {
    private static boolean isSuccess = false;
    private String sEmail, sPassword, sMobileNo;
    private View cAgree;

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("org.rmj.guanzongroup.authlibrary.test", appContext.getPackageName());

    }
    @Rule
    public ActivityTestRule<Activity_Authenticate> activityTestRule =
            new ActivityTestRule<>(Activity_Authenticate.class);
    @Rule
    public ActivityScenarioRule<Activity_Authenticate> activityRule =
            new ActivityScenarioRule<>(Activity_Authenticate.class);

    @Before
    public void setup() {
        // Initialize Intents for monitoring intents
        Intents.init();
    }

    @After
    public void tearDown() {
        // Release Intents after the test
        Intents.release();
    }

    @Test
    public void testSuccessfulLogin() {
        sEmail = "mikegarcia8748@gmail.com";
        sPassword = "123456";
        sMobileNo = "09123654789";

        // Enter valid username and password
        onView(withId(R.id.tie_loginEmail))
                .perform(ViewActions.typeText(sEmail), ViewActions.closeSoftKeyboard());
        onView(withId(R.id.tie_loginPassword))
                .perform(ViewActions.typeText(sPassword), ViewActions.closeSoftKeyboard());

        if (isViewDisplayed(true)) {
            onView(withId(R.id.tie_loginMobileNo))
                    .perform(ViewActions.typeText(sMobileNo), ViewActions.closeSoftKeyboard());
        }
        if (ischeckboxnotChecked(true)) {
            onView(withId(R.id.cbAgree))
                    .perform(ViewActions.click());
        }

        // Perform login by clicking the login button
        onView(withId(R.id.btn_login))
                .perform(ViewActions.click());



        if (isLoginSuccess(true)) {
            Log.e("TEEJEI", "Success!");
        } else {
            Log.e("TEEJEI", "failed!");
        }
    }

    private boolean isViewDisplayed(boolean isSuccessx) {
        ViewInteraction txtLogMobile = onView(ViewMatchers.withId(R.id.tie_loginMobileNo));
        if (txtLogMobile.equals(matches(not(isDisplayed())))) {
            isSuccessx = true;
        } else {
            isSuccessx = false;

        }
        return isSuccessx;
    }

    private boolean ischeckboxnotChecked(boolean isSuccessx) {
        ViewInteraction checkBox = onView(ViewMatchers.withId(R.id.cbAgree));
        if (checkBox.equals(matches(isNotChecked()))) {
            isSuccessx = true;
        } else {
            isSuccessx = false;
        }
        return isSuccessx;
    }
    private boolean isLoginSuccess(boolean isSuccessx) {
        String expectedMessage = "GhostRider";
        String expectedMessagex = "Login Success!";
        try {

//            onView(withText(expectedMessage))
//                    .check(matches(isDisplayed()));
            onView(withId(R.id.lbl_dialogTitle))
                    .check(matches(withText(expectedMessage)));
//            Thread.sleep(1000);

//            onView(withText(expectedMessage)).inRoot(ToastMatcher.isToast()).check(matches(isDisplayed()));
            isSuccessx = true;
        } catch (Exception e){
            isSuccessx = false;
        }
       return isSuccessx;
    }
}
