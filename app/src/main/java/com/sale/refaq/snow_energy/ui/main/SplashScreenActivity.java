package com.sale.refaq.snow_energy.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sale.refaq.snow_energy.R;
import com.sale.refaq.snow_energy.ui.DeliveryAddressDetailsActivity;
import com.sale.refaq.snow_energy.ui.DeliveryAllDetailsActivity;
import com.sale.refaq.snow_energy.ui.MainViewActivity;
import com.sale.refaq.snow_energy.ui.inti.LoginActivity;
import com.sale.refaq.snow_energy.ui.inti.VerifySmsActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_LENTGH = 2000;
    private ImageView img;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        firebaseAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                checkUserStatus();
            }
        },SPLASH_DISPLAY_LENTGH);
    }
    private void checkUserStatus()
    {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null)
        {
            startActivity(new Intent(getApplicationContext(),MainViewActivity.class));
            finish();
        }else
        {
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            finish();
        }
    }
}
