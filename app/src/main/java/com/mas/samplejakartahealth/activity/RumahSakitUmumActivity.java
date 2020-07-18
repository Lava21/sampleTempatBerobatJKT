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
import com.mas.samplejakartahealth.adapter.RSUmumAdapter;
import com.mas.samplejakartahealth.model.RumahSakitModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RumahSakitUmumActivity extends AppCompatActivity implements RSUmumAdapter.onSelectData {

    RecyclerView recyclerView;
    RSUmumAdapter rsUmumAdapter;
    List<RumahSakitModel> rumahSakitModelList = new ArrayList<>();
    ProgressDialog progressDialog;
    Toolbar tbRSU;
    TextView tvNoteFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rumah_sakit);

        tvNoteFound = findViewById(R.id.tvNotFound);
        ImageView ivRefresh = findViewById(R.id.ivRefresh);
        tbRSU = findViewById(R.id.tbRS);
        setSupportActionBar(tbRSU);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Mohon Tunggu");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Sedang menampilkan data...");

        recyclerView = findViewById(R.id.rvRS);
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
        AndroidNetworking.get("http://api.jakarta.go.id/v1/rumahsakitumum")
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
                                RumahSakitModel dataAPI = new RumahSakitModel();

                                JSONObject jsonObject1 = jsonObject.getJSONObject("location");
                                String alamat = jsonObject1.getString("alamat");

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

                                dataAPI.setNama_rs(jsonObject.getString("nama_rsu"));
                                dataAPI.setJenis_rs(jsonObject.getString("jenis_rsu"));
                                dataAPI.setKode_pos(jsonObject.getString("kode_pos"));
                                dataAPI.setEmail(jsonObject.getString("email"));
                                dataAPI.setWebsite(jsonObject.getString("website"));
                                dataAPI.setLatitude(jsonObject.getDouble("latitude"));
                                dataAPI.setLongitude(jsonObject.getDouble("longitude"));

                                rumahSakitModelList.add(dataAPI);
                                showRumahSakit();
                            }
                        } catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(RumahSakitUmumActivity.this,
                                    "Gagal menampilkan data!!!", Toast.LENGTH_SHORT).show();
                        }

                        if (response.length() == 0){
                            tvNoteFound.setVisibility(View.VISIBLE);
                        } else {
                            tvNoteFound.setVisibility(View.GONE);
                        }
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(ANError anError) {
                        tvNoteFound.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                        Toast.makeText(RumahSakitUmumActivity.this,
                                "Tidak ada jaringan Internet!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showRumahSakit(){
        rsUmumAdapter = new RSUmumAdapter(RumahSakitUmumActivity.this, rumahSakitModelList, this);
        recyclerView.setAdapter(rsUmumAdapter);
    }

    @Override
    public void onSelectData(RumahSakitModel rumahSakitModel) {
        Intent intent = new Intent(RumahSakitUmumActivity.this, DetailRumahSakitActivity.class);
        intent.putExtra("rsDetail", rumahSakitModel);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}