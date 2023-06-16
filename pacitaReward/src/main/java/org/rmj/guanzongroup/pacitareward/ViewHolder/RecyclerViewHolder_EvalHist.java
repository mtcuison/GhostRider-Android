package org.rmj.guanzongroup.pacitareward.ViewHolder;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.guanzongroup.pacitareward.R;

public class RecyclerViewHolder_EvalHist extends RecyclerView.ViewHolder {

    public MaterialTextView mtv_branchname;
    public MaterialTextView mtv_evaldate;
    public MaterialTextView mtv_branchrate;
    public MaterialButton btn_branch;
    public View view;

    public RecyclerViewHolder_EvalHist(@NonNull View itemView) {
        super(itemView);
        mtv_branchname = itemView.findViewById(R.id.mtv_branchname);
        mtv_evaldate = itemView.findViewById(R.id.mtv_evaldate);
        mtv_branchrate = itemView.findViewById(R.id.mtv_branchrate);
        btn_branch = itemView.findViewById(R.id.btn_branch);
        view = itemView;
    }
}
