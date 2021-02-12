package org.rmj.guanzongroup.ghostrider.dailycollectionplan.Data;

import android.app.Application;
import android.os.AsyncTask;

import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;

public class UploadCollection {
    private static final String TAG = UploadCollection.class.getSimpleName();
    private final Application instance;

    public UploadCollection(Application application){
        this.instance = application;
    }

    public void postApplication(){

    }


    private static class PostTask extends AsyncTask<EDCPCollectionDetail, Void, String>{
        private Application instance;

        public PostTask(Application instance) {
            this.instance = instance;
        }

        @Override
        protected String doInBackground(EDCPCollectionDetail... edcpCollectionDetails) {

            return null;
        }
    }
}
