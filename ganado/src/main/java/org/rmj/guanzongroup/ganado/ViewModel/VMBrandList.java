package org.rmj.guanzongroup.ganado.ViewModel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.rmj.g3appdriver.GCircle.room.Entities.EMcBrand;
import org.rmj.g3appdriver.GCircle.room.Repositories.RMcBrand;

import java.util.List;

public class VMBrandList extends AndroidViewModel {
    private static final String TAG = VMBrandList.class.getSimpleName();

    private final RMcBrand poProdct;

    private String message;


    public VMBrandList(@NonNull Application application) {
        super(application);
        this.poProdct = new RMcBrand(application);
    }

    public LiveData<List<EMcBrand>> getAllBrandName(){
        return poProdct.getAllBrandInfo();
    }

}
