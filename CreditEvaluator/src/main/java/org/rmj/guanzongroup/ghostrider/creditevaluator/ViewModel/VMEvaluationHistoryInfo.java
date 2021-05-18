/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.CreditEvaluator
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.creditevaluator.ViewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DBranchLoanApplication;
import org.rmj.g3appdriver.GRider.Database.Entities.ECIEvaluation;
import org.rmj.g3appdriver.GRider.Database.Repositories.RBranchLoanApplication;
import org.rmj.g3appdriver.GRider.Database.Repositories.RCIEvaluation;
import org.rmj.g3appdriver.GRider.Database.Repositories.RRelation;
import org.rmj.guanzongroup.ghostrider.creditevaluator.Model.EvaluationHistoryInfoModel;

import java.util.ArrayList;
import java.util.List;

public class VMEvaluationHistoryInfo extends AndroidViewModel {
    private static final String TAG = VMEvaluationHistoryInfo.class.getSimpleName();
    private final RCIEvaluation poInvestx;
    private final RRelation poRelatex;

    private final MutableLiveData<String> psTransNo = new MutableLiveData<>();

    public VMEvaluationHistoryInfo(@NonNull Application application) {
        super(application);
        this.poInvestx = new RCIEvaluation(application);
        this.poRelatex = new RRelation(application);
    }

    public void setTransNo(String fsTransNo) {
        this.psTransNo.setValue(fsTransNo);
    }

    public LiveData<ECIEvaluation> getAllDoneCiInfo() {
        return poInvestx.getAllDoneCiInfo(psTransNo.getValue());
    }

