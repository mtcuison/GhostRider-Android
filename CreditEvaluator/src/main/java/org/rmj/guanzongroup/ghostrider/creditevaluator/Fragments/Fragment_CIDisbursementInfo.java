package org.rmj.guanzongroup.ghostrider.creditevaluator.Fragments;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.guanzongroup.ghostrider.creditevaluator.R;
import org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel.VMCIDisbursement;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Objects;

public class Fragment_CIDisbursementInfo extends Fragment {

    private VMCIDisbursement mViewModel;

    private final DecimalFormat currency_total = new DecimalFormat("###,###,###.###");
    private TextInputEditText tieWater,
            tieElctx,
            tieFoodx,
            tieLoans,
            tieEducation,
            tieOthers,
            tieTotalExpenses;
    public static Fragment_CIDisbursementInfo newInstance() {
        return new Fragment_CIDisbursementInfo();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_ci_disbursement_info, container, false);
        initWidgets(root);
        return root;
    }
    void initWidgets(View view){
        tieWater = view.findViewById(R.id.tie_ci_dbmWater);
        tieElctx = view.findViewById(R.id.tie_ci_dbmElectricity);
        tieFoodx = view.findViewById(R.id.tie_ci_dbmFood);
        tieLoans = view.findViewById(R.id.tie_ci_dbmLoans);
        tieEducation = view.findViewById(R.id.tie_ci_dbmEducation);
        tieOthers = view.findViewById(R.id.tie_ci_dbmOthers);
        tieTotalExpenses = view.findViewById(R.id.tie_ci_dbmExpenses);

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(VMCIDisbursement.class);
        tieWater.addTextChangedListener(textWatcher);
        tieElctx.addTextChangedListener(textWatcher);
        tieFoodx.addTextChangedListener(textWatcher);
        tieLoans.addTextChangedListener(textWatcher);
        tieEducation.addTextChangedListener(textWatcher);
        tieOthers.addTextChangedListener(textWatcher);

        // TODO: Use the ViewModel
    }
    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            if (!TextUtils.isEmpty(tieWater.getText().toString().trim())
                    || !TextUtils.isEmpty(tieElctx.getText().toString().trim())
                    || !TextUtils.isEmpty(tieFoodx.getText().toString().trim())
                    || !TextUtils.isEmpty(tieLoans.getText().toString().trim())
                    || !TextUtils.isEmpty(tieEducation.getText().toString().trim())
                    || !TextUtils.isEmpty(tieOthers.getText().toString().trim())
            ) {


                try {
                    double firtValue = TextUtils.isEmpty(tieWater.getText().toString().trim()) ? 0 : Double.parseDouble(tieWater.getText().toString().trim());
                    double secondValue = TextUtils.isEmpty(tieElctx.getText().toString().trim()) ? 0 : Double.parseDouble(tieElctx.getText().toString().trim());
                    double thirdValue = TextUtils.isEmpty(tieFoodx.getText().toString().trim()) ? 0 : Double.parseDouble(tieFoodx.getText().toString().trim());
                    double forthValue = TextUtils.isEmpty(tieLoans.getText().toString().trim()) ? 0 : Double.parseDouble(tieLoans.getText().toString().trim());
                    double fithValue = TextUtils.isEmpty(tieEducation.getText().toString().trim()) ? 0 : Double.parseDouble(tieEducation.getText().toString().trim());
                    double sixValue = TextUtils.isEmpty(tieOthers.getText().toString().trim()) ? 0 : Double.parseDouble(tieOthers.getText().toString().trim());

                    double answer = firtValue + secondValue + thirdValue + forthValue + fithValue + sixValue;


                    Log.e("RESULT", String.valueOf(answer));
                    tieTotalExpenses.setText(String.valueOf(currency_total.format(answer)));
                }catch (NullPointerException e){

                }

            }else {
                tieTotalExpenses.setText("0.0");
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}