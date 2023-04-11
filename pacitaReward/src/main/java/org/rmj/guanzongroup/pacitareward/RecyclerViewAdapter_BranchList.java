package org.rmj.guanzongroup.pacitareward;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecyclerViewAdapter_BranchList extends RecyclerView.Adapter<RecyclerViewHolder_BranchList> {
    public List<String> branches;
    public HashMap<String, String> branchesloc;
    Context context;

    public RecyclerViewAdapter_BranchList(Context context, List<String> branches, HashMap<String, String> branchesloc){
        this.context = context;
        this.branches = branches;
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
        String branchItem = String.valueOf(branches.get(position));
        String branchLoc = String.valueOf(branchesloc.get(branchItem));

        holder.item_branch.setText(branchItem);
        holder.item_branchloc.setText(branchLoc);
    }

    @Override
    public int getItemCount() {
        return branches.size();
    }
}
