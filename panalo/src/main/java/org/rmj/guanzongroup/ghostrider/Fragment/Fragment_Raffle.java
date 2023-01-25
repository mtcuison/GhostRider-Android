package org.rmj.guanzongroup.ghostrider.Fragment;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.lib.Panalo.model.PanaloRewards;
import org.rmj.guanzongroup.ghostrider.Adapter.AdapterRaffleDraw;
import org.rmj.guanzongroup.ghostrider.Dialog.DialogPanaloRedeem;
import org.rmj.guanzongroup.ghostrider.R;
import org.rmj.guanzongroup.ghostrider.ViewModel.VMRaffle;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Raffle extends Fragment {

    private VMRaffle mViewModel;
    private MessageBox loMessage;
    private RecyclerView recyclerView;
    private RelativeLayout rlEmpty;
    private TextView lblFirstNme,
            lblLastNme,
            lblMiddleNme,
            textView;

    public static Fragment_Raffle newInstance() {
        return new Fragment_Raffle();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(VMRaffle.class);
        View view = inflater.inflate(R.layout.fragment_raffle, container, false);
        initWidgets(view);
        mViewModel.GetRewards(0, new VMRaffle.OnRetrieveRaffleListener() {
            @Override
            public void OnLoad(String title, String message) {

            }

            @Override
            public void OnSuccess(List<PanaloRewards> args) {
//                if(String.valueOf(args.size()) == null){
                if (args.size() > 0){
                    recyclerView.setVisibility(View.VISIBLE);
                    rlEmpty.setVisibility(View.GONE);


                    AdapterRaffleDraw loAdapter = new AdapterRaffleDraw(args, new AdapterRaffleDraw.OnClickListener() {

                        @Override
                        public void OnClick(String args) {
                            //to display dialog here
                            DialogPanaloRedeem dialogPanaloRedeem = new DialogPanaloRedeem(getActivity());
                            dialogPanaloRedeem.show();
                            Toast.makeText(requireActivity(), args, Toast.LENGTH_SHORT).show();
                        }
                    }) {
                        @Override
                        public void OnClick(String args) {

                        }
                    };
                    recyclerView.setAdapter(loAdapter);
                }else {
                    recyclerView.setVisibility(View.GONE);
                    rlEmpty.setVisibility(View.VISIBLE);

                }
            }

            @Override
            public void OnFailed(String message) {

            }
        });
        return  view;
    }

    private void initWidgets(View v) {
        recyclerView = v.findViewById(R.id.recyclerView);
        textView = v.findViewById(R.id.textView);
        rlEmpty = v.findViewById(R.id.rlEmpty);
        LinearLayoutManager loManager = new LinearLayoutManager(requireActivity());
        loManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(loManager);

        List<PanaloRewards> loList = new ArrayList<>();

    }


}