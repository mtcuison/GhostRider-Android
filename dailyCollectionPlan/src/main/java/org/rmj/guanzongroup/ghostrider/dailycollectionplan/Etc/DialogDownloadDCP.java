package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Etc;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.google.android.material.textfield.TextInputEditText;

import org.rmj.guanzongroup.ghostrider.dailycollectionplan.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DialogDownloadDCP {
    private static final String TAG = DialogAccountDetail.class.getSimpleName();

    private AlertDialog poDialogx;
    private final Context context;
    private String lsDate = "";

    public DialogDownloadDCP(Context context){
        this.context = context;
    }

    public void initDialog(OnDialogButtonClickListener listener){
        AlertDialog.Builder loBuilder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_download_dcp, null, false);
        loBuilder.setCancelable(false)
                .setView(view);
        poDialogx = loBuilder.create();
        poDialogx.setCancelable(false);

        TextInputEditText txtDate = view.findViewById(R.id.txt_dcpDate);
        Button btnDownLoad = view.findViewById(R.id.btn_dcpDownload);
        Button btnCancel = view.findViewById(R.id.btn_cancel);

        txtDate.setOnClickListener(view13 -> {
            final Calendar newCalendar = Calendar.getInstance();
            @SuppressLint("SimpleDateFormat") final SimpleDateFormat dateFormatter = new SimpleDateFormat("MMMM dd, yyyy");
            final DatePickerDialog  StartTime = new DatePickerDialog(context, (view131, year, monthOfYear, dayOfMonth) -> {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                lsDate = dateFormatter.format(newDate.getTime());
                txtDate.setText(lsDate);
            }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
            StartTime.getDatePicker().setMaxDate(System.currentTimeMillis() + 1000);
            StartTime.show();
        });

        btnDownLoad.setOnClickListener(view12 -> listener.OnDownloadClick(poDialogx, lsDate));

        btnCancel.setOnClickListener(view1 -> listener.OnCancel(poDialogx));
    }

    public void show(){
        if(!poDialogx.isShowing()){
            poDialogx.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            poDialogx.show();
        }
    }

    public interface OnDialogButtonClickListener{
        void OnDownloadClick(Dialog Dialog, String Date);
        void OnCancel(Dialog Dialog);
    }

    /*private static class DcpDatePicker extends DialogFragment implements DatePickerDialog.OnDateSetListener{



        @NonNull
        @Override
        public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog dialog = new DatePickerDialog(getActivity(), this, year, month, day);
            dialog.getDatePicker().setMaxDate(c.getTimeInMillis());
            dialog.getDatePicker().setMaxDate(System.currentTimeMillis() + 1000);
            return  dialog;
        }

        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

        }
    }*/
}
