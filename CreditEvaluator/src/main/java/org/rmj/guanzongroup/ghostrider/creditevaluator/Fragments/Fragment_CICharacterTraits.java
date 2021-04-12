package org.rmj.guanzongroup.ghostrider.creditevaluator.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.rmj.guanzongroup.ghostrider.creditevaluator.R;

public class Fragment_CICharacterTraits extends Fragment {

//    private VMCICharacterTraits mViewModel;

    public static Fragment_CICharacterTraits newInstance() {
        return new Fragment_CICharacterTraits();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ci_character_traits, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        mViewModel = new ViewModelProvider(this).get(VMCICharacterTraits.class);
        // TODO: Use the ViewModel
    }

}