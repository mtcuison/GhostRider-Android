package org.rmj.guanzongroup.ghostrider.ahmonitoring;


import static org.junit.Assert.assertNotNull;

import android.location.Location;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(AndroidJUnit4.class)
public class CoordinatesToPosition {
    private static final String TAG = CoordinatesToPosition.class.getSimpleName();

    @Before
    public void setup() throws Exception{

    }

    @After
    public void finis() throws Exception{

    }

    @Test
    public void test01ConvertCoordinates() throws Exception{
        double lnLatitude = 122.6489887;
        double lnLongtude = 10.8484694;


        String lsResult = convertToDMS(lnLatitude, lnLongtude);
        assertNotNull(lsResult);
    }

    public String convertCoordDMS(double latitude, double longitude){
        try {
            int latSeconds = (int) Math.round(latitude * 3600);
            int latDegrees = latSeconds / 3600;
            latSeconds = Math.abs(latSeconds % 3600);
            int latMinutes = latSeconds / 60;
            latSeconds %= 60;

            int longSeconds = (int) Math.round(longitude * 3600);
            int longDegrees = longSeconds / 3600;
            longSeconds = Math.abs(longSeconds % 3600);
            int longMinutes = longSeconds / 60;
            longSeconds %= 60;
            String latDegree = latDegrees >= 0 ? "N" : "S";
            String lonDegrees = longDegrees >= 0 ? "E" : "W";

            return  Math.abs(latDegrees) + "°" + latMinutes + "'" + latSeconds
                    + "\"" + latDegree +" "+ Math.abs(longDegrees) + "°" + longMinutes
                    + "'" + longSeconds + "\"" + lonDegrees;
        } catch (Exception e) {
            return ""+ String.format("%8.5f", latitude) + "  "
                    + String.format("%8.5f", longitude) ;
        }
    }

    public String convertToDMS(double latitude, double longitude){
        StringBuilder builder = new StringBuilder();

        if (longitude < 0) {
            builder.append("");
        } else {
            builder.append("");
        }

        String longitudeDegrees = Location.convert(Math.abs(longitude), Location.FORMAT_SECONDS);
        String[] longitudeSplit = longitudeDegrees.split(":");
        builder.append(longitudeSplit[0]);
        builder.append("°");
        builder.append(longitudeSplit[1]);
        builder.append("'");
        builder.append(longitudeSplit[2]);
        builder.append("\"");

        builder.append(" ");

        if (latitude < 0) {
            builder.append("");
        } else {
            builder.append("");
        }

        String latitudeDegrees = Location.convert(Math.abs(latitude), Location.FORMAT_SECONDS);
        String[] latitudeSplit = latitudeDegrees.split(":");
        builder.append(latitudeSplit[0]);
        builder.append("°");
        builder.append(latitudeSplit[1]);
        builder.append("'");
        builder.append(latitudeSplit[2]);
        builder.append("\"");

        return builder.toString();
    }
}
