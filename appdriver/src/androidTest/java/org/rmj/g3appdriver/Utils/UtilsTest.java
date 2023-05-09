package org.rmj.g3appdriver.Utils;

import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.json.JSONObject;




import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.rmj.g3appdriver.utils.Task.OnTaskExecuteListener;
import org.rmj.g3appdriver.utils.Task.TaskExecutor;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class UtilsTest {
    private static final String TAG = UtilsTest.class.getSimpleName();

    @Test
    public void test01AsynckTask() {
//        new TaskExecutor().Execute(new JSONObject().toString(), new OnTaskExecuteListener() {
//            @Override
//            public void OnPreExecute() {
//                Log.d(TAG, "Task started.");
//            }
//
//            @Override
//            public void OnPostExecute(Object object) {
//                Log.d(TAG, "Task finished.");
//                boolean loResult = (boolean) object;
//            }
//        });
    }
}
