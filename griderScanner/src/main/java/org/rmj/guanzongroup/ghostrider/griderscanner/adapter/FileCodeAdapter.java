package org.rmj.guanzongroup.ghostrider.griderscanner.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.GRider.Database.Entities.EDCPCollectionDetail;
import org.rmj.g3appdriver.GRider.Database.Entities.EFileCode;
import org.rmj.g3appdriver.GRider.Etc.FormatUIText;
import org.rmj.guanzongroup.ghostrider.griderscanner.R;

import java.util.ArrayList;
import java.util.List;

public class FileCodeAdapter extends RecyclerView.Adapter<FileCodeAdapter.FileCodeViewHolder> {

    public interface OnItemClickListener {
        void OnClick(int position);

        void OnActionButtonClick();
    }

    private final List<EFileCode> plCollection;
    private List<EFileCode> collctFilter;

    private final CollectionSearch poSearch;
    private final OnItemClickListener mListener;

    public FileCodeAdapter(List<EFileCode> plCollection, OnItemClickListener mListener) {
        this.plCollection = plCollection;
        this.collctFilter = plCollection;
        this.mListener = mListener;
        this.poSearch = new CollectionSearch(this);
    }

    @NonNull
    @Override
    public FileCodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_file_to_scan, parent, false);
        return new FileCodeViewHolder(v, mListener);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FileCodeViewHolder holder, int position) {
        EFileCode collection = collctFilter.get(position);
        try {
            holder.loPlan = collection;
            holder.lbl_fileCode.setText(collection.getBriefDsc());
//            holder.lblDCPNox.setText(String.valueOf(collection.getEntryNox()));

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
        public TextView lblClient;

        public FileCodeViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            lbl_fileCode = itemView.findViewById(R.id.lbl_fileCode);
//            lblDCPNox = itemView.findViewById(R.id.lbl_dcpNox);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.OnClick(position);
                }
            });
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