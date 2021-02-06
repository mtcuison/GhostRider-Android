package org.rmj.guanzongroup.onlinecreditapplication.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.guanzongroup.onlinecreditapplication.R;
import org.rmj.guanzongroup.onlinecreditapplication.ViewModel.VMFinance;

public class Fragment_Finance extends Fragment {
    private static final String TAG = Fragment_Finance.class.getSimpleName();
    private VMFinance mViewModel;

    private Spinner spnRelation;
    private TextInputEditText txtFNamex,
                                txtFIncme,
                                txtFMoble,
                                txtFFacbk,
                                txtFEmail;

    private AutoCompleteTextView txtFCntry;

    public static Fragment_Finance newInstance() {
        return new Fragment_Finance();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_finance, container, false);

        spnRelation = view.findViewById(R.id.spn_financierRelation);
        txtFNamex = view.findViewById(R.id.txt_financierName);
        txtFIncme = view.findViewById(R.id.txt_financierInc);
        txtFCntry = view.findViewById(R.id.txt_financierCntry);
        txtFMoble = view.findViewById(R.id.txt_financierContact);
        txtFFacbk = view.findViewById(R.id.txt_financierFb);
        txtFEmail = view.findViewById(R.id.txt_financierEmail);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(VMFinance.class);
        mViewModel.getCountryNameList().observe(getViewLifecycleOwner(), strings -> {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, strings);
            txtFCntry.setAdapter(adapter);
        });

        class OnSpinnerItemSelectListener implements AdapterView.OnItemSelectedListener{

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i == 0){

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        }
    }

}