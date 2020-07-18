package com.mas.samplejakartahealth.holder;

import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mas.samplejakartahealth.R;

public class PuskesmasHolder extends RecyclerView.ViewHolder {
    public TextView tvNamaPuskesmas, tvLocation;
    public CardView cvPuskesmas;

    public PuskesmasHolder(View view){
        super(view);

        cvPuskesmas = view.findViewById(R.id.cvPuskesmas);
        tvNamaPuskesmas = view.findViewById(R.id.tvNamaPuskesmas);
        tvLocation = view.findViewById(R.id.tvLocationPuskesmas);
    }
}
