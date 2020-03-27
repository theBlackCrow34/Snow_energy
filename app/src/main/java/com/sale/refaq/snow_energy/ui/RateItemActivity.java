package com.sale.refaq.snow_energy.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.sale.refaq.snow_energy.R;
import com.sale.refaq.snow_energy.databinding.ActivityRateItemBinding;

public class RateItemActivity extends AppCompatActivity {

    ActivityRateItemBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_rate_item);
        binding.setLifecycleOwner(this);
    }
}
