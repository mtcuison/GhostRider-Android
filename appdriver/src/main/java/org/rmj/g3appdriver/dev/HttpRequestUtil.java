/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.g3appdriver
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.g3appdriver.dev;

import android.annotation.SuppressLint;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.HashMap;

public class HttpRequestUtil extends WebClient {
    private static final String TAG = HttpRequestUtil.class.getSimpleName();

    public static void sendRequest(String URL, onServerResponseListener listener){
        try{
            Log.e(TAG, "Http Request is created. Sending request to " + URL);

            @SuppressLint({"NewApi", "LocalSuppress"}) String response = sendRequest(URL, listener.setData().toString(), listener.setHeaders());
            Log.e(TAG, "Http Request has been sent successfully.");

            if(response == null){
                listener.onErrorResponse("Unable to connect to our remote server.");
                Log.e(TAG, "Server no response. Server connection is not establish/Server is not active.");
            } else {
                JSONObject jsonResponse = new JSONObject(response);
                Log.e(TAG, "Server response : " + jsonResponse.toString());
                String serverResult = jsonResponse.getString("result");
                if(serverResult.equalsIgnoreCase("success")){
                    listener.onResponse(jsonResponse);
                } else {
                    String error = jsonResponse.getString("error");
                    JSONObject code = new JSONObject(error);

                    //If server response error with error code: 40003
                    //this indicates that user hasn't verified his/her account yet...
                    if(code.getString("code").equalsIgnoreCase("40003")) {
                        listener.onErrorResponse(jsonResponse.toString());
                    } else {
                        listener.onErrorResponse(getErrorMessage(error));
                    }
                    Log.e(TAG, "Http Request has been sent with some error response.");
                }
            }
        } catch (MalformedURLException e) {
            listener.onErrorResponse("Something went wrong. Unknown network error has occurred." );
            e.printStackTrace();
        } catch (UnknownHostException e){
            listener.onErrorResponse("Something went wrong. Unable to reach our remote servers." );
            e.printStackTrace();
        } catch (IOException e) {
            listener.onErrorResponse("Something went wrong. Please check your connection" );
            e.printStackTrace();
        } catch (JSONException e) {
            listener.onErrorResponse("Something went wrong. Please try again later." );
            e.printStackTrace();
        } catch (Exception e){
            listener.onErrorResponse("Something went wrong. Please try again later." );
            e.printStackTrace();
        }
    }

    public interface onServerResponseListener{
        HashMap setHeaders();
        JSONObject setData();
        void onResponse(JSONObject jsonResponse);
        void onErrorResponse(String message);
    }

    /**
     *
     * @param jsonResponse error message from server must be parse to get the specific message and error code
     * @return returns error message or json object...
     * @throws JSONException
     *
     * if Class that implements this method is Login of GuanzonApp
     * returns the error code if the account that login is not yet activated
     * else if the account has other error code return the error message...
     * if the error returns 40003 this indicates that the account was not yet activated.
     * return the otp to the main method...
     *
     * For Importing data
     * if server response returns error code 40026 this indicates that
     * no data was found on database.
     */
    private static String getErrorMessage(String jsonResponse) throws JSONException{
        JSONObject errorResponse = new JSONObject(jsonResponse);
        String Message = errorResponse.getString("message");
        String lsErrCde = errorResponse.getString("code");

        if(lsErrCde.equalsIgnoreCase("40003")){
            return jsonResponse;
        } else if(lsErrCde.equalsIgnoreCase("40026")){
            return jsonResponse;
        } else if(lsErrCde.equalsIgnoreCase("2002")){
            return "Unable to connect to server. Please contact MIS Dept.";
        } else if(lsErrCde.equalsIgnoreCase("40004")){
            return jsonResponse;
        } else if(lsErrCde.equalsIgnoreCase("40012")){
            return "";
        } else if(lsErrCde.equalsIgnoreCase("40020")){
            return "Your login session is invalid.\nPlease re-login account to send your data to server.";
        } else if(lsErrCde.equalsIgnoreCase("CNF")){
            return jsonResponse;
        }
        return Message;
    }
}
