package org.rmj.guanzongroup.ghostrider.cashcount;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class Fragment_CashCounter extends Fragment {

    private VMCashCounter mViewModel;

    private EditText txtQty;
    private EditText txtTtl;

    public static Fragment_CashCounter newInstance() {
        return new Fragment_CashCounter();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cash_counter, container, false);

        txtQty = view.findViewById(R.id.txtQuantity);
        txtTtl = view.findViewById(R.id.txtTotal);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMCashCounter.class);
        txtQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                txtQty.removeTextChangedListener(this);
                if(txtQty.getText().toString().equalsIgnoreCase("0")){
                    txtQty.setText("");
                } else if(!txtQty.getText().toString().isEmpty()) {
                    mViewModel.setQuantity(Integer.parseInt(txtQty.getText().toString()));
                } else {
                    mViewModel.setQuantity(0);
                }
                txtQty.addTextChangedListener(this);
            }
        });

        mViewModel.getTotal().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {
                txtTtl.setText(s);
            }
        });
    }

}