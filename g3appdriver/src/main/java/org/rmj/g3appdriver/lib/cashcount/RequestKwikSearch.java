package org.rmj.g3appdriver.lib.cashcount;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.rmj.g3appdriver.utils.ConnectionUtil;
import org.rmj.g3appdriver.GRider.Http.HttpRequestUtil;
import org.rmj.g3appdriver.GRider.Http.RequestHeaders;

import java.util.ArrayList;
import java.util.HashMap;

public class RequestKwikSearch extends HttpRequestUtil {
    private static final String TAG = RequestKwikSearch.class.getSimpleName();
    private static final String URL_KWIKSEARCH = "https://restgk.guanzongroup.com.ph/integsys/paramqry/cash_count_rqst_officer.php";
    private Context mContext;
    private RequestHeaders headers;
    private ConnectionUtil connectionUtil;
    private onReceiveKwikSearchList mListener;
    private ArrayList<String[]> requestList;

    public interface onReceiveKwikSearchList{
        void onReceiveList(ArrayList<String[]> kwikSearchList);
        void onErrorReceive(String errorMessage);
    }

    public void requestKwikSearchList(Context context, final JSONObject parameters, onReceiveKwikSearchList listener){
        this.mContext = context;
        this.headers = new RequestHeaders(mContext);
        this.connectionUtil = new ConnectionUtil(mContext);
        this.mListener = listener;
        this.requestList = new ArrayList<>();

        if(connectionUtil.isDeviceConnected()) {
            sendRequest(URL_KWIKSEARCH, new onServerResponseListener() {
                @Override
                public HashMap setHeaders() {
                    return (HashMap)headers.getHeaders();
                }

                @Override
                public JSONObject setData() {
                    return parameters;
                }

                @Override
                public void onResponse(JSONObject jsonResponse) {
                    parseData(jsonResponse);
                    mListener.onReceiveList(requestList);
                }

                @Override
                public void onErrorResponse(String message) {
                    mListener.onErrorReceive(message);
                }
            });
        } else {
            mListener.onErrorReceive("No server connection establish. Please connect to internet.");
        }
    }

    private void parseData(JSONObject obj){
        try{
            JSONArray jsonA = obj.getJSONArray("detail");
            for (int x = 0;  x < jsonA.length(); x++) {
                String[] details = new String[3];
                JSONObject jsonDetail = jsonA.getJSONObject(x);
                details[0] = jsonDetail.getString("reqstdid");
                details[1] = jsonDetail.getString("reqstdnm");
                details[2] = jsonDetail.getString("department");
                requestList.add(details);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
