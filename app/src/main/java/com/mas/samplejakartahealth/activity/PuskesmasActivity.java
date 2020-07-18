package com.mas.samplejakartahealth.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.mas.samplejakartahealth.R;
import com.mas.samplejakartahealth.adapter.PuskesmasAdapter;
import com.mas.samplejakartahealth.model.PuskesmasModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PuskesmasActivity extends AppCompatActivity implements PuskesmasAdapter.onSelectData {

    RecyclerView recyclerView;
    PuskesmasAdapter puskesmasAdapter;
    List<PuskesmasModel> puskesmasModelList = new ArrayList<>();
    ProgressDialog progressDialog;
    Toolbar tbPuskesmas;
    TextView tvNotFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puskesmas);

        tvNotFound = findViewById(R.id.tvNotFound);
        ImageView ivRefresh = findViewById(R.id.ivRefresh);
        tbPuskesmas = findViewById(R.id.tbPuskesmas);
        setSupportActionBar(tbPuskesmas);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Mohon Tunggu");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Sedang menampilkan data...");

        recyclerView = findViewById(R.id.rvPuskesmas);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadJSON();

        ivRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });
    }

    private void loadJSON(){
        progressDialog.show();
        AndroidNetworking.get("http://api.jakarta.go.id/v1/puskesmas")
                .addHeaders("Authorization", "5h30dB4K4Uwuhj4KkmHmFuOgeIeo+XxK4jKRm/v5lNNfbGfsYx2wB2D4IKQWaSu7")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            progressDialog.dismiss();
                            JSONArray jsonArray = response.getJSONArray("data");
                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                PuskesmasModel dataAPI = new PuskesmasModel();

                                JSONObject jsonObject1 = jsonObject.getJSONObject("location");
                                String alamat = jsonObject1.getString("alamat");
                                double latitude = jsonObject1.getDouble("latitude");
                                double longitude = jsonObject1.getDouble("longitude");

                                JSONArray jsonArray1 = jsonObject.getJSONArray("telepon");
                                for (int x = 0; x < jsonArray1.length(); x++){
                                    String telepon = jsonArray1.get(x).toString();
                                    dataAPI.setTelepon(telepon);
                                }

                                JSONArray jsonArray2 = jsonObject.getJSONArray("faximile");
                                for (int x = 0; x < jsonArray2.length(); x++){
                                    String faximile = jsonArray2.get(x).toString();
                                    dataAPI.setFaximile(faximile);
                                }

                                dataAPI.setNamaPuskesmas(jsonObject.getString("nama_Puskesmas"));
                                dataAPI.setKepalaPuskesmas(jsonObject.getString("kepala_puskesmas"));
                                dataAPI.setEmail(jsonObject.getString("email"));
                                dataAPI.setLatitude(latitude);
                                dataAPI.setLongitude(longitude);
                                dataAPI.setLocation(alamat);

                                puskesmasModelList.add(dataAPI);
                                shohPuskesmas();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(PuskesmasActivity.this,
                                    "Gagal menampilkan data!", Toast.LENGTH_SHORT).show();
                        }

                        if (response.length() == 0){
                            tvNotFound.setVisibility(View.VISIBLE);
                        } else {
                            tvNotFound.setVisibility(View.GONE);
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(ANError anError) {
                        tvNotFound.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                        Toast.makeText(PuskesmasActivity.this,
                                "Tidak ada jaringan internet!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void shohPuskesmas(){
        puskesmasAdapter = new PuskesmasAdapter(PuskesmasActivity.this, puskesmasModelList, this);
        recyclerView.setAdapter(puskesmasAdapter);
    }

    @Override
    public void onSelected(PuskesmasModel puskesmasModel) {
        Intent intent = new Intent(PuskesmasActivity.this, DetailPuskesmasActivity.class);
        intent.putExtra("puskesmasDetail", puskesmasModel);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}