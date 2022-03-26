package org.guanzongroup.com.creditevaluation.Adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.guanzongroup.com.creditevaluation.Core.oChildFndg;
import org.guanzongroup.com.creditevaluation.Core.oParentFndg;
import org.guanzongroup.com.creditevaluation.R;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class NoScrollExListView extends  ExpandableListView  {

    public NoScrollExListView(Context context) {
        super(context);
    }
    public NoScrollExListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    public NoScrollExListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMeasureSpec_custom = MeasureSpec.makeMeasureSpec(
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        setHeightWrapContent();
        super.onMeasure(widthMeasureSpec, heightMeasureSpec_custom);
//        ViewGroup.LayoutParams params = getLayoutParams();
//        params.height = getMeasuredHeight();
    }
    public void setHeightWrapContent() {
        ListAdapter listAdapter = getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, this);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = this.getLayoutParams();

        params.height = totalHeight
                + (this.getDividerHeight() * (listAdapter.getCount() - 1));
        this.setLayoutParams(params);
    }
}
