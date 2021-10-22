/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.settings
 * Electronic Personnel Access Control Security System
 * project file created : 6/23/21 2:25 PM
 * project file last modified : 6/23/21 2:25 PM
 */

package org.rmj.guanzongroup.ghostrider.settings.ViewModel;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DRawDao;
import org.rmj.g3appdriver.GRider.Database.Repositories.RRawData;
import org.rmj.g3appdriver.GRider.ImportData.ImportBarangay;
import org.rmj.g3appdriver.GRider.ImportData.ImportBranch;
import org.rmj.g3appdriver.GRider.ImportData.ImportBrand;
import org.rmj.g3appdriver.GRider.ImportData.ImportBrandModel;
import org.rmj.g3appdriver.GRider.ImportData.ImportCategory;
import org.rmj.g3appdriver.GRider.ImportData.ImportCountry;
import org.rmj.g3appdriver.GRider.ImportData.ImportDataCallback;
import org.rmj.g3appdriver.GRider.ImportData.ImportFileCode;
import org.rmj.g3appdriver.GRider.ImportData.ImportInstance;
import org.rmj.g3appdriver.GRider.ImportData.ImportMcModelPrice;
import org.rmj.g3appdriver.GRider.ImportData.ImportMcTermCategory;
import org.rmj.g3appdriver.GRider.ImportData.ImportProvinces;
import org.rmj.g3appdriver.GRider.ImportData.ImportTown;
import org.rmj.g3appdriver.GRider.ImportData.Import_BankList;
import org.rmj.g3appdriver.GRider.ImportData.Import_Occupations;
import org.rmj.g3appdriver.GRider.ImportData.Import_Relation;
import org.rmj.g3appdriver.GRider.ImportData.Import_SysConfig;
import org.rmj.guanzongroup.ghostrider.settings.Objects.LocalData;

import java.util.ArrayList;
import java.util.List;

import static org.rmj.g3appdriver.utils.WebApi.URL_BRANCH_REMITTANCE_ACC;
import static org.rmj.g3appdriver.utils.WebApi.URL_DOWNLOAD_BANK_INFO;
import static org.rmj.g3appdriver.utils.WebApi.URL_DOWNLOAD_RELATION;
import static org.rmj.g3appdriver.utils.WebApi.URL_IMPORT_BARANGAY;
import static org.rmj.g3appdriver.utils.WebApi.URL_IMPORT_BRANCHES;
import static org.rmj.g3appdriver.utils.WebApi.URL_IMPORT_BRAND;
import static org.rmj.g3appdriver.utils.WebApi.URL_IMPORT_COUNTRY;
import static org.rmj.g3appdriver.utils.WebApi.URL_IMPORT_FILE_CODE;
import static org.rmj.g3appdriver.utils.WebApi.URL_IMPORT_MC_CATEGORY;
import static org.rmj.g3appdriver.utils.WebApi.URL_IMPORT_MC_MODEL;
import static org.rmj.g3appdriver.utils.WebApi.URL_IMPORT_MC_MODEL_PRICE;
import static org.rmj.g3appdriver.utils.WebApi.URL_IMPORT_OCCUPATIONS;
import static org.rmj.g3appdriver.utils.WebApi.URL_IMPORT_PROVINCE;
import static org.rmj.g3appdriver.utils.WebApi.URL_IMPORT_RAFFLE_BASIS;
import static org.rmj.g3appdriver.utils.WebApi.URL_IMPORT_TERM_CATEGORY;
import static org.rmj.g3appdriver.utils.WebApi.URL_IMPORT_TOWN;

public class VMLocalData extends AndroidViewModel {

    private final Application instance;
    private final RRawData poData;
    private List<LocalData> dataList = new ArrayList<>();
    private final MutableLiveData<List<LocalData>> loDataList = new MutableLiveData<>();

    private DRawDao.AppLocalData poAppData = new DRawDao.AppLocalData();

    public interface OnRefreshDataCallback{
        void OnLoad(String Title, String Message);
        void OnSuccess();
        void OnFailed();
    }

    public VMLocalData(@NonNull Application application) {
        super(application);
        this.instance = application;
        this.poData = new RRawData(application);
    }

    public LiveData<DRawDao.AppLocalData> getAppLocalData(){
        return poData.getAppLocalData();
    }

