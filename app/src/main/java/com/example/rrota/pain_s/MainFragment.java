package com.example.rrota.pain_s;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by EmilLatypov
 * Активити с CardView
 */
public class MainFragment extends Fragment {


    @Nullable
    @Override

    //Создание Активити
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);
        CardView card = (CardView) v.findViewById(R.id.profile_card);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intt = new Intent(getActivity(), Profile.class);
                startActivity(intt);
            }
        });
        CardView card2 = (CardView) v.findViewById(R.id.history_card);
        card2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intt = new Intent(getActivity(), History.class);
                startActivity(intt);
            }
        });
        CardView card3 = (CardView) v.findViewById(R.id.card_info);
        card3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intt = new Intent(getActivity(), Info.class);
                startActivity(intt);
            }
        });
        CardView card4 = (CardView) v.findViewById(R.id.connect_card);
        card4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intt = new Intent(getActivity(), ConnectActivity.class);
                startActivity(intt);
            }
        });
        return v;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Главная");

    }
}