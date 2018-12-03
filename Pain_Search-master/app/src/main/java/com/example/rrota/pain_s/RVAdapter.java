package com.example.rrota.pain_s;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
/**
 * Created by KamillaKhairullina
 * Адаптер для RecyclerView
 */
class RVAdapter extends RecyclerView.Adapter < RVAdapter.PersonViewHolder > {

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView Date;
        TextView Disease;
        TextView Doctors;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            Date = (TextView) itemView.findViewById(R.id.date);
            Disease = (TextView) itemView.findViewById(R.id.disease);
            Doctors = (TextView) itemView.findViewById(R.id.doctors);
        }
    }

    List < Disease > diseases;

    RVAdapter(List < Disease > diseases) {
        this.diseases = diseases;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.disease_cards, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.Date.setText(diseases.get(i).date);
        personViewHolder.Disease.setText(diseases.get(i).disease);
        personViewHolder.Doctors.setText(diseases.get(i).doctors);
    }

    @Override
    public int getItemCount() {
        return diseases.size();
    }
}