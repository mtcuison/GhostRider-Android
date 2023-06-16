package org.rmj.guanzongroup.pacitareward.ViewHolder;

import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.guanzongroup.pacitareward.R;

public class RecyclerViewHolder_BranchRate extends RecyclerView.ViewHolder {
    public MaterialTextView item_question;
    public MaterialButton pass_btn;
    public MaterialButton fail_btn;
    public MaterialButtonToggleGroup toggleGroup;
    public RecyclerViewHolder_BranchRate(@NonNull View itemView) {
        super(itemView);
        item_question = itemView.findViewById(R.id.item_question);
        toggleGroup = itemView.findViewById(R.id.materialButtonToggleGroup);
        pass_btn = itemView.findViewById(R.id.pass_btn);
        fail_btn = itemView.findViewById(R.id.fail_btn);
    }
}
