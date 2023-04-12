package org.rmj.guanzongroup.pacitareward;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RecyclerViewAdapter_BranchList extends RecyclerView.Adapter<RecyclerViewHolder_BranchList> implements Filterable {
    public List<String> branches;
    public List<String> branchesFilter;
    public HashMap<String, String> branchesloc;
    Context context;

    public RecyclerViewAdapter_BranchList(Context context, List<String> branches, HashMap<String, String> branchesloc){
        this.context = context;
        this.branches = branches;
        this.branchesFilter = branches;
        this.branchesloc = branchesloc;
    }

    @NonNull
    @Override
    public RecyclerViewHolder_BranchList onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.layout_branchlist, parent, false);
        return new RecyclerViewHolder_BranchList(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder_BranchList holder, int position) {
        String branchItem = String.valueOf(branchesFilter.get(position));
        String branchLoc = String.valueOf(branchesloc.get(branchItem));

        holder.item_branch.setText(branchItem);
        holder.item_branchloc.setText(branchLoc);

        holder.item_branch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog_SelectAction dialog_selectAction = new Dialog_SelectAction(context);
                dialog_selectAction.initDialog();
            }
        });
    }
    @Override
    public int getItemCount() {
        return branchesFilter.size();
    }
    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults = new FilterResults();

                if (constraint == null || constraint.length() == 0){
                    branchesFilter = branches;
                }else {
                    String searchChr = constraint.toString();
                    List<String> filteredList = new ArrayList<>();

                    for(String s: branches){
                        if(s.toLowerCase().contains(searchChr.toString().toLowerCase())){
                            filteredList.add(s);
                        }
                    branchesFilter = filteredList;
                    }

                    filterResults.values = branchesFilter;
                    filterResults.count = branchesFilter.size();
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                branches = (List<String>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}
