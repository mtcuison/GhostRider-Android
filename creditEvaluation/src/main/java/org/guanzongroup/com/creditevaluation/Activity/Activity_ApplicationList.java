
package org.guanzongroup.com.creditevaluation.Activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.RadioGroup;
import android.widget.Toast;

import org.guanzongroup.com.creditevaluation.Adapter.EvaluationAdapter;
import org.guanzongroup.com.creditevaluation.Core.FindingsParser;
import org.guanzongroup.com.creditevaluation.Core.oChildFndg;
import org.guanzongroup.com.creditevaluation.Core.oParentFndg;
import org.guanzongroup.com.creditevaluation.R;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Activity_ApplicationList extends AppCompatActivity {
    private static final String TAG = Activity_ApplicationList.class.getSimpleName();

    private ExpandableListView listView;
    private RadioGroup rgEval;
    private oChildFndg loChild;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application_list);

        listView = findViewById(R.id.expListview);

        listView.setGroupIndicator(null);
        listView.setChildIndicator(null);
        listView.setChildDivider(getResources().getDrawable(R.color.textColor_Black));
        listView.setDivider(getResources().getDrawable(R.color.textColor_Black));
        listView.setDividerHeight(2);
        String lsLabel = "{\"present_address\":{\"cAddrType\":\"0\",\"sAddressx\":\"B3 Lt4 Lingueta St., Pio Cruzcosa, Calumpit Bulacan\",\"sAddrImge\":\"\",\"nLatitude\":0.0,\"nLongitud\":0.0},\"primary_address\":{\"cAddrType\":\"1\",\"sAddressx\":\"B3 Lt4 Lingueta St., Pio Cruzcosa, Calumpit Bulacan\",\"sAddrImge\":\"\",\"nLatitude\":0.0,\"nLongitud\":0.0}}";
        String lsFndng = "{\"present_address\":{\"cAddrType\":null,\"sAddressx\":\"-1\",\"sAddrImge\":null,\"nLatitude\":0.0,\"nLongitud\":0.0},\"primary_address\":{\"cAddrType\":null,\"sAddressx\":\"-1\",\"sAddrImge\":null,\"nLatitude\":0.0,\"nLongitud\":0.0}}";

        String lsLabel1 = "{\"sProprty1\":\"\",\"sProprty2\":\"\",\"sProprty3\":\"\",\"cWith4Whl\":\"0\",\"cWith3Whl\":\"0\",\"cWith2Whl\":\"0\",\"cWithRefx\":\"1\",\"cWithTVxx\":\"1\",\"cWithACxx\":\"1\"}";
        String lsFndng1 = "{\"sProprty1\":null,\"sProprty2\":null,\"sProprty3\":null,\"cWith4Whl\":null,\"cWith3Whl\":null,\"cWith2Whl\":null,\"cWithRefx\":\"-1\",\"cWithTVxx\":\"-1\",\"cWithACxx\":null}";
        String lsLabel2 = "{\"employed\":{\"sEmployer\":\"United Phlp And Phper Inc.\",\"sWrkAddrx\":\"Km 48 Mc Arthur Hi-way Iba Este, Calumpit Bulacan\",\"sPosition\":\"M009680\",\"nLenServc\":23.0,\"nSalaryxx\":35000.0},\"self_employed\":null,\"financed\":null,\"pensioner\":null}";
        String lsFndng2 = "{\"employed\":{\"sEmployer\":null,\"sWrkAddrx\":\"-1\",\"sPosition\":null,\"nLenServc\":0.0,\"nSalaryxx\":-1.0},\"self_employed\":{\"sBusiness\":null,\"sBusAddrx\":null,\"nBusLenxx\":0.0,\"nBusIncom\":0.0,\"nMonExpns\":0.0},\"financed\":{\"sFinancer\":null,\"sReltnDsc\":null,\"sCntryNme\":null,\"nEstIncme\":0.0},\"pensioner\":{\"sPensionx\":null,\"nPensionx\":0.0}}";


        try {
            HashMap<oParentFndg, List<oChildFndg>> poChild = FindingsParser.getForEvaluation(oChildFndg.FIELDS.ADDRESS, lsLabel, lsFndng);
            HashMap<oParentFndg, List<oChildFndg>> poChild1 = FindingsParser.getForEvaluation(oChildFndg.FIELDS.ASSETS, lsLabel1, lsFndng1);
            HashMap<oParentFndg, List<oChildFndg>> poChild2 = FindingsParser.getForEvaluation(oChildFndg.FIELDS.MEANS, lsLabel2, lsFndng2);

            HashMap<oParentFndg, List<oChildFndg>> loMaster = new HashMap<>();
            loMaster.putAll(poChild);
            loMaster.putAll(poChild1);
            loMaster.putAll(poChild2);

            List<oParentFndg> poParentLst = new ArrayList<>();
            loMaster.forEach((loParent, loChild)->{
                poParentLst.add(loParent);
            });
            poParentLst.sort(new Comparator<oParentFndg>() {
                @Override
                public int compare(oParentFndg oParentFndg, oParentFndg t1) {
                    return 0;
                }
            });
            listView.setAdapter(new EvaluationAdapter(Activity_ApplicationList.this, poParentLst, loMaster, new EvaluationAdapter.OnConfirmInfoListener() {
                @Override
                public void OnConfirm(oParentFndg foParent, oChildFndg foChild) {
                    Log.e(TAG, "Parent : " + foParent.getParentDescript() +
                            ", Child : " + foChild.getLabel() +
                            ", Value : " + foChild.getValue());
                }
            }));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}