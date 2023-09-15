package org.rmj.g3appdriver.utils.Task;

public interface OnDoBackgroundTaskListener {
    Object DoInBackground(Object args);
    void OnPostExecute(Object object);
}
