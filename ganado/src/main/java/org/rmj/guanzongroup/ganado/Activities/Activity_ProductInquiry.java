package org.rmj.guanzongroup.ganado.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.g3appdriver.etc.FormatUIText;
import org.rmj.guanzongroup.ganado.R;

import java.util.Objects;

public class Activity_ProductInquiry extends AppCompatActivity {

    private MaterialTextView lblBranchNm, lblBrandAdd, lblDate;
    private MaterialAutoCompleteTextView txtBranchNm, txtBrandNm, txtModelNm,txtModelCd;
    private TextInputLayout tilApplType;
    private TextInputEditText txtDownPymnt, txtAmort, txtDTarget;
    private MaterialAutoCompleteTextView spn_color, spnPayment, spnAcctTerm;
    private MaterialButton btnContinue;
    private ShapeableImageView imgMC;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_inquiry);
        initWidgets();

        txtDownPymnt.addTextChangedListener(new FormatUIText.CurrencyFormat(txtDownPymnt));

        txtDownPymnt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try{
                    if (!Objects.requireNonNull(txtDownPymnt.getText()).toString().trim().isEmpty()) {

                        txtDownPymnt.removeTextChangedListener(this);

                        String lsInput = txtDownPymnt.getText().toString().trim();

                        Double lnInput = FormatUIText.getParseDouble(lsInput);
//
//                        mViewModel.getModel().setDownPaymt(lnInput);
//
//                        double lnVal = mViewModel.getModel().getDownPaymt();
//
//                        double lnMonthly = mViewModel.GetMonthlyPayment(lnVal);
//
//                        mViewModel.getModel().setMonthlyAm(lnMonthly);

//                        txtAmort.setText(FormatUIText.getCurrencyUIFormat(String.valueOf(lnMonthly)));

                        txtDownPymnt.addTextChangedListener(this);
                    }
                } catch (Exception e){
                    e.printStackTrace();

                    txtDownPymnt.addTextChangedListener(this);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void initWidgets(){
        MaterialToolbar toolbar = findViewById(R.id.toolbar_Inquiry);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        txtBrandNm = findViewById(R.id.lblBrand);
        txtModelCd = findViewById(R.id.lblModelCde);
        txtModelNm = findViewById(R.id.lblModelNme);
        txtDownPymnt = findViewById(R.id.txt_downpayment);
        txtAmort = findViewById(R.id.txt_monthlyAmort);
        spnPayment = findViewById(R.id.spn_paymentMethod);
        spnAcctTerm = findViewById(R.id.spn_installmentTerm);
        imgMC = findViewById(R.id.imgMC);

        btnContinue = findViewById(R.id.btnContinue);
    }
}
