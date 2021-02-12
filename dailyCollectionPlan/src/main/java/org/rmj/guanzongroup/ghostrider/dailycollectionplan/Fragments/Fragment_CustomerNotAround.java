package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_Transaction;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

public class Fragment_CustomerNotAround extends Fragment {
    private MessageBox poMessage;
    private TextView lblBranch, lblAddress, lblAccNo, lblClientNm, lblTransNo, lblClientAddress;
    private RadioGroup rg_CNA_Input;
    private TextInputEditText txtContact, txtHouseNox, txtAddress, txtTown, txtBrgy;
    private MaterialButton btnAdd, btnCommit, btnSubmit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customer_not_around_fragment, container, false);

        poMessage = new MessageBox(getActivity());
        initWidgets(view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void initWidgets(View v){
        lblBranch = v.findViewById(R.id.lbl_headerBranch);
        lblAddress = v.findViewById(R.id.lbl_headerAddress);
        lblAccNo = v.findViewById(R.id.lbl_dcpAccNo);
        lblClientNm = v.findViewById(R.id.lbl_dcpClientNm);
        lblTransNo = v.findViewById(R.id.lbl_dcpTransNo);
        rg_CNA_Input = v.findViewById(R.id.rg_CnaInput);

        txtContact = v.findViewById(R.id.txt_dcpCNA_contactNox);
        txtHouseNox = v.findViewById(R.id.txt_houseNox);
        txtAddress = v.findViewById(R.id.txt_address);
        txtTown = v.findViewById(R.id.txt_dcpCNA_town);
        txtBrgy = v.findViewById(R.id.txt_dcpCNA_brgy);
    }
}