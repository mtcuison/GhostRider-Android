package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.lifecycle.MutableLiveData;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc.DCP_Constants;
import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

public class DialogAddCollection {
    private static final String TAG = DialogAddCollection.class.getSimpleName();

    private AlertDialog poDialogx;
    private final Context context;
    private TextInputLayout tilParms;
    private String psType = "";

    public DialogAddCollection(Context context) {
        this.context = context;
    }

    public void initDialog(OnDialogButtonClickListener listener){
        AlertDialog.Builder loBuilder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_get_client, null, false);
        loBuilder.setCancelable(false)
                .setView(view);
        poDialogx = loBuilder.create();
        poDialogx.setCancelable(false);

        tilParms = view.findViewById(R.id.til_dcpParameter);
        TextInputEditText txtAccntNo = view.findViewById(R.id.txt_dcpParameter);
        RadioGroup rgColllect = view.findViewById(R.id.rg_collection_tp);
        RadioButton rbArClient = view.findViewById(R.id.rb_ar_client);
        RadioButton rbInsrnce = view.findViewById(R.id.rb_insurance_client);
        rgColllect.setOnCheckedChangeListener(new OnRadioButtonSelectListener());

        Button btnDownLoad = view.findViewById(R.id.btn_dcpDownload);
        Button btnCancel = view.findViewById(R.id.btn_cancel);

        btnDownLoad.setOnClickListener(view1 -> {
            if(!rbArClient.isChecked() && !rbInsrnce.isChecked()) {
                GToast.CreateMessage(context,"Please select a collection type.", GToast.WARNING).show();
            }
            else if(txtAccntNo.getText().toString().trim().isEmpty()) {
                GToast.CreateMessage(context,
                        "Please enter " + DCP_Constants.ADD_COLLECTION_PARAM[Integer.parseInt(psType)] + ".",
                        GToast.WARNING).show();
            } else {
                listener.OnDownloadClick(poDialogx,
                        txtAccntNo.getText().toString(), psType.trim());
            }
        });

        btnCancel.setOnClickListener(view12 -> listener.OnCancel(poDialogx));
    }

    public void show(){
        if(!poDialogx.isShowing()){
            poDialogx.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            poDialogx.getWindow().getAttributes().windowAnimations = org.rmj.g3appdriver.R.style.PopupAnimation;
            poDialogx.show();
        }
    }

    private class OnRadioButtonSelectListener implements RadioGroup.OnCheckedChangeListener{
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            if(radioGroup.getId() == R.id.rg_collection_tp){
                if (i == R.id.rb_ar_client) {
                    tilParms.setHint(DCP_Constants.ADD_COLLECTION_PARAM[0]);
                    psType = "0";
                } else if (i == R.id.rb_insurance_client) {
                    tilParms.setHint(DCP_Constants.ADD_COLLECTION_PARAM[1]);
                    psType = "1";
                }
            }
        }
    }

    public interface OnDialogButtonClickListener{
        void OnDownloadClick(Dialog Dialog, String args, String fsType);
        void OnCancel(Dialog Dialog);
    }
}
