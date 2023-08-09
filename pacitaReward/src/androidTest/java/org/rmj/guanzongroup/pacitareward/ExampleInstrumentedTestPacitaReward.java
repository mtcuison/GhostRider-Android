package org.rmj.guanzongroup.pacitareward;

import static org.junit.Assert.assertTrue;

import androidx.fragment.app.testing.FragmentScenario;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.ViewModelFactoryDsl;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.rmj.g3appdriver.GCircle.room.Entities.EBranchInfo;
import org.rmj.g3appdriver.etc.AppConfigPreference;
import org.rmj.guanzongroup.pacitareward.Fragments.Fragment_BranchList;
import org.rmj.guanzongroup.pacitareward.ViewModel.VMBranchList;


import java.util.List;

import javax.inject.Inject;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTestPacitaReward {
    FragmentScenario fragmentScenario;
    VMBranchList mviewModel;
    @Before
    public void setup() throws Exception{
        AppConfigPreference.getInstance(InstrumentationRegistry.getInstrumentation().getTargetContext()).setTestCase(true);

        mviewModel = ViewModelProvider.AndroidViewModelFactory.getInstance(ApplicationProvider.getApplicationContext()).create(VMBranchList.class);
        mviewModel.importCriteria();

        fragmentScenario = FragmentScenario.launchInContainer(Fragment_BranchList.class, null, R.style.GhostRiderMaterialTheme);
    }
    @Test
    public void getBranchList(){
       fragmentScenario.onFragment(fragment -> {
           mviewModel.getBranchlist().observe(fragment.getViewLifecycleOwner(), new Observer<List<EBranchInfo>>() {
               @Override
               public void onChanged(List<EBranchInfo> eBranchInfos) {
                   assertTrue("List size is " + eBranchInfos.size(), eBranchInfos.size() > 0);
               }
           });
       });
    }
}