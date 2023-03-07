package org.rmj.g3appdriver.lib.Notifications.Obj;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.DataAccessObject.DPayslip;
import org.rmj.g3appdriver.dev.Database.GGC_GriderDB;

import java.util.List;

public class Payslip {
    private static final String TAG = Payslip.class.getSimpleName();

    private final Application instance;

    private final DPayslip poDao;

    public Payslip(Application instance) {
        this.instance = instance;
        this.poDao = GGC_GriderDB.getInstance(instance).payslipDao();
    }

    public LiveData<List<DPayslip.Payslip>> GetPaySliplist(){
        return poDao.GetPaySlipList();
    }
}
