package com.mas.samplejakartahealth.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mas.samplejakartahealth.R;
import com.mas.samplejakartahealth.holder.PuskesmasHolder;
import com.mas.samplejakartahealth.model.PuskesmasModel;

import java.util.List;

public class PuskesmasAdapter extends RecyclerView.Adapter<PuskesmasHolder> {
    public List<PuskesmasModel> puskesmasModelList;
    private PuskesmasAdapter.onSelectData onSelectData;
    private Context mContext;

    public interface onSelectData{
        void onSelected(PuskesmasModel puskesmasModel);
    }

    public PuskesmasAdapter(Context context, List<PuskesmasModel> puskesmasModels, PuskesmasAdapter.onSelectData xSelectData){
        this.mContext = context;
        this.puskesmasModelList = puskesmasModels;
        this.onSelectData = xSelectData;
    }

    @NonNull
    @Override
    public PuskesmasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_puskesmas, parent, false);
        return new PuskesmasHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PuskesmasHolder holder, int position) {
        final PuskesmasModel data = puskesmasModelList.get(position);
        holder.tvNamaPuskesmas.setText(data.getNamaPuskesmas());
        holder.tvLocation.setText(data.getLocation());
        holder.cvPuskesmas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectData.onSelected(data);
            }
        });
    }

    @Override
    public int getItemCount() {
        return puskesmasModelList.size();
    }
}
