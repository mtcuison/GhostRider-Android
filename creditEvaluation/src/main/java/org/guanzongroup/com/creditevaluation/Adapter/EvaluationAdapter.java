package org.guanzongroup.com.creditevaluation.Adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import org.guanzongroup.com.creditevaluation.Activity.Activity_ApplicationList;
import org.guanzongroup.com.creditevaluation.Activity.Activity_Evaluation;
import org.guanzongroup.com.creditevaluation.Core.oChildFndg;
import org.guanzongroup.com.creditevaluation.Core.oParentFndg;
import org.guanzongroup.com.creditevaluation.R;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class EvaluationAdapter extends BaseExpandableListAdapter {
    private final Context mContext;

    private List<oChildFndg> poChildLst;
    private final List<oParentFndg> poParentLst;
    private final HashMap<oParentFndg, List<oChildFndg>> poChild;
    private final OnConfirmInfoListener mListener;

    public interface OnConfirmInfoListener{
        void OnConfirm(oParentFndg foParent, oChildFndg foChild);
    }

    public EvaluationAdapter(Context mContext,
                             List<oParentFndg> poParentLst,
                             HashMap<oParentFndg, List<oChildFndg>> poChild,
                             OnConfirmInfoListener listener) {
        this.mContext = mContext;
        this.poParentLst = poParentLst;
        this.poChild = poChild;
        this.mListener = listener;
    }


    @Override
    public int getGroupCount() {
        return poParentLst.size();
    }

    @Override
    public int getChildrenCount(int i) {
        if(this.poChild.get(this.poParentLst.get(i)) == null) {
            return 0;
        } else {
            return Objects.requireNonNull(this.poChild.get(this.poParentLst.get(i))).size();
        }
    }

    @Override
    public Object getGroup(int i) {
        return this.poParentLst.get(i);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return Objects.requireNonNull(this.poChild.get(this.poParentLst.get(groupPosition))).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean b, View view, ViewGroup viewGroup) {
        oParentFndg loParent = (oParentFndg) getGroup(groupPosition);
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.list_item_evaluation, null);
        }
        ExpandableListView mExpandableListView = (ExpandableListView) viewGroup;
        mExpandableListView.expandGroup(groupPosition);
        TextView txtListChild = view.findViewById(R.id.lbl_evalLabel);
        RadioGroup rgEval = view.findViewById(R.id.rg_evaluator);
        txtListChild.setVisibility(View.GONE);
        rgEval.setVisibility(View.GONE);
        TextView lblField = view.findViewById(R.id.lbl_evalField);
        TextView lblTitle = view.findViewById(R.id.lbl_evalTitle);
        lblField.setText(loParent.getTitle());
        lblTitle.setText(loParent.getParentDescript());
//        if(loParent.getParentDescript().isEmpty()){
//            lblTitle.setVisibility(View.GONE);
//        }
//
        lblField.setVisibility(View.GONE);
        lblTitle.setVisibility(View.GONE);

        return view;
    }
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean b, View view, ViewGroup viewGroup) {
        oParentFndg loParent = (oParentFndg) getGroup(groupPosition);
        oChildFndg loChild = (oChildFndg) getChild(groupPosition, childPosition);
        String lsLabel = loChild.getLabel();
        if (view == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = infalInflater.inflate(R.layout.list_item_evaluation, null);
        }
        TextView lblField = view.findViewById(R.id.lbl_evalField);
        TextView lblTitle = view.findViewById(R.id.lbl_evalTitle);
        lblField.setVisibility(View.GONE);
        lblTitle.setVisibility(View.GONE);
        TextView txtListChild = view.findViewById(R.id.lbl_evalLabel);
        RadioGroup rgEval = view.findViewById(R.id.rg_evaluator);
        txtListChild.setText(lsLabel);
        if(txtListChild.getVisibility() == View.GONE){
            lblField.setVisibility(View.GONE);
            lblTitle.setVisibility(View.GONE);
        }else{
            if(loParent.getParentDescript().isEmpty()){
                lblTitle.setVisibility(View.GONE);
            }else{
                lblField.setText(loParent.getTitle());
                lblTitle.setText(loParent.getParentDescript());
                lblTitle.setVisibility(View.VISIBLE);
                lblField.setVisibility(View.VISIBLE);
            }
        }

        rgEval.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rb_correct){
                    if(loChild.getKey().equalsIgnoreCase("nLenServc") ||
                            loChild.getKey().equalsIgnoreCase("nSalaryxx") ||
                            loChild.getKey().equalsIgnoreCase("nBusLenxx")||
                            loChild.getKey().equalsIgnoreCase("nBusIncom")||
                            loChild.getKey().equalsIgnoreCase("nMonExpns")||
                            loChild.getKey().equalsIgnoreCase("nEstIncme")||
                            loChild.getKey().equalsIgnoreCase("nPensionx")){
                        loChild.setsValue(loChild.getLabel());
                    }else {
                        loChild.setsValue("1");
                    }
                } else {
                    if(loChild.getKey().equalsIgnoreCase("nLenServc") || loChild.getKey().equalsIgnoreCase("nSalaryxx")){
                        loChild.setsValue(loChild.getLabel());
                    }else {
                        loChild.setsValue("0");
                    }
                }
                mListener.OnConfirm(loParent,loChild);
            }
        });
        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }

}
