package org.rmj.g3appdriver.utils.Task;

public interface OnLoadApplicationListener {
    Object DoInBackground();
    void OnProgress(int progress);
    void OnPostExecute(Object object);
}
