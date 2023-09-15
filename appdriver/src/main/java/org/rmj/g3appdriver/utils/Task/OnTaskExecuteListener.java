package org.rmj.g3appdriver.utils.Task;

public interface OnTaskExecuteListener {
    void OnPreExecute();
    Object DoInBackground(Object args);
    void OnPostExecute(Object object);
}
