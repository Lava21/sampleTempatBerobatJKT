package com.mas.samplejakartahealth.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
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
import com.mas.samplejakartahealth.model.RumahSakitModel;

public class DetailRumahSakitActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mGoogleMap;
    private double latitude, longitude;
    TextView tvNameRS, tvJenisRS, tvKodePosRS, tvNoTelpnRS, tvEmailRS, tvFaxRS, tvWebsiteRS;
    String NameRS, JenisRS, KodePosRS, NoTelpnRS, EmailRS, WebsiteRS, AlamatRS, FaxRS;
    RumahSakitModel rumahSakitModel;
    Button btnCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_rumah_sakit);

        Toolbar tbDetailRS = findViewById(R.id.tbDetailRS);
        setSupportActionBar(tbDetailRS);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        rumahSakitModel = (RumahSakitModel) getIntent().getSerializableExtra("rsDetail");
        if (rumahSakitModel != null){
            NameRS = rumahSakitModel.getNama_rs();
            JenisRS = rumahSakitModel.getJenis_rs();
            KodePosRS = rumahSakitModel.getKode_pos();
            NoTelpnRS = rumahSakitModel.getTelepon();
            EmailRS = rumahSakitModel.getEmail();
            FaxRS = rumahSakitModel.getFaximile();
            WebsiteRS = rumahSakitModel.getWebsite();
            latitude = rumahSakitModel.getLatitude();
            longitude = rumahSakitModel.getLongitude();

            tvNameRS = findViewById(R.id.tvDetailNameRS);
            tvJenisRS = findViewById(R.id.tvDetailJenisRS);
            tvKodePosRS = findViewById(R.id.tvDetailKodePos);
            tvNoTelpnRS = findViewById(R.id.tvDetailNoTelpn);
            tvEmailRS = findViewById(R.id.tvDetailEmail);
            tvFaxRS = findViewById(R.id.tvDetailFax);
            tvWebsiteRS = findViewById(R.id.tvDetailWebsite);
            btnCall = findViewById(R.id.btnCall);

            tvNameRS.setText("RS" + " " + NameRS);
            tvJenisRS.setText(JenisRS);
            tvKodePosRS.setText(KodePosRS);
            tvNoTelpnRS.setText("021" + NoTelpnRS);
            tvEmailRS.setText(EmailRS);
            tvFaxRS.setText("021" + FaxRS);
            if (WebsiteRS == null){
                tvWebsiteRS.setText("-");
            } else {
                SpannableString spannableString = new SpannableString("http://" + WebsiteRS);
                spannableString.setSpan(new UnderlineSpan(), 0, spannableString.length(), 0);
                tvWebsiteRS.setText(spannableString);
                tvWebsiteRS.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + WebsiteRS));
                        startActivity(intent);
                    }
                });
            }

            btnCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "021" + NoTelpnRS));
                    startActivity(intent);
                }
            });
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        LatLng latLng = new LatLng(latitude, longitude);
        mGoogleMap.addMarker(new MarkerOptions().position(latLng).title(AlamatRS));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 16));
        mGoogleMap.getUiSettings().setAllGesturesEnabled(true);
        mGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        mGoogleMap.setTrafficEnabled(true);
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