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

package org.rmj.guanzongroup.ghostrider.settings.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import org.rmj.guanzongroup.ghostrider.settings.Model.SettingsModel;
import org.rmj.guanzongroup.ghostrider.settings.R;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;


/**
 * Created by anupamchugh on 22/12/17.
 */


public class ExpandableListHelpAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<SettingsModel> listDataHeader;
    private HashMap<SettingsModel, List<SettingsModel>> listDataChild;
    private View bottom_divider;
    public ExpandableListHelpAdapter(Context context, List<SettingsModel> listDataHeader,
                                     HashMap<SettingsModel, List<SettingsModel>> listChildData) {
        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
    }

    @Override
    public SettingsModel getChild(int groupPosition, int childPosititon) {
        return Objects.requireNonNull(this.listDataChild.get(this.listDataHeader.get(groupPosition))).get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final String childText = getChild(groupPosition, childPosition).moduleName;
        final int layoutView = getChild(groupPosition, childPosition).visibility;
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.help_list_item, null);
        }
        bottom_divider = convertView.findViewById(R.id.divider_bottom);
        if (isLastChild){
            bottom_divider.setVisibility(View.VISIBLE);
        }else {
            bottom_divider.setVisibility(View.GONE);
        }
        TextView txtListChild = convertView.findViewById(R.id.lblHelpListItem);
        txtListChild.setText(childText);
        txtListChild.setVisibility(layoutView);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        if (this.listDataChild.get(this.listDataHeader.get(groupPosition)) == null)
            return 0;
        else
            return Objects.requireNonNull(this.listDataChild.get(this.listDataHeader.get(groupPosition))).size();
    }

    @Override
    public SettingsModel getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();

    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = getGroup(groupPosition).moduleName;
        int headerIcon = getGroup(groupPosition).iconImg;
        int layoutView = getGroup(groupPosition).visibility;
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.help_list_header, null);
        }

        TextView lblListHeader = convertView.findViewById(R.id.lblHelpHeader);
        ImageView iconImg = convertView.findViewById(R.id.icon_help_image);
        iconImg.setImageResource(headerIcon);

        lblListHeader.setTypeface(null, Typeface.NORMAL);
        lblListHeader.setText(headerTitle);
        lblListHeader.setVisibility(layoutView);
        iconImg.setVisibility(layoutView);

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