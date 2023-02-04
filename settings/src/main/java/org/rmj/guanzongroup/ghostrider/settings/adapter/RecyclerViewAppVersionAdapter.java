package org.rmj.guanzongroup.ghostrider.settings.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.lib.Version.VersionInfo;
import org.rmj.guanzongroup.ghostrider.settings.ViewModel.VMAppVersion;
import org.rmj.guanzongroup.ghostrider.settings.etc.RecyclerViewHolder;
import org.rmj.guanzongroup.ghostrider.settings.Model.RecycleViewHolder_Model;

import java.util.Collections;
import java.util.List;

public class RecyclerViewAppVersionAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    public @LayoutRes int resource;
    List<VersionInfo> list = Collections.emptyList(); //reset the list value
    Context context;

    /*SERVE AS THE PARENT OBJECT FOR THE LIST. HOLDS THE LIST. RETURNS TEXTVIEW OBJECT*/
    public RecyclerViewAppVersionAdapter(List<VersionInfo> list, Context context){
        this.list = list;
        this.context = context; //context from the main activity
     }
    /*CREATE A VIEW OBJECT OF THE LIST USING THE XML FILE. RETURNS CREATED VIEWHOLDER*/
    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext(); //get context from this ViewHolder object
        LayoutInflater inflater = LayoutInflater.from(context); //get context from main activity(passed context param)

        View view = inflater.inflate(resource, parent, false);
        RecyclerViewHolder model =  new RecyclerViewHolder(view);
        return model;
    }
    /*ATTACH THE LIST DATA TO THE TEXTVIEW OBJECT*/
     @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
         holder.tvCategory.setText((CharSequence) list.get(position));
    }
    /*GET THE LIST COUNT AND RETURN AS THE POSITION*/
    @Override
    public int getItemCount() {
        return list.size();
    }
}
