package org.rmj.guanzongroup.petmanager.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.AlertDialog;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.lib.EmployeeLoan.model.LoanApplication;
import org.rmj.g3appdriver.lib.EmployeeLoan.model.LoanType;
import org.rmj.guanzongroup.petmanager.R;
import org.rmj.guanzongroup.petmanager.ViewModel.VMEmployeeLoanEntry;

public class Activity_EmployeeLoanEntry extends AppCompatActivity {
    private static final String TAG = Activity_EmployeeLoanEntry.class.getSimpleName();

    private VMEmployeeLoanEntry mViewModel;
    private LoanApplication loApp;
    private MaterialToolbar toolbar;
    private MaterialAutoCompleteTextView spn_loantype;
    private TextInputEditText txt_loanamt;
    private TextInputEditText txt_interest;
    private TextInputEditText txt_terms;
    private TextInputEditText txt_firstpay;
    private TextInputEditText txt_amort;
    private TextInputEditText txt_totalinterest;
    private MaterialButton btn_saveloanentry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_loan_entry);

        mViewModel = new ViewModelProvider(this).get(VMEmployeeLoanEntry.class);
        mViewModel.context = Activity_EmployeeLoanEntry.this;

        loApp = new LoanApplication();

        //set declared  object value by getting id object from xml file
        toolbar = findViewById(R.id.toolbar);
        spn_loantype = findViewById(R.id.spn_loantype);
        txt_loanamt = findViewById(R.id.txt_loanamt);
        txt_interest = findViewById(R.id.txt_interest);
        txt_terms = findViewById(R.id.txt_terms);
        txt_firstpay = findViewById(R.id.txt_firstpay);
        txt_amort = findViewById(R.id.txt_monthlypay);
        txt_totalinterest = findViewById(R.id.txt_totalintrst);
        btn_saveloanentry = findViewById(R.id.btn_saveloanentry);

        /*TOOL BAR*/
        setSupportActionBar(toolbar); //set object toolbar as default action bar for activity
        getSupportActionBar().setTitle("Employee Loan"); //set default title for action bar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); //set back button to toolbar
        getSupportActionBar().setDisplayShowHomeEnabled(true); //enable the back button set on toolbar

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        /*Loan Type Object*/
        spn_loantype.setAdapter(mViewModel.getLoanTypeListAdapter());
        spn_loantype.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                LoanType loType = mViewModel.GetLoanTypes().get(position);
                String lsIDxx = loType.getLoanIDxx();
                loApp.setLoanIDxx(lsIDxx);
                //String lsType = loType.getLoanName();
                //loApp.setLoanType(lsType);
            }
        });

        /*TextInput*/
        txt_loanamt.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //set default text
                txt_loanamt.setText(mViewModel.getDefaultText(txt_loanamt.getText().toString(),
                        ".00", "decimal"));
                //set total interest
                txt_totalinterest.setText(mViewModel.computeTotalAmt(txt_loanamt.getText().toString(), txt_interest.getText().toString(),
                        txt_firstpay.getText().toString(), txt_terms.getText().toString(), "interest"));
                //set total amort
                txt_amort.setText(mViewModel.computeTotalAmt(txt_loanamt.getText().toString(), txt_interest.getText().toString(),
                        txt_firstpay.getText().toString(), txt_terms.getText().toString(), "amort"));
            }
        });
        txt_interest.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //set default text
                txt_interest.setText(mViewModel.getDefaultText(txt_interest.getText().toString(),
                        ".00", "decimal"));
                //set total interest
                txt_totalinterest.setText(mViewModel.computeTotalAmt(txt_loanamt.getText().toString(), txt_interest.getText().toString(),
                        txt_firstpay.getText().toString(), txt_terms.getText().toString(), "interest"));
                //set total amort
                txt_amort.setText(mViewModel.computeTotalAmt(txt_loanamt.getText().toString(), txt_interest.getText().toString(),
                        txt_firstpay.getText().toString(), txt_terms.getText().toString(), "amort"));
            }
        });
        txt_firstpay.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //set default text
                txt_firstpay.setText(mViewModel.getDefaultText(txt_firstpay.getText().toString(),
                        ".00", "decimal"));
                //set total interest
                txt_totalinterest.setText(mViewModel.computeTotalAmt(txt_loanamt.getText().toString(), txt_interest.getText().toString(),
                        txt_firstpay.getText().toString(), txt_terms.getText().toString(), "interest"));
                //set total amort
                txt_amort.setText(mViewModel.computeTotalAmt(txt_loanamt.getText().toString(), txt_interest.getText().toString(),
                        txt_firstpay.getText().toString(), txt_terms.getText().toString(), "amort"));
            }
        });
        txt_terms.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                //set default text
                txt_terms.setText(mViewModel.getDefaultText(txt_terms.getText().toString(),
                        "0", "integer"));
                //set total interest
                txt_totalinterest.setText(mViewModel.computeTotalAmt(txt_loanamt.getText().toString(), txt_interest.getText().toString(),
                        txt_firstpay.getText().toString(), txt_terms.getText().toString(), "interest"));
                //set total amort
                txt_amort.setText(mViewModel.computeTotalAmt(txt_loanamt.getText().toString(), txt_interest.getText().toString(),
                        txt_firstpay.getText().toString(), txt_terms.getText().toString(), "amort"));
            }
        });
        btn_saveloanentry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //VALIDATE TEXTFIELD'S VALUE FOR AMOUNT
                Boolean outputValid = mViewModel.validateInputAmt(Activity_EmployeeLoanEntry.this, true, new TextInputEditText[]{txt_loanamt, txt_terms, txt_firstpay});
                Boolean outputValidIntrst = mViewModel.validateInputAmt(Activity_EmployeeLoanEntry.this, false, new TextInputEditText[]{txt_interest});
                if(outputValid.equals(false)) {
                    return;
                }else if (outputValidIntrst.equals(false)){
                    return;
                }

                Boolean save = mViewModel.SaveLoanApplication(spn_loantype.getText().toString(), txt_loanamt.getText().toString(),
                        txt_interest.getText().toString(), txt_firstpay.getText().toString(), txt_terms.getText().toString()
                        , loApp);

                if (save.equals(true)) {

                    //initialize dialog class
                    MessageBox messageBox = new MessageBox(Activity_EmployeeLoanEntry.this);
                    messageBox.initDialog();

                    //set dialog title and message
                    messageBox.setTitle("Loan Application");
                    messageBox.setMessage("Confirm Loan Application?");

                    //set dialog buttons
                    messageBox.setPositiveButton("YES", new MessageBox.DialogButton() {
                        @Override
                        public void OnButtonClick(View view, AlertDialog dialog) {
                            //loApp.setLoanType(loantype);
                            loApp.setAmountxx(Double.parseDouble(txt_loanamt.getText().toString()));
                            loApp.setInterest(Double.parseDouble(txt_interest.getText().toString()));
                            loApp.setLoanTerm(Integer.parseInt(txt_terms.getText().toString()));
                            loApp.setAmountxx(Double.parseDouble(txt_firstpay.getText().toString()));

                            //close dialog
                            dialog.dismiss();

                            spn_loantype.setText("");
                            txt_loanamt.setText("");
                            txt_interest.setText("");
                            txt_terms.setText("");
                            txt_firstpay.setText("");
                            txt_amort.setText("");
                            txt_totalinterest.setText("");
                        }
                    });

                    messageBox.setNegativeButton("NO", new MessageBox.DialogButton() {
                        @Override
                        public void OnButtonClick(View view, AlertDialog dialog) {
                            dialog.dismiss();
                            return;
                        }
                    });

                    //show dialog
                    messageBox.show();
                }
            }
        });
    }
}