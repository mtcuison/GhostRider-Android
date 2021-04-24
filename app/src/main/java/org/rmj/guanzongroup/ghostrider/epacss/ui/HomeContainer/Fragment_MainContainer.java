package org.rmj.guanzongroup.ghostrider.epacss.ui.HomeContainer;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeInfo;
import org.rmj.guanzongroup.ghostrider.epacss.R;
import org.rmj.guanzongroup.ghostrider.epacss.ViewModel.VMMainContainer;
import org.rmj.guanzongroup.ghostrider.epacss.ui.etc.appConstants;
import org.rmj.guanzongroup.onlinecreditapplication.Adapter.FragmentAdapter;

public class Fragment_MainContainer extends Fragment {

    private VMMainContainer mViewModel;

    private static Fragment_MainContainer instance;
    private static ViewPager viewPager;
    private String transNox;
    public static Fragment_MainContainer newInstance() {
        return new Fragment_MainContainer();
    }

    public static Fragment_MainContainer getInstance(){
        return instance;
    }

    public String getTransNox(){
        return transNox;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_container, container, false);
        instance = this;
        viewPager = view.findViewById(R.id.viewpager_mainContainer);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMMainContainer.class);

        mViewModel.getEmployeeInfo().observe(getViewLifecycleOwner(), eEmployeeInfo -> {
            try {
                viewPager.setAdapter(new FragmentAdapter(getParentFragmentManager(), appConstants.getHomePages(Integer.parseInt(eEmployeeInfo.getEmpLevID()))));
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }
    public static void moveToPageNumber(int fnPageNum){
        viewPager.setCurrentItem(fnPageNum);
    }

}