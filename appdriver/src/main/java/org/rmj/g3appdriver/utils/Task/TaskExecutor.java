package org.rmj.g3appdriver.utils.Task;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskExecutor {
    private static final String TAG = TaskExecutor.class.getSimpleName();

    private Object poResult;

    public TaskExecutor(){

    }

    public static void Execute(Object params, OnTaskExecuteListener mListener){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {
                handler.post(mListener::OnPreExecute);

                //Background work here...
                Object loResult = mListener.DoInBackground(params);

                handler.post(() -> mListener.OnPostExecute(loResult));
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    public static void Execute(Object params, OnDoBackgroundTaskListener mListener){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {
                //Background work here...
                Object loResult = mListener.DoInBackground(params);

                handler.post(() -> mListener.OnPostExecute(loResult));
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }
}
