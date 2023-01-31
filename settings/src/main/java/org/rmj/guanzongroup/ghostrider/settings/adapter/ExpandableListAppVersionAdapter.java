package org.rmj.guanzongroup.ghostrider.settings.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.rmj.guanzongroup.ghostrider.settings.Model.AppVersion_Model;
import org.rmj.guanzongroup.ghostrider.settings.R;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ExpandableListAppVersionAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<AppVersion_Model> listDataHeader;
    private HashMap<AppVersion_Model, List<AppVersion_Model>> listDataChild;

    public ExpandableListAppVersionAdapter(Context context, List<AppVersion_Model> listDataHeader,
                                          HashMap<AppVersion_Model, List<AppVersion_Model>> listChildData){

        this.context = context;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
    }

    @Override
    public int getGroupCount() {
        return listDataHeader.size();
    }

    @Override
    public AppVersion_Model getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
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
    public int getChildrenCount(int groupPosition) {
        if (this.listDataChild.get(this.listDataHeader.get(groupPosition)) == null)
            return 0;
        else
            return Objects.requireNonNull(this.listDataChild.get(this.listDataHeader.get(groupPosition)).size());
    }

    @Override
    public AppVersion_Model getChild(int groupPosition, int childPosition) {
        return Objects.requireNonNull(this.listDataChild.get(this.listDataHeader.get(groupPosition)).get(childPosition));
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = getGroup(groupPosition).moduleName;

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.update_logs_header, null);
        }
        TextView lblListHeader = convertView.findViewById(R.id.lblLogHeader);
        lblListHeader.setText(headerTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        String childDetail = getChild(groupPosition, childPosition).moduleName;

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.updated_logs_detail, null);
        }
        TextView lblListHeader = convertView.findViewById(R.id.lblLogdetail);
        lblListHeader.setText(childDetail);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
