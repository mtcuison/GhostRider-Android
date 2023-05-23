package org.rmj.guanzongroup.ganado.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.g3appdriver.etc.FormatUIText;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.guanzongroup.ganado.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class Activity_PersonalInfo extends AppCompatActivity {

    private MessageBox poMessage;

    private TextInputEditText txtLastNm, txtFrstNm, txtMiddNm, txtSuffixx, txtNickNm, txtBirthDt,
            txtMothNm, txtTellNox, txtEmailAdd, txtFbAccount, txtViberAccount;

    private TextInputLayout tilMothNm;
    private MaterialAutoCompleteTextView txtTown, txtCitizen;
    private RadioGroup rgGender;
    private MaterialAutoCompleteTextView spnCivilStatus;
    private MaterialButton btnContinue, btnPrev;
    private MaterialCheckBox txtMobileType1, txtMobileType2, txtMobileType3;

    private TextInputEditText[] txtMobileNo;
    private TextInputEditText[] txtMobileYear;
    private TextInputLayout[] tilMobileYear;
    private MaterialToolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        poMessage = new MessageBox(Activity_PersonalInfo.this);
        setContentView(R.layout.activity_personal_info);
        initWidgets();
    }

    private void initWidgets() {
        toolbar = findViewById(R.id.toolbar_PersonalInfo);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Guanzon Group OACS");

        txtLastNm = findViewById(R.id.txt_lastname);
        txtFrstNm = findViewById(R.id.txt_firstname);
        txtMiddNm = findViewById(R.id.txt_middname);
        txtSuffixx = findViewById(R.id.txt_suffix);
        txtBirthDt = findViewById(R.id.txt_birthdate);


        txtTown = findViewById(R.id.txt_bpTown);
        rgGender = findViewById(R.id.rg_gender);

        txtBirthDt.setOnClickListener(v -> {
            final Calendar newCalendar = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") final SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM dd, yyyy");
            final DatePickerDialog StartTime = new DatePickerDialog(Activity_PersonalInfo.this,
                    android.R.style.Theme_Holo_Dialog, (view131, year, monthOfYear, dayOfMonth) -> {
                try {
                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    String lsDate = dateFormatter.format(newDate.getTime());
                    txtBirthDt.setText(lsDate);
                    Date loDate = new SimpleDateFormat("MMMM dd, yyyy").parse(lsDate);
                    lsDate = new SimpleDateFormat("yyyy-MM-dd").format(loDate);
//                    Log.d(TAG, "Save formatted time: " + lsDate);
//                    mViewModel.getModel().setBrthDate(lsDate);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            StartTime.getDatePicker().setMaxDate(new Date().getTime());
            StartTime.show();
        });

        btnContinue = findViewById(R.id.btnContinue);
    }

}