package com.mas.samplejakartahealth.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mas.samplejakartahealth.R;
import com.mas.samplejakartahealth.model.PuskesmasModel;

public class DetailPuskesmasActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMaps;
    private double latitude, longitude;
    TextView tvNamaPuskes, tvNamaKepalaPuskes, tvNoTelpnPuskes, tvEmailPuskes, tvFaxPuskes;
    String sNamaPuskes, sNamaKepalaPuskes, sNoTelpnPuskes, sEmailPuskes, sAlamatPuskes, sFaxPuskes;
    PuskesmasModel puskesmasModel;
    Button btnCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_puskesmas);

        Toolbar tbDetailPuskes = findViewById(R.id.tbDetailPuskesmas);
        setSupportActionBar(tbDetailPuskes);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this);

        puskesmasModel = (PuskesmasModel) getIntent().getSerializableExtra("puskesmasDetail");
        if (puskesmasModel != null){
            sNamaPuskes = puskesmasModel.getNamaPuskesmas();
            sNamaKepalaPuskes = puskesmasModel.getKepalaPuskesmas();
            sNoTelpnPuskes = puskesmasModel.getTelepon();
            sEmailPuskes = puskesmasModel.getEmail();
            sFaxPuskes = puskesmasModel.getFaximile();
            latitude = puskesmasModel.getLatitude();
            longitude = puskesmasModel.getLongitude();

            tvNamaPuskes = findViewById(R.id.tvDetailNamaPuskes);
            tvNamaKepalaPuskes = findViewById(R.id.tvDetailNamaKepalaPuskes);
            tvNoTelpnPuskes = findViewById(R.id.tvDetailNoTelpnPuskes);
            tvEmailPuskes = findViewById(R.id.tvDetailEmailPuskes);
            tvFaxPuskes = findViewById(R.id.tvDetailFaxPuskes);
            btnCall = findViewById(R.id.btnCall);

            tvNamaPuskes.setText(sNamaPuskes);
            tvNamaKepalaPuskes.setText(sNamaKepalaPuskes);
            tvNoTelpnPuskes.setText(sNoTelpnPuskes);
            tvFaxPuskes.setText(sFaxPuskes);
            tvEmailPuskes.setText(sEmailPuskes);

            btnCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "021" + sNoTelpnPuskes));
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        googleMaps = googleMap;
        LatLng latLng = new LatLng(latitude, longitude);
        googleMaps.addMarker(new MarkerOptions().position(latLng).title(sAlamatPuskes));
        googleMaps.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMaps.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        googleMaps.getUiSettings().setAllGesturesEnabled(true);
        googleMaps.getUiSettings().setZoomControlsEnabled(true);
        googleMaps.setTrafficEnabled(true);
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