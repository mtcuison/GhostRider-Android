/*
 * Created by Android Team MIS-SEG Year 2021
 * Copyright (c) 2021. Guanzon Central Office
 * Guanzon Bldg., Perez Blvd., Dagupan City, Pangasinan 2400
 * Project name : GhostRider_Android
 * Module : GhostRider_Android.app
 * Electronic Personnel Access Control Security System
 * project file created : 4/24/21 3:19 PM
 * project file last modified : 4/24/21 3:17 PM
 */

package org.rmj.guanzongroup.ghostrider.epacss.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import org.rmj.guanzongroup.ghostrider.epacss.Object.ChildObject;
import org.rmj.guanzongroup.ghostrider.epacss.Object.ParentObject;
import org.rmj.guanzongroup.ghostrider.epacss.R;

import com.google.android.material.textview.MaterialTextView;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by anupamchugh on 22/12/17.
 */

public class ExpandableListDrawerAdapter extends BaseExpandableListAdapter {
    private Context context;

    private List<ParentObject> poParentLst = new ArrayList<>();
    private List<ChildObject> poChildLst;
    private HashMap<ParentObject, List<ChildObject>> poChild = new HashMap<>();

    public ExpandableListDrawerAdapter(Context context, List<ParentObject> foParent, HashMap<ParentObject, List<ChildObject>> foChild) {
        this.context = context;
        this.poParentLst = foParent;
        this.poChild = foChild;
    }

    @Override
    public ChildObject getChild(int groupPosition, int childPosititon) {
        return Objects.requireNonNull(this.poChild.get(this.poParentLst.get(groupPosition))).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = getChild(groupPosition, childPosition).getChildMenuName();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.drawer_list_item, null);
        }
        TextView txtListChild = convertView.findViewById(R.id.lblListItem);
        txtListChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if (this.poChild.get(this.poParentLst.get(groupPosition)) == null)
            return 0;
        else
            return Objects.requireNonNull(this.poChild.get(this.poParentLst.get(groupPosition))).size();
    }

    @Override
    public ParentObject getGroup(int groupPosition) {
        return this.poParentLst.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.poParentLst.size();

    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = getGroup(groupPosition).getMenuName();
        int headerIcon = getGroup(groupPosition).getMenuIcon();
//        int layoutView = getGroup(groupPosition).visibility;
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.drawer_list_header, null);
        }

        MaterialTextView lblListHeader = convertView.findViewById(R.id.lblHeader);
        ShapeableImageView iconImg = convertView.findViewById(R.id.iconimage);
        iconImg.setImageResource(headerIcon);
        lblListHeader.setTypeface(null, Typeface.NORMAL);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}