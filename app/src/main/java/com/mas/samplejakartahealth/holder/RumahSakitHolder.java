package com.mas.samplejakartahealth.holder;

import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mas.samplejakartahealth.R;

public class RumahSakitHolder extends RecyclerView.ViewHolder {
    public TextView tvNamaRS, tvLocation;
    public CardView cvRS;

    public RumahSakitHolder(View view){
        super(view);

        cvRS = view.findViewById(R.id.cvRS);
        tvNamaRS = view.findViewById(R.id.tvNamaRS);
        tvLocation = view.findViewById(R.id.tvLocationRS);
    }
}
