package org.rmj.guanzongroup.pacitareward;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.viewpager.widget.ViewPager;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rmj.guanzongroup.pacitareward.Activity.Activity_BranchList;
import org.rmj.guanzongroup.pacitareward.Adapter.Fragment_BranchListAdapter;
import org.rmj.guanzongroup.pacitareward.Fragments.Fragment_BranchList;
import org.rmj.guanzongroup.pacitareward.Fragments.Fragment_HistoryEval;
import org.rmj.guanzongroup.pacitareward.ViewModel.VMBranchList;

import static org.hamcrest.Matchers.allOf;
import static org.junit.Assert.*;

import com.google.android.material.tabs.TabLayout;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTestPacitaReward {
    ViewPager viewPager;
   /*@Rule
    public ActivityScenarioRule<Activity_BranchList> activityRule =
            new ActivityScenarioRule<>(Activity_BranchList.class);*/
    @Before
    public void setup(){
        FragmentScenario fragmentScenario = FragmentScenario.launchInContainer(Fragment_BranchList.class, null, R.style.GhostRiderMaterialTheme);
        fragmentScenario.moveToState(Lifecycle.State.CREATED);

        fragmentScenario.onFragment(fragment -> {
           fragment.getParentFragmentManager()
                   .beginTransaction();
        });
    }
    @Test
    public void selectBranch(){

    }
}