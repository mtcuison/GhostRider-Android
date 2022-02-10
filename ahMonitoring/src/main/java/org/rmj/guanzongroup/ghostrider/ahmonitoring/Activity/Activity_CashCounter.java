/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.ahMonitoring
 * Electronic Personnel Access Control Security System
 * project file created : 6/7/21 2:34 PM
 * project file last modified : 6/7/21 2:34 PM
 */

package org.rmj.guanzongroup.ghostrider.ahmonitoring.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.rmj.g3appdriver.GRider.Constants.AppConstants;
import org.rmj.g3appdriver.GRider.Etc.GToast;
import org.rmj.g3appdriver.GRider.Etc.MessageBox;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.Model.CashCountInfoModel;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.R;
import org.rmj.guanzongroup.ghostrider.ahmonitoring.ViewModel.VMCashCounter;

import java.text.DecimalFormat;
import java.util.Objects;

public class Activity_CashCounter extends AppCompatActivity {

    public static final String TAG = Activity_CashCounter.class.getSimpleName();
    private static Activity_CashCounter instance;
    private VMCashCounter mViewModel;

    public static Activity_CashCounter getInstance() {
        return instance;
    }
    //Array for Peso Bill
    private TextInputEditText[] qtyPeso;
    private TextInputEditText[] totalPeso;
    private TextView[] cprice;

    //Array for Coin Bill
    private TextInputEditText[] qtyCoin;
    private TextInputEditText[] totalCoin;
    private TextView[] cpriceCoin;

    private TextView lblPesoTotal, lblCoinsTotal, lblGrandTotal;
    private TextView lblBranch, lblAddxx, lblDate;
    private MaterialButton btnNext;

