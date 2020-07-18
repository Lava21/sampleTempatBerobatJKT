package com.mas.samplejakartahealth.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.mas.samplejakartahealth.R;
import com.mas.samplejakartahealth.adapter.RSKhususAdapter;
import com.mas.samplejakartahealth.model.RumahSakitModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.RandomAccess;

public class RumahSakitKhususActivity extends AppCompatActivity implements RSKhususAdapter.onSelectData {
    RecyclerView recyclerView;
    RSKhususAdapter hospitalAdapter;
    List<RumahSakitModel> modelRumahSakitList = new ArrayList<>();
    ProgressDialog progressDialog;
    Toolbar tbRSU;
    TextView tvNotFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rumah_sakit);

        tvNotFound = findViewById(R.id.tvNotFound);
        ImageView imgRefresh = findViewById(R.id.ivRefresh);
        tbRSU = findViewById(R.id.tbRS);
        setSupportActionBar(tbRSU);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Mohon tunggu");
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Sedang menampilkan data");

        recyclerView = findViewById(R.id.rvRS);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadJSON();

        imgRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getIntent();
                finish();
                startActivity(intent);
            }
        });

    }

    private void loadJSON() {
        progressDialog.show();
        AndroidNetworking.get("http://api.jakarta.go.id/v1/rumahsakitkhusus")
                .addHeaders("Authorization", "5h30dB4K4Uwuhj4KkmHmFuOgeIeo+XxK4jKRm/v5lNNfbGfsYx2wB2D4IKQWaSu7")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            progressDialog.dismiss();
                            JSONArray jsonArray1 = response.getJSONArray("data");
                            for (int i = 0; i < jsonArray1.length(); i++) {

                                JSONObject jsonObject1 = jsonArray1.getJSONObject(i);
                                RumahSakitModel dataApi = new RumahSakitModel();

                                JSONObject jsonObject2 = jsonObject1.getJSONObject("location");
                                String alamat = jsonObject2.getString("alamat");

                                JSONArray jsonArray2 = jsonObject1.getJSONArray("telepon");
                                for (int x = 0; x < jsonArray2.length(); x++) {
                                    String telepon = jsonArray2.get(x).toString();
                                    dataApi.setTelepon((telepon));
                                }

                                JSONArray jsonArray3 = jsonObject1.getJSONArray("faximile");
                                for (int x = 0; x < jsonArray3.length(); x++) {
                                    String faximile = jsonArray3.get(x).toString();
                                    dataApi.setFaximile((faximile));
                                }

                                dataApi.setNama_rs(jsonObject1.getString("nama_rsk"));
                                dataApi.setJenis_rs(jsonObject1.getString("jenis_rsk"));
                                dataApi.setKode_pos(jsonObject1.getString("kode_pos"));
                                dataApi.setEmail(jsonObject1.getString("email"));
                                dataApi.setWebsite(jsonObject1.getString("website"));
                                dataApi.setLatitude(jsonObject1.getDouble("latitude"));
                                dataApi.setLongitude(jsonObject1.getDouble("longitude"));
                                dataApi.setLocation(alamat);

                                modelRumahSakitList.add(dataApi);
                                showRumahSakit();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RumahSakitKhususActivity.this,
                                    "Gagal menampilkan data!", Toast.LENGTH_SHORT).show();
                        }
                        if (response.length() == 0)
                            tvNotFound.setVisibility(View.VISIBLE);
                        else
                            tvNotFound.setVisibility(View.GONE);
                        progressDialog.dismiss();
                    }

                    @Override
                    public void onError(ANError anError) {
                        tvNotFound.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                        Toast.makeText(RumahSakitKhususActivity.this, "Tidak ada jaringan internet!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showRumahSakit() {
        hospitalAdapter = new RSKhususAdapter(RumahSakitKhususActivity.this, modelRumahSakitList, this);
        recyclerView.setAdapter(hospitalAdapter);
    }

    @Override
    public void onSelectData(RumahSakitModel rumahSakitModel) {
        Intent intent = new Intent(RumahSakitKhususActivity.this, DetailRumahSakitActivity.class);
        intent.putExtra("rsDetail", rumahSakitModel);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
