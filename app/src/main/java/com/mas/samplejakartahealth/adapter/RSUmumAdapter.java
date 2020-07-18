package com.mas.samplejakartahealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mas.samplejakartahealth.R;
import com.mas.samplejakartahealth.activity.RumahSakitUmumActivity;
import com.mas.samplejakartahealth.holder.RumahSakitHolder;
import com.mas.samplejakartahealth.model.RumahSakitModel;

import java.util.List;

public class RSUmumAdapter extends RecyclerView.Adapter<RumahSakitHolder> {
    public List<RumahSakitModel> rumahSakitModelList;
    private RSUmumAdapter.onSelectData onSelectData;
    private Context mContext;

    public interface onSelectData {
        void onSelectData(RumahSakitModel rumahSakitModel);
    }

    public RSUmumAdapter(Context mContext, List<RumahSakitModel> rumahSakitModelList, RSUmumAdapter.onSelectData xSelectData) {
        this.rumahSakitModelList = rumahSakitModelList;
        this.onSelectData = xSelectData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RumahSakitHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_rs_umum, parent, false);
        return new RumahSakitHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RumahSakitHolder holder, int position) {
        final RumahSakitModel data = rumahSakitModelList.get(position);
        holder.tvNamaRS.setText("RS" + " " + data.getNama_rs());
        holder.tvLocation.setText(data.getLocation());
        holder.cvRS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectData.onSelectData(data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return rumahSakitModelList.size();
    }
}
