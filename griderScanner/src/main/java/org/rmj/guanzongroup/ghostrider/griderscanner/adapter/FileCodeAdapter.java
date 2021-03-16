package org.rmj.guanzongroup.ghostrider.griderscanner.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.GRider.Database.Entities.ECreditApplicationDocuments;
import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EFileCode;
import org.rmj.g3appdriver.GRider.Database.Entities.EImageInfo;
import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.guanzongroup.ghostrider.griderscanner.R;
import org.rmj.guanzongroup.ghostrider.griderscanner.viewModel.VMClientInfo;

import java.util.ArrayList;
import java.util.List;

import static org.rmj.guanzongroup.ghostrider.griderscanner.R.*;
import static org.rmj.guanzongroup.ghostrider.griderscanner.R.drawable.ic_baseline_add_24;

public class FileCodeAdapter extends RecyclerView.Adapter<FileCodeAdapter.FileCodeViewHolder> {

    public interface OnItemClickListener {
        void OnClick(int position);

        void OnActionButtonClick();
    }

    private final List<EFileCode> plCollection;
    private List<EFileCode> collctFilter;
    private List<ECreditApplicationDocuments> documentsInfo;
    private List<EImageInfo> imgInfo;
    private final CollectionSearch poSearch;
    private final OnItemClickListener mListener;
    private boolean isChecked;
    private int pos;
    public FileCodeAdapter(List<ECreditApplicationDocuments> docInfo,List<EFileCode> plCollection, OnItemClickListener mListener) {
        this.plCollection = plCollection;
        this.collctFilter = plCollection;
        this.documentsInfo = docInfo;
        this.mListener = mListener;
        this.poSearch = new CollectionSearch(this);
    }

    @NonNull
    @Override
    public FileCodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout.list_item_file_to_scan, parent, false);

        return new FileCodeViewHolder(v, mListener,isChecked);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FileCodeViewHolder holder, int position) {
        EFileCode collection = collctFilter.get(position);

        try {
            holder.loPlan = collection;
            holder.lbl_fileCode.setText(collection.getBriefDsc());
            if(documentsInfo.get(position).getEntryNox() == position){
               holder.fileStat.setVisibility(View.GONE);
                holder.fileStatDone.setVisibility(View.VISIBLE);
                isChecked = true;
            }else{
                holder.fileStat.setVisibility(View.VISIBLE);
                holder.fileStatDone.setVisibility(View.GONE);
                isChecked = false;
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return collctFilter.size();
    }

    public CollectionSearch getCollectionSearch() {
        return poSearch;
    }

    public static class FileCodeViewHolder extends RecyclerView.ViewHolder {

        public EFileCode loPlan;
        public TextView lbl_fileCode;
        //        public TextView lblDCPNox;
        public ImageView fileStat;
        public ImageView fileStatDone;

        public FileCodeViewHolder(@NonNull View itemView, OnItemClickListener listener, boolean checked) {
            super(itemView);
            lbl_fileCode = itemView.findViewById(id.lbl_fileCode);
            fileStat = itemView.findViewById(id.tick_cross);
            fileStatDone = itemView.findViewById(id.tick_done);
            if (checked){
                itemView.setOnClickListener(view -> {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.OnClick(position);
                    }
                });
            }
        }
    }

    public class CollectionSearch extends Filter {

        private final FileCodeAdapter poAdapter;

        public CollectionSearch(FileCodeAdapter poAdapter) {
            super();
            this.poAdapter = poAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            final FilterResults results = new FilterResults();
            if (charSequence.length() == 0) {
                collctFilter.addAll(plCollection);
            } else {
                List<EFileCode> filterSearch = new ArrayList<>();
                for (EFileCode plan : plCollection) {
                    String lsClientNme = plan.getBriefDsc().toLowerCase();
                    if (lsClientNme.contains(charSequence.toString().toLowerCase())) {
                        filterSearch.add(plan);
                    }
                }
                collctFilter = filterSearch;
            }

            results.values = collctFilter;
            results.count = collctFilter.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            poAdapter.collctFilter = (List<EFileCode>) filterResults.values;
            this.poAdapter.notifyDataSetChanged();
        }
    }

}