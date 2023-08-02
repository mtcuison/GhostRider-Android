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

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.filters.LargeTest;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rmj.guanzongroup.authlibrary.Activity.Activity_Authenticate;

import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

import junit.framework.AssertionFailedError;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExampleInstrumentedTestAuthLib {

    @Rule //runs activity by default before test
    public ActivityScenarioRule<Activity_Authenticate> activityRule =
            new ActivityScenarioRule<>(Activity_Authenticate.class);

    @Test
    public void fillUp(){
        //onView -> gets UI view from current class, withId -> finds object from view through Id
        //.perform -> create object action, typeText -> type text on object
        //closeSoftKeyboard() -> close keyboard after typing

        if (isObjectDisplayed(onView(withId(R.id.tie_loginEmail))) == true){
            onView(withId(R.id.tie_loginEmail)).perform(typeText("ggutoman@gmail.com"));
            closeSoftKeyboard();
        }

        if (isObjectDisplayed(onView(withId(R.id.tie_loginPassword))) == true){
            onView(withId(R.id.tie_loginPassword)).perform(typeText("ggutoman1998"));
            closeSoftKeyboard();
        }

        if (isObjectDisplayed(onView(withId(R.id.tie_loginMobileNo))) == true){
            onView(withId(R.id.tie_loginMobileNo)).perform(typeText("09993095066"));
            closeSoftKeyboard();
        }

        if (isObjectChecked(onView(withId(R.id.cbAgree))) == true){
            onView(withId(R.id.cbAgree)).perform(click());
        }

        if (isObjectDisplayed(onView(withId(R.id.btn_login))) == true){
            onView(withId(R.id.btn_login)).perform(click());
        }

        if (isObjectDisplayed(onView(withText("Okay"))) == true && isObjectDialog(onView(withText("Okay"))) == true){
            onView(withText("Okay")).perform(click());
        }
    }

    //assertEquals -> matches two params, not -> expecting opposite result
    //.check -> perform object validation, matches -> validate obj, isDisplayed -> test visibility
    //.inRoot -> gets the object scope unto the parent view, isDialog -> validates object if dialog
    private Boolean isObjectDisplayed(ViewInteraction obj){
        try{
            assertEquals(obj.check(matches(isDisplayed())), obj.check(matches(not(isDisplayed()))));
            return true;
        }catch (NoMatchingViewException | AssertionFailedError e){
            e.printStackTrace();
            return false;
        }
    }
    private Boolean isObjectChecked(ViewInteraction obj){
        try{
           assertEquals(obj.check(matches(isChecked())), obj.check(matches(isNotChecked())));
            return true;
        }catch (NoMatchingViewException | AssertionFailedError e){
            e.printStackTrace();
            return false;
        }
    }
    private Boolean isObjectDialog(ViewInteraction obj){
        try{
            assertEquals(obj.inRoot(isDialog()), obj.inRoot(not(isDialog())));
            return true;
        }catch (NoMatchingViewException | AssertionFailedError e){
            e.printStackTrace();
            return false;
        }
    }
}