    public void setAppLocalData(DRawDao.AppLocalData localData){
        poAppData = localData;
        dataList.clear();
        loDataList.setValue(dataList);
        dataList.add(new LocalData("Branch Data", poAppData.Branch_Data, URL_IMPORT_BRANCHES));
        dataList.add(new LocalData("Barangay Data", poAppData.Barangay_Data, URL_IMPORT_BARANGAY));
        dataList.add(new LocalData("Town Data", poAppData.Town_Data, URL_IMPORT_TOWN));
        dataList.add(new LocalData("Province Data", poAppData.Province_Data, URL_IMPORT_PROVINCE));
        dataList.add(new LocalData("Country Data", poAppData.Country_Data, URL_IMPORT_COUNTRY));
        dataList.add(new LocalData("Mc Brand", poAppData.Mc_Brand, URL_IMPORT_BRAND));
        dataList.add(new LocalData("Mc Model", poAppData.Mc_Model, URL_IMPORT_MC_MODEL));
        dataList.add(new LocalData("Mc Category", poAppData.Mc_Category, URL_IMPORT_MC_CATEGORY));
        dataList.add(new LocalData("Mc Model Price", poAppData.Mc_Model_Price, URL_IMPORT_MC_MODEL_PRICE));
        dataList.add(new LocalData("Mc Term Category", poAppData.Mc_Term_Category, URL_IMPORT_TERM_CATEGORY));
        dataList.add(new LocalData("Occupation Data", poAppData.Occupation_Data, URL_IMPORT_OCCUPATIONS));
        dataList.add(new LocalData("Raffle Basis", poAppData.Raffle_Basis, URL_IMPORT_RAFFLE_BASIS));
        dataList.add(new LocalData("File Code", poAppData.File_Code, URL_IMPORT_FILE_CODE));
        dataList.add(new LocalData("Bank Data", poAppData.Bank_Data, URL_DOWNLOAD_BANK_INFO));
        dataList.add(new LocalData("Remittance Data", poAppData.Remittance_Data, URL_BRANCH_REMITTANCE_ACC));
        dataList.add(new LocalData("Relation Data", poAppData.Relation_Data, URL_DOWNLOAD_RELATION));
        loDataList.setValue(dataList);
    }

    public LiveData<List<LocalData>> getDataList(){
        return loDataList;
    }

    public void RefreshAllRecords(OnRefreshDataCallback callback){
        new RefreshAllRecordsTask(instance, callback).execute();
    }

    public static class RefreshAllRecordsTask extends AsyncTask<String, Void, String>{
        private final Application instance;
        private final OnRefreshDataCallback callback;

        ImportInstance[]  importInstances;

        public RefreshAllRecordsTask(Application application, OnRefreshDataCallback callback){
            this.instance = application;
            this.callback = callback;
            importInstances = new ImportInstance[]{
                    new Import_BankList(instance),
                    new ImportFileCode(instance),
                    new Import_Relation(instance),
                    new ImportBranch(instance),
                    new ImportBrand(instance),
                    new ImportBrandModel(instance),
                    new ImportCategory(instance),
                    new ImportProvinces(instance),
                    new ImportMcModelPrice(instance),
                    new ImportTown(instance),
                    new ImportBarangay(instance),
                    new ImportMcTermCategory(instance),
                    new ImportCountry(instance),
                    new Import_Occupations(instance),
                    new Import_SysConfig(instance)};
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            callback.OnLoad("Local Data", "Refreshing all local data. Please wait...");
        }

        @Override
        protected String doInBackground(String... strings) {
            String result;
            try {
                for (int x = 0; x < importInstances.length; x++) {
                    importInstances[x].ImportData(new ImportDataCallback() {
                        @Override
                        public void OnSuccessImportData() {

                        }

                        @Override
                        public void OnFailedImportData(String message) {

                        }
                    });
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                result = "success";
            } catch (Exception e){
                e.printStackTrace();
                result = "error";
            }
            return result;
        }

        @Override
        protected void onPostExecute(String integer) {
            super.onPostExecute(integer);
            if(integer.equalsIgnoreCase("success")) {
                callback.OnSuccess();
            } else {
                callback.OnFailed();
            }
        }
    }
}
