package org.rmj.g3appdriver.utils;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.regex.Pattern;

public class StringHelperMisc {
    private static final char[] HEX_CHARS = "0123456789abcdef".toCharArray();
    private static final Map<String, Character> lookupHex = new HashMap<String, Character>();

    public static boolean isNumeric(String str) {
        try {  
            double lnValue = Double.parseDouble(str);
        } catch(NumberFormatException nfe) {
            return false;  
        }  

        return true;  
    }

    public static char ASCIIToChar(final int ascii){
        return (char)ascii;		
    }
    
    public static String Bin2Dec (String bin) {
        return new BigInteger(bin,2).toString();
    }
    
    public static int CharToASCII(final char character){
        return (int)character;
    }

    public static String Dec2Bin (int value) {
        return Integer.toString(value , 2/* radix */ );
    }
    
    public String Dec2Hex(int dec){
        return Integer.toString(dec, 16);
    }

    public static String Hex(int value){
        return Integer.toString(value , 16 /* radix */ );
    }

    public static int Hex2Dec(String value){
        return Integer.parseInt(value.trim(), 16 /* radix */ );
    }

    public static String Hex2Str(String hex) {
        StringBuilder sb = new StringBuilder();
        for (int count = 0; count < hex.length() - 1; count += 2) {
            String output = hex.substring(count, (count + 2));
            sb.append((char)lookupHex.get(output));
        }
        return sb.toString();
    }
    
    public static boolean isDate(CharSequence date) {
    	 
        // some regular expression
        String time = "(\\s(([01]?\\d)|(2[0123]))[:](([012345]\\d)|(60))"
            + "[:](([012345]\\d)|(60)))?"; // with a space before, zero or one time
     
        // no check for leap years (Schaltjahr)
        // and 31.02.2006 will also be correct
        String day = "(([12]\\d)|(3[01])|(0?[1-9]))"; // 01 up to 31
        String month = "((1[012])|(0\\d))"; // 01 up to 12
        String year = "\\d{4}";
     
        // define here all date format
        ArrayList<Pattern> patterns = new ArrayList<Pattern>();
        patterns.add(Pattern.compile(day + "[-.]" + month + "[-.]" + year + time));
        patterns.add(Pattern.compile(year + "-" + month + "-" + day + time));
        // here you can add more date formats if you want
     
        // check dates
        for (Pattern p : patterns)
          if (p.matcher(date).matches())
            return true;
     
        return false;
     
    }
	
    public static int Rand(int fnMin, int fnMax){
        Random rand = new Random();

        int n = fnMax - fnMin + 1;
        int i = rand.nextInt() % n;
        if (i < 0)
                i = -i;
        return fnMin + i;
    }
	
    public static Integer TotalStr(String fsStr){
        fsStr = fsStr.replace(" ", "");
        fsStr = fsStr.replace(",", "");

        int lnTotal = 0;
        String lsTemp = "";

        for (int lnCtr = 0; lnCtr <= fsStr.length()-1; lnCtr++){			
            lsTemp = fsStr.substring(lnCtr);
            lnTotal = lnTotal + (int) lsTemp.charAt(0);
        } 
        return lnTotal;
    }
    
    private static String asHex(byte[] buf){
        char[] chars = new char[2 * buf.length];
        for (int i = 0; i < buf.length; ++i)
        {
            chars[2 * i] = HEX_CHARS[(buf[i] & 0xF0) >>> 4];
            chars[2 * i + 1] = HEX_CHARS[buf[i] & 0x0F];
        }
        return new String(chars);
    }
}