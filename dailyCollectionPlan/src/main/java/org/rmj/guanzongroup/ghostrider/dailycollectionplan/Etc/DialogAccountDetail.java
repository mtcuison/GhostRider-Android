package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployee;
import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Activities.Activity_CollectionList;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Fragments.Fragment_LoanUnit;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

public class DialogAccountDetail {
    private static final String TAG = DialogAccountDetail.class.getSimpleName();

    private AlertDialog poDialogx;
    private final Context context;
    private String transPosition = "-1", stringSpinnerItem = "";
//    private static AutoCompleteTextView spnTransact;
    private AutoCompleteTextView spnTransact;
    private Spinner spnTransacts;
    private MessageBox loMessage;

    public DialogAccountDetail(Context context){
        this.context = context;
    }

    public void initAccountDetail(EDCPCollectionDetail foDetail, DialogButtonClickListener listener){
        AlertDialog.Builder loBuilder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_account_detail, null, false);
        loBuilder.setCancelable(false)
                .setView(view);
        poDialogx = loBuilder.create();
        poDialogx.setCancelable(false);

        loMessage = new MessageBox(context);
        TextView lblReferNo = view.findViewById(R.id.lbl_dcpReferNo);
        TextView lblTransNo = view.findViewById(R.id.lbl_dcpTransNo);
//        TextView lblTranDte = view.findViewById(R.id.lbl_dcpTranDate);
        TextView lblAccntNo = view.findViewById(R.id.lbl_dcpAccNo);
        TextView lblSerialx = view.findViewById(R.id.lbl_dcpPRNo);
        TextView lblAmountx = view.findViewById(R.id.lbl_dcpAmount);
        TextView lblDueDate = view.findViewById(R.id.lbl_dcpDueDate);
//        TextView lblOthersx = view.findViewById(R.id.lbl_dcpOthers);
//        Spinner spnTransact = view.findViewById(R.id.spn_transaction);

        spnTransact = view.findViewById(R.id.spn_transaction);
        spnTransacts = view.findViewById(R.id.spn_transactions);
        Button btnConfirm = view.findViewById(R.id.btn_confirm);
        Button btnCancelx = view.findViewById(R.id.btn_cancel);

        lblReferNo.setText(foDetail.getReferNox());
        lblTransNo.setText(foDetail.getTransNox());
        lblAccntNo.setText(foDetail.getAcctNmbr());
        lblSerialx.setText(foDetail.getSerialNo());
        lblAmountx.setText(FormatUIText.getCurrencyUIFormat(foDetail.getAmtDuexx()));
        lblDueDate.setText(FormatUIText.formatGOCasBirthdate(foDetail.getDueDatex()));
//        spnTransact.setOnClickListener(v-> spnTransacts);
//        spnTransact.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, DCP_Constants.TRANSACTION_TYPE));
//        spnTransacts.setAdapter(new ArrayAdapter<>(context, android.R.layout.simple_spinner_dropdown_item, DCP_Constants.TRANSACTION_TYPE));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item,  DCP_Constants.TRANSACTION_TYPE){
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                spnTransacts.performClick();
//                spnTransacts.setOnItemSelectedListener(new SpinnerSelectionListener(spnTransacts));
                return v;
            }
        };
        spnTransacts.setAdapter(adapter);
        spnTransact.setAdapter(adapter);
        spnTransact.setOnItemClickListener(new OnItemClickListener(spnTransact));
        spnTransacts.setOnItemSelectedListener(new SpinnerSelectionListener(spnTransacts));

        btnConfirm.setOnClickListener(view1 -> {
            if (spnTransact.getText().toString().trim().isEmpty()){
                showValid();

            }else{
                listener.OnClick(poDialogx,spnTransact.getText().toString());
            }
        });
        btnCancelx.setOnClickListener(view12 -> dismiss());
    }

    public void setAutoCompleteTextViewValue(){
        spnTransact.setText(spnTransacts.getSelectedItem().toString());
    }
    public void show(){
        poDialogx.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        poDialogx.show();
    }
    public void showValid(){
        loMessage.setNegativeButton("Okay", (view, dialog) -> {
            dialog.dismiss();

        });
        loMessage.setTitle("GhostRider");
        loMessage.setMessage(stringSpinnerItem);
        loMessage.show();
    }
    public void dismiss(){
        if(poDialogx != null && poDialogx.isShowing()){
            poDialogx.dismiss();
        }
    }
    class SpinnerSelectionListener implements AdapterView.OnItemSelectedListener{
        private final Spinner spinnerView;
        SpinnerSelectionListener(Spinner spinner){
            this.spinnerView = spinner;
        }
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(spinnerView.equals(R.id.spn_transactions)){
                Log.e("Item selected", parent.getSelectedItem().toString());
                stringSpinnerItem = parent.getSelectedItem().toString();

                Toast.makeText(context, stringSpinnerItem, Toast.LENGTH_LONG).show();
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }

    }

    public interface DialogButtonClickListener{
        void OnClick(Dialog dialog, String remarksCode);
    }

    class OnItemClickListener implements AdapterView.OnItemClickListener {
        AutoCompleteTextView poView;

        public OnItemClickListener(AutoCompleteTextView view) {
            this.poView = view;
        }

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            if (spnTransact.equals(poView)) {

                Log.e("Item selected", adapterView.getSelectedItem().toString());
                Toast.makeText(context,  (String)adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_LONG).show();
            }

        }
    }
}