    private MessageBox poMessage;
    private CashCountInfoModel infoModel;
    DecimalFormat formatter = new DecimalFormat("###,###,##0.00");
    private String psNotifNo = "";
    private String BranchCd = "";
    private boolean cancelable = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cash_counter);
        instance = Activity_CashCounter.this;
        mViewModel = new ViewModelProvider(this).get(VMCashCounter.class);
        initWidgets();
        try {
            BranchCd = getIntent().getStringExtra("BranchCd");
        } catch (Exception e){
            e.printStackTrace();
        }
        cancelable = getIntent().getBooleanExtra("cancelable", true);
        poMessage = new MessageBox(Activity_CashCounter.this);
        infoModel = new CashCountInfoModel();
        mViewModel.getUserBranchInfo().observe(Activity_CashCounter.this, eBranchInfo -> {
            try {
                lblBranch.setText(eBranchInfo.getBranchNm());
                lblAddxx.setText(eBranchInfo.getAddressx());
                lblDate.setText(new AppConstants().CURRENT_DATE_WORD);
            } catch (Exception e){
                e.printStackTrace();
            }
        });
        //Peso Bill Computation
        for (int x = 0; x < qtyPeso.length; x++) {
            final int i = x;
            final String cprices = String.valueOf(cprice[i].getText());
            qtyPeso[x].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {
                        int total = 0;
                        String price = "", qty = "";
                        price = String.valueOf(cprice[i].getText());
                        qty = String.valueOf(qtyPeso[i].getText());
                        if (qty.isEmpty()){
                            totalPeso[i].setText("0");
                            if (i == 0) {
                                mViewModel.setP1000((double) total, Integer.parseInt(qty));
                            }
                            else if (i == 1) {
                                mViewModel.setP500((double) total, Integer.parseInt(qty));
                            }
                            else if (i == 2) {
                                mViewModel.setP200((double) total, Integer.parseInt(qty));
                            }
                            else if (i == 3) {
                                mViewModel.setP100((double) total, Integer.parseInt(qty));
                            }
                            else if (i == 4) {
                                mViewModel.setP50((double) total, Integer.parseInt(qty));
                            }
                            else if (i == 5) {
                                mViewModel.setP20((double) total, Integer.parseInt(qty));
                            }
                        }
                        else {
                            total = Integer.parseInt(price.substring(1)) * Integer.parseInt(qty);

                            if (i == 0) {
                                totalPeso[i].setText(formatter.format(total));
                                infoModel.setnNte1000p(String.valueOf(total));
                                mViewModel.setP1000((double) total, Integer.parseInt(qty));
                            } else if (i == 1) {
                                totalPeso[i].setText(formatter.format(total));
                                infoModel.setnNte0500p(String.valueOf(total));
                                mViewModel.setP500((double) total, Integer.parseInt(qty));
                            } else if (i == 2) {
                                totalPeso[i].setText(formatter.format(total));
                                infoModel.setnNte0200p(String.valueOf(total));
                                mViewModel.setP200((double) total, Integer.parseInt(qty));
                            } else if (i == 3) {
                                totalPeso[i].setText(formatter.format(total));
                                infoModel.setnNte0100p(String.valueOf(total));
                                mViewModel.setP100((double) total, Integer.parseInt(qty));
                            } else if (i == 4) {
                                totalPeso[i].setText(formatter.format(total));
                                infoModel.setnNte0050p(String.valueOf(total));
                                mViewModel.setP50((double) total, Integer.parseInt(qty));
                            } else if (i == 5) {
                                totalPeso[i].setText(formatter.format(total));
                                infoModel.setnNte0020p(String.valueOf(total));
                                mViewModel.setP20((double) total, Integer.parseInt(qty));
                            }

                        }

                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        }

//        lblPesoTotal.setText(formatter.format(PesoTotal));
        //Coin Bill Computation
        int a = 0;
        for (a = 0; a < qtyCoin.length; a++) {
            final int b = a;
            final String cpricecoin = String.valueOf(cpriceCoin[b].getText());
            int finalA = a;
            qtyCoin[a].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    try {
                        Double totalcoin = 0.0;
                        int totalcoins = 0;
                        String coinprice = "", qtycoins = "";
                        coinprice = String.valueOf(cpriceCoin[b].getText());
                        qtycoins = String.valueOf(qtyCoin[b].getText());
                        if (qtycoins.isEmpty()) {
                            totalCoin[b].setText("0");
                            if (b == 0) {
                                mViewModel.setP10((double) totalcoins, Integer.parseInt(qtycoins));
                            } else if (b == 1) {
                                mViewModel.setP5((double) totalcoins, Integer.parseInt(qtycoins));
                            } else if (b == 2) {
                                mViewModel.setP1((double) totalcoins, Integer.parseInt(qtycoins));
                            } else if (b == 3) {
                                mViewModel.setC1(Double.valueOf(totalcoin), Integer.parseInt(qtycoins));
                            } else if (b == 4) {
                                mViewModel.setC25(Double.valueOf(totalcoin), Integer.parseInt(qtycoins));
                            } else if (b == 5) {
                                mViewModel.setC10(Double.valueOf(totalcoin), Integer.parseInt(qtycoins));
                            } else if (b == 6) {
                                mViewModel.setC5(Double.valueOf(totalcoin), Integer.parseInt(qtycoins));
                            }
                        } else {
                            if (b <= 2) {
                                totalcoins = Integer.parseInt(coinprice.substring(1)) * Integer.parseInt(qtycoins);
                                totalCoin[b].setText(formatter.format(totalcoins));
                            } else {
                                totalcoin = (Double.parseDouble(coinprice.substring(1)) * Integer.parseInt(qtycoins)) / 100;
                                totalCoin[b].setText(formatter.format(totalcoin));
                            }
                            if (b == 0) {
                                totalCoin[b].setText(formatter.format(totalcoins));
                                mViewModel.setP10((double) totalcoins, Integer.parseInt(qtycoins));
                            } else if (b == 1) {
                                totalCoin[b].setText(formatter.format(totalcoins));
                                mViewModel.setP5((double) totalcoins, Integer.parseInt(qtycoins));
                            } else if (b == 2) {
                                totalCoin[b].setText(formatter.format(totalcoins));
                                mViewModel.setP1((double) totalcoins, Integer.parseInt(qtycoins));
                            } else if (b == 3) {
                                totalCoin[b].setText(formatter.format(totalcoin));
                                mViewModel.setC25(Double.valueOf(totalcoin), Integer.parseInt(qtycoins));
                            } else if (b == 4) {
                                totalCoin[b].setText(formatter.format(totalcoin));
                                mViewModel.setC10(Double.valueOf(totalcoin), Integer.parseInt(qtycoins));
                            } else if (b == 5) {
                                totalCoin[b].setText(formatter.format(totalcoin));
                                mViewModel.setC5(Double.valueOf(totalcoin), Integer.parseInt(qtycoins));
                            } else if (b == 6) {
                                totalCoin[b].setText(formatter.format(totalcoin));
                                mViewModel.setC1(Double.valueOf(totalcoin), Integer.parseInt(qtycoins));
                            }
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    }

                }

                @Override
                public void afterTextChanged(Editable s) {
                }
            });
        }
        mViewModel.getPesoTotalAmount().observe(this, val -> lblPesoTotal.setText(formatter.format(val)));
        mViewModel.getCoinsTotalAmount().observe(this, val -> lblCoinsTotal.setText(formatter.format(val)));
        mViewModel.getGrandTotalAmount().observe(this, val -> lblGrandTotal.setText(formatter.format(val)));
        btnNext.setOnClickListener(v->{
            if(lblGrandTotal.getText().toString().equalsIgnoreCase("0.00")) {
                GToast.CreateMessage(this, "Cash Details are Empty.", GToast.ERROR).show();
            } else {
                Intent intent = new Intent(Activity_CashCounter.this, Activity_CashCountSubmit.class);
                intent.putExtra("params", String.valueOf(mViewModel.getJsonData().getValue()));
                intent.putExtra("BranchCd", BranchCd);
                startActivity(intent);
//                poMessage.initDialog();
//                poMessage.setTitle("Collection Remittance");
//                poMessage.setMessage(mViewModel.getJsonData().getValue().toString());
//                poMessage.setPositiveButton("Okay", (view, dialog) -> {
//                    dialog.dismiss();
//
//                });
//                poMessage.show();
            }
        });
    }

    public void initWidgets(){
        Toolbar toolbar = findViewById(R.id.toolbar_cashCounter);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        lblBranch = findViewById(R.id.lbl_headerBranch);
        lblAddxx = findViewById(R.id.lbl_headerAddress);
        lblDate = findViewById(R.id.lbl_headerDate);
        btnNext = findViewById(R.id.btn_cash_count);
        lblPesoTotal = findViewById(R.id.lblPesoTotal);
        lblCoinsTotal = findViewById(R.id.lblCoinsTotal);
        lblGrandTotal = findViewById(R.id.lblGrandTotal);
        qtyPeso = new TextInputEditText[] {
                findViewById(R.id.edt1000p),
                findViewById(R.id.edt500p),
                findViewById(R.id.edt200p),
                findViewById(R.id.edt100p),
                findViewById(R.id.edt50p),
                findViewById(R.id.edt20p)
        };
        cprice = new TextView[] {
               findViewById(R.id.tv1000p),
               findViewById(R.id.tv500p),
               findViewById(R.id.tv200p),
               findViewById(R.id.tv100p),
               findViewById(R.id.tv50p),
               findViewById(R.id.tv20p)
        };
        totalPeso = new TextInputEditText[] {
                findViewById(R.id.edtTotal1000p),
                findViewById(R.id.edtTotal500p),
                findViewById(R.id.edtTotal200p),
                findViewById(R.id.edtTotal100p),
                findViewById(R.id.edtTotal50p),
                findViewById(R.id.edtTotal20p)
        };

        qtyCoin = new TextInputEditText[] {
                findViewById(R.id.edt10pc),
                findViewById(R.id.edt5pc),
                findViewById(R.id.edt1pc),
                findViewById(R.id.edt25cc),
                findViewById(R.id.edt10cc),
                findViewById(R.id.edt5cc),
                findViewById(R.id.edt1cc),
        };
        cpriceCoin = new TextView[] {
               findViewById(R.id.tv10pc),
               findViewById(R.id.tv5pc),
               findViewById(R.id.tv1pc),
               findViewById(R.id.tv25cc),
               findViewById(R.id.tv10cc),
               findViewById(R.id.tv5cc),
                findViewById(R.id.tv1cc)
        };
        totalCoin = new TextInputEditText[] {
                findViewById(R.id.edtTotal10pc),
                findViewById(R.id.edtTotal5pc),
                findViewById(R.id.edtTotal1pc),
                findViewById(R.id.edtTotal25cc),
                findViewById(R.id.edtTotal10cc),
                findViewById(R.id.edtTotal5cc),
                findViewById(R.id.edtTotal1cc)
        };
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finishActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finishActivity();
    }

    private void finishActivity(){
        if(cancelable) {
            finish();
        } else {
            poMessage.initDialog();
            poMessage.setTitle("Cash Count");
            poMessage.setMessage("To finish your selfie log. Please finish cash count entry.");
            poMessage.setPositiveButton("Okay", (view, dialog) -> dialog.dismiss());
            poMessage.show();
        }
    }

}