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

import java.text.SimpleDateFormat;
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
        Context context = parent.getContext(); //get context from this ViewHolder object
        LayoutInflater inflater = LayoutInflater.from(context); //get context from main activity(passed context param)
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
             /*SET LIST OF VERSIONS TO RECYCLER VIEW HOLDER OBJECTS*/
             //get update feature
             holder.tvHeader.setText(list.get(position).getNewFeatures().get(position).getsFeaturex());
             //get update description
             holder.tvDetails.setText(list.get(position).getNewFeatures().get(position).getsDescript());
             //get update fixed concerns
             holder.tvOthers.setText(list.get(position).getOthers().get(position));

             /*SET TEXT FOR PARENT OBJECTS*/
             //set current date as build date
             SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd");
             String formatDate = simpleDateFormat.format(System.currentTimeMillis());

             View parentview = new View(context);
             tvBuildVers = parentview.findViewById(R.id.build_version);
             tvDateBuild = parentview.findViewById(R.id.date_build);
             tvNewUpdate = parentview.findViewById(R.id.about_update);

             tvBuildVers.setText(list.get(position).getsVrsionNm());
             tvDateBuild.setText(formatDate);
             tvNewUpdate.setText(list.get(position).getcNewUpdte());

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
