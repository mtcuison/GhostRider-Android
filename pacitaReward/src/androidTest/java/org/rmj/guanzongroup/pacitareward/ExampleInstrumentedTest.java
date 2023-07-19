package org.rmj.guanzongroup.pacitareward;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Context;
import android.view.View;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rmj.guanzongroup.pacitareward.Activity.Activity_BranchList;
import org.rmj.guanzongroup.pacitareward.Adapter.RecyclerViewAdapter_BranchList;
import org.rmj.guanzongroup.pacitareward.Dialog.Dialog_SelectAction;
import org.rmj.guanzongroup.pacitareward.Fragments.Fragment_BranchList;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        //validate if package is under this test
        assertEquals("org.rmj.guanzongroup.pacitareward.test", appContext.getPackageName());
    }

    @Rule
    public ActivityScenarioRule<Activity_BranchList> activityRule =
            new ActivityScenarioRule<>(Activity_BranchList.class); //launch primary activity before test
    @Rule
    public FragmentScenario fragmentScenario =
            FragmentScenario.launchInContainer(Fragment_BranchList.class);

    @Test
    public void openBranchRate(){
        onData(withId(R.id.branch_list)).inAdapterView(withId(R.id.item_branch)).perform(click());
    }
}