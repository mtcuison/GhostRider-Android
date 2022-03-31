package org.rmj.g3appdriver.utils;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Random;

public class GenerateID {

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static String GetRandomID(){
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }
}
