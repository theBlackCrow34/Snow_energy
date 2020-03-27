package com.sale.refaq.snow_energy.ui.inti;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sale.refaq.snow_energy.R;
import com.sale.refaq.snow_energy.databinding.ActivityVerifySmsBinding;

import java.util.Date;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class VerifySmsActivity extends AppCompatActivity {

    ActivityVerifySmsBinding binding;
    int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_verify_sms);
        binding.setLifecycleOwner(this);

        new CountDownTimer(120000,1000)
        {
            @Override
            public void onTick(long millisUntilFinished) {
                int seconds = (int) millisUntilFinished/1000;
                if (seconds >= 60)
                {
                    if ((seconds-60) <10)
                        binding.timer.setText("01:0"+(seconds-60));
                    else
                        binding.timer.setText("01:"+(seconds-60));

                }else if (seconds < 60)
                {
                    if ((seconds-counter) < 10)
                        binding.timer.setText("00:0"+(seconds-counter));
                    else
                        binding.timer.setText("00:"+(seconds-counter));
                        counter++;
                }

            }

            @Override
            public void onFinish() {
                binding.sendCode.setText("ارسال الكود");
            }
        }.start();

        SetCursor(binding.txInput1,binding.txInput2);
        SetCursor(binding.txInput2,binding.txInput3);
        SetCursor(binding.txInput3,binding.txInput4);
        SetCursor(binding.txInput4,binding.txInput4);

        binding.sendCode.setText("انتظر ارسال الكود مرة أخري بعد");



    }
    private void SetCursor(final TextInputEditText tx_bg, final TextInputEditText tx_forward)
    {
        tx_bg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (tx_bg == tx_forward && s.length()!=0)
                {
                    binding.progressDialog.setVisibility(View.VISIBLE);

                    Intent intent = new Intent(getApplicationContext(),RegisterDataActivity.class);
                    startActivity(intent);
                }else if (s.length()!=0)
                {
                    tx_forward.setFocusableInTouchMode(true);
                    tx_forward.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
