package org.rmj.g3appdriver.lib.Firebase;

import com.google.firebase.crashlytics.FirebaseCrashlytics;

public class CrashReportingUtil {

    public static void reportException(String userId, String errorMessage) {
        FirebaseCrashlytics crashlytics = FirebaseCrashlytics.getInstance();

        // Set user identifier (optional)
        if (userId != null && !userId.isEmpty()) {
            crashlytics.setUserId(userId);
        }

        // Log custom error message (optional)
        if (errorMessage != null && !errorMessage.isEmpty()) {
            crashlytics.log(errorMessage);
        }

        // Record a non-fatal exception
        crashlytics.recordException(new Exception(errorMessage));
    }
}