package org.rmj.guanzongroup.ganado.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import org.rmj.g3appdriver.GCircle.room.Entities.EMcModel;
import org.rmj.guanzongroup.ganado.R;

import java.util.ArrayList;
import java.util.List;

public class ModelGridAdapter extends BaseAdapter {

    private final List<EMcModel> paMCModel;
    private final List<EMcModel> paModel;

    private List<EMcModel> paModelFilter;

    private final ModelFilter poFilter;

//    private final OnModelSelectListener listener;
    Context context;
    String[] ModelNm;
    int[] modelimg;

    LayoutInflater inflater;

    public ModelGridAdapter(List<EMcModel> paMCModel, List<EMcModel> paModel, ModelFilter poFilter, Context context, String[] modelNm, int[] modelimg) {
        this.paMCModel = paMCModel;
        this.paModel = paModel;
        this.poFilter = poFilter;
        this.context = context;
        this.ModelNm = modelNm;
        this.modelimg = modelimg;
    }

    @Override
    public int getCount() {
        return ModelNm.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.mcmodelgrid_item, null);


            ImageView imageView = convertView.findViewById(R.id.imagemodel0);
            TextView textView = convertView.findViewById(R.id.itemName);

            imageView.setImageResource(modelimg[position]);
            textView.setText(ModelNm[position]);
        }
        return convertView;
    }
    public class ModelFilter extends Filter {
        private final ModelGridAdapter poAdapter;
        public ModelFilter(ModelGridAdapter poAdapter) {
            super();
            this.poAdapter = poAdapter;
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            final FilterResults results = new FilterResults();

            if(constraint.length() == 0){
                paModelFilter = paModel;
            } else {
                List<EMcModel> filterSearch = new ArrayList<>();
                for (EMcModel model:paModel){
                    String lsBranchNm = model.getModelNme();
                    if(lsBranchNm.toLowerCase().contains(constraint.toString().toLowerCase())){
                        filterSearch.add(model);
                    }
                }
                paModelFilter = filterSearch;
            }
            results.values = paModelFilter;
            results.count = paModelFilter.size();
            return results;
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            poAdapter.paModelFilter = (List<EMcModel>) results.values;
            this.poAdapter.notifyDataSetChanged();
        }
    }
}
