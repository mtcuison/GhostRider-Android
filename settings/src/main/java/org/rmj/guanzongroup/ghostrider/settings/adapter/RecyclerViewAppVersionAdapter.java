package org.rmj.guanzongroup.ghostrider.settings.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.lib.Version.VersionInfo;
import org.rmj.guanzongroup.ghostrider.settings.R;
import org.rmj.guanzongroup.ghostrider.settings.etc.RecyclerViewHolder;

import java.util.Collections;
import java.util.List;

public class RecyclerViewAppVersionAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    public TextView tvBuildVers;
    public TextView tvDateBuild;
    public TextView tvNewUpdate;
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
        //get context from this ViewHolder object
        Context context = parent.getContext();
        //get context from main activity(passed context param)
        LayoutInflater inflater = LayoutInflater.from(context);
        //set view, by attaching the layout resource (xml) to the parent view (view holder)
        View view = inflater.inflate(resource, parent, false);
        //set data to the view holder
        RecyclerViewHolder model =  new RecyclerViewHolder(view);
        return model;
    }
    /*ATTACH THE LIST DATA TO THE TEXTVIEW OBJECT*/
     @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
         try {
             //get parent/root view
             View parentview = new View(context);
             //get parent view objects
             tvBuildVers = parentview.findViewById(R.id.build_version);
             tvDateBuild = parentview.findViewById(R.id.date_build);
             tvNewUpdate = parentview.findViewById(R.id.about_update);
             //set default values for parent view
             tvBuildVers.setText(list.get(position).getsVrsionNm());

             //get layout xml id from current view
             String layoutid = holder.itemView.getResources().getResourceEntryName(holder.itemView.getId());

             /*SET LIST OF VERSIONS TO RECYCLER VIEW HOLDER OBJECTS*/
             if (layoutid.equals("layout_newfeatures")){
                 //get update feature
                 holder.tvHeader.setText(list.get(position).getNewFeatures().get(position).getsFeaturex());
                 //get update description
                 holder.tvDetails.setText(list.get(position).getNewFeatures().get(position).getsDescript());
             }else if (layoutid.equals("layout_fixedconcerns")){
                 //get update fixed concerns
                 holder.tvFixedConcerns.setText(list.get(position).getOthers().get(position));
             }
         } catch (Exception e) {
             Log.d(context.getClass().getSimpleName(), e.getMessage());
         }
     }
    /*GET THE LIST COUNT AND RETURN AS THE POSITION*/
    @Override
    public int getItemCount() {
        return list.size();
    }
}
