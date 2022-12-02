/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.approvalCode
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:18 PM
 */

package org.rmj.guanzongroup.ghostrider.approvalcode.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.sqlite.db.SimpleSQLiteQuery;

import org.rmj.appdriver.mob.base.MiscUtil;
import org.rmj.appdriver.mob.base.SQLUtil;
import org.rmj.g3appdriver.GRider.Database.Entities.ESCA_Request;
import org.rmj.g3appdriver.GRider.Database.Repositories.RApprovalCode;
import org.rmj.g3appdriver.GRider.Etc.SessionManager;
import org.rmj.g3appdriver.etc.AppConfigPreference;

import java.util.List;

public class VMApprovalSelection extends AndroidViewModel {
    public static final String TAG = VMApprovalSelection.class.getSimpleName();
    private final RApprovalCode poApprvlR;
    private final SessionManager poSession;
    private final AppConfigPreference poSharedx;
    //private final ApprovalCode poApprovl;
    private LiveData<List<ESCA_Request>> paAuthLst;

    public VMApprovalSelection(@NonNull Application application) {
        super(application);
        this.poSession = new SessionManager(application);
        this.poSharedx = AppConfigPreference.getInstance(application);
        this.poApprvlR = new RApprovalCode(application);
    }

    public LiveData<List<ESCA_Request>> getReferenceAuthList(String Type){
        String lsSqlQryxx = "SELECT *" +
                            " FROM xxxSCA_Request" +
                            " WHERE cSCATypex = " + SQLUtil.toSQL(Type)  +
                                " AND cRecdStat = '1'" +
                            " ORDER BY sSCATitle";

        String lsCondition = "";
        String lsEmpLvID = poSession.getEmployeeLevel();
        String lsDeptIDx = poSession.getDeptID();
        String lsPostion = poSession.getPositionID();

        if (lsEmpLvID.equals("4")){
            lsCondition  = "cAreaHead = '1'";
        } else{
            switch (lsDeptIDx){
                case "021": //hcm
                    lsCondition = "cHCMDeptx = '1'"; break;
                case "022": //css
                    lsCondition = "cCSSDeptx = '1'"; break;
                case "034": //cm
                    lsCondition = "cComplnce = '1'"; break;
                case "025": //m&p
                    lsCondition = "cMktgDept = '1'"; break;
                case "027": //asm
                    lsCondition = "cASMDeptx = '1'"; break;
                case "035": //tele
                    lsCondition = "cTLMDeptx = '1'"; break;
                case "024": //scm
                    lsCondition = "cSCMDeptx = '1'"; break;
                case "026": //mis
                    break;
                case "015": //sales
                    if (lsPostion.equals("091") || lsPostion.equals("299") || lsPostion.equals("298")){
                        //field specialist
                        lsCondition = "sSCACodex = 'CA'";
                    } else {
                        lsCondition = "0=1";
                    }
                    break;
                default: lsCondition = "0=1";
            }
        }

        if (!lsCondition.isEmpty()) lsSqlQryxx= MiscUtil.addCondition(lsSqlQryxx, lsCondition);

        return poApprvlR.getAuthorizedFeatures(new SimpleSQLiteQuery(lsSqlQryxx));
    }
}
