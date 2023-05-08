package org.rmj.g3appdriver.utils.Task;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.rmj.g3appdriver.utils.Task.OnTaskExecuteListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskExecutor {
    private static final String TAG = TaskExecutor.class.getSimpleName();

    private final OnTaskExecuteListener mListener;

    public TaskExecutor(OnTaskExecuteListener listener){
        this.mListener = listener;
    }

    public void Execute(Object params){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {
                //start handler
                handler.post(mListener::OnPreExecute);

                //Background work here...
                Object loResult = mListener.DoInBackground(params);

                //result handler
                handler.post(() -> mListener.OnPostExecute(loResult));
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }
}