    public List<EvaluationHistoryInfoModel> onFetchCreditEvaluationDetail(ECIEvaluation foCiDetl) {
        // new CustomerEvaluationDetailTask(this.instance, poRelatex, fmListenr).execute(foCiDetl);
        ECIEvaluation loDetail = foCiDetl;
        List<EvaluationHistoryInfoModel> loListDetl = new ArrayList<>();
        try {
            // Headers
            loListDetl.add(new EvaluationHistoryInfoModel(true,"Residence Information", "", ""));
            loListDetl.add(new EvaluationHistoryInfoModel(false, "", "Landmark", loDetail.getLandMark()));
            loListDetl.add(new EvaluationHistoryInfoModel(false, "", "House Ownership", parseHouseOwn(loDetail.getOwnershp())));
            loListDetl.add(new EvaluationHistoryInfoModel(false,"", "Households", parseHouseHold(loDetail.getOwnOther())));
            loListDetl.add(new EvaluationHistoryInfoModel(false, "", "Type of House", parseHouseType(loDetail.getHouseTyp())));
            loListDetl.add(new EvaluationHistoryInfoModel(false, "", "With Safe Garage", getAnswer(loDetail.getGaragexx())));
            loListDetl.add(new EvaluationHistoryInfoModel(false, "", "Do they have other address or residence", getAnswer(loDetail.getHasOther())));

            loListDetl.add(new EvaluationHistoryInfoModel(true, "Disbursement Information", "", ""));
            loListDetl.add(new EvaluationHistoryInfoModel(false,"", "Water", parseAmtToString(loDetail.getWaterBil())));
            loListDetl.add(new EvaluationHistoryInfoModel(false,"", "Electricity", parseAmtToString(loDetail.getElctrcBl())));
            loListDetl.add(new EvaluationHistoryInfoModel(false,"", "Food", parseAmtToString(loDetail.getFoodAllw())));
            loListDetl.add(new EvaluationHistoryInfoModel(false,"", "Loans", parseAmtToString(loDetail.getLoanAmtx())));
            loListDetl.add(new EvaluationHistoryInfoModel(false,"", "Education", parseAmtToString(loDetail.getEducExpn())));
            loListDetl.add(new EvaluationHistoryInfoModel(false,"", "Others", parseAmtToString(loDetail.getOthrExpn())));

            loListDetl.add(new EvaluationHistoryInfoModel(true, "Barangay Record Information", "", ""));
            loListDetl.add(new EvaluationHistoryInfoModel(true, "Neighbor 1", "", ""));
            loListDetl.add(new EvaluationHistoryInfoModel(false,"", "Name", loDetail.getNeighbr1()));
            loListDetl.add(new EvaluationHistoryInfoModel(false,"", "Relation", parseRelation(loDetail.getReltnCD1())));
            loListDetl.add(new EvaluationHistoryInfoModel(false,"", "Address", loDetail.getAddress1()));
            loListDetl.add(new EvaluationHistoryInfoModel(false,"", "Contact No.", loDetail.getMobileN1()));
            loListDetl.add(new EvaluationHistoryInfoModel(false,"", "Feedback", getVibes(loDetail.getFeedBck1())));
            loListDetl.add(new EvaluationHistoryInfoModel(false,"", "Remarks", loDetail.getFBRemrk1()));

            loListDetl.add(new EvaluationHistoryInfoModel(true, "Neighbor 2", "", ""));
            loListDetl.add(new EvaluationHistoryInfoModel(false,"", "Name", loDetail.getNeighbr2()));
            loListDetl.add(new EvaluationHistoryInfoModel(false,"", "Relation", parseRelation(loDetail.getReltnCD2())));
            loListDetl.add(new EvaluationHistoryInfoModel(false,"", "Address", loDetail.getAddress2()));
            loListDetl.add(new EvaluationHistoryInfoModel(false,"", "Contact No.", loDetail.getMobileN2()));
            loListDetl.add(new EvaluationHistoryInfoModel(false,"", "Feedback", getVibes(loDetail.getFeedBck2())));
            loListDetl.add(new EvaluationHistoryInfoModel(false,"", "Remarks", loDetail.getFBRemrk2()));

            loListDetl.add(new EvaluationHistoryInfoModel(true, "Neighbor 3", "", ""));
            loListDetl.add(new EvaluationHistoryInfoModel(false,"", "Name", loDetail.getNeighbr3()));
            loListDetl.add(new EvaluationHistoryInfoModel(false,"", "Relation", parseRelation(loDetail.getReltnCD3())));
            loListDetl.add(new EvaluationHistoryInfoModel(false,"", "Address", loDetail.getAddress3()));
            loListDetl.add(new EvaluationHistoryInfoModel(false,"", "Contact No.", loDetail.getMobileN3()));
            loListDetl.add(new EvaluationHistoryInfoModel(false,"", "Feedback", getVibes(loDetail.getFeedBck3())));
            loListDetl.add(new EvaluationHistoryInfoModel(false,"", "Remarks", loDetail.getFBRemrk3()));

            loListDetl.add(new EvaluationHistoryInfoModel(true, "Character Traits", "", ""));
            loListDetl.add(new EvaluationHistoryInfoModel(false,"", "Gambler", getAnswer(loDetail.getGamblerx())));
            loListDetl.add(new EvaluationHistoryInfoModel(false,"",  "Womanizer", getAnswer(loDetail.getWomanizr())));
            loListDetl.add(new EvaluationHistoryInfoModel(false,"",  "Heavy Borrower", getAnswer(loDetail.getHvyBrwer())));
            loListDetl.add(new EvaluationHistoryInfoModel(false,"",  "With Repossessions", getAnswer(loDetail.getWithRepo())));
            loListDetl.add(new EvaluationHistoryInfoModel(false,"",  "With Mortgage Properties", getAnswer(loDetail.getWithMort())));
            loListDetl.add(new EvaluationHistoryInfoModel(false,"",  "Arrogant", getAnswer(loDetail.getArrogant())));
            loListDetl.add(new EvaluationHistoryInfoModel(false,"",  "Others", getAnswer(loDetail.getOtherBad())));

            return loListDetl;

        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String getAnswer(String fsAnswer) {
        if(fsAnswer.equalsIgnoreCase("0")) {
            return "No";
        }
        return "Yes";
    }

    private String getVibes(String fsFeedBck) {
        if(fsFeedBck.equalsIgnoreCase("0")) {
            return "Negative Feedback";
        }
        return "Positive Feedback";
    }

    private String parseHouseOwn(String fsOwnrshp) {
        if(fsOwnrshp.equalsIgnoreCase("0")) {
            return "Owned";
        } else if(fsOwnrshp.equalsIgnoreCase("1")) {
            return "Rent";
        } else if(fsOwnrshp.equalsIgnoreCase("2")) {
            return "Care-Taker";
        }
        return null;
    }

    private String parseHouseHold(String sHousehld) {
        if(sHousehld.equalsIgnoreCase("0")) {
            return "Living With Family";
        } else if(sHousehld.equalsIgnoreCase("1")) {
            return "Living With Family(Parents & Siblings)";
        } else if(sHousehld.equalsIgnoreCase("2")) {
            return "Living With Relatives";
        }
        return null;
    }

    private String parseHouseType(String fsHouseTp) {
        if(fsHouseTp.equalsIgnoreCase("0")) {
            return "Concrete";
        } else if(fsHouseTp.equalsIgnoreCase("1")) {
            return "Combination(Wood & Concrete)";
        } else if(fsHouseTp.equalsIgnoreCase("2")) {
            return "Wood/Nipa";
        }
        return null;
    }

    private String parseAmtToString(String fsAmount) {
        String lsAmount = fsAmount.replace(",","");
        return "₱" + Double.parseDouble(lsAmount);
    }

    private String parseRelation(String fsRelatId) {
        try {
            return poRelatex.getRelationFromId(fsRelatId);
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
