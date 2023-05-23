package org.rmj.g3appdriver.lib.Ganado.model;


public class GConstants {
    public static String[] CATEGORY = {
            "AUTO",
            "MOBILE PHONE",
            "MOTORCYCLE"};

    public static String[] MC_BRAND = {
            "HONDA",
            "KAWASAKI",
            "SUZUKI",
            "YAMAHA"};
    public static String getBrandID(String value){
        switch (value){
            case "0":
                return "M0W1001";
            case "1":
                return "M0W1009";
            case "2":
                return "M0W1002";
            case "3":
                return "M0W1003";
        }
        return "";
    }
}
