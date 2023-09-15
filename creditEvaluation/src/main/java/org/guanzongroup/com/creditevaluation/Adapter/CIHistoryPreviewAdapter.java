package org.guanzongroup.com.creditevaluation.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.material.radiobutton.MaterialRadioButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.android.material.divider.MaterialDivider;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import  com.google.android.material.checkbox.MaterialCheckBox;


import org.guanzongroup.com.creditevaluation.Core.oChildFndg;
import org.guanzongroup.com.creditevaluation.Core.oParentFndg;
import org.guanzongroup.com.creditevaluation.R;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class CIHistoryPreviewAdapter extends BaseExpandableListAdapter {
    private final Context mContext;

    private List<oChildFndg> poChildLst;
    private final List<oParentFndg> poParentLst;
    private final HashMap<oParentFndg, List<oChildFndg>> poChild;


    public CIHistoryPreviewAdapter(Context mContext,
                                   List<oParentFndg> poParentLst,
                                   HashMap<oParentFndg, List<oChildFndg>> poChild) {
        this.mContext = mContext;
        this.poParentLst = poParentLst;
        this.poChild = poChild;
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
        MaterialTextView txtListChild = view.findViewById(R.id.lbl_evalLabel);
        RadioGroup rgEval = view.findViewById(R.id.rg_evaluator);
        txtListChild.setVisibility(View.GONE);
        rgEval.setVisibility(View.GONE);
        MaterialTextView lblField = view.findViewById(R.id.lbl_evalField);
        MaterialTextView lblTitle = view.findViewById(R.id.lbl_evalTitle);
        lblField.setText(loParent.getTitle());
        lblTitle.setText(loParent.getParentDescript());
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
        MaterialTextView lblField = view.findViewById(R.id.lbl_evalField);
        MaterialTextView lblTitle = view.findViewById(R.id.lbl_evalTitle);
        lblField.setVisibility(View.GONE);
        lblTitle.setVisibility(View.GONE);
        MaterialTextView txtListChild = view.findViewById(R.id.lbl_evalLabel);
        RadioGroup rgEval = view.findViewById(R.id.rg_evaluator);
        MaterialRadioButton rb_correct = view.findViewById(R.id.rb_correct);
        MaterialRadioButton rb_incorrect = view.findViewById(R.id.rb_incorrect);
        rb_incorrect.setClickable(false);
        rb_correct.setClickable(false);
        txtListChild.setText(lsLabel);

        if(txtListChild.getVisibility() == View.GONE){
            lblField.setVisibility(View.GONE);
            lblTitle.setVisibility(View.GONE);
        }else{

            if(loChild.getValue().equalsIgnoreCase("0")){
                rb_incorrect.setChecked(true);
            }else if(loChild.getValue().equalsIgnoreCase("1")){
                rb_correct.setChecked(true);
            }
            if(loParent.getParentDescript().isEmpty()){
                lblTitle.setVisibility(View.GONE);
            }else{
                lblField.setText(loParent.getTitle());
                lblTitle.setText(loParent.getParentDescript());

                if(loChild.getKey().equalsIgnoreCase("nLenServc") ||
                        loChild.getKey().equalsIgnoreCase("nSalaryxx") ||
                        loChild.getKey().equalsIgnoreCase("nBusLenxx")||
                        loChild.getKey().equalsIgnoreCase("nBusIncom")||
                        loChild.getKey().equalsIgnoreCase("nMonExpns")||
                        loChild.getKey().equalsIgnoreCase("nEstIncme")||
                        loChild.getKey().equalsIgnoreCase("nPensionx")){
                    if(!loChild.getValue().equalsIgnoreCase(loChild.getLabel())){
                        Log.e("not equal",loChild.getValue());
                        rb_incorrect.setChecked(true);
                    }else if(loChild.getValue().equalsIgnoreCase(loChild.getLabel())){
                        Log.e("equal",loChild.getValue());
                        rb_correct.setChecked(true);
                    }
                }
                if(loChild.getKey().equalsIgnoreCase("nLatitude") ||
                        loChild.getKey().equalsIgnoreCase("nLongitud") ||
                        !rb_incorrect.isChecked() && !rb_correct.isChecked()){
                    rgEval.setVisibility(View.GONE);
                    lblTitle.setVisibility(View.GONE);
                    lblField.setVisibility(View.GONE);
                    txtListChild.setVisibility(View.GONE);
                }else{
                    lblTitle.setVisibility(View.VISIBLE);
                    lblField.setVisibility(View.VISIBLE);
                }
            }
        }

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
    void hiddenLayout(){

    }
}
