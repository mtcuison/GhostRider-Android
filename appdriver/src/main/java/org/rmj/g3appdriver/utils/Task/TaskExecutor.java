package org.rmj.g3appdriver.utils.Task;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskExecutor {
    private static final String TAG = TaskExecutor.class.getSimpleName();

    private Object poResult;

    public TaskExecutor(){

    }

    public void Execute(Object params, OnTaskExecuteListener listener){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        listener.OnPreExecute();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                //Background work here...
                for(int x = 0; x < 10; x++){
                    Log.d(TAG, "Sample: " + x);
                }
            }
        });

        handler.post(new Runnable() {
            @Override
            public void run() {
                listener.OnPostExecute(poResult);
            }
        });
    }
}
