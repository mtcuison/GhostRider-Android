package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DTownInfo;
import org.rmj.g3appdriver.GRider.Database.Entities.EBarangayInfo;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.AddressUpdate;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Model.MobileUpdate;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.ViewModel.VMCustomerNotAround;

import java.util.List;

public class Fragment_CustomerNotAround extends Fragment {

    private VMCustomerNotAround mViewModel;
    private AddressUpdate poAddress;
    private MobileUpdate poMobile;
    private AutoCompleteTextView txtTown, txtBrgy;

    public static Fragment_CustomerNotAround newInstance() {
        return new Fragment_CustomerNotAround();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_not_around_fragment, container, false);
        poAddress = new AddressUpdate();
        //TODO:  Initialize mobile before saving info.
        //poMobile = new MobileUpdate();

        txtTown = view.findViewById(R.id.txt_townProvince);
        txtBrgy = view.findViewById(R.id.txt_Barangay);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMCustomerNotAround.class);

        //TODO: Add RadioButton Selection Listener
        //TODO: Condition : if Mobile is Selected call mViewModel.getMobileRequestList()
        //TODO: else if Address is selected call mViewModel.getAddressRequestList()

        mViewModel.getTownProvinceInfo().observe(getViewLifecycleOwner(), townProvinceInfos -> {
            String[] townProvince = new String[townProvinceInfos.size()];
            for(int x = 0; x < townProvinceInfos.size(); x++){
                townProvince[x] = townProvinceInfos.get(x).sTownName + ", " + townProvinceInfos.get(x).sProvName;
            }
            ArrayAdapter<String> loAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, townProvince);
            txtTown.setAdapter(loAdapter);
        });

        txtTown.setOnItemClickListener((adapterView, view, i, l) -> {
            String lsTown = txtTown.getText().toString();
            String[] town = lsTown.split(", ");
            mViewModel.getTownProvinceInfo().observe(getViewLifecycleOwner(), townProvinceInfos -> {
                for(int x = 0; x < townProvinceInfos.size(); x++){
                    if(town[0].equalsIgnoreCase(townProvinceInfos.get(x).sTownName + ", " + townProvinceInfos.get(x).sProvName)){
                        poAddress.setTownID(townProvinceInfos.get(x).sTownIDxx);
                        mViewModel.setTownID(townProvinceInfos.get(x).sTownIDxx);
                        break;
                    }
                }

                mViewModel.getBarangayNameList().observe(getViewLifecycleOwner(), strings -> {
                    ArrayAdapter<String> loAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
                    txtBrgy.setAdapter(loAdapter);
                });
            });
        });

        txtBrgy.setOnItemClickListener((adapterView, view, i, l) -> mViewModel.getBarangayInfoList().observe(getViewLifecycleOwner(), eBarangayInfos -> {
            for(int x = 0; x < eBarangayInfos.size(); x++){
                if(txtBrgy.getText().toString().equalsIgnoreCase(eBarangayInfos.get(x).getBrgyName())){
                    poAddress.setBarangayID(eBarangayInfos.get(x).getBrgyIDxx());
                    break;
                }
            }
        }));

    }
}