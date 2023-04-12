package org.rmj.guanzongroup.pacitareward.Adapter;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.guanzongroup.pacitareward.R;

public class RecyclerViewHolder_BranchRate extends RecyclerView.ViewHolder {
    public MaterialTextView item_question;
    MaterialButton pass_btn;
    MaterialButton fail_btn;
    public RecyclerViewHolder_BranchRate(@NonNull View itemView) {
        super(itemView);
        item_question = itemView.findViewById(R.id.item_question);

        pass_btn = itemView.findViewById(R.id.pass_btn);
        fail_btn = itemView.findViewById(R.id.fail_btn);
    }
}
