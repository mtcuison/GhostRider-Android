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

import static androidx.test.espresso.Espresso.closeSoftKeyboard;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.isDialog;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isNotChecked;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.Context;

import androidx.test.espresso.FailureHandler;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rmj.guanzongroup.authlibrary.Activity.Activity_Authenticate;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExampleInstrumentedTest {

    @Rule //runs activity by default before test
    public ActivityScenarioRule<Activity_Authenticate> activityRule =
            new ActivityScenarioRule<>(Activity_Authenticate.class);
    @Test
    public void testshowMobileInput() throws RuntimeException{
        onView(withId(R.id.tie_loginMobileNo))
                .check(matches(isDisplayed()));
    }
    @Test
    public void fillUp(){

        onView(withId(R.id.tie_loginEmail)) //onView -> gets UI view from current class, withId -> finds object from view through Id
                .check(matches(isDisplayed())) //.check -> perform object validation, matches -> validate obj, isDisplayed -> test visibility
                .perform(typeText("ggutoman@gmail.com")); //.perform -> create object action, typeText -> type text on object
        closeSoftKeyboard(); //close keyboard after typing

        onView(withId(R.id.tie_loginPassword))
                .check(matches(isDisplayed()))
                .perform(typeText("ggutoman1998"));
        closeSoftKeyboard();

        onView(withId(R.id.tie_loginMobileNo))
                .check(matches(isDisplayed()))
                .perform(typeText("09993095066"));
        closeSoftKeyboard();

        onView(withId(R.id.cbAgree))
                .check(matches(isChecked())) //isChecked -> test object check state
                .perform(click()) //click -> clicks object
                .check(matches(isNotChecked())) //isNotChecked -> test object uncheck state
                .perform(click());

        onView(withId(R.id.btn_login))
                .check(matches(isDisplayed()))
                .perform(click());

        onView(withText("Okay"))
                .check(matches(not(isDisplayed())))
                .noActivity()
                .check(matches(isDisplayed()))
                .inRoot(isDialog()) //.inRoot -> access parent view, isDialog -> test object if dialog
                .perform(click());
    }
}