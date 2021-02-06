package org.rmj.g3appdriver.lib.cashcount;

import android.content.Context;
import android.database.Cursor;

import org.rmj.g3appdriver.dev.AppData;

import java.util.ArrayList;

public class CashCountData {
    private static final String TAG = CashCountData.class.getSimpleName();

    private AppData appData;

    private ArrayList<ArrayList<String>> cashcount_result = new ArrayList<>();
    private ArrayList<String> transNox = new ArrayList<>();
    private ArrayList<String> tranDate = new ArrayList<>();
    private ArrayList<String> nCn0001cx = new ArrayList<>();
    private ArrayList<String> nCn0005cx = new ArrayList<>();
    private ArrayList<String> nCn0025cx = new ArrayList<>();
    private ArrayList<String> nCn0001px = new ArrayList<>();
    private ArrayList<String> nCn0005px = new ArrayList<>();
    private ArrayList<String> nCn0010px = new ArrayList<>();
    private ArrayList<String> nNte0020p = new ArrayList<>();
    private ArrayList<String> nNte0050p = new ArrayList<>();
    private ArrayList<String> nNte0100p = new ArrayList<>();
    private ArrayList<String> nNte0200p = new ArrayList<>();
    private ArrayList<String> nNte0500p = new ArrayList<>();
    private ArrayList<String> nNte1000p = new ArrayList<>();
    private ArrayList<String> orNoxxxx = new ArrayList<>();
    private ArrayList<String> siNoxxxx = new ArrayList<>();
    private ArrayList<String> prNoxxxx = new ArrayList<>();
    private ArrayList<String> crNoxxxx = new ArrayList<>();
    private ArrayList<String> entryTme = new ArrayList<>();
    private ArrayList<String> received = new ArrayList<>();
    private ArrayList<String> reqstdId = new ArrayList<>();
    private ArrayList<String> reqstdNm = new ArrayList<>();
    private ArrayList<String> sModified = new ArrayList<>();

    public CashCountData(Context context){
        this.appData = AppData.getInstance(context);
    }

    public ArrayList<ArrayList<String>> getUnsentCashCountData(){
        Cursor cursor = appData.getReadableDb().rawQuery("SELECT * FROM Cash_Count_Master WHERE dReceived = ''", null);

        int rowCount = cursor.getCount();
        if(rowCount>0){
            cursor.moveToFirst();
            for(int x = 0; x<rowCount; x++){
                transNox.add(cursor.getString(cursor.getColumnIndex("sTransNox")));
                tranDate.add(cursor.getString(cursor.getColumnIndex("dTransact")));
                nCn0001cx.add(cursor.getString(cursor.getColumnIndex("nCn0001cx")));
                nCn0005cx.add(cursor.getString(cursor.getColumnIndex("nCn0005cx")));
                nCn0025cx.add(cursor.getString(cursor.getColumnIndex("nCn0025cx")));
                nCn0001px.add(cursor.getString(cursor.getColumnIndex("nCn0001px")));
                nCn0005px.add(cursor.getString(cursor.getColumnIndex("nCn0005px")));
                nCn0010px.add(cursor.getString(cursor.getColumnIndex("nCn0010px")));
                nNte0020p.add(cursor.getString(cursor.getColumnIndex("nNte0020p")));
                nNte0050p.add(cursor.getString(cursor.getColumnIndex("nNte0050p")));
                nNte0100p.add(cursor.getString(cursor.getColumnIndex("nNte0100p")));
                nNte0200p.add(cursor.getString(cursor.getColumnIndex("nNte0200p")));
                nNte0500p.add(cursor.getString(cursor.getColumnIndex("nNte0500p")));
                nNte1000p.add(cursor.getString(cursor.getColumnIndex("nNte1000p")));
                orNoxxxx.add(cursor.getString(cursor.getColumnIndex("sORNoxxxx")));
                siNoxxxx.add(cursor.getString(cursor.getColumnIndex("sSINoxxxx")));
                prNoxxxx.add(cursor.getString(cursor.getColumnIndex("sPRNoxxxx")));
                crNoxxxx.add(cursor.getString(cursor.getColumnIndex("sCRNoxxxx")));
                entryTme.add(cursor.getString(cursor.getColumnIndex("dEntryDte")));
                received.add(cursor.getString(cursor.getColumnIndex("dReceived")));
                reqstdId.add(cursor.getString(cursor.getColumnIndex("sReqstdBy")));
                sModified.add(cursor.getString(cursor.getColumnIndex("sModified")));
                cursor.moveToNext();
            }
        }
        cashcount_result.clear();
        cashcount_result.add(transNox);
        cashcount_result.add(tranDate);
        cashcount_result.add(nCn0001cx);
        cashcount_result.add(nCn0005cx);
        cashcount_result.add(nCn0025cx);
        cashcount_result.add(nCn0001px);
        cashcount_result.add(nCn0005px);
        cashcount_result.add(nCn0010px);
        cashcount_result.add(nNte0020p);
        cashcount_result.add(nNte0050p);
        cashcount_result.add(nNte0100p);
        cashcount_result.add(nNte0200p);
        cashcount_result.add(nNte0500p);
        cashcount_result.add(nNte1000p);
        cashcount_result.add(orNoxxxx);
        cashcount_result.add(siNoxxxx);
        cashcount_result.add(prNoxxxx);
        cashcount_result.add(crNoxxxx);
        cashcount_result.add(entryTme);
        cashcount_result.add(received);
        cashcount_result.add(reqstdId);
        cashcount_result.add(sModified);

        cursor.close();
        return cashcount_result;
    }

