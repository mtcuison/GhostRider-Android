package org.rmj.g3appdriver.utils.Task;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TaskExecutor {
    private static final String TAG = TaskExecutor.class.getSimpleName();

    private Object poResult;

    private OnLoadApplicationListener onLoadApplicationListener;

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

    public void setOnLoadApplicationListener(OnLoadApplicationListener onLoadApplicationListener) {
        this.onLoadApplicationListener = onLoadApplicationListener;
    }

    public void Execute(){
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(() -> {
            try {
                //Background work here...
                Object loResult = onLoadApplicationListener.DoInBackground();

                handler.post(() -> onLoadApplicationListener.OnPostExecute(loResult));
            } catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    public interface OnShowProgress{
        void OnProgress();
    }

    public static void ShowProgress(OnShowProgress listener){
        Handler handler = new Handler(Looper.getMainLooper());

        handler.post(() -> listener.OnProgress());
    }

    public void publishProgress(int progress){
        Handler handler = new Handler(Looper.getMainLooper());

        handler.post(() -> onLoadApplicationListener.OnProgress(progress));
    }
}
