package org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity;

import static org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.CashCountDetailedInfo.Values.C0001;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.*;
import com.google.android.material.textview.MaterialTextView;
import com.google.android.material.divider.MaterialDivider;

import org.rmj.g3appdriver.etc.CashFormatter;
import org.rmj.g3appdriver.etc.FormatUIText;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Adapter.Adapter_CashCountDetailInfo;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.CashCountDetailedInfo;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMCashCountLogDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Activity_CashCountLogDetails extends AppCompatActivity {

    private VMCashCountLogDetails mViewModel;
    private Adapter_CashCountDetailInfo poAdapter;
    private MaterialToolbar toolbar;
    private RecyclerView recyclerView;
    private MaterialTextView lblBranch, lblTranDt, lblTransN, lblGrandT, lblOrNoxx,
    lblSiNoxx, lblPrNoxx, lblCrNoxx, lblPettyC ;
    private String psTransNo = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_count_log_details);
        psTransNo = Objects.requireNonNull(getIntent().getStringExtra("sTransNox"));
        mViewModel = new ViewModelProvider(Activity_CashCountLogDetails.this)
                .get(VMCashCountLogDetails.class);

        initWidgets();
        setViewValues();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    private void initWidgets() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(Activity_CashCountLogDetails.this);
        recyclerView.setLayoutManager(layoutManager);

        lblBranch = findViewById(R.id.lbl_branch);
        lblTranDt = findViewById(R.id.lbl_date);
        lblTransN = findViewById(R.id.lbl_transNo);
        lblGrandT = findViewById(R.id.lbl_grand_total);

        lblPettyC = findViewById(R.id.lbl_total_pettyCash);
        lblOrNoxx = findViewById(R.id.lbl_orNo);
        lblSiNoxx = findViewById(R.id.lbl_siNo);
        lblPrNoxx = findViewById(R.id.lbl_prNo);
        lblCrNoxx = findViewById(R.id.lbl_crNo);

    }

    private void setViewValues() {
        mViewModel.getCashCounDetetail(psTransNo).observe(Activity_CashCountLogDetails.this, cashCount -> {
            try {
                mViewModel.getBranchName(cashCount.getBranchCd()).observe(Activity_CashCountLogDetails.this,
                        branchName -> {
                            try {
                                lblBranch.setText(branchName + " Cash Count");
                            } catch (NullPointerException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

                lblTransN.setText(psTransNo);
                lblTranDt.setText(FormatUIText.getParseDateTime(cashCount.getEntryDte()));
                lblPettyC.setText(String.valueOf(cashCount.getPettyAmt()));
                lblOrNoxx.setText(cashCount.getORNoxxxx());
                lblSiNoxx.setText(cashCount.getSINoxxxx());
                lblPrNoxx.setText(cashCount.getPRNoxxxx());
                lblCrNoxx.setText(cashCount.getCRNoxxxx());

                List<CashCountDetailedInfo> loDtaInfo = new ArrayList<>();
                loDtaInfo.add(new CashCountDetailedInfo(true, false, "Peso Bills (₱)", "", "", CashCountDetailedInfo.Values.NONE));
                loDtaInfo.add(new CashCountDetailedInfo(false, true, "", "", "", CashCountDetailedInfo.Values.NONE));
                loDtaInfo.add(new CashCountDetailedInfo(false, false, "", "₱1000", String.valueOf(cashCount.getNte1000p()), CashCountDetailedInfo.Values.P1000));
                loDtaInfo.add(new CashCountDetailedInfo(false, false, "", "₱500", String.valueOf(cashCount.getNte0500p()), CashCountDetailedInfo.Values.P0500));
                loDtaInfo.add(new CashCountDetailedInfo(false, false, "", "₱200", String.valueOf(cashCount.getNte0200p()), CashCountDetailedInfo.Values.P0200));
                loDtaInfo.add(new CashCountDetailedInfo(false, false, "", "₱100", String.valueOf(cashCount.getNte0100p()), CashCountDetailedInfo.Values.P0100));
                loDtaInfo.add(new CashCountDetailedInfo(false, false, "", "₱50", String.valueOf(cashCount.getNte0050p()), CashCountDetailedInfo.Values.P0050));
                loDtaInfo.add(new CashCountDetailedInfo(false, false, "", "₱20", String.valueOf(cashCount.getNte0020p()), CashCountDetailedInfo.Values.P0020));

                loDtaInfo.add(new CashCountDetailedInfo(true, false, "Peso Coins (₱)", "", "", CashCountDetailedInfo.Values.NONE));
                loDtaInfo.add(new CashCountDetailedInfo(false, true, "", "", "", CashCountDetailedInfo.Values.NONE));
                loDtaInfo.add(new CashCountDetailedInfo(false, false, "", "₱10", String.valueOf(cashCount.getCn0010px()), CashCountDetailedInfo.Values.P0010));
                loDtaInfo.add(new CashCountDetailedInfo(false, false, "", "₱5", String.valueOf(cashCount.getCn0005px()), CashCountDetailedInfo.Values.P0005));
                loDtaInfo.add(new CashCountDetailedInfo(false, false, "", "₱1", String.valueOf(cashCount.getCn0001px()), CashCountDetailedInfo.Values.P0001));
                loDtaInfo.add(new CashCountDetailedInfo(false, false, "", "¢25", String.valueOf(cashCount.getCn0025cx()), CashCountDetailedInfo.Values.C0025));
                loDtaInfo.add(new CashCountDetailedInfo(false, false, "", "¢10", String.valueOf(cashCount.getCn0010cx()), CashCountDetailedInfo.Values.C0010));
                loDtaInfo.add(new CashCountDetailedInfo(false, false, "", "¢5", String.valueOf(cashCount.getCn0005cx()), CashCountDetailedInfo.Values.C0005));
                loDtaInfo.add(new CashCountDetailedInfo(false, false, "", "¢1", String.valueOf(cashCount.getCn0001cx()), CashCountDetailedInfo.Values.C0001));
                poAdapter = new Adapter_CashCountDetailInfo(loDtaInfo);
                recyclerView.setAdapter(poAdapter);
                poAdapter.notifyDataSetChanged();


                String lsGrandT = "";
                double lnGrandT = 0.0;
                for (int x = 0; x < loDtaInfo.size(); x++) {
                    lnGrandT += Double.parseDouble(loDtaInfo.get(x).getTotal());
                }
                lblGrandT.setText(CashFormatter.parse(String.valueOf(lnGrandT)));
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

}