    public ArrayList<ArrayList<String>> getSentCashCountData(){
        Cursor cursor = appData.getReadableDb().rawQuery("SELECT * FROM Cash_Count_Master", null);

        int rowCount = cursor.getCount();
        if(rowCount>0){
            cursor.moveToFirst();
            for(int x = 0; x<rowCount; x++){
                transNox.add(cursor.getString(cursor.getColumnIndex("sTransNox")));
                tranDate.add(cursor.getString(cursor.getColumnIndex("dTransact")));
                nCn0001cx.add(cursor.getString(cursor.getColumnIndex("nCn0001cx")));
                nCn0005cx.add(cursor.getString(cursor.getColumnIndex("nCn0005cx")));
                nCn0025cx.add(cursor.getString(cursor.getColumnIndex("nCn0025cx")));
                nCn0001px.add(cursor.getString(cursor.getColumnIndex("nCn0001px")));
                nCn0005px.add(cursor.getString(cursor.getColumnIndex("nCn0005px")));
                nCn0010px.add(cursor.getString(cursor.getColumnIndex("nCn0010px")));
                nNte0020p.add(cursor.getString(cursor.getColumnIndex("nNte0020p")));
                nNte0050p.add(cursor.getString(cursor.getColumnIndex("nNte0050p")));
                nNte0100p.add(cursor.getString(cursor.getColumnIndex("nNte0100p")));
                nNte0200p.add(cursor.getString(cursor.getColumnIndex("nNte0200p")));
                nNte0500p.add(cursor.getString(cursor.getColumnIndex("nNte0500p")));
                nNte1000p.add(cursor.getString(cursor.getColumnIndex("nNte1000p")));
                orNoxxxx.add(cursor.getString(cursor.getColumnIndex("sORNoxxxx")));
                siNoxxxx.add(cursor.getString(cursor.getColumnIndex("sSINoxxxx")));
                prNoxxxx.add(cursor.getString(cursor.getColumnIndex("sPRNoxxxx")));
                crNoxxxx.add(cursor.getString(cursor.getColumnIndex("sCRNoxxxx")));
                entryTme.add(cursor.getString(cursor.getColumnIndex("dEntryDte")));
                received.add(cursor.getString(cursor.getColumnIndex("dReceived")));
                reqstdId.add(cursor.getString(cursor.getColumnIndex("sReqstdBy")));
                sModified.add(cursor.getString(cursor.getColumnIndex("sModified")));
                cursor.moveToNext();
            }
        }
        cashcount_result.clear();
        cashcount_result.add(transNox);
        cashcount_result.add(tranDate);
        cashcount_result.add(nCn0001cx);
        cashcount_result.add(nCn0005cx);
        cashcount_result.add(nCn0025cx);
        cashcount_result.add(nCn0001px);
        cashcount_result.add(nCn0005px);
        cashcount_result.add(nCn0010px);
        cashcount_result.add(nNte0020p);
        cashcount_result.add(nNte0050p);
        cashcount_result.add(nNte0100p);
        cashcount_result.add(nNte0200p);
        cashcount_result.add(nNte0500p);
        cashcount_result.add(nNte1000p);
        cashcount_result.add(orNoxxxx);
        cashcount_result.add(siNoxxxx);
        cashcount_result.add(prNoxxxx);
        cashcount_result.add(crNoxxxx);
        cashcount_result.add(entryTme);
        cashcount_result.add(received);
        cashcount_result.add(reqstdId);
        cashcount_result.add(sModified);

        cursor.close();
        return cashcount_result;
    }
}
