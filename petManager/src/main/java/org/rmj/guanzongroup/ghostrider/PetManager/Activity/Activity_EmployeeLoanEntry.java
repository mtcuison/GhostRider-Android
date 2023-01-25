package org.rmj.guanzongroup.ghostrider.PetManager.Activity;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.ClipData;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.lib.EmployeeLoan.model.LoanApplication;
import org.rmj.g3appdriver.lib.EmployeeLoan.model.LoanType;
import org.rmj.guanzongroup.ghostrider.PetManager.R;
import org.rmj.guanzongroup.ghostrider.PetManager.ViewModel.VMEmployeeLoanEntry;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Pattern;

public class Activity_EmployeeLoanEntry extends AppCompatActivity {
    private static final String TAG = Activity_EmployeeLoanEntry.class.getSimpleName();

    private VMEmployeeLoanEntry mViewModel;
    private LoanApplication loApp;

    private Toolbar toolbar;
    private AppCompatAutoCompleteTextView spn_loantype;
    private TextInputEditText txt_loanamt;
    private TextInputEditText txt_interest;
    private TextInputEditText txt_terms;
    private EditText edt_firstpay;
    private TextView txtview_balance;
    private TextView txtview_amort;
    private TextView txtview_currdate;
    private MaterialButton btn_saveloanentry;

    Toast toast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_loan_entry);

        mViewModel = new ViewModelProvider(this).get(VMEmployeeLoanEntry.class);
        loApp = new LoanApplication();

//      set declared  object value by getting id object from xml file
        toolbar = findViewById(R.id.toolbar);
        txtview_currdate = findViewById(R.id.txtview_currdate);
        spn_loantype = findViewById(R.id.spn_loantype);
        txt_loanamt = findViewById(R.id.txt_loanamt);
        txt_interest = findViewById(R.id.txt_interest);
        txt_terms = findViewById(R.id.txt_terms);
        edt_firstpay = findViewById(R.id.edt_firstpay);
        txtview_balance = findViewById(R.id.txtview_balance);
        txtview_amort = findViewById(R.id.txtview_amort);
        btn_saveloanentry = findViewById(R.id.btn_saveloanentry);

        /*TOOL BAR*/
        setToolbarAction();
        /*TEXTVIEW FOR CURRENT DAY*/
        setDisplayCurrentDate();
        /*Loan Type Object Function*/
        setLoanTypeList();
        /*DatePicker*/
        setDatePickeronEditText();
        /*TextInput*/
        setDecimalText(txt_loanamt, ".00");
        setDecimalText(txt_interest, ".00");
        mViewModel.validateAmtFormat("integer", txt_terms.getText().toString());
        /*Apply Button*/
        setButtonAction();
    }
    private void setToolbarAction(){
        setSupportActionBar(toolbar); //set object toolbar as default action bar for activity
        getSupportActionBar().setTitle(""); //set default title for action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //set back button to toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true); //enable the back button set on toolbar
    }
    private void setLoanTypeList(){
//      get different loan type values from declared view model above and store to list type
        List<LoanType> loList = mViewModel.GetLoanTypes();
//      declare an array type
        ArrayList<String> loTypes = new ArrayList<>();
//      create a for loop that scans the list value and store to array object
        for(int x= 0; x < loList.size(); x++){
            LoanType loType = loList.get(x);
            String lsType = loType.getLoanName();
            loTypes.add(lsType);
        }
//      create an adapter for array as on how it could be set on object,
//      1st arg: CLASS, 2nd arg: Object Layout View, 3rd arg: Value
        ArrayAdapter<String> loAdapter = new ArrayAdapter<>(
                Activity_EmployeeLoanEntry.this,
                android.R.layout.simple_dropdown_item_1line,
                loTypes.toArray(new String[0]));

//      set the view of values to object
        spn_loantype.setAdapter(loAdapter);

        //      set event listener for the object
        spn_loantype.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LoanType loType = loList.get(position);
                String lsIDxx = loType.getLoanIDxx();
                String lsType = loType.getLoanName();
                loApp.setLoanIDxx(lsIDxx);
                loApp.setLoanType(lsType);

                Log.d(TAG, "SELECTED Loan Type: " + lsIDxx +", "+ lsType);
            }
        });
    }
    private void setDisplayCurrentDate(){
        Calendar cal = Calendar.getInstance();
        String currdate;
        String month = mViewModel.convertMonthtoWord(cal.get(MONTH));
        String day = String.valueOf(cal.get(DAY_OF_MONTH));
        String year = String.valueOf(cal.get(YEAR));

        if (month == null){
            currdate = "";
            Toast toast = Toast.makeText(getApplicationContext(), "Current Date cannot be Displayed", Toast.LENGTH_LONG);
            toast.show();
        }else{
            currdate = month + " " + day + ", " + year;
        }
        txtview_currdate.setText(currdate);
    }
    private void setDatePickeronEditText(){
        mViewModel.editDtPicker = edt_firstpay;
        edt_firstpay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mViewModel.showDatePicker(Activity_EmployeeLoanEntry.this);
            }
        });
    }
    private void setDecimalText(TextInputEditText editText, String defText){
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == false){
                    String currtext = editText.getText().toString();
                    if (currtext.isEmpty() == false && currtext.trim() != ""){
                        Boolean matchFormat = mViewModel.validateAmtFormat("decimal", currtext);

                        if (matchFormat == false) {
                            if (defText.isEmpty() == false && defText.trim() != "") {
                                currtext = currtext.concat(defText);
                                editText.setText("");
                                editText.setText(currtext);
                            }
                        }
                    }
                }
            }
        });
    }
    private void setButtonAction(){
        btn_saveloanentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    private void SaveLoanApplication(){
        if(txt_loanamt.getText().toString().trim().isEmpty()){
            Toast.makeText(Activity_EmployeeLoanEntry.this, "Please enter amount", Toast.LENGTH_SHORT).show();
            return;
        }
        loApp.setLoanTerm(Integer.parseInt(txt_terms.getText().toString()));
        loApp.setInterest(Double.parseDouble(txt_interest.getText().toString()));
        loApp.setAmountxx(Double.parseDouble(txt_loanamt.getText().toString()));
    }
}