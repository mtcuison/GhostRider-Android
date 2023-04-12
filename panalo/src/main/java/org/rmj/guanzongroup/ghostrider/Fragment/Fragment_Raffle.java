package org.rmj.guanzongroup.ghostrider.Fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.rmj.g3appdriver.dev.Database.Entities.ERaffleStatus;
import org.rmj.g3appdriver.etc.MessageBox;
import org.rmj.g3appdriver.lib.Panalo.model.PanaloRewards;
import org.rmj.guanzongroup.ghostrider.Adapter.AdapterRaffleDraw;
import org.rmj.guanzongroup.ghostrider.Dialog.DialogPanaloRedeem;
import org.rmj.guanzongroup.ghostrider.R;
import org.rmj.guanzongroup.ghostrider.ViewModel.VMRaffle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Fragment_Raffle extends Fragment {

    private VMRaffle mViewModel;
    private MessageBox loMessage;
    private RecyclerView recyclerView;
    private RelativeLayout rlEmpty;
    private TextView lblFirstNme,
            lblLastNme,
            lblMiddleNme,
            lblStatus;
    private Handler handler;
    private boolean isRunning;

    private String[] names = {"John", "Jane", "Bob", "Alice", "Tom", "Linda", "Mike", "Sara"};

    public static Fragment_Raffle newInstance() {
        return new Fragment_Raffle();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(requireActivity()).get(VMRaffle.class);
        View view = inflater.inflate(R.layout.fragment_raffle, container, false);
        initWidgets(view);
        handler = new Handler(Looper.getMainLooper());

        mViewModel.GetRaffleStatus().observe(requireActivity(), new Observer<ERaffleStatus>() {
            @Override
            public void onChanged(ERaffleStatus status) {
                try{
                    if(status == null) {
                        return;
                    }
                    switch (status.getHasRffle()){
                        case 0:
                            lblStatus.setText("Raffle draw every monday at 3PM");
                            isRunning = false;
//                            startGeneratingNames();
                            initResult();
                            break;
                        case 1:
                            lblStatus.setText("Raffle starting soon...");
                            isRunning = false;
//                            startGeneratingNames();
                            initResult();
                            break;
                        case 2:
                            lblStatus.setText("Raffle Started");
                            isRunning = true;
                            startGeneratingNames();
                            break;
                        default:
                            lblStatus.setText("Raffle Ended.");
                            isRunning = false;
//                            startGeneratingNames();
                            initResult();
                            break;
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


//
        return  view;
    }
    private void startGeneratingNames() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning) {
                    final String randomName = getRandomName();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            lblStatus.setText(randomName);
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private String getRandomName() {
        Random random = new Random();
        int index = random.nextInt(names.length);
        return names[index];
    }
    private void initWidgets(View v) {
        recyclerView = v.findViewById(R.id.recyclerView);
        lblStatus = v.findViewById(R.id.lblRaffleStatus);
        rlEmpty = v.findViewById(R.id.rlEmpty);
        LinearLayoutManager loManager = new LinearLayoutManager(requireActivity());
        loManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(loManager);

        List<PanaloRewards> loList = new ArrayList<>();

    }

    private void initResult(){
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
    }
}