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

import android.content.Context;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rmj.guanzongroup.authlibrary.Activity.Activity_Authenticate;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExampleInstrumentedTest {

    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("org.rmj.guanzongroup.authlibrary.test", appContext.getPackageName());
    }

    @Rule
    public ActivityScenarioRule<Activity_Authenticate> activityRule =
            new ActivityScenarioRule<>(Activity_Authenticate.class);

    @Test
    public void TypeEmail(){
        onView(withId(R.id.tie_loginEmail))
                .perform(typeText("mikegarcia8748@gmail.com"));
        closeSoftKeyboard();
    }

    @Test
    public void TypePassword(){
        onView(withId(R.id.tie_loginPassword))
                .perform(typeText("12345678"));
        closeSoftKeyboard();
    }

    @Test
    public void Login() {
        onView(withId(R.id.btn_login))
                .perform(click());
    }
}