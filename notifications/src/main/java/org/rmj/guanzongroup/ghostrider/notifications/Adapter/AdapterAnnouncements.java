package org.rmj.guanzongroup.ghostrider.notifications.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import org.rmj.g3appdriver.lib.Notifications.pojo.AnnouncementInfo;
import org.rmj.g3appdriver.utils.ImageFileManager;
import org.rmj.guanzongroup.ghostrider.notifications.R;

import java.util.List;

public class AdapterAnnouncements extends RecyclerView.Adapter<AdapterAnnouncements.VHAnnouncements> {

    private final List<AnnouncementInfo> poList;
    private final OnItemClickListener mListener;

    public interface OnItemClickListener{
        void OnClick(String args);
    }

    public AdapterAnnouncements(List<AnnouncementInfo> list, OnItemClickListener listener) {
        this.poList = list;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public VHAnnouncements onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_company_announcement, parent, false);
        return new VHAnnouncements(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VHAnnouncements holder, int position) {
        AnnouncementInfo loInfo = poList.get(position);
        String lsImgLink = loInfo.getsImageLnk();
        holder.lblTitle.setText(loInfo.getsTitlexxx());
        holder.lblMessage.setText(loInfo.getsContentx());
        holder.lblDate.setText(loInfo.getsDateTime());
        ImageFileManager.LoadImageToView(lsImgLink, holder.imageView);
    }

    @Override
    public int getItemCount() {
        return poList.size();
    }

    public static class VHAnnouncements extends RecyclerView.ViewHolder{

        public ShapeableImageView imageView;

        public MaterialTextView lblTitle, lblMessage, lblDate;

        public VHAnnouncements(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgAnnouncement);
            lblTitle = itemView.findViewById(R.id.lbl_title);
            lblMessage = itemView.findViewById(R.id.lbl_messageBody);
            lblDate = itemView.findViewById(R.id.lbl_dateTime);
        }
    }
}
