package org.rmj.guanzongroup.pacitareward;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.guanzongroup.pacitareward.R;

public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    //public MaterialTextView item_no;
    public MaterialTextView item_question;
    MaterialButton pass_btn;
    MaterialButton fail_btn;

    LinearLayout layout_pass;
    LinearLayout layout_fail;
    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);

        //item_no = itemView.findViewById(R.id.item_no);
        item_question = itemView.findViewById(R.id.item_question);

        pass_btn = itemView.findViewById(R.id.pass_btn);
        fail_btn = itemView.findViewById(R.id.fail_btn);
    }
}
