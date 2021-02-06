package org.rmj.g3appdriver.utils;

public class ImeiValidator {
    private static int sumDig(int n){
        int a = 0;
        while (n>0){
            a = a + n % 10;
            n = n / 10;
        }
        return a;
    }

    public static boolean isValidImei(long n){
        String s = Long.toString(n);
        int len = s.length();

        if(len != 15){
            return false;
        }

        int sum = 0;
        for(int i = len; i >= 1; i--){
            int d = (int)(n % 10);

            if(i % 2 == 0)
                d = 2 * d;

            sum += sumDig(d);
            n = n /10;
        }

        return (sum % 10 == 0);
    }
}
