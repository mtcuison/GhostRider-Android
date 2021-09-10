/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.app
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.ghostrider.epacss.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GRider.Database.Entities.EEmployeeRole;
import org.rmj.g3appdriver.GRider.Database.Repositories.REmployeeRole;
import org.rmj.guanzongroup.ghostrider.epacss.Service.InternetStatusReciever;

import java.util.List;

public class VMMainActivity extends AndroidViewModel {
    private static final String TAG = "GRider Main Activity";
    private final Application app;
    private final InternetStatusReciever poNetRecvr;
    private final REmployeeRole poRole;

    public VMMainActivity(@NonNull Application application) {
        super(application);
        this.app = application;
        this.poNetRecvr = new InternetStatusReciever(app);
        this.poRole = new REmployeeRole(application);
        setupLocal();
    }

    public InternetStatusReciever getInternetReceiver(){
        return poNetRecvr;
    }

    public LiveData<List<EEmployeeRole>> getEmployeeRole(){
        return poRole.getEmployeeRoles();
    }

    public LiveData<List<EEmployeeRole>> getChildRoles(){
        return poRole.getChildRoles();
    }

    private void setupLocal(){
        EEmployeeRole loRole = new EEmployeeRole();
        loRole.setProdctID("gRider");
        loRole.setUserIDxx("GAP021002961");
        loRole.setObjectNm("Samsung Knox");
        loRole.setHasChild("1");
        loRole.setObjectTP("M");
        loRole.setRecdStat("1");
        poRole.InsertEmployeeRole(loRole);

        loRole = new EEmployeeRole();
        loRole.setProdctID("gRider");
        loRole.setUserIDxx("GAP021002961");
        loRole.setObjectNm("Daily Collection Plan");
        loRole.setHasChild("1");
        loRole.setObjectTP("M");
        loRole.setRecdStat("1");
        poRole.InsertEmployeeRole(loRole);

        loRole = new EEmployeeRole();
        loRole.setProdctID("gRider");
        loRole.setUserIDxx("GAP021002961");
        loRole.setObjectNm("Credit Application");
        loRole.setHasChild("1");
        loRole.setObjectTP("M");
        loRole.setRecdStat("1");
        poRole.InsertEmployeeRole(loRole);

        loRole = new EEmployeeRole();
        loRole.setProdctID("gRider");
        loRole.setUserIDxx("GAP021002961");
        loRole.setObjectNm("PET Manager");
        loRole.setHasChild("1");
        loRole.setObjectTP("M");
        loRole.setRecdStat("1");
        poRole.InsertEmployeeRole(loRole);

        loRole = new EEmployeeRole();
        loRole.setProdctID("gRider");
        loRole.setUserIDxx("GAP021002961");
        loRole.setObjectNm("Health Checklist");
        loRole.setHasChild("0");
        loRole.setObjectTP("M");
        loRole.setRecdStat("1");
        poRole.InsertEmployeeRole(loRole);

        loRole = new EEmployeeRole();
        loRole.setProdctID("gRider");
        loRole.setUserIDxx("GAP021002961");
        loRole.setObjectNm("CI Evaluation");
        loRole.setHasChild("1");
        loRole.setObjectTP("M");
        loRole.setRecdStat("1");
        poRole.InsertEmployeeRole(loRole);

        EEmployeeRole loChild = new EEmployeeRole();
        loChild.setProdctID("gRider");
        loChild.setUserIDxx("GAP021002961");
        loChild.setParentxx("Samsung Knox");
        loChild.setObjectNm("Unlock");
        loChild.setRecdStat("1");
        loChild.setObjectTP("M");
        loChild.setHasChild("0");
        poRole.InsertEmployeeRole(loChild);

        loChild.setProdctID("gRider");
        loChild.setUserIDxx("GAP021002961");
        loChild.setParentxx("Samsung Knox");
        loChild.setObjectNm("Get PIN");
        loChild.setRecdStat("1");
        loChild.setObjectTP("M");
        loChild.setHasChild("0");
        poRole.InsertEmployeeRole(loChild);

        loChild.setProdctID("gRider");
        loChild.setUserIDxx("GAP021002961");
        loChild.setParentxx("Samsung Knox");
        loChild.setObjectNm("Get Offline PIN");
        loChild.setRecdStat("1");
        loChild.setObjectTP("M");
        loChild.setHasChild("0");
        poRole.InsertEmployeeRole(loChild);

        loChild.setProdctID("gRider");
        loChild.setUserIDxx("GAP021002961");
        loChild.setParentxx("Samsung Knox");
        loChild.setObjectNm("Check Status");
        loChild.setRecdStat("1");
        loChild.setObjectTP("M");
        loChild.setHasChild("0");
        poRole.InsertEmployeeRole(loChild);

        loChild.setProdctID("gRider");
        loChild.setUserIDxx("GAP021002961");
        loChild.setParentxx("Daily Collection Plan");
        loChild.setObjectNm("Collection List");
        loChild.setRecdStat("1");
        loChild.setObjectTP("M");
        loChild.setHasChild("0");
        poRole.InsertEmployeeRole(loChild);

        loChild.setProdctID("gRider");
        loChild.setUserIDxx("GAP021002961");
        loChild.setParentxx("Daily Collection Plan");
        loChild.setObjectNm("Transaction Log");
        loChild.setRecdStat("1");
        loChild.setObjectTP("M");
        loChild.setHasChild("0");
        poRole.InsertEmployeeRole(loChild);

        loChild.setProdctID("gRider");
        loChild.setUserIDxx("GAP021002961");
        loChild.setParentxx("Credit Application");
        loChild.setObjectNm("Loan Application");
        loChild.setRecdStat("1");
        loChild.setObjectTP("M");
        loChild.setHasChild("0");
        poRole.InsertEmployeeRole(loChild);

        loChild.setProdctID("gRider");
        loChild.setUserIDxx("GAP021002961");
        loChild.setParentxx("Credit Application");
        loChild.setObjectNm("User Application List");
        loChild.setRecdStat("1");
        loChild.setObjectTP("M");
        loChild.setHasChild("0");
        poRole.InsertEmployeeRole(loChild);

        loChild.setProdctID("gRider");
        loChild.setUserIDxx("GAP021002961");
        loChild.setParentxx("Credit Application");
        loChild.setObjectNm("Branch Application List");
        loChild.setRecdStat("1");
        loChild.setObjectTP("M");
        loChild.setHasChild("0");
        poRole.InsertEmployeeRole(loChild);

        loChild.setProdctID("gRider");
        loChild.setUserIDxx("GAP021002961");
        loChild.setParentxx("Credit Application");
        loChild.setObjectNm("Document Scanner");
        loChild.setRecdStat("1");
        loChild.setObjectTP("M");
        loChild.setHasChild("0");
        poRole.InsertEmployeeRole(loChild);

        loChild.setProdctID("gRider");
        loChild.setUserIDxx("GAP021002961");
        loChild.setParentxx("PET Manager");
        loChild.setObjectNm("Leave Application");
        loChild.setRecdStat("1");
        loChild.setObjectTP("M");
        loChild.setHasChild("0");
        poRole.InsertEmployeeRole(loChild);

        loChild.setProdctID("gRider");
        loChild.setUserIDxx("GAP021002961");
        loChild.setParentxx("PET Manager");
        loChild.setObjectNm("Business Trip");
        loChild.setRecdStat("1");
        loChild.setObjectTP("M");
        loChild.setHasChild("0");
        poRole.InsertEmployeeRole(loChild);
//
//        loChild.setProdctID("gRider");
//        loChild.setUserIDxx("GAP021002961");
//        loChild.setParentxx("PET Manager");
//        loChild.setObjectNm("Selfie Log");
//        loChild.setRecdStat("1");
//        loChild.setObjectTP("M");
//        loChild.setHasChild("0");
//        poRole.InsertEmployeeRole(loChild);
//
//        loChild.setProdctID("gRider");
//        loChild.setUserIDxx("GAP021002961");
//        loChild.setParentxx("PET Manager");
//        loChild.setObjectNm("Application Approval");
//        loChild.setRecdStat("1");
//        loChild.setObjectTP("M");
//        loChild.setHasChild("0");
//        poRole.InsertEmployeeRole(loChild);
    }
}
