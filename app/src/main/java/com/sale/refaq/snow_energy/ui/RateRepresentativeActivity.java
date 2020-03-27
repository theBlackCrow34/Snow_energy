package com.sale.refaq.snow_energy.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.sale.refaq.snow_energy.R;
import com.sale.refaq.snow_energy.databinding.ActivityRateRepresentativeBinding;

public class RateRepresentativeActivity extends AppCompatActivity {

    ActivityRateRepresentativeBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_rate_representative);
        binding.setLifecycleOwner(this);
    }
}
