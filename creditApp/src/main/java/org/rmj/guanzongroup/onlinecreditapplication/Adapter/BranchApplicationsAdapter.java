package org.rmj.guanzongroup.onlinecreditapplication.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.guanzongroup.onlinecreditapplication.Model.BranchApplicationModel;
import org.rmj.guanzongroup.onlinecreditapplication.R;

import java.util.ArrayList;
import java.util.List;

public class BranchApplicationsAdapter extends RecyclerView.Adapter<BranchApplicationsAdapter.ClientInfoViewHolder> implements Filterable{

    private List<BranchApplicationModel> plLoanApp;
    private List<BranchApplicationModel> plLoanApp1;
    private List<BranchApplicationModel> plSchList;
    private List<BranchApplicationModel> filteredList;
    private BranchApplicationsAdapter.OnVoidApplicationListener onVoidApplicationListener;
    private BranchApplicationsAdapter.OnExportGOCASListener onExportGOCASListener;
    private BranchApplicationsAdapter.OnApplicationClickListener onApplicationClickListener;

//    private final SearchFilter poSearch;

    public BranchApplicationsAdapter(List<BranchApplicationModel> plLoanApp, BranchApplicationsAdapter.OnApplicationClickListener onApplicationClickListener) {
        this.plLoanApp = plLoanApp;
        this.plLoanApp1 = plLoanApp;
        this.onApplicationClickListener = onApplicationClickListener;
//        this.poSearch = new SearchFilter();
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


        holder.lblTransNoxxx.setText("Transaction No. :"+poLoan.getsTransNox());
        holder.lblClientName.setText(poLoan.getsCompnyNm());
        holder.lblAppltnDate.setText(poLoan.getdTransact());
        holder.lblStatus.setText(poLoan.getTransactionStatus());
        holder.lblAccntTern.setText(poLoan.getnAcctTerm());
        holder.lblModelName.setText(poLoan.getsModelNme());
        holder.lblMobileNo.setText(poLoan.getsMobileNo());
    }

    @Override
    public int getItemCount() {
        return plLoanApp.size();
    }

//    public SearchFilter getSearchFilter(){
//        return poSearch;
//    }
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

            lblTransNoxxx = itemView.findViewById(R.id.lbl_list_transNox);
            lblClientName = itemView.findViewById(R.id.lbl_list_applicantName);
            lblAppltnDate = itemView.findViewById(R.id.lbl_list_applicationDate);
            lblStatus = itemView.findViewById(R.id.lbl_list_applicationWithCI);
            lblModelName = itemView.findViewById(R.id.lbl_modelName);
            lblAccntTern = itemView.findViewById(R.id.lbl_accntTerm);
            lblMobileNo = itemView.findViewById(R.id.lbl_mobileNo);


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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                if (charString.isEmpty() || charString.length()==0 || charString == null) {
                    plLoanApp = plLoanApp1;

                } else {
                    filteredList = new ArrayList<>();
                    for (BranchApplicationModel row : plLoanApp1) {
                        // name match condition. this might differ depending on your requirement
                        // here we are looking for name or category in match match
                        if (row.getsCompnyNm().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }

                    }

                    plLoanApp = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = plLoanApp;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                plLoanApp = (ArrayList<BranchApplicationModel>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}
