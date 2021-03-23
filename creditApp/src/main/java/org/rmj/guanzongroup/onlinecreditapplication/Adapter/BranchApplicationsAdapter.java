package org.rmj.guanzongroup.onlinecreditapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.onlinecreditapplication.Model.BranchApplicationModel;
import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.ArrayList;
import java.util.List;

public class BranchApplicationsAdapter extends RecyclerView.Adapter<BranchApplicationsAdapter.ClientInfoViewHolder> {

    private List<BranchApplicationModel> plLoanApp;
    private List<BranchApplicationModel> plSchList;
    private BranchApplicationsAdapter.OnVoidApplicationListener onVoidApplicationListener;
    private BranchApplicationsAdapter.OnExportGOCASListener onExportGOCASListener;
    private BranchApplicationsAdapter.OnApplicationClickListener onApplicationClickListener;

    private final SearchFilter poSearch;
    public BranchApplicationsAdapter(List<BranchApplicationModel> plLoanApp, BranchApplicationsAdapter.OnApplicationClickListener onApplicationClickListener) {
        this.plLoanApp = plLoanApp;
        this.onApplicationClickListener = onApplicationClickListener;
        this.poSearch = new SearchFilter(this);
    }
    public interface OnItemClickListener {
        void OnClick(int position);

        void OnActionButtonClick();
    }
    @NonNull
    @Override
    public BranchApplicationsAdapter.ClientInfoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_branch_application, parent, false);
        return new BranchApplicationsAdapter.ClientInfoViewHolder(view,onApplicationClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientInfoViewHolder holder, int position) {
        BranchApplicationModel poLoan = plLoanApp.get(position);


        holder.lblTransNoxxx.setText("TransNox. :"+poLoan.getsTransNox());
        holder.lblClientName.setText(poLoan.getsCompnyNm());
        holder.lblAppltnDate.setText(poLoan.getdTransact());
        holder.lblStatus.setText(poLoan.getTransactionStatus());
        holder.lblAccntTern.setText(poLoan.getnAcctTerm());
        holder.lblModelName.setText(poLoan.getsModelNme());
        holder.lblMobileNo.setText(poLoan.getsMobileNo());
//        holder.lblApplResult.setText(poLoan.getTransactionStatus());
//
//        holder.btnVoid.setVisibility(poLoan.getVoidStatus());
//
//        holder.lblSentStatus.setVisibility(poLoan.getSendStatus());
    }

    @Override
    public int getItemCount() {
        return plLoanApp.size();
    }

    public SearchFilter getSearchFilter(){
        return poSearch;
    }
    public class ClientInfoViewHolder extends RecyclerView.ViewHolder{

        TextView lblTransNoxxx;
        TextView lblClientName;
        TextView lblAppltnDate;
        TextView lblModelName;
        TextView lblAccntTern;
        TextView lblMobileNo;
        TextView lblStatus;
        ImageButton btnVoid;

        public ClientInfoViewHolder(@NonNull View itemView, BranchApplicationsAdapter.OnApplicationClickListener onApplicationClickListener) {
            super(itemView);

            lblTransNoxxx = itemView.findViewById(org.rmj.guanzongroup.ghostrider.griderscanner.R.id.lbl_list_transNox);
            lblClientName = itemView.findViewById(org.rmj.guanzongroup.ghostrider.griderscanner.R.id.lbl_list_applicantName);
            lblAppltnDate = itemView.findViewById(org.rmj.guanzongroup.ghostrider.griderscanner.R.id.lbl_list_applicationDate);
            lblStatus = itemView.findViewById(org.rmj.guanzongroup.ghostrider.griderscanner.R.id.lbl_list_applicationWithCI);
            lblModelName = itemView.findViewById(org.rmj.guanzongroup.ghostrider.griderscanner.R.id.lbl_modelName);
            lblAccntTern = itemView.findViewById(org.rmj.guanzongroup.ghostrider.griderscanner.R.id.lbl_accntTerm);
            lblMobileNo = itemView.findViewById(org.rmj.guanzongroup.ghostrider.griderscanner.R.id.lbl_mobileNo);


            itemView.setOnClickListener(v12 -> {
                if(BranchApplicationsAdapter.this.onApplicationClickListener !=null){
                    int lnPos = getAdapterPosition();
                    if(lnPos != RecyclerView.NO_POSITION){
                        BranchApplicationsAdapter.this.onApplicationClickListener.OnClick(lnPos, plLoanApp);
                    }
                }
            });
        }
    }


    public interface OnVoidApplicationListener{
        void OnVoid(int position, String TransNox);
    }

    public interface OnApplicationClickListener{
        void OnClick(int position, List<BranchApplicationModel> loanList);
    }

    public interface OnExportGOCASListener{
        void onExport(String GOCAS, String ClientName,String DateApplied);
    }
    public class SearchFilter extends Filter {

        private final BranchApplicationsAdapter poAdapter;

        public SearchFilter(BranchApplicationsAdapter poAdapter) {
            super();
            this.poAdapter = poAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            final FilterResults results = new FilterResults();
            if(constraint.length() == 0){
                plSchList.addAll(plLoanApp);
            } else {
                List<BranchApplicationModel> filterSearch = new ArrayList<>();
                for(BranchApplicationModel poLoan : plLoanApp){
                    String lsClientNm = poLoan.getsCompnyNm().toLowerCase();
                    if(lsClientNm.contains(constraint.toString().toLowerCase())){
                        filterSearch.add(poLoan);
                    }
                }
                plSchList = filterSearch;
            }

            results.values = plSchList;
            results.count = plSchList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            poAdapter.plSchList = (List<BranchApplicationModel>) results.values;
            this.poAdapter.notifyDataSetChanged();
        }
    }
}
