package com.mas.samplejakartahealth.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.mas.samplejakartahealth.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= 19 && Build.VERSION.SDK_INT < 21){
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true);
        }

        if (Build.VERSION.SDK_INT >= 19){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_LAYOUT_STABLE|
                                View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN|
                                View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                );
            }
        }

        if (Build.VERSION.SDK_INT >= 21){
            setWindowFlag(this, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        LinearLayout llRSUmum = findViewById(R.id.llRSUmum);
        llRSUmum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RumahSakitUmumActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout llRSKhusus = findViewById(R.id.llRSKhusus);
        llRSKhusus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RumahSakitKhususActivity.class);
                startActivity(intent);
            }
        });

        LinearLayout llPuskesmas = findViewById(R.id.llPuskesmas);
        llPuskesmas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(MainActivity.this, PuskesmasActivity.class);
                startActivity(intent);
            }
        });
    }

    public static void setWindowFlag(Activity activity, final int bits, boolean on){
        Window window = activity.getWindow();
        WindowManager.LayoutParams winParams = window.getAttributes();
        if (on){
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        window.setAttributes(winParams);
    }
}