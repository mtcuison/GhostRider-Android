package org.rmj.g3appdriver.FacilityTrack.Personnel;

import static org.rmj.g3appdriver.etc.AppConstants.getLocalMessage;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.FacilityTrack.pojo.Personnel;

import java.util.ArrayList;
import java.util.List;

public class PersonnelMaster {
    private static final String TAG = PersonnelMaster.class.getSimpleName();

    private final Application instance;

    private String message;

    public PersonnelMaster(Application instance) {
        this.instance = instance;
    }

    public String getMessage() {
        return message;
    }

    public boolean ImportActivePersonnel(){
        try{

            return true;
        } catch (Exception e){
            e.printStackTrace();
            message = getLocalMessage(e);
            return false;
        }
    }

    public LiveData<List<Personnel>> GetPersonnelList(){
        MutableLiveData<List<Personnel>> loPersonnel = new MutableLiveData<>();
        List<Personnel> loList = new ArrayList<>();
        loList.add(new Personnel("MX0123456780",
                "Johnson, Emma",
                "1",
                ""));

        loList.add(new Personnel("MX0123456781",
                "Smith, Liam",
                "1",
                ""));

        loList.add(new Personnel("MX0123456782",
                "Brown, Olivia",
                "1",
                ""));

        loList.add(new Personnel("MX0123456783",
                "Davis, Noah",
                "0",
                ""));

        loList.add(new Personnel("MX0123456784",
                "Wilson, Ava",
                "0",
                ""));

        loList.add(new Personnel("MX0123456785",
                "Miller, Isabella",
                "0",
                ""));
        loPersonnel.postValue(loList);
        return loPersonnel;
    }
}
