package org.rmj.guanzongroup.pacitareward.Fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.rmj.g3appdriver.GCircle.room.DataAccessObject.DPacita;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.guanzongroup.pacitareward.Activity.Activity_BranchRecords;
import org.rmj.guanzongroup.pacitareward.Adapter.RecyclerViewAdapter_BranchRecord;
import org.rmj.guanzongroup.pacitareward.Adapter.RecyclerViewAdapter_EvalHistory;
import org.rmj.guanzongroup.pacitareward.R;
import org.rmj.guanzongroup.pacitareward.ViewModel.VMBranchList;

import java.util.Calendar;
import java.util.List;

public class Fragment_HistoryEval extends Fragment implements DatePickerDialog.OnDateSetListener {
    private TextInputEditText searchview_fromdate;
    private TextInputEditText searchview_todate;
    private TextInputLayout srchview_fromdtt;
    private TextInputLayout srchview_todtt;
    private VMBranchList mViewModel;
    private RecyclerViewAdapter_EvalHistory rec_evalhist;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_layouthisteval, container, false);

        searchview_fromdate = view.findViewById(R.id.searchview_fromdate);
        searchview_todate = view.findViewById(R.id.searchview_todate);
        srchview_fromdtt = view.findViewById(R.id.srchview_fromdtt);
        srchview_todtt = view.findViewById(R.id.srchview_todtt);

        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePicker = new DatePickerDialog(getContext(), this, year, month, day);
        datePicker.setCancelable(true);

        mViewModel = new ViewModelProvider(this).get(VMBranchList.class);
        mViewModel.getRecentRecords().observe(getActivity(), new Observer<List<DPacita.RecentRecords>>() {
            @Override
            public void onChanged(List<DPacita.RecentRecords> recentRecords) {
                rec_evalhist = new RecyclerViewAdapter_EvalHistory(getContext(), recentRecords, new RecyclerViewAdapter_EvalHistory.onSelectItem() {
                    @Override
                    public void onItemSelected(String transactNox, String branchcode, String branchname) {
                        Intent intent = new Intent(getContext(), Activity_BranchRecords.class);
                        intent.putExtra("Branch Code", branchcode);
                        intent.putExtra("Branch Name", branchname);

                        startActivity(intent);
                    }
                });
            }
        });

        searchview_fromdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month += 1;
                        searchview_fromdate.setText(String.valueOf(year) + '/' + String.valueOf(month) + '/' + String.valueOf(year));
                    }
                });
                datePicker.show();
            }
        });

        searchview_todate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month += 1;
                        searchview_todate.setText(String.valueOf(year) + '/' + String.valueOf(month) + '/' + String.valueOf(year));
                    }
                });
                datePicker.show();
            }
        });

        srchview_fromdtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month += 1;
                        searchview_fromdate.setText(String.valueOf(year) + '/' + String.valueOf(month) + '/' + String.valueOf(year));
                    }
                });
                datePicker.show();
            }
        });

        srchview_todtt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month += 1;
                        searchview_todate.setText(String.valueOf(year) + '/' + String.valueOf(month) + '/' + String.valueOf(year));
                    }
                });
                datePicker.show();
            }
        });

        return view;
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

    }
}
