package org.rmj.guanzongroup.ghostrider.griderscanner.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCreditApplicationDocuments;
import org.rmj.g3appdriver.GRider.Database.DataAccessObject.DCreditApplicationDocuments.ApplicationDocument;
import org.rmj.g3appdriver.GRider.Database.Entities.EFileCode;
import org.rmj.guanzongroup.ghostrider.griderscanner.R;
import org.rmj.guanzongroup.ghostrider.griderscanner.helpers.ScannerConstants;
import org.rmj.guanzongroup.ghostrider.griderscanner.model.CreditAppDocumentModel;

import java.util.ArrayList;
import java.util.List;

public class ClientFileCodeAdapter extends RecyclerView.Adapter<ClientFileCodeAdapter.MyViewHolder> {

    private Context mContext;
    private List<EFileCode> docfileCode;
    private List<DCreditApplicationDocuments.ApplicationDocument> documents;
    public class MyViewHolder extends RecyclerView.ViewHolder  {

        public View mView;
        public TextView title;
        public TextView fileCode;
        public ImageView cross;
        public ImageView check;
        //public LinearLayout linearLayout;

        private List<EFileCode> infoModel = new ArrayList<>();
        private List<DCreditApplicationDocuments.ApplicationDocument> documentsInfo;
        private Context mContext;
        public MyViewHolder(View view, Context mContext, List<EFileCode> docfileCode, List<DCreditApplicationDocuments.ApplicationDocument> documentsInfo) {
            super(view);
            mView = view;
            this.mContext = mContext;
            this.infoModel = docfileCode;
            this.documentsInfo = documentsInfo;
            fileCode = (TextView) view.findViewById(R.id.lbl_fileCode);
            check = (ImageView) view.findViewById(R.id.tick_done);
            cross = (ImageView) view.findViewById(R.id.tick_cross);


        }



    }


    public ClientFileCodeAdapter(Context mContext, List<EFileCode> docfileCode, List<DCreditApplicationDocuments.ApplicationDocument> documents) {
        this.mContext = mContext;
        this.docfileCode = docfileCode;
        this.documents = documents;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_file_to_scan, parent, false);

        return new MyViewHolder(itemView, mContext, docfileCode, documents);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final EFileCode category = docfileCode.get(position);


        holder.fileCode.setText(category.getBriefDsc());

        if (documents.size()> 0){
            DCreditApplicationDocuments.ApplicationDocument documentInfo = documents.get(position);
            if (documentInfo.sTransNox.equalsIgnoreCase(ScannerConstants.TransNox)  &&
                    documentInfo.nEntryNox == position &&
                    documentInfo.sFileCode.equalsIgnoreCase(category.getFileCode())){
                holder.check.setVisibility(View.VISIBLE);
                holder.cross.setVisibility(View.GONE);
            }
        }else{
            holder.check.setVisibility(View.GONE);
            holder.cross.setVisibility(View.VISIBLE);
        }


    }

    
    @Override
    public int getItemCount() {

        return docfileCode.size();
    }


  

}