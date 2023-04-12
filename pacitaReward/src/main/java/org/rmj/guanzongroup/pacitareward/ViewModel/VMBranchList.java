package org.rmj.guanzongroup.pacitareward.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.dev.Database.Entities.EBranchInfo;
import org.rmj.g3appdriver.lib.GawadPacita.Obj.Pacita;
import org.rmj.g3appdriver.utils.ConnectionUtil;

import java.util.List;

public class VMBranchList extends AndroidViewModel {

    private final Pacita poSys;
    private final ConnectionUtil poConn;


    public VMBranchList(@NonNull Application application) {
        super(application);

        poSys = new Pacita(application);
        poConn = new ConnectionUtil(application);
    }

    public LiveData<List<EBranchInfo>> getBranchlist(){
        return poSys.GetBranchesList();
    }
